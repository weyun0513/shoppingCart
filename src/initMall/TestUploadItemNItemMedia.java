package initMall;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import proj.basic.item.model.ItemVO;
import proj.basic.item.model.ItemDAO;
import proj.basic.itemmedia.model.ItemMediaVO;
import proj.basic.itemmedia.model.ItemMediaDAO;

public class TestUploadItemNItemMedia {
	public static void main(String[] args){
		TestUploadItemNItemMedia test = new TestUploadItemNItemMedia();
		List<ItemMediaVO> list = new ArrayList<ItemMediaVO>();
		try {
		ItemVO item = new ItemVO();
		ItemMediaVO iMedia = new ItemMediaVO();
		ItemDAO itemDao = new ItemDAO();

		item.setItemClassNo(1);
		item.setItemName("測試");
		item.setItemsQty(1);
		item.setPrice(6000*1.0);
		item.setDiscount(0.5);
		item.setOnSaleTime(java.sql.Date.valueOf("2005-06-16"));
		item.setOffSaleTime(java.sql.Date.valueOf("2005-06-16"));
		item.setItemDscrp("尚無");
		item.setItemStatus(1);

		iMedia.setItemMediaNo(1);
		iMedia.setMediaType("image/jpeg");
		iMedia.setMediaDscrp("暫時說明");
		iMedia.setItemMedia(test.ProcFile("E:\\proj\\itemPhoto\\01cover.jpg"));
		list.add(iMedia);
		
		itemDao.insertItemAndMedia(item, list);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public byte[] ProcFile(String path){
		
		byte[] bFile = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(path));
			bFile = new byte[in.available()];
			
			in.read(bFile);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return bFile;
	}
}
