package gov.czgs.fm.service;

import gov.czgs.fm.dao.RecglDao;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdky.restfull.dao.MessageDao;
import com.gdky.restfull.entity.User;
import com.gdky.restfull.utils.Common;
import com.gdky.restfull.utils.Condition;

@Service
@Transactional(rollbackFor=Exception.class)
public class RecglService {
	@Resource
	private RecglDao recglDao;
	
	public Map<String, Object> getRecList(User sendUser, int page,
			int pagesize, String whereparam) {
		Condition condition = new Condition();
		if (!StringUtils.isEmpty(whereparam)) {
			Map<String, Object> where = Common.decodeURItoMap(whereparam);
			condition.add("BT", Condition.FUZZY, where.get("bt"));
			condition.add("LRRQ", Condition.FUZZY_RIGHT, where.get("lrrq"));
		}
		//condition.add("senderid", Condition.EQUAL, sendUser.getId());
		Map<String, Object> obj = recglDao.getRecList(sendUser,condition, page,
				pagesize);

		return obj;
	}
	
	public void addRec(Map<String,Object> rec){
		recglDao.addRec((String)rec.get("bt"), (String)rec.get("url"), (String)rec.get("user"));
	}

}
