package com.yx.common.web.configuration;

import com.alibaba.fastjson.JSONObject;
import com.yx.common.core.base.BaseModel;
import com.yx.common.core.constant.AuthorizationConstant;
import com.yx.common.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 平台controller参数转换
 *
 * @author YanBingHao
 * @since 2018/8/1
 */
@Slf4j
public class PlatformMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return BaseModel.class.isAssignableFrom(methodParameter.getParameterType()) || Serializable.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String jsonStr = getJsonStr(nativeWebRequest);
        try {
            return JSONObject.parseObject(jsonStr, methodParameter.getParameterType());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("数据格式异常！");
        }
    }

    /**
     * 获取请求中的参数信息并转成json串
     *
     * @param webRequest
     * @return
     */
    private String getJsonStr(NativeWebRequest webRequest) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String jsonBody = "";
        try {
            jsonBody = IOUtils.toString(request.getInputStream());
            if (StringUtils.isBlank(jsonBody)) {
                Map<String, String> param = new HashMap<String, String>();
                Enumeration<String> parameterNames = request.getParameterNames();
                String element = "";
                while (parameterNames.hasMoreElements()) {
                    element = parameterNames.nextElement();
                    param.put(element, request.getParameter(element));
                }
                jsonBody = JSONObject.toJSONString(param);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //jsonBody = jsonBody.replace(" ", "");

        String accessToken = request.getHeader(AuthorizationConstant.ACCESS_TOKEN_HEADER);
        String channelType = request.getHeader(AuthorizationConstant.CHANNEL_TYPE_HEADER);
        String channelVersion = request.getHeader(AuthorizationConstant.CHANNEL_VERSION_HEADER);

        log.info(String.format("请求头accessToken：【%s】", accessToken));
        log.info(String.format("请求头channelType：【%s】", channelType));
        log.info(String.format("请求头channelVersion：【%s】", channelVersion));

        JSONObject jsonObject = JSONObject.parseObject(jsonBody);

        jsonObject.put("accessToken", accessToken);
        jsonObject.put("channelType", channelType);
        jsonObject.put("channelVersion", channelVersion);

        jsonBody = jsonObject.toJSONString();

        log.info(String.format("请求参数信息：【%s】", jsonBody));
        return jsonBody;
    }

}
