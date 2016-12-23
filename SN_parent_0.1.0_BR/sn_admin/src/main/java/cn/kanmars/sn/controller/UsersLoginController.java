package cn.kanmars.sn.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.sn.common.security.aes.AESUtil;
import cn.com.sn.frame.controller.AjaxBaseController;
import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;
import cn.kanmars.sn.base.AdvancedUserLoginBean;
import cn.kanmars.sn.entity.TblSysConfig;
import cn.kanmars.sn.entity.TblSysmenuInfo;
import cn.kanmars.sn.entity.TblSysuserInfo;
import cn.kanmars.sn.logic.MenuInfoLogic;
import cn.kanmars.sn.logic.SysConfigLogic;
import cn.kanmars.sn.logic.UserInfoLogic;
import cn.kanmars.sn.util.DicCheckbox;
import cn.kanmars.sn.util.DicList;
import cn.kanmars.sn.util.DicSelect;
import net.sf.json.JSONArray;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: UsersLoginController 
 * @Description: 用户登录 
 * @author qpf
 * @date 2016年1月7:09:08  
 */
@Controller
@RequestMapping("/login")
public class UsersLoginController extends AjaxBaseController {

	public static final HLogger logger = LoggerManager.getLoger("UsersLoginController");

	@Autowired
	private UserInfoLogic userInfoLogic;
	
	@Autowired
	private MenuInfoLogic menuInfoLogic;

	@Autowired
	private SysConfigLogic sysConfigLogic;

	private String getImageUrl(){
		String imgUrl = "/img/login-background.jpg";//默认图片
		TblSysConfig tsc = new TblSysConfig();
		tsc.setStatus("001");
		//获取系统名称
		try {
			tsc = sysConfigLogic.querySysConfig(tsc);
			if(tsc != null){
				imgUrl = tsc.getImgUrl();
			}
		} catch (Exception e) {
			logger.error("获取图片地址出错",e);
		}
		return imgUrl;
	}
	private String getSystemName(){
		String systemName = "神农后台管理系统";
		TblSysConfig tsc = new TblSysConfig();
		tsc.setStatus("001");
		//获取系统名称
		try {
			tsc = sysConfigLogic.querySysConfig(tsc);
			if(tsc != null){
				systemName = tsc.getSystemName();
			}
		} catch (Exception e) {
			logger.error("获取系统名称出错", e);
		}
		return systemName;
	}
	
	@RequestMapping("/login.dhtml")
	public String init(Model model,HttpServletRequest request, HttpServletResponse response) {
		bindHelperObject(request, response);
		String goUrl = request.getParameter("goUrl");
		if(StringUtils.isEmpty(goUrl)){
			model.addAttribute("goUrl", "");
		}else{
			model.addAttribute("goUrl", goUrl);
		}
		String systemName = getSystemName();
		String imgUrl = getImageUrl();
		model.addAttribute("systemName", systemName);
		model.addAttribute("imgUrl", imgUrl);
		model.addAttribute("checkCode", createCheckCode(request, response));
		return "login";
	}
	
