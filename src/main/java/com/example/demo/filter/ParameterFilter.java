package com.example.demo.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.UUID;

/**
 * Parameter Filter
 *
 * @author <a href="mailto:hongwen0928@outlook.com">Karas</a>
 */
@Component
@WebFilter(filterName = "ParameterFilter", urlPatterns = {ParameterFilter.URL_PATTERNS})
public class ParameterFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 全局过滤规则
     */
    public static final String URL_PATTERNS = "/*";

    /**
     * spring boot 监控
     */
    private static final String MONITOR_ACTUATOR = "/actuator";
    private static final String MONITOR_STATUS = "/status";

    /**
     * 无效头
     */
    private static final String INVALID_URL = "unknown";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        try {
            // 简单追踪日志(先根据Source IP找到uniqueId, 再根据uniqueId找到相关的请求日志)
            MDC.put("uniqueId", UUID.randomUUID().toString().replaceAll("-", "")
                    .substring(0, 7) + System.currentTimeMillis() % 100000);
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            String uri = req.getRequestURI();
            if (StringUtils.isNotBlank(uri) && !uri.startsWith(MONITOR_ACTUATOR) && !uri.equals(MONITOR_STATUS)) {
                // 输入Source IP 对应的参数
                JSONObject paramsJson = new JSONObject();
                Enumeration<String> paramNames = req.getParameterNames();
                while (paramNames.hasMoreElements()) {
                    String name = paramNames.nextElement();
                    String value = req.getParameter(name);
                    paramsJson.put(name, value);
                }
                logger.info("Request : {} > Source IP : {} > Parameters : {} ", uri, getIpAddr(req),
                        paramsJson.toString());
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            logger.error("filter error ", e);
        } finally {
            MDC.remove("uniqueId");
        }

    }

    @Override
    public void destroy() {
    }

    /**
     * 获取请求的真实IP
     *
     * @param request 请求
     * @return IP
     */
    private String getIpAddr(HttpServletRequest request) {
        try {
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || (INVALID_URL.equalsIgnoreCase(ip))) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || (INVALID_URL.equalsIgnoreCase(ip))) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || (INVALID_URL.equalsIgnoreCase(ip))) {
                ip = request.getHeader("X-Real-IP");
            }
            if (ip == null || ip.length() == 0 || (INVALID_URL.equalsIgnoreCase(ip))) {
                ip = request.getRemoteAddr();
            }
            if (ip != null) {
                String[] ips = ip.split(",");
                ip = ips[0];
            }
            return ip;
        } catch (Exception e) {
            logger.error("getIpAddr error ", e);
        }
        return null;
    }

}
