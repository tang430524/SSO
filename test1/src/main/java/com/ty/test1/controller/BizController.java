package com.ty.test1.controller;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class BizController {

    @ResponseBody
    @RequestMapping("/hi")
    public String home(HttpSession session) {
        return "hello," +session.getAttribute("uname");
    }
    
    
}
