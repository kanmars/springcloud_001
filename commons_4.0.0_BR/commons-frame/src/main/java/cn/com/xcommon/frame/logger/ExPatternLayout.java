package cn.com.xcommon.frame.logger;

/**
 * Description: 新的PatternParser类
 * 
 * @author chen_dapeng
 * 
 * @version 1.0, 2009-08-03
 */

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;

public class ExPatternLayout extends PatternLayout {

	
	
	public ExPatternLayout(String pattern) {
		super(pattern);
	}

	public ExPatternLayout() {
		super();
	}
	
	@Override
	public String getConversionPattern() {
		return LoggerConfiger.getConfig(LoggerConfiger.LOG_LOGOUT_CONVERSIONPATTERN);
	}
	/**
	 * 重写createPatternParser方法，返回PatternParser的子类
	 */
	protected PatternParser createPatternParser(String pattern) {
		return new ExPatternParser(getConversionPattern());
	}
	
}
