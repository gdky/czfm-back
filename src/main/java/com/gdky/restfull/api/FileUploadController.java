package com.gdky.restfull.api;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdky.restfull.configuration.Constants;
import com.gdky.restfull.entity.ResponseMessage;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

@RestController
@RequestMapping(value = Constants.URI_API_PREFIX)
public class FileUploadController {

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String provideUploadInfo() {
		return "You can upload a file by posting to this same URL.";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<?> handleFileUpload(
			@RequestParam("file") MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			String name = file.getOriginalFilename();
			String ext = FilenameUtils.getExtension(name);
			String uploadDir = "/files/";
			File path = new File(Constants.UPLOAD_LOCATION + uploadDir);
			if (!path.exists()) {
				path.mkdir();
			}
			String filename = uploadDir
					+ Hashing.crc32().hashBytes(file.getBytes()) + "." + ext;
			File to = new File(Constants.UPLOAD_LOCATION + filename);
			ResponseMessage rm = new ResponseMessage(
					ResponseMessage.Type.success, "201", filename);
			try {
				Files.write(file.getBytes(), to);
				return new ResponseEntity<>(rm, HttpStatus.CREATED);
			} catch (Exception e) {
				rm = new ResponseMessage(ResponseMessage.Type.danger, "400",
						"上传失败:" + e.getMessage());
				return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
			}
		} else {
			ResponseMessage rm = new ResponseMessage(
					ResponseMessage.Type.danger, "400", "文件为空");
			return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/mlitupload", consumes = "multipart/form-data", method = RequestMethod.POST)
    public void picture(HttpServletRequest request) {
 
        String pathname = Constants.UPLOAD_LOCATION + "/xpian2/";
        File file = new File(pathname);
        if (!file.exists()) {
            file.mkdirs();
        }
        MultipartHttpServletRequest muti = (MultipartHttpServletRequest) request;
        System.out.println(muti.getMultiFileMap().size());
 
        MultiValueMap<String, MultipartFile> map = muti.getMultiFileMap();
        for (Map.Entry<String, List<MultipartFile>> entry : map.entrySet()) {
 
            List<MultipartFile> list = entry.getValue();
            for (MultipartFile multipartFile : list) {
                try {
                    multipartFile.transferTo(new File(pathname
                            + multipartFile.getOriginalFilename()));
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
