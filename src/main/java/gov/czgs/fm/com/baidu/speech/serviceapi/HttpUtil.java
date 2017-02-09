package gov.czgs.fm.com.baidu.speech.serviceapi;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtil {

	private static final String serverURL = "http://tsn.baidu.com/text2audio";
	private static final String tokenUrl ="https://openapi.baidu.com/oauth/2.0/token";
    private static final String testFileName = "test.pcm";
    //put your own params here
    private static final String apiKey = "pnNBpkOpAW5TWqO7E1RL3LfW";
    private static final String secretKey = "cd491a30388a60e1b34aba83d3bf6e3a";
    private static final String cuid = "test";

    
    
    
    public static String getToken(){
    	String url = tokenUrl+"?";
		String accesstoken = "";
		Map<String,String> params = new HashMap<String,String>();
        params.put("grant_type", "client_credentials");
        params.put("client_id", apiKey);
        params.put("client_secret", secretKey);
		URL u = null;
		HttpURLConnection con = null;
		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		if (params != null) {
			for (Entry<String, String> e : params.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			sb.substring(0, sb.length() - 1);
		}
		// 尝试发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			//// POST 只能为大写，严格限制，post会不识别
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			osw.write(sb.toString());
			osw.flush();
			osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}

		// 读取返回内容
		try {
			//一定要有返回值，否则无法把请求发送给server端。
			InputStream is = con.getInputStream();
	        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	        String line;
	        StringBuffer response = new StringBuffer();
	        while ((line = rd.readLine()) != null) {
	            response.append(line);
	            response.append('\r');
	        }
	        rd.close();
	        String sp[] = response.toString().split("\"");
			for(int i = 0; i < sp.length; i++){
				if(sp[i].equals("access_token")){
					accesstoken = sp[i+2];
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return accesstoken;
    	
    }
    
	public static byte[] http(String wz,String token) {
		String url = serverURL+"?";
		Map<String,String> params = new HashMap<String,String>();
        params.put("tex", wz);
        params.put("lan", "zh");
        params.put("tok", token);
        params.put("cuid", cuid);
        params.put("ctp", "1");
		URL u = null;
		HttpURLConnection con = null;
		// 构建请求参数
		 byte[] in2b = null;
		StringBuffer sb = new StringBuffer();
		if (params != null) {
			for (Entry<String, String> e : params.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			sb.substring(0, sb.length() - 1);
		}
		System.out.println("send_url:" + url);
		System.out.println("send_data:" + sb.toString());
		// 尝试发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			//// POST 只能为大写，严格限制，post会不识别
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			osw.write(sb.toString());
			osw.flush();
			osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}

		// 读取返回内容
		try {
			//一定要有返回值，否则无法把请求发送给server端。
			InputStream is = con.getInputStream();
			
			
			 ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
		        byte[] buff = new byte[100];  
		        int rc = 0;  
		        while ((rc = is.read(buff, 0, 100)) > -1) {  
		            swapStream.write(buff, 0, rc);  
		        }  
		        in2b = swapStream.toByteArray();  

		} catch (Exception e) {
			e.printStackTrace();
		}

		return in2b;
	}
	
	public static void main(String[] args) {
	}
	

}