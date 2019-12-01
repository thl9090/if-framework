package com.yx.sys.rpc.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseServiceImpl;
import com.yx.common.utils.StringUtils;
import com.yx.sys.common.UmpConstants;
import com.yx.sys.dao.mapper.SysDicMapper;
import com.yx.sys.model.SysDic;
import com.yx.sys.rpc.api.SysDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典管理 服务实现类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
@Service("sysDicService")
@CacheConfig(cacheNames = UmpConstants.UmpCacheName.DIC)
public class SysDicServiceImpl extends BaseServiceImpl<SysDicMapper, SysDic> implements SysDicService {

    @Autowired
    private  SysDicMapper sysDicMapper;

    @Override
    public Page<SysDic> queryListPage(Page<SysDic> page) {
        SysDic sysDicModel = new SysDic();
        sysDicModel.setIsDel(0);
        EntityWrapper<SysDic> entityWrapper = new EntityWrapper<>(sysDicModel);
        if (ObjectUtil.isNotNull(page.getCondition())) {
            StringBuilder conditionSql = new StringBuilder();
            Map<String, Object> paramMap = page.getCondition();
            paramMap.forEach((k, v) -> {
                if (StrUtil.isNotBlank(v + "")) {
                    conditionSql.append(k + " like '%" + v + "%' AND ");
                }
            });
            entityWrapper.and(StrUtil.removeSuffix(conditionSql.toString(), "AND "));
        }
        page.setCondition(null);
        return super.selectPage(page, entityWrapper);
    }

    @Override
    public SysDic selectByTypeAndCode(String type,Integer code) {
        if(!StringUtils.isNotEmpty(type) || code == null){
            return null;
        }
        return this.sysDicMapper.selectByTypeAndCode(type,code);
    }

    @Override
    @CacheEvict(value = UmpConstants.UmpCacheName.DIC, allEntries = true)
    public boolean deleteBatchIds(List<? extends Serializable> idList){
        List<SysDic> sysDicModelList = new ArrayList<SysDic>();
        idList.forEach(id -> {
            SysDic entity = new SysDic();
            entity.setId((Long)id);
            entity.setIsDel(1);
            entity.setUpdateTime(new Date());
            sysDicModelList.add(entity);
        });
        return super.updateBatchById(sysDicModelList);
    }
}
