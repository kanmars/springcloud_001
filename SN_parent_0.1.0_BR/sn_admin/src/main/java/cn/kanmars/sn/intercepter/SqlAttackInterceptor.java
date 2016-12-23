package cn.kanmars.sn.intercepter;

import cn.com.xcommon.frame.cache.ApplicationCache;
import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.kanmars.sn.properties.sn_adminProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import cn.kanmars.sn.properties.*;

/**
 * Created by baolong on 2016/2/20.
 */
public class SqlAttackInterceptor  implements HandlerInterceptor {

    protected HLogger logger = LoggerManager.getLoger("SqlAttackInterceptor");

    private String[] sqlAttackSql;

    public void setSqlAttackSql(String[] sqlAttackSql) {
        this.sqlAttackSql = sqlAttackSql;
    }

    public String[] getSqlAttackSql() {
        return sqlAttackSql;
    }

    @Autowired
    private sn_adminProperties sn_adminProperties;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(sqlAttackSql == null){
            return true;
        }

        for(Map.Entry<String,String[]> e : (Set<Map.Entry<String,String[]>>)request.getParameterMap().entrySet()){
            String key = e.getKey();
            String[] value = e.getValue();
            if(key!=null){
                for(String sqlAttack:sqlAttackSql){
                    if(key.contains(sqlAttack)){
                        logger.info("SQL攻击被拦截:来源[参数名称="+key+"][参数值="+sL2S(value)+"],涉嫌攻击的字符串[sqlAttack="+sqlAttack+"]");
                        String var11 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + sn_adminProperties.getLoginUrl();
                        response.sendRedirect(var11);
                        return false;
                    }
                }
            }
            if(value!=null){
                for(String sqlAttack:sqlAttackSql){
                    for(String oneValue:value){
                        if(oneValue.contains(sqlAttack)){
                            logger.info("SQL攻击被拦截:来源[参数名称="+key+"][参数值="+sL2S(value)+"],涉嫌攻击的字符串[sqlAttack="+sqlAttack+"]");
                            String var11 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + sn_adminProperties.getLoginUrl();
                            response.sendRedirect(var11);
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public static Object sL2S(Object ss){
        String result = "";
        if(ss != null && ss instanceof   String[] ){
            for(String s : (String[])ss){
                result +=s;
            }
        }
        return result;
    }
}
