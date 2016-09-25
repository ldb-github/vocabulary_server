package com.ldb.vocabulary.server.android.controller;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ldb.vocabulary.server.domain.CommunicationContract;
import com.ldb.vocabulary.server.service.ICategoryService;
import com.ldb.vocabulary.server.service.impl.CategoryService;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/category/*")
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if(pathInfo.endsWith("/")){
			pathInfo = pathInfo.substring(0, pathInfo.length() - 1);
		}
		String[] serviceInfo = pathInfo.split("/");
		if(serviceInfo.length == 0){
			return;
		}
//		OutputStream out = response.getOutputStream();
		String result = null;
		String method = serviceInfo[1];
		ICategoryService service = new CategoryService();
		int page = 0;
		String categoryId = null;
		switch (method) {
		case CommunicationContract.METHOD_LIST:
			// /list?page=1&sort=f&s_type=a
			if(serviceInfo.length != 2){
				return;
			}
			// TODO 获取类别列表
			page = request.getParameter(CommunicationContract.KEY_PAGE) == null ? 0 
					: Integer.valueOf(request.getParameter(CommunicationContract.KEY_PAGE));
			String sort = request.getParameter(CommunicationContract.KEY_SORT);
			String sortType = request.getParameter(CommunicationContract.KEY_SORT_TYPE);
			result = service.getCategoryList(page, sort, sortType);
			break;
		case CommunicationContract.METHOD_LIST_VOCABULARY:
			// /listv?c_id=1&page=1
			if(serviceInfo.length != 2){
				return;
			}
			page = Integer.valueOf(request.getParameter(CommunicationContract.KEY_PAGE));
			categoryId = request.getParameter(CommunicationContract.KEY_CATEGORY_ID);
			// TODO 获取类别词汇列表
			result = service.getCategoryItemList(categoryId, page);
			break;
		case CommunicationContract.METHOD_IMAGE:
			// /image/[categoryid]/XXX.png
			if(serviceInfo.length != 4){
				return;
			}
			// TODO 获取图片
			categoryId = serviceInfo[2];
			String imageName = serviceInfo[3];
//			service.getImage(categoryId, imageName);
			OutputStream out = response.getOutputStream();
			getImage(categoryId, imageName, out);
//			getImage2(request.getRequestURL().toString(), response.getOutputStream());
			break;
		default:
			break;
		}
		
		if(result != null){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
//			out.write(result.getBytes());
			response.getWriter().write(result);
		}
		System.out.println("-------------response to client-----------");
	}
	
	private void getImage(String categoryId, String imageName, OutputStream out) throws IOException{
		FileInputStream fileIn = new FileInputStream(getServletContext()
				.getRealPath("/WEB-INF/image/" + categoryId + "/" + imageName));
		FileChannel in = fileIn.getChannel();
		ByteBuffer buff = ByteBuffer.allocate(1024);
		BufferedOutputStream bufferedOut = new BufferedOutputStream(out);
		while(in.read(buff) != -1){
			buff.flip();
			bufferedOut.write(buff.array());
			buff.clear();
		}
		// 记得flush，开始漏了这个，客户端解析不到数据！！
		bufferedOut.flush();
		bufferedOut.close();
		in.close();
		fileIn.close();
	}
	
	private void getImage2(String url, OutputStream out) throws IOException{
		// 想仿照 https://farm9.staticflickr.com/8041/28769655083_9954c93dd3_m.jpg，返回一个<img..>标签 ，没有成功
		out.write(("<img src='" + url + "' />").getBytes());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
