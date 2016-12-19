/**
 * Gome SN Generator 
 */
package cn.kanmars.sn.entity;


/**
 * 系统角色表
 * tbl_sysrole_info
 */
public class TblSysroleInfo implements java.io.Serializable{
    /**
     * 角色编号
     * tbl_sysrole_info.role_no
     */
    private String roleNo;

    /**
     * 角色名称
     * tbl_sysrole_info.role_name
     */
    private String roleName;

    /**
     * 应用名称
     * tbl_sysrole_info.application_code
     */
    private String applicationCode;

    /**
     * 创建人编号
     * tbl_sysrole_info.create_opr_no
     */
    private String createOprNo;

    /**
     * 创建人名称
     * tbl_sysrole_info.create_opr_name
     */
    private String createOprName;

    /**
     * 角色附属信息
     * tbl_sysrole_info.role_oth_msg
     */
    private String roleOthMsg;

    /**
     * 添加时间
     * tbl_sysrole_info.create_time
     */
    private String createTime;

    /**
     * 更新时间
     * tbl_sysrole_info.up_time
     */
    private String upTime;


    public TblSysroleInfo(){super();}
    public TblSysroleInfo(String roleNo,String roleName,String applicationCode,String createOprNo,String createOprName,String roleOthMsg,String createTime,String upTime) {
        this.roleNo = roleNo;
        this.roleName = roleName;
        this.applicationCode = applicationCode;
        this.createOprNo = createOprNo;
        this.createOprName = createOprName;
        this.roleOthMsg = roleOthMsg;
        this.createTime = createTime;
        this.upTime = upTime;
    }
    /**
     * 角色编号
     * tbl_sysrole_info.role_no
     */
    public String getRoleNo(){
        return roleNo;
    }

    /**
     * 角色编号
     * tbl_sysrole_info.role_no
     */
    public void setRoleNo(String roleNo){
        this.roleNo=roleNo;
    }

    /**
     * 角色名称
     * tbl_sysrole_info.role_name
     */
    public String getRoleName(){
        return roleName;
    }

    /**
     * 角色名称
     * tbl_sysrole_info.role_name
     */
    public void setRoleName(String roleName){
        this.roleName=roleName;
    }

    /**
     * 应用名称
     * tbl_sysrole_info.application_code
     */
    public String getApplicationCode(){
        return applicationCode;
    }

    /**
     * 应用名称
     * tbl_sysrole_info.application_code
     */
    public void setApplicationCode(String applicationCode){
        this.applicationCode=applicationCode;
    }

    /**
     * 创建人编号
     * tbl_sysrole_info.create_opr_no
     */
    public String getCreateOprNo(){
        return createOprNo;
    }

    /**
     * 创建人编号
     * tbl_sysrole_info.create_opr_no
     */
    public void setCreateOprNo(String createOprNo){
        this.createOprNo=createOprNo;
    }

    /**
     * 创建人名称
     * tbl_sysrole_info.create_opr_name
     */
    public String getCreateOprName(){
        return createOprName;
    }

    /**
     * 创建人名称
     * tbl_sysrole_info.create_opr_name
     */
    public void setCreateOprName(String createOprName){
        this.createOprName=createOprName;
    }

    /**
     * 角色附属信息
     * tbl_sysrole_info.role_oth_msg
     */
    public String getRoleOthMsg(){
        return roleOthMsg;
    }

    /**
     * 角色附属信息
     * tbl_sysrole_info.role_oth_msg
     */
    public void setRoleOthMsg(String roleOthMsg){
        this.roleOthMsg=roleOthMsg;
    }

    /**
     * 添加时间
     * tbl_sysrole_info.create_time
     */
    public String getCreateTime(){
        return createTime;
    }

    /**
     * 添加时间
     * tbl_sysrole_info.create_time
     */
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }

    /**
     * 更新时间
     * tbl_sysrole_info.up_time
     */
    public String getUpTime(){
        return upTime;
    }

    /**
     * 更新时间
     * tbl_sysrole_info.up_time
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

