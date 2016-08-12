package controllers;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;

import org.apache.commons.codec.binary.Hex;

import play.mvc.Controller;
import play.mvc.Http;

public class Login extends Controller{
	/*登录*/
    public static void checkLogin(String username,String password,HttpServletRequest request,HttpServletResponse response) 
    		throws ServletException, IOException ,Throwable{
    	User user = User.find("username=?",username).first();
    	Map<String,String> map=new HashMap<String,String>();
    	try {
	  		MessageDigest md = MessageDigest.getInstance("MD5");
	  		byte[] pwdMD5 = md.digest(password.getBytes());
	  		String pwd = Hex.encodeHexString(pwdMD5);
	    	if(user!= null){
	    		if(user.password.equals(pwd)){
	    			//System.out.println("yes");
	    			Http.Response.current().setCookie("username",username);
	    			Http.Response.current().setCookie("id",user.id.toString());
	    			Http.Response.current().setCookie("password", pwd);
	    			((ServletRequest) session).setAttribute("username",username);
	    			((ServletRequest) session).setAttribute("id",user.id);
	    			((ServletRequest) session).setAttribute("password",password);
	    			map.put("checked", "1");
	    			map.put("username", username);
	    			map.put("password",pwd);
	    		}else{
	    			System.out.println("password error!");
	    			map.put("checked", "2");
	    		}
	    	}else{
	    		System.out.println("user error!");
	    		map.put("checked", "0");
	    	};
	    	renderJSON(map);
	  	} catch (NoSuchAlgorithmException e) {
	  		// TODO Auto-generated catch block
	  		e.printStackTrace();
	  	}
    	
    }

    /*登出*/
    public static void logout(){
    	Http.Response.current().setCookie("username", null);
    	Http.Response.current().setCookie("id",null);
    	Http.Response.current().removeCookie("username");
    	Http.Response.current().removeCookie("id");
    } 
}