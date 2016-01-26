package proj.basic.member.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import proj.basic.member.model.MemberVO;

public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String path = request.getContextPath();

		MemberService memberService = new MemberService();
		Map<String, String> memberMap = new HashMap<String, String>();

		FileItemFactory fileFac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fileFac);

		InputStream in = null;

		session.removeAttribute("pwdChkError");
		
		try {
			List<FileItem> fileList = upload.parseRequest(request);
			
			for (FileItem i : fileList) {

				if (i.isFormField()) {
					memberMap.put(i.getFieldName(), i.getString("UTF-8"));
				} else if (!i.isFormField() && i.getSize() != 0) {
					in = i.getInputStream();
					memberService.procPhoto(in);
				}
			}

			MemberVO memberVO_update = null;
			
			if(memberMap.get("pwd")!= null && memberMap.get("pwd").trim().length()!=0 && !memberMap.get("pwd").equals(memberMap.get("pwdChk"))){//密碼有輸入時, 判斷到兩者不符=>轉回丟錯誤訊息
				memberVO_update = memberService.getMemberVO(memberMap.get("account"));
				session.setAttribute("pwdChkError", "密碼驗證錯誤請重新輸入");
			}else
				memberVO_update = memberService.update(memberMap, in); //密碼有無輸入在service裡面會判斷

			// 將session裡面的資料更新
			session.setAttribute("memberVO", memberVO_update);

			response.sendRedirect(path + "/02_member/memberInfo.jsp");

		} catch (FileUploadException e) {
			e.printStackTrace();
		}

	}// End of doPost
}
