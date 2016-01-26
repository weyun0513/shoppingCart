package proj.basic.item.model;

import java.io.File;
import java.util.List;

public interface ItemDAO_interface {
	public void multi_Insert(File itemFile);
	public void insert(ItemVO itemVO);
	public void update(ItemVO itemVO);
	public void delete(ItemVO itemVO);
	public ItemVO findByPrimaryKey(Integer itemNo);
	public List<ItemVO> getAll();
}
