package cn.com.sn.frame.controller;

import cn.com.sn.frame.cache.ApplicationCache;
import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;

public class BaseController {

	protected HLogger logger = LoggerManager.getLoger(this.getClass().getSimpleName());

	protected String props(String key) {
		return ApplicationCache.getInstance().getStr(key);
	}

}