package proj.basic.evaluate.model;

public class EvaluateVO {

//	create table Evaluate(
//			OrderNo int , /*訂單編號*/
//			memberid int, /*會員編號*/
//			itemno int,/*商品編號*/
//			evaluateStar int,/*滿意度*/
//			evaluatebody nvarchar(500) /*評價內容*/
//			FOREIGN KEY (OrderNo)REFERENCES orderlist(OrderNo),
//			FOREIGN KEY (MemberID)REFERENCES member(MemberID),
//			FOREIGN KEY (itemno)REFERENCES item(itemno),
//			constraint EvaluateStar check (EvaluateStar in('1','2','3','4','5')),
//			primary key ( OrderNo,memberid,itemno)
//			)
	
	private Integer orderNo;
	private Integer memberID;
	private Integer itemNo;
	private Integer evaluateStar;
	private String evaluateBody;
	
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getMemberID() {
		return memberID;
	}
	public void setMemberID(Integer memberID) {
		this.memberID = memberID;
	}
	public Integer getItemNo() {
		return itemNo;
	}
	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}
	public Integer getEvaluateStar() {
		return evaluateStar;
	}
	public void setEvaluateStar(Integer evaluateStar) {
		this.evaluateStar = evaluateStar;
	}
	public String getEvaluateBody() {
		return evaluateBody;
	}
	public void setEvaluateBody(String evaluateBody) {
		this.evaluateBody = evaluateBody;
	}
	
}
