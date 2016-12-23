/**   
* @Title: FtpTools.java 
* @Package cn.com.xcommon.commons.ftp 
* @Description: ftp工具类 
* @author zoufangfang   
* @date 2015-3-11 上午10:35:48 
* @company cn.com.xcommon
* @version V1.0   
*/ 


package cn.com.xcommon.commons.ftp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;

/** 
 * @ClassName: FtpTools 
 * @Description: ftp工具类  
 * @author zoufangfang 
 * @date 2015-3-11 上午10:35:48  
 */
public class FtpTools {
	protected HLogger logger = LoggerManager.getLoger(this.getClass().getName());
	private String hostname;//ftp地址
	private String username;//ftp用户名
	private String password;//ftp密码
	private String ftpPath;//ftp路径
	private int port;//ftp端口
	
	/**
	 * 初始化参数
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param hostname
	* @param username
	* @param password
	* @param ftpPath
	* @param port
	 */
	public FtpTools(String hostname,String username,String password,String ftpPath,int port){
		this.hostname=hostname;
		this.username=username;
		this.password=password;
		this.ftpPath=ftpPath;
		this.port = port;
	}
	
	/** 
	* 获取FTPClient对象 
	* @param hostname FTP主机服务器 
	* @param password FTP 登录密码 
	* @param hostname FTP登录用户名 
	* @param port FTP端口 默认为21 
	* @return 
	*/  
    private FTPClient getFTPClient() {  
		FTPClient ftpClient = null;  
		try {
			ftpClient = new FTPClient();  
			ftpClient.connect(hostname, port);// 连接FTP服务器  
			ftpClient.login(username, password);// 登陆FTP服务器  
			ftpClient.setControlEncoding("UTF-8"); // 中文支持  
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
			ftpClient.enterLocalPassiveMode();
			
			if(!StringUtils.isEmpty(ftpPath)){
			   ftpClient.changeWorkingDirectory(ftpPath);  
			}
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {  
				logger.error("未连接到FTP，用户名或密码错误。");  
			    ftpClient.disconnect();  
			} 
		} catch (SocketException e) {  
	        logger.error("FTP的IP地址可能错误，请正确配置。",e);  
	    } catch (IOException e) {  
	        logger.info("FTP的端口错误,请正确配置。",e);  
	    }  catch (Exception e) {  
	        logger.error("FTP的端口错误,请正确配置。",e);  
	    }  
	    return ftpClient;  
    } 
    /**
     * 读取文件文本内容
     * @param fileName
     * @return
     */
	public List<String> readFileForFTP(String fileName){
		InputStream in = null;
		List<String> result = new ArrayList<String>();
		FTPClient ftpClient=null;
		try {
			ftpClient=getFTPClient();
			in = ftpClient.retrieveFileStream(fileName);
		} catch (FileNotFoundException e) {  
		    logger.error("没有找到" + ftpPath + fileName+ "文件",e); 
		} catch (SocketException e) {  
		    logger.error("连接FTP失败.",e);
		} catch (IOException e) {  
		    logger.error("文件读取错误。",e); 
		}
		if (in != null) {  
	    	BufferedReader br = new BufferedReader(new InputStreamReader(in));  
	    	String data = null;  
	    	try {  
	    		while ((data = br.readLine()) != null) {  
	    			if(data!=null&&!data.trim().equals("")){
	    				result.add(data);
	    			}
	    		}  
	    	} catch (IOException e) {  
	    		logger.error("文件读取错误。",e);  
	    	} 
	    }else{  
	    	logger.error(" 未获取到文件 "+fileName+" 内容，请核查文件的有效性。");  
	    } 
		
		try {
			if(ftpClient!=null){
    			ftpClient.disconnect();
    		}
		} catch (Exception e2) {
			logger.error("关闭ftp连接失败",e2);  
		}
		return result;
	}
	
	
	/**
	 * FTP 文件上传
	 * @param targetFilePath  存储的目标路径
	 * @param fileName        存储文件名称
	 * @param sourceFileIns   输入的IO流
	 * @return boolean        返回true/false
	 * @author xieronghui
	 * @throws Exception 
	 */
	
	public boolean uploadFileFtp(String targetFilePath,String fileName, InputStream sourceFileIns) throws Exception {
		boolean success = false;
		OutputStream out =  null;
		FTPClient ftpClient=null;
		try {
			ftpClient=getFTPClient();
			String fileAbsPath = targetFilePath + fileName;
			String[] fileAbsPathArr = fileAbsPath.split("/");
			for (int i = 0; fileAbsPathArr != null && i < fileAbsPathArr.length - 1; i++) {
				String tmpPath = fileAbsPathArr[i];
				if (!StringUtils.isEmpty(tmpPath)) {
					boolean result = ftpClient.changeWorkingDirectory(tmpPath);
					if (!result) {
						ftpClient.makeDirectory(tmpPath);
						ftpClient.changeWorkingDirectory(tmpPath);
					}
				}
			}
			String fileNameStr = fileAbsPathArr[fileAbsPathArr.length - 1];
			ftpClient.setBufferSize(1024); //设置1M缓冲
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	        success = ftpClient.storeFile(fileNameStr, sourceFileIns);
		} catch (IOException e) {
			logger.error("上传文件异常",e);
			throw e;
		} finally {
			try {
				if (sourceFileIns != null) {
					sourceFileIns.close();
					sourceFileIns = null;
			     } 
				if(out != null){
					out.close();
					out = null;
				}
			}catch (IOException e) {
				logger.error("输入文件流close 异常",e);
				throw e;
			}
			closeConnect(ftpClient); // 关闭ftp
		}
		return success;
	}
		
   /**
    *  ftpClient 关闭
    * @param ftpClient
    * @author xieronghui
    */
   public void closeConnect(FTPClient ftpClient) {
		if(ftpClient != null){
			if (ftpClient.isConnected()) {  
                try {
                	ftpClient.logout();
                    ftpClient.disconnect();  
                } catch (IOException e) { 
                	logger.error("ftpClient logout/disconnect 出现异常 ",e);
                }  
            }  
		 }
		} 
}
