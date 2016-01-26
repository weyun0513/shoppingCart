package proj.basic.item.controller;

import java.util.List;
import java.util.Map;

import initMall.InitMall;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import proj.basic.item.model.ItemDAO;
import proj.basic.item.model.ItemVO;
import proj.basic.itemmedia.model.ItemMediaVO;

public class AddItemAndMediaService {
	ItemDAO itemDao;
	public AddItemAndMediaService(){
		try {
			itemDao = new ItemDAO();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addItemAndMedia(ItemVO itemVO, Map<byte[], String> mMap){
		itemDao.insertItemAndMedia(itemVO, mMap);
	}
	public void addItemAndMedia(ItemVO itemVO, List<ItemMediaVO> iMediaList){
		itemDao.insertItemAndMedia(itemVO, iMediaList);
	}

}
