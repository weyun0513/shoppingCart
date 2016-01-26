package proj.basic.orderDetail.model;

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

public class OrderDetailDAO {
	
	DataSource ds;
	public OrderDetailDAO() throws NamingException{
		Context cntxt = new InitialContext();
		ds = (DataSource)cntxt.lookup(InitMall.LOOK_UP_DATA);
	}

	private static final String GET_ORDER_DETAIL = "SELECT * FROM orderdetail WHERE orderno = ?";
	
	public List<OrderDetailVO> getAOrderDetail(Integer orderNo){
		List<OrderDetailVO> list  = new ArrayList<OrderDetailVO>();
		OrderDetailVO orderDetailVO = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		
		try {
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(GET_ORDER_DETAIL);
			pstmt.setInt(1, orderNo);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				orderDetailVO = new OrderDetailVO();
				orderDetailVO.setOrderNo(rs.getInt(1));
				orderDetailVO.setItemNo(rs.getInt(2));
				orderDetailVO.setItemName(rs.getString(3));
				orderDetailVO.setItemPrice(rs.getDouble(4));
				orderDetailVO.setDiscount(rs.getDouble(5));
				orderDetailVO.setQuantity(rs.getInt(6));
				
				list.add(orderDetailVO);
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
