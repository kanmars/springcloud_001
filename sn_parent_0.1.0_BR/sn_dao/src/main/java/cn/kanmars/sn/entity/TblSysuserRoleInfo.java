/**
 * Gome SN Generator 
 */
package cn.kanmars.sn.entity;


/**
 * 系统用户角色绑定表
 * tbl_sysuser_role_info
 */
public class TblSysuserRoleInfo implements java.io.Serializable{
    /**
     * 用户编号
     * tbl_sysuser_role_info.user_no
     */
    private String userNo;

    /**
     * 角色编号
     * tbl_sysuser_role_info.role_no
     */
    private String roleNo;

    /**
     * 添加时间
     * tbl_sysuser_role_info.create_time
     */
    private String createTime;

    /**
     * 更新时间
     * tbl_sysuser_role_info.up_time
     */
    private String upTime;


    public TblSysuserRoleInfo(){super();}
    public TblSysuserRoleInfo(String userNo,String roleNo,String createTime,String upTime) {
        this.userNo = userNo;
        this.roleNo = roleNo;
        this.createTime = createTime;
        this.upTime = upTime;
    }
    /**
     * 用户编号
     * tbl_sysuser_role_info.user_no
     */
    public String getUserNo(){
        return userNo;
    }

    /**
     * 用户编号
     * tbl_sysuser_role_info.user_no
     */
    public void setUserNo(String userNo){
        this.userNo=userNo;
    }

    /**
     * 角色编号
     * tbl_sysuser_role_info.role_no
     */
    public String getRoleNo(){
        return roleNo;
    }

    /**
     * 角色编号
     * tbl_sysuser_role_info.role_no
     */
    public void setRoleNo(String roleNo){
        this.roleNo=roleNo;
    }

    /**
     * 添加时间
     * tbl_sysuser_role_info.create_time
     */
    public String getCreateTime(){
        return createTime;
    }

    /**
     * 添加时间
     * tbl_sysuser_role_info.create_time
     */
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }

    /**
     * 更新时间
     * tbl_sysuser_role_info.up_time
     */
    public String getUpTime(){
        return upTime;
    }

    /**
     * 更新时间
     * tbl_sysuser_role_info.up_time
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

