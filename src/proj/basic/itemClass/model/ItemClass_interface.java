package proj.basic.itemClass.model;

import java.util.List;

import proj.basic.item.model.ItemVO;

public interface ItemClass_interface {
	public void multi_Insert(List<ItemClassVO> itemClassList);
	public void insert(ItemClassVO itemClass);
	public void update(ItemClassVO itemClass);
	public void delete(ItemClassVO itemClass);
	public ItemClassVO findByPrimaryKey(Integer itemNo);
	public List<ItemClassVO> getAll();
}
