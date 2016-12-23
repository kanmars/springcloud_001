package cn.kanmars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by kanmars on 2016/12/4.
 */
@Controller
public class MainController {
    @RequestMapping("/")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("title","SPRING BOOT WEB");
        mv.addObject("wellcome","欢迎使用SPRING BOOT WEB");
        return mv;
    }
}
