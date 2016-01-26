package proj.basic.itemClass.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class ItemClassVO implements Serializable {

//	/*類別*/
//	create table itemclass(
//	itemclassno tinyint primary key, /*類別編號*/-->改欄位名
//	classname nvarchar(7), /*類別名稱*/-->改nvarchar
//	classstatus tinyint, /*類別狀態*/
//	fatherClassno tinyint /*父類別代號*/-->add
//	constraint classstatus check (classstatus in(0,1)),
//	)
	
	private Integer itemClassNo;
	private String className;
	private Integer classStatus;
	private Integer fatherClassno;
	
//	private Set<ItemVO> items = new LinkedHashSet<ItemVO>();
	
	public Integer getFatherClassno() {
		return fatherClassno;
	}
	public Integer getItemClassNo() {
		return itemClassNo;
	}
	public void setItemClassNo(Integer itemClassNo) {
		this.itemClassNo = itemClassNo;
	}
	public void setFatherClassno(Integer fatherClassno) {
		this.fatherClassno = fatherClassno;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Integer getClassStatus() {
		return classStatus;
	}
	public void setClassStatus(Integer classStatus) {
		this.classStatus = classStatus;
	}
//	public Set<ItemVO> getItems() {
//		return items;
//	}
//	public void setItems(Set<ItemVO> items) {
//		this.items = items;
//	}
	
}