	@RequestMapping("/loginCheck.dhtml")
	public String usersLogin(@ModelAttribute("userAccount") String userAccount , 
			@ModelAttribute("password") String password,
			@ModelAttribute("checkCode") String checkCode,Model model,HttpServletRequest request, HttpServletResponse response){
		try {
			if(StringUtils.isEmpty(userAccount) || StringUtils.isEmpty(password) || StringUtils.isEmpty(checkCode)){
				String systemName = getSystemName();
				String imgUrl = getImageUrl();
				model.addAttribute("systemName", systemName);
				model.addAttribute("imgUrl", imgUrl);
				model.addAttribute("checkCode", createCheckCode(request, response));
				model.addAttribute("message", "验证码为空或者密码为空");
				return "login";
			}
			String checkCodeOld = (String)request.getSession().getAttribute("checkCode");
			if(checkCodeOld == null || !checkCode.equalsIgnoreCase(checkCodeOld)){
				String systemName = getSystemName();
				String imgUrl = getImageUrl();
				model.addAttribute("systemName", systemName);
				model.addAttribute("imgUrl", imgUrl);
				model.addAttribute("checkCode", createCheckCode(request, response));
				model.addAttribute("userAccount", userAccount);
				model.addAttribute("message", "验证码不正确或者过期！");
				return "login";
			}

			//密码校验，先用AES自加密，如果没有找到，则直接用密码查询，如果还是没找到，则报错
			TblSysuserInfo tblSysuserInfo = new TblSysuserInfo();
			tblSysuserInfo.setLoginName(userAccount);
			tblSysuserInfo.setPassword(AESUtil.encryptStr(password, password));
			tblSysuserInfo = (TblSysuserInfo) userInfoLogic.queryUserInfo(tblSysuserInfo);
			if(tblSysuserInfo == null){
				tblSysuserInfo = new TblSysuserInfo();
				tblSysuserInfo.setLoginName(userAccount);
				tblSysuserInfo.setPassword(password);
				tblSysuserInfo = (TblSysuserInfo) userInfoLogic.queryUserInfo(tblSysuserInfo);
				if(tblSysuserInfo == null){
					String systemName = getSystemName();
					String imgUrl = getImageUrl();
					model.addAttribute("systemName", systemName);
					model.addAttribute("imgUrl", imgUrl);
					model.addAttribute("checkCode", createCheckCode(request, response));
					model.addAttribute("userAccount", userAccount);
					model.addAttribute("message", "登录失败,登录账号和密码不正确！");
					return "login";
				}
			}

			if(!tblSysuserInfo.getUserStatus().equals("010")){//非正常状态用户
				String systemName = getSystemName();
				String imgUrl = getImageUrl();
				model.addAttribute("systemName", systemName);
				model.addAttribute("imgUrl", imgUrl);
				model.addAttribute("checkCode", createCheckCode(request, response));
				model.addAttribute("userAccount", userAccount);
				model.addAttribute("message", "用户已被锁定，请联系管理员！");
				return "login";
			}

			tblSysuserInfo.setLoginName(userAccount);

			AdvancedUserLoginBean userLoginBean = new AdvancedUserLoginBean(tblSysuserInfo.getUserNo(),tblSysuserInfo.getLoginName(),tblSysuserInfo.getUserNickname(),tblSysuserInfo.getUserName(),null);
			userLoginBean = queryAdvancedUserLoginBean(userLoginBean);
			request.getSession().setAttribute("menuList", userLoginBean.getMenulist());
			//把登录用户的信息设置到cookie中和session中
			request.getSession().setAttribute("user", userLoginBean);
			//添加到cookie时，menulist并不会添加到cookie中
			AdvancedUserLoginBean.CookiesUtils.addCookie(userLoginBean, response);
			String goUrl = request.getParameter("goUrl");
			if(StringUtils.isNotEmpty(goUrl)){
				return "redirect:" + goUrl;
			}
		} catch (Exception e) {
			logger.error("登录异常",e);
			String systemName = getSystemName();
			String imgUrl = getImageUrl();
			model.addAttribute("systemName", systemName);
			model.addAttribute("imgUrl", imgUrl);
			model.addAttribute("checkCode", createCheckCode(request, response));
			model.addAttribute("message", "登录异常");
			return "login";
		}
		return "redirect:/login/main.dhtml";
	}

	/**
	 * 纯也没跳转，处理刷页面问题
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/main.dhtml")
	public String mainPage(Model model,HttpServletRequest request, HttpServletResponse response){
		
		return "template";
	}

	/**
	 * ajax访问获取一个新的code
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/refreshANewCheckCode.dhtml")
	public void refreshANewCheckCode(Model model,HttpServletRequest request, HttpServletResponse response){
		String code = createCheckCode(request,response);
		ajaxJsonMessage(request,response,code);
	}


	/**
	 * 创建一个新的验证码并与session绑定
	 * @param request
	 * @param response
	 * @return
	 */
	private String createCheckCode(HttpServletRequest request, HttpServletResponse response){
		String code = "";
		Random r = new Random();
		for(int i=0;i<4;i++){
			code +=r.nextInt(9);
		}
		request.getSession().setAttribute("checkCode", code);
		return code;
	}

	@RequestMapping("/logout.dhtml")
	public String logout(Model model,HttpServletRequest request, HttpServletResponse response) {
		AdvancedUserLoginBean.CookiesUtils.delCookie(request, response);
		request.getSession().removeAttribute("userInfo");
		return "redirect:/login/login.dhtml";
	}

