package proj.basic.blackList.controller;

import java.util.List;

import javax.naming.NamingException;

import proj.basic.blackList.model.BlackListDAO;
import proj.basic.blackList.model.BlackListVO;
import proj.basic.member.model.MemberVO;

public class BlackListService {
	
	BlackListDAO balckListDao;
	public BlackListService(){
		try {
			balckListDao = new BlackListDAO();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public void addBlackListFromIP(String ip) {
		balckListDao.insert_ip(ip);
	}
	
	public void addBlackListFromAccount(MemberVO memberVO, String ip) {
		balckListDao.insert_account(memberVO, ip);
	}

	public List<BlackListVO> getList(){
		return balckListDao.getAll();
	}
}
