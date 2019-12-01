package com.yx.sys.rpc.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.Constants;
import com.yx.common.core.annotation.DistributedLock;
import com.yx.common.core.base.BaseServiceImpl;
import com.yx.common.core.exception.BusinessException;
import com.yx.sys.common.UmpConstants;
import com.yx.sys.dao.mapper.SysDeptMapper;
import com.yx.sys.dao.mapper.SysTreeMapper;
import com.yx.sys.model.SysDept;
import com.yx.sys.model.SysTree;
import com.yx.sys.rpc.api.SysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author TangHuaLiang
 * @Date: 17/12/1 14:49:30
 */
@Service("sysDeptService")
@Slf4j
@CacheConfig(cacheNames = UmpConstants.UmpCacheName.DEPT)
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysTreeMapper sysTreeMapper;

    @Override
    @CachePut
    @DistributedLock(value = "#sysDeptModel.getParentId()")
    public SysDept addDept(SysDept sysDeptModel) throws BusinessException {
        EntityWrapper<SysDept> wrapper = new EntityWrapper<>();

        wrapper.eq("parent_id", sysDeptModel.getParentId());
        wrapper.eq("dept_name", sysDeptModel.getDeptName());
        wrapper.eq("is_del", 0);
        List<SysDept> deptModelList = sysDeptMapper.selectList(wrapper);
        if (ObjectUtil.isNotNull(super.selectOne(wrapper))) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "同级部门名称不能重复");
        }
        sysDeptModel.setUnitId(Long.valueOf(1));
        Date now = new Date();
        sysDeptModel.setCreateTime(now);
        sysDeptModel.setUpdateTime(now);
        if (sysDeptModel.getParentId() == null) {
            sysDeptModel.setParentId(0L);
        }
        return super.add(sysDeptModel);
    }

    @Override
    @CacheEvict(value = UmpConstants.UmpCacheName.DEPT, allEntries = true)
    public boolean modifyDept(SysDept sysDeptModel) {
        return super.updateById(sysDeptModel);
    }

    @Override
    public Page<SysDept> queryListPage(Page<SysDept> page) {
        log.info("SysDeptServiceImpl->queryListPage->page:" + page.toString());
        log.info("SysDeptServiceImpl->queryListPage->page->condition:" + JSON.toJSONString(page.getCondition()));
        String deptName = page.getCondition() == null ? null : page.getCondition().get("dept_name").toString();
        List<SysDept> list = sysDeptMapper.selectPage(page, deptName);
        page.setRecords(list);
        return page;
    }

    @Override
    @Cacheable
    public SysDept queryOne(Long id) {
        log.info("SysDeptServiceImpl->queryOne->id:" + id);
        SysDept sysDeptModel = sysDeptMapper.selectOne(id);
        return sysDeptModel;
    }

    @Override
    @CacheEvict(value = UmpConstants.UmpCacheName.DEPT, allEntries = true)
    public Integer deleteBatch(Long[] ids) {
        int succ = 0;
        for (Long id : ids) {
            Boolean res = false;
            try {
                res = this.delDept(id);
            } catch (Exception e) {e.printStackTrace();
                log.error("删除部门失败，id:{}", id, e);
            }
            if (res) {
                succ++;
            }
            log.debug("删除部门完成，id:{}，执行结果：{}", id, res);
        }
        return succ;
    }

    @Override
    public List<SysTree> queryTree() {
        return this.queryTree(null);
    }

    ;

    @Override
    @Cacheable
    public List<SysTree> queryTree(Long id) {
        List<SysTree> sysTreeModelList = sysTreeMapper.selectDeptTree(id);
        List<SysTree> list = SysTree.getTree(sysTreeModelList);
        return list;
    }

    @Override
    @DistributedLock
    @CacheEvict(value = UmpConstants.UmpCacheName.DEPT, allEntries = true)
    public boolean delDept(Long id) throws BusinessException {
        int subDeptCount = querySubDeptCount(id);
        if (subDeptCount > 0) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(),"必须先删除子部门");
        }
        boolean result = false;
        SysDept sysDeptModel = new SysDept();
        sysDeptModel.setId(id);
        sysDeptModel.setIsDel(1);
        sysDeptModel = super.modifyById(sysDeptModel);
        if (sysDeptModel != null) {
            result = true;
        }
        return result;
    }

    @Override
    @Cacheable
    public List<SysDept> querySubDept(Long id) {
        Assert.notNull(id);
        EntityWrapper<SysDept> wrapper = new EntityWrapper<>();
        wrapper.eq("parent_id", id);
        wrapper.eq("is_del", 0);
        List<SysDept> list = sysDeptMapper.selectList(wrapper);
        return list;
    }

    @Override
    public int querySubDeptCount(Long id) {
        Assert.notNull(id);
        EntityWrapper<SysDept> wrapper = new EntityWrapper<>();
        wrapper.eq("parent_id", id);
        wrapper.eq("is_del", 0);
        return sysDeptMapper.selectCount(wrapper);
    }

    @Override
    public int querySubDeptCount(Long[] ids) {
        Assert.notNull(ids);
        EntityWrapper<SysDept> wrapper = new EntityWrapper<>();
        wrapper.in("parent_id", ids);
        wrapper.eq("is_del", 0);
        return sysDeptMapper.selectCount(wrapper);
    }
}
