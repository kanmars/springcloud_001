/**
 * SN Generator
 */
package cn.kanmars.sn.dao;


import java.util.List;
import java.util.Map;
import java.util.HashMap;
import cn.kanmars.sn.entity.TblSysmenuInfo;
/**
 * 系统菜单表
 * tbl_sysmenu_info
 */
@cn.kanmars.sn.MybatisRepository
public interface TblSysmenuInfoMapper {
    /**
     * 查询单个对象
     */
    public TblSysmenuInfo select(TblSysmenuInfo tblSysmenuInfo);

    /**
     * 查询一个对象列表
     */
    public List<TblSysmenuInfo> selectList(TblSysmenuInfo tblSysmenuInfo);

    /**
     * 新增对象，返回增加的数量
     */
    public int insert(TblSysmenuInfo tblSysmenuInfo);

    /**
     * 通过主键修改对象，返回修改的数量，主键不可修改
     */
    public int updateByPrimaryKey(TblSysmenuInfo tblSysmenuInfo);

    /**
     * 删除对象，返回删除的数量
     */
    public int delete(TblSysmenuInfo tblSysmenuInfo);

    /**
     * 查询一个对象HashMap，字段未驼峰化
     */
    public HashMap queryOneMap(HashMap paramMap);

    /**
     * 查询一个对象List<HashMap>，字段未驼峰化
     */
    public List<HashMap> queryListMap(HashMap paramMap);

    /**
     * 原子性更新一个对象，主键不可修改
     */
    public int updateCAS(HashMap paramMap);

    /**
     * 分页查询
     * 参数有两种情况：
     *     1、传递countFlag=Y标记,则返回的是计数
     *     2、传递countFlag=N标记、limitStart、limitSize,则返回的是分页结果,字段未驼峰化
     */
    public List<HashMap> queryForPage(HashMap paramMap);

    /**
     * 批量插入,如果是mysql数据库，需要在jdbcUrl中设置allowMultiQueries=true参数才可以使用
     * 如果为MYSQL数据库，返回值为插入条数，如果为ORACLE，返回值为第一条更新语句的指定结果，并非所有批量插入的总影响行数
     */
    public int insertBatch(List<HashMap> tblSysmenuInfo);

    /**
     * 批量更新,如果是mysql数据库，需要在jdbcUrl中设置allowMultiQueries=true参数才可以使用
     * 返回值为第一条更新语句的执行结果，并非所有批量更新的语句总和
     */
    public int updateBatch(List<HashMap> tblSysmenuInfo);

    /**
     * 根据主键查询单个对象
     */
    public TblSysmenuInfo selectByPrimaryKey(String menuNo);

    /**
     * editInfo，空的update语句
     */
    public int editInfo(HashMap paramMap);

	/**
     * 根据当前用户ID 查询菜单
     *
     */
    List<TblSysmenuInfo> queryList(String userNo);


}

