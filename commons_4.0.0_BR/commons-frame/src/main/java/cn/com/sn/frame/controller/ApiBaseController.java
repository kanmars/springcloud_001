package cn.com.sn.frame.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.com.sn.frame.cache.ApplicationCache;
import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;

/**
 * @ClassName: AjaxBaseController
 * @Description: Api服务专用的Ajax异步操作基础控制器
 * @date 2015年2月26日 上午11:18:44
 */
public class ApiBaseController{
	
	protected HLogger logger = LoggerManager.getLoger(this.getClass().getSimpleName());

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
	
	protected String props(String key) {
		return ApplicationCache.getInstance().getStr(key);
	}

	private String ajax(String content, String type,HttpServletResponse response) {
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

	public String ajaxJsonMessage(String info,HttpServletResponse response) {
		return ajax(info, "text/html",response);
	}

	public String ajaxJsonMessage(String code, String message, String type,HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		jo.put("code", code);
		jo.put("message", message);
		jo.put("type", type);
		return ajax(jo.toString(), "text/html",response);
	}
	public String ajaxJsonMessage(String code, String message, String type,Map othMsg,HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		jo.put("code", code);
		jo.put("message", message);
		jo.put("type", type);
		jo.put("othMsg", othMsg);
		return ajax(jo.toString(), "text/html",response);
	}

	public String ajaxJsonMessage(Map entity, String type,HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		JSONArray json = JSONArray.fromObject(entity);
		jo.put("rows", json.toString());
		jo.put("code", SUCCESS);
		jo.put("type", type);
		return ajax(jo.toString(), "text/html",response);
	}
}
