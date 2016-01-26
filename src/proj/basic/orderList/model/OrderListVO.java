package proj.basic.orderList.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

public class OrderListVO implements Serializable {

	private Integer memberID;
//	private MemberVO memberVO;
	private Integer orderNo;
	private Timestamp orderDate;
	private Integer totalPrice;
	private String invoiceIncNo;
	private String invoiceTitle;
	private String shippingAddr;
	private Double shippingRate;
	private String payWay;
	private Date shippingDate;
	private Date receiveDate;
	private String orderStatus;
	private String invoiceNo;
	
	/**20140731 Huang 新增三個欄位*/
	private Integer useDeposit;/*此筆訂單使用多少儲值金*/
	private Integer useBonus; /*此筆訂單使用多少紅利金*/
	private Integer receiveBonus;/*訂單獲得的紅利金*/
	
	public Integer getUseDeposit() {
		return useDeposit;
	}
	public void setUseDeposit(Integer useDeposit) {
		this.useDeposit = useDeposit;
	}
	public Integer getUseBonus() {
		return useBonus;
	}
	public void setUseBonus(Integer useBonus) {
		this.useBonus = useBonus;
	}
	public Integer getReceiveBonus() {
		return receiveBonus;
	}
	public void setReceiveBonus(Integer receiveBonus) {
		this.receiveBonus = receiveBonus;
	}
	
	
	
//	private Set<OrderDetailVO> orderDetailVO = new LinkedHashSet<OrderDetailVO>(); 
	
//	public MemberVO getMemberVO() {
//		return memberVO;
//	}
//	public void setMemberVO(MemberVO memberVO) {
//		this.memberVO = memberVO;
//	}
	
	public Integer getOrderNo() {
		return orderNo;
	}
	public Integer getMemberID() {
		return memberID;
	}
	public void setMemberID(Integer memberID) {
		this.memberID = memberID;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Timestamp getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Timestamp oderDate) {
		this.orderDate = oderDate;
	}
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getInvoiceIncNo() {
		return invoiceIncNo;
	}
	public void setInvoiceIncNo(String invoiceIncNo) {
		this.invoiceIncNo = invoiceIncNo;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getShippingAddr() {
		return shippingAddr;
	}
	public void setShippingAddr(String shippingAddr) {
		this.shippingAddr = shippingAddr;
	}
	public Double getShippingRate() {
		return shippingRate;
	}
	public void setShippingRate(Double shippingRate) {
		this.shippingRate = shippingRate;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public Date getShippingDate() {
		return shippingDate;
	}
	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
//	public Set<OrderDetailVO> getOrderDetailVO() {
//		return orderDetailVO;
//	}
//	public void setOrderDetailVO(Set<OrderDetailVO> orderDetailVO) {
//		this.orderDetailVO = orderDetailVO;
//	}
	
}
