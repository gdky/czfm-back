package gov.czgs.fm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;

@Repository
public class WzglDao extends BaseJdbcDao  {

	public List<Map<String, Object>> getAsideMenu( String lx) {
		// TODO Auto-generated method stub
		StringBuffer sql =new StringBuffer("select * from fm_wz_lm");
		if(lx.equals("yx")){
			sql.append(" where yxbz ='1' ");
		}
		sql.append(" order by pid  ");
		return this.jdbcTemplate.queryForList(sql.toString());
	}

	public Number newMenu(Map<String, Object> para) {
		// TODO Auto-generated method stub
		String sql = "insert into fm_wz_lm(mc,yxbz,pid) values(?,'1',?)";
		Object[] arg = new Object[] { para.get("mc"), (Integer)para.get("pid") };

		Number rs = this.insertAndGetKeyByJdbc(sql, arg,
				new String[] { "id" });
		return rs;
	}

	public void removeMenu(String id) {
		// TODO Auto-generated method stub
		String sql = "delete from fm_wz_lm where id = ?";
		this.jdbcTemplate.update(sql, new Object[] {id});
	}

	public void updateMenu(Map<String, Object> para) {
		// TODO Auto-generated method stub
		String sql = "update fm_wz_lm set mc = ? ,yxbz = ? where id = ?";
		this.jdbcTemplate.update(sql, new Object[] {para.get("mc"),para.get("yxbz"),para.get("id")});
	}

	public Map<String, Object> getWzinfo(Map<String, Object> param) {
		// TODO Auto-generated method stub
		String sql = "select a.*,b.names sender from fm_wz_nr a ,fw_users b where a.senderid =b.id ";
		List<Map<String,Object>> ls= this.jdbcTemplate.queryForList(sql.toString());
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("data", ls);
		obj.put("total", 1);
		obj.put("pagesize", 1);
		obj.put("current", 1);

		return obj;
	}


}
