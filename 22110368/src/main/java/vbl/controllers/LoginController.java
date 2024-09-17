package vbl.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vbl.dao.UserService;
import vbl.models.User;
import vbl.models.UserServiceImpl;

import java.io.IOException;

import org.eclipse.tags.shaded.org.apache.bcel.classfile.Constant;

/**
 * Servlet implementation class LoginController
 */
@WebServlet(urlPatterns ="/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("account") != null) {
		resp.sendRedirect(req.getContextPath()+ "/waiting");
		return;
	}
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
		for (Cookie cookie : cookies) {
		if (cookie.getName().equals("username")) {
		session = req.getSession(true);
		session.setAttribute("username", cookie.getValue());
		resp.sendRedirect(req.getContextPath()+ "/waiting");
		return;
		}}}
		req.getRequestDispatcher("views/login.jsp").forward(req, resp);
		}	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		 resp.setCharacterEncoding("UTF-8");
		 req.setCharacterEncoding("UTF-8");
		String username = req.getParameter("uname");
		 String password = req.getParameter("psw");
		 boolean isRememberMe = false;
		 String remember = req.getParameter("remember");

		 if("on".equals(remember)){
		 isRememberMe = true;
		 }
		 String alertMsg="";
		 if(username.isEmpty() || password.isEmpty()){
			 alertMsg = "Tài khoản hoặc mật khẩu không được rỗng";
			  req.setAttribute("alert", alertMsg);
			  req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
			  return;
			  }
			  UserService service = new UserServiceImpl();
			  User user = service.login(username, password);
			 if(user!=null){
			  HttpSession session = req.getSession(true);
			  session.setAttribute("account", user);
			  if(isRememberMe){
				  saveRememberMe(resp, username);
			  }
			  resp.sendRedirect(req.getContextPath()+"/waiting");
			  }else{
			  alertMsg =
			 "Tài khoản hoặc mật khẩu không đúng";
			  req.setAttribute("alert", alertMsg);
			  req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
			  }
			  
	}
	
	private void saveRememberMe(HttpServletResponse response, String username) {
	    Cookie cookie = new Cookie(vbl.util.Constant.COOKIE_REMEMBER, username); 
	    cookie.setMaxAge(30 * 60); // 30 minutes
	    response.addCookie(cookie);
	}

		

}
