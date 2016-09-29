package com.ldb.vocabulary.server.android.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.ldb.util.ImageUtils;
import com.ldb.vocabulary.server.domain.CommunicationContract;
import com.ldb.vocabulary.server.domain.Constants;
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
			// /list?page=1&sort=f&s_type=a&s_lan
			if(serviceInfo.length != 2){
				return;
			}
			// TODO 获取类别列表
			page = request.getParameter(CommunicationContract.KEY_PAGE) == null ? 0 
					: Integer.valueOf(request.getParameter(CommunicationContract.KEY_PAGE));
			String sort = request.getParameter(CommunicationContract.KEY_SORT);
			String sortType = request.getParameter(CommunicationContract.KEY_SORT_TYPE);
			String secondLan = request.getParameter(CommunicationContract.KEY_CATEGORY_SECOND_LANGUAGE);
			result = service.getCategoryList(page, sort, sortType, secondLan);
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
		case CommunicationContract.METHOD_ADD:
			// /add?image=sjfso&name=test
			if(serviceInfo.length != 2){
				return;
			}
//			String imageStr = request.getParameter(CommunicationContract.KEY_CATEGORY_IMAGE);
//			String imageNameAdd = request.getParameter("name");
//			stringToImageFile(imageStr, imageNameAdd);
//			stringToImageFile(request, response);
			getFile(request, response);
			break;
		default:
			break;
		}
		
		if(result != null){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
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
	
	private void getFile(HttpServletRequest request, HttpServletResponse response){
        String savePath = Constants.UPLOAD_IMAGE_DIR_TEMP;
        File file = new File(savePath);
        // 判断上传文件的保存目录是否存在，不存在则创建目录
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        
        //消息提示
        String message = "";
        try{
            //使用Apache文件上传组件处理文件上传步骤：
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
             //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8"); 
            //3、判断提交上来的数据是否是上传表单的数据
            if(!ServletFileUpload.isMultipartContent(request)){
                //按照传统方式获取数据
            	String value= request.getParameter(CommunicationContract.KEY_CATEGORY_NAME);
            	System.out.println("traditional: " + CommunicationContract.KEY_CATEGORY_NAME + "=" + value);
            	
                return;
            }
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
//            List<FileItem> list = upload.parseRequest(request);
            List<FileItem> list = (List<FileItem>)upload.parseRequest(new ServletRequestContext(request));
            for(FileItem item : list){
                //如果fileitem中封装的是普通输入项的数据
                if(item.isFormField()){
                    String name = item.getFieldName();
                    //解决普通输入项的数据的中文乱码问题
                    String value = item.getString("UTF-8");
                    //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                    System.out.println(name + "=" + value);
                }else{//如果fileitem中封装的是上传文件
                    //得到上传的文件名称，
                    String filename = item.getName();
                    System.out.println(filename);
                    if(filename==null || filename.trim().equals("")){
                        continue;
                    }
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("\\")+1);
                    //获取item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    //创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while((len=in.read(buffer))>0){
                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                        out.write(buffer, 0, len);
                    }
                    //关闭输入流
                    in.close();
                    //关闭输出流
                    out.close();
                    //删除处理文件上传时生成的临时文件
                    item.delete();
                    message = "文件上传成功！";
                }
            }
        }catch (Exception e) {
            message= "文件上传失败！";
            e.printStackTrace();
            
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
