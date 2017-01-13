package gov.czgs.fm.controller;

import gov.czgs.fm.service.PubApiService;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Config;

@RestController
@RequestMapping(value = Config.URL_PUBLIC)
public class PubApiController {

	@Resource
	private PubApiService pubApiService;
	/**
	 * 获取栏目信息
	 * @return
	 */
	@RequestMapping(value = "/wzglmenu", method = RequestMethod.GET)
	public ResponseEntity<List<Map<String, Object>>> getAsideMenu() {
		List<Map<String, Object>> ls = pubApiService.getAsideMenu();
		return new ResponseEntity<>(ls, HttpStatus.OK);
	}
	/**
	 * 根据id获取文章信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/wzinfo/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getWz(@PathVariable("id") String id) {
		Map<String, Object> obj = pubApiService.getWz(id);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	/**
	 * 根据栏目ID获取对应的文章信息
	 * @param lmid
	 * @return
	 */
	@RequestMapping(value = "/wzinfobylm/{lmid}", method = RequestMethod.GET)
	public ResponseEntity<?> getWzByLm(@PathVariable("lmid") String lmid) {
		List<Map<String, Object>> obj = pubApiService.getWzByLm(lmid);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
}
