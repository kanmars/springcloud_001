/**
 * Gome SN Generator 
 */
package cn.kanmars.sn.logic;


import java.util.HashMap;
import cn.kanmars.sn.entity.TblTaskBasicConfig;
/**
 * 任务基本信息配置表
 * tbl_task_basic_config
 */
public interface TaskBasicConfigLogic {

    /*
     * 查询信息
     */
    public TblTaskBasicConfig queryTaskBasicConfig(TblTaskBasicConfig tblTaskBasicConfig) throws Exception;

    /*
     * 新增信息
     */
    public Integer insertTaskBasicConfig(TblTaskBasicConfig tblTaskBasicConfig) throws Exception;

    /*
     * 修改信息
     */
    public Integer updateTaskBasicConfig(TblTaskBasicConfig tblTaskBasicConfig) throws Exception;

    /*
     * 删除信息
     */
    public Integer deleteTaskBasicConfig(TblTaskBasicConfig tblTaskBasicConfig) throws Exception;

    /*
     * 查询信息queryPage
     */
    public HashMap queryPageTaskBasicConfig(HashMap paramMap) throws Exception;

}

