package com.ldb.vocabulary.server.android.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ldb.vocabulary.server.domain.Account;
import com.ldb.vocabulary.server.domain.DeviceInfo;
import com.ldb.vocabulary.server.domain.LoginBean;
import com.ldb.vocabulary.server.domain.RegisterBean;
import com.ldb.vocabulary.server.service.ILoginService;
import com.ldb.vocabulary.server.service.impl.LoginService;

import oracle.net.aso.s;
import vocabulary.servlet.Login;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/AndroidLogin")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LoginBean loginBean = new LoginBean();
		loginBean.setCheckCode(request.getParameter("checkcode"));
		
		Account account = new Account();
		account.setUsername(request.getParameter("username"));
		account.setPassword(request.getParameter("password"));
		account.setEmail(request.getParameter("email"));
		account.setPhoneNumber(request.getParameter("phonenumber"));
		account.setToken(request.getParameter("token"));
		
		loginBean.setAccount(account);
		
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setSource(request.getParameter("source"));
		deviceInfo.setIp(request.getParameter("ip"));
		deviceInfo.setMac(request.getParameter("mac"));
		deviceInfo.setImei(request.getParameter("imei"));
		deviceInfo.setLocation(request.getParameter("location"));
		
		loginBean.setDeviceInfo(deviceInfo);
		
		ILoginService service = new LoginService();
		String result = service.login(loginBean);
		
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
