package proj.basic.itemClass.model;

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

public class ItemClassDAO implements ItemClass_interface {

	DataSource ds;
	public ItemClassDAO() throws NamingException{
		Context cntxt = new InitialContext();
		ds = (DataSource)cntxt.lookup(InitMall.LOOK_UP_DATA);
	}

	// itemclassno tinyint primary key, /*商品類別編號(父類別名稱)*/
	// classname nvarchar(7), /*類別名稱*/
	// classstatus tinyint, /*類別狀態*/
	// fatherClassno tinyint /*父類別代號*/

	private static final String INSERT = "INSERT INTO itemclass VALUES(?, ?, ?)";
	private static final String UPDATE = "UPDATE itemclass SET classname = ?, classstatus = ?, fatherClassno = ? WHERE itemclassno = ?";
	private static final String FIND_BY_PRIMARYKEY = "SELECT * FROM itemclass WHERE itemclassno = ?";
	private static final String DELETE = "DELETE itemclass WHERE itemclassno = ?";
	private static final String CHG_DELETE = "UPDATE itemclass SET classstatus = ? WHERE itemclassno = ?";
	private static final String SELECT_CHILD = "SELECT * FROM itemclass WHERE classstatus = 1 AND fatherclassno = ?";
	private static final String FIND_ITEM_BY_CLASSNO = "SELECT * FROM item WHERE classstatus = 1 AND itemclassno = ?";
	private static final String FIND_BY_CLASSNO = "SELECT * FROM itemclass WHERE classstatus = 1 AND itemclassno = ?";
//	private static final String FIND_BY_CLASSNAME_FROM_ITEM = "SELECT * FROM item WHERE itemclassname = ?";
	private static final String FIND_BY_CLASSNAME = "SELECT * FROM itemclass WHERE classname = ?";
	private static final String FIND_F_BY_CLASSNO = "SELECT * FROM itemclass WHERE itemclassno = ?";
	private static final String GET_ALL_FATHER_ALIVE = "SELECT * FROM itemclass WHERE classstatus = 1 AND fatherclassno = 0";
	private static final String GET_ALL_CLASSVO = "SELECT * FROM itemclass WHERE classstatus = 1";
	private static final String GET_CHLD = "SELECT * FROM itemclass WHERE fatherclassno = ? AND classstatus = 1";

	
	public void truncate(){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
//			TRUNCATE  table BackAccount;TRUNCATE  table adv;
			pstmt = conn.prepareStatement("TRUNCATE  table BackAccount;TRUNCATE  table advtable;TRUNCATE  table Evaluate;TRUNCATE  table bookmark;TRUNCATE  table loginRecord;TRUNCATE  table blacklist;TRUNCATE  table orderdetail;TRUNCATE  table orderlist;TRUNCATE  table itemmedia;TRUNCATE  table item;TRUNCATE  table itemclass;TRUNCATE  table member;");
			pstmt.executeUpdate();
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
	public void multi_Insert(List<ItemClassVO> itemClassList) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			int count = 0;
			pstmt = conn.prepareStatement("TRUNCATE  table BackAccount;TRUNCATE  table adv;TRUNCATE  table Evaluate;TRUNCATE  table bookmark;TRUNCATE  table loginRecord;TRUNCATE  table blacklist;TRUNCATE  table orderdetail;TRUNCATE  table orderlist;TRUNCATE  table itemmedia;TRUNCATE  table item;TRUNCATE  table itemclass;TRUNCATE  table member;");
			pstmt.execute();
			for (ItemClassVO i : itemClassList) {

				pstmt = conn.prepareStatement(INSERT);
				pstmt.setString(1, i.getClassName());
				pstmt.setInt(2, i.getClassStatus());
				pstmt.setInt(3, i.getFatherClassno());
				pstmt.executeUpdate();
				count++;
			}

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
	
	public void multi_Insert(File itemClassFile) {
		InputStream in = null;
		List<ItemClassVO> list = new ArrayList<ItemClassVO>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			in = new FileInputStream(itemClassFile);
			XSSFWorkbook book = new XSSFWorkbook(in);
			XSSFRow r = null;
			int j = 1;
			ItemClassVO itemClassVO = null;
			while (book.getSheetAt(1).getRow(j) != null) {
				
				r = book.getSheetAt(1).getRow(j);
				
				itemClassVO = new ItemClassVO();

				itemClassVO.setClassName(r.getCell(0).toString());// i=0,// 商品所屬類別編號
				itemClassVO.setClassStatus(Integer.valueOf(r.getCell(1).toString().substring(0, r.getCell(1).toString().indexOf('.'))));
				itemClassVO.setFatherClassno(Integer.valueOf(r.getCell(2).toString().substring(0, r.getCell(2).toString().indexOf('.'))));
				list.add(itemClassVO);
				
				j++;
			}
			
			conn = ds.getConnection();

			conn.setAutoCommit(false);
			int count = 0;
			for(ItemClassVO i:list){
				pstmt = conn.prepareStatement(INSERT);
				pstmt.setString(1, i.getClassName());
				pstmt.setInt(2, i.getClassStatus());
				pstmt.setInt(3, i.getFatherClassno());

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

	@Override
	public void insert(ItemClassVO itemClass) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();

			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, itemClass.getClassName());
			pstmt.setInt(2, itemClass.getClassStatus());
			pstmt.setInt(3, itemClass.getFatherClassno());
			pstmt.executeUpdate();

			conn.commit();
			System.out.println("新增成功");
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
	public void update(ItemClassVO itemClass) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setString(1, itemClass.getClassName());
			pstmt.setInt(2, itemClass.getClassStatus());
			pstmt.setInt(3, itemClass.getFatherClassno());
			pstmt.setInt(4, itemClass.getItemClassNo());
			pstmt.executeUpdate();
			conn.commit();

			System.out.println("修改成功");
			
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
	public void delete(ItemClassVO itemClass) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(SELECT_CHILD);
			pstmt.setInt(1, itemClass.getItemClassNo());
			ResultSet st = pstmt.executeQuery();

			if (!st.next()) {
				pstmt1 = conn.prepareStatement(FIND_BY_CLASSNO);
				pstmt1.setInt(1, itemClass.getItemClassNo());
				st = pstmt1.executeQuery();
				if (!st.next()) {
					conn.setAutoCommit(false);
					// 實際刪除
					// pstmt2 = conn.prepareStatement(DELETE);
					// pstmt2.setInt(1, itemClass.getItemClassNo());

					// 更改狀態為0=>下架
					pstmt2 = conn.prepareStatement(CHG_DELETE);
					pstmt2.setInt(1, 0);
					pstmt2.setInt(2, itemClass.getItemClassNo());
					pstmt2.executeUpdate();
					conn.commit();
					System.out.println("成功刪除(下架)" + itemClass.getClassName()
							+ "類");
				} else
					System.out.println("尚有商品屬於此類別");
			} else
				System.out.println("該類別為其他類別之父類別");

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

			if (pstmt1 != null)
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			if (pstmt2 != null)
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
	public ItemClassVO findByPrimaryKey(Integer itemClassNo) {
		ItemClassVO itemClassVO = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(FIND_BY_PRIMARYKEY);
			pstmt.setInt(1, itemClassNo);
			ResultSet st = pstmt.executeQuery();

			if (st.next()) {
				itemClassVO = new ItemClassVO();
				itemClassVO.setItemClassNo(st.getInt(1));
				itemClassVO.setClassName(st.getString(2));
				itemClassVO.setClassStatus(st.getInt(3));
				itemClassVO.setFatherClassno(st.getInt(4));
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
		return itemClassVO;
	}
	
	
	public List<ItemClassVO> findItemByClassNo(Integer ClassNo) {
		List<ItemClassVO> list = new ArrayList<ItemClassVO>();
		ItemClassVO itemClassVO = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(FIND_ITEM_BY_CLASSNO);
			pstmt.setInt(1, ClassNo);
			ResultSet st = pstmt.executeQuery();

			while (st.next()) {
				itemClassVO = new ItemClassVO();
				itemClassVO.setItemClassNo(st.getInt(1));
				itemClassVO.setClassName(st.getString(2));
				itemClassVO.setClassStatus(st.getInt(3));
				itemClassVO.setFatherClassno(st.getInt(4));
				list.add(itemClassVO);
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
	
	
	public ItemClassVO findClassByClassNo(Integer ClassNo) {
		ItemClassVO itemClassVO = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(FIND_BY_CLASSNO);
			pstmt.setInt(1, ClassNo);
			ResultSet st = pstmt.executeQuery();

			if (st.next()) {
				itemClassVO = new ItemClassVO();
				itemClassVO.setItemClassNo(st.getInt(1));
				itemClassVO.setClassName(st.getString(2));
				itemClassVO.setClassStatus(st.getInt(3));
				itemClassVO.setFatherClassno(st.getInt(4));
			}
			System.out.println(itemClassVO.getClassName());
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
		return itemClassVO;
	}

	@Override
	public List<ItemClassVO> getAll() {
		List<ItemClassVO> list = new ArrayList<ItemClassVO>();
		ItemClassVO itemClassVO = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(GET_ALL_FATHER_ALIVE);
			ResultSet st = pstmt.executeQuery();

			while (st.next()) {
				itemClassVO = new ItemClassVO();
				itemClassVO.setItemClassNo(st.getInt(1));
				itemClassVO.setClassName(st.getString(2));
				itemClassVO.setClassStatus(st.getInt(3));
				itemClassVO.setFatherClassno(st.getInt(4));
				list.add(itemClassVO);
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
	
	//縮小搜尋類別用的
	public List<ItemClassVO> findEveryChild(Integer classNo) {
		 List<ItemClassVO> list = new ArrayList<ItemClassVO>();
		ItemClassVO itemClassVO = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
			
			ResultSet st = null;
			PreparedStatement pstmt = conn.prepareStatement(SELECT_CHILD);
			pstmt.setInt(1, classNo);
			st = pstmt.executeQuery();
			
			while(st.next()){
				itemClassVO = new ItemClassVO();
				itemClassVO.setItemClassNo(st.getInt(1));
				itemClassVO.setClassName(st.getString(2));
				itemClassVO.setClassStatus(st.getInt(3));
				itemClassVO.setFatherClassno(st.getInt(4));
				list.add(itemClassVO);
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


	//用類別名稱尋找下一層
	public List<ItemClassVO> findOneChild(String className) {
		 List<ItemClassVO> list = new ArrayList<ItemClassVO>();
		ItemClassVO itemClassVO = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
			
			ResultSet st = null;
			
			PreparedStatement pstmt = conn.prepareStatement(FIND_BY_CLASSNAME);
			pstmt.setString(1, className);
			st = pstmt.executeQuery();

			if (st.next()) {
				itemClassVO = new ItemClassVO();
				itemClassVO.setItemClassNo(st.getInt(1));
				itemClassVO.setClassName(st.getString(2));
				itemClassVO.setClassStatus(st.getInt(3));
				itemClassVO.setFatherClassno(st.getInt(4));
				}
			
			while(true){
				
				pstmt = conn.prepareStatement(SELECT_CHILD);
				pstmt.setInt(1, itemClassVO.getItemClassNo());
				st = pstmt.executeQuery();

				if (st.next()) {
					itemClassVO = new ItemClassVO();
					itemClassVO.setItemClassNo(st.getInt(1));
					itemClassVO.setClassName(st.getString(2));
					itemClassVO.setClassStatus(st.getInt(3));
					itemClassVO.setFatherClassno(st.getInt(4));
					list.add(itemClassVO);
					}else
						break;
				
				pstmt.close();
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
	public ItemClassVO findFather(Integer itemClassNo) {
		ItemClassVO itemClassVO = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
			
			ResultSet st = null;
			
			PreparedStatement pstmt = conn.prepareStatement(FIND_F_BY_CLASSNO);
			pstmt.setInt(1, itemClassNo);
			st = pstmt.executeQuery();
			
			if (st.next()) {
				itemClassVO = new ItemClassVO();
				itemClassVO.setItemClassNo(st.getInt(1));
				itemClassVO.setClassName(st.getString(2));
				itemClassVO.setClassStatus(st.getInt(3));
				itemClassVO.setFatherClassno(st.getInt(4));
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
		return itemClassVO;
	}
	
	public List<ItemClassVO> findChild(Integer fatherClassno) {
		List<ItemClassVO> list = new ArrayList<ItemClassVO>();
		ItemClassVO itemClassVO = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
			
			ResultSet st = null;
			
			PreparedStatement pstmt = conn.prepareStatement(GET_CHLD);
			pstmt.setInt(1, fatherClassno);
			st = pstmt.executeQuery();
			
			while (st.next()) {
				itemClassVO = new ItemClassVO();
				itemClassVO.setItemClassNo(st.getInt(1));
				itemClassVO.setClassName(st.getString(2));
				itemClassVO.setClassStatus(st.getInt(3));
				itemClassVO.setFatherClassno(st.getInt(4));
				list.add(itemClassVO);
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
	public List<ItemClassVO> getAllClass() {
		List<ItemClassVO> list = new ArrayList<ItemClassVO>();
		ItemClassVO itemClassVO = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
			
			ResultSet st = null;
			
			PreparedStatement pstmt = conn.prepareStatement(GET_ALL_CLASSVO);
			st = pstmt.executeQuery();
		
			while (st.next()) {
				itemClassVO = new ItemClassVO();
				itemClassVO.setItemClassNo(st.getInt(1));
				itemClassVO.setClassName(st.getString(2));
				itemClassVO.setClassStatus(st.getInt(3));
				itemClassVO.setFatherClassno(st.getInt(4));
				list.add(itemClassVO);
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


}
