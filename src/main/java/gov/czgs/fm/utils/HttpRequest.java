package gov.czgs.fm.utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequest {
	public void mixRec(){
		HttpRequest req = new HttpRequest();
//		String e = req.sendPost("https://openapi.baidu.com/oauth/2.0/token", "grant_type=client_credentials&client_id=D65mrFN5Uw46pt9HagNwzq2G&client_secret=8c97bdb0bec95f7de3d307c94862aa33&");
		//Calendar now = Calendar.getInstance(); 
		//System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH)); 
		StringBuffer sb = new StringBuffer();
		sb.append("tex=啦啦啦啦啦啦，啦啦，啦，啦啦&");
		sb.append("lan=zh&");
		sb.append("tok=24.60d5a435b488d43da9b5b5eb931ccee8.2592000.1487475755.282335-9221632&");
		sb.append("ctp=1&");
		sb.append("cuid=gduser&");
//		String w = req.sendPost("http://tsn.baidu.com/text2audio",sb.toString());
		InputStream q = req.sendPostReturnSteam("http://tsn.baidu.com/text2audio",sb.toString());
		try {
			FileOutputStream fos = new FileOutputStream("E:/upload/1111.mp3");
			byte[] b = new byte[128];
			int len = 0;
			while((len = q.read(b)) != -1)
			{
			fos.write(b,0,len);
			}
			q.close();
			fos.close();
		} catch (Exception e2) {
			// TODO: handle exception
		}
	}
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public  String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求by字符流
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public  String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    /**
     * 向指定 URL 发送POST方法的请求by字节流返回流
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public  InputStream sendPostReturnSteam(String url, String param) {
    	PrintWriter out = null;
    	BufferedReader in = null;
    	InputStream stream =null;
    	try {
    		URL realUrl = new URL(url);
    		// 打开和URL之间的连接
    		URLConnection conn = realUrl.openConnection();
    		// 设置通用的请求属性
    		conn.setRequestProperty("accept", "*/*");
    		conn.setRequestProperty("connection", "Keep-Alive");
    		conn.setRequestProperty("user-agent",
    				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
    		// 发送POST请求必须设置如下两行
    		conn.setDoOutput(true);
    		conn.setDoInput(true);
    		// 获取URLConnection对象对应的输出流
    		out = new PrintWriter(conn.getOutputStream());
    		// 发送请求参数
    		out.print(param);
    		// flush输出流的缓冲
    		out.flush();
    		 stream = conn.getInputStream();
    		
    	} catch (Exception e) {
    		System.out.println("发送 POST 请求出现异常！"+e);
    		e.printStackTrace();
    	}
    	//使用finally块来关闭输出流、输入流
    	finally{
    		try{
    			if(out!=null){
    				out.close();
    			}
    			if(in!=null){
    				in.close();
    			}
    		}
    		catch(IOException ex){
    			ex.printStackTrace();
    		}
    	}
    	return stream;
    }    
}
