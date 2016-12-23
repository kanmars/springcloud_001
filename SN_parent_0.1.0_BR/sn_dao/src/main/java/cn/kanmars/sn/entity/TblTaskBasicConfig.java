/**
 * SN Generator
 */
package cn.kanmars.sn.entity;


/**
 * 任务基本信息配置表
 * tbl_task_basic_config
 */
public class TblTaskBasicConfig implements java.io.Serializable{
    /**
     * 主键id
     * tbl_task_basic_config.id
     */
    private String id;

    /**
     * 任务名称
     * tbl_task_basic_config.task_name
     */
    private String taskName;

    /**
     * 任务系统类型
     * tbl_task_basic_config.parent_id
     */
    private String parentId;

    /**
     * 执行规则
     * tbl_task_basic_config.run_rule
     */
    private String runRule;

    /**
     * 执行状态
     * tbl_task_basic_config.run_status
     */
    private String runStatus;

    /**
     * 状态
     * tbl_task_basic_config.status
     */
    private String status;

    /**
     * 业务对象名称
     * tbl_task_basic_config.business_obj_name
     */
    private String businessObjName;

    /**
     * 任务数
     * tbl_task_basic_config.task_count
     */
    private Integer taskCount;

    /**
     * 网卡名称
     * tbl_task_basic_config.internet_name
     */
    private String internetName;

    /**
     * 服务器IP
     * tbl_task_basic_config.server_ip
     */
    private String serverIp;

    /**
     * 业务参数
     * tbl_task_basic_config.business_info
     */
    private String businessInfo;

    /**
     * 配置时间
     * tbl_task_basic_config.config_time
     */
    private String configTime;

    /**
     * 修改人
     * tbl_task_basic_config.up_person
     */
    private String upPerson;

    /**
     * 修改时间
     * tbl_task_basic_config.up_time
     */
    private String upTime;


    public TblTaskBasicConfig(){super();}
    public TblTaskBasicConfig(String id,String taskName,String parentId,String runRule,String runStatus,String status,String businessObjName,Integer taskCount,String internetName,String serverIp,String businessInfo,String configTime,String upPerson,String upTime) {
        this.id = id;
        this.taskName = taskName;
        this.parentId = parentId;
        this.runRule = runRule;
        this.runStatus = runStatus;
        this.status = status;
        this.businessObjName = businessObjName;
        this.taskCount = taskCount;
        this.internetName = internetName;
        this.serverIp = serverIp;
        this.businessInfo = businessInfo;
        this.configTime = configTime;
        this.upPerson = upPerson;
        this.upTime = upTime;
    }
    /**
     * 主键id
     * tbl_task_basic_config.id
     */
    public String getId(){
        return id;
    }

    /**
     * 主键id
     * tbl_task_basic_config.id
     */
    public void setId(String id){
        this.id=id;
    }

    /**
     * 任务名称
     * tbl_task_basic_config.task_name
     */
    public String getTaskName(){
        return taskName;
    }

    /**
     * 任务名称
     * tbl_task_basic_config.task_name
     */
    public void setTaskName(String taskName){
        this.taskName=taskName;
    }

    /**
     * 任务系统类型
     * tbl_task_basic_config.parent_id
     */
    public String getParentId(){
        return parentId;
    }

    /**
     * 任务系统类型
     * tbl_task_basic_config.parent_id
     */
    public void setParentId(String parentId){
        this.parentId=parentId;
    }

    /**
     * 执行规则
     * tbl_task_basic_config.run_rule
     */
    public String getRunRule(){
        return runRule;
    }

    /**
     * 执行规则
     * tbl_task_basic_config.run_rule
     */
    public void setRunRule(String runRule){
        this.runRule=runRule;
    }

    /**
     * 执行状态
     * tbl_task_basic_config.run_status
     */
    public String getRunStatus(){
        return runStatus;
    }

    /**
     * 执行状态
     * tbl_task_basic_config.run_status
     */
    public void setRunStatus(String runStatus){
        this.runStatus=runStatus;
    }

    /**
     * 状态
     * tbl_task_basic_config.status
     */
    public String getStatus(){
        return status;
    }

    /**
     * 状态
     * tbl_task_basic_config.status
     */
    public void setStatus(String status){
        this.status=status;
    }

    /**
     * 业务对象名称
     * tbl_task_basic_config.business_obj_name
     */
    public String getBusinessObjName(){
        return businessObjName;
    }

    /**
     * 业务对象名称
     * tbl_task_basic_config.business_obj_name
     */
    public void setBusinessObjName(String businessObjName){
        this.businessObjName=businessObjName;
    }

    /**
     * 任务数
     * tbl_task_basic_config.task_count
     */
    public Integer getTaskCount(){
        return taskCount;
    }

    /**
     * 任务数
     * tbl_task_basic_config.task_count
     */
    public void setTaskCount(Integer taskCount){
        this.taskCount=taskCount;
    }

    /**
     * 网卡名称
     * tbl_task_basic_config.internet_name
     */
    public String getInternetName(){
        return internetName;
    }

    /**
     * 网卡名称
     * tbl_task_basic_config.internet_name
     */
    public void setInternetName(String internetName){
        this.internetName=internetName;
    }

    /**
     * 服务器IP
     * tbl_task_basic_config.server_ip
     */
    public String getServerIp(){
        return serverIp;
    }

    /**
     * 服务器IP
     * tbl_task_basic_config.server_ip
     */
    public void setServerIp(String serverIp){
        this.serverIp=serverIp;
    }

    /**
     * 业务参数
     * tbl_task_basic_config.business_info
     */
    public String getBusinessInfo(){
        return businessInfo;
    }

    /**
     * 业务参数
     * tbl_task_basic_config.business_info
     */
    public void setBusinessInfo(String businessInfo){
        this.businessInfo=businessInfo;
    }

    /**
     * 配置时间
     * tbl_task_basic_config.config_time
     */
    public String getConfigTime(){
        return configTime;
    }

    /**
     * 配置时间
     * tbl_task_basic_config.config_time
     */
    public void setConfigTime(String configTime){
        this.configTime=configTime;
    }

    /**
     * 修改人
     * tbl_task_basic_config.up_person
     */
    public String getUpPerson(){
        return upPerson;
    }

    /**
     * 修改人
     * tbl_task_basic_config.up_person
     */
    public void setUpPerson(String upPerson){
        this.upPerson=upPerson;
    }

    /**
     * 修改时间
     * tbl_task_basic_config.up_time
     */
    public String getUpTime(){
        return upTime;
    }

    /**
     * 修改时间
     * tbl_task_basic_config.up_time
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

