package com.yx.common.jpush.configuration;

import com.alibaba.fastjson.JSON;
import com.yx.common.jpush.properties.JpushProperties;
import com.yx.common.jpush.service.JpushService;
import com.yx.common.jpush.service.impl.JpushServiceImpl;
import com.yx.common.utils.SpringContextUtils;
import com.yx.sys.model.SysParam;
import com.yx.sys.rpc.api.SysParamService;
import org.apache.commons.lang.StringUtils;

public final class JpushFactory {

    /**
     * 极光推送配置KEY
     */
    public final static String JPUSH_CONFIG_KEY = "JPUSH_CONFIG_KEY";



    private static SysParamService sysParamService;

    static {
//        JpushFactory.sysParamService = (SysParamService) SpringContextUtils.getBean("sysParamService");
    }

    public static JpushService build() {
        JpushProperties jpushProperties=new JpushProperties();
        jpushProperties.setAppKey("yourappkey");
        jpushProperties.setMasterSecret("yoursecret");
//        SysParam sysParam = sysParamService.queryByKey(JPUSH_CONFIG_KEY);
//        final JpushProperties jpushProperties = JSON.parseObject(sysParam.getParamValue(), JpushProperties.class);

        if (StringUtils.isBlank(jpushProperties.getAppKey())) {
            return null;
        }
        if (StringUtils.isBlank(jpushProperties.getMasterSecret())) {
            return null;
        }

        return new JpushServiceImpl(jpushProperties);
    }

    public static JpushProperties getProperties() {
        JpushProperties properties=new JpushProperties();
        properties.setAppKey("yourappkey");
        properties.setMasterSecret("yourSecret");

        return properties;

    }

    public static void main(String[] args) {
        System.out.println("JPUSH_CONFIG_KEY:"+JSON.toJSONString(getProperties()));
    }

}
