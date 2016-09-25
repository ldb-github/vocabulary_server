package com.ldb.vocabulary.server.android.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ldb.vocabulary.server.domain.RegisterBean;
import com.ldb.vocabulary.server.service.impl.RegisterService;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/AndroidRegister")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RegisterBean registerBean = new RegisterBean();
		registerBean.setUsername(request.getParameter("username"));
		registerBean.setPassword(request.getParameter("password"));
		registerBean.setConfirmPwd(request.getParameter("confirmpwd"));
		registerBean.setEmail(request.getParameter("email"));
		registerBean.setPhoneNumber(request.getParameter("phonenumber"));
		registerBean.setCheckCode(request.getParameter("checkcode"));
		
		registerBean.setSource(request.getParameter("source"));
		
		registerBean.setIp(request.getParameter("ip"));
		registerBean.setMac(request.getParameter("mac"));
		registerBean.setImei(request.getParameter("imei"));
		registerBean.setLocation(request.getParameter("location"));
//		registerBean.setStartTime(new Date(request.getParameter("starttime")));
//		registerBean.setEndTime(request.getParameter("endtime"));
		
//	    SimpleDateFormat simpleTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义日期格式  默认时间格式：yyyy-MM-dd HH:mm:ss  
//	    //SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");  
//	    String passTime = pwoEditForm.getPasstime();  
//	    java.util.Date passUtilDate = simpleTime.parse(passTime);  
//	    java.sql.TimeStamp passSqlDate = new java.sql.TimeStamp(passUtilDate.getTime());  
//	    pwOrder.setPasstime(passSqlDate);  

		
		RegisterService registerService = new RegisterService();
		String result = registerService.register(registerBean);
		
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/json;charset=UTF-8");
		response.getWriter().write(result);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
