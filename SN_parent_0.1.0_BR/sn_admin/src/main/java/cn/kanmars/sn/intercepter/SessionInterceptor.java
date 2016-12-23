package cn.kanmars.sn.intercepter;

import cn.com.sn.frame.cache.ApplicationCache;
import cn.com.sn.frame.util.CookiesUtils;
import cn.kanmars.sn.base.AdvancedUserLoginBean;
import cn.kanmars.sn.controller.UsersLoginController;
import cn.kanmars.sn.properties.sn_adminProperties;
import cn.kanmars.sn.util.DicCheckbox;
import cn.kanmars.sn.util.DicList;
import cn.kanmars.sn.util.DicSelect;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by baolong on 2016/1/18.
 */
@Service
public class SessionInterceptor implements HandlerInterceptor,ApplicationContextAware {

    protected static ApplicationContext ac ;
    private String[] allowUrls;
    public SessionInterceptor(){}

    @Autowired
    private sn_adminProperties sn_adminProperties;



    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        String[] userLoginBean = this.allowUrls;
        int goUrl = userLoginBean.length;

        for(int basePath = 0; basePath < goUrl; ++basePath) {
            String url = userLoginBean[basePath];
            if(uri.contains(url)) {
                return true;
            }
        }

        AdvancedUserLoginBean var9 = AdvancedUserLoginBean.CookiesUtils.getCookie(request, response);
        if(var9 == null) {
            String var11 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + sn_adminProperties.getLoginUrl();
            response.sendRedirect(var11);
            return false;
        } else {
            //如果刷新的是登录页面，则需要查询菜单权限信息
            if(uri.contains("/login/main.dhtml")||uri.contains("/debug/debug.dhtml")){
                UsersLoginController uc = (UsersLoginController)ac.getBean("usersLoginController");
                var9 = uc.queryAdvancedUserLoginBean(var9);
            }
            //session值刷新
            request.setAttribute(CookiesUtils.USER_LOGIN_BEAN, var9);
            request.getSession().setAttribute("user", var9);
            request.getSession().setAttribute("menuList", var9.getMenulist());
            request.getSession().setAttribute("dicSelect", DicSelect.getInstance());
            request.getSession().setAttribute("dicCheckbox", DicCheckbox.getInstance());
            request.getSession().setAttribute("dicList", DicList.getInstance());
            //重新设置过期时间，保证用户有操作则不过期，在存入cookie时，不储存menuList，因为menuList极大
            AdvancedUserLoginBean.CookiesUtils.addCookie(var9, response);
            return true;
        }
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public String[] getAllowUrls() {
        return this.allowUrls;
    }

    public void setAllowUrls(String[] allowUrls) {
        this.allowUrls = allowUrls;
    }

    public synchronized void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            ac = applicationContext;
    }
}
