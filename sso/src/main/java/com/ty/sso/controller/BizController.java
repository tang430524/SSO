package com.ty.sso.controller;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ty.sso.util.RedisUtil;
import com.ty.sso.util.Token;
import com.ty.sso.vo.TokenModel;
import com.ty.sso.vo.User;



@Controller
public class BizController {
	@Autowired
	RedisUtil ru;
	@Autowired
	Token tk;
    @Value("${server.port}")
    String port;
    @ResponseBody
    @RequestMapping("/hi")
    public String home() {
        return "hi,i am from port:" +port;
    }
    @ResponseBody
    @RequestMapping("/checkToken.action")
    public Object checkToken(@RequestParam String reurl,@RequestParam String token,HttpServletResponse response) throws Exception {
    	String uid=tk.getIdByToken(token);
    	System.out.println(uid);
    	if(ru.exists(uid)){
    		TokenModel tm=(TokenModel) ru.get(uid);
    		String uname=tm.getToken()==token||token.equals(tm.getToken())?tm.getUser().getUname().toString():"";
    		response.setHeader("uname",uname);
    		System.out.println(uname);
    		return ru.get(token);
    	}
		response.setHeader("uname", null);
    	return false;
    }
    
    
    @RequestMapping("/ssoLogin")
    public String login(@RequestParam String reurl) {
    	if(reurl!=null){
    		return "redirect:login.html?reurl="+reurl;
    	}
    	return "hi,i am from port:" +port;
    }
    
    //登陆
    @RequestMapping(value="/logincheck",method=RequestMethod.POST)
    public @ResponseBody void logincheck(@ModelAttribute User user,@RequestParam String reurl,HttpServletResponse response) throws Exception {
    	String uname=user.getUname();String upassword=user.getUpassword();
    	System.out.println(uname+"..."+upassword);
    	if(uname.equals("tang")&&upassword.equals("123")){
    		String token=tk.getToken("1001");
    		user.setUpassword("");user.setUid(1001);
    		TokenModel tm=new TokenModel(token, user);
    		ru.set("1001", tm, new Long(10000));
    		try {
				response.sendRedirect(reurl+"?token="+token);
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
    		return;
    	}
    	try {
			response.sendRedirect("login.html?reurl="+reurl);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return;
    }
    
}