	@RequestMapping("/isLogin.dhtml")
	public void isLogin(Model model,HttpServletRequest request, HttpServletResponse response) {
		HashMap paramMap = new HashMap();
		String result = "false";
		AdvancedUserLoginBean bean = AdvancedUserLoginBean.CookiesUtils.getCookie(request,response);
		try {
			if(bean!=null){
				result="true";
			}else{
				result="false";
			}
		} catch (Exception e) {
			logger.error("判断是否登录出错", e);
		}
		paramMap.put("result",result);
		ajaxJsonMessage(request, response, paramMap, QUERY);
	}
	
	public static List<Map> treeList(List<TblSysmenuInfo> list){
		
		Map<String,TblSysmenuInfo> menuMap = new HashMap<String,TblSysmenuInfo>();
		for(TblSysmenuInfo e:list){
			menuMap.put(e.getMenuNo(), e);
		}
		Map<String,Map> result = new HashMap<String,Map>();
		for(TblSysmenuInfo e : list){
			getMenuNode(e.getMenuNo(),result,menuMap);
		}
		List<Map> arrayList = new ArrayList<Map>();
		for(Map m:result.values()){
			arrayList.add(m);
		}
		Collections.sort(arrayList, new Comparator<Map>() {

			public int compare(Map o1, Map o2) {
				Integer munuIdx1 = (Integer) o1.get("menuIdx");
				Integer munuIdx2 = (Integer) o2.get("menuIdx");
				if (munuIdx1 == null || munuIdx2 == null) return 0;
				return munuIdx1.compareTo(munuIdx2);
			}
		});
		return arrayList;
	}

	/**
	 * 查询用户的权限信息
	 * @param a
	 * @return
	 */
	public AdvancedUserLoginBean queryAdvancedUserLoginBean(AdvancedUserLoginBean a){
		try{
			List<TblSysmenuInfo> menuList = menuInfoLogic.queryUserMenu(a.getUserId());
			List<Map> arrayList = new ArrayList<Map>();
			arrayList = treeList(menuList);
			JSONArray jsonarray = JSONArray.fromObject(arrayList);
			//把登录用户的菜单信息放到session中
			a.setMenulist(jsonarray.toString());
			return a;
		}catch (Exception e){
			logger.error("查询权限信息出错",e);
		}
		return null;
	}
	

	
	
	
	
	public static void main(String[] args) {
		
		List<TblSysmenuInfo> list = new ArrayList<TblSysmenuInfo>();
		list.add(new TblSysmenuInfo("0001", "0001", "", "0000", null, 1, null, null, null));
		list.add(new TblSysmenuInfo("0002", "0002", "", "0001", null, 2, null, null, null));
		list.add(new TblSysmenuInfo("0003", "0003", "", "0001", null, 3, null, null, null));
		list.add(new TblSysmenuInfo("0004", "0004", "", "0002", null, 4, null, null, null));
		list.add(new TblSysmenuInfo("0005", "0005", "", "0004", null, 5, null, null, null));
		list.add(new TblSysmenuInfo("0006", "0006", "",  null,  null, 6, null, null, null));
		list.add(new TblSysmenuInfo("0007", "0007", "", "0006", null, 7, null, null, null));
		list.add(new TblSysmenuInfo("0008", "0008", "", "0007", null, 8, null, null, null));
		list.add(new TblSysmenuInfo("0009", "0009", "", "0006", null, 9, null, null, null));
		list.add(new TblSysmenuInfo("0010", "0010", "", "0007", null, 10, null, null, null));
		
		Map<String,TblSysmenuInfo> menuMap = new HashMap<String,TblSysmenuInfo>();
		for(TblSysmenuInfo e:list){
			menuMap.put(e.getMenuNo(), e);
		}
		
		Map<String,Map> result = new HashMap<String,Map>();
		for(TblSysmenuInfo e : list){
			if(e.getMenuNo().equals("0006")){
				logger.info("1111");
			}
			getMenuNode(e.getMenuNo(),result,menuMap);
		}
		List<Map> arrayList = new ArrayList<Map>();
		for(Map m:result.values()){
			arrayList.add(m);
		}
		Collections.sort(arrayList,new Comparator<Map>() {

			public int compare(Map o1, Map o2) {
				Integer munuIdx1 = (Integer)o1.get("menuIdx");
				Integer munuIdx2 = (Integer)o2.get("menuIdx");
				if(munuIdx1==null || munuIdx2 == null)return 0;
				return munuIdx1.compareTo(munuIdx2);
			}
		});
		
		logger.info(JSONArray.fromObject(arrayList).toString());
	}
	
