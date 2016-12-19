package web.generatorservice;

import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import utils.StringUtils;
import web.AbstractProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Created by baolong on 2016/12/19.
 */
public class GeneratorService  extends AbstractProcessor {
    @Override
    public void process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try{
            String basePath = httpServletRequest.getParameter("basePath");
            try{
                basePath = URLDecoder.decode(new String(new BASE64Decoder().decodeBuffer(basePath),"utf-8"));
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("basePath不正确");
            }
            String serviceName = httpServletRequest.getParameter("serviceName");
            String serviceDesc = httpServletRequest.getParameter("serviceDesc");
            String buildproperties = httpServletRequest.getParameter("buildproperties");
            try{
                buildproperties = URLDecoder.decode(new String(new BASE64Decoder().decodeBuffer(buildproperties)), "utf-8");
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("buildproperties不正确");
            }

            exec.GeneratorService g = new exec.GeneratorService();
            final String finalbuildproperties = buildproperties;
            g.exec(basePath,serviceName,serviceDesc,new ArrayList<String>(){{
                for(String service : finalbuildproperties.split("\n")){
                    service = service.trim();
                    if(StringUtils.isNotEmpty(service)&&!service.trim().startsWith("#")){
                        this.add(service);
                    }
                }
            }});

            JSONObject result = new JSONObject();
            result.put("resCode","0000");
            result.put("resDesc", "成功");
            try {
                httpServletResponse.getOutputStream().write(result.toString().getBytes("utf-8"));
                httpServletResponse.getOutputStream().flush();
                httpServletResponse.getOutputStream().close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }catch (Exception e){
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
