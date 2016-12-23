/**   
* @Title: SftpTools.java 
* @Package cn.com.sn.commons.sftp 
* @Description: sftp工具类 
* @author zoufangfang   
* @date 2015-3-11 上午11:04:01 
* @company cn.com.sn
* @version V1.0   
*/ 


package cn.com.sn.commons.sftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

/** 
 * @ClassName: SftpTools 
 * @Description: sftp工具类 
 * @author zoufangfang 
 * @date 2015-3-11 上午11:04:01
 */
public class SFTPTools {
	 public static  HLogger logger = LoggerManager.getLoger("SFTPTools");
	
	/**
	 * 链接sftp服务器
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @return
	 */
	public  static ChannelSftp connect(String hostname, int port, String username,String password) {
		Session sshSession = null;
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			sshSession = jsch.getSession(username, hostname, port);
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
		} catch (Exception e) {
			logger.error("连接sftp服务器失败",e);
		}
		return sftp;
	}
	
	
	/**
	 * 关闭sftp服务器
	 * @param sshSession
	 */
	public  static void closeChannelSftp(ChannelSftp channelSftp){
		Session sshSession=null;
		try {
			if (channelSftp != null) {
				sshSession=channelSftp.getSession();
				if (channelSftp.isConnected()) {
					channelSftp.disconnect();
					channelSftp=null;
				} else if (channelSftp.isClosed()) {
					logger.info("sftp is closed already");
				}
			}
			if (sshSession != null) {
				if (sshSession.isConnected()) {
					sshSession.disconnect();
					sshSession=null;
				} else {
					logger.info("sshSession is disconnected...");
				}
			}
	    } catch (JSchException e) {
	    	logger.error("关闭sftp服务器失败",e);
		}
	}
	

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录，如果目录不存在，则自动创建
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 */
	public  static boolean upload(String directory, String uploadFile, ChannelSftp sftp) {
		boolean result = false;
		try {
			/**进入文件夹 并上传文件，并修改结果为true*/
			sftp.cd(directory);
			File file = new File(uploadFile);
			FileInputStream inputStream = new FileInputStream(file);
			sftp.put(inputStream, file.getName());
			inputStream.close();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 */
	public  static boolean download(String directory, String downloadFile,
			String saveFile, ChannelSftp sftp) {
		boolean result = false;
		FileOutputStream fileOutputStream=null;
		File file =null;
		try {
			 file = new File(saveFile);
			 fileOutputStream=new FileOutputStream(file);
			sftp.cd(directory);
			sftp.get(downloadFile,fileOutputStream );
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
           if(fileOutputStream!=null){
        	   try {
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
           }
		}
		return result;
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @param sftp
	 */
	public static void delete(String directory, String deleteFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	public static List listFiles(String directory, ChannelSftp sftp)
			throws SftpException {
		/**
		 * 返回数据类型为 Vector
		 */
		return sftp.ls(directory);
	}
	
	/**
	 * 递归创建文件夹
	 * @param directory
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	public static boolean mkdirs(String directory, ChannelSftp sftp){
		boolean result = false;
		/** 判断文件是否已经存在*/
		try {
			SftpATTRS sftpAttrs = sftp.lstat(directory);
			/**  判断是否是文件类型 */
			if(sftpAttrs.isDir()){
				return true;
			}
		} catch (SftpException e1) {
			//e1.printStackTrace();
			//不存在的情况
			//查看父级是否存在
			//(确认父级存在)父级不存在->递归调用父级
			try {
				directory = directory.replaceAll("\\\\", "/");
				SftpATTRS sftpAttrs = sftp.lstat(directory.substring(0,directory.lastIndexOf("/")));
				/**  判断是否是文件类型 */
				if(!sftpAttrs.isDir()){
					//不操作
					return false;
				}
			}catch(Exception e){
				//e.printStackTrace();
				/**递归生成父级*/
				boolean res_f = mkdirs(directory.substring(0,directory.lastIndexOf("/")), sftp);
				if(!res_f){
					//父级没有创建成功，
					result = false;
					return result;
				}
			}
			//父级存在->直接创建
			try{
				sftp.mkdir(directory);
				result = true;
			}catch(Exception e){
				//e.printStackTrace();
				result = false;
			}
		}
		return result;
	}

	/**
	 * 
	* @Title: readFileForSFTP 
	* @Description: 读取文件内容 
	* @param filePath 文件路径
	* @param fileName  文件名字
	* @param sftp 
	* @return  
	* @return List<String>    返回类型 
	* @throws
	 */
	public static List<String> readFileForSFTP(String filePath, String fileName,ChannelSftp sftp){
		InputStream in = null;
		List<String> result = new ArrayList<String>();
		try {
			if(sftp!=null){
				if(filePath!=null&&!"".equals(filePath)){
				 sftp.cd(filePath);
				}
				in=sftp.get(fileName);
				if (in != null) {     
				    LineNumberReader lineInputStream = new LineNumberReader(new InputStreamReader(in));
			    	String data = null;  
			    	try {  
			    		while ((data = lineInputStream.readLine()) != null) {  
			    			if(data!=null&&!data.trim().equals("")){
			    				result.add(data);
			    			}
			    		}  
			    		in.close(); 
			    	} catch (IOException e) {  
			    			logger.error("文件读取错误。",e);
			    	}
			    }else{
			    	logger.error(" 未获取到文件 "+fileName+" 内容，请核查文件的有效性。");
			    }  
			}
		} catch (Exception e) {
			logger.error(" 未获取到文件 "+fileName+" 内容，请核查文件的有效性。",e);
		}
		return result;
	}
	
	public static void main(String[] args) throws JSchException, IOException {
		String host = "10.126.53.194";
		int port = 22;
		String username = "nginx";
		String password = "nginx";
		String directory = "/app/nginx";
		String uploadFile = "E:\\m.log";
		String downloadFile = "nginx.conf.bak";
		String saveFile = "E:\\nginx.conf.bak";
		String deleteFile = "m.log";
		SFTPTools tools=new SFTPTools();
		ChannelSftp sftp = tools.connect(host, port, username, password);
//		boolean r = tools.mkdirs("/app/nginx/123/589/756", sftp);
//		System.out.println("创建结果["+r+"]");
//		tools.upload(directory, uploadFile, sftp);
//		tools.download(directory, downloadFile, saveFile, sftp);
//		tools.delete(directory, deleteFile, sftp);
//		tools.mkdirs("/app/nginx/20150105", sftp);
		List<String> lineList = tools.readFileForSFTP(directory,"instId_capital_detail_20150129.txt",sftp);
		for(String s:lineList){
			System.out.println(s);
		}
		tools.closeChannelSftp(sftp);
	}
	
	
}
