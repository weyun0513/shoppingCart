package proj.basic.itemmedia.model;

import java.io.File;
import java.util.List;

public interface ItemMediaDAO_interface {
	public void multi_Insert(File itemFile);
	public void insert(ItemMediaVO itemMediaVO);
	public void update(ItemMediaVO itemMediaVO);
	public void delete(ItemMediaVO itemMediaVO);
	public ItemMediaVO findByPrimaryKey(Integer itemNo, Integer itemMediaNo);
	public List<ItemMediaVO> getAll();
}
