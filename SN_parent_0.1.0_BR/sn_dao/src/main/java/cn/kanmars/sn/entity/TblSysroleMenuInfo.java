/**
 * SN Generator
 */
package cn.kanmars.sn.entity;


/**
 * 系统角色菜单绑定表
 * tbl_sysrole_menu_info
 */
public class TblSysroleMenuInfo implements java.io.Serializable{
    /**
     * 角色编号
     * tbl_sysrole_menu_info.role_no
     */
    private String roleNo;

    /**
     * 菜单编号
     * tbl_sysrole_menu_info.menu_no
     */
    private String menuNo;

    /**
     * 添加时间
     * tbl_sysrole_menu_info.create_time
     */
    private String createTime;

    /**
     * 更新时间
     * tbl_sysrole_menu_info.up_time
     */
    private String upTime;


    public TblSysroleMenuInfo(){super();}
    public TblSysroleMenuInfo(String roleNo,String menuNo,String createTime,String upTime) {
        this.roleNo = roleNo;
        this.menuNo = menuNo;
        this.createTime = createTime;
        this.upTime = upTime;
    }
    /**
     * 角色编号
     * tbl_sysrole_menu_info.role_no
     */
    public String getRoleNo(){
        return roleNo;
    }

    /**
     * 角色编号
     * tbl_sysrole_menu_info.role_no
     */
    public void setRoleNo(String roleNo){
        this.roleNo=roleNo;
    }

    /**
     * 菜单编号
     * tbl_sysrole_menu_info.menu_no
     */
    public String getMenuNo(){
        return menuNo;
    }

    /**
     * 菜单编号
     * tbl_sysrole_menu_info.menu_no
     */
    public void setMenuNo(String menuNo){
        this.menuNo=menuNo;
    }

    /**
     * 添加时间
     * tbl_sysrole_menu_info.create_time
     */
    public String getCreateTime(){
        return createTime;
    }

    /**
     * 添加时间
     * tbl_sysrole_menu_info.create_time
     */
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }

    /**
     * 更新时间
     * tbl_sysrole_menu_info.up_time
     */
    public String getUpTime(){
        return upTime;
    }

    /**
     * 更新时间
     * tbl_sysrole_menu_info.up_time
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

