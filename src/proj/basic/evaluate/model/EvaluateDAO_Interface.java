package proj.basic.evaluate.model;

import java.util.List;



public interface EvaluateDAO_Interface {
		
    public void insert(EvaluateVO evaluateVO);
    public void update(EvaluateVO evaluateVO);
    public void delete(Integer orderNo);
    public List<EvaluateVO> findByID(Integer memberid);
    public List<EvaluateVO> findByItem(Integer itemno);
    public EvaluateVO findByCompositeKey(Integer orderno , Integer memberid , Integer itemno);
    public List<Integer> whichEvaluated(Integer orderNo, Integer memberid); //回傳itemno
    public List<EvaluateVO> getAll();

}
