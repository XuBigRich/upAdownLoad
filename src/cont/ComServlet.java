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
	//����
	public void download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			SmartUpload su=new SmartUpload();
			PageContext pc=JspFactory.getDefaultFactory().getPageContext(this, request, response,null, true, 8192, true);
			su.initialize(pc);//��ʼ��su����
			
			su.setContentDisposition(null);//��������Ϊnull��������������word��excel���ļ�����ֱ����������д��ļ�����
			
			String fn=request.getParameter("fn");
			su.downloadFile("D:\\upload\\"+fn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//�ļ������б�
	public void allFiles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path=request.getSession().getServletContext().getRealPath("upload");//��ȡuploadĿ¼����ʵ·��
		System.out.println(path);
		java.io.File fp=new java.io.File("D:\\upload");//��uploadĿ¼����Ŀ¼����
		java.io.File fs[]=fp.listFiles();//�г�uploadĿ¼�µ������ļ���������
		
		List fnames=new ArrayList();
		//�����Ǳ��������飬��ȡÿ���ļ�������
		for(int i=0;i<fs.length;i++)fnames.add(fs[i].getName());
		
		request.setAttribute("fnames",fnames);
		request.getRequestDispatcher("filelist.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			SmartUpload su=new SmartUpload();
			PageContext pc=JspFactory.getDefaultFactory().getPageContext(this, request, response,null, true, 8192, true);
			su.initialize(pc);//��ʼ��su����
			
//			su.setAllowedFilesList("txt,jpg,jpeg,gif,png,bmp,xls,xlsx,doc,docx,ppt,pptx,java,class");//���������ϴ����ļ�����
			su.setDeniedFilesList("html,jsp,htm,exe,com,bin");//���þܾ��ϴ����ļ��б�
			su.setMaxFileSize(1024*1024*5);//���������ϴ�������ļ���С
			
			su.upload();//�������ϴ���������ļ��Ƿ����Ҫ�������������ᱨ�쳣
			
			Files fs=su.getFiles();//��ȡ�ϴ����ļ��б�
			File f=fs.getFile(0);//��ȡ����Ϊ0��Ԫ�أ����ϴ����Ǹ��ļ�����
			
			f.saveAs("D:\\upload\\"+f.getFileName());//�����ڵ�ǰ��վ��uploadĿ¼��
			
			System.out.println(su.getRequest().getParameter("txt"));
			//response.sendRedirect(arg0)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
