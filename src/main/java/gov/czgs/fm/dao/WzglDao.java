package gov.czgs.fm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;
import com.gdky.restfull.entity.User;
import com.gdky.restfull.service.AccountService;

@Repository
public class WzglDao extends BaseJdbcDao {

	@Autowired
	private AccountService accountService;

	@Autowired
	private HttpServletRequest request;

	public List<Map<String, Object>> getAsideMenu(String lx) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("select * from fm_wz_lm");
		if (lx.equals("yx")) {
			sql.append(" where yxbz ='1' ");
		}
		sql.append(" order by pid  ");
		return this.jdbcTemplate.queryForList(sql.toString());
	}

	public Number newMenu(Map<String, Object> para) {
		// TODO Auto-generated method stub
		String sql = "insert into fm_wz_lm(mc,yxbz,pid) values(?,'1',?)";
		Object[] arg = new Object[] { para.get("mc"), (Integer) para.get("pid") };

		Number rs = this.insertAndGetKeyByJdbc(sql, arg, new String[] { "id" });
		return rs;
	}

	public void removeMenu(String id) {
		// TODO Auto-generated method stub
		String sql = "delete from fm_wz_lm where id = ?";
		this.jdbcTemplate.update(sql, new Object[] { id });
	}

	public void updateMenu(Map<String, Object> para) {
		// TODO Auto-generated method stub
		String sql = "update fm_wz_lm set mc = ? ,yxbz = ? where id = ?";
		this.jdbcTemplate
				.update(sql, new Object[] { para.get("mc"), para.get("yxbz"),
						para.get("id") });
	}

	public Map<String, Object> getWzinfo(Map<String, Object> para) {
		// TODO Auto-generated method stub
		String lmid = (String) para.get("lmid");
			
			String sql = "select a.*,DATE_FORMAT(a.create_time,'%Y-%d-%m %T')  createtime ,b.names sender from fm_wz_nr a ,fw_users b where a.senderid =b.id and a.lmid = ? ";
			List<Map<String, Object>> ls = this.jdbcTemplate.queryForList(sql
					.toString(),new Object[]{lmid});
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("data", ls);
			obj.put("total", 1);
			obj.put("pagesize", 1);
			obj.put("current", 1);

			return obj;
		
	}

	public Map<String, Object> newWz(Map<String, Object> para) {
		// TODO Auto-generated method stub
		User user = accountService.getUserFromHeaderToken(request);
		int lmid = (Integer) para.get("lmid");
		String title = (String) para.get("title");
		String content = (String) para.get("content");
		String sql = " insert into fm_wz_nr(lmid,content,title,senderid,create_time) values(?,?,?,?,now()) ";
		Object[] arg = new Object[] { lmid, content, title, user.getId() };

		this.jdbcTemplate.update(sql, arg);
		return null;
	}

	public Map<String, Object> getWz(String id) {
		// TODO Auto-generated method stub
		String sql = "select * from fm_wz_nr where id = ? ";
		List<Map<String, Object>> ls = this.jdbcTemplate.queryForList(sql,new Object[]{id});
		if(ls.size()>0){
			return ls.get(0);
		}else{
			return null;
		}
	}

	public Map<String, Object> updateWz(String id, Map<String, Object> para) {
		// TODO Auto-generated method stub
		User user = accountService.getUserFromHeaderToken(request);
		int lmid = (Integer) para.get("lmid");
		String title = (String) para.get("title");
		String content = (String) para.get("content");
		String sql ="update fm_wz_nr set content = ? ,title = ?,senderid = ? where id = ?";
		Object[] arg = new Object[] {  content, title, user.getId(),id };

		this.jdbcTemplate.update(sql, arg);
		return null;
	}

	public void deleteWz(String mp) {
		// TODO Auto-generated method stub
		String sql = "delete from fm_wz_nr where id = ? ";
		this.jdbcTemplate.update(sql, new Object[]{mp});
	}

}
