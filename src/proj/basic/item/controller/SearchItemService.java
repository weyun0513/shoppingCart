package proj.basic.item.controller;

import java.util.List;

import javax.naming.NamingException;

import proj.basic.item.model.ItemDAO;
import proj.basic.item.model.ItemVO;

public class SearchItemService {
	ItemDAO itemDao;
	
	public SearchItemService(){
 
		try {
			itemDao = new ItemDAO();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<ItemVO> searchItem(String searchText, Integer itemType, String[] multiSearchArray, Double minPrice,Double maxPrice,Boolean discount,Boolean onSale){
//	public List<ItemVO> searchItem(String searchText, String itemType){
		List<ItemVO> list = null;
		//使用者可能使用空白鍵入多組關鍵字
		String[] searchArray = searchText.split(" ");
		list = itemDao.searchItem(searchArray, itemType, multiSearchArray,minPrice,maxPrice,discount,onSale);
		return list;
	}
	
	public ItemVO findByItemNo(Integer itemNo){
		return itemDao.findByPrimaryKey(itemNo);
	}
	
}
