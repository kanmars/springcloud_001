package cn.kanmars.sn.controller;

import cn.com.sn.frame.util.MainSecurity;
import cn.com.sn.frame.util.StringUtils;
import cn.kanmars.sn.utils.GlobalTaskInfoUtils;
import cn.kanmars.sn.utils.BasisConstants;
import cn.kanmars.sn.utils.TaskTrigerUtils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by baolong on 2016/12/23.
 */
@Controller
public class MainController {

    @RequestMapping("/")
    public ModelAndView Main(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        ModelAndView mv = new ModelAndView("index");
        String s = request.getParameter("type");
        if(StringUtils.isEmpty(s)){s="";}
        Cookie[] cookies = request.getCookies();
        boolean isLogin = false;
        if(cookies!=null)for(Cookie cookie : cookies){
            if(cookie.getName().equals("SN-taskadmin")){
                String val = cookie.getValue();
                try{
                    val = MainSecurity.decode(val);
                    mv.addObject("val",val);
                    isLogin = true;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        mv.addObject("isLogin",isLogin);
        mv.addObject("s",s);
        if(!isLogin&&s.equals("login")){
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            System.out.println("userName["+userName+"]password["+password+"]");
            if(userName.equals("admin")&&password.equals(MainSecurity.decode("axgh7wnjjRGqOMwPFYz4Qw=="))){
                System.out.println("OK");
                Cookie ck = new Cookie("SN-taskadmin",MainSecurity.encode(password));
                ck.setMaxAge(60 * 60);//1小时
                response.addCookie(ck);
                mv.addObject("passwordCheck",true);
                return mv;
            }
            mv.addObject("passwordCheck",false);
            return mv;
        }

        //退出登录
        if(isLogin&&(StringUtils.isNotEmpty(s)&&s.equals("logout"))){
            Cookie ck = new Cookie("SN-taskadmin","out");
            ck.setMaxAge(0);
            response.addCookie(ck);
            return mv;
        }

        if(isLogin&&StringUtils.isEmpty(s)){
            GlobalTaskInfoUtils g = new GlobalTaskInfoUtils();
            mv.addObject("tBean",g);

            String paixuType = request.getParameter("paixuType");
            String searchStr = request.getParameter("searchStr");
            String ascFlg = request.getParameter("ascFlg");
            String tableInfo = g.getGlobalTaskInfo(paixuType,searchStr,ascFlg);
            mv.addObject("tableInfo", tableInfo);
            return mv;
        }

        if(isLogin&&StringUtils.isNotEmpty(s)&&s.trim().equals("taskTriger")) {
            String globalTaskName = request.getParameter("globalTaskName");
            globalTaskName = URLDecoder.decode(globalTaskName, "utf-8");
            String msg = request.getParameter("msg");
            msg = URLDecoder.decode(msg,"utf-8");
            String globalIndex = request.getParameter("globalIndex");

            String mqProducerGroup = BasisConstants.consumerGroup;
            String instanceName = "finance-payment-instance_001";
            String nameServerAddr = BasisConstants.mqNotifyAddr;
            String mqNotifyGroup = BasisConstants.mqNotifyGroup;
            String businessObjName = globalTaskName.substring(0,globalTaskName.indexOf("_"));
            String globalTaskIndex = globalIndex;//调用第N个全局任务
            boolean trigerIsSuccess = TaskTrigerUtils.trigerTask(mqProducerGroup, instanceName, nameServerAddr, mqNotifyGroup, businessObjName, globalTaskName, "TASKTriger", msg);
            System.out.println("nameServerAddr["+nameServerAddr+"]");
            System.out.println("mqNotifyGroup["+mqNotifyGroup+"]");
            System.out.println("businessObjName["+businessObjName+"]");
            System.out.println("globalTaskIndex["+globalIndex+"]");
            System.out.println("globalTaskName["+request.getParameter("globalTaskName")+"]");
            System.out.println("topic["+"TASK_TRIGER_TOPIC_"+mqNotifyGroup+"]");
            System.out.println("tags["+request.getParameter("globalTaskName").replaceAll("\\.", "_")+"]");
            JSONObject jsonObject = new JSONObject();
            if(trigerIsSuccess){
                jsonObject.put("resCode","0000");
                jsonObject.put("resDesc", "成功");
            }else{
                jsonObject.put("resCode","0001");
                jsonObject.put("resDesc", "失败");
            }
            mv.addObject("trigerMsg",jsonObject.toString());
        }

        return mv;
    }
}
