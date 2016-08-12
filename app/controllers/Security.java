package controllers;

import java.security.NoSuchAlgorithmException;

import play.mvc.Http;
import models.User;

public class Security extends Secure.Security{
	public static boolean authenticate(String username,String password){
		User user = User.find("username = ?", username).first();
		Http.Response.current().setCookie("id",user.id.toString());
		if(user == null){
			return false;
		}
		try {
			if(user.passwordMaches(password)){
				return true;
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
}
