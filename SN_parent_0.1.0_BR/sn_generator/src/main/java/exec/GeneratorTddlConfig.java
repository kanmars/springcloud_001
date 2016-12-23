package exec;

import utils.FileUtils;
import utils.SecureIdentityLoginModule;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by zhaojiuyang on 2016/1/7.
 *说明：
 * 1，此类生成默认  四个组  每个组三个数据库   一共十二个数据库
 * 2，默认只能有一个写库  多个读库
 * 3，如果修改分组个数   或者  每个组数据库个数  需要修改  wIPsList  rIPsList（ r0IPsList  r1IPsList）
 * 4.修改数据密码  dbPassword = "123456" ;//数据库密码
 * 5，修改数据库名称 dbUuserName = "snuat" ;//数据库用户名
 * 6，修改生产文档路径 fileName = "D://TddlReadMe/" ;//文件路径
 * 7，修改分组名称前缀 groupPrefix = "group_";
 * 8，修改atom名称前缀 atomPrefix = "atom_";
 */
public class GeneratorTddlConfig {

    public static void main(String[] args) throws Exception {
        System.out.println(exec("SN", 4, 3, "snmain", "123456"));
    }



    public static String exec(String projectName,int groupNum,int readDbNum,String username,String password) throws Exception{
        return exec(projectName,groupNum,readDbNum+1,readDbNum,"192.168.0.x","3306",5,6,10,5,15,"characterEncoding=utf-8",username,password);
    }

    public static String exec(String projectName,int groupNum,int readDbNum,String defaultDbAddr,String defaultDbPort,int minPoolSize,int maxPoolSize,String username,String password) throws Exception{
        return exec(projectName,groupNum,readDbNum+1,readDbNum,defaultDbAddr,defaultDbPort,minPoolSize,maxPoolSize,10,5,15,"characterEncoding=utf-8",username,password);
    }

