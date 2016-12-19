/**
 * Gome SN Generator 
 */
package cn.kanmars.sn.logic;


import java.util.HashMap;
import cn.kanmars.sn.entity.TblFileInfo;
/**
 * 文件信息
 * tbl_file_info
 */
public interface FileInfoLogic {

    /*
     * 查询信息
     */
    public TblFileInfo queryFileInfo(TblFileInfo tblFileInfo) throws Exception;

    /*
     * 新增信息
     */
    public Integer insertFileInfo(TblFileInfo tblFileInfo) throws Exception;

    /*
     * 修改信息
     */
    public Integer updateFileInfo(TblFileInfo tblFileInfo) throws Exception;

    /*
     * 删除信息
     */
    public Integer deleteFileInfo(TblFileInfo tblFileInfo) throws Exception;

    /*
     * 查询信息queryPage
     */
    public HashMap queryPageFileInfo(HashMap paramMap) throws Exception;

}

