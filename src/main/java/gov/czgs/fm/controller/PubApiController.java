package gov.czgs.fm.controller;

import gov.czgs.fm.com.baidu.speech.serviceapi.HttpUtil;
import gov.czgs.fm.service.PubApiService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Config;

@RestController
@RequestMapping(value = Config.URL_PUBLIC)
public class PubApiController {

	@Autowired
	private HttpServletRequest httpRequest;
	@Autowired
	private HttpServletResponse response;
	@Resource
	private PubApiService pubApiService;

	/**
	 * 获取栏目信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/wzglmenu", method = RequestMethod.GET)
	public ResponseEntity<List<Map<String, Object>>> getAsideMenu() {
		List<Map<String, Object>> ls = pubApiService.getAsideMenu();
		return new ResponseEntity<>(ls, HttpStatus.OK);
	}

	/**
	 * 根据id获取文章信息
	 * 
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
	 * 
	 * @param lmid
	 * @return
	 */
	@RequestMapping(value = "/wzinfobylm/{lmid}", method = RequestMethod.GET)
	public ResponseEntity<?> getWzByLm(@PathVariable("lmid") String lmid) {
		List<Map<String, Object>> obj = pubApiService.getWzByLm(lmid);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/tts", method = RequestMethod.POST)
	public ResponseEntity<?> getTTS(/*@RequestParam(value = "text") String text*/
	@RequestBody Map<String,String> body) throws IOException {
		 String text = body.get("text");

		List<String> fdwz = getFdWz(text);

		Vector<InputStream> v = new Vector<>();
		for (String dw : fdwz) {
			byte[] baos = HttpUtil.http(dw);
			ByteArrayInputStream bis = new ByteArrayInputStream(baos);
			v.addElement(bis);
		}

		Enumeration<InputStream> e = v.elements();
		SequenceInputStream se = new SequenceInputStream(e);
		response.setContentType("audio/mpeg3");
		response.setHeader("pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		response.setHeader("expires", "0");
		response.addHeader("Content-Disposition",
				"attachment;filename='t2a.mp3'");
		IOUtils.copyLarge(se, response.getOutputStream());
		response.flushBuffer();
		return new ResponseEntity<>("t2a.mp3", HttpStatus.OK);
	}

	private List<String> getFdWz(String fdwz) {
		String[] list = fdwz.split("。");
		List<String> zw = new ArrayList<String>();
		int count = 0;
		String ls = "";
		for (int i = 0; i < list.length; i++) {
			// 如果这一段超过1024字节 少于2048 字节 强行截半
			if (list[i].length() * 4 > 1024) {
				zw.add(ls + "。");
				String test = list[i];
				zw.add(test.substring(0, list[i].length() / 2) + "。");
				zw.add(test.substring(list[i].length() / 2, list[i].length())
						+ "。");
				count = 0;
				ls = "";
			} else {
				count += list[i].length() * 4;
				int len = ls.length() * 4;
				if (count > 1024) {
					zw.add(ls + "。");
					count = list[i].length() * 4;
					ls = "";
				}
				ls += list[i];
			}
			if (i == list.length - 1) {
				zw.add(ls + "。");
			}
		}
		return zw;
	}
}
