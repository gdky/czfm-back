package gov.czgs.fm.controller;

import gov.czgs.fm.service.WzglService;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Config;
import com.gdky.restfull.entity.AsideMenu;
import com.gdky.restfull.entity.ResponseMessage;
import com.gdky.restfull.entity.User;

@RestController
@RequestMapping(value = Config.URL_PROJECT)
public class WzglController {
	@Resource
	private WzglService wzglService;

	@RequestMapping(value = "/wzglmenu", method = RequestMethod.GET)
	public ResponseEntity<List<Map<String, Object>>> getAsideMenu(@RequestParam(value = "lx", required = true) String lx) {
		List<Map<String, Object>> ls = wzglService.getAsideMenu(lx);
		return new ResponseEntity<>(ls, HttpStatus.OK);
	}

	@RequestMapping(value = "/wzglmenu", method = RequestMethod.POST)
	public ResponseEntity<?> newMenu(@RequestBody Map<String, Object> para) {
		Map<String, Object> obj = wzglService.newMenu(para);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/wzglmenu/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateMenu(@PathVariable("id") String id,
			@RequestBody Map<String, Object> para) {
		wzglService.updateMenu(para);
		return new ResponseEntity<>(ResponseMessage.success("更新成功"),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/wzglmenu/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<List<Map<String, Object>>> removeMenu(
			@PathVariable("id") String id,@RequestParam(value = "lx", required = true) String lx) {
		wzglService.removeMenu(id);
		List<Map<String, Object>> ls = wzglService.getAsideMenu(lx);
		return new ResponseEntity<>(ls, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/wzinfo", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getWzinfo(@RequestParam Map<String,Object> param) {
		Map<String, Object> obj = wzglService.getWzinfo(param);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
}
