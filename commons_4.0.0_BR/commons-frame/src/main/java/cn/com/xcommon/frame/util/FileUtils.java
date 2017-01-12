package cn.com.xcommon.frame.util;

import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;

/**
 * @description 文件
 * @author wangyi-ds
 */
public class FileUtils {
	private static HLogger logger= LoggerManager.getLoger("FileUtils");
	static FileUtils fu = new FileUtils();

	public static FileUtils getInstatance() {
		if (fu == null)
			fu = new FileUtils();
		return fu;
	}

	public String readFile(String path) {
		File txtFile = new File(path);
		if (!txtFile.exists()) {
			return "";
		}
		InputStream is = null;

		try {
			is = new BufferedInputStream(new FileInputStream(txtFile));
		} catch (FileNotFoundException e1) {
			logger.error(e1.getMessage());
		}
		byte b[] = new byte[1024];
		int size = 0;
		StringBuffer message = new StringBuffer();
		try {
			while ((size = is.read(b)) != -1) {
				message.append(new String(b, 0, size, "gb2312"));
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return message.toString();

	}

	/**
	 * 某一个目录下文件的个数
	 * 
	 * @param path
	 * @return
	 */
	public int getFileSize(String path) {
		int size = 0;
		File file = new File(path);
		if (!file.exists()) {
			return size;
		}
		if (!file.isDirectory()) {
			return size;
		}
		String[] tempList = file.list();
		return tempList.length;
	}

	/**
	 * 创建文件,如果这个文件的目录不存在，自动创建
	 * 
	 * @param message
	 * @param path
	 */
	public void createFile(String message, String path) {
		if (path.endsWith("rar")) {
			path = path.substring(0, path.indexOf(".") + 1) + "xml";
		}
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
		createDir(path.substring(0, path.lastIndexOf("/")));
		try {
			File textFile = new File(path);
			OutputStreamWriter out = null;
			// 输出流
			out = new OutputStreamWriter(new FileOutputStream(textFile),"UTF-8");
			out.write(message);
			out.close();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	/**
	 * 创建文件,如果这个文件的目录不存在，自动创建
	 * 
	 * @param byteMessage 字节形式的消息
	 * @param path
	 */
	public void createFile(byte[] byteMessage, String path) {
		if (path.endsWith("rar")) {
			path = path.substring(0, path.indexOf(".") + 1) + "xml";
		}
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
		createDir(path.substring(0, path.lastIndexOf("/")));
		try {
			File textFile = new File(path);
			FileOutputStream fos = new FileOutputStream(textFile);
			fos.write(byteMessage);
			fos.close();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	/**
	 * 创建文件,如果这个文件的目录不存在，自动创建
	 * 
	 * @param inFile
	 * @param outFilePath
	 */
	public void fileUpload(CommonsMultipartFile inFile, String outFilePath) {
		FileOutputStream bos = null;
		InputStream inStream = null;
		if (outFilePath.endsWith("rar")) {
			outFilePath = outFilePath.substring(0, outFilePath.indexOf(".") + 1) + "xml";
		}
		File file = new File(outFilePath);
		if (file.exists()) {
			file.delete();
		}
		createDir(outFilePath.substring(0, outFilePath.lastIndexOf(File.separator)));
		try {
            bos = new FileOutputStream(outFilePath);
            inStream = inFile.getInputStream();
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while((bytesRead = inStream.read(buffer,0,8192))!=-1){
				bos.write(buffer, 0, bytesRead);
			}
            bos.flush();
        }catch(FileNotFoundException e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }catch(IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally{
			try{
				bos.close();
			}catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			try {
				inStream.close();
			}catch(IOException e){
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
	}
	/**
	 * 更新文件,如果这个文件的目录不存在，自动创建
	 * 
	 * @param byteMessage
	 * @param outFilePath
	 */
	public void fileUpload(byte[] byteMessage, String outFilePath) {
		FileOutputStream bos = null;
		InputStream inStream = null;
		if (outFilePath.endsWith("rar")) {
			outFilePath = outFilePath.substring(0, outFilePath.indexOf(".") + 1) + "xml";
		}
		File file = new File(outFilePath);
		if (file.exists()) {
			file.delete();
		}
		createDir(outFilePath.substring(0, outFilePath.lastIndexOf(File.separator)));
		try {
            bos = new FileOutputStream(outFilePath);
			bos.write(byteMessage);
            bos.flush();
        }catch(FileNotFoundException e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }catch(IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally{
			try{
				bos.close();
			}catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * 创建目录
	 * 
	 * @param destDirName
	 * @return
	 */
	public boolean createDir(String destDirName) {
		File dir = new File(destDirName);

		if (dir.exists()) {
			// logger.info("创建目录" + destDirName + "失败，目标目录已存在！");
			return false;
		}
		if (!destDirName.endsWith(File.separator))
			destDirName = destDirName + File.separator;
		// 创建单个目录
		if (dir.mkdirs()) {
			// logger.info("创建目录" + destDirName + "成功！");
			return true;
		} else {
			// logger.info("创建目录" + destDirName + "失败！");
			return false;
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 * @return
	 */
	public void deleteFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		file.delete();
	}
	
	/**
	 * 文件分隔符
	 * 
	 * @return
	 */
	public String getFileSeparator() {
		String fileSeparator = System.getProperties().getProperty("file.separator");
		return fileSeparator;
	}

	/**
	 * 将inputStream转换为bytes
	 * @param inFile
	 * @return
	 */
	public static byte[] transInputStreamToBytes(CommonsMultipartFile inFile) {
		ByteArrayOutputStream bao = null;
		InputStream inStream = null;
		byte[] result = null;
		try {
			bao = new ByteArrayOutputStream();
            inStream = inFile.getInputStream();
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while((bytesRead = inStream.read(buffer,0,8192))!=-1){
            	bao.write(buffer, 0, bytesRead);
			}
            bao.flush();
            result = bao.toByteArray();
        }catch(FileNotFoundException e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }catch(IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally{
			try{
				bao.close();
			}catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			try {
				inStream.close();
			}catch(IOException e){
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		return result;
	}
	public static void main(String[] args) {
		String name = "ceshi.rar";
		logger.info(name.substring(name.indexOf(".") + 1));
		String message = "nihao你好！";
		FileUtils file = new FileUtils();
		// file.deletePhoto("D:/diary/12983/mm/133.txt");
		// file.createDir("e:/data/log/cc/dd/");
		// file.createFile("cccccccccc", "e:/data/log/cc/nn/1328526101599.txt");
		// 创建目录
		 String dirName = "e:/test/test0/aa.png";
		 String yyyyMMStr = DateUtils.getCurrMonth();
			String yyyyMMddStr = DateUtils.getCurrDate();
			String yyyyMMddHHmmssStr = DateUtils.getCurrDateTime();
			String imageFileServierPath = "e:"+File.separator+yyyyMMStr+File.separator+yyyyMMddStr+File.separator;

		 file.createDir(imageFileServierPath);
		// FileUtils.delFolder(dirName);
		// 创建文件
		// String fileName = dirName + "/test2/testFile.txt";
		// FileUtils.CreateFile(fileName);
		// 创建临时文件
		// String prefix = "temp";
		// String suffix = ".txt";
		// for (int i = 0; i < 10; i++) {
		// logger.info("创建了临时文件:"
		// + FileUtils.createTempFile(prefix, suffix, dirName));
		// }

	}

}
