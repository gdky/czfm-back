package gov.czgs.fm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;

@Repository
public class WzglDao extends BaseJdbcDao  {

	public List<Map<String, Object>> getAsideMenu() {
		// TODO Auto-generated method stub
		String sql ="select * from fm_wz_lm order by pid  ";
		return this.jdbcTemplate.queryForList(sql);
	}

	public Number newMenu(Map<String, Object> para) {
		// TODO Auto-generated method stub
		String sql = "insert into fm_wz_lm(mc,yxbz,pid) values(?,'Y',?)";
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


}
