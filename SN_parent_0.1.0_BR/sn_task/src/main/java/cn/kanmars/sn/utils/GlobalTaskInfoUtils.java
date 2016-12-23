package cn.kanmars.sn.utils;

import cn.com.xcommon.frame.cache.ApplicationCache;
import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.com.xcommon.frame.util.DateFormatUtils;
import cn.com.xcommon.frame.util.StringUtils;
import cn.kanmars.sn.cache.SystemConfigInfoCache;
import cn.kanmars.sn.service.TaskListenerService;
import net.sf.json.JSONObject;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by baolong on 2016/4/14.
 */
public class GlobalTaskInfoUtils {

    public static final HLogger logger = LoggerManager.getLoger("GlobalTaskInfoUtils");

    private volatile boolean inited = false;

    private static ZkClient zkClient;

    private static String zookeeperGroup = BasisConstants.ZOOKEEPER_DEFAULT_GROUP;

    @Autowired
    private TaskListenerService taskListenerService;

    public synchronized void init(){
        String registerFlg = BasisConstants.registerFlg;
        if("true".equals(registerFlg)&&zkClient==null){
            String registerAddr = BasisConstants.registerAddr;
            String registerGroup = BasisConstants.registerGroup;
            zookeeperGroup = registerGroup;
            //2创建注册核心对象
            Map<String,ConcurrentHashMap<String,Object>> kernel = SystemConfigInfoCache.getTaskInnerInfos();
            //3创建远程链接
            zkClient = new ZkClient(registerAddr);
        }
    }

    public String getGlobalTaskInfo(){
        return getGlobalTaskInfo(null, null, null);
    }

