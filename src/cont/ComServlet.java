package cont;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;

public class ComServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cmd=request.getParameter("cmd");
		if(cmd.equals("allFiles"))allFiles(request,response);
		else if(cmd.equals("download"))download(request,response);
	}
	//下载
	public void download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			SmartUpload su=new SmartUpload();
			PageContext pc=JspFactory.getDefaultFactory().getPageContext(this, request, response,null, true, 8192, true);
			su.initialize(pc);//初始化su对象
			
			su.setContentDisposition(null);//必须设置为null！！！否则下载word或excel等文件，会直接在浏览器中打开文件本身
			
			String fn=request.getParameter("fn");
			su.downloadFile("D:\\upload\\"+fn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//文件下载列表
	public void allFiles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path=request.getSession().getServletContext().getRealPath("upload");//获取upload目录的真实路径
		System.out.println(path);
		java.io.File fp=new java.io.File("D:\\upload");//将upload目录做成目录对象
		java.io.File fs[]=fp.listFiles();//列出upload目录下的所有文件对象数组
		
		List fnames=new ArrayList();
		//以下是遍历该数组，获取每个文件的名称
		for(int i=0;i<fs.length;i++)fnames.add(fs[i].getName());
		
		request.setAttribute("fnames",fnames);
		request.getRequestDispatcher("filelist.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			SmartUpload su=new SmartUpload();
			PageContext pc=JspFactory.getDefaultFactory().getPageContext(this, request, response,null, true, 8192, true);
			su.initialize(pc);//初始化su对象
			
//			su.setAllowedFilesList("txt,jpg,jpeg,gif,png,bmp,xls,xlsx,doc,docx,ppt,pptx,java,class");//设置允许上传的文件类型
			su.setDeniedFilesList("html,jsp,htm,exe,com,bin");//设置拒绝上传的文件列表
			su.setMaxFileSize(1024*1024*5);//设置允许上传的最大文件大小
			
			su.upload();//真正的上传，并检查文件是否符合要求。如果不符合则会报异常
			
			Files fs=su.getFiles();//获取上传的文件列表
			File f=fs.getFile(0);//获取索引为0的元素，即上传的那个文件对象
			
			f.saveAs("D:\\upload\\"+f.getFileName());//保存在当前网站的upload目录下
			
			System.out.println(su.getRequest().getParameter("txt"));
			//response.sendRedirect(arg0)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
