package com.ldb.vocabulary.server.android.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ldb.vocabulary.server.domain.RegisterBean;
import com.ldb.vocabulary.server.service.impl.CheckCodeService;
import com.ldb.vocabulary.server.service.impl.RegisterService;

/**
 * Servlet implementation class CheckCodeServlet
 */
@WebServlet(urlPatterns={"/checkcode/image","/checkcode/image/category/*"})
public class CheckCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckCodeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		CheckCodeService service = new CheckCodeService();
		service.setPhoneNumber(request.getParameter("phonenumber"));
		service.setType(request.getParameter("type"));
		service.setSource(request.getParameter("source"));
		service.setIp(request.getParameter("ip"));
		service.setMac(request.getParameter("mac"));
		service.setImei(request.getParameter("imei"));
		service.setLocation(request.getParameter("location"));
		
		String result = service.getCheckCode();
		
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/json;charset=UTF-8");
		response.getWriter().write(result);
		
		/**
         * 1.获得客户机信息
         */
//        String requestUrl = request.getRequestURL().toString();//得到请求的URL地址
//        String requestUri = request.getRequestURI();//得到请求的资源
//        String queryString = request.getQueryString();//得到请求的URL地址中附带的参数
//        String remoteAddr = request.getRemoteAddr();//得到来访者的IP地址
//        String remoteHost = request.getRemoteHost();
//        int remotePort = request.getRemotePort();
//        String remoteUser = request.getRemoteUser();
//        String method = request.getMethod();//得到请求URL地址时使用的方法
//        String pathInfo = request.getPathInfo();
//        String localAddr = request.getLocalAddr();//获取WEB服务器的IP地址
//        String localName = request.getLocalName();//获取WEB服务器的主机名
//        response.setCharacterEncoding("UTF-8");//设置将字符以"UTF-8"编码输出到客户端浏览器
//        //通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
//        response.setHeader("content-type", "text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        out.write("获取到的客户机信息如下：");
//        out.write("<hr/>");
//        out.write("请求的URL地址："+requestUrl);
//        out.write("<br/>");
//        out.write("请求的资源："+requestUri);
//        out.write("<br/>");
//        out.write("请求的URL地址中附带的参数："+queryString);
//        out.write("<br/>");
//        out.write("来访者的IP地址："+remoteAddr);
//        out.write("<br/>");
//        out.write("来访者的主机名："+remoteHost);
//        out.write("<br/>");
//        out.write("使用的端口号："+remotePort);
//        out.write("<br/>");
//        out.write("remoteUser："+remoteUser);
//        out.write("<br/>");
//        out.write("请求使用的方法："+method);
//        out.write("<br/>");
//        out.write("pathInfo："+pathInfo);
//        out.write("<br/>");
//        out.write("localAddr："+localAddr);
//        out.write("<br/>");
//        out.write("localName："+localName);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
