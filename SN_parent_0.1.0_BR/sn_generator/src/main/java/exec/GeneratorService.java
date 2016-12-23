package exec;

import context.Context;
import utils.FileUtils;
import utils.FormatUtils;
import utils.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by baolong on 2016/2/22.
 */
public class GeneratorService {

    public static final String charset="utf-8";

    public static void main(String[] args) throws Exception {
        exec("E:\\work\\GFS_NEW_IntenlliJ\\SN\\branches\\SN-parent_0.1.0_BR",
                "Test",
                "金融业务",
                new ArrayList<String>(){{
                    this.add("serviceA[方法A];GET;id,name,vvv;hasBody;Alogic[LogicA],Blogic[LogicC];HashMap");
                    this.add("serviceB[方法B];POST;id,name;;Alogic[LogicA],Blogic[LogicC];String");
                    this.add("serviceB[方法B];POST;id,name,age;hasBody;Alogic[LogicA],Blogic[LogicC];String");
                }}

        );
    }


    public static void exec(String basePath,String serviceName,String serviceDesc ,List<String> serviceMethodList) throws Exception{
        Context c = new Context();
        c.put("servicePath", basePath+"\\sn_service\\src\\main\\java\\cn\\kanmars\\sn\\service");
        c.put("servicePackage", "cn.kanmars.sn.service");
        c.put("logicPath", basePath+"\\sn_service\\src\\main\\java\\cn\\kanmars\\sn\\logic");
        c.put("logicImplPath", basePath+"\\sn_service\\src\\main\\java\\cn\\kanmars\\sn\\logic\\impl");
        c.put("logicPackage", "cn.kanmars.sn.logic");
        c.put("logicPackageImpl", "cn.kanmars.sn.logic.impl");

        //需要修改内容-------开始
        c.put("serviceName", serviceName);//接口名称
        c.put("serviceDesc", serviceDesc);//接口描述
        for(String serviceMethod : serviceMethodList){
            c.addtoList("serviceMethodList", serviceMethod);
        }
        HashMap<String,String> logics = new HashMap<String,String>();
        for(String serviceMethod : serviceMethodList){
            String[] params = serviceMethod.split(";");
            String logics_strs = params[4];
            for(String logic : logics_strs.split(",")){
                if(!logic.contains("[")){
                    logics.put(logic,"");
                }else{
                    logics.put(getName(logic),getDesc(logic));
                }
            }
        }
        c.put("logics", logics);


        createService(c);

        for(Map.Entry<String,String> logic:logics.entrySet()){
            createLogic(c,logic);
            createLogicImpl(c,logic);
        }

    }


