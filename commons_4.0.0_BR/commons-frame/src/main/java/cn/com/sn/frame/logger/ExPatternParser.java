package cn.com.sn.frame.logger;

import java.lang.reflect.Field;

import org.apache.log4j.Level;
import org.apache.log4j.helpers.FormattingInfo;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 使用反射方式获取堆栈信息的ExPatternParser
 * 
 * @author baolong 20140121
 *
 */
public class ExPatternParser extends PatternParser {

	public ExPatternParser(String pattern) {
		super(pattern);
	}

	/**
	 * 重写finalizeConverter，对特定的占位符进行处理，P表示日志级别数字显示
	 */
	protected void finalizeConverter(char c) {

		if (c == 'P') {
			this.addConverter(new LogLevel(this.formattingInfo));
		} else if (c == 'T') {
			this.addConverter(new ThreadNumber(this.formattingInfo));
		} else if (c == 'N') {
			this.addConverter(new LineNumber(this.formattingInfo));
		} else if (c == 'f') {
			String dOpt = extractOption();
			this.addConverter(new FileName(this.formattingInfo, dOpt));
		} else {
			super.finalizeConverter(c);
		}
	}

	/**
	 * 线程号显示数字
	 * 
	 * @author Captain
	 * 
	 */
	private static class ThreadNumber extends PatternConverter {

		public ThreadNumber(FormattingInfo fi) {
			super(fi);
		}

		protected String convert(LoggingEvent event) {

			int hashCode = Thread.currentThread().hashCode();
			if (hashCode < 0) {
				hashCode = -hashCode;
			}

			String hashCode2 = Integer.toString(hashCode);
			hashCode2 = Utils.fillString(hashCode2, '0', 10, false);

			return hashCode2;
		}

	}

	/**
	 * 行号显示
	 * 
	 * @author Captain
	 * 
	 */
	private static class LineNumber extends PatternConverter {

		public LineNumber(FormattingInfo fi) {
			super(fi);
		}

		protected String convert(LoggingEvent event) {
//			Object[] fullInfos = getFullInfoByREF(event);
//			if (fullInfos == null)
//				return "?";
//			Integer lineNum_i = (Integer) fullInfos[3];
//			String lineNum_s = Utils.fillString(lineNum_i.toString(), '0', 4, false);
//			return lineNum_s;
			return "";
		}

	}

	/**
	 * 文件名对齐
	 * 
	 * @author Captain
	 * 
	 */
	private static class FileName extends PatternConverter {

		private int length = 0;

		public FileName(FormattingInfo fi, String dOpt) {
			super(fi);
			try {
				if (dOpt != null && !dOpt.trim().equals("")) {
					length = Integer.parseInt(dOpt.trim());
				}

			} catch (NumberFormatException e) {
				e.printStackTrace();
				System.out.println("dOpt=" + dOpt);
			}
		}
		private byte[] spaceBytes = "                                                              ".getBytes();
		private byte[] zeroBytes  = "00000000000000000000000000000000000000000000000000000000000000".getBytes();
		protected String convert(LoggingEvent event) {

			Object[] fullInfos = getFullInfoByREF(event);
			if (fullInfos == null)
				return "?";
			String fileName = (String) fullInfos[2];
			Integer lineNum_i = (Integer) fullInfos[3];
			//原生算法性能好
			int lineNumerPlace = 4;
			int fileNameLength = fileName.length();
			int totalLength = length + 1+ lineNumerPlace;
			if(fileNameLength+1+4>totalLength){
				totalLength = fileNameLength+1+lineNumerPlace;
			}
			byte[] bytes = new byte[totalLength];

			System.arraycopy(spaceBytes,0,bytes,0,totalLength-1-lineNumerPlace);
			System.arraycopy(zeroBytes,0,bytes,totalLength-1-lineNumerPlace,lineNumerPlace);
			bytes[totalLength-1-lineNumerPlace]=':';

			if(fileNameLength+1+lineNumerPlace>totalLength){
				System.arraycopy(fileName.getBytes(),0,bytes,0,fileNameLength);
			}else{
				System.arraycopy(fileName.getBytes(),0,bytes,totalLength-1-lineNumerPlace-fileNameLength,fileNameLength);
			}

			int tempLineNum_mod = lineNum_i;
			for(int i = 0;i<lineNumerPlace;i++){
				bytes[totalLength-1-i]=(byte)(tempLineNum_mod%10+'0');
				tempLineNum_mod = tempLineNum_mod/10;
			}
			return new String(bytes);
		}
	}

	/**
	 * 
	 * @author Captain
	 * 
	 */
	private static class LogLevel extends PatternConverter {

