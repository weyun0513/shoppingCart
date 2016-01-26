package proj.basic.blackList.model;

import initMall.InitMall;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import proj.basic.member.model.MemberVO;

public class BlackListDAO {
	DataSource ds;
	public BlackListDAO() throws NamingException{
		Context cntxt = new InitialContext();
		ds = (DataSource)cntxt.lookup(InitMall.LOOK_UP_DATA);
	}
	
	private long lockMin = 1000 * 60 * 5;
	private static final String INSERT_IP="INSERT INTO blacklist (IP) VALUES(?)";
	private static final String INSERT_MEMBER="INSERT INTO blacklist VALUES(?, ?, ?, ?)";
	private static final String GET_ALL="SELECT * FROM blacklist";
	
	public void insert_account(MemberVO memberVO, String ip){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try { 

			conn = ds.getConnection();

			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(INSERT_MEMBER);
			pstmt.setInt(1,memberVO.getMemberID());
			pstmt.setString(2,memberVO.getAccount());
			pstmt.setString(3,ip);
			pstmt.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime() + 5*60*1000));
			int count = pstmt.executeUpdate();

			conn.commit();

			System.out.println("成功新增 " + count + " 筆至黑名單; memberID = " + memberVO.getMemberID());

			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void insert_ip(String ip){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {

			conn = ds.getConnection();
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(INSERT_IP);
			pstmt.setString(1,ip);
			conn.commit();

			int count = pstmt.executeUpdate();

			System.out.println("成功新增 " + count + " 筆至黑名單; IP = " + ip);

			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
		public List<BlackListVO> getAll(){
			Connection conn = null;
			PreparedStatement pstmt = null;
			List<BlackListVO> blackList = new ArrayList<BlackListVO>();
			BlackListVO blackListVO = null;
			try {

				conn = ds.getConnection();

				conn.setAutoCommit(false);

				pstmt = conn.prepareStatement(GET_ALL);
				conn.commit();

				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()){
					blackListVO = new BlackListVO();
					blackListVO.setMemberID(rs.getInt(1));
					blackListVO.setAccount(rs.getString(2));
					blackListVO.setIp(rs.getString(3));
					blackListVO.setUnLockTime(rs.getTimestamp(4));
					
					blackList.add(blackListVO);
				}

				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
			return blackList;
		}
	}
