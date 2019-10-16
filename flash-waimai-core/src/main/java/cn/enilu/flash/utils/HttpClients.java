package cn.enilu.flash.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dell on 2015/1/13.
 */
public class HttpClients {
	private static Logger logger = LoggerFactory.getLogger(HttpClients.class);

	/**
	 * 封装HTTP POST方法
	 *
	 * @param
	 * @param
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String post(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> formparams = setHttpParams(paramMap);
		UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");
		httpPost.setEntity(param);
		HttpResponse response = httpClient.execute(httpPost);
		String httpEntityContent = getHttpEntityContent(response);
		httpPost.abort();
		return httpEntityContent;
	}

	/**
	 * 封装HTTP POST方法
	 *
	 * @param
	 * @param （如JSON串）
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String post(String url, String data) throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "text/json; charset=utf-8");
		httpPost.setEntity(new StringEntity(URLEncoder.encode(data, "UTF-8")));
		HttpResponse response = httpClient.execute(httpPost);
		String httpEntityContent = getHttpEntityContent(response);
		httpPost.abort();
		return httpEntityContent;
	}

	/**
	 * 封装HTTP GET方法
	 *
	 * @param
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String get(String url) throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet();
		httpGet.setURI(URI.create(url));
		HttpResponse response = httpClient.execute(httpGet);
		String httpEntityContent = getHttpEntityContent(response);
		httpGet.abort();
		return httpEntityContent;
	}

	/**
	 * 封装HTTP GET方法
	 *
	 * @param
	 * @param
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String get(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet();
		List<NameValuePair> formparams = setHttpParams(paramMap);
		String param = URLEncodedUtils.format(formparams, "UTF-8");
		httpGet.setURI(URI.create(url + "?" + param));
		HttpResponse response = httpClient.execute(httpGet);
		String httpEntityContent = getHttpEntityContent(response);
		httpGet.abort();
		return httpEntityContent;
	}

	/**
	 * 封装HTTP PUT方法
	 *
	 * @param
	 * @param
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String put(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPut httpPut = new HttpPut(url);
		List<NameValuePair> formparams = setHttpParams(paramMap);
		UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");
		httpPut.setEntity(param);
		HttpResponse response = httpClient.execute(httpPut);
		String httpEntityContent = getHttpEntityContent(response);
		httpPut.abort();
		return httpEntityContent;
	}

	/**
	 * 封装HTTP DELETE方法
	 *
	 * @param
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String delete(String url) throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpDelete httpDelete = new HttpDelete();
		httpDelete.setURI(URI.create(url));
		HttpResponse response = httpClient.execute(httpDelete);
		String httpEntityContent = getHttpEntityContent(response);
		httpDelete.abort();
		return httpEntityContent;
	}

	/**
	 * 封装HTTP DELETE方法
	 *
	 * @param
	 * @param
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String delete(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpDelete httpDelete = new HttpDelete();
		List<NameValuePair> formparams = setHttpParams(paramMap);
		String param = URLEncodedUtils.format(formparams, "UTF-8");
		httpDelete.setURI(URI.create(url + "?" + param));
		HttpResponse response = httpClient.execute(httpDelete);
		String httpEntityContent = getHttpEntityContent(response);
		httpDelete.abort();
		return httpEntityContent;
	}


	/**
	 * 设置请求参数
	 *
	 * @param
	 * @return
	 */
	private static List<NameValuePair> setHttpParams(Map<String, String> paramMap) {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		Set<Map.Entry<String, String>> set = paramMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return formparams;
	}

	/**
	 * 获得响应HTTP实体内容
	 *
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private static String getHttpEntityContent(HttpResponse response) throws IOException, UnsupportedEncodingException {
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream is = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = br.readLine();
			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line + "\n");
				line = br.readLine();
			}
			return sb.toString();
		}
		return "";
	}

	public static String downloadImg(String imgUrl, String path)     {
		try {
			logger.debug("imgUrl:{}\r\npath:{}",imgUrl,path);
			URL url = new URL(imgUrl);
			//链接网络地址
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			//获取链接的输出流
			InputStream is = connection.getInputStream();
			//创建文件，fileName为编码之前的文件名
			File file = new File(path);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			//根据输入流写入文件
			FileOutputStream out = new FileOutputStream(file);
			int i = 0;
			while ((i = is.read()) != -1) {
				out.write(i);
			}
			out.close();
			is.close();
			return path;
		}catch (Exception e) {
			logger.debug("抓取图片异常imgUrl:{}\r\npath:{}",imgUrl,path,e);
		}
		return null;

	}

}
