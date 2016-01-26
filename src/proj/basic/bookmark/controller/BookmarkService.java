package proj.basic.bookmark.controller;

import java.util.List;

import javax.naming.NamingException;

import proj.basic.bookmark.model.BookmarkDAO;
import proj.basic.bookmark.model.BookmarkVO;
import proj.basic.item.model.ItemVO;
import proj.basic.member.model.MemberVO;

public class BookmarkService {
	BookmarkDAO bookmarkDao;
	
	public BookmarkService(){
		try {
			bookmarkDao = new BookmarkDAO();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	public int addBookmark(MemberVO memberVO, Integer itemNo){
		int errorCode = bookmarkDao.insert(memberVO, itemNo);
		System.out.println("service == " + errorCode);
		return errorCode;
	}
	
	public List<ItemVO> getAllBookmark(MemberVO member){
		return bookmarkDao.getAllOfAMember(member);
	}

}
