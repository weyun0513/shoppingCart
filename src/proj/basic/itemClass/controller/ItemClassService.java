package proj.basic.itemClass.controller;

import java.util.List;

import javax.naming.NamingException;

import proj.basic.itemClass.model.ItemClassDAO;
import proj.basic.itemClass.model.ItemClassVO;

public class ItemClassService {
	
	private ItemClassDAO itemClassDao;
	public ItemClassService(){
		try {
			itemClassDao = new ItemClassDAO();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
//	public List<ItemClassVO> findByClassName(String className){
//		List<ItemClassVO> list =itemClassDao.findByClassName(className);
//		return list;
//	}
	
//	public List<ItemClassVO> findClassChild(String className){
//		List<ItemClassVO> list = itemClassDao.findEveryChild(className);
//		return list;
//	}
	
	public List<ItemClassVO> findClassChild(Integer classNo){
		List<ItemClassVO> list = itemClassDao.findEveryChild(classNo);
		return list;
	}
	public ItemClassVO findFatherClass(Integer classNo){
		return itemClassDao.findFather(classNo);
	}
	
	public List<ItemClassVO>  findItemByClassNo(Integer classNo){
		List<ItemClassVO> list = itemClassDao. findItemByClassNo(classNo);
		return list;
	}
	
	public List<ItemClassVO>  getAllAlive(){
		List<ItemClassVO> list = itemClassDao.getAll();
		return list;
	}
	public List<ItemClassVO>  getAllClassAlive(){
		List<ItemClassVO> list = itemClassDao.getAllClass();
		return list;
	}
	
	public ItemClassVO  findClassByClassNo(Integer classNo){
		ItemClassVO itemClassVO = itemClassDao.findClassByClassNo(classNo);
		return itemClassVO;
	}
	
	public List<ItemClassVO>  getChild(Integer fatherClassno){
		return itemClassDao.findChild(fatherClassno);
	}
}
