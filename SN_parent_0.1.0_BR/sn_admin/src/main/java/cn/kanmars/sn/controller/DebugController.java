package cn.kanmars.sn.controller;


import cn.com.xcommon.frame.util.StringUtils;
import cn.kanmars.sn.base.AdvancedAjaxBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by baolong on 2016/2/21.
 * 该类专用于单个页面进行单元测试。
 * 由于jquert load的debug较为困难，需要采用各种正规与非正规的手段才可以进行，
 * 因此创建了独立的debug页面，通过修改debug.ftl中的include地址，即可调试不同的页面
 */
@Controller
@RequestMapping("/debug")
public class DebugController extends AdvancedAjaxBaseController {
    @RequestMapping("/debug.dhtml")
    public String debug(Model model,HttpServletRequest request, HttpServletResponse response) {
        String debugPage = request.getParameter("debugPage");
        if(StringUtils.isNotEmpty(debugPage)){
            request.setAttribute("debugPath",debugPage);
        }
        return "debug/debug";
    }
}
