/**
 * SN Generator
 */
package cn.kanmars.sn.logic;


import java.util.HashMap;
import cn.kanmars.sn.entity.TblDemoInfo;
/**
 * DEMO表信息演示
 * tbl_demo_info
 */
public interface DemoInfoLogic {

    /*
     * 查询信息
     */
    public TblDemoInfo queryDemoInfo(TblDemoInfo tblDemoInfo) throws Exception;

    /*
     * 新增信息
     */
    public Integer insertDemoInfo(TblDemoInfo tblDemoInfo) throws Exception;

    /*
     * 修改信息
     */
    public Integer updateDemoInfo(TblDemoInfo tblDemoInfo) throws Exception;

    /*
     * 删除信息
     */
    public Integer deleteDemoInfo(TblDemoInfo tblDemoInfo) throws Exception;

    /*
     * 查询信息queryPage
     */
    public HashMap queryPageDemoInfo(HashMap paramMap) throws Exception;

}

