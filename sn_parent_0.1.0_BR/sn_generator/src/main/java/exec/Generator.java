package exec;

import context.Context;
import pipe.PipeLine;
import pipe.impl.*;
import pipe.impl.dmodel.InputModel;
import pipe.impl.meta.Power;
import pipe.impl.meta.SQLTYPE;
import pipe.impl.meta.TableInfoMetaCreater;
import pipe.impl.meta.DBTYPE;
import tablelist.TableList;

/**
 * Created by baolong on 2016/1/7.
 * 使用说明：
 * 请修改：17行到44行的数据源、生成路径，包名，表名
 * 请修改：67行到76行的生成内容
 * 之后以Main方法运行，即可生成目标代码
 */
public class Generator {

    public static void main(String[] args) throws Exception {
        exec(
                DBTYPE.MYSQL.toString(),//数据库类型//数据库类型，可以为MYSQL或者为ORACLE，或者SQLITE
                "E:\\work\\GFS_NEW_IntenlliJ\\SN\\branches\\SN-parent_0.1.0_BR",//基础路径
//                "E:\\work\\GFS_NEW_IntenlliJ\\SN\\trunk",//基础路径
//                "C:\\workspace\\SVN\\SN\\branches\\SN-parent_0.0.1_BR",//基础路径
                //*******************  MYSQL数据库  *****************************************************************
                "com.mysql.jdbc.Driver",//驱动名称
                "jdbc:mysql://10.126.53.197:3306/sn_main",//链接URL
                "snuat",//用户名称
                "123456",//密码
                "sn_main",//schema
                //*******************  ORACLE数据库  *****************************************************************
//                "oracle.jdbc.driver.OracleDriver",//驱动名称
//                "jdbc:oracle:thin:@10.126.53.197:1521:finance",//链接URL
//                "billuat",//用户名称
//                "billuat",//密码
//                "BILLUAT",//schema
                //*******************  SQLITE数据库  *****************************************************************
//                "org.sqlite.JDBC",//驱动名称
//                "jdbc:sqlite:D:/sqlite.sn.db3",//链接URL
//                "",//用户名称
//                "",//密码
//                "",//schema
                new String[]{
                        "tbl_demo_info",
//                        "tbl_sys_dic",
//                        "tbl_sys_sequence",
//                        "tbl_sysmenu_info",
//                        "tbl_sysrole_info",
//                        "tbl_sysrole_menu_info",
//                        "tbl_sysuser_info",
//                        "tbl_sysuser_role_info",
//                        "tbl_task_basic_config",
//                        "tbl_sys_config",
//                        "tbl_file_info"
                },
                //****************************************************************************************************
                //使用内部构建的方式获取表信息，如果是在数据库中获取Meta，则此段可以注释掉
                //****************************************************************************************************
                null,
//                new TableInfoMetaCreater(){
//                    {
//                        tableInfoMetaCreater.A_001_addTable("tbl_d_info","测试表");
//                        tableInfoMetaCreater.addColumn("tbl_d_info", "d1", "列名称", true, SQLTYPE.VARCHAR, 200, 0, InputModel.getInstance(Power.POWER_ADDWRITEREAD, null, -1, -1, null, "^[.*]$", "不符合规则", false, null).getModelJSON());
//                        a_getTableInfo.setGetter(tableInfoMetaCreater);
//                    }
//                },
                true, //entity
                true, //dao
                true, //mapper
                true, //logic
                true, //controller
                true, //ftl
                true //js
        );
    }



    public static void exec(String dbtype,String basePath,String driver,String connectionurl,String username,String password,String schema,String[] tables,TableInfoMetaCreater tableInfoMetaCreater,boolean entity,boolean dao,boolean mapper,boolean logic,boolean controller,boolean ftl,boolean js) throws Exception {
        Context context = new Context();
        context.put(Context.FILE_CHARSET,"utf-8");
        String mohuchaxun = "true";//是否支持模糊查询
        //************************************************************************************************************
        //预处理，设置路径，设置各mapper,logic,controller所继承的类
        //************************************************************************************************************
        preProcess(context, basePath);
        {
            context.put(Context.JDBC_DRIVERCLASS,driver);
            context.put(Context.JDBC_CONNECTIONURL,connectionurl);
            context.put(Context.JDBC_USERNAME,username);
            context.put(Context.JDBC_PASSWORD, password);
            context.put(Context.JDBC_SCHEMA, schema);
        }
        context.init();

        TableList tableList = new TableList();
        if(tableInfoMetaCreater==null){
            for(String table : tables){
                tableList.addTable(table);
            }
        }else{
            for(String table : tableInfoMetaCreater.getTables()){
                tableList.addTable(table);
            }
        }

        //************************************************************************************************************
        //管道初始设置
        //************************************************************************************************************
        {

            PipeLine pipeline = new PipeLine();
            A_GetTableInfo a_getTableInfo = new A_GetTableInfo(dbtype,tableInfoMetaCreater);
            pipeline.addPipe(a_getTableInfo);
            if(entity)     pipeline.addPipe(new Create_001_Entity());
            if(dao)        pipeline.addPipe(new Create_002_Dao());
            if(mapper)     pipeline.addPipe(new Create_003_Mapper(dbtype,mohuchaxun));
            if(logic)      pipeline.addPipe(new Create_004_Logic());
            if(logic)      pipeline.addPipe(new Create_004_LogicImpl());
            if(controller) pipeline.addPipe(new Create_005_Controller());
            if(ftl)        pipeline.addPipe(new Create_006_Ftl());
            if(js)         pipeline.addPipe(new Create_007_Js());
            pipeline.exec(context,tableList);
        }
        context.close();
    }

