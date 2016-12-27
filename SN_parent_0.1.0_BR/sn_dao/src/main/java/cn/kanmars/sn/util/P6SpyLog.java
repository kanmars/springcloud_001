//package cn.kanmars.sn.util;
//
//import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
//import com.p6spy.engine.spy.appender.StdoutLogger;
//
//public class P6SpyLog extends StdoutLogger {
//	@Override
//	public void logText(String text) {
//		super.setStrategy(new MessageFormattingStrategy() {
//
//			public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql) {
//				return sql;
//			}
//		});
//		if (text != null && !text.equals("")) {
//			super.logText("==========================================================================\r\n"
//					+ text.replace("from", "\r\n  from").replace("where", "\r\n    where").replace("order by", "\r\n      order by").replace("values (", "\r\n  values ("));
//		}
//	}
//}