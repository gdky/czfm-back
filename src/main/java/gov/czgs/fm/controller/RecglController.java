package gov.czgs.fm.controller;

import gov.czgs.fm.service.RecglService;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Constants;
import com.gdky.restfull.entity.ResponseMessage;
import com.gdky.restfull.entity.User;
import com.gdky.restfull.service.AccountService;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX)
public class RecglController {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private RecglService recglService;
	
	/**
	 * 获取发件箱列表
	 */
	@RequestMapping(value = "/recgl", method = RequestMethod.GET)
	public ResponseEntity<?> getSendbox(
			@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pagesize", required = true) int pagesize,
			@RequestParam(value = "where", required = false) String where) {

		User user = accountService.getUserFromHeaderToken(request);
		Map<String, Object> obj = recglService.getRecList(user,page, pagesize, where);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	/**
	 * 添加语音文件
	 * @param rec
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/recgl/recAdd", method = RequestMethod.POST)
	public ResponseEntity<?> spsq(
			@RequestBody Map<String, Object> rec,HttpServletRequest request ) throws Exception{
		try {
			User user =  accountService.getUserFromHeaderToken(request);
			rec.put("user", user.getUsername());
		} catch (Exception e) {
			rec.put("user","");
		}
		recglService.addRec(rec);
		return new ResponseEntity<>(ResponseMessage.success("提交成功"),HttpStatus.CREATED);
	}
}
