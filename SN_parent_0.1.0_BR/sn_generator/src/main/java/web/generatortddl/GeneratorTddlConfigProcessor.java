package web.generatortddl;

import exec.GeneratorTddlConfig;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import utils.StringUtils;
import web.AbstractProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Created by baolong on 2016/9/10.
 */
public class GeneratorTddlConfigProcessor extends AbstractProcessor {
    @Override
    public void process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try{

            String projectName = httpServletRequest.getParameter("projectName");
            String groupNum = httpServletRequest.getParameter("groupNum");
            String readDbNum = httpServletRequest.getParameter("readDbNum");
            String defaultDbAddr = httpServletRequest.getParameter("defaultDbAddr");
            String defaultDbPort = httpServletRequest.getParameter("defaultDbPort");
            String minPoolSize = httpServletRequest.getParameter("minPoolSize");
            String maxPoolSize = httpServletRequest.getParameter("maxPoolSize");
            String username = httpServletRequest.getParameter("username");
            String password = httpServletRequest.getParameter("password");
            password = URLDecoder.decode(new String(new BASE64Decoder().decodeBuffer(password), "utf-8"), "utf-8");

            String result_str = GeneratorTddlConfig.exec(projectName,Integer.parseInt(groupNum),Integer.parseInt(readDbNum),defaultDbAddr,defaultDbPort,Integer.parseInt(minPoolSize),Integer.parseInt(maxPoolSize),username,password);
            JSONObject result = new JSONObject();
            result.put("resCode","0000");
            result.put("resDesc", result_str);
            try {
                httpServletResponse.getOutputStream().write(result.toString().getBytes("utf-8"));
                httpServletResponse.getOutputStream().flush();
                httpServletResponse.getOutputStream().close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject result = new JSONObject();
            result.put("resCode","0002");
            result.put("resDesc", e.getMessage());
            try {
                httpServletResponse.getOutputStream().write(result.toString().getBytes("utf-8"));
                httpServletResponse.getOutputStream().flush();
                httpServletResponse.getOutputStream().close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
