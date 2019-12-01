package com.yx.common.oss.configuration;

import com.alibaba.fastjson.JSON;
import com.yx.common.oss.OssProperties;
import com.yx.common.oss.service.CloudStorageService;
import com.yx.common.oss.service.impl.AliCloudStorageServiceImpl;
import com.yx.common.oss.service.impl.QqCloudStorageServiceImpl;
import com.yx.common.utils.SpringContextUtils;
import com.yx.sys.model.SysParam;
import com.yx.sys.rpc.api.SysParamService;

public final class OSSFactory {

    /**
     * 云存储配置KEY
     */
    public final static String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";



    private static SysParamService sysParamService;

    static {
        OSSFactory.sysParamService = (SysParamService) SpringContextUtils.getBean("sysParamService");
    }

    public static CloudStorageService build() {
        SysParam sysParam = sysParamService.queryByKey(CLOUD_STORAGE_CONFIG_KEY);
        final OssProperties ossProperties = JSON.parseObject(sysParam.getParamValue(), OssProperties.class);

        if (ossProperties.getType() == null) {
            return null;
        }
        if (ossProperties.getType().equals(1)) {
            return new AliCloudStorageServiceImpl(ossProperties);
        } else if (ossProperties.getType().equals(2)) {
            return new QqCloudStorageServiceImpl(ossProperties);
        }
        return null;
    }

}
