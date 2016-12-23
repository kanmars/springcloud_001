package web;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import web.common.CommonProcessor;
import web.generator.GeneratorProcessor;
import web.generatorservice.GeneratorService;
import web.generatortddl.GeneratorTddlConfigProcessor;
import web.mover.MoverProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by baolong on 2016/8/23.
 */
public class PathHandler extends AbstractHandler {

    private GeneratorProcessor generatorProcessor = new GeneratorProcessor();

    private CommonProcessor commonProcessor = new CommonProcessor();

    private GeneratorTddlConfigProcessor generatorTddlConfigProcessor = new GeneratorTddlConfigProcessor();

    private MoverProcessor moverProcessor = new MoverProcessor();

    private GeneratorService generatorService = new GeneratorService();

    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        if(s.startsWith("/generatortddl")){
            generatorTddlConfigProcessor.process(httpServletRequest,httpServletResponse);
        }else if (s.startsWith("/generatorservice")){
            generatorService.process(httpServletRequest,httpServletResponse);
        }else if(s.startsWith("/generator")){
            generatorProcessor.process(httpServletRequest,httpServletResponse);
        }else if(s.startsWith("/common")){
            commonProcessor.process(httpServletRequest,httpServletResponse);
        }else if (s.startsWith("/mover")){
            moverProcessor.process(httpServletRequest,httpServletResponse);
        }else{
            commonProcessor.process(httpServletRequest,httpServletResponse);
        }
    }
}
