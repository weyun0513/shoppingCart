package proj.basic.orderList.controller;

import java.util.List;

import javax.naming.NamingException;

import proj.basic.member.model.MemberVO;
import proj.basic.orderList.model.OrderListDAO;
import proj.basic.orderList.model.OrderListVO;

public class LoadMemberOrderService {
	OrderListDAO orderListDao;
	public LoadMemberOrderService(){
		try {
			orderListDao = new OrderListDAO();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public List<OrderListVO> getAllOrder(MemberVO memberVO){
		List<OrderListVO> list = orderListDao.getMemberAllOrders(memberVO);
		return list;
	}
}
