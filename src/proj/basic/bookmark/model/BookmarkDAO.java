package proj.basic.bookmark.model;

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

import proj.basic.item.model.ItemVO;
import proj.basic.member.model.MemberVO;

public class BookmarkDAO {
	
	DataSource ds;
	public BookmarkDAO() throws NamingException{
		Context cntxt = new InitialContext();
		ds = (DataSource)cntxt.lookup(InitMall.LOOK_UP_DATA);
	}

	private static final String INSERT = "INSERT INTO bookmark VALUES(?, ?)";
	private static final String FIND_BY_PRIMARYKEY = "SELECT * FROM item WHERE itemno = ?";
	private static final String DELETE = "DELETE bookmark WHERE itemno = ? AND memberID = ?";
//	private static final String GET_ALL = "SELECT * FROM bookmark WHERE itemno = ? AND memberID = ? ";
	private static final String GET_ALL = "SELECT * FROM item WHERE item.itemno IN(SELECT itemno FROM bookmark WHERE memberid = ?)";

	public int insert(MemberVO memberVO, Integer itemNo) {

		int errorCode = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();

			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(INSERT);
			pstmt.setInt(1, memberVO.getMemberID());
			pstmt.setInt(2, itemNo);
			int count = pstmt.executeUpdate();
			
			conn.commit();

			System.out.println("成功 " + count + " 筆我的最愛");

			conn.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println("errorCode=="+e.getErrorCode());
			if(e.getErrorCode() == 2627){
				//違反 PRIMARY KEY 條件約束 'PK__bookmark__6ABEF5945A039A2E'。無法在物件 'dbo.bookmark' 中插入重複的索引鍵。
				errorCode = 2627;
			}
			try {
				conn.rollback();
			} catch (SQLException e1) {
//				e1.printStackTrace();
			}
//			e.printStackTrace();
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
	
	public List<ItemVO> getAllOfAMember(MemberVO memberVO){
		List<ItemVO> list= new ArrayList<ItemVO>();
		ItemVO itemVO = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {

			conn = ds.getConnection();

			pstmt = conn.prepareStatement(GET_ALL);
			pstmt.setInt(1, memberVO.getMemberID());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				itemVO = new ItemVO();
				itemVO.setItemClassNo(rs.getInt(1));
				itemVO.setItemNo(rs.getInt(2));
				itemVO.setItemName(rs.getString(3));
				itemVO.setPrice(rs.getDouble(4));
				itemVO.setDiscount(rs.getDouble(5));
				itemVO.setOnSaleTime(rs.getDate(6));
				itemVO.setOffSaleTime(rs.getDate(7));
				itemVO.setItemDscrp(rs.getString(8));
				
				list.add(itemVO);
			}

			System.out.println("成功查詢我的最愛");

		} catch (SQLException e) {
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
		
		return list;
	}
}
