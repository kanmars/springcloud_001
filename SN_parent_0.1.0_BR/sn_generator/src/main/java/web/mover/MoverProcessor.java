package web.mover;

import exec.Mover;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import utils.StringUtils;
import web.AbstractProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Created by baolong on 2016/8/26.
 */
public class MoverProcessor extends AbstractProcessor {
    @Override
    public void process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            String srcDir = httpServletRequest.getParameter("srcDir");
            try{
                srcDir = URLDecoder.decode(new String(new BASE64Decoder().decodeBuffer(srcDir), "utf-8"));
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("srcDir不正确");
            }

            String dstDir = httpServletRequest.getParameter("dstDir");
            try{
                dstDir = URLDecoder.decode(new String(new BASE64Decoder().decodeBuffer(dstDir), "utf-8"));
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("dstDir不正确");
            }

            if(new File(dstDir).exists()){
                throw new RuntimeException("dstDir["+dstDir+"]已存在，请保证目标路径不存在");
            }

            String replaceList = httpServletRequest.getParameter("replaceList");
            try{
                replaceList = URLDecoder.decode(new String(new BASE64Decoder().decodeBuffer(replaceList), "utf-8"));
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("replaceList不正确");
            }

            Mover m = new Mover();
            final String finalReplaceList = replaceList;
            m.exec(srcDir,dstDir,new ArrayList<String>(){{
                for(String replaceRule : finalReplaceList.split("\n")){
                    replaceRule = replaceRule.trim();
                    if(StringUtils.isNotEmpty(replaceRule)){
                        this.add(replaceRule);
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
