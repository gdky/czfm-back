package gov.czgs.fm.dao;

import java.util.ArrayList;
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
		String lmid = (String) para.get("lmid");
		String page = (String) para.get("page");
		String pagesize = (String) para.get("pagesize");
		String sql = "select a.*,DATE_FORMAT(a.create_time,'%Y-%d-%m %T')  createtime ,b.names sender from fm_wz_nr a ,fw_users b where a.senderid =b.id and a.lmid = ? order by create_time desc  limit ?,? ";
		List<Map<String, Object>> ls = this.jdbcTemplate.queryForList(
				sql, new Object[] { lmid,((Integer.parseInt(page)-1)*Integer.parseInt(pagesize)),Integer.parseInt(pagesize) });
		sql = "select count(*) rs from fm_wz_nr a ,fw_users b where a.senderid =b.id and a.lmid = ? ";
		int total = this.jdbcTemplate.queryForObject(sql, new Object[]{lmid},Integer.class);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("data", ls);
		obj.put("total", total);
		obj.put("pagesize", pagesize);
		obj.put("current", page);

		return obj;

	}

	public Map<String, Object> newWz(Map<String, Object> para) {
		// TODO Auto-generated method stub
		User user = accountService.getUserFromHeaderToken(request);
		List<Object> arg = new ArrayList<Object>();
		int lmid = (Integer) para.get("lmid");
		String title = (String) para.get("title");
		String content = (String) para.get("content");
		StringBuffer sql1 = new StringBuffer(" insert into fm_wz_nr(lmid,content,title,senderid,create_time,state  ");
		StringBuffer sql2 = new StringBuffer(" values(?,?,?,?,now(),'0' ");
		arg.add(lmid);
		arg.add(content);
		arg.add(title);
		arg.add(user.getId());
		
		if(para.get("audioid")!=null){
			arg.add((Integer) para.get("audioid"));
			sql1.append(",recgl_id ");
			sql2.append(",? ");
		}else{
			
		}
		if(para.get("uploadUrl")!=null){
			arg.add((String)para.get("uploadUrl"));
			sql1.append(",attachment ");
			sql2.append(",? ");
		}else{
			
		}
		sql1.append(" ) "); 
		sql2.append(" ) ");
		sql1.append(sql2);
		this.jdbcTemplate.update(sql1.toString(), arg.toArray());
		return null;
	}

	public Map<String, Object> getWz(String id) {
		// TODO Auto-generated method stub
		String sql = "select a.*,r.id rekey ,r.url,r.bt from fm_wz_nr a  left join fm_recgl r on r.ID=a.recgl_id where a.id = ? ";
		List<Map<String, Object>> ls = this.jdbcTemplate.queryForList(sql,
				new Object[] { id });
		if (ls.size() > 0) {
			return ls.get(0);
		} else {
			return null;
		}
	}

	public Map<String, Object> updateWz(String id, Map<String, Object> para) {
		// TODO Auto-generated method stub
		User user = accountService.getUserFromHeaderToken(request);
		int lmid = (Integer) para.get("lmid");
		String title = (String) para.get("title");
		String content = (String) para.get("content");
		StringBuffer sql = new StringBuffer("update fm_wz_nr set content = ? ,title = ?,senderid = ? ");

		List<Object> arg = new ArrayList<Object>();
		arg.add(content);
		arg.add(title);
		arg.add(user.getId());
		if(para.get("audioid")!=null){
			arg.add((Integer) para.get("audioid"));
			sql.append(",recgl_id = ? ");
		}else{
			
		}
		if(para.get("uploadUrl")!=null){
			arg.add((String)para.get("uploadUrl"));
			sql.append(",attachment=? ");
		}else{
			
		}
		sql.append(" where id = ? ");
		arg.add(id);
		this.jdbcTemplate.update(sql.toString(), arg.toArray());
		return null;
	}

	public void deleteWz(String mp) {
		// TODO Auto-generated method stub
		String sql = "delete from fm_wz_nr where id = ? ";
		this.jdbcTemplate.update(sql, new Object[] { mp });
	}

	public List<Map<String, Object>> getAudio() {
		// TODO Auto-generated method stub
		String sql = "select * from fm_recgl ";
		return this.jdbcTemplate.queryForList(sql.toString());
	}
	//当前状态 0：未发布 1：已发布 2：已作废
	public void releaseWz(String mp) {
		// TODO Auto-generated method stub
		String sql = "update fm_wz_nr set state = '1'  where id = ? ";
		this.jdbcTemplate.update(sql, new Object[] { mp });
	}

	public void cancelWz(String mp) {
		// TODO Auto-generated method stub
		String sql = "update fm_wz_nr set state = '2'  where id = ? ";
		this.jdbcTemplate.update(sql, new Object[] { mp });
	}

}
