package controllers;
 
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Hex;

import com.google.gson.JsonObject;






















/*import net.sf.oval.guard.Post;*/
import play.*;
import play.mvc.*;
import play.mvc.Http.Cookie;
import models.*;

@With(Secure.class)
public class Application extends Controller {

	public static void login(){
		render();
	}
	public static void index() {
	    render();
	}
	
    public static void list() throws IOException{
    	String username = session.get("username");
    	User user = User.find("username=?",username).first();
    	Long id = user.id;
    	if(user.isAdmin==1){
    		List<Note> notes= Note.find("order by time desc").fetch();
    		renderJSON(notes);
    	}else{
	    		List<Note> notes= Note.find("userId = ? order by time desc",id).fetch();
       	     	renderJSON(notes);
    	    }
    }
    
    public static void note(Long id){
    	Note note = Note.findById(id);
    	renderJSON(note);
    }
    
    public static void addNote(String title,String content) throws IOException{
    	if(title==null||content==null){
    		renderJSON(0);
    	}else{
    		Cookie usernameCookie = Http.Request.current().cookies.get("username");
    		Cookie idCookie = Http.Request.current().cookies.get("id");
    		System.out.println(idCookie);
    		Note note = new Note();
    		note.title=title;
    		note.content=content;
    		note.time=new Date();
    		note.userId =Integer.parseInt(idCookie.value);
    		//System.out.println(Integer.parseInt(idCookie.value));
    		note.save();
    		renderJSON(1);
    	}
    	
    }
    
    public static void modify(Long id,String title,String content){
    	Note note=Note.findById(id);
    	note.title=title;
    	note.content=content;
    	note.time=new Date();
    	note.save().isPersistent();
    	renderJSON(title);
    }
    
    public static void delete(Long id){
    	Note note=Note.findById(id);
    	note.delete().isPersistent();
    	renderJSON(1);
    }
   
   
/*public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
	response.setCharacterEncoding("UTF-8");
	//使用request对象的getSession()获取session，如果session不存在就创建一个
	HttpSession session = request.getSession();
	//将数据存储到session当中
	
	String username = (String) session.getAttribute("username");
	int id = (int)session.getAttribute("id");
	String password = (String) session.getAttribute("password");
}
public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
	doGet(request,response);
}
   */
    
    
    
    
    
    
}