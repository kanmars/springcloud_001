package cn.kanmars.sn.base;

import cn.com.xcommon.commons.excel.ExcelTools;
import cn.com.xcommon.frame.controller.AjaxBaseController;
import cn.com.xcommon.frame.util.StringUtils;
import cn.kanmars.sn.util.PageQueryUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 具有导出功能的AjaxBaseController
 * Created by baolong on 2016/1/18.
 */
public class AdvancedAjaxBaseController extends AjaxBaseController {
    /**
     * 导出的分页处理
     * @param request
     * @param paramMap
     * @return
     */
    protected HashMap beforeExport(HttpServletRequest request,HashMap paramMap){
        String queryType = (String)paramMap.get("queryType");
        if(StringUtils.isNotEmpty(queryType)&&"download".equals(queryType)){
            paramMap.put("startIndex",1+"");
            paramMap.put("pageSize",Integer.MAX_VALUE+"");
        }
        return paramMap;
    }

    /**
     * 导出
     * @param request
     * @param response
     * @param resultMap
     * @throws IOException
     */
    protected void export(HttpServletRequest request, HttpServletResponse response,HashMap resultMap) throws IOException {
        String exportParam =(String)resultMap.get("exportParam");
        String fileName = (String)resultMap.get("fileName");
        List<String[]> exportParamList = new ArrayList<String[]>();
        List<List<String>> result = new ArrayList<List<String>>();
        List<String> title = new ArrayList<String>();
        for(String s : exportParam.split(",")){
            String[] ss = new String[2];
            ss[0] = s.split(":")[0];
            ss[1] = s.split(":")[1];
            title.add(ss[1]);
            exportParamList.add(ss);
        }
        //加入标题
        result.add(title);
        //加入数据体
        for(HashMap cell_map:(List<HashMap>)resultMap.get(PageQueryUtil.PAGERECORDS)){
            List<String> cell_list = new ArrayList<String>();
            for(String[] ss : exportParamList){
                cell_list.add(cell_map.get(ss[0]) != null ? cell_map.get(ss[0]).toString() : "");
            }
            result.add(cell_list);//将内容放入cell
        }
        //生成Excel
        HSSFWorkbook workBook = new ExcelTools().export(result);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workBook.write(os);
        byte[] bytes = os.toByteArray();
        response.setContentType("application/msexcel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename= " + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
