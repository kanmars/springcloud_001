package cn.com.sn.frame.logger;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Layout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

public class HRollingFileAppender extends RollingFileAppender {
	
	private volatile String datetime = Utils.getCurrDate();
	
	private boolean append = true;
	private boolean bufferedIO = true;
	private int bufferSize = 10240;
	
	@Override
	public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize)throws IOException {
		String appendName = this.getName();
		if(appendName.equals("TXN_LOG_FILE")){
		}
		if(appendName.equals("ERROR_LOG_FILE") && fileName.equals("default")){
			fileName = getLogDir()+File.separator+LoggerConfiger.getConfig(LoggerConfiger.ERROR_LOG_FILE_NAME);
		}
		if(appendName.equals("ALL_LOG_FILE") && fileName.equals("default")){
			fileName = getLogDir()+File.separator+LoggerConfiger.getConfig(LoggerConfiger.ALL_LOG_FILE_NAME);
		}
		this.append = append;
		this.bufferedIO = bufferedIO;
		
		super.setFile(fileName, append, bufferedIO, bufferSize);
	}
	
	@Override
	public boolean equals(Object obj) {
		String appendName_this = this.getName();
		String appendName_obj = ((WriterAppender)obj).getName();
		return appendName_this.equals(appendName_obj);
	}
	
	/**
	 * 覆盖父，父父类WriterAppender的subAppend方法
	 */
	protected void subAppend(LoggingEvent event) {
		//如果发生日切
		if(!datetime.equals(Utils.getCurrDate())){
			//关闭当前的qw
			try {
				this.qw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//重新设置qw
			try {
				this.setFile("default", append, bufferedIO, bufferSize);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		this.qw.write(this.layout.format(event));
		if(layout.ignoresThrowable()) {
			String[] s = event.getThrowableStrRep();
			if (s != null) {
				int len = s.length;
				for(int i = 0; i < len; i++) {
					this.qw.write(s[i]);
					this.qw.write(Layout.LINE_SEP);
				}
			}
		}

		if(shouldFlush(event)) {
			this.qw.flush();
		}
	}
	
	public static String LOG_DIR = System.getProperty("user.home") + File.separator + LoggerConfiger.getConfig(LoggerConfiger.LOG_DIR); // 相对于HOME目录
	
	public static String getLogDir() {
		String logDir = LOG_DIR;
		if (LoggerConfiger.getConfig(LoggerConfiger.LOG_DIR_DATE_FLAG) != null
				&& LoggerConfiger.getConfig(LoggerConfiger.LOG_DIR_DATE_FLAG).trim()
						.equals("true")) {
			logDir = logDir + File.separator + LoggerConfiger.getConfig(LoggerConfiger.LOG_ENV) + File.separator + Utils.getCurrDate();
		}
		return logDir;
	}
}
