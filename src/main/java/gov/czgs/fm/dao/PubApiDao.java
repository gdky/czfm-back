package gov.czgs.fm.dao;

import java.util.List;
import java.util.Map;

import org.omg.CORBA.INTF_REPOS;
import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;
@Repository
public class PubApiDao extends BaseJdbcDao  {

	public List<Map<String, Object>> getAsideMenu() {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("select * from fm_wz_lm");
			sql.append(" where yxbz ='1' ");
		sql.append(" order by pid  ");
		return this.jdbcTemplate.queryForList(sql.toString());
	}

	public Map<String, Object> getWz(String id) {
		// TODO Auto-generated method stub
		String sql = "select a.*, DATE_FORMAT(a.create_time,'%Y-%d-%m')  date , DATE_FORMAT(a.create_time,'%T')  time ,b.names sender,r.bt,r.url  from fm_wz_nr a  left join fm_recgl r on r.ID=a.recgl_id ,fw_users b where a.senderid =b.id and a.id = ? ";
		List<Map<String, Object>> ls = this.jdbcTemplate.queryForList(sql,new Object[]{id});
		if(ls.size()>0){
			return ls.get(0);
		}else{
			return null;
		}
	}

	public List<Map<String, Object>> getWzByLm(String lmid) {
		// TODO Auto-generated method stub
		String sql = "select a.*, DATE_FORMAT(a.create_time,'%Y-%d-%m')  date , DATE_FORMAT(a.create_time,'%T')  time,b.names sender,r.bt,r.url  from fm_wz_nr a  left join fm_recgl r on r.ID=a.recgl_id,fw_users b where a.senderid =b.id and a.lmid = ? ";
		List<Map<String, Object>> ls = this.jdbcTemplate.queryForList(sql,new Object[]{lmid});
		return ls;
	}

	public boolean isExpired() {
		// TODO Auto-generated method stub
    	String sql = " select date<=date_add(sysdate(), INTERVAL -30 day) bj from fm_record where name = 'accesstoken'  ";
    	int i = this.jdbcTemplate.queryForObject(sql, Integer.class);
    	if(i==0){
    		return false;
    	}else{
    		return true;
    	}
	}

	public void updateToken(String token) {
		// TODO Auto-generated method stub
		String sql = "update fm_record set date = sysdate(),value = ? where name = 'accesstoken' ";
		this.jdbcTemplate.update(sql,new Object[]{token});
	}

	public String getToken() {
		// TODO Auto-generated method stub
    	String sql = " select value bj from fm_record where name = 'accesstoken'  ";
		
    	return this.jdbcTemplate.queryForObject(sql, String.class);
	}

}
