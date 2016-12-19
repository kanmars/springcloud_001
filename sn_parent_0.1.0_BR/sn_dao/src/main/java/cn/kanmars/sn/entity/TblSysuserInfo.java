/**
 * Gome SN Generator 
 */
package cn.kanmars.sn.entity;


/**
 * 系统用户表
 * tbl_sysuser_info
 */
public class TblSysuserInfo implements java.io.Serializable{
    /**
     * 用户编号
     * tbl_sysuser_info.user_no
     */
    private String userNo;

    /**
     * 用户姓名
     * tbl_sysuser_info.user_name
     */
    private String userName;

    /**
     * 用户昵称
     * tbl_sysuser_info.user_nickname
     */
    private String userNickname;

    /**
     * 用户登录帐号
     * tbl_sysuser_info.login_name
     */
    private String loginName;

    /**
     * 用户密码
     * tbl_sysuser_info.password
     */
    private String password;

    /**
     * 用户状态
     * tbl_sysuser_info.user_status
     */
    private String userStatus;

    /**
     * 应用名称
     * tbl_sysuser_info.application_code
     */
    private String applicationCode;

    /**
     * 最后登录ip
     * tbl_sysuser_info.last_login_ip
     */
    private String lastLoginIp;

    /**
     * 最后登录时间
     * tbl_sysuser_info.last_login_tm
     */
    private String lastLoginTm;

    /**
     * 添加时间
     * tbl_sysuser_info.create_time
     */
    private String createTime;

    /**
     * 更新时间
     * tbl_sysuser_info.up_time
     */
    private String upTime;


    public TblSysuserInfo(){super();}
    public TblSysuserInfo(String userNo,String userName,String userNickname,String loginName,String password,String userStatus,String applicationCode,String lastLoginIp,String lastLoginTm,String createTime,String upTime) {
        this.userNo = userNo;
        this.userName = userName;
        this.userNickname = userNickname;
        this.loginName = loginName;
        this.password = password;
        this.userStatus = userStatus;
        this.applicationCode = applicationCode;
        this.lastLoginIp = lastLoginIp;
        this.lastLoginTm = lastLoginTm;
        this.createTime = createTime;
        this.upTime = upTime;
    }
    /**
     * 用户编号
     * tbl_sysuser_info.user_no
     */
    public String getUserNo(){
        return userNo;
    }

    /**
     * 用户编号
     * tbl_sysuser_info.user_no
     */
    public void setUserNo(String userNo){
        this.userNo=userNo;
    }

    /**
     * 用户姓名
     * tbl_sysuser_info.user_name
     */
    public String getUserName(){
        return userName;
    }

    /**
     * 用户姓名
     * tbl_sysuser_info.user_name
     */
    public void setUserName(String userName){
        this.userName=userName;
    }

    /**
     * 用户昵称
     * tbl_sysuser_info.user_nickname
     */
    public String getUserNickname(){
        return userNickname;
    }

    /**
     * 用户昵称
     * tbl_sysuser_info.user_nickname
     */
    public void setUserNickname(String userNickname){
        this.userNickname=userNickname;
    }

    /**
     * 用户登录帐号
     * tbl_sysuser_info.login_name
     */
    public String getLoginName(){
        return loginName;
    }

    /**
     * 用户登录帐号
     * tbl_sysuser_info.login_name
     */
    public void setLoginName(String loginName){
        this.loginName=loginName;
    }

    /**
     * 用户密码
     * tbl_sysuser_info.password
     */
    public String getPassword(){
        return password;
    }

    /**
     * 用户密码
     * tbl_sysuser_info.password
     */
    public void setPassword(String password){
        this.password=password;
    }

    /**
     * 用户状态
     * tbl_sysuser_info.user_status
     */
    public String getUserStatus(){
        return userStatus;
    }

    /**
     * 用户状态
     * tbl_sysuser_info.user_status
     */
    public void setUserStatus(String userStatus){
        this.userStatus=userStatus;
    }

    /**
     * 应用名称
     * tbl_sysuser_info.application_code
     */
    public String getApplicationCode(){
        return applicationCode;
    }

    /**
     * 应用名称
     * tbl_sysuser_info.application_code
     */
    public void setApplicationCode(String applicationCode){
        this.applicationCode=applicationCode;
    }

    /**
     * 最后登录ip
     * tbl_sysuser_info.last_login_ip
     */
    public String getLastLoginIp(){
        return lastLoginIp;
    }

    /**
     * 最后登录ip
     * tbl_sysuser_info.last_login_ip
     */
    public void setLastLoginIp(String lastLoginIp){
        this.lastLoginIp=lastLoginIp;
    }

    /**
     * 最后登录时间
     * tbl_sysuser_info.last_login_tm
     */
    public String getLastLoginTm(){
        return lastLoginTm;
    }

    /**
     * 最后登录时间
     * tbl_sysuser_info.last_login_tm
     */
    public void setLastLoginTm(String lastLoginTm){
        this.lastLoginTm=lastLoginTm;
    }

    /**
     * 添加时间
     * tbl_sysuser_info.create_time
     */
    public String getCreateTime(){
        return createTime;
    }

    /**
     * 添加时间
     * tbl_sysuser_info.create_time
     */
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }

    /**
     * 更新时间
     * tbl_sysuser_info.up_time
     */
    public String getUpTime(){
        return upTime;
    }

    /**
     * 更新时间
     * tbl_sysuser_info.up_time
     */
    public void setUpTime(String upTime){
        this.upTime=upTime;
    }

    /**
     * 自定义列，分页查询用
     */
    private Integer limitStart;
    public Integer getLimitStart(){
        return limitStart;
    }
    public void setLimitStart(Integer limitStart){
        this.limitStart = limitStart;
    }
    private Integer limitSize;
    public Integer getLimitSize(){
        return limitSize;
    }
    public void setLimitSize(Integer limitSize){
        this.limitSize = limitSize;
    }
}

