package cn.com.xcommon.frame.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @ClassName: AjaxBaseController
 * @Description: Ajax异步操作基础控制器
 * @date 2015年2月26日 上午11:18:44
 */
public class AjaxBaseController extends BaseController {

	protected String SUCCESS = "success";

	protected String ERROR = "error";

	protected String SAVE = "save";

	protected String EDIT = "edit";

	protected String DEL = "del";

	protected String QUERY = "query";

	protected String VIEW = "view";

	protected String SUCCESS_TXT = "添加成功";

	protected String ERROR_TXT = "添加失败";

	protected String EDIT_FAIL_TXT = "修改失败";

	protected String EDIT_SUCCESS_TXT = "修改成功";

	protected String DEL_FAIL_TXT = "删除失败";

	protected String DEL_SUCCESS_TXT = "删除成功";

	protected String QUERY_FAIL_TXT = "没有查到相符的信息";

	private String ajax(HttpServletRequest request, HttpServletResponse response,String content, String type) {
		try {
			logger.info(content);
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String ajaxJsonMessage(HttpServletRequest request, HttpServletResponse response,String info) {
		return ajax(request,response,info, "text/html");
	}

	public String ajaxJsonMessage(HttpServletRequest request, HttpServletResponse response,String code, String message, String type) {
		JSONObject jo = new JSONObject();
		jo.put("code", code);
		jo.put("message", message);
		jo.put("type", type);
		return ajax(request,response,jo.toString(), "text/html");
	}
	public String ajaxJsonMessage(HttpServletRequest request, HttpServletResponse response,String code, String message, String type,Map othMsg) {
		JSONObject jo = new JSONObject();
		jo.put("code", code);
		jo.put("message", message);
		jo.put("type", type);
		jo.put("othMsg", othMsg);
		return ajax(request,response,jo.toString(), "text/html");
	}


	public String ajaxJsonMessage(HttpServletRequest request, HttpServletResponse response,Map entity, String type) {
		JSONObject jo = new JSONObject();
		JSONArray json = JSONArray.fromObject(entity);
		jo.put("rows", json.toString());
		jo.put("code", SUCCESS);
		jo.put("type", type);
		return ajax(request,response,jo.toString(), "text/html");
	}
	public String ajaxJsonMessage(List<?> entity, String type, HttpServletRequest request, HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		JSONArray json = JSONArray.fromObject(entity);
		jo.put("rows", json.toString());
		jo.put("code", SUCCESS);
		jo.put("type", type);
		return ajax(request,response,jo.toString(), "text/html");
	}
}
