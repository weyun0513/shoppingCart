package proj.basic.item.controller;

import java.util.List;

import javax.naming.NamingException;

import proj.basic.item.model.ItemDAO;
import proj.basic.item.model.ItemVO;

public class ShowItemsService {

	ItemDAO itemDao = null;
	private int itemNumPerPage = 6;
	
	public	ShowItemsService(){
		try {
			itemDao = new ItemDAO();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//每頁的開始編號及結束編號當作參數傳入DAO
	public List<ItemVO> showItem(int classNo, int pageNo) {
		
		int startNo = itemNumPerPage * (pageNo-1) +1; 
		int endNo = pageNo * itemNumPerPage;

		List<ItemVO> items = itemDao.getPerPageItem(classNo, startNo, endNo);

		return items;
	}
	//給最大類別用
	public List<ItemVO> showClassItem(int classNo, int pageNo) {
		
		int startNo = itemNumPerPage * (pageNo-1) +1; 
		int endNo = pageNo * itemNumPerPage;

		List<ItemVO> items = itemDao.getPerPageClassItem(classNo, startNo, endNo);

		return items;
	}
	
	//計算總共幾頁
	public int itemCount(int classNo){
		int totalPage = 0;
		int totalCount = itemDao.getCount(classNo);
		if((totalCount % itemNumPerPage) == 0)
			totalPage = totalCount / itemNumPerPage;
		else
			totalPage = totalCount / itemNumPerPage + 1;
		return totalPage;
	}
	
	public ItemVO showItemDetail(Integer itemNo){
//		itemDao = new ItemDAO();
		ItemVO itemVO = itemDao.findByPrimaryKey(itemNo);
		
		return itemVO;
		
	}

}
