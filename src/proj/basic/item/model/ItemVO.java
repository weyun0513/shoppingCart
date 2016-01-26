package proj.basic.item.model;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Date;

public class ItemVO implements Serializable {
///*商品*/
//	create table item(
//			itemclassno tinyint not null, /*商品類別編號*/
//			itemno int identity primary key, /*商品編號*/
//			itemname nvarchar(30) not null, /*商品名稱*/
//			price float not null, /*商品價格*/
//			discount float, /*商品折扣*/
//			onsaletime datetime not null, /*上架時間*/
//			offsaletime datetime not null, /*下架時間*/
//			itemdscrp nvarchar(1000), /*商品文字說明*/
//			itemsQty int, /*商品庫存量*/
//			itemstatus tinyint, /*商品狀態*/
//			FOREIGN KEY (itemclassno) REFERENCES itemclass(itemclassno)
//			)
	
	private Integer itemClassNo;
	private Integer itemNo; //identity
	private String itemName;
	private Double price;
	private Double discount;
	private Date onSaleTime;
	private Date offSaleTime;
	private String itemDscrp;
	private Integer itemsQty;
	private Integer itemStatus;
	
	
	public Integer getItemClassNo() {
		return itemClassNo;
	}
	public void setItemClassNo(Integer itemClassNo) {
		this.itemClassNo = itemClassNo;
	}
	public Integer getItemNo() {
		return itemNo;
	}
	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Date getOnSaleTime() {
		return onSaleTime;
	}
	public void setOnSaleTime(Date onSaleTime) {
		this.onSaleTime = onSaleTime;
	}
	public Date getOffSaleTime() {
		return offSaleTime;
	}
	public void setOffSaleTime(Date offSaleTime) {
		this.offSaleTime = offSaleTime;
	}
	public String getItemDscrp() {
		return itemDscrp;
	}
	public void setItemDscrp(String itemDscrp) {
		this.itemDscrp = itemDscrp;
	}
	public Integer getItemsQty() {
		return itemsQty;
	}
	public void setItemsQty(Integer itemsQty) {
		this.itemsQty = itemsQty;
	}
	public Integer getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}
	
}
