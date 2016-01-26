package proj.basic.itemmedia.model;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Date;

public class ItemMediaVO implements Serializable {
//	/*商品影音圖片檔*/
//	create table itemmedia(
//	itemno int, /*商品編號*/
//	itemmediano int, /*編號, 表商品第n個多媒體檔*/
//	itemmedia varbinary(max),/*商品說明多媒體檔*/
//	FOREIGN KEY (itemno) REFERENCES item(itemno),
//	primary key (itemno,itemmediano)
//	)
	
	private Integer itemNo;
	private Integer itemMediaNo;
	private byte[] itemMedia;
	private String mediaType;
	private String mediaDscrp;
	
	
	public String getMediaDscrp() {
		return mediaDscrp;
	}
	public void setMediaDscrp(String mediaDscrp) {
		this.mediaDscrp = mediaDscrp;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public Integer getItemNo() {
		return itemNo;
	}
	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}
	public Integer getItemMediaNo() {
		return itemMediaNo;
	}
	public void setItemMediaNo(Integer itemMediaNo) {
		this.itemMediaNo = itemMediaNo;
	}
	public byte[] getItemMedia() {
		return itemMedia;
	}
	public void setItemMedia(byte[] itemMedia) {
		this.itemMedia = itemMedia;
	}
	
}
