package proj.basic.item.controller;

import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import proj.basic.item.model.ItemDAO;
import proj.basic.item.model.ItemVO;

public class AddSugViewListService {

	private ItemDAO itemDao;
	public AddSugViewListService(){
		try {
			itemDao = new ItemDAO();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public List<ItemVO> getSugItemsDef(){
		return itemDao.getSugItemsDef();
	}
	
	public List<ItemVO> getSugUserItems(Map<String, String> iHistoryMap,String classNo){
		return itemDao.getSugUserItems(iHistoryMap.keySet(), classNo);

	}
	public List<ItemVO> getSugUserClassItems(String classNo){
		return itemDao.getSugClassItemsDef(classNo);
		
	}
	public List<ItemVO> getSugUserItems(String classNo){
		return itemDao.getSugClassItemsCtoF(classNo);
	}
	public List<ItemVO> getSugFatherItems(String classNo){
		return itemDao.getSugFatherClassItems(classNo);
	}
}
