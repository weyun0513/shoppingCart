package proj.basic.itemmedia.model;

import initMall.InitMall;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ItemMediaDAO implements ItemMediaDAO_interface {

	DataSource ds;
	public ItemMediaDAO() throws NamingException{
		Context cntxt = new InitialContext();
		ds = (DataSource)cntxt.lookup(InitMall.LOOK_UP_DATA);
	}

	private static final String INSERT = "INSERT INTO itemmedia VALUES(?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE itemmedia SET itemmedia = ? , medaidscrp = ? WHERE itemno = ? and itemmediano = ?";
	private static final String FIND_BY_PRIMARYKEY = "SELECT * FROM itemmedia WHERE itemno = ? and itemmediano = ?";
	private static final String FIND_BY_ITEMNO = "SELECT * FROM itemmedia WHERE itemno = ?";
	private static final String DELETE = "DELETE itemmedia WHERE WHERE itemno = ? and itemmediano = ?";
	private static final String GET_ALL = "SELECT * FROM itemmedia";
	private static final String GET_ITEMMEDIANO = "SELECT count(*) FROM itemmedia WHERE itemno = ?";
	

//	public static void main(String[] args) {
//		InputStream in = null;
//		ItemMediaDAO itemMediaDao = new ItemMediaDAO();
//		try {
//// -insert---------------------------------------------------------------------
//			ItemMediaVO itemMediaVO = new ItemMediaVO();
//			itemMediaVO.setItemNo(20);
//			itemMediaVO.setItemMediaNo(3);
//			
//			 File file = new File(
//			 "E:\\PHOTO\\1379226_10151930719404328_1711627124_n.jpg");
//			 in = new FileInputStream(file);
//			 byte[] bFile = new byte[(int) file.length()];
//			
//			 in.read(bFile);
//			 itemMediaVO.setItemMedia(bFile);
//			 itemMediaDao.insert(itemMediaVO);
//			
//		 } catch (IOException e) {
//		 e.printStackTrace();
//
//			
//// -multi_Insert----------------------------------------------------------------------------
////			File file = new File("E:\\itemMedia.xlsx");
////			itemMediaDao.multi_Insert(file);
//		
//			
//// -delete----------------------------------------------------------------------------
////			ItemMediaVO itemMediaVO = new ItemMediaVO();
////			itemMediaVO.setItemNo(1);
////			itemMediaVO.setItemMediaNo(2);
////			itemMediaDao.delete(itemMediaVO);
//			
//// -update----------------------------------------------------------------------------
////			ItemMediaVO itemMediaVO = itemMediaDao.findByPrimaryKey(1, 2);
//////模擬丟入更換的多媒體檔
////			itemMediaDao.prcFile("E:\\01descrip01");//此動作機會是在service執行
////			itemMediaVO.setItemMedia(new byte[1024]);//此負責接收處理完的多媒體檔(byte[])直接丟入DB
////			itemMediaDao.update(itemMediaVO);
////			if(itemMediaVO != null){
////				itemMediaVO.getItemNo();
////				itemMediaVO.getItemMediaNo();
////				itemMediaVO.getItemMedia();
////			}
//
//			
//// -findByPrimaryKey----------------------------------------------------------------------------
////			ItemMediaVO itemMediaVO = null;
////			itemMediaVO = itemMediaDao.findByPrimaryKey(2, 1);	
////			if(itemMediaVO != null){
////				itemMediaVO.getItemNo();
////				itemMediaVO.getItemMediaNo();
////				itemMediaVO.getItemMedia();
////			}
//			
//// -getAll----------------------------------------------------------------------------
////			List<ItemMediaVO> allItems = itemMediaDao.getAll();
////			for(ItemMediaVO i:allItems){
////				i.getItemNo();
////				i.getItemMediaNo();
////				i.getItemMedia();
////			}
//
//		} finally {
//			if (in != null)
//				try {
//					in.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//		}
//
//	}

	@Override
	public void multi_Insert(File itemFile) {
		InputStream in = null;
		List<ItemMediaVO> list = new ArrayList<ItemMediaVO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			in = new FileInputStream(itemFile);
			XSSFWorkbook book = new XSSFWorkbook(in);
			XSSFRow r = null;
			int j = 1;
			ItemMediaVO itemMediaVO = null;
			while (book.getSheetAt(0).getRow(j) != null) {
				
				r = book.getSheetAt(0).getRow(j);
				
				itemMediaVO = new ItemMediaVO();

				itemMediaVO.setItemNo(Integer.valueOf(r.getCell(0).toString().substring(0, r.getCell(0).toString().indexOf('.'))));// i=0,// 商品所屬類別編號
				itemMediaVO.setItemMediaNo(Integer.valueOf(r.getCell(1).toString().substring(0, r.getCell(1).toString().indexOf('.'))));// i=0,// 商品所屬類別編號
				itemMediaVO.setItemMedia(prcFile(r.getCell(2).toString()));
				itemMediaVO.setMediaType(r.getCell(3).toString());
				itemMediaVO.setMediaDscrp(r.getCell(4).toString());
				list.add(itemMediaVO);
				
				j++;
			}
			
			conn = ds.getConnection();

			conn.setAutoCommit(false);
			int count = 0;
			for(ItemMediaVO i:list){
				pstmt = conn.prepareStatement(INSERT);
				pstmt.setInt(1, i.getItemNo());
				pstmt.setInt(2, i.getItemMediaNo());
				pstmt.setBytes(3, i.getItemMedia());
				pstmt.setString(4, i.getMediaType());
				pstmt.setString(5, i.getMediaDscrp());
				
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
			if(conn!= null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	//未來要在service
	private byte[] prcFile(String path) {
		File file = null;
		InputStream in = null;
		byte[] bFile = null;
		try {
			file = new File(path);
			bFile = new byte[(int)file.length()];
			in = new FileInputStream(file);
			in.read(bFile);
			
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

	@Override
	public void insert(ItemMediaVO itemMediaVO) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {

			conn = ds.getConnection();

			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(INSERT);
			pstmt.setInt(1, itemMediaVO.getItemNo());
			pstmt.setInt(2, itemMediaVO.getItemMediaNo());
			pstmt.setBytes(3, itemMediaVO.getItemMedia());
			pstmt.setString(4, itemMediaVO.getMediaType());
			pstmt.setString(5, itemMediaVO.getMediaDscrp());
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
	public void update(ItemMediaVO itemMediaVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {

			conn = ds.getConnection();

			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setBytes(1, itemMediaVO.getItemMedia());
			pstmt.setString(2, itemMediaVO.getMediaDscrp());
			pstmt.setInt(3, itemMediaVO.getItemNo());
			pstmt.setInt(4, itemMediaVO.getItemMediaNo());
			
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
	public void delete(ItemMediaVO itemMediaVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setInt(1, itemMediaVO.getItemNo());
			int count = pstmt.executeUpdate();
			if(count != 0)
			System.out.println("成功刪除" + count + "筆 (商品編號為 "+itemMediaVO.getItemNo()+")之商品");
			else
				System.out.println("無符合刪除條件 (商品編號為 "+itemMediaVO.getItemNo()+")之商品");
			
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
	public ItemMediaVO findByPrimaryKey(Integer itemNo, Integer itemMediaNo) {
		ItemMediaVO itemMediaVO = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(FIND_BY_PRIMARYKEY);
			pstmt.setInt(1, itemNo);
			pstmt.setInt(2, itemMediaNo);
			ResultSet st = pstmt.executeQuery();
			
			if(st.next()){
				itemMediaVO = new ItemMediaVO();
				itemMediaVO.setItemNo(st.getInt(1));
				itemMediaVO.setItemMediaNo(st.getInt(2));
				itemMediaVO.setItemMedia(st.getBytes(3));
				itemMediaVO.setMediaType(st.getString(4));
				itemMediaVO.setMediaDscrp(st.getString(5));
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
		return itemMediaVO;
	}
	
	public List<ItemMediaVO> getItemMediaAll(Integer itemNo) {
		List<ItemMediaVO> list = new ArrayList<ItemMediaVO>();
		ItemMediaVO itemMediaVO = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(FIND_BY_ITEMNO);
			pstmt.setInt(1, itemNo);
			ResultSet st = pstmt.executeQuery();
			
			while(st.next()){
				itemMediaVO = new ItemMediaVO();
				itemMediaVO.setItemNo(st.getInt(1));
				itemMediaVO.setItemMediaNo(st.getInt(2));
				itemMediaVO.setItemMedia(st.getBytes(3));
				itemMediaVO.setMediaType(st.getString(4));
				itemMediaVO.setMediaDscrp(st.getString(5));
				list.add(itemMediaVO);
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
		return list;
	}

	@Override
	public List<ItemMediaVO> getAll() {
		Connection conn = null;
		ItemMediaVO itemMediaVO = null;
		List<ItemMediaVO> allItems = null;
		try {
			conn = ds.getConnection();
			
			allItems = new ArrayList<ItemMediaVO>();
			PreparedStatement pstmt = conn.prepareStatement(GET_ALL);
			ResultSet st = pstmt.executeQuery();
			
			while(st.next()){
				itemMediaVO = new ItemMediaVO();
				itemMediaVO.setItemNo(st.getInt(1));
				itemMediaVO.setItemMediaNo(st.getInt(2));
				itemMediaVO.setItemMedia(st.getBytes(3));
				itemMediaVO.setMediaType(st.getString(4));
				itemMediaVO.setMediaDscrp(st.getString(5));
				allItems.add(itemMediaVO);
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

	public Integer getLastMediano(Integer itemNo){
		Integer itemMediaNo = 0;
		Connection conn = null;
		try {
			conn = ds.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement(GET_ITEMMEDIANO);
			pstmt.setInt(1, itemNo);
			ResultSet st = pstmt.executeQuery();
			
			if(st.next()){
				itemMediaNo = st.getInt(1) + 1;
				System.out.println(itemMediaNo);
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
		return itemMediaNo;
		
	}
}