	public static HashMap getMenuNode(String menuCode,Map<String,Map> result,Map<String,TblSysmenuInfo> menuMap){
		if(menuMap.get(menuCode)==null)return null;
		if(menuMap.get(menuCode).getParentMenuCode()==null || menuMap.get(menuCode).getParentMenuCode().equals("")){
			HashMap map1 = (HashMap)result.get(menuCode);
			if(map1!=null){
				return map1;
			}
			map1= new LinkedHashMap<String, Object>();
			map1.put("menuNo", menuMap.get(menuCode).getMenuNo());
			map1.put("menuName", menuMap.get(menuCode).getMenuName());
			map1.put("menuUrl", menuMap.get(menuCode).getMenuUrl());
			map1.put("menuType", menuMap.get(menuCode).getMenuType());
			map1.put("menuIdx", menuMap.get(menuCode).getMenuIdx());
			map1.put("parentMenuCode", menuMap.get(menuCode).getParentMenuCode());
			result.put(menuMap.get(menuCode).getMenuNo(), map1);
			return map1;
		}else{
			HashMap parent = getMenuNode(menuMap.get(menuCode).getParentMenuCode(),result,menuMap);
			if(parent == null){
				HashMap map1 = (HashMap)result.get(menuCode);
				if(map1!=null){
					return map1;
				}
				map1= new LinkedHashMap<String, Object>();
				map1.put("menuNo", menuMap.get(menuCode).getMenuNo());
				map1.put("menuName", menuMap.get(menuCode).getMenuName());
				map1.put("menuUrl", menuMap.get(menuCode).getMenuUrl());
				map1.put("menuType", menuMap.get(menuCode).getMenuType());
				map1.put("menuIdx", menuMap.get(menuCode).getMenuIdx());
				map1.put("parentMenuCode", menuMap.get(menuCode).getParentMenuCode());
				result.put(menuMap.get(menuCode).getMenuNo(), map1);
				return map1;
			}
			
			HashMap map1= null;
			//获取父层结构
			List<HashMap> childList  = (List<HashMap>)parent.get("child");
			if(childList==null){
				childList = new ArrayList();
				parent.put("child", childList);
			}
			boolean hasCell = false;
			for(HashMap cell:childList){
				String cell_menuNo = (String)cell.get("menuNo");
				if(cell_menuNo.equals(menuCode)){
					return cell;
				}
			}
			
			map1 = new LinkedHashMap<String, Object>();
			map1.put("menuNo", menuMap.get(menuCode).getMenuNo());
			map1.put("menuName", menuMap.get(menuCode).getMenuName());
			map1.put("menuUrl", menuMap.get(menuCode).getMenuUrl());
			map1.put("menuType", menuMap.get(menuCode).getMenuType());
			map1.put("menuIdx", menuMap.get(menuCode).getMenuIdx());
			map1.put("parentMenuCode", menuMap.get(menuCode).getParentMenuCode());
			
			childList.add(map1);
			
			Collections.sort(childList,new Comparator<Map>() {

				public int compare(Map o1, Map o2) {
					Integer munuIdx1 = (Integer)o1.get("menuIdx");
					Integer munuIdx2 = (Integer)o2.get("menuIdx");
					if(munuIdx1==null || munuIdx2 == null)return 0;
					return munuIdx1.compareTo(munuIdx2);
				}
			});
			return map1;
		}
	}

	/**
	 * 对Session绑定帮助对象
	 * 用法为，在FTL页面上调用${dicSelect['l1Code,l2Code']}
	 * 用法为，在FTL页面上调用${dicRadio['l1Code,l2Code']}
	 * @param request
	 * @param response
	 */
	private void bindHelperObject(HttpServletRequest request, HttpServletResponse response){
		request.getSession().setAttribute("dicSelect", DicSelect.getInstance());
		request.getSession().setAttribute("dicCheckbox", DicCheckbox.getInstance());
		request.getSession().setAttribute("dicList", DicList.getInstance());
	}

}
