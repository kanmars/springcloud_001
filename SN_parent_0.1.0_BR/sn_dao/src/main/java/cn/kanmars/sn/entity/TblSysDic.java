/**
 * SN Generator
 */
package cn.kanmars.sn.entity;


/**
 * 系统字典表
 * tbl_sys_dic
 */
public class TblSysDic implements java.io.Serializable{
    /**
     * ID
     * tbl_sys_dic.id
     */
    private Integer id;

    /**
     * 一级字典码
     * tbl_sys_dic.l1_code
     */
    private String l1Code;

    /**
     * 一级字典描述
     * tbl_sys_dic.l1_desc
     */
    private String l1Desc;

    /**
     * 二级字典码
     * tbl_sys_dic.l2_code
     */
    private String l2Code;

    /**
     * 二级字典描述
     * tbl_sys_dic.l2_desc
     */
    private String l2Desc;

    /**
     * 码表参数
     * tbl_sys_dic.code_param
     */
    private String codeParam;

    /**
     * 实际值
     * tbl_sys_dic.code_value
     */
    private String codeValue;

    /**
     * 排序
     * tbl_sys_dic.code_idx
     */
    private Integer codeIdx;

    /**
     * 存活时间(秒)
     * tbl_sys_dic.live_count
     */
    private Integer liveCount;

    /**
     * 添加时间
     * tbl_sys_dic.create_time
     */
    private String createTime;

    /**
     * 更新时间
     * tbl_sys_dic.up_time
     */
    private String upTime;


    public TblSysDic(){super();}
    public TblSysDic(Integer id,String l1Code,String l1Desc,String l2Code,String l2Desc,String codeParam,String codeValue,Integer codeIdx,Integer liveCount,String createTime,String upTime) {
        this.id = id;
        this.l1Code = l1Code;
        this.l1Desc = l1Desc;
        this.l2Code = l2Code;
        this.l2Desc = l2Desc;
        this.codeParam = codeParam;
        this.codeValue = codeValue;
        this.codeIdx = codeIdx;
        this.liveCount = liveCount;
        this.createTime = createTime;
        this.upTime = upTime;
    }
    /**
     * ID
     * tbl_sys_dic.id
     */
    public Integer getId(){
        return id;
    }

    /**
     * ID
     * tbl_sys_dic.id
     */
    public void setId(Integer id){
        this.id=id;
    }

    /**
     * 一级字典码
     * tbl_sys_dic.l1_code
     */
    public String getL1Code(){
        return l1Code;
    }

    /**
     * 一级字典码
     * tbl_sys_dic.l1_code
     */
    public void setL1Code(String l1Code){
        this.l1Code=l1Code;
    }

    /**
     * 一级字典描述
     * tbl_sys_dic.l1_desc
     */
    public String getL1Desc(){
        return l1Desc;
    }

    /**
     * 一级字典描述
     * tbl_sys_dic.l1_desc
     */
    public void setL1Desc(String l1Desc){
        this.l1Desc=l1Desc;
    }

    /**
     * 二级字典码
     * tbl_sys_dic.l2_code
     */
    public String getL2Code(){
        return l2Code;
    }

    /**
     * 二级字典码
     * tbl_sys_dic.l2_code
     */
    public void setL2Code(String l2Code){
        this.l2Code=l2Code;
    }

    /**
     * 二级字典描述
     * tbl_sys_dic.l2_desc
     */
    public String getL2Desc(){
        return l2Desc;
    }

    /**
     * 二级字典描述
     * tbl_sys_dic.l2_desc
     */
    public void setL2Desc(String l2Desc){
        this.l2Desc=l2Desc;
    }

    /**
     * 码表参数
     * tbl_sys_dic.code_param
     */
    public String getCodeParam(){
        return codeParam;
    }

    /**
     * 码表参数
     * tbl_sys_dic.code_param
     */
    public void setCodeParam(String codeParam){
        this.codeParam=codeParam;
    }

    /**
     * 实际值
     * tbl_sys_dic.code_value
     */
    public String getCodeValue(){
        return codeValue;
    }

    /**
     * 实际值
     * tbl_sys_dic.code_value
     */
    public void setCodeValue(String codeValue){
        this.codeValue=codeValue;
    }

    /**
     * 排序
     * tbl_sys_dic.code_idx
     */
    public Integer getCodeIdx(){
        return codeIdx;
    }

    /**
     * 排序
     * tbl_sys_dic.code_idx
     */
    public void setCodeIdx(Integer codeIdx){
        this.codeIdx=codeIdx;
    }

    /**
     * 存活时间(秒)
     * tbl_sys_dic.live_count
     */
    public Integer getLiveCount(){
        return liveCount;
    }

    /**
     * 存活时间(秒)
     * tbl_sys_dic.live_count
     */
    public void setLiveCount(Integer liveCount){
        this.liveCount=liveCount;
    }

    /**
     * 添加时间
     * tbl_sys_dic.create_time
     */
    public String getCreateTime(){
        return createTime;
    }

    /**
     * 添加时间
     * tbl_sys_dic.create_time
     */
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }

    /**
     * 更新时间
     * tbl_sys_dic.up_time
     */
    public String getUpTime(){
        return upTime;
    }

    /**
     * 更新时间
     * tbl_sys_dic.up_time
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