    public String getGlobalTaskInfo(String paixuType,String searchStr,String ascFlg){
        init();
        StringBuilder sb = new StringBuilder();
        sb.append("<table>\r\n");
        sb.append("<thead><tr><td>任务名称</td><td>分组</td><td>全局名称</td><td>全局序号</td><td>最近执行时间</td><td>应执行</td><td>成功</td><td>历史记录</td>"+(BasisConstants.mqNotifyFlg.equals("true")?"<td>手工触发</td>":"")+"</tr></thead>\r\n");
        sb.append("<tbody>\r\n");
        try{

            List<Object[]> formatData_ = (List<Object[]>) ApplicationCache.getInstance().get("GLOBALTASK_INFO_CACHE");
            if(formatData_==null){
                formatData_ = getFormatData();
                ApplicationCache.getInstance().put("GLOBALTASK_INFO_CACHE", formatData_,3);
            }

            //过滤
            List<Object[]> formatData = new ArrayList<Object[]>();
            if(StringUtils.isEmpty(searchStr)){
                formatData.addAll(formatData_);
            }else{
                String searchStr_decode = URLDecoder.decode(searchStr,"utf-8");
                String[] searchStr_decode_s = searchStr_decode.split(",");
                for(String ss:searchStr_decode_s){
                    o_s_label:for(Object[] o_s : formatData_){
                        for(Object o:o_s){
                            if(o!=null&&o.toString().contains(ss)){
                                formatData.add(o_s);
                                continue o_s_label;
                            }
                        }
                    }
                }
            }
            //排序
            final List<Integer> sortList = new ArrayList<Integer>();
            if(StringUtils.isEmpty(paixuType)||paixuType.equals("1")){
                sortList.add(0);sortList.add(1);sortList.add(2);sortList.add(3);
            }else if(paixuType.equals("2")){
                sortList.add(2);
            }else if(paixuType.equals("3")){
                sortList.add(8);//IP
            }else if(paixuType.equals("4")){
                sortList.add(3);
            }else if(paixuType.equals("5")){
                sortList.add(4);
            }
            //正序或者倒序
            final List<Boolean> ascList = new ArrayList<Boolean>();
            if(StringUtils.isEmpty(ascFlg)||ascFlg.equals("1")){
                ascList.add(true);
            }else if(ascFlg.equals("2")){
                ascList.add(false);
            }else{
                ascList.add(false);
            }
            Collections.sort(formatData, new Comparator<Object[]>() {
                public int compare(Object[] o1, Object[] o2) {
                    Boolean ascFlg = ascList.get(0);
                    for(int i:sortList){
                        if(((Comparable)o1[i]).compareTo(((Comparable)o2[i]))!=0){
                            if(ascFlg){
                                return ((Comparable)o1[i]).compareTo(((Comparable) o2[i]));
                            }else{
                                return 0-((Comparable)o1[i]).compareTo(((Comparable) o2[i]));
                            }
                        }
                    }
                    return 0;
                }
            });

            for(Object[] data:formatData){
                StringBuilder tr = new StringBuilder();
                tr.append("<tr>");
                tr.append("<td>"+data[0]+"</td>");
                tr.append("<td>"+data[1]+"</td>");
                tr.append("<td>"+data[2]+"</td>");
                tr.append("<td>"+data[3]+"</td>");
                tr.append("<td>"+data[4] +"</td>");
                tr.append("<td>"+data[5]+"</td>");
                tr.append("<td>"+data[6]+"</td>");
                tr.append("<td><button onclick='showHistory_"+data[2].toString().replaceAll("\\.", "_").replaceAll("-", "_")+"()'>查看</button></td>");
                if(BasisConstants.mqNotifyFlg.equals("true")){
                    tr.append("<td><input class=\"tdiput\" value=\"\"/><button onclick='taskTriger_"+data[2].toString().replaceAll("\\.", "_").replaceAll("-", "_")+"(this)'>手工触发</button></td>");
                }
                tr.append("</tr>\r\n");
                tr.append("<script>\r\n");
                tr.append("    function showHistory_"+data[2].toString().replaceAll("\\.", "_").replaceAll("-", "_")+"() { \r\n");
                tr.append("        alert('任务执行历史信息如下：\\r\\n");
                for(Object o : (List)data[7]){
                    String execTime_his = "";
                    String planCount_his = "";
                    String execCount_his = "";
                    String execUseTime_his = "";
                    String perAvgExecUseTime_his = "";
                    try{
                        execTime_his = (String)((Map)o).get("execTime");
                        execTime_his = DateFormatUtils.format(execTime_his, "yyyyMMddHHmmss", "yyyyMMddHHmmss_");
                        planCount_his = (String)((Map)o).get("planCount");
                        execCount_his = (String)((Map)o).get("execCount");
                        execUseTime_his = (String)((Map)o).get("execUseTime");
                        perAvgExecUseTime_his = (String)((Map)o).get("perAvgExecUseTime");
                        if(execUseTime_his==null)execUseTime_his="";
                        if(perAvgExecUseTime_his==null)perAvgExecUseTime_his="";
                    }catch (Exception e){
                        logger.error("历史数据获取异常，可忽略", e);
                    }
                    tr.append(""+execTime_his+"  " );
                    tr.append("应执行:"+StringUtils.fillString(planCount_his,' ',5,false)+" " );
                    tr.append("成功:"+StringUtils.fillString(execCount_his,' ',5,false)+" " );
                    tr.append("耗时:"+StringUtils.fillString(execUseTime_his,' ',3,false)+" " );
                    tr.append("平均:"+StringUtils.fillString(perAvgExecUseTime_his,' ',3,false)+"  \\r\\n" );
                }
                tr.append("')\r\n");
                tr.append("    }\r\n");
                if(BasisConstants.mqNotifyFlg.equals("true")) {
                    tr.append("    function taskTriger_" + data[2].toString().replaceAll("\\.", "_").replaceAll("-", "_") + "(obj) { \r\n");
                    tr.append("        var msg = $(obj).parent().find(\"input\").val();\r\n");
                    tr.append("        var globalIndex_of_businessObjName_group = $(obj).parent().parent().find(\"td\").eq(3).text();\r\n");
                    tr.append("        var globalName_of_businessObjName_group = $(obj).parent().parent().find(\"td\").eq(2).text();\r\n");
                    tr.append("        msg = encodeURIComponent(encodeURIComponent(msg)) \r\n");
                    tr.append("        globalName_of_businessObjName_group = encodeURIComponent(encodeURIComponent(globalName_of_businessObjName_group)) \r\n");
                    tr.append("        $.ajax({\r\n");
                    tr.append("            type: \"POST\",\r\n");
                    tr.append("            url: \"/?type=taskTriger&msg=\"+msg+\"&globalTaskName=\"+globalName_of_businessObjName_group+\"&globalIndex=\"+globalIndex_of_businessObjName_group,\r\n");
                    tr.append("            dataType: \"json\",\r\n");
                    tr.append("            success: function(data){\r\n");
                    tr.append("                if(data.resCode == \"0000\"){\r\n");
                    tr.append("                    alert(\"触发成功\");\r\n");
                    tr.append("                } else {\r\n");
                    tr.append("                    alert(\"触发失败，失败原因为:[\"+data.resDesc+\"]\");\r\n");
                    tr.append("                }\r\n");
                    tr.append("            }\r\n");
                    tr.append("        });\r\n");
                    tr.append("    }\r\n");
                }
                tr.append("</script>\r\n");
                sb.append(tr);
            }
        }catch(Exception e){
            logger.error("生成页面异常",e);
        }
        sb.append("</tbody>\r\n");
        sb.append("</table>");
        return sb.toString();
    }

