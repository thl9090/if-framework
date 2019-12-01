package com.yx.common.web.filter;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * CSRF过滤器
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@Slf4j
public class CsrfFilter implements Filter {

    /**
     * 白名单
     */
    private List<String> whiteUrls;

    private int size = 0;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 读取文件
        String path = CsrfFilter.class.getResource("/").getFile();
        whiteUrls = FileUtil.readUtf8Lines(path + "csrf-white.txt");
        size = null == whiteUrls ? 0 : whiteUrls.size();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        // 获取请求url地址
        String url = req.getRequestURL().toString();
        String referurl = req.getHeader("Referer");
        if (isWhiteReq(referurl)) {
            chain.doFilter(request, response);
        } else {
            req.getRequestDispatcher("/").forward(req, res);
            StringBuilder logSb = new StringBuilder();
            String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String clientIp = HttpUtil.getClientIP(req);
            // 记录跨站请求日志
            logSb.append("跨站请求---->>>").append(clientIp).append("||").append(date).append("||").append(referurl)
                    .append("||").append(url);
            //TODO 使用消息中间件记录日志
            log.warn(logSb.toString());
        }
    }

    /**
     * 判断是否是白名单
     *
     * @param referUrl url
     * @return boolean
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    private boolean isWhiteReq(String referUrl) {
        if (referUrl == null || "".equals(referUrl) || size == 0) {
            return true;
        } else {
            String refHost = "";
            referUrl = referUrl.toLowerCase();
            String httpUrlPrefix = "http://";
            String httpsUrlPrefix = "https://";
            if (referUrl.startsWith(httpUrlPrefix)) {
                refHost = referUrl.substring(7);
            } else if (referUrl.startsWith(httpsUrlPrefix)) {
                refHost = referUrl.substring(8);
            }

            for (String urlTemp : whiteUrls) {
                if (refHost.contains(urlTemp.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void destroy() {
    }
}
