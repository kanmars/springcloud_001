/**
 * SN Generator
 */
package cn.kanmars.sn.entity;


/**
 * 系统ID表
 * tbl_sys_sequence
 */
public class TblSysSequence implements java.io.Serializable{
    /**
     * id
     * tbl_sys_sequence.id
     */
    private Integer id;

    /**
     * 序列Key
     * tbl_sys_sequence.key_value
     */
    private String keyValue;

    /**
     * 序列描述
     * tbl_sys_sequence.seq_desc
     */
    private String seqDesc;

    /**
     * 前缀
     * tbl_sys_sequence.top
     */
    private String top;

    /**
     * 后缀
     * tbl_sys_sequence.suffix
     */
    private String suffix;

    /**
     * 当前值
     * tbl_sys_sequence.curr_value
     */
    private Integer currValue;

    /**
     * 递增数量
     * tbl_sys_sequence.batch_size
     */
    private Integer batchSize;

    /**
     * 添加时间
     * tbl_sys_sequence.create_time
     */
    private String createTime;

    /**
     * 更新时间
     * tbl_sys_sequence.up_time
     */
    private String upTime;


    public TblSysSequence(){super();}
    public TblSysSequence(Integer id,String keyValue,String seqDesc,String top,String suffix,Integer currValue,Integer batchSize,String createTime,String upTime) {
        this.id = id;
        this.keyValue = keyValue;
        this.seqDesc = seqDesc;
        this.top = top;
        this.suffix = suffix;
        this.currValue = currValue;
        this.batchSize = batchSize;
        this.createTime = createTime;
        this.upTime = upTime;
    }
    /**
     * id
     * tbl_sys_sequence.id
     */
    public Integer getId(){
        return id;
    }

    /**
     * id
     * tbl_sys_sequence.id
     */
    public void setId(Integer id){
        this.id=id;
    }

    /**
     * 序列Key
     * tbl_sys_sequence.key_value
     */
    public String getKeyValue(){
        return keyValue;
    }

    /**
     * 序列Key
     * tbl_sys_sequence.key_value
     */
    public void setKeyValue(String keyValue){
        this.keyValue=keyValue;
    }

    /**
     * 序列描述
     * tbl_sys_sequence.seq_desc
     */
    public String getSeqDesc(){
        return seqDesc;
    }

    /**
     * 序列描述
     * tbl_sys_sequence.seq_desc
     */
    public void setSeqDesc(String seqDesc){
        this.seqDesc=seqDesc;
    }

    /**
     * 前缀
     * tbl_sys_sequence.top
     */
    public String getTop(){
        return top;
    }

    /**
     * 前缀
     * tbl_sys_sequence.top
     */
    public void setTop(String top){
        this.top=top;
    }

    /**
     * 后缀
     * tbl_sys_sequence.suffix
     */
    public String getSuffix(){
        return suffix;
    }

    /**
     * 后缀
     * tbl_sys_sequence.suffix
     */
    public void setSuffix(String suffix){
        this.suffix=suffix;
    }

    /**
     * 当前值
     * tbl_sys_sequence.curr_value
     */
    public Integer getCurrValue(){
        return currValue;
    }

    /**
     * 当前值
     * tbl_sys_sequence.curr_value
     */
    public void setCurrValue(Integer currValue){
        this.currValue=currValue;
    }

    /**
     * 递增数量
     * tbl_sys_sequence.batch_size
     */
    public Integer getBatchSize(){
        return batchSize;
    }

    /**
     * 递增数量
     * tbl_sys_sequence.batch_size
     */
    public void setBatchSize(Integer batchSize){
        this.batchSize=batchSize;
    }

    /**
     * 添加时间
     * tbl_sys_sequence.create_time
     */
    public String getCreateTime(){
        return createTime;
    }

    /**
     * 添加时间
     * tbl_sys_sequence.create_time
     */
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }

    /**
     * 更新时间
     * tbl_sys_sequence.up_time
     */
    public String getUpTime(){
        return upTime;
    }

    /**
     * 更新时间
     * tbl_sys_sequence.up_time
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

