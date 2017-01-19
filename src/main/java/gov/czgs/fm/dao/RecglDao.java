package gov.czgs.fm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gdky.restfull.dao.BaseJdbcDao;
import com.gdky.restfull.entity.User;
import com.gdky.restfull.utils.Condition;

@Repository
public class RecglDao extends BaseJdbcDao {

	public Map<String, Object> getRecList(User user,Condition condition, int page,
			int pagesize) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select *,case YXBZ when 0 then '无效' when 1 then '有效' else null end as yx from fm_recgl ");
		sb.append(condition.getSql());
		sb.append(" LIMIT ?, ?");

		// 装嵌传值数组
		int startIndex = pagesize * (page - 1);
		ArrayList<Object> params = condition.getParams();
		params.add(startIndex);
		params.add(pagesize);

		// 获取符合条件的记录
		List<Map<String, Object>> ls = jdbcTemplate.query(sb.toString(),params.toArray(),
				new RowMapper<Map<String, Object>>() {
			public Map<String, Object> mapRow(ResultSet rs, int arg1)
					throws SQLException {
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
						"yyyy-MM-dd");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("BT", rs.getObject("BT"));
				map.put("URL", rs.getObject("URL"));
				map.put("LRR", rs.getObject("LRR"));
				map.put("LRRQ", sdf.format(rs.getObject("LRRQ")));
				map.put("YXBZ", rs.getObject("yx"));
				return map;
			}
		});

		// 获取符合条件的记录数
		String countSql = condition.getCountSql("id", "fm_recgl");
		int total = jdbcTemplate.queryForObject(countSql, condition.getParams()
				.toArray(), Integer.class);

		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("data", ls);
		obj.put("total", total);
		obj.put("pagesize", pagesize);
		obj.put("current", page);

		return obj;
	}
	
	public void addRec(String bt,String url,String user){
		String sql ="insert into fm_recgl (BT,URL,LRR,LRRQ,YXBZ) values(?,?,?,now(),'1')";
		this.jdbcTemplate.update(sql,new Object[]{bt,url,user});
	}
}
