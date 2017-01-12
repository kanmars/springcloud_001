package cn.kanmars.sn.aspect;

import cn.com.xcommon.frame.logger.ExPatternParser;
import cn.com.xcommon.frame.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求跟踪切面，从请求中获取到head域中的REQUESTTRACK信息，然后存入本地线程号，处理完毕后移除
 * 使用场景sn-service中获取到请求，将请求中的REQUESTTRACK信息设置入线程中
 * Created by baolong on 2017/1/10.
 */
@Aspect
@Order(-100)//在所有操作之前执行日志清理
public class RequestTrackAspect {

    public static final String REQUESTTRACK="REQUESTTRACK";

    @Pointcut("execution(public * cn.kanmars.sn.service..*.*(..))")
    public Object serviceMethod() {
        return null;
    }

    @Around("serviceMethod()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //1.设置线程号
        //1.1保存旧的线程号
        String pre_threadNumber = ExPatternParser.ThreadNumber.getThreadNumber();
        //1.2尝试从Request中获取业务串联线程号并设置入日志模块，此线程号由RequestTrackInterceptor设置完毕
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        if(request!=null){
            String threadNumberInHeader = request.getHeader(RequestTrackAspect.REQUESTTRACK);
            if(StringUtils.isNotEmpty(threadNumberInHeader)){
                ExPatternParser.ThreadNumber.setThreadNumber(threadNumberInHeader);
            }
        }
        //1.3执行业务代码
        //此处直接抛出异常，但一般情况不会出现，因为在Order=-10的ServiceFacadeAspect中已经全部trycatch
        Object returnValue = proceedingJoinPoint.proceed();

        //1.4恢复之前的线程号
        ExPatternParser.ThreadNumber.setThreadNumber(pre_threadNumber);

        return returnValue;
    }
}
