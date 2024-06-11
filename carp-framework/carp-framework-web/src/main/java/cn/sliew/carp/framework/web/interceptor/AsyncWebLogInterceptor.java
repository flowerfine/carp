package cn.sliew.carp.framework.web.interceptor;

import cn.sliew.carp.framework.web.util.RequestParamUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

@Slf4j
public class AsyncWebLogInterceptor implements AsyncHandlerInterceptor {

    /**
     * exception catched by GlobalExceptionHandler and here can't be aware of ex
     *
     * @see cn.sliew.carp.framework.web.exception.GlobalExceptionHandler
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logQuery(request);
        ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (responseWrapper != null) {
            responseWrapper.copyBodyToResponse();
        }
    }

    private void logQuery(HttpServletRequest request) {
        if (!RequestParamUtil.ignorePath(request.getRequestURI()) && log.isInfoEnabled()) {
            String params = RequestParamUtil.formatRequestParams(request);
            log.info("{} {} {}", request.getMethod(), request.getRequestURI(), params);
        }
    }
}
