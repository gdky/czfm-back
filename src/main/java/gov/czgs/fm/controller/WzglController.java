package gov.czgs.fm.controller;

import gov.czgs.fm.com.baidu.speech.serviceapi.HttpUtil;
import gov.czgs.fm.service.WzglService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Config;
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
			@PathVariable("id") String id) {
		wzglService.removeMenu(id);
		List<Map<String, Object>> ls = wzglService.getAsideMenu("all");
		return new ResponseEntity<>(ls, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/wzinfo", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getWzinfo(@RequestParam Map<String,Object> para) {
		Map<String, Object> obj = wzglService.getWzinfo(para);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/newwz", method = RequestMethod.POST)
	public ResponseEntity<?> newWz(
			@RequestBody Map<String,Object> para) {
		Map<String, Object> obj = wzglService.newWz(para);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getwz/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getWz(@PathVariable("id") String id) {
		Map<String, Object> obj = wzglService.getWz(id);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/editwz/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateWz(@PathVariable("id") String id,
			@RequestBody Map<String, Object> para) {
		Map<String, Object> obj = wzglService.updateWz(id,para);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deletewz", method = RequestMethod.DELETE)
	public ResponseEntity<List<Map<String, Object>>> deleteWz(@RequestBody List<String> para) {
		wzglService.deleteWz(para);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@RequestMapping(value = "/releasewz", method = RequestMethod.PUT)
	public ResponseEntity<List<Map<String, Object>>> releaseWz(@RequestBody List<String> para) {
		wzglService.releaseWz(para);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@RequestMapping(value = "/cancelwz", method = RequestMethod.PUT)
	public ResponseEntity<List<Map<String, Object>>> cancelWz(@RequestBody List<String> para) {
		wzglService.cancelWz(para);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getaudio", method = RequestMethod.GET)
	public ResponseEntity<List<Map<String, Object>>> getAudio() {
		List<Map<String, Object>> ls = wzglService.getAudio();
		return new ResponseEntity<>(ls, HttpStatus.OK);
	}

	
}
