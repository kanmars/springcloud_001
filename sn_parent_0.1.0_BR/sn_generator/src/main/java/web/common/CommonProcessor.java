package web.common;

import utils.StringUtils;
import web.AbstractProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by baolong on 2016/8/23.
 */
public class CommonProcessor extends AbstractProcessor {
    @Override
    public void process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String url = httpServletRequest.getRequestURI();
        String path = url;
        if(url.equals("/")|| StringUtils.isEmpty(url)||url.equals("/common")||url.equals("/common/")||url.startsWith("/common?")||url.startsWith("/common/?")){
            path="/common/index.html";
        }
        if(url.equals("/favicon.ico")){
            return;
        }
        String absolutePath = CommonProcessor.class.getResource(path).getPath();
        File file = new File(absolutePath);
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            byte[] temps = new byte[512];
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            int count =0;
            while((count = is.read(temps))>=0){
                bao.write(temps,0,count);
            }
            httpServletResponse.getOutputStream().write(bao.toByteArray());
            httpServletResponse.getOutputStream().flush();
            httpServletResponse.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
