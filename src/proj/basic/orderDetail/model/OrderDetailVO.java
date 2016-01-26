package proj.basic.orderDetail.model;

import java.io.Serializable;

public class OrderDetailVO implements Serializable {
	
//	/*訂單明細*/
//	create table orderdetail(
//	 orderno int, /*訂單編號*/-->不是primarykey
//	 itemno int, /*商品編號*/
//	 itemname nvarchar(30), /*商品名稱*/
//	 itemprice float, /*商品價格*/
//	 discount float, /*商品折扣*/
//	 quantity int, /*購買數量*/
//	 FOREIGN KEY (orderno) REFERENCES orderlist(orderno),
//	 FOREIGN KEY (itemno) REFERENCES item(itemno)
//	primary key ( OrderNo,ItemNo)  -->改為複合主鍵
//	);

	private Integer orderDetailNo;
	private Integer orderNo;
//	private OrderListVO orderListVO;
//	private ItemVO itemVO;
	private Integer itemNo;
	private String itemName;
	private Double itemPrice;
	private Double discount;
	private Integer quantity;
	
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public void setOrderDetailNo(Integer orderDetailNo){
		this.orderDetailNo = orderDetailNo;
	}
	
	public Integer getOrderDetailNo(){
		return orderDetailNo;
	}
	
//	public OrderListVO getOrderListVO() {
//		return orderListVO;
//	}
//	public void setOrderListVO(OrderListVO orderListVO) {
//		this.orderListVO = orderListVO;
//	}
//	public ItemVO getItemVO() {
//		return itemVO;
//	}
//	public void setItemVO(ItemVO itemVO) {
//		this.itemVO = itemVO;
//	}
	
	
	public String getItemName() {
		return itemName;
	}
	public Integer getItemNo() {
		return itemNo;
	}

	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
