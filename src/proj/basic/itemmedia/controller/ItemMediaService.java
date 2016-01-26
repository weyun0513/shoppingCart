package proj.basic.itemmedia.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.naming.NamingException;

import proj.basic.itemmedia.model.ItemMediaDAO;
import proj.basic.itemmedia.model.ItemMediaVO;

public class ItemMediaService {
	private ItemMediaDAO itemMediaDao;
	public ItemMediaService(){
		try {
			itemMediaDao = new ItemMediaDAO();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void uploadItemMedia(String itemNo, InputStream in, String mediaType){ 
		
		ItemMediaVO itemMediaVO = new ItemMediaVO();
		Integer itemMediaNo = itemMediaDao.getLastMediano(Integer.valueOf(itemNo));
		itemMediaVO.setItemNo(Integer.valueOf(itemNo));
		itemMediaVO.setItemMediaNo(itemMediaNo);
		itemMediaVO.setItemMedia(prcFile(in));
		itemMediaVO.setMediaType(mediaType);
		itemMediaDao.insert(itemMediaVO);
		
	}
	
	private byte[] prcFile(InputStream in) {
		byte[] bFile = null;
		try {
			bFile = new byte[in.available()];
			in.read(bFile);
			System.out.println(bFile.length);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return bFile;
	}
	
	public List<ItemMediaVO> getAllMediaOfOneItem(Integer itemNo){
		
		List<ItemMediaVO> list = itemMediaDao.getItemMediaAll(itemNo);
		
		return list;
		
	}
 
	public ItemMediaVO getFirstPhoto(Integer itemNo, Integer itemMediaNo){
		return itemMediaDao.findByPrimaryKey(itemNo, itemMediaNo);
	}

}
