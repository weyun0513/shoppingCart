package proj.basic.item.model;

import initMall.InitMall;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import proj.basic.itemmedia.model.ItemMediaVO;

public class ItemDAO implements ItemDAO_interface {

	DataSource ds;
	public ItemDAO() throws NamingException{
		Context cntxt = new InitialContext();
		ds = (DataSource)cntxt.lookup(InitMall.LOOK_UP_DATA);
	}

	private static final String INSERT = "INSERT INTO item VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE item SET itemclassno = ?, itemname = ?,  price = ?, discount = ?, onsaletime = ?, offsaletime = ?, itemdscrp = ?, itemsQty = ?,itemstatus = ? WHERE itemno = ?";
	private static final String FIND_BY_PRIMARYKEY = "SELECT * FROM item WHERE itemno = ?";
	private static final String DELETE = "UPDATE item SET itemstatus = 0 WHERE itemno = ?";
	private static final String GET_ALL = "SELECT * FROM item WHERE itemstatus = 1 ";//AND itemsQty != 0
	private static final String GET_PER_PAGE_ITEM = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER( ORDER BY itemno) AS number FROM item WHERE itemclassno = ? AND itemstatus = 1)AS onSaleItems WHERE number BETWEEN ? AND ?";
//	private static final String GET_PER_PAGE_ITEM = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER( ORDER BY itemno) AS number FROM item WHERE itemclassno = ? AND itemstatus = 1 AND itemsQty != 0)AS onSaleItems WHERE number BETWEEN ? AND ?";
	private static final String GET_PER_PAGE_CLASSITEM = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER( ORDER BY price) AS number FROM  item, (SELECT itemclassno AS itno FROM itemclass WHERE fatherclassno = ? )AS t2 WHERE t2.itno = item.itemclassno AND itemstatus = 1 )AS onSaleItems WHERE number BETWEEN ? AND ?";
	//首頁預設的推薦
	private static final String GET_SUG_ITEMS_DEF = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER( ORDER BY discount) AS number FROM item)as t1 WHERE t1.number BETWEEN 1 AND 9 ORDER BY t1.discount";
	private static final String GET_SUG_CLASS_ITEMS_DEF = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER( ORDER BY discount) AS number FROM item WHERE itemclassno in (select itemclassno from itemclass where itemclassno = ?))as t1 WHERE t1.number BETWEEN 1 AND 9 ORDER BY t1.discount";
	private static final String GET_SUG_CLASS_ITEMS_CTOF = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER( ORDER BY discount) AS number FROM item WHERE itemclassno in (select itemclassno from itemclass where fatherClassno = (select fatherClassno from itemclass where itemclassno = ?)))as t1 WHERE t1.number BETWEEN 1 AND 9 ORDER BY t1.discount";
	private static final String GET_SUG_FATHERCLASS_ITEMS = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER( ORDER BY discount) AS number FROM item WHERE itemclassno in (select itemclassno from itemclass where fatherClassno = ?))as t1 WHERE t1.number BETWEEN 1 AND 9 ORDER BY t1.discount";
//	private static final String GET_SUG_ITEMS = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER( ORDER BY discount) AS number FROM item WHERE itemclassno = ?)as t1 WHERE t1.number BETWEEN 1 AND 3";
	private static final String GET_SUG_ITEMS = "SELECT * FROM ( SELECT *, ROW_NUMBER() OVER( ORDER BY price) AS number FROM (SELECT * FROM item WHERE price BETWEEN (SELECT MIN(price)-500 FROM item WHERE ? ) and (SELECT MAX(price)+500 FROM item WHERE ? ) AND itemclassno = ? )t1 )t2 WHERE t2.number BETWEEN 1 AND 9";
	private static final String GET_COUNT = "SELECT COUNT(*) FROM item WHERE itemclassno = ? AND itemstatus = 1 AND itemsQty != 0";
	private static final String SEARCH = "SELECT * FROM item ";
