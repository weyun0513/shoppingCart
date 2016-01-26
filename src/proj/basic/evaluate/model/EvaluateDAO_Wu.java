package proj.basic.evaluate.model;

import initMall.InitMall;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import proj.basic.member.model.MemberVO;

public class EvaluateDAO_Wu {
	
	DataSource ds;
	public EvaluateDAO_Wu() throws NamingException{
		Context cntxt = new InitialContext();
		ds = (DataSource)cntxt.lookup(InitMall.LOOK_UP_DATA);
	}

	private static final String INSERT = "INSERT INTO Evaluate VALUES(?, ?, ? ,?, ?)";
	private static final String GET_ITEM_E_ALL = "SELECT * FROM evaluate WHERE itemno = ?";
	private static final String CHK_CAN_E_OR_NOT = "SELECT * FROM evaluate WHERE orderno = ? AND memberid= ? AND itemno = ?";
	
	
//	public int insert(OrderListVO orderListVO, ItemVO itemVO, MemberVO memberVO, String evaluateBody){
//		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		int errorCode = 0; 
//		try {
//
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pwd);
//
//			conn.setAutoCommit(false);
//
//			pstmt = conn.prepareStatement(INSERT);
//			pstmt.setInt(1, orderListVO.getOrderNo());
//			pstmt.setInt(2, memberVO.getMemberID());
//			pstmt.setInt(3, itemVO.getItemNo());
//			pstmt.setString(4, evaluateBody);
//			int count = pstmt.executeUpdate();
//			
//			conn.commit();
//
//			System.out.println("成功 " + count + " 筆商品編號 " +  itemVO.getItemNo() + " 的評價");
//
//			conn.setAutoCommit(true);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			if(e.getErrorCode() == 2627){
//				//違反 PRIMARY KEY 條件約束 'PK__bookmark__6ABEF5945A039A2E'。無法在物件 'dbo.bookmark' 中插入重複的索引鍵。
//				//表示已評價過
//				errorCode = 2627;
//			}
//			try {
//				conn.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//			e.printStackTrace();
//		} finally {
//
//			if (pstmt != null)
//				try {
//					pstmt.close();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//
//			if (conn != null)
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//		}
//		return errorCode;
//	}
	
	public int insert(EvaluateVO eVO){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		int errorCode = 0; 
		try {
			
			conn = ds.getConnection();
			
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setInt(1, eVO.getOrderNo());
			pstmt.setInt(2, eVO.getMemberID());
			pstmt.setInt(3, eVO.getItemNo());
			pstmt.setInt(4, eVO.getEvaluateStar());
			pstmt.setString(5, eVO.getEvaluateBody());
			int count = pstmt.executeUpdate();
			
			conn.commit();
			
			System.out.println("成功 " + count + " 筆商品編號 " +  eVO.getItemNo() + " 的評價");
			
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			if(e.getErrorCode() == 2627){
				//違反 PRIMARY KEY 條件約束 'PK__bookmark__6ABEF5945A039A2E'。無法在物件 'dbo.bookmark' 中插入重複的索引鍵。
				//表示已評價過
				errorCode = 2627;
			}
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
		return errorCode;
	}

	public Boolean chkEorNot(Integer orderNo, MemberVO memberVO, Integer itemNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		boolean check = false;
		try {
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(CHK_CAN_E_OR_NOT);
			pstmt.setInt(1, orderNo);
			pstmt.setInt(2, memberVO.getMemberID());
			pstmt.setInt(3, itemNo);
			ResultSet rs = pstmt.executeQuery();
			
			if(!rs.next())
				check = true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return check;
	}
	
	public List<EvaluateVO> getItemEAll(Integer itemNo){
		
		List<EvaluateVO> list = new ArrayList<EvaluateVO>();
		EvaluateVO eVO = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(GET_ITEM_E_ALL);
			pstmt.setInt(1, itemNo);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				eVO = new EvaluateVO();
				eVO.setOrderNo(rs.getInt(1));
				eVO.setMemberID(rs.getInt(2));
				eVO.setItemNo(rs.getInt(3));
				eVO.setEvaluateStar(rs.getInt(4));
				eVO.setEvaluateBody(rs.getString(5));
				
				list.add(eVO);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return list;
	}
	
}