    public static void createService(Context c)  throws Exception {
        //创建文件夹
        String serviceName = (String)c.get("serviceName");
        String serviceDesc = (String)c.get("serviceDesc");
        String servicePath = (String)c.get("servicePath");
        String servicePackage = (String)c.get("servicePackage");
        List<String> serviceMethodList = (List<String>)c.get("serviceMethodList");
        HashMap<String,String> logics = (HashMap<String,String>)c.get("logics");
        if(!FileUtils.mkdirs(servicePath)){
            throw new Exception("文件夹["+servicePath+"]创建不成功");
        }
        String className = FormatUtils.firstBig(serviceName)+"FacadeService";
        String fileName = className+".java";
        File entityFile = new File(servicePath + File.separator+fileName);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(entityFile),charset));

        //准备内容
        StringBuilder sb = new StringBuilder();
        sb.append("/**\r\n * SN Generator \r\n */\r\n");
        sb.append("package " + servicePackage +";").append("\r\n").append("\r\n");


        sb.append("import cn.com.xcommon.frame.logger.HLogger;").append("\r\n");
        sb.append("import cn.com.xcommon.frame.logger.LoggerManager;").append("\r\n");
        sb.append("import cn.com.xcommon.frame.logic.ResultEnum;").append("\r\n");
        sb.append("import cn.com.xcommon.frame.util.*;").append("\r\n");

        for(Map.Entry e : logics.entrySet()){
            sb.append("import cn.kanmars.sn.logic."+e.getKey()+";").append("\r\n");
        }

        sb.append("import org.springframework.beans.factory.annotation.Autowired;").append("\r\n");
        sb.append("import org.springframework.stereotype.Controller;").append("\r\n");
        sb.append("import org.springframework.web.bind.annotation.*;").append("\r\n");
        sb.append("import java.util.*;").append("\r\n").append("\r\n");


        sb.append("/**").append("\r\n");
        sb.append(" * "+serviceDesc).append("\r\n");
        sb.append(" */").append("\r\n");
        sb.append("@Controller").append("\r\n");
        sb.append("@RequestMapping(\"/"+serviceName.toLowerCase()+"\")").append("\r\n");
        sb.append("public class "+className+" {").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("    protected HLogger logger = LoggerManager.getLoger(\""+className+"\");").append("\r\n");
        sb.append("").append("\r\n");


        for(Map.Entry e : logics.entrySet()){
            sb.append("    @Autowired").append("\r\n");
            sb.append("    private "+FormatUtils.firstBig(e.getKey().toString())+" "+FormatUtils.firstSmall(e.getKey().toString())+";").append("\r\n");
        }
        sb.append("").append("\r\n");

        for(String serviceMethod : serviceMethodList){
            //#方法名;请求类型GET、POST等;路径参数列表逗号分割;是否有RequestBody;逻辑列表;
            String[] serviceMethod_strs = serviceMethod.split(";");
            String servicename = serviceMethod_strs[0];
            String method = serviceMethod_strs[1];
            String params = serviceMethod_strs[2];
            String hasBody = serviceMethod_strs[3];
            String logics_in_service = serviceMethod_strs[4];



            sb.append("    @ResponseBody").append("\r\n");
            sb.append("    @RequestMapping(value=\"/" + getName(servicename) + "");

            if(StringUtils.isNotEmpty(params)){
                for(String param:params.split(",")){
                    sb.append("/{"+param+"}");
                }
            }
            sb.append("\",method = RequestMethod."+method+")").append("\r\n");
            sb.append("    public HashMap "+getName(servicename)+"(");
            boolean has = false;
            if(StringUtils.isNotEmpty(params)){
                for(String param:params.split(",")){
                    if(has)sb.append(",");
                    sb.append("@PathVariable(\""+getName(param)+"\") String "+getName(param)+"");
                    has = true;
                }
            }
            if(hasBody.equalsIgnoreCase("hasBody")){
                if(has)sb.append(",");
                sb.append("@RequestBody String requestBody");
            }

            sb.append(") throws Exception {").append("\r\n");


            sb.append("        HashMap result = new HashMap();").append("\r\n");
            sb.append("        logger.info(\"" + className + "." + getName(servicename) + ".start\");").append("\r\n");
            if(StringUtils.isNotEmpty(params)){
                for(String param:params.split(",")){
                    sb.append("        result.put(\""+param+"\", "+param+");").append("\r\n");
                }
            }
            if(hasBody.equalsIgnoreCase("hasBody")){
                    sb.append("        result.put(\"requestBody\", requestBody);").append("\r\n");
            }

            for(Map.Entry e : logics.entrySet()){
                sb.append("        if(!ResultEnum.PartOK.equals(" + FormatUtils.firstSmall(e.getKey().toString()) + ".exec(result))){").append("\r\n");
                sb.append("            return result;").append("\r\n");
                sb.append("        }").append("\r\n");
            }
            sb.append("        result.put(\"resCode\",\"0000\");").append("\r\n");
            sb.append("        result.put(\"resDesc\",\"成功\");").append("\r\n");
            sb.append("        logger.info(\""+className+"."+servicename+".end\");").append("\r\n");
            sb.append("        return result;").append("\r\n");
            sb.append("    }").append("\r\n");


        }
        sb.append("}").append("\r\n");
        sb.append("").append("\r\n");
        //增加标签
        pw.println(sb.toString());
        pw.close();
    }

    private static void createLogic(Context c, Map.Entry<String,String> logic) throws Exception {
        //创建文件夹
        String logicName = logic.getKey();
        String logicDesc = logic.getValue();
        String logicPath = (String)c.get("logicPath");
        String logicPackage = (String)c.get("logicPackage");

        if(!FileUtils.mkdirs(logicPath)){
            throw new Exception("文件夹["+logicPath+"]创建不成功");
        }


        String className = FormatUtils.firstBig(logicName)+"";
        String fileName = className+".java";
        File entityFile = new File(logicPath + File.separator+fileName);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(entityFile),charset));

        //准备内容
        StringBuilder sb = new StringBuilder();
        sb.append("/**\r\n * SN Generator \r\n */\r\n");
        sb.append("package " + logicPackage +";").append("\r\n").append("\r\n");


        sb.append("import cn.com.xcommon.frame.logic.ResultEnum;").append("\r\n").append("\r\n");

        sb.append("/**").append("\r\n");
        sb.append(" * "+logicDesc).append("\r\n");
        sb.append(" */").append("\r\n");
        sb.append("public interface "+className+" {").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("    public ResultEnum exec(Object o) throws Exception;").append("\r\n");
        sb.append("}").append("\r\n");
        sb.append("").append("\r\n");
        //增加标签
        pw.println(sb.toString());
        pw.close();


    }


    private static void createLogicImpl(Context c, Map.Entry<String,String> logic) throws Exception {
        //创建文件夹
        String logicName = logic.getKey();
        String logicDesc = logic.getValue();
        String logicPath = (String)c.get("logicPath");
        String logicPackage = (String)c.get("logicPackage");
        String logicImplPath = (String)c.get("logicImplPath");
        String logicPackageImpl = (String)c.get("logicPackageImpl");

        if(!FileUtils.mkdirs(logicImplPath)){
            throw new Exception("文件夹["+logicImplPath+"]创建不成功");
        }

        String className = FormatUtils.firstBig(logicName)+"Impl";
        String fileName = className+".java";
        File entityFile = new File(logicImplPath + File.separator+fileName);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(entityFile),charset));

        //准备内容
        StringBuilder sb = new StringBuilder();
        sb.append("/**\r\n * SN Generator \r\n */\r\n");
        sb.append("package " + logicPackageImpl +";").append("\r\n").append("\r\n");


        sb.append("import cn.com.xcommon.frame.logger.HLogger;").append("\r\n");
        sb.append("import cn.com.xcommon.frame.logger.LoggerManager;").append("\r\n");
        sb.append("import cn.com.xcommon.frame.logic.ResultEnum;").append("\r\n");
        sb.append("import " + logicPackage + "." + FormatUtils.firstBig(logicName) + ";").append("\r\n");
        sb.append("import org.springframework.stereotype.Component;").append("\r\n");
        sb.append("import org.springframework.transaction.annotation.Propagation;").append("\r\n");
        sb.append("import org.springframework.transaction.annotation.Transactional;").append("\r\n");

        sb.append("/**").append("\r\n");
        sb.append(" * "+logicDesc).append("\r\n");
        sb.append(" */").append("\r\n");
        sb.append("@Component").append("\r\n");
        sb.append("@Transactional").append("\r\n");
        sb.append("public class "+className+" implements "+FormatUtils.firstBig(logicName)+"{").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("    protected HLogger logger = LoggerManager.getLoger(\""+className+"\");").append("\r\n");
        sb.append("").append("\r\n");

        sb.append("    @Override").append("\r\n");
        sb.append("    @Transactional(value=\"sn-txManager\",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)").append("\r\n");
        sb.append("    public ResultEnum exec(Object o) throws Exception {").append("\r\n");
        sb.append("        logger.info(\""+className+".exec.start\");").append("\r\n");
        sb.append("        logger.info(\""+className+".exec.end\");").append("\r\n");
        sb.append("        return ResultEnum.PartOK;").append("\r\n");
        sb.append("    }").append("\r\n");
        sb.append("}").append("\r\n");

        sb.append("").append("\r\n");
        //增加标签
        pw.println(sb.toString());
        pw.close();
    }


    public static String getName(String nameAll){
        return nameAll.contains("[")?nameAll.substring(0, nameAll.indexOf("[")):nameAll;
    }
    public static String getDesc(String nameAll){
        return nameAll.contains("[")?nameAll.substring(nameAll.indexOf("[")+1, nameAll.trim().length()-1):"";
    }

}
