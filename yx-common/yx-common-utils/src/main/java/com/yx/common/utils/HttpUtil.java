package com.yx.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.*;

/**
 * http请求工具类
 */
@SuppressWarnings({ "resource", "deprecation" })
public class HttpUtil {
	public List<NameValuePair> setParams(Map<String, String> map) {
		List<NameValuePair> params = new ArrayList<>();
		StringBuffer buffer = new StringBuffer("<FM>");
		Set<Map.Entry<String, String>> set = map.entrySet();
        for (Map.Entry<String, String> entry : set) {
        	buffer.append("<"+entry.getKey()+">"+entry.getValue()+"</"+entry.getKey()+">");
        }
        buffer.append("</FM>");
        params.add(new BasicNameValuePair("FM",buffer.toString()));
        return params;
	}
	
	/**
	 * 发送get请求
	 * @throws Exception
	 */
	public Map<String, String> get(String url) throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		httpClient.execute(httpGet);
		
		Map<String, String> httpEntityContent = new HashMap<>();
		httpEntityContent.put("code", "200");

		httpGet.abort();
		return httpEntityContent;
	}
	
	/**
	 * 发送post请求
	 * @throws Exception
	 */
	public Map<String, String> post(Map<String, String> paramMap, String url) throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> formparams = setParams(paramMap);
        UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");
        httpPost.setEntity(param);
        HttpResponse response = httpClient.execute(httpPost);
        
        Map<String, String> httpEntityContent = getHttpEntityContent(response);
        httpPost.abort();
        return httpEntityContent;
	}
	
	/**
	 * 解析响应参数
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getHttpEntityContent(HttpResponse response) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		
		HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream is = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = br.readLine();
            
            if (line == null) {
            	int code = response.getStatusLine().getStatusCode();
            	map.put("code", code + "");
            	return map;
			}
            
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line + "\n");
                line = br.readLine();
            }
            
            String xml = sb.toString();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
            DocumentBuilder builder = factory.newDocumentBuilder();   
            Document doc = builder.parse(new InputSource(new StringReader(xml)));   
 
            Element root = doc.getDocumentElement();   
            NodeList books = root.getChildNodes();   
            if (books != null) {  
               for (int i = 0; i < books.getLength(); i++) {   
                    Node book = books.item(i);
                    String nodeName = book.getNodeName();
                    if (nodeName != null && book.getFirstChild() != null) {
                    	map.put(book.getNodeName(), book.getFirstChild().getNodeValue());
					}
                    continue;
                }   
            }   
        }
        return map;
	}
}
