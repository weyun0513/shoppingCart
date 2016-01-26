package proj.basic.member.model;

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

public class MemberDAO implements MemberDAO_interface {

	DataSource ds;
	public MemberDAO() throws NamingException{
		Context cntxt = new InitialContext();
		ds = (DataSource)cntxt.lookup(InitMall.LOOK_UP_DATA);
	}

	private static final String INSERT = "INSERT INTO member VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	//密碼未來要分開? photo沒放入(分開?) 紅利, 儲值金, 黑名單狀態, 錯誤次數->未放入
	
	/**2014/8/5 修改增加bonus deposit */
	private static final String UPDATE = "UPDATE member SET ChineseName = ?,Gender=?,Birthday=?,Email=?, Pwd=?, Addr=?, Phone=?, Photo=?, bonus=?, deposit=? WHERE Account = ?";
	private static final String UPDATE_STATUS = "UPDATE member  SET isBlock = ? WHERE memberID = ?";
	private static final String RESET_PWD = "UPDATE member  SET pwd = ? WHERE memberID = ?";
	private static final String UPDATE_WRONG_TIMES = "UPDATE member  SET wrongtimes = ? WHERE memberID = ?";
	private static final String FIND_BY_ACCOUNT = "SELECT * FROM member WHERE account = ?";
	private static final String FIND_BY_MEMBERID = "SELECT * FROM member WHERE memberID = ?";
	//delete 要刪除 or 改會員狀態? =>目前刪除
	private static final String DELETE = "DELETE member WHERE memberid = ?";
	private static final String GET_ALL = "SELECT * FROM member";
	
