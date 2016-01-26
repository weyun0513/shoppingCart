package proj.basic.loginRecord.model;

import initMall.InitMall;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class LoginRecordDAO {
	
	DataSource ds;
	public LoginRecordDAO() throws NamingException{
		Context cntxt = new InitialContext();
		ds = (DataSource)cntxt.lookup(InitMall.LOOK_UP_DATA);
	}
	
	private final static String INSERT = "INSERT INTO loginRecord VALUES(?, ?, ?, ?, ?)";
	
	public void insert(LoginRecordVO loginRecord) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();

			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(INSERT);
			pstmt.setInt(1, loginRecord.getMemberID());
			pstmt.setString(2, loginRecord.getIp());
			pstmt.setString(3, loginRecord.getAccount());
			pstmt.setTimestamp(4, loginRecord.getLoginTime());
			pstmt.setString(5, loginRecord.getLoginMsg());
			pstmt.executeUpdate();

			conn.commit();
			System.out.println("新增成功");
			conn.setAutoCommit(true);

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	

}
