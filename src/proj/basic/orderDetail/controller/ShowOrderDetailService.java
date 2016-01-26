package proj.basic.orderDetail.controller;

import java.util.List;

import javax.naming.NamingException;

import proj.basic.orderDetail.model.OrderDetailDAO;
import proj.basic.orderDetail.model.OrderDetailVO;

public class ShowOrderDetailService {
	OrderDetailDAO orderDetailDAO;
	
	public ShowOrderDetailService(){
		try {
			orderDetailDAO = new OrderDetailDAO();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<OrderDetailVO> getAOrderDetail(Integer orderNo){
		return orderDetailDAO.getAOrderDetail(orderNo);
	}
	
}
