/**
 * Gome SN Generator 
 */
package cn.kanmars.sn.entity;


/**
 * 文件信息
 * tbl_file_info
 */
public class TblFileInfo implements java.io.Serializable{
    /**
     * 文件编号
     * tbl_file_info.file_no
     */
    private Integer fileNo;

    /**
     * 业务类型
     * tbl_file_info.business_type
     */
    private String businessType;

    /**
     * 业务编号
     * tbl_file_info.business_no
     */
    private Integer businessNo;

    /**
     * 文件名称
     * tbl_file_info.file_Name
     */
    private String fileName;

    /**
     * 文件路径
     * tbl_file_info.file_path
     */
    private String filePath;

    /**
     * 文件根路径
     * tbl_file_info.file_root_path
     */
    private String fileRootPath;

    /**
     * 添加时间
     * tbl_file_info.create_tm
     */
    private String createTm;

    /**
     * 文件状态
     * tbl_file_info.file_stat
     */
    private String fileStat;

    /**
     * 文件大小
     * tbl_file_info.file_size
     */
    private String fileSize;

    /**
     * 文件描述
     * tbl_file_info.file_desc
     */
    private String fileDesc;


    public TblFileInfo(){super();}
    public TblFileInfo(Integer fileNo,String businessType,Integer businessNo,String fileName,String filePath,String fileRootPath,String createTm,String fileStat,String fileSize,String fileDesc) {
        this.fileNo = fileNo;
        this.businessType = businessType;
        this.businessNo = businessNo;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileRootPath = fileRootPath;
        this.createTm = createTm;
        this.fileStat = fileStat;
        this.fileSize = fileSize;
        this.fileDesc = fileDesc;
    }
    /**
     * 文件编号
     * tbl_file_info.file_no
     */
    public Integer getFileNo(){
        return fileNo;
    }

    /**
     * 文件编号
     * tbl_file_info.file_no
     */
    public void setFileNo(Integer fileNo){
        this.fileNo=fileNo;
    }

    /**
     * 业务类型
     * tbl_file_info.business_type
     */
    public String getBusinessType(){
        return businessType;
    }

    /**
     * 业务类型
     * tbl_file_info.business_type
     */
    public void setBusinessType(String businessType){
        this.businessType=businessType;
    }

    /**
     * 业务编号
     * tbl_file_info.business_no
     */
    public Integer getBusinessNo(){
        return businessNo;
    }

    /**
     * 业务编号
     * tbl_file_info.business_no
     */
    public void setBusinessNo(Integer businessNo){
        this.businessNo=businessNo;
    }

    /**
     * 文件名称
     * tbl_file_info.file_Name
     */
    public String getFileName(){
        return fileName;
    }

    /**
     * 文件名称
     * tbl_file_info.file_Name
     */
    public void setFileName(String fileName){
        this.fileName=fileName;
    }

    /**
     * 文件路径
     * tbl_file_info.file_path
     */
    public String getFilePath(){
        return filePath;
    }

    /**
     * 文件路径
     * tbl_file_info.file_path
     */
    public void setFilePath(String filePath){
        this.filePath=filePath;
    }

    /**
     * 文件根路径
     * tbl_file_info.file_root_path
     */
    public String getFileRootPath(){
        return fileRootPath;
    }

    /**
     * 文件根路径
     * tbl_file_info.file_root_path
     */
    public void setFileRootPath(String fileRootPath){
        this.fileRootPath=fileRootPath;
    }

    /**
     * 添加时间
     * tbl_file_info.create_tm
     */
    public String getCreateTm(){
        return createTm;
    }

    /**
     * 添加时间
     * tbl_file_info.create_tm
     */
    public void setCreateTm(String createTm){
        this.createTm=createTm;
    }

    /**
     * 文件状态
     * tbl_file_info.file_stat
     */
    public String getFileStat(){
        return fileStat;
    }

    /**
     * 文件状态
     * tbl_file_info.file_stat
     */
    public void setFileStat(String fileStat){
        this.fileStat=fileStat;
    }

    /**
     * 文件大小
     * tbl_file_info.file_size
     */
    public String getFileSize(){
        return fileSize;
    }

    /**
     * 文件大小
     * tbl_file_info.file_size
     */
    public void setFileSize(String fileSize){
        this.fileSize=fileSize;
    }

    /**
     * 文件描述
     * tbl_file_info.file_desc
     */
    public String getFileDesc(){
        return fileDesc;
    }

    /**
     * 文件描述
     * tbl_file_info.file_desc
     */
    public void setFileDesc(String fileDesc){
        this.fileDesc=fileDesc;
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

