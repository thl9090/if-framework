package com.yx.admin.server.controller.ueditor.define;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class CommonUtil {

	public static HttpSession getHttpSession(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session == null){
			session = request.getSession(true);
		}
		session.setMaxInactiveInterval(30*60);//session的有效时长为30分钟
		return session;
	}
	
	public static void responseCORS(HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		response.addHeader("Access-Control-Allow-Headers", "Content-Type");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.addHeader("Access-Control-Max-Age", "1800");//30 min
		response.setCharacterEncoding("UTF-8");
	}

	public static Map<String, Object> resquestParameter2Map(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String[]> url = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : url.entrySet()) {
			String key = entry.getKey();
			String[] valueArray = entry.getValue();
			String value;
			value = new String(valueArray[0]);
			map.put(key, value);
		}
		return map;
	}

}
