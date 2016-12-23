package cn.com.xcommon.frame.exception;


public class SnCommonException extends Exception {

	private static final long serialVersionUID = -2445488256264617507L;

	/**
	 * 异常码
	 */
	private String code;

	/**
	 * 自定义异常信息
	 */
	private String message;
	
	public SnCommonException(){
		super();
	}
	
	public SnCommonException(String message){
		super(message);
		this.code = "0001";
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
