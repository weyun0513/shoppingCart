package proj.basic.orderList.model;

import initMall.InitMall;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import proj.basic.itemmedia.model.ItemMediaVO;
import proj.basic.member.model.MemberVO;
import proj.basic.orderDetail.model.OrderDetailVO;

public class OrderListDAO {
	
	DataSource ds;
	public OrderListDAO() throws NamingException{
		Context cntxt = new InitialContext();
		ds = (DataSource)cntxt.lookup(InitMall.LOOK_UP_DATA);
	}

	private static final String INSERT_NEW = "INSERT INTO orderlist(memberid, orderdate, totalprice, invoiceincno, invoicetitle, shippingaddr, shippingrate, payway, orderstatus, useDeposit, useBonus ,receiveBonus ) VALUES(?, getdate(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String INSERT_NEW_DETAIL = "INSERT INTO orderdetail VALUES(?, ?, ?, ?, ?, ?)";
	private static final String FIND_BY_PRIMARYKEY = "SELECT * FROM orderlist WHERE orderNo = ?";
	private static final String FIND_MEMBER_ORDERS = "SELECT * FROM orderlist WHERE memberid = ?";
	private static final String Q_QTY = "SELECT itemsqty FROM item WHERE itemno = ?";
	// private static final String DELETE =
	// "DELETE bookmark WHERE itemno = ? AND memberID = ?";
	private static final String GET_MEMBER_ALL = "SELECT * FROM orderlist WHERE memberID = ?";

	public List<Integer> insert(OrderListVO orderListVO, Map<String, OrderDetailVO> cartList, MemberVO memberVO) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Integer> emtyItemNo = new ArrayList<Integer>();
		try {

			conn = ds.getConnection();
//			http://docs.oracle.com/javase/6/docs/api/java/sql/Connection.html
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(INSERT_NEW);
			pstmt.setInt(1, memberVO.getMemberID());
			pstmt.setDouble(2, orderListVO.getTotalPrice());
			pstmt.setString(3, orderListVO.getInvoiceIncNo());
			pstmt.setString(4, orderListVO.getInvoiceTitle());
			pstmt.setString(5, orderListVO.getShippingAddr());
			pstmt.setDouble(6, orderListVO.getShippingRate());
			pstmt.setString(7, orderListVO.getPayWay());
			pstmt.setString(8, orderListVO.getOrderStatus());
			pstmt.setInt(9, orderListVO.getUseDeposit());
			pstmt.setInt(10, orderListVO.getUseBonus());
			pstmt.setInt(11, orderListVO.getReceiveBonus());
			int count = pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement("SELECT max(orderno) from orderlist");
			rs = pstmt.executeQuery();
			
			Integer orderNo = 0;
			if(rs.next()){
				orderNo = rs.getInt(1);	
			}
			
			int countDetail = 0;
			int itemQty = 0;
			
			Iterator<OrderDetailVO> list = cartList.values().iterator();
			OrderDetailVO orderDeatil = null;
			while(list.hasNext()){
				orderDeatil = list.next();
				
				pstmt = conn.prepareStatement(Q_QTY);
				pstmt.setInt(1, orderDeatil.getItemNo());
				rs = pstmt.executeQuery();
				rs.next();
					
				if(rs.getInt(1) < orderDeatil.getQuantity())
					emtyItemNo.add(orderDeatil.getItemNo());
			}
			
			if(emtyItemNo.size() !=0)
				throw new SQLException(); 
			
			Iterator<OrderDetailVO> list2 = cartList.values().iterator();
			while(list2.hasNext()){
				orderDeatil = list2.next();
				
				pstmt = conn.prepareStatement(INSERT_NEW_DETAIL);
				pstmt.setInt(1, orderNo);
				pstmt.setInt(2,orderDeatil.getItemNo()); 
				pstmt.setString(3, orderDeatil.getItemName());
				pstmt.setDouble(4, orderDeatil.getItemPrice());
				pstmt.setDouble(5, orderDeatil.getDiscount());
				pstmt.setInt(6,orderDeatil.getQuantity()); 
				countDetail += pstmt.executeUpdate();
				
				pstmt = conn.prepareStatement("UPDATE item SET itemsQty = (SELECT itemsqty FROM item WHERE itemno = ?) - ? WHERE itemno = ?");
				pstmt.setInt(1, orderDeatil.getItemNo());
				pstmt.setInt(2, orderDeatil.getQuantity());
				pstmt.setInt(3, orderDeatil.getItemNo());
				itemQty  = pstmt.executeUpdate();
				System.out.println("商品編號：" + orderDeatil.getItemNo() + " 售出" + orderDeatil.getQuantity() + "個");
			}

			conn.commit();

			System.out.println("成功 " + count + " 筆新訂單; 共" + countDetail + " 筆明細");

			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
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
		return emtyItemNo;
	}
	public ItemMediaVO findByPrimaryKey(Integer itemNo, Integer itemMediaNo) {
		ItemMediaVO itemMediaVO = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(FIND_BY_PRIMARYKEY);
			pstmt.setInt(1, itemNo);
			//pstmt.setInt(2, itemMediaNo);
			ResultSet st = pstmt.executeQuery();
			
			if(st.next()){
				itemMediaVO = new ItemMediaVO();
				itemMediaVO.setItemNo(st.getInt(1));
				itemMediaVO.setItemMediaNo(st.getInt(2));
				itemMediaVO.setItemMedia(st.getBytes(3));
				itemMediaVO.setMediaType(st.getString(4));
				itemMediaVO.setMediaDscrp(st.getString(5));
			}
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
		return itemMediaVO;
	}
	
