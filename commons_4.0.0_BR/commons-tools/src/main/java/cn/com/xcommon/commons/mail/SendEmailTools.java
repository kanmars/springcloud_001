package cn.com.xcommon.commons.mail;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class SendEmailTools {
	
	/**
	 * 发送普通文字邮件
	* @Title: sendSimpleEmail 
	* @param receives 接收人，多个接收人用','隔开
	* @param title 邮件标题
	* @param content 内容
	* @return void 返回类型 
	* @throws
	 */
	public static void sendSimpleEmail(String host, String userName, String password, String sendFrom, String sendTo, String title, String content){
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    	mailSender.setHost(host);
    	mailSender.setUsername(userName);
    	mailSender.setPassword(password);
    	Properties props = new Properties();  
        props.put("mail.smtp.auth", "true");  
            
        //通过文件获取信息    
        mailSender.setJavaMailProperties(props);
    	
    	SimpleMailMessage smm = new SimpleMailMessage();
    	// 设定邮件参数
    	smm.setFrom(sendFrom);
    	smm.setTo(sendTo.split(","));
    	smm.setSubject(title);
    	smm.setText(content);
    	// 发送邮件
    	mailSender.send(smm);
    	
    	System.out.println("send success !!!");
    	
	}
	
	/**
	 * 发送HTML格式邮件
	* @Title: sendHtmlEmail 
	* @param host
	* @param userName
	* @param password
	* @param sendFrom
	* @param sendTo
	* @param title 邮件标题
	* @param content 内容
	* @return void 返回类型 
	* @throws
	 */
	public static void sendHtmlEmail(String host, String userName, String password, String sendFrom, String sendTo, String title, String content){
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    	mailSender.setHost(host);
    	mailSender.setUsername(userName);
    	mailSender.setPassword(password);
    	Properties props = new Properties();  
        props.put("mail.smtp.auth", "true");  
            
        //通过文件获取信息    
        mailSender.setJavaMailProperties(props);
    	
    	Session mailSession = Session.getDefaultInstance(props);
    	MimeMessage testMessage = new MimeMessage(mailSession);
    	try {
    		
    		InternetAddress from  = new InternetAddress(sendFrom);
			testMessage.setFrom(from);
	    	testMessage.addRecipients(javax.mail.Message.RecipientType.TO, sendTo);
	    	testMessage.setSentDate(new java.util.Date());
	    	testMessage.setSubject(MimeUtility.encodeText(title,"UTF-8","B"));
	    	testMessage.setContent(content, "text/html;charset=UTF-8");
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	mailSender.send(testMessage);
    	
    	System.out.println("send success !!!");
    	
	}
	
	public static void main(String[] args) {
		
		StringBuffer theMessage = new StringBuffer();
		theMessage.append("<table>");
		theMessage.append("<tr><td>序号</td><td>产品名称</td></tr>");
		theMessage.append("<tr><td>1</td><td>某商品100元</td></tr>");
		theMessage.append("</table>");
		sendHtmlEmail("mail.aliyun.com", "帐号", "密码", "来源地址sendfrom","去向地址逗号分割","[测试邮件]",theMessage.toString());
		
//		sendSimpleEmail("mail.XXX.com", "DS\\ganguiwen", "1234567", "ganguiwen@XXX.com", "3XXXXXXXXX5@qq.com", "标题", "内容");
		
	}
	
}
