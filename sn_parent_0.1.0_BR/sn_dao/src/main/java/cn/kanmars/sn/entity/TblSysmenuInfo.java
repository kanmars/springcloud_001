/**
 * Gome SN Generator 
 */
package cn.kanmars.sn.entity;


/**
 * 系统菜单表
 * tbl_sysmenu_info
 */
public class TblSysmenuInfo implements java.io.Serializable{
    /**
     * 菜单编号
     * tbl_sysmenu_info.menu_no
     */
    private String menuNo;

    /**
     * 菜单名称
     * tbl_sysmenu_info.menu_name
     */
    private String menuName;

    /**
     * 菜单类型
     * tbl_sysmenu_info.menu_type
     */
    private String menuType;

    /**
     * 父菜单编码
     * tbl_sysmenu_info.parent_menu_code
     */
    private String parentMenuCode;

    /**
     * 菜单url
     * tbl_sysmenu_info.menu_url
     */
    private String menuUrl;

    /**
     * 菜单排序
     * tbl_sysmenu_info.menu_idx
     */
    private Integer menuIdx;

    /**
     * 应用名称
     * tbl_sysmenu_info.application_code
     */
    private String applicationCode;

    /**
     * 添加时间
     * tbl_sysmenu_info.create_time
     */
    private String createTime;

    /**
     * 更新时间
     * tbl_sysmenu_info.up_time
     */
    private String upTime;


    public TblSysmenuInfo(){super();}
    public TblSysmenuInfo(String menuNo,String menuName,String menuType,String parentMenuCode,String menuUrl,Integer menuIdx,String applicationCode,String createTime,String upTime) {
        this.menuNo = menuNo;
        this.menuName = menuName;
        this.menuType = menuType;
        this.parentMenuCode = parentMenuCode;
        this.menuUrl = menuUrl;
        this.menuIdx = menuIdx;
        this.applicationCode = applicationCode;
        this.createTime = createTime;
        this.upTime = upTime;
    }
    /**
     * 菜单编号
     * tbl_sysmenu_info.menu_no
     */
    public String getMenuNo(){
        return menuNo;
    }

    /**
     * 菜单编号
     * tbl_sysmenu_info.menu_no
     */
    public void setMenuNo(String menuNo){
        this.menuNo=menuNo;
    }

    /**
     * 菜单名称
     * tbl_sysmenu_info.menu_name
     */
    public String getMenuName(){
        return menuName;
    }

    /**
     * 菜单名称
     * tbl_sysmenu_info.menu_name
     */
    public void setMenuName(String menuName){
        this.menuName=menuName;
    }

    /**
     * 菜单类型
     * tbl_sysmenu_info.menu_type
     */
    public String getMenuType(){
        return menuType;
    }

    /**
     * 菜单类型
     * tbl_sysmenu_info.menu_type
     */
    public void setMenuType(String menuType){
        this.menuType=menuType;
    }

    /**
     * 父菜单编码
     * tbl_sysmenu_info.parent_menu_code
     */
    public String getParentMenuCode(){
        return parentMenuCode;
    }

    /**
     * 父菜单编码
     * tbl_sysmenu_info.parent_menu_code
     */
    public void setParentMenuCode(String parentMenuCode){
        this.parentMenuCode=parentMenuCode;
    }

    /**
     * 菜单url
     * tbl_sysmenu_info.menu_url
     */
    public String getMenuUrl(){
        return menuUrl;
    }

    /**
     * 菜单url
     * tbl_sysmenu_info.menu_url
     */
    public void setMenuUrl(String menuUrl){
        this.menuUrl=menuUrl;
    }

    /**
     * 菜单排序
     * tbl_sysmenu_info.menu_idx
     */
    public Integer getMenuIdx(){
        return menuIdx;
    }

    /**
     * 菜单排序
     * tbl_sysmenu_info.menu_idx
     */
    public void setMenuIdx(Integer menuIdx){
        this.menuIdx=menuIdx;
    }

    /**
     * 应用名称
     * tbl_sysmenu_info.application_code
     */
    public String getApplicationCode(){
        return applicationCode;
    }

    /**
     * 应用名称
     * tbl_sysmenu_info.application_code
     */
    public void setApplicationCode(String applicationCode){
        this.applicationCode=applicationCode;
    }

    /**
     * 添加时间
     * tbl_sysmenu_info.create_time
     */
    public String getCreateTime(){
        return createTime;
    }

    /**
     * 添加时间
     * tbl_sysmenu_info.create_time
     */
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }

    /**
     * 更新时间
     * tbl_sysmenu_info.up_time
     */
    public String getUpTime(){
        return upTime;
    }

    /**
     * 更新时间
     * tbl_sysmenu_info.up_time
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

