package gov.czgs.fm.service;

import gov.czgs.fm.dao.WzglDao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WzglService {

	@Resource
	private WzglDao wzglDao;
	
	public List<Map<String, Object>> getAsideMenu( String lx) {
		// TODO Auto-generated method stub
		return wzglDao.getAsideMenu(lx);
	}

	public Map<String, Object> newMenu(Map<String, Object> para) {
		// TODO Auto-generated method stub
		Number rs =  wzglDao.newMenu(para);
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("id", rs);
		return map;
	}

	public void removeMenu(String id) {
		// TODO Auto-generated method stub
		wzglDao.removeMenu(id);
	}

	public void updateMenu(Map<String, Object> para) {
		// TODO Auto-generated method stub
		wzglDao.updateMenu(para);
	}

	public Map<String, Object> getWzinfo(Map<String, Object> para) {
		// TODO Auto-generated method stub
		return wzglDao.getWzinfo(para);
	}

	public Map<String, Object> newWz(Map<String, Object> para) {
		// TODO Auto-generated method stub
		return  wzglDao.newWz(para);
	}

	public Map<String, Object> getWz(String id) {
		// TODO Auto-generated method stub
		return wzglDao.getWz(id);
	}

	public Map<String, Object> updateWz(String id, Map<String, Object> para) {
		// TODO Auto-generated method stub
		return wzglDao.updateWz(id,para);
	}

	public void deleteWz(List<String> para) {
		// TODO Auto-generated method stub
		for(String mp :para){
			wzglDao.deleteWz(mp);
		}
	}

}
