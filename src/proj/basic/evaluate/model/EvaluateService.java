package proj.basic.evaluate.model;

import java.util.List;


public class EvaluateService {

	private EvaluateDAO_Interface dao;

	public EvaluateService() {
		dao = new EvaluateDAO();
	}

	public void insert(EvaluateVO evaluateVO) {
		dao.insert(evaluateVO);
	}

	public void update(EvaluateVO evaluateVO) {
		dao.update(evaluateVO);
	}

	public void delete(Integer orderNo) {
		dao.delete(orderNo);
	}

	public List<EvaluateVO> findByID(Integer memberid) {
		return dao.findByID(memberid);
	}

	public List<EvaluateVO> findByItem(Integer itemno) {
		return dao.findByItem(itemno);
	}
	
	public EvaluateVO findByCompositeKey(Integer orderno , Integer memberid , Integer itemno){
		return dao.findByCompositeKey(orderno, memberid, itemno);
	}

	public List<Integer> whichEvaluated(Integer orderNo, Integer memberid){
		return dao.whichEvaluated(orderNo, memberid);
	}
	
	public List<EvaluateVO> getAll() {
		return dao.getAll();
	}
}