//	, (SELECT itemCLASSNO FROM ITEMCLASS WHERE CLASSName = 'DVD')AS iclass WHERE iclass.itemCLASSNO = item.itemclassno AND 
//	select * from item ,(select itemclassno from itemclass where fatherclassno = ?)as t2 where t2.itemclassno = item.itemclassno
	public List<ItemVO> searchItem(String[] searchArray, Integer itemClassNo,  String[] multiSearchArray, Double minPrice, Double maxPrice,Boolean discount,Boolean onSale){
		ItemVO itemVO = null;
		List<ItemVO> list = new ArrayList<ItemVO>();
		StringBuffer userSearch = new StringBuffer(SEARCH);

		if( itemClassNo != 0 && multiSearchArray.length == 0)
			userSearch.append(",(SELECT itemclassno FROM itemclass WHERE fatherclassno = " + itemClassNo + " )AS t2 WHERE t2.itemclassno = item.itemclassno AND (");
		else if(itemClassNo != 0 || multiSearchArray.length!=0)
			userSearch.append("WHERE (");
		else 
			userSearch.append("WHERE ");
		
		int likeNum = 0;
		
		for(String s:searchArray){
			userSearch.append(" itemName LIKE '%" + s + "%' OR itemDscrp LIKE '%" + s + "%'");
			likeNum++;
			if(likeNum < searchArray.length)
				userSearch.append(" OR ");
			if( itemClassNo !=0 && likeNum == searchArray.length)
				userSearch.append(" )");
		}
		likeNum = 0;
		if(multiSearchArray.length != 0){
			userSearch.append(" AND (");
			for(String s: multiSearchArray){
				userSearch.append(" item.itemclassno = " + s);
				likeNum++;
				if(likeNum < multiSearchArray.length)
					userSearch.append(" OR ");
				if(likeNum == multiSearchArray.length)
					userSearch.append(" )");
			}
		}
		
		if(minPrice!= null){
			userSearch.append(" AND price >= " + minPrice);
		}
			
		if(maxPrice != null){
			userSearch.append(" AND price <= " + maxPrice);
		}
		
		if(discount == true){
			userSearch.append(" AND discount != 0 ");
		}
		if(onSale == true){
			userSearch.append(" AND onsaletime BETWEEN DATEADD(MONTH,-1,getdate()) and getdate()");
		}
		
		System.out.println(userSearch);
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(userSearch.toString());
			ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()){
			itemVO = new ItemVO();
			itemVO.setItemClassNo(rs.getInt(1));
			itemVO.setItemNo(rs.getInt(2));
			itemVO.setItemName(rs.getString(3));
			itemVO.setPrice(rs.getDouble(4));
			itemVO.setDiscount(rs.getDouble(5));
			itemVO.setOnSaleTime(rs.getDate(6));
			itemVO.setOffSaleTime(rs.getDate(7));
			itemVO.setItemDscrp(rs.getString(8));
			itemVO.setItemsQty(rs.getInt(9));
			itemVO.setItemStatus(rs.getInt(10));
			list.add(itemVO);
		}

		System.out.println("查詢商品符合條件共 " + list.size() + " 筆");
	}catch (SQLException e) {
		e.printStackTrace();
	}finally{
		if(conn!=null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
		return list;
	}
	

	@Override
	public void multi_Insert(File itemFile) {
		InputStream in = null;
		List<ItemVO> list = new ArrayList<ItemVO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			in = new FileInputStream(itemFile);
			XSSFWorkbook book = new XSSFWorkbook(in);
			XSSFRow r = null;
			int j = 1;
			ItemVO itemVO = new ItemVO();
			while (book.getSheetAt(0).getRow(j) != null) {
				r = book.getSheetAt(0).getRow(j);
				itemVO = new ItemVO();

				itemVO.setItemClassNo(Integer.valueOf(r.getCell(0).toString().substring(0, r.getCell(0).toString().indexOf('.'))));// i=0,// 商品所屬類別編號
				itemVO.setItemName(r.getCell(1).toString());// i=1, 商品名稱
				itemVO.setPrice(Double.valueOf(r.getCell(2).toString()));
				itemVO.setDiscount((Double.valueOf(r.getCell(3).toString())));
				itemVO.setOnSaleTime(new java.sql.Date(r.getCell(4)	.getDateCellValue().getTime()));
				itemVO.setOffSaleTime(new java.sql.Date(r.getCell(5).getDateCellValue().getTime()));
				itemVO.setItemDscrp(r.getCell(6).toString());
				itemVO.setItemsQty(Integer.valueOf(r.getCell(7).toString().substring(0,r.getCell(7).toString().indexOf('.') )));
				itemVO.setItemStatus(Integer.valueOf(r.getCell(8).toString().substring(0, r.getCell(8).toString().indexOf('.'))));
				
				list.add(itemVO);
				j++;
			}
			conn = ds.getConnection();
			
			conn.setAutoCommit(false);
			int count = 0;
			for(ItemVO i:list){
				pstmt = conn.prepareStatement(INSERT);
				pstmt.setInt(1, i.getItemClassNo());
				pstmt.setString(2, i.getItemName());
				pstmt.setDouble(3, i.getPrice());
				pstmt.setDouble(4, i.getDiscount());
				pstmt.setDate(5, i.getOnSaleTime());
				pstmt.setDate(6, i.getOffSaleTime());
				pstmt.setString(7, i.getItemDscrp());
				pstmt.setInt(8, i.getItemsQty());
				pstmt.setInt(9, i.getItemStatus());
				pstmt.executeUpdate();
				count++;
			}
			
			conn.commit();
			System.out.println("成功 " + count + " 筆");
			conn.setAutoCommit(true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	@Override
	public void insert(ItemVO itemVO) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {

			conn = ds.getConnection();
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(INSERT);
			pstmt.setInt(1, itemVO.getItemClassNo());
			pstmt.setString(2, itemVO.getItemName());
			pstmt.setDouble(3, itemVO.getPrice());
			pstmt.setDouble(4, itemVO.getDiscount());
			pstmt.setDate(5, itemVO.getOnSaleTime());
			pstmt.setDate(6, itemVO.getOffSaleTime());
			pstmt.setString(7, itemVO.getItemDscrp());
			pstmt.setInt(8, itemVO.getItemsQty());
			pstmt.setInt(9, itemVO.getItemStatus());
			
			int count = pstmt.executeUpdate();
			
			conn.commit();

			System.out.println("成功 " + count + " 筆");

			conn.setAutoCommit(true);
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

	}

	@Override
	public void update(ItemVO itemVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {

			conn = ds.getConnection();

			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setInt(1, itemVO.getItemClassNo());
			pstmt.setString(2, itemVO.getItemName());
			pstmt.setDouble(3, itemVO.getPrice());
			pstmt.setDouble(4, itemVO.getDiscount());
			pstmt.setDate(5, itemVO.getOnSaleTime());
			pstmt.setDate(6, itemVO.getOffSaleTime());
			pstmt.setString(7, itemVO.getItemDscrp());
			pstmt.setInt(8, itemVO.getItemsQty());
			pstmt.setInt(9, itemVO.getItemStatus());
			pstmt.setInt(10, itemVO.getItemNo());
			
			int count = pstmt.executeUpdate();
			
			conn.commit();

			System.out.println("成功修改 " + count + " 筆");

			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	@Override
	public void delete(ItemVO itemVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			
//			pstmt = conn.prepareStatement(DELETE);
//			pstmt.setInt(1, itemVO.getItemNo());
//			int count = pstmt.executeUpdate();
//			if(count != 0)
//			System.out.println("成功刪除" + count + "筆 (商品編號為 "+itemVO.getItemNo()+")之商品");
//			else
//				System.out.println("無符合刪除條件 (商品編號為 "+itemVO.getItemNo()+")之商品");
			
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setInt(1, itemVO.getItemNo());
			int count = pstmt.executeUpdate();
			if(count != 0)
			System.out.println("成功下架" + count + "筆 (商品編號為 "+itemVO.getItemNo()+")之商品");
			else
				System.out.println("無符合刪除條件 (商品編號為 "+itemVO.getItemNo()+")之商品");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	@Override
	public ItemVO findByPrimaryKey(Integer itemNo) {
		ItemVO itemVO = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(FIND_BY_PRIMARYKEY);
			pstmt.setInt(1, itemNo);
			ResultSet st = pstmt.executeQuery();
			
			if(st.next()){
				itemVO = new ItemVO();
				itemVO.setItemClassNo(st.getInt(1));
				itemVO.setItemNo(st.getInt(2));
				itemVO.setItemName(st.getString(3));
				itemVO.setPrice(st.getDouble(4));
				itemVO.setDiscount(st.getDouble(5));
				itemVO.setOnSaleTime(st.getDate(6));
				itemVO.setOffSaleTime(st.getDate(7));
				itemVO.setItemDscrp(st.getString(8));
				itemVO.setItemsQty(st.getInt(9));
				itemVO.setItemStatus(st.getInt(10));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return itemVO;
	}

	@Override
	public List<ItemVO> getAll() {
		Connection conn = null;
		ItemVO itemVO = null;
		List<ItemVO> allItems = null;
		try {
			conn = ds.getConnection();
			
			allItems = new ArrayList<ItemVO>();
			PreparedStatement pstmt = conn.prepareStatement(GET_ALL);
			ResultSet st = pstmt.executeQuery();
			
			while(st.next()){
				itemVO = new ItemVO();
				itemVO.setItemClassNo(st.getInt(1));
				itemVO.setItemNo(st.getInt(2));
				itemVO.setItemName(st.getString(3));
				itemVO.setPrice(st.getDouble(4));
				itemVO.setDiscount(st.getDouble(5));
				itemVO.setOnSaleTime(st.getDate(6));
				itemVO.setOffSaleTime(st.getDate(7));
				itemVO.setItemDscrp(st.getString(8));
				itemVO.setItemsQty(st.getInt(9));
				itemVO.setItemStatus(st.getInt(10));
				allItems.add(itemVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return allItems;
	}
	
	public List<ItemVO> getSugItemsDef() {
		Connection conn = null;
		ItemVO itemVO = null;
		List<ItemVO> sugItems = null;
		try {
			conn = ds.getConnection();
			
			sugItems = new ArrayList<ItemVO>();
			PreparedStatement pstmt = conn.prepareStatement(GET_SUG_ITEMS_DEF);
			ResultSet st = pstmt.executeQuery();
			
			while(st.next()){
				itemVO = new ItemVO();
				itemVO.setItemClassNo(st.getInt(1));
				itemVO.setItemNo(st.getInt(2));
				itemVO.setItemName(st.getString(3));
				itemVO.setPrice(st.getDouble(4));
				itemVO.setDiscount(st.getDouble(5));
				itemVO.setOnSaleTime(st.getDate(6));
				itemVO.setOffSaleTime(st.getDate(7));
				itemVO.setItemDscrp(st.getString(8));
				itemVO.setItemsQty(st.getInt(9));
				itemVO.setItemStatus(st.getInt(10));
				sugItems.add(itemVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return sugItems;
	}
	
	public List<ItemVO> getSugClassItemsDef(String classNo) {
		Connection conn = null;
		ItemVO itemVO = null;
		List<ItemVO> sugItems = null;
		try {
			conn = ds.getConnection();
			
			sugItems = new ArrayList<ItemVO>();
			PreparedStatement pstmt = conn.prepareStatement(GET_SUG_CLASS_ITEMS_DEF);
			pstmt.setInt(1, Integer.valueOf(classNo));
			ResultSet st = pstmt.executeQuery();
			
			while(st.next()){
				itemVO = new ItemVO();
				itemVO.setItemClassNo(st.getInt(1));
				itemVO.setItemNo(st.getInt(2));
				itemVO.setItemName(st.getString(3));
				itemVO.setPrice(st.getDouble(4));
				itemVO.setDiscount(st.getDouble(5));
				itemVO.setOnSaleTime(st.getDate(6));
				itemVO.setOffSaleTime(st.getDate(7));
				itemVO.setItemDscrp(st.getString(8));
				itemVO.setItemsQty(st.getInt(9));
				itemVO.setItemStatus(st.getInt(10));
				sugItems.add(itemVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return sugItems;
	}
	public List<ItemVO> getSugClassItemsCtoF(String classNo) {
		Connection conn = null;
		ItemVO itemVO = null;
		List<ItemVO> sugItems = null;
		try {
			conn = ds.getConnection();
			
			sugItems = new ArrayList<ItemVO>();
			PreparedStatement pstmt = conn.prepareStatement(GET_SUG_CLASS_ITEMS_CTOF);
			pstmt.setInt(1, Integer.valueOf(classNo));
			ResultSet st = pstmt.executeQuery();
			
			while(st.next()){
				itemVO = new ItemVO();
				itemVO.setItemClassNo(st.getInt(1));
				itemVO.setItemNo(st.getInt(2));
				itemVO.setItemName(st.getString(3));
				itemVO.setPrice(st.getDouble(4));
				itemVO.setDiscount(st.getDouble(5));
				itemVO.setOnSaleTime(st.getDate(6));
				itemVO.setOffSaleTime(st.getDate(7));
				itemVO.setItemDscrp(st.getString(8));
				itemVO.setItemsQty(st.getInt(9));
				itemVO.setItemStatus(st.getInt(10));
				sugItems.add(itemVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return sugItems;
	}
	
	public List<ItemVO> getSugFatherClassItems(String classNo) {
		Connection conn = null;
		ItemVO itemVO = null;
		List<ItemVO> sugItems = null;
		try {
			conn = ds.getConnection();
			
			sugItems = new ArrayList<ItemVO>();
			PreparedStatement pstmt = conn.prepareStatement(GET_SUG_FATHERCLASS_ITEMS);
			pstmt.setInt(1, Integer.valueOf(classNo));
			ResultSet st = pstmt.executeQuery();
			
			while(st.next()){
				itemVO = new ItemVO();
				itemVO.setItemClassNo(st.getInt(1));
				itemVO.setItemNo(st.getInt(2));
				itemVO.setItemName(st.getString(3));
				itemVO.setPrice(st.getDouble(4));
				itemVO.setDiscount(st.getDouble(5));
				itemVO.setOnSaleTime(st.getDate(6));
				itemVO.setOffSaleTime(st.getDate(7));
				itemVO.setItemDscrp(st.getString(8));
				itemVO.setItemsQty(st.getInt(9));
				itemVO.setItemStatus(st.getInt(10));
				sugItems.add(itemVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return sugItems;
	}
	
//	public List<ItemVO> getSugUserItems(List<String> sugClassList) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet st = null;
//		ItemVO itemVO = null;
//		List<ItemVO> sugItemsList = null;
//		try {
//			conn = ds.getConnection();
//			
//			sugItemsList = new ArrayList<ItemVO>();
//			
//			for(String i:sugClassList){
//				pstmt = conn.prepareStatement(GET_SUG_ITEMS);
//				pstmt.setString(1, i);
//				st = pstmt.executeQuery();
//				
//				while(st.next()){
//					itemVO = new ItemVO();
//					itemVO.setItemClassNo(st.getInt(1));
//					itemVO.setItemNo(st.getInt(2));
//					itemVO.setItemName(st.getString(3));
//					itemVO.setPrice(st.getDouble(4));
//					itemVO.setDiscount(st.getDouble(5));
//					itemVO.setOnSaleTime(st.getDate(6));
//					itemVO.setOffSaleTime(st.getDate(7));
//					itemVO.setItemDscrp(st.getString(8));
//					itemVO.setItemsQty(st.getInt(9));
//					itemVO.setItemStatus(st.getInt(10));
//					sugItemsList.add(itemVO);
//				}
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null)
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//		}
//		return sugItemsList;
//	}
	
	public List<ItemVO> getSugUserItems(Set<String> itemNoSet, String classNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet st = null;
		StringBuffer stBuf = new StringBuffer();
		StringBuffer psBuf = new StringBuffer();
		ItemVO itemVO = null;
		List<ItemVO> sugItemsList = null;
		try {
			conn = ds.getConnection();
			
			sugItemsList = new ArrayList<ItemVO>();
			Iterator<String> iNoIto = itemNoSet.iterator();
			while(iNoIto.hasNext()){
				stBuf.append(" OR itemno = " + iNoIto.next());
			}
			stBuf.delete(0, 3);
			psBuf.append(GET_SUG_ITEMS);
			
			psBuf.insert(psBuf.indexOf("?", 0),stBuf);
			psBuf.delete(psBuf.indexOf("?"),psBuf.indexOf("?")+1);
			psBuf.insert(psBuf.indexOf("?", 0),stBuf);
			psBuf.delete(psBuf.indexOf("?"),psBuf.indexOf("?")+1);
			
		
				pstmt = conn.prepareStatement(psBuf.toString());
				pstmt.setString(1, classNo);
				st = pstmt.executeQuery();
				
				while(st.next()){
					itemVO = new ItemVO();
					itemVO.setItemClassNo(st.getInt(1));
					itemVO.setItemNo(st.getInt(2));
					itemVO.setItemName(st.getString(3));
					itemVO.setPrice(st.getDouble(4));
					itemVO.setDiscount(st.getDouble(5));
					itemVO.setOnSaleTime(st.getDate(6));
					itemVO.setOffSaleTime(st.getDate(7));
					itemVO.setItemDscrp(st.getString(8));
					itemVO.setItemsQty(st.getInt(9));
					itemVO.setItemStatus(st.getInt(10));
					sugItemsList.add(itemVO);
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return sugItemsList;
	}
	
	public List<ItemVO> getPerPageItem(int classNo, int startNo, int endNo) {
		Connection conn = null;
		ItemVO itemVO = null;
		List<ItemVO> allItems = null;
		try {
			conn = ds.getConnection();
			
			allItems = new ArrayList<ItemVO>();
			PreparedStatement pstmt = conn.prepareStatement(GET_PER_PAGE_ITEM);
			pstmt.setInt(1, classNo);
			pstmt.setInt(2, startNo);
			pstmt.setInt(3, endNo);
			ResultSet st = pstmt.executeQuery();
			
			while(st.next()){
				itemVO = new ItemVO();
				itemVO.setItemClassNo(st.getInt(1));
				itemVO.setItemNo(st.getInt(2));
				itemVO.setItemName(st.getString(3));
				itemVO.setPrice(st.getDouble(4));
				itemVO.setDiscount(st.getDouble(5));
				itemVO.setOnSaleTime(st.getDate(6));
				itemVO.setOffSaleTime(st.getDate(7));
				itemVO.setItemDscrp(st.getString(8));
				itemVO.setItemsQty(st.getInt(9));
				itemVO.setItemStatus(st.getInt(10));
				allItems.add(itemVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return allItems;
	}

	public List<ItemVO> getPerPageClassItem(int classNo, int startNo, int endNo) {
		Connection conn = null;
		ItemVO itemVO = null;
		List<ItemVO> allItems = null;
		try {
			conn = ds.getConnection();
			
			allItems = new ArrayList<ItemVO>();
			PreparedStatement pstmt = conn.prepareStatement(GET_PER_PAGE_CLASSITEM);
			pstmt.setInt(1, classNo);
			pstmt.setInt(2, startNo);
			pstmt.setInt(3, endNo);
			ResultSet st = pstmt.executeQuery();
//			StringBuffer str = new StringBuffer().append("SELECT * FROM (SELECT *, ROW_NUMBER() OVER( ORDER BY price) AS number FROM  item, (SELECT itemclassno AS itno FROM itemclass WHERE fatherclassno in");
//			
//			if(!st.next()){
//				str.append(" (SELECT itemclassno FROM itemclass WHERE fatherclassno = ?) )AS t2 WHERE t2.itno = item.itemclassno AND itemstatus = 1 AND itemsQty != 0)AS onSaleItems WHERE number BETWEEN ? AND ?"); 
//				pstmt = conn.prepareStatement(str.toString());
//				pstmt.setInt(1, classNo);
//				pstmt.setInt(2, startNo);
//				pstmt.setInt(3, endNo);
//				st = pstmt.executeQuery();
//			}
			
			while(st.next()){
				itemVO = new ItemVO();
				itemVO.setItemClassNo(st.getInt(1));
				itemVO.setItemNo(st.getInt(2));
				itemVO.setItemName(st.getString(3));
				itemVO.setPrice(st.getDouble(4));
				itemVO.setDiscount(st.getDouble(5));
				itemVO.setOnSaleTime(st.getDate(6));
				itemVO.setOffSaleTime(st.getDate(7));
				itemVO.setItemDscrp(st.getString(8));
				itemVO.setItemsQty(st.getInt(9));
				itemVO.setItemStatus(st.getInt(10));
				allItems.add(itemVO);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return allItems;
	}
	
	public Integer getCount(int classNo) {
		Connection conn = null;
		Integer count = null;
		try {
			conn = ds.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(GET_COUNT);
			pstmt.setInt(1, classNo);
			ResultSet st = pstmt.executeQuery();
			if(st.next())
				count = st.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return count;
	}
	
	
	public void insertItemAndMedia(ItemVO item, List<ItemMediaVO> iMediaList){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {

			conn = ds.getConnection();

			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(INSERT);
			pstmt.setInt(1, item.getItemClassNo());
			pstmt.setString(2, item.getItemName());
			pstmt.setDouble(3, item.getPrice());
			pstmt.setDouble(4, item.getDiscount());
			pstmt.setDate(5, item.getOnSaleTime());
			pstmt.setDate(6, item.getOffSaleTime());
			pstmt.setString(7, item.getItemDscrp());
			pstmt.setInt(8, item.getItemsQty());
			pstmt.setInt(9, item.getItemStatus());
			int count = pstmt.executeUpdate();
			
			Integer itemNo = 0;
			pstmt = conn.prepareStatement("SELECT MAX(itemno) FROM item");
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
				itemNo = rs.getInt(1);
			
			int count2 = 0;
			for(ItemMediaVO iMediaVO:iMediaList){
			pstmt = conn.prepareStatement("INSERT INTO itemmedia VALUES(" + itemNo +" , ?, ?, ?, ?)");
			pstmt.setInt(1, iMediaVO.getItemMediaNo());
			pstmt.setBytes(2, iMediaVO.getItemMedia());
			pstmt.setString(3, iMediaVO.getMediaType());
			pstmt.setString(4, iMediaVO.getMediaDscrp());
			count2 += pstmt.executeUpdate();
			}
			
			conn.commit();

			System.out.println("成功新增 " + count + " 筆商品資料, " + count2 + "筆商品多媒體說明檔");

			conn.setAutoCommit(true);
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

	}


public void insertItemAndMedia(ItemVO item, Map<byte[], String> mMap){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {

			conn = ds.getConnection();

			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(INSERT);
			pstmt.setInt(1, item.getItemClassNo());
			pstmt.setString(2, item.getItemName());
			pstmt.setDouble(3, item.getPrice());
			pstmt.setDouble(4, item.getDiscount());
			pstmt.setDate(5, item.getOnSaleTime());
			pstmt.setDate(6, item.getOffSaleTime());
			pstmt.setString(7, item.getItemDscrp());
			pstmt.setInt(8, item.getItemsQty());
			pstmt.setInt(9, item.getItemStatus());
			int count = pstmt.executeUpdate();
			
			Integer itemNo = 0;
			pstmt = conn.prepareStatement("SELECT MAX(itemno) FROM item");
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
				itemNo = rs.getInt(1);
			
			int count2 = 0;
			int iMediaNo = 1;
			Set<byte[]> kSet = mMap.keySet();
			for(byte[] k:kSet){
			pstmt = conn.prepareStatement("INSERT INTO itemmedia VALUES(" + itemNo +" , ?, ?, ?, ?)");
			pstmt.setInt(1, iMediaNo);
			pstmt.setBytes(2, k);
			pstmt.setString(3, mMap.get(k));
			pstmt.setString(4, "");
			count2 += pstmt.executeUpdate();
			iMediaNo++;
			}
			
			conn.commit();

			System.out.println("成功新增 " + count + " 筆商品資料, " + count2 + "筆商品多媒體說明檔");

			conn.setAutoCommit(true);
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

	}
}
