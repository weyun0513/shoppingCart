package proj.basic.member.model;

import java.util.List;

public interface MemberDAO_interface {
//	public void multi_Insert(File itemFile);
	public void insert(MemberVO memberVO);
	public void update(MemberVO memberVO);
	public void delete(MemberVO memberVO);
	public MemberVO findByAccount(String memberID);
	public List<MemberVO> getAll();
}