    /**
     *
     * @param projectName
     * @param groupNum
     * @param atomNum
     * @param readDbNum
     * @param defaultDbAddr
     * @param defaultDbPort
     * @param minPoolSize
     * @param maxPoolSize
     * @param idleTimeout（ app ，如果不配置时间单位，缺省为分钟）
     * @param blockingTimeout（ app ，如果不配置时间单位，缺省为分钟）
     * @param preparedStatementCacheSize
     * @param connectionProperties
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    public static String exec(String projectName,int groupNum,int atomNum,int readDbNum,String defaultDbAddr,String defaultDbPort,int minPoolSize,int maxPoolSize,int idleTimeout,int blockingTimeout,int preparedStatementCacheSize,String connectionProperties,String username,String password) throws Exception{
        if(groupNum<=0||atomNum<=0||minPoolSize<0||maxPoolSize<=0||idleTimeout<=0||blockingTimeout<=0||preparedStatementCacheSize<=0)throw new Exception("参数groupNum<=0||atomNum<=0||minPoolSize<0||maxPoolSize<=0||idleTimeout<=0||blockingTimeout<=0||preparedStatementCacheSize<=0");
        StringBuilder sb = new StringBuilder();
        sb.append("默认Diamond位置 http://diamond.sit.ds.XXXX.com.cn/diamond-server  abc/123").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("该文档描述了一个TDDL配置在Diamond中的配置过程").append("\r\n");
        sb.append("").append("\r\n");
        sb.append("###############################################################################").append("\r\n");
        sb.append("# matrix配置信息").append("\r\n");
        sb.append("###############################################################################").append("\r\n");
        sb.append("dataId=com.taobao.tddl.v1_"+projectName+"_dbgroups").append("\r\n");
        sb.append("groupId=DEFAULT_GROUP").append("\r\n");
        sb.append("context=");
        for(int i=0;i<groupNum;i++){
            if(i!=0)sb.append(",");
            sb.append("group_"+projectName+"_"+i);
        }
        sb.append("").append("\r\n");
        sb.append("###############################################################################").append("\r\n");
        sb.append("# group[0-"+groupNum+"]配置信息").append("\r\n");
        sb.append("###############################################################################").append("\r\n");
        for(int i=0;i<groupNum;i++){
            sb.append("# group_"+projectName+"_"+i).append("\r\n");
            sb.append("dataId=com.taobao.tddl.jdbc.group_V2.4.1_group_"+projectName+"_"+i).append("\r\n");
            sb.append("groupId=DEFAULT_GROUP").append("\r\n");
            sb.append("context=");
            for(int j=0;j<atomNum;j++){
                if(j==0){
                    sb.append("atom_" + projectName + "_"+j+":r0w10");
                }else{
                    sb.append(",atom_" + projectName + "_read_"+j+":r10w0");
                }
            }
            sb.append("").append("\r\n");
        }
        sb.append("").append("\r\n");
        sb.append("###############################################################################").append("\r\n");
        sb.append("# atom[0-"+(groupNum*(atomNum))+"]配置信息").append("\r\n");
        sb.append("###############################################################################").append("\r\n");
        for(int i=0;i<groupNum;i++){//每个group有一个atom作为主写库，因此此处循环groupNum即为循环主库atom数量
            sb.append("# atom_"+projectName+"_"+i).append("\r\n");
            sb.append("dataId=com.taobao.tddl.atom.global.atom_"+projectName+"_"+i).append("\r\n");
            sb.append("groupId=DEFAULT_GROUP").append("\r\n");
            sb.append("context=ip=" + defaultDbAddr).append("\r\n");
            sb.append("port=" + defaultDbPort).append("\r\n");
            sb.append("dbName=atom_"+projectName+"_"+i).append("\r\n");
            sb.append("dbType=mysql").append("\r\n");
            sb.append("dbStatus=RW").append("\r\n");
            for(int j=0;j<readDbNum;j++){
                sb.append("").append("\r\n");
                sb.append("# atom_"+projectName+"_"+i+"_read_"+j).append("\r\n");
                sb.append("dataId=com.taobao.tddl.atom.global.atom_"+projectName+"_"+i+"_read_"+j).append("\r\n");
                sb.append("groupId=DEFAULT_GROUP").append("\r\n");
                sb.append("context=ip=" + defaultDbAddr).append("\r\n");
                sb.append("port=" + defaultDbPort).append("\r\n");
                sb.append("dbName=atom_"+projectName+"_"+i+"_read_"+j).append("\r\n");
                sb.append("dbType=mysql").append("\r\n");
                sb.append("dbStatus=RW").append("\r\n");
            }
            sb.append("").append("\r\n");
        }

        sb.append("###############################################################################").append("\r\n");
        sb.append("# atom[0-"+(groupNum*(atomNum))+"]AppConfig连接参数配置").append("\r\n");
        sb.append("###############################################################################").append("\r\n");
        for(int i=0;i<groupNum;i++){//每个group有一个atom作为主写库，因此此处循环groupNum即为循环主库atom数量
            sb.append("# atom_"+projectName+"_"+i).append("\r\n");
            sb.append("dataId=com.taobao.tddl.atom.app."+projectName+".atom_"+projectName+"_"+i).append("\r\n");
            sb.append("groupId=DEFAULT_GROUP").append("\r\n");
            sb.append("context=userName="+username).append("\r\n");
            sb.append("minPoolSize="+minPoolSize).append("\r\n");
            sb.append("maxPoolSize="+maxPoolSize).append("\r\n");
            sb.append("idleTimeout="+idleTimeout).append("\r\n");
            sb.append("blockingTimeout="+blockingTimeout).append("\r\n");
            sb.append("preparedStatementCacheSize="+preparedStatementCacheSize).append("\r\n");
            sb.append("connectionProperties="+connectionProperties).append("\r\n");
            for(int j=0;j<readDbNum;j++){
                sb.append("").append("\r\n");
                sb.append("# atom_"+projectName+"_"+i+"_read_"+j).append("\r\n");
                sb.append("dataId=com.taobao.tddl.atom.app."+projectName+".atom_"+projectName+"_"+i+"_read_"+j).append("\r\n");
                sb.append("groupId=DEFAULT_GROUP").append("\r\n");
                sb.append("context=userName="+username).append("\r\n");
                sb.append("minPoolSize="+minPoolSize).append("\r\n");
                sb.append("maxPoolSize="+maxPoolSize).append("\r\n");
                sb.append("idleTimeout="+idleTimeout).append("\r\n");
                sb.append("blockingTimeout="+blockingTimeout).append("\r\n");
                sb.append("preparedStatementCacheSize="+preparedStatementCacheSize).append("\r\n");
                sb.append("connectionProperties="+connectionProperties).append("\r\n");
            }
            sb.append("").append("\r\n");

        }


        sb.append("###############################################################################").append("\r\n");
        sb.append("# atom[0-"+(groupNum*(atomNum))+"]passwordConfig连接密码配置").append("\r\n");
        sb.append("###############################################################################").append("\r\n");
        for(int i=0;i<groupNum;i++){//每个group有一个atom作为主写库，因此此处循环groupNum即为循环主库atom数量
            sb.append("# atom_"+projectName+"_"+i).append("\r\n");
            sb.append("dataId=com.taobao.tddl.atom.passwd.atom_"+projectName+"_"+i+".mysql."+username).append("\r\n");
            sb.append("groupId=DEFAULT_GROUP").append("\r\n");
            sb.append("context=encPasswd="+SecureIdentityLoginModule.encode(password)).append("\r\n");
            for(int j=0;j<readDbNum;j++){
                sb.append("").append("\r\n");
                sb.append("# atom_"+projectName+"_"+i+"_read_"+j).append("\r\n");
                sb.append("dataId=com.taobao.tddl.atom.passwd.atom_"+projectName+"_"+i+"_read_"+j+".mysql."+username).append("\r\n");
                sb.append("groupId=DEFAULT_GROUP").append("\r\n");
                sb.append("context=encPasswd="+SecureIdentityLoginModule.encode(password)).append("\r\n");
            }
            sb.append("").append("\r\n");

        }


        sb.append("").append("\r\n");
        sb.append("").append("\r\n");






        sb.append("").append("\r\n");

        return sb.toString();
    }

}
