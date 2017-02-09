package gov.czgs.fm.service;

import gov.czgs.fm.dao.PubApiDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
@Service
public class PubApiService {

	@Resource
	private PubApiDao pubApiDao;
	
	public List<Map<String, Object>> getAsideMenu() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> ls= pubApiDao.getAsideMenu();
		List<Map<String, Object>> root = new ArrayList<Map<String, Object>>();
		Map<Integer, Integer> item = new HashMap<Integer, Integer>();
		int key = 0;
		for(Map<String, Object> mp:ls){
			int pid = (Integer)mp.get("pid");
			if(pid==0){
				root.add(mp);
				item.put((Integer)mp.get("id"), key);
				key++;
			}else{
				Map<String, Object> children = root.get(item.get((Integer)mp.get("pid")));
				children.put("children", mp);
			}
		}
		return root;
	}

	public Map<String, Object> getWz(String id) {
		// TODO Auto-generated method stub
		return pubApiDao.getWz(id);
	}

	public List<Map<String, Object>> getWzByLm(String lmid) {
		// TODO Auto-generated method stub
		return pubApiDao.getWzByLm(lmid);
	}

	public boolean isExpired() {
		// TODO Auto-generated method stub
		return pubApiDao.isExpired();
	}

	public void updateToken(String token) {
		// TODO Auto-generated method stub
		pubApiDao.updateToken(token);
	}

	public String getToken() {
		// TODO Auto-generated method stub
		return pubApiDao.getToken();
	}

}
