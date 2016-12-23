package cn.com.xcommon.frame.controller;

import cn.com.xcommon.frame.cache.ApplicationCache;
import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;

public class BaseController {

	protected HLogger logger = LoggerManager.getLoger(this.getClass().getSimpleName());

	protected String props(String key) {
		return ApplicationCache.getInstance().getStr(key);
	}

}