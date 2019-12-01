package com.yx.sys.rpc.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseServiceImpl;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.core.model.PageModel;
import com.yx.common.utils.DateUtils;
import com.yx.sys.common.UmpConstants;
import com.yx.sys.dao.mapper.SysParamMapper;
import com.yx.sys.model.SysParam;
import com.yx.sys.model.company.Company;
import com.yx.sys.rpc.api.SysParamService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 全局参数表 服务实现类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
@Service("sysParamService")
@CacheConfig(cacheNames = UmpConstants.UmpCacheName.PARAM)
public class SysParamServiceImpl extends BaseServiceImpl<SysParamMapper, SysParam> implements SysParamService {

    @Autowired
    private SysParamMapper sysParamMapper;

    @Override
    public Page<SysParam> queryListPage(Page<SysParam> page) {
        SysParam sysParamModel = new SysParam();
        sysParamModel.setIsDel(0);
        EntityWrapper<SysParam> entityWrapper = new EntityWrapper<>(sysParamModel);
        if (ObjectUtil.isNotNull(page.getCondition())) {
            StringBuilder conditionSql = new StringBuilder();
            Map<String, Object> paramMap = page.getCondition();
            paramMap.forEach((k, v) -> {
                if (StrUtil.isNotBlank(v + "")) {
                    conditionSql.append(k + " like '%" + v + "%' AND ");
                }
            });
            entityWrapper.and(StrUtil.removeSuffix(conditionSql.toString(), "AND "));
            entityWrapper.orderBy("id",false);
        }
        page.setCondition(null);
        return super.selectPage(page, entityWrapper);
    }

    @Override
    public SysParam queryByKey(String key) {
        return sysParamMapper.getByKey(key);
    }

    @Override
    public Company selectCompany(PageModel<Company> pageModel) {

        return sysParamMapper.selectCompany(pageModel);
    }

    @Override
    public void updateCompany(Company entity) {

        this.sysParamMapper.updateCompany("COMPANY_NAME",entity.getCompanyName());
        this.sysParamMapper.updateCompany("COMPANY_ADDRESS",entity.getAddress());
        this.sysParamMapper.updateCompany("COMPANY_STAMP_URL",entity.getMsg());
        this.sysParamMapper.updateCompany("COMPANY_SERVICE_PHONE",entity.getServicePhone());
        this.sysParamMapper.updateCompany("COMPANY_SERVICE_TIME",entity.getServiceTime());
        this.sysParamMapper.updateCompany("COMPANY_TEL_PHONE",entity.getTelPhone());
        this.sysParamMapper.updateCompany("COMPANY_RECORDATION",entity.getRecordation());
        this.sysParamMapper.updateCompany("COMPANY_PROMPT",entity.getPrompt());
        this.sysParamMapper.updateCompany("COMPANY_PLATFORM",entity.getPlatform());
        this.sysParamMapper.updateCompany("COMPANY_URL",entity.getUrl());
        this.sysParamMapper.updateCompany("COMPANY_EMAIL",entity.getEmail());
    }

    @Override
    public String queryStringByKey(String key) {
        return sysParamMapper.getStringByKey(key);
    }

    @Override
    public BigDecimal queryBigDecimalByKey(String key) {
        String param = sysParamMapper.getStringByKey(key);
        if (StringUtils.isBlank(param)) {
            return null;
        }
        return BigDecimal.valueOf(Double.valueOf(param));
    }

    @Override
    public Long queryLongByKey(String key) {
        String param = sysParamMapper.getStringByKey(key);
        if (StringUtils.isBlank(param)) {
            return null;
        }
        return Long.valueOf(param);
    }

    @Override
    public Integer queryIntegerByKey(String key) {
        String param = sysParamMapper.getStringByKey(key);
        if (StringUtils.isBlank(param)) {
            return null;
        }
        return Integer.valueOf(param);
    }

    @Override
    public Date queryDateByKey(String key) {
        String param = sysParamMapper.getStringByKey(key);
        if (StringUtils.isBlank(param)) {
            return null;
        }
        return DateUtils.parseDate(param);
    }

    @Override
    public <T> List<T> querySplitListByKey(String key, String regex, Class<T> clazz) {
        String param = sysParamMapper.getStringByKey(key);
        if (StringUtils.isBlank(param)) {
            return null;
        }
        String[] strings = param.split(regex);
        List<T> result = new ArrayList<T>(strings.length);
        for (String string : strings) {
            result.add(JSONObject.parseObject(string, clazz));
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(JSONObject.parseObject("368", Long.class));
    }


    @Override
    public int updateParam(String key, String param) {
        return sysParamMapper.updateParam(key, param);
    }


    @Override
    @CacheEvict(value = UmpConstants.UmpCacheName.PARAM, allEntries = true)
    public SysParam add(SysParam paramModel) throws BusinessException {
        return super.add(paramModel);
    }

    @Override
    @CacheEvict(value = UmpConstants.UmpCacheName.PARAM, allEntries = true)
    public boolean deleteBatchIds(List<? extends Serializable> idList) {
        List<SysParam> sysParamModelList = new ArrayList<SysParam>();
        idList.forEach(id -> {
            SysParam entity = new SysParam();
            entity.setId((Long) id);
            entity.setIsDel(1);
            entity.setUpdateTime(new Date());
            sysParamModelList.add(entity);
        });
        return super.updateBatchById(sysParamModelList);
    }

}