	public List<OrderListVO> getMemberAllOrders(MemberVO memberVO) {
		List<OrderListVO> list = new ArrayList<OrderListVO>();
		OrderListVO orderListVO = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_MEMBER_ORDERS);
			pstmt.setInt(1, memberVO.getMemberID());
			ResultSet st = pstmt.executeQuery();
			
			while(st.next()){
				orderListVO = new OrderListVO();
				orderListVO.setOrderNo(st.getInt(2));
				orderListVO.setOrderDate(st.getTimestamp(3));
				orderListVO.setTotalPrice(st.getInt(4));
				orderListVO.setInvoiceIncNo(st.getString(5));
				orderListVO.setInvoiceTitle(st.getString(6));
				orderListVO.setShippingAddr(st.getString(7));
				orderListVO.setShippingRate(st.getDouble(8));
				orderListVO.setPayWay(st.getString(9));
				
				if(st.getDate(10)== null || st.getDate(10).toString().trim().length() == 0)
					orderListVO.setShippingDate(java.sql.Date.valueOf("1970-01-01"));
				else
					orderListVO.setShippingDate(st.getDate(10));
				
				if(st.getDate(11) == null || st.getDate(11).toString().trim().length() == 0)
					orderListVO.setReceiveDate(st.getDate(11));
				else
					orderListVO.setReceiveDate(java.sql.Date.valueOf("1970-01-01"));
				
				orderListVO.setOrderStatus(st.getString(12));
				
				if(st.getString(13) == null || st.getString(13).toString().trim().length() == 0)
					orderListVO.setInvoiceNo(st.getString(13));
				else
					orderListVO.setInvoiceNo("");
				
				orderListVO.setUseDeposit(st.getInt(14));
				orderListVO.setUseBonus(st.getInt(15));
				orderListVO.setReceiveBonus(st.getInt(16));
				
				list.add(orderListVO);
			}
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
		return list;
	}

	/**
	 * 2014/7/31 Huang 新增更改訂單主檔狀態方法
	 * @orderNo 訂單編號
	 * @orderStatus 訂單狀態字樣 process'處理中,'shipping'運送中,'arrival'已簽收, 'Refund'退貨處理中, 'returned'已退貨
	 * */
	public int updateStatus(Integer orderNo, String orderStatus) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int updateInt = 0;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement("UPDATE orderList set orderStatus=? WHERE orderNo = ? ");//更新主檔狀態
			
			pstmt.setString(1, orderStatus);
			pstmt.setInt(2, orderNo);

			updateInt = pstmt.executeUpdate();
			
			// Handle any driver errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "+ excep.getMessage());
				}
			}
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
		return updateInt;
	}
}
