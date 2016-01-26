package proj.basic.orderList.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import proj.basic.item.model.ItemVO;
import proj.basic.member.model.MemberVO;
import proj.basic.orderDetail.model.OrderDetailVO;
import proj.basic.orderList.model.OrderListDAO;
import proj.basic.orderList.model.OrderListVO;

public class CfmOrderListService {
	OrderListDAO orderListDao;
	
	public CfmOrderListService(){
		try {
			orderListDao = new OrderListDAO();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public List<Integer> makeOrder(OrderListVO orderListVO, Map<String, OrderDetailVO> cartList, MemberVO memberVO){
		return orderListDao.insert(orderListVO, cartList, memberVO);
	}
	/**2014/07/31 Huang 增加方法提供servlet使用***************************************************/
	/**
	 * @orderStatus 資料庫取出的英文訂單狀態
	 * @return 處理過後中文的訂單狀態
	 * */
	public String translateOrderStatus (String orderStatus){
		
		if(orderStatus.equalsIgnoreCase("process"))
			orderStatus = "處理中";
		if(orderStatus.equalsIgnoreCase("shipping"))
			orderStatus = "運送中";
		if(orderStatus.equalsIgnoreCase("arrival"))
			orderStatus = "已簽收";
		if(orderStatus.equalsIgnoreCase("refund"))
			orderStatus = "退貨處理中";
		if(orderStatus.equalsIgnoreCase("returned"))
			orderStatus = "已退貨";

		return orderStatus;
		
	}
	
	/**
	 * @list 購物車總共消費項目
	 * @return 計算購物車總金額
	 * */
	public int calculateOrderDetailVO (List<OrderDetailVO> list){

		Double totalPrice = 0.0;
		
		for(OrderDetailVO detailVO : list){
			Double subTotle = 0.0;
			//判斷折扣 若是0.0就沒有折扣為1.0，若是有折數就用原來數字
			Double discount = detailVO.getDiscount() == 0.0 ? 1.0 : detailVO.getDiscount();
			
			subTotle = (Double) ((detailVO.getItemPrice() * detailVO.getQuantity()) 
					* discount);
			totalPrice += subTotle;
			
		}
		
		//消費滿1000免80元運費
		totalPrice = totalPrice >= 1000.0 ? totalPrice : totalPrice + 80.0;
		return totalPrice.intValue() ;
	}
	
	/**
	 * @list 購物車總共消費項目
	 * @bonus 紅利金
	 * @deposit 儲值金
	 * @return 計算帳單總金額
	 * 如果沒有使用bonus 或 deposit 就等於0.0
	 * */
	public int calculateFinalPrice (Iterator<OrderDetailVO> iterator , String bonusStr , String depositStr ){
		
		Double totalPrice = 0.0;
		
		while(iterator.hasNext()){
			OrderDetailVO vo = iterator.next();
			Double subTotle = 0.0;
			//判斷折扣 若是0.0就沒有折扣為1.0，若是有折數就用原來數字
			Double discount = vo.getDiscount() == 0.0 ? 1.0 : vo.getDiscount();
			
			subTotle = (Double) ((vo.getItemPrice() * vo.getQuantity())* discount);
			totalPrice += subTotle;
			
		}
		
		//消費滿1000免80元運費
		totalPrice = totalPrice >= 1000.0 ? totalPrice : totalPrice + 80.0;
		
		//如果沒有使用bonus 或 deposit 就等於0.0
		Double bonus = bonusStr == null ? Double.valueOf(0.0) : Double.valueOf(bonusStr);
		Double deposit = depositStr == null ? Double.valueOf(0.0) : Double.valueOf(depositStr);
		totalPrice = totalPrice - bonus - deposit;
		
		return totalPrice.intValue() ;
	}
	
	/**結束2014/07/31 Huang 增加方法提供servlet使用***************************************************/


}
