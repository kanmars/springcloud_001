/**
 * Gome SN Generator 
 */
package cn.kanmars.sn.entity;


/**
 * DEMO表信息演示
 * tbl_demo_info
 */
public class TblDemoInfo implements java.io.Serializable{
    /**
     * 表ID
     * tbl_demo_info.demo_id
     */
    private Integer demoId;

    /**
     * 演示名称
     * tbl_demo_info.demo_nm
     */
    private String demoNm;

    /**
     * 演示金额
     * tbl_demo_info.demo_money
     */
    private Integer demoMoney;

    /**
     * 创建日期
     * tbl_demo_info.create_date
     */
    private String createDate;

    /**
     * 创建时间
     * tbl_demo_info.create_time
     */
    private String createTime;

    /**
     * 静态选择框
     * tbl_demo_info.select_static
     */
    private String selectStatic;

    /**
     * 动态选择框
     * tbl_demo_info.select_dynamic
     */
    private String selectDynamic;

    /**
     * 静态单选按钮
     * tbl_demo_info.radio_static
     */
    private String radioStatic;

    /**
     * 动态单选按钮
     * tbl_demo_info.radio_dynamic
     */
    private String radioDynamic;

    /**
     * 静态复选框
     * tbl_demo_info.checkbox_static
     */
    private String checkboxStatic;

    /**
     * 动态复选框
     * tbl_demo_info.checkbox_dynamic
     */
    private String checkboxDynamic;


    public TblDemoInfo(){super();}
    public TblDemoInfo(Integer demoId,String demoNm,Integer demoMoney,String createDate,String createTime,String selectStatic,String selectDynamic,String radioStatic,String radioDynamic,String checkboxStatic,String checkboxDynamic) {
        this.demoId = demoId;
        this.demoNm = demoNm;
        this.demoMoney = demoMoney;
        this.createDate = createDate;
        this.createTime = createTime;
        this.selectStatic = selectStatic;
        this.selectDynamic = selectDynamic;
        this.radioStatic = radioStatic;
        this.radioDynamic = radioDynamic;
        this.checkboxStatic = checkboxStatic;
        this.checkboxDynamic = checkboxDynamic;
    }
    /**
     * 表ID
     * tbl_demo_info.demo_id
     */
    public Integer getDemoId(){
        return demoId;
    }

    /**
     * 表ID
     * tbl_demo_info.demo_id
     */
    public void setDemoId(Integer demoId){
        this.demoId=demoId;
    }

    /**
     * 演示名称
     * tbl_demo_info.demo_nm
     */
    public String getDemoNm(){
        return demoNm;
    }

    /**
     * 演示名称
     * tbl_demo_info.demo_nm
     */
    public void setDemoNm(String demoNm){
        this.demoNm=demoNm;
    }

    /**
     * 演示金额
     * tbl_demo_info.demo_money
     */
    public Integer getDemoMoney(){
        return demoMoney;
    }

    /**
     * 演示金额
     * tbl_demo_info.demo_money
     */
    public void setDemoMoney(Integer demoMoney){
        this.demoMoney=demoMoney;
    }

    /**
     * 创建日期
     * tbl_demo_info.create_date
     */
    public String getCreateDate(){
        return createDate;
    }

    /**
     * 创建日期
     * tbl_demo_info.create_date
     */
    public void setCreateDate(String createDate){
        this.createDate=createDate;
    }

    /**
     * 创建时间
     * tbl_demo_info.create_time
     */
    public String getCreateTime(){
        return createTime;
    }

    /**
     * 创建时间
     * tbl_demo_info.create_time
     */
    public void setCreateTime(String createTime){
        this.createTime=createTime;
    }

    /**
     * 静态选择框
     * tbl_demo_info.select_static
     */
    public String getSelectStatic(){
        return selectStatic;
    }

    /**
     * 静态选择框
     * tbl_demo_info.select_static
     */
    public void setSelectStatic(String selectStatic){
        this.selectStatic=selectStatic;
    }

    /**
     * 动态选择框
     * tbl_demo_info.select_dynamic
     */
    public String getSelectDynamic(){
        return selectDynamic;
    }

    /**
     * 动态选择框
     * tbl_demo_info.select_dynamic
     */
    public void setSelectDynamic(String selectDynamic){
        this.selectDynamic=selectDynamic;
    }

    /**
     * 静态单选按钮
     * tbl_demo_info.radio_static
     */
    public String getRadioStatic(){
        return radioStatic;
    }

    /**
     * 静态单选按钮
     * tbl_demo_info.radio_static
     */
    public void setRadioStatic(String radioStatic){
        this.radioStatic=radioStatic;
    }

    /**
     * 动态单选按钮
     * tbl_demo_info.radio_dynamic
     */
    public String getRadioDynamic(){
        return radioDynamic;
    }

    /**
     * 动态单选按钮
     * tbl_demo_info.radio_dynamic
     */
    public void setRadioDynamic(String radioDynamic){
        this.radioDynamic=radioDynamic;
    }

    /**
     * 静态复选框
     * tbl_demo_info.checkbox_static
     */
    public String getCheckboxStatic(){
        return checkboxStatic;
    }

    /**
     * 静态复选框
     * tbl_demo_info.checkbox_static
     */
    public void setCheckboxStatic(String checkboxStatic){
        this.checkboxStatic=checkboxStatic;
    }

    /**
     * 动态复选框
     * tbl_demo_info.checkbox_dynamic
     */
    public String getCheckboxDynamic(){
        return checkboxDynamic;
    }

    /**
     * 动态复选框
     * tbl_demo_info.checkbox_dynamic
     */
    public void setCheckboxDynamic(String checkboxDynamic){
        this.checkboxDynamic=checkboxDynamic;
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

