package proj.basic.itemmedia.controller;

import java.util.List;

import javax.naming.NamingException;

import proj.basic.itemmedia.model.ItemMediaDAO;
import proj.basic.itemmedia.model.ItemMediaVO;

public class ShowItemMediaService {
	ItemMediaDAO itemMediaDao;
	public ShowItemMediaService(){
		try {
			itemMediaDao = new ItemMediaDAO();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//取得某項商品的所有媒體檔
	public List<ItemMediaVO> showItem(Integer itemNo) {

//		ItemMediaDAO itemMediaDao = new ItemMediaDAO();
		List<ItemMediaVO> list = itemMediaDao.getItemMediaAll(itemNo); 

		return list;
	}

	//呼叫(顯示)某項商品的某項媒體檔=>use primarykey
	public byte[] pickItemMedia(Integer itemNo, Integer itemMediaNo) {
		ItemMediaDAO itemDao;
		ItemMediaVO itemMedia = null;
		try {
			itemDao = new ItemMediaDAO();
			itemMedia = itemDao.findByPrimaryKey(itemNo, itemMediaNo);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemMedia.getItemMedia();
	}

}