    private static void setEntityProperties(Context context){
        //增加entity实现的接口，可增加多个
        context.addtoList(Context.IMPLEMENTS_ENTITY, "java.io.Serializable");
        //增加entity继承的类，仅有一个
        //context.put(Context.EXTENDS_ENTITY, "java.util.HashMap");
        //增加entity的注解，则增加多个
        //context.addtoList(Context.ANNOTATION_ENTITY,"cn.com.gome.sn.MybatisRepository");
    }

    private static void setDaoProperties(Context context){
        //************************************************************************************************************
        //dao配置，包括实现的接口，继承的类，类的注解
        //************************************************************************************************************
        //增加dao实现的接口，可增加多个
        //context.addtoList(Context.IMPLEMENTS_DAO, "java.io.Serializable");
        //增加dao继承的类，仅有一个
        //context.put(Context.EXTENDS_DAO, "java.util.HashMap");
        //增加dao的注解，则增加多个
        context.addtoList(Context.ANNOTATION_DAO,"cn.kanmars.sn.MybatisRepository");
    }

    private static void setLogicProperties(Context context){
        //增加logicImpl实现的接口，可增加多个
        //context.addtoList(Context.IMPLEMENTS_LOGIC, "java.io.Serializable");
        //增加logicImpl继承的类，仅有一个
        //context.put(Context.EXTENDS_LOGIC, "java.util.HashMap");
        //增加logicImpl的注解，可增加多个,LogicImpl应有Service和Transactional注解
        context.addtoList(Context.ANNOTATION_LOGIC,"Service");
        context.addtoList(Context.ANNOTATION_LOGIC,"Transactional");
    }
    private static void setControllerProperties(Context context){
        //增加Controller实现的接口，可增加多个
        //context.addtoList(Context.IMPLEMENTS_CONTROLLER, "java.io.Serializable");
        //增加Controller继承的类，仅有一个
        context.put(Context.EXTENDS_CONTROLLER, "AdvancedAjaxBaseController");
        //增加Controller的注解，可增加多个
        context.addtoList(Context.ANNOTATION_CONTROLLER,"Controller");
    }

    private static void preProcess(Context context,String basepath){
        context.put(Context.PATH_ENTITY,      basepath+"\\sn_dao\\src\\main\\java\\cn\\kanmars\\sn\\entity");
        context.put(Context.PATH_DAO,         basepath+"\\sn_dao\\src\\main\\java\\cn\\kanmars\\sn\\dao");
        context.put(Context.PATH_MAPPER,      basepath+"\\sn_dao\\src\\main\\resources\\props\\sn\\mapper");
        context.put(Context.PATH_LOGIC,       basepath+"\\sn_admin\\src\\main\\java\\cn\\kanmars\\sn\\logic");
        context.put(Context.PATH_CONTROLLER,  basepath+"\\sn_admin\\src\\main\\java\\cn\\kanmars\\sn\\controller");
        context.put(Context.PATH_FTL,         basepath+"\\sn_admin\\src\\main\\resources\\templates");
        context.put(Context.PATH_JS,          basepath+"\\sn_admin\\src\\main\\resources\\static\\js");

        context.put(Context.PACKAGE_ENTITY, "cn.kanmars.sn.entity");
        context.put(Context.PACKAGE_DAO, "cn.kanmars.sn.dao");
        context.put(Context.PACKAGE_LOGIC, "cn.kanmars.sn.logic");
        context.put(Context.PACKAGE_CONTROLLER, "cn.kanmars.sn.controller");

        //************************************************************************************************************
        //entity配置，包括实现的接口，继承的类，类的注解,一般不需要修改
        //Dao配置，包括实现的接口，继承的类，类的注解,一般不需要修改
        //logic配置，包括实现的接口，继承的类，类的注解
        //Controller配置，包括实现的接口，继承的类，类的注解,一般不需要修改
        //************************************************************************************************************
        setEntityProperties(context);
        setDaoProperties(context);
        setLogicProperties(context);
        setControllerProperties(context);
    }
}
