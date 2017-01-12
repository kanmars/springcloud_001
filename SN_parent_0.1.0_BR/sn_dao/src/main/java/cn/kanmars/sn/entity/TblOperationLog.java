/**
 * SN Generator 
 */
package cn.kanmars.sn.entity;


/**
 * 操作日志表
 * tbl_operation_log
 */
public class TblOperationLog implements java.io.Serializable{
    /**
     * 操作ID
     * tbl_operation_log.operation_id
     */
    private Integer operationId;

    /**
     * 操作员
     * tbl_operation_log.operation_user
     */
    private String operationUser;

    /**
     * 操作时间
     * tbl_operation_log.operation_time
     */
    private String operationTime;

    /**
     * 操作名称
     * tbl_operation_log.operation_name
     */
    private String operationName;

    /**
     * 操作描述
     * tbl_operation_log.operation_desc
     */
    private String operationDesc;

    /**
     * 操作应用
     * tbl_operation_log.operation_app
     */
    private String operationApp;

    /**
     * 操作类与方法
     * tbl_operation_log.operation_classmethod
     */
    private String operationClassmethod;

    /**
     * 操作信息
     * tbl_operation_log.operation_info
     */
    private String operationInfo;

    /**
     * 添加时间
     * tbl_operation_log.create_time
     */
    private String createTime;

    /**
     * 更新时间
     * tbl_operation_log.update_time
     */
    private String updateTime;


    public TblOperationLog(){super();}
    public TblOperationLog(Integer operationId,String operationUser,String operationTime,String operationName,String operationDesc,String operationApp,String operationClassmethod,String operationInfo,String createTime,String updateTime) {
        this.operationId = operationId;
        this.operationUser = operationUser;
        this.operationTime = operationTime;
        this.operationName = operationName;
        this.operationDesc = operationDesc;
        this.operationApp = operationApp;
        this.operationClassmethod = operationClassmethod;
        this.operationInfo = operationInfo;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
    /**
     * 操作ID
     * tbl_operation_log.operation_id
     */
    public Integer getOperationId(){
        return operationId;
    }

    /**
     * 操作ID
     * tbl_operation_log.operation_id
     */
    public void setOperationId(Integer operationId){
        this.operationId=operationId;
    }

    /**
     * 操作员
     * tbl_operation_log.operation_user
     */
    public String getOperationUser(){
        return operationUser;
    }

    /**
     * 操作员
     * tbl_operation_log.operation_user
     */
    public void setOperationUser(String operationUser){
        this.operationUser=operationUser;
    }

    /**
     * 操作时间
     * tbl_operation_log.operation_time
     */
    public String getOperationTime(){
        return operationTime;
    }

    /**
     * 操作时间
     * tbl_operation_log.operation_time
     */
    public void setOperationTime(String operationTime){
        this.operationTime=operationTime;
    }

    /**
     * 操作名称
     * tbl_operation_log.operation_name
     */
    public String getOperationName(){
        return operationName;
    }

    /**
     * 操作名称
     * tbl_operation_log.operation_name
     */
    public void setOperationName(String operationName){
        this.operationName=operationName;
    }

    /**
     * 操作描述
     * tbl_operation_log.operation_desc
     */
    public String getOperationDesc(){
        return operationDesc;
    }

    /**
     * 操作描述
     * tbl_operation_log.operation_desc
     */
    public void setOperationDesc(String operationDesc){
        this.operationDesc=operationDesc;
    }

    /**
     * 操作应用
     * tbl_operation_log.operation_app
     */
    public String getOperationApp(){
        return operationApp;
    }

    /**
     * 操作应用
     * tbl_operation_log.operation_app
     */
    public void setOperationApp(String operationApp){
        this.operationApp=operationApp;
    }

    /**
     * 操作类与方法
     * tbl_operation_log.operation_classmethod
     */
    public String getOperationClassmethod(){
        return operationClassmethod;
    }

    /**
     * 操作类与方法
     * tbl_operation_log.operation_classmethod
     */
    public void setOperationClassmethod(String operationClassmethod){
        this.operationClassmethod=operationClassmethod;
    }

    /**
     * 操作信息
     * tbl_operation_log.operation_info
     */
    public String getOperationInfo(){
        return operationInfo;
    }

    /**
     * 操作信息
     * tbl_operation_log.operation_info
     */
    public void setOperationInfo(String operationInfo){
        this.operationInfo=operationInfo;
    }

    /**
     * 添加时间
     * tbl_operation_log.create_time
     */
    public String getCreateTime(){
        return createTime;
    }

    /**
     * 添加时间
     * tbl_operation_log.create_time
     */
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }

    /**
     * 更新时间
     * tbl_operation_log.update_time
     */
    public String getUpdateTime(){
        return updateTime;
    }

    /**
     * 更新时间
     * tbl_operation_log.update_time
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