    public List<Object[]> getFormatData(){
        List<Object[]> result = new ArrayList<Object[]>();
        if(BasisConstants.registerFlg.equalsIgnoreCase("true")){
            //如果是存放在zookeeper中的，则从zookeeper中获取
            try{
                List<String> businessObjNames = zkClient.getChildren(BasisConstants.ZOOKEEPER_BASE_PATH);
                Collections.sort(businessObjNames);
                for(String businessObjName : businessObjNames){
                    List<String> groups = zkClient.getChildren(BasisConstants.ZOOKEEPER_BASE_PATH+"/"+businessObjName);
                    Collections.sort(groups);
                    for(String group : groups){
                        List<String> GLOBALTASK_NAMES = zkClient.getChildren(BasisConstants.ZOOKEEPER_BASE_PATH+"/"+businessObjName+"/"+group);
                        Collections.sort(GLOBALTASK_NAMES);
                        int globalIndex_of_businessObjName_group = 0;
                        for(String GLOBALTASK_NAME:GLOBALTASK_NAMES){
                            try{
                                String execTime = "";
                                String planCount = "";
                                String execCount = "";
                                List execHistory = new ArrayList();
                                try{
                                    String data = zkClient.readData(BasisConstants.ZOOKEEPER_BASE_PATH+"/"+businessObjName+"/"+group+"/"+GLOBALTASK_NAME);
                                    JSONObject json = JSONObject.fromObject(data);
                                    execTime = json.getString("execTime");
                                    execTime = DateFormatUtils.format(execTime, "yyyyMMddHHmmss", "yyyyMMddHHmmssC");
                                    planCount = json.getString("planCount");
                                    execCount = json.getString("execCount");
                                    execHistory = json.getJSONArray("execHistory");
                                }catch (Exception e){
                                    logger.error("历史数据获取异常，可忽略", e);
                                }
                                Object[] cell = new Object[16];
                                cell[0]=businessObjName;
                                cell[1]=group;
                                cell[2]=GLOBALTASK_NAME;
                                cell[3]=globalIndex_of_businessObjName_group+"";
                                cell[4]=execTime;
                                cell[5]=planCount;
                                cell[6]=execCount;
                                cell[7]=execHistory;
                                cell[8]=GLOBALTASK_NAME.substring(GLOBALTASK_NAME.indexOf("_IP")+3,GLOBALTASK_NAME.indexOf("_PID"));
                                globalIndex_of_businessObjName_group++;
                                result.add(cell);
                            }catch (Exception e){
                                logger.error("生成格式化数据异常",e);
                            }
                        }
                    }
                }
            }catch(Exception e){
                logger.error("生成格式化数据异常",e);
            }
        }
        return result;
    }

    public boolean isInited() {
        return inited;
    }

    public void setIsInited(boolean inited) {
        this.inited = inited;
    }

    public static ZkClient getZkClient() {
        return zkClient;
    }

    public static void setZkClient(ZkClient zkClient) {
        GlobalTaskInfoUtils.zkClient = zkClient;
    }

    public static String getZookeeperGroup() {
        return zookeeperGroup;
    }

    public static void setZookeeperGroup(String zookeeperGroup) {
        GlobalTaskInfoUtils.zookeeperGroup = zookeeperGroup;
    }

    public TaskListenerService getTaskListenerService() {
        return taskListenerService;
    }

    public void setTaskListenerService(TaskListenerService taskListenerService) {
        this.taskListenerService = taskListenerService;
    }

    public static void main(String[] args) {
        GlobalTaskInfoUtils g = new GlobalTaskInfoUtils();
        logger.info(g.getGlobalTaskInfo());
    }
}
