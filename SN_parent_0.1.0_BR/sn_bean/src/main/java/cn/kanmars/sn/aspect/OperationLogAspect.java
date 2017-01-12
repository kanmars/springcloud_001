package cn.kanmars.sn.aspect;

import cn.com.xcommon.frame.exception.SnCommonException;
import cn.com.xcommon.frame.interceptor.OperationLogDescription;
import cn.com.xcommon.frame.interceptor.UserLoginBean;
import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.com.xcommon.frame.util.DateUtils;
import cn.com.xcommon.frame.util.StringUtils;
import cn.kanmars.sn.dao.TblOperationLogMapper;
import cn.kanmars.sn.entity.TblOperationLog;
import cn.kanmars.sn.util.SysSequenceUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by baolong on 2017/1/9.
 * 适用场景，有数据库链接的，需要记录日志的情况
 */
@Aspect
@Order(-20)//在事务前执行，即无事务方式
public class OperationLogAspect {
    private HLogger logger = LoggerManager.getLoger("SnLogAspect");

    @Autowired
    private TblOperationLogMapper tblOperationLogMapper;

    @Before("@annotation(operationLogDescription)")
    public void recordLog(JoinPoint joinPoint,OperationLogDescription operationLogDescription) throws Throwable {
        try{
            Object[] args = joinPoint.getArgs();
            //0、构建TblOperationLog操作信息
            TblOperationLog tblOperationLog = new TblOperationLog();
            //1、设置随机序号
            try {
                tblOperationLog.setOperationId(Integer.parseInt(SysSequenceUtils.generateStringId("tbl_operation_id")));
            } catch (SnCommonException e) {
                e.printStackTrace();
                tblOperationLog.setOperationId((int) (long) SysSequenceUtils.createSimpleId());
            }
            //2、获取操作者信息
            if(StringUtils.isEmpty(operationLogDescription.operationUser())){
                ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if(sra!=null){
                    HttpServletRequest request = sra.getRequest();
                    HttpServletResponse response = sra.getResponse();
                    if(request!=null){
                        UserLoginBean userLoginBean = UserLoginBean.CookiesUtils.getCookie(request,response);//尝试从request中获取
                        if(userLoginBean!=null){
                            tblOperationLog.setOperationUser(userLoginBean.getUserName()+"_"+userLoginBean.getUserId()+"_"+getIpAddr(request));
                        }else{
                            tblOperationLog.setOperationUser("未知用户");
                        }
                    }
                }
            }else{
                tblOperationLog.setOperationUser(operationLogDescription.operationUser());
            }
            //3、设置其他操作信息
            String datetime = DateUtils.getCurrDateTime();
            tblOperationLog.setOperationTime(datetime);
            tblOperationLog.setOperationName(operationLogDescription.operationName());
            tblOperationLog.setOperationDesc(operationLogDescription.operationDesc());
            tblOperationLog.setOperationApp(operationLogDescription.operationApp());
            //4、获取方法与签名信息
            if(StringUtils.isEmpty(operationLogDescription.operationClassmethod())){
                tblOperationLog.setOperationClassmethod(joinPoint.getSignature().toString());
            }else{
                tblOperationLog.setOperationClassmethod(operationLogDescription.operationClassmethod());
            }
            //5、将参数转化为操作信息
            if(StringUtils.isEmpty(operationLogDescription.operationInfo())){
                tblOperationLog.setOperationInfo(buildOperationInfoByArgs(args,1800));
            }else{
                tblOperationLog.setOperationInfo(operationLogDescription.operationInfo());
            }
            tblOperationLog.setCreateTime(datetime);
            tblOperationLog.setUpdateTime(datetime);
            int i = tblOperationLogMapper.insert(tblOperationLog);
        }catch (Exception e){
            logger.error("日志系统记录异常", e);
        }
    }

    public  String getIpAddr(HttpServletRequest request)  {
        String ip  =  request.getHeader("X-Forwarded-For");
        if (ip  ==   null   ||  ip.length()  ==   0   ||   "unknown" .equalsIgnoreCase(ip))  {
            ip  =  request.getHeader("Proxy-Client-IP");
        }
        if (ip  ==   null   ||  ip.length()  ==   0   ||   "unknown" .equalsIgnoreCase(ip))  {
            ip  =  request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip  ==   null   ||  ip.length()  ==   0   ||   "unknown" .equalsIgnoreCase(ip))  {
            ip  =  request.getRemoteAddr();
        }
        return  ip;
    }

    public String buildOperationInfoByArgs(Object[] args,int length){
        HashMap operationInfo = new HashMap();
        for(int i=0;i<args.length;i++){
            if(args[i]==null){
                operationInfo.put(i + "", "null");
                continue;
            }
            if(args[i] instanceof HttpServletRequest){
                operationInfo.put(i + "", transHttprequest2Map((HttpServletRequest)args[i]));
                continue;
            }
            if(args[i] instanceof HttpServletResponse){
                operationInfo.put(i + "", args[i].toString());
                continue;
            }
            try{
                operationInfo.put(i + "", JSONObject.fromObject(args[i]).toString());
            }catch (Exception eo){
                try{
                    operationInfo.put(i + "", JSONArray.fromObject(args[i]).toString());
                }catch (Exception ea){
                    operationInfo.put(i + "", args[i].toString());
                }
            }
        }

        String result = JSONObject.fromObject(operationInfo).toString();
        if(result.length()>=length){
            result = result.substring(0,length);
        }else{
            result = result;
        }
        //此处加一个_的目的是为了防止数据库中储存的是JsonObject，在页面显示为[Object Object]，无其他用途
        return "_"+result;
    }

    /**
     * 将request转化为String
     * @param request
     * @return
     */
    public String transHttprequest2Map(HttpServletRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append(request.getMethod());
        sb.append(" ");
        sb.append(request.getRequestURI());
        sb.append("\r\n");
        Map<String,String[]> m =  request.getParameterMap();
        Map<String,String> parameterMap = new HashMap<String, String>();
        for(Map.Entry<String,String[]> e : m.entrySet()){
            StringBuilder sb2 = new StringBuilder();
            for(String s : e.getValue()){
                if(sb2.length()!=0)sb2.append(",");
                sb2.append(s);
            }
            parameterMap.put(e.getKey(), sb2.toString());
        }
        sb.append(JSONObject.fromObject(parameterMap).toString());

        return sb.toString();
    }
}
