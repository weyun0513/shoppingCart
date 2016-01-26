package proj.basic.evaluate.model;

import initMall.InitMall;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class EvaluateDAO implements EvaluateDAO_Interface {
	
	private static DataSource ds = null;

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(InitMall.LOOK_UP_DATA);

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	/**INSERT INTO 5 column*/
	private static final String INSERT_STMT =
		      "INSERT INTO evaluate (OrderNo ,MemberID, itemno, evaluateStar, evaluatebody) VALUES (?, ?, ?, ?, ?)";


	/**UPDATE 5 column by itemno,  最後3個是 orderNo memberid itemno */
	private static final String UPDATE =
		      "UPDATE evaluate set OrderNo=? memberid=?, itemno=?, evaluateStar=?, evaluatebody =? where orderNo = ? AND memberid=? AND itemno=?";
	
	/**SELECT 5 column ALL BY_ITEM*/
	private static final String GET_ALL_STMT_BY_ITEM =
		      "SELECT orderNo, memberid, itemno, evaluateStar, evaluatebody FROM evaluate where itemno = ? order by orderNo";
	
	/**SELECT 5 column ALL BY_ID*/
	private static final String GET_ALL_STMT_BY_ID =
		      "SELECT orderNo, memberid, itemno, evaluateStar, evaluatebody FROM evaluate where memberid = ? order by orderNo";
	
	/**SELECT 5 column ALL*/
	private static final String GET_ALL =
		      "SELECT orderNo, memberid, itemno, evaluateStar, evaluatebody FROM evaluate order by orderNo";
	
	/**SELECT 5 column By 複合主鍵三個*/
	private static final String GET_COM_3KEY =
		      "SELECT orderNo, memberid, itemno, evaluateStar, evaluatebody "
		      + " FROM evaluate WHERE orderNo=? AND memberid=? AND itemno=?";
	
	/**SELECT 1(item) column By 複合主鍵兩個*/
	private static final String GET_COM_2KEY =
		      "SELECT itemno FROM evaluate WHERE orderNo=? AND memberid=?";
	
	private static final String DELETE =
		      "DELETE FROM evaluate where orderNo = ?";
	

	@Override
	public void insert(EvaluateVO evaluateVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, evaluateVO.getOrderNo());
			pstmt.setInt(2, evaluateVO.getMemberID());
			pstmt.setInt(3, evaluateVO.getItemNo());
			pstmt.setInt(4, evaluateVO.getEvaluateStar());
			pstmt.setString(5, evaluateVO.getEvaluateBody());

			pstmt.executeUpdate();

			// Handle any driver errors
		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. "+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(EvaluateVO evaluateVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, evaluateVO.getMemberID());
			pstmt.setInt(2, evaluateVO.getItemNo());
			pstmt.setInt(3, evaluateVO.getEvaluateStar());
			pstmt.setString(4, evaluateVO.getEvaluateBody());
			pstmt.setInt(5, evaluateVO.getOrderNo());

			pstmt.executeUpdate();

			// Handle any driver errors
		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. "+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(Integer orderNo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, orderNo);

			pstmt.executeUpdate();

			// Handle any driver errors
		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public List<EvaluateVO> findByID(Integer memberid) {
		List<EvaluateVO> list = new ArrayList<EvaluateVO>();
		EvaluateVO evaluateVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT_BY_ID);

			pstmt.setInt(1, memberid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				evaluateVO = new EvaluateVO();
				evaluateVO.setOrderNo(rs.getInt("orderNo"));
				evaluateVO.setMemberID(rs.getInt("memberid"));
				evaluateVO.setItemNo(rs.getInt("itemno"));
				evaluateVO.setEvaluateStar(rs.getInt("evaluateStar"));
				evaluateVO.setEvaluateBody(rs.getString("evaluatebody"));
				
				list.add(evaluateVO);
			}
			// Handle any driver errors
		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	@Override
	public List<EvaluateVO> findByItem(Integer itemno) {
		List<EvaluateVO> list = new ArrayList<EvaluateVO>();
		EvaluateVO evaluateVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT_BY_ITEM);

			pstmt.setInt(1, itemno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				evaluateVO = new EvaluateVO();
				evaluateVO.setOrderNo(rs.getInt("orderNo"));
				evaluateVO.setMemberID(rs.getInt("memberid"));
				evaluateVO.setItemNo(rs.getInt("itemno"));
				evaluateVO.setEvaluateStar(rs.getInt("evaluateStar"));
				evaluateVO.setEvaluateBody(rs.getString("evaluatebody"));
				
				list.add(evaluateVO);
			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}


	
	
	
	@Override
	public List<EvaluateVO> getAll() {
		List<EvaluateVO> list = new ArrayList<EvaluateVO>();
		EvaluateVO evaluateVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				evaluateVO = new EvaluateVO();
				evaluateVO.setOrderNo(rs.getInt("orderNo"));
				evaluateVO.setMemberID(rs.getInt("memberid"));
				evaluateVO.setItemNo(rs.getInt("itemno"));
				evaluateVO.setEvaluateStar(rs.getInt("evaluateStar"));
				evaluateVO.setEvaluateBody(rs.getString("evaluatebody"));
				
				list.add(evaluateVO);
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public EvaluateVO findByCompositeKey(Integer orderno, Integer memberid,Integer itemno) {
		
		EvaluateVO evaluateVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_COM_3KEY);
			
			pstmt.setInt(1 ,orderno );
			pstmt.setInt(2 ,memberid);
			pstmt.setInt(3 ,itemno );
			
			rs = pstmt.executeQuery();
			
			
			while (rs.next()) {
				evaluateVO = new EvaluateVO();
				evaluateVO.setOrderNo(rs.getInt("orderNo"));
				evaluateVO.setMemberID(rs.getInt("memberid"));
				evaluateVO.setItemNo(rs.getInt("itemno"));
				evaluateVO.setEvaluateStar(rs.getInt("evaluateStar"));
				evaluateVO.setEvaluateBody(rs.getString("evaluatebody"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return evaluateVO;
	}

	@Override
	public List<Integer> whichEvaluated(Integer orderNo, Integer memberid) {
		
		List<Integer> list = new ArrayList<Integer>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_COM_2KEY);
			pstmt.setInt(1, orderNo);
			pstmt.setInt(2, memberid);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Integer itemNo =  rs.getInt("itemNo");
				list.add(itemNo);
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	

	

}