		public LogLevel(FormattingInfo fi) {
			super(fi);
		}

		protected String convert(LoggingEvent event) {
			Level level = event.getLevel();
			String level_s = level.toString().toUpperCase();
			if(level_s.indexOf("INFO")>-1){
				return "INF";
			}else if(level_s.indexOf("DEBUG")>-1){
				return "DEB";
			}else if(level_s.indexOf("ERROR")>-1){
				return "ERR";
			}else if(level_s.indexOf("WARN")>-1){
				return "WAR";
			}else if(level_s.indexOf("FATAL")>-1){
				return "FAT";
			}else if(level_s.indexOf("OFF")>-1){
				return "OFF";
			}
			return level_s;
		}
	}

	public static Field stackTraceElement_declaringClass = null;
	public static Field stackTraceElement_methodName = null;
	public static Field stackTraceElement_fileName = null;
	public static Field stackTraceElement_lineNumber = null;
	static {
		try {
			Class<StackTraceElement> clazz = StackTraceElement.class;
			stackTraceElement_declaringClass = clazz.getDeclaredField("declaringClass");
			stackTraceElement_methodName = clazz.getDeclaredField("methodName");
			stackTraceElement_fileName = clazz.getDeclaredField("fileName");
			stackTraceElement_lineNumber = clazz.getDeclaredField("lineNumber");
			stackTraceElement_declaringClass.setAccessible(true);// 设置private为可访问
			stackTraceElement_methodName.setAccessible(true);// 设置private为可访问
			stackTraceElement_fileName.setAccessible(true);// 设置private为可访问
			stackTraceElement_lineNumber.setAccessible(true);// 设置private为可访问
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取发生日志事件的堆栈信息数组， new Object[]{declaringClass,methodName,fileName,new Integer(lineNumber)} 表示：全类名，方法名，类名，行号
	 * 
	 * @param event
	 * @return
	 */
	public static Object[] getFullInfoByREF(LoggingEvent event) {
		String fqnOfCallingClass = event.fqnOfCategoryClass;
		StackTraceElement astacktraceelement[] = new Throwable().getStackTrace();
		String declaringClass = null;
		String nextDeclaringClass = null;
		String methodName = null;
		String fileName = null;
		int lineNumber = 0;
		for(int i=astacktraceelement.length-1;i>0;i--){
			StackTraceElement ste = astacktraceelement[i];
			try {
				declaringClass = ste.getClassName();
				methodName = ste.getMethodName();
				fileName = ste.getFileName();
				lineNumber = ste.getLineNumber();
				nextDeclaringClass = astacktraceelement[i-1].getClassName();
				// 如果下一个class等于fqnOfCallingClass
				// 该动作是为了获取到发生打日志事件的类的堆栈
				if (nextDeclaringClass.equals(fqnOfCallingClass)) {
					return new Object[] { declaringClass, methodName, fileName, lineNumber };
				}
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
//		for (StackTraceElement ste : astacktraceelement) {
//			if (stackTraceElement_declaringClass != null && stackTraceElement_methodName != null
//					&& stackTraceElement_fileName != null && stackTraceElement_lineNumber != null) {
//				try {
////					declaringClass = (String) stackTraceElement_declaringClass.get(ste);
////					methodName = (String) stackTraceElement_methodName.get(ste);
////					fileName = (String) stackTraceElement_fileName.get(ste);
////					lineNumber = stackTraceElement_lineNumber.getInt(ste);
//					declaringClass = ste.getClassName();
//					methodName = ste.getMethodName();
//					fileName = ste.getFileName();
//					lineNumber = ste.getLineNumber();
//					// 如果上一个class等于fqnOfCallingClass并且上一个class不等于现在的class
//					// 该动作是为了获取到发生打日志事件的类的堆栈
//					if (preClass.equals(fqnOfCallingClass) && !declaringClass.equals(preClass)) {
//						return new Object[] { declaringClass, methodName, fileName, new Integer(lineNumber) };
//					}
//					// 将当前的类名作为下一次迭代的上一class
//					preClass = declaringClass;
//				} catch (Exception e) {
//					e.printStackTrace();
//					break;
//				}
//			}
//		}
		// 如果没有找到发生事件的类的，则返回rull
		return null;
	}

	public static void main(String[] args) throws Exception {
		// new ccc().exec();
		// c();
	}

	public static class ccc {
		public static void exec() {
			HLogger loger = LoggerManager.getLoger("1234");
			loger.info("1234567");
		}
	}

	public static void c() {
		d();
	}

	public static void d() {
		e();
	}

	public static void e() {
		// getFullInfoByREF();
	}

}
