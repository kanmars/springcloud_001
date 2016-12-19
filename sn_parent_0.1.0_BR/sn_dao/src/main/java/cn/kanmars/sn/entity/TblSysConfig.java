/**
 * Gome SN Generator 
 */
package cn.kanmars.sn.entity;


/**
 * 系统配置表
 * tbl_sys_config
 */
public class TblSysConfig implements java.io.Serializable{
    /**
     * 主键id
     * tbl_sys_config.id
     */
    private Integer id;

    /**
     * 版本号
     * tbl_sys_config.version
     */
    private String version;

    /**
     * 系统名称
     * tbl_sys_config.system_name
     */
    private String systemName;

    /**
     * 图片路径
     * tbl_sys_config.img_url
     */
    private String imgUrl;

    /**
     * 状态
     * tbl_sys_config.status
     */
    private String status;

    /**
     * 添加时间
     * tbl_sys_config.create_time
     */
    private String createTime;

    /**
     * 创建人名称
     * tbl_sys_config.create_name
     */
    private String createName;

    /**
     * 更新时间
     * tbl_sys_config.update_time
     */
    private String updateTime;


    public TblSysConfig(){super();}
    public TblSysConfig(Integer id,String version,String systemName,String imgUrl,String status,String createTime,String createName,String updateTime) {
        this.id = id;
        this.version = version;
        this.systemName = systemName;
        this.imgUrl = imgUrl;
        this.status = status;
        this.createTime = createTime;
        this.createName = createName;
        this.updateTime = updateTime;
    }
    /**
     * 主键id
     * tbl_sys_config.id
     */
    public Integer getId(){
        return id;
    }

    /**
     * 主键id
     * tbl_sys_config.id
     */
    public void setId(Integer id){
        this.id=id;
    }

    /**
     * 版本号
     * tbl_sys_config.version
     */
    public String getVersion(){
        return version;
    }

    /**
     * 版本号
     * tbl_sys_config.version
     */
    public void setVersion(String version){
        this.version=version;
    }

    /**
     * 系统名称
     * tbl_sys_config.system_name
     */
    public String getSystemName(){
        return systemName;
    }

    /**
     * 系统名称
     * tbl_sys_config.system_name
     */
    public void setSystemName(String systemName){
        this.systemName=systemName;
    }

    /**
     * 图片路径
     * tbl_sys_config.img_url
     */
    public String getImgUrl(){
        return imgUrl;
    }

    /**
     * 图片路径
     * tbl_sys_config.img_url
     */
    public void setImgUrl(String imgUrl){
        this.imgUrl=imgUrl;
    }

    /**
     * 状态
     * tbl_sys_config.status
     */
    public String getStatus(){
        return status;
    }

    /**
     * 状态
     * tbl_sys_config.status
     */
    public void setStatus(String status){
        this.status=status;
    }

    /**
     * 添加时间
     * tbl_sys_config.create_time
     */
    public String getCreateTime(){
        return createTime;
    }

    /**
     * 添加时间
     * tbl_sys_config.create_time
     */
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }

    /**
     * 创建人名称
     * tbl_sys_config.create_name
     */
    public String getCreateName(){
        return createName;
    }

    /**
     * 创建人名称
     * tbl_sys_config.create_name
     */
    public void setCreateName(String createName){
        this.createName=createName;
    }

    /**
     * 更新时间
     * tbl_sys_config.update_time
     */
    public String getUpdateTime(){
        return updateTime;
    }

    /**
     * 更新时间
     * tbl_sys_config.update_time
     */
    public void setUpdateTime(String updateTime){
        this.updateTime=updateTime;
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