	public byte[] getImg(String path){
		File file = new File(path);
		InputStream in = null;
		byte[] bFile = null;
		try {
			in = new FileInputStream(file);
			bFile = new byte[(int)file.length()];
			in.read(bFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(in!=null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return bFile;
	}
	@Override
	public void insert(MemberVO memberVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1,memberVO.getChineseName());
			pstmt.setString(2, memberVO.getGender());
			pstmt.setDate(3, memberVO.getBirthday());
			pstmt.setString(4, memberVO.getEmail());
			pstmt.setString(5, memberVO.getAccount());
			pstmt.setString(6, memberVO.getPwd());
			pstmt.setString(7, memberVO.getAddr());
			pstmt.setDate(8, memberVO.getRegistDate());
			pstmt.setString(9, memberVO.getPhone());
			pstmt.setInt(10, memberVO.getBonus());
			pstmt.setInt(11, memberVO.getDeposit());
			pstmt.setBytes(12, memberVO.getPhoto());
			pstmt.setInt(13, memberVO.getIsBlock());
			pstmt.setInt(14, memberVO.getWrongtimes());

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
	public void update(MemberVO memberVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();

			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setString(1,memberVO.getChineseName());
			pstmt.setString(2, memberVO.getGender());
			pstmt.setDate(3, memberVO.getBirthday());
			pstmt.setString(4, memberVO.getEmail());
			pstmt.setString(5, memberVO.getPwd());
			pstmt.setString(6, memberVO.getAddr());
			pstmt.setString(7, memberVO.getPhone());
			pstmt.setBytes(8, memberVO.getPhoto());
			pstmt.setInt(9, memberVO.getBonus());
			pstmt.setInt(10, memberVO.getDeposit());
			pstmt.setString(11, memberVO.getAccount());
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
	public void resetPwd(String pwd, String memberID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(RESET_PWD);
			pstmt.setString(1, pwd);
			pstmt.setString(2, memberID);
			pstmt.executeUpdate();
			
			System.out.println("密碼修改成功");
			
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

	public void updateStatus(MemberVO memberVO, Integer status) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();

			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(UPDATE_STATUS);
			pstmt.setInt(1, status);
			pstmt.setInt(2, memberVO.getMemberID());
			pstmt.executeUpdate();

			conn.commit();
			System.out.println("修改會員 = " + memberVO.getMemberID() + "狀態成功");
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
	
	public void updateWrongTimes(MemberVO memberVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();

			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(UPDATE_WRONG_TIMES);
			pstmt.setInt(1, memberVO.getWrongtimes());
			pstmt.setInt(2, memberVO.getMemberID());
			pstmt.executeUpdate();

			conn.commit();
			System.out.println("修改錯誤次數 = "+ memberVO.getWrongtimes() +"成功");
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
	public void delete(MemberVO memberVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ds.getConnection();
			
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setInt(1, memberVO.getMemberID());
			int count = pstmt.executeUpdate();

			conn.commit();
			
			if(count == 1)
				System.out.println("刪除成功");
			
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
	public MemberVO findByAccount(String account) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		MemberVO memberVO = null;
		try {
			conn = ds.getConnection();
			
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(FIND_BY_ACCOUNT);
			pstmt.setString(1, account);
			ResultSet rs = pstmt.executeQuery();
			conn.commit();

			if(rs.next()){
				memberVO = new MemberVO();
				memberVO.setMemberID(rs.getInt(1));
				memberVO.setChineseName(rs.getString(2));
				memberVO.setGender(rs.getString(3));
				memberVO.setBirthday(rs.getDate(4));
				memberVO.setEmail(rs.getString(5));
				memberVO.setAccount(rs.getString(6));
				memberVO.setPwd(rs.getString(7));
				memberVO.setAddr(rs.getString(8));
				memberVO.setRegistDate(rs.getDate(9));
				memberVO.setPhone(rs.getString(10));
				memberVO.setBonus(rs.getInt(11));
				memberVO.setDeposit(rs.getInt(12));
				memberVO.setPhoto(rs.getBytes(13));
				memberVO.setIsBlock(rs.getInt(14));
				memberVO.setWrongtimes(rs.getInt(15));
			}
			
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
		return memberVO;
	}
	
	public MemberVO findByID(String memberID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		MemberVO memberVO = null;
		try {
			conn = ds.getConnection();
			
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(FIND_BY_MEMBERID);
			pstmt.setString(1, memberID);
			ResultSet rs = pstmt.executeQuery();
			conn.commit();
			
			if(rs.next()){
				memberVO = new MemberVO();
				memberVO.setMemberID(rs.getInt(1));
				memberVO.setChineseName(rs.getString(2));
				memberVO.setGender(rs.getString(3));
				memberVO.setBirthday(rs.getDate(4));
				memberVO.setEmail(rs.getString(5));
				memberVO.setAccount(rs.getString(6));
				memberVO.setPwd(rs.getString(7));
				memberVO.setAddr(rs.getString(8));
				memberVO.setRegistDate(rs.getDate(9));
				memberVO.setPhone(rs.getString(10));
				memberVO.setBonus(rs.getInt(11));
				memberVO.setDeposit(rs.getInt(12));
				memberVO.setPhoto(rs.getBytes(13));
				memberVO.setIsBlock(rs.getInt(14));
				memberVO.setWrongtimes(rs.getInt(15));
			}
			
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
		return memberVO;
	}

	@Override
	public List<MemberVO> getAll() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO memberVO = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(GET_ALL);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				memberVO = new MemberVO();
				memberVO.setMemberID(rs.getInt(1));
				memberVO.setChineseName(rs.getString(2));
				memberVO.setGender(rs.getString(3));
				memberVO.setBirthday(rs.getDate(4));
				memberVO.setEmail(rs.getString(5));
				memberVO.setAccount(rs.getString(6));
				memberVO.setPwd(rs.getString(7));
				memberVO.setAddr(rs.getString(8));
				memberVO.setRegistDate(rs.getDate(9));
				memberVO.setPhone(rs.getString(10));
				memberVO.setBonus(rs.getInt(11));
				memberVO.setDeposit(rs.getInt(12));
				memberVO.setPhoto(rs.getBytes(13));
				memberVO.setIsBlock(rs.getInt(14));
				memberVO.setWrongtimes(rs.getInt(15));
				list.add(memberVO);
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
