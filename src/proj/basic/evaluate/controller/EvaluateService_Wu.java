package proj.basic.evaluate.controller;

import java.util.List;

import javax.naming.NamingException;

import proj.basic.evaluate.model.EvaluateDAO_Wu;
import proj.basic.evaluate.model.EvaluateVO;
import proj.basic.member.model.MemberVO;

public class EvaluateService_Wu {
	
	EvaluateDAO_Wu evaluateDao;
	
	public EvaluateService_Wu(){
		try {
			evaluateDao = new EvaluateDAO_Wu();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void doEvaluate(EvaluateVO eVO){
		evaluateDao.insert(eVO);
	}
	
	public Boolean chkEorNot(Integer orderNo, MemberVO memberVO, Integer itemNo){
		return evaluateDao.chkEorNot(orderNo, memberVO, itemNo);
	}

	public List<EvaluateVO> getItemEAll(Integer itemNo){
		return evaluateDao.getItemEAll(itemNo);
	}
}
