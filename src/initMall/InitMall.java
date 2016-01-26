package initMall;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import proj.basic.item.model.ItemDAO;
import proj.basic.itemClass.model.ItemClassDAO;
import proj.basic.itemClass.model.ItemClassVO;
import proj.basic.itemmedia.model.ItemMediaDAO;
import proj.basic.member.controller.MemberService;
import proj.basic.member.model.MemberDAO;
import proj.basic.member.model.MemberVO;

public class InitMall {
	public static final String LOOK_UP_DATA = "java:comp/env/jdbc/ProjDB";

	public void init(){
//	public static void main(String[] args) {
		try {
		// 商品類別
		ItemClassDAO itemClassDAO = new ItemClassDAO();
//		itemClassDAO.truncate();
//		System.out.println("look");
//		ItemClassVO itemClassVO = new ItemClassVO();
//		List<ItemClassVO> itemClassList = new ArrayList<ItemClassVO>();
//
//		itemClassVO.setClassName("影音商品");
//		itemClassVO.setClassStatus(1);
//		itemClassVO.setFatherClassno(0);
//		itemClassList.add(itemClassVO);
//
//		itemClassVO = new ItemClassVO();
//		itemClassVO.setClassName("CD");
//		itemClassVO.setClassStatus(1);
//		itemClassVO.setFatherClassno(1);
//		itemClassList.add(itemClassVO);
//
//		itemClassVO = new ItemClassVO();
//		itemClassVO.setClassName("DVD");
//		itemClassVO.setClassStatus(1);
//		itemClassVO.setFatherClassno(1);
//		itemClassList.add(itemClassVO);
//
//		itemClassVO = new ItemClassVO();
//		itemClassVO.setClassName("VCD");
//		itemClassVO.setClassStatus(1);
//		itemClassVO.setFatherClassno(1);
//		itemClassList.add(itemClassVO);
//
//		itemClassVO = new ItemClassVO();
//		itemClassVO.setClassName("女性服裝");
//		itemClassVO.setClassStatus(1);
//		itemClassList.add(itemClassVO);
//		itemClassVO.setFatherClassno(0);
//
//		itemClassVO = new ItemClassVO();
//		itemClassVO.setClassName("洋裝");
//		itemClassVO.setClassStatus(1);
//		itemClassVO.setFatherClassno(2);
//		itemClassList.add(itemClassVO);
//
//		itemClassVO = new ItemClassVO();
//		itemClassVO.setClassName("上衣");
//		itemClassVO.setClassStatus(1);
//		itemClassVO.setFatherClassno(2);
//		itemClassList.add(itemClassVO);
//
//		itemClassVO = new ItemClassVO();
//		itemClassVO.setClassName("褲/裙");
//		itemClassVO.setClassStatus(1);
//		itemClassVO.setFatherClassno(2);
//		itemClassList.add(itemClassVO);
//		itemClassDAO.multi_Insert(itemClassList);
		
		File file = new File("D:\\items.xlsx");
//		File file = new File("E:\\items.xlsx");
		itemClassDAO.multi_Insert(file);
		
		// 商品
		ItemDAO itemDao = new ItemDAO();
//		file = new File("E:\\items.xlsx");
		file = new File("D:\\items.xlsx");
		itemDao.multi_Insert(file);
		

		// 多媒體檔
		ItemMediaDAO itemMediaDao = new ItemMediaDAO();
//		file = new File("E:\\itemMedia.xlsx");
		file = new File("D:\\itemMedia.xlsx");
		itemMediaDao.multi_Insert(file);
	
		//一個會員
		MemberDAO memberDao = new MemberDAO();
		MemberVO memberVO = new MemberVO();
		memberVO.setChineseName("林小華");
		memberVO.setGender("男");
		memberVO.setBirthday(java.sql.Date.valueOf("1980-06-30"));
		memberVO.setEmail("abc123@yahoo.com.");
		memberVO.setAccount("test2");
		memberVO.setPwd(pwdEncoMD5("pwd123"));
		memberVO.setAddr("台北市");
		memberVO.setRegistDate(new java.sql.Date(new java.util.Date().getTime()));
		memberVO.setPhone("0986123456");
		memberVO.setBonus(0);
		memberVO.setDeposit(0);
//		memberVO.setPhoto(memberDao.getImg("E:\\proj\\memberPhoto\\member01.jpg"));
		memberVO.setPhoto(memberDao.getImg("D:\\proj\\0713_2310\\proj\\memberPhoto\\member01.jpg"));
		memberVO.setIsBlock(1);
		memberVO.setWrongtimes(0);
		
		memberDao.insert(memberVO);
		}
		catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String pwdEncoMD5(String password){
		StringBuffer savePwd = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] pwd = md.digest(password.getBytes());
			savePwd = new StringBuffer();
			for(byte b:pwd)
				savePwd.append(b);
			System.out.println(savePwd.length());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return savePwd.toString();
	}
	
}
