package proj.basic.itemmedia.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import proj.basic.itemmedia.model.ItemMediaDAO;

public class ItemMediaUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String itemNo = null;
		String mediaType = null;
		ItemMediaService itMediasrv = new ItemMediaService();
		FileItemFactory fileFac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fileFac);

		InputStream in = null;

		try {
			List<FileItem> fileList = upload.parseRequest(request);
			for (FileItem i : fileList) {

				if (i.isFormField()){
					itemNo = i.getString();
				}
				else {
					in = i.getInputStream();
					mediaType = i.getContentType();
				}
			}
		itMediasrv.uploadItemMedia(itemNo, in, mediaType);
		
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

}
