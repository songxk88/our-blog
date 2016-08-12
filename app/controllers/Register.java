package controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import models.User;

import org.apache.commons.codec.binary.Hex;

import play.mvc.*;

public class Register extends Controller {

	 /*注册*/
    public static void addUser(String username,String password){
    	try {
	  		MessageDigest md = MessageDigest.getInstance("MD5");
	  		byte[] pwdMD5 = md.digest(password.getBytes());
	  		String pwd = Hex.encodeHexString(pwdMD5);
	  		User user = new User(username,pwd);
	    	user.save().isPersistent();
	    	renderJSON(user);
	  	} catch (NoSuchAlgorithmException e) {
	  		// TODO Auto-generated catch block
	  		e.printStackTrace();
	  	}
    }

}
