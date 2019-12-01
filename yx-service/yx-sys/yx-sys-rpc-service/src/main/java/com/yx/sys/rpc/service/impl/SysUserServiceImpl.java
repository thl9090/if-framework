package com.yx.sys.rpc.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.annotation.DistributedLock;
import com.yx.common.core.base.BaseServiceImpl;
import com.yx.sys.common.UmpConstants;
import com.yx.sys.dao.mapper.SysRoleMapper;
import com.yx.sys.dao.mapper.SysTreeMapper;
import com.yx.sys.dao.mapper.SysUserMapper;
import com.yx.sys.dao.mapper.SysUserRoleMapper;
import com.yx.sys.model.SysRole;
import com.yx.sys.model.SysTree;
import com.yx.sys.model.SysUser;
import com.yx.sys.model.SysUserRole;
import com.yx.sys.model.vo.SysUserVO;
import com.yx.sys.rpc.api.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户管理服务实现
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@Slf4j
@Service("sysUserService")
@CacheConfig(cacheNames = UmpConstants.UmpCacheName.USER)
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysTreeMapper sysTreeMapper;


    @Override
    public SysUser selectById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        return sysUser;
    }

    @Override
	public SysUserVO selectVOById(Long id) {
    	SysUserVO sysUserVO = sysUserMapper.selectVOById(id);
		return sysUserVO;
	}

    @Override
    public Page<SysUser> selectPage(Page<SysUser> page,SysUser sysUser) {
        List<SysUser> list = sysUserMapper.selectPage(page, sysUser);
        page.setRecords(list);
        return page;
    }

    @Override
	public Page<SysUserVO> selectVOPage(Page<SysUserVO> page, SysUserVO sysUserVO) {
    	List<SysUserVO> list = sysUserMapper.selectVOPage(page, sysUserVO);
    	page.setRecords(list);
        return page;
	}



    @Override
    public SysUser selectByAccount(String account) {
        SysUser sysUserModel = new SysUser();
        sysUserModel.setAccount(account);
        sysUserModel.setIsDel(0);
        EntityWrapper<SysUser> entityWrapper = new EntityWrapper<>(sysUserModel);
        return super.selectOne(entityWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = UmpConstants.UmpCacheName.USER, allEntries = true)
    public boolean delBatchByIds(List<Long> ids) {
        List<SysUser> sysUserModelList = new ArrayList<>(5);
        for (Long id : ids) {
            SysUser sysUserModel = new SysUser();
            sysUserModel.setId(id);
            sysUserModel.setIsDel(1);

            sysUserModelList.add(sysUserModel);

            SysUserRole sysUserRoleModel = new SysUserRole();
            sysUserRoleModel.setIsDel(1);
            sysUserRoleModel.setUpdateTime(new Date());
            sysUserRoleModel.setUpdateBy(sysUserModel.getCreateBy());
            EntityWrapper<SysUserRole> wrapper = new EntityWrapper<>();
            wrapper.eq("user_id", sysUserModel.getId());
            sysUserRoleMapper.update(sysUserRoleModel, wrapper);
        }
        return super.updateBatchById(sysUserModelList);
    }

    @Override
    public List<SysRole> selectRolesByDeptId(Long deptId) {
        Assert.notNull(deptId);
        EntityWrapper<SysRole> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("dept_id", deptId);
        return sysRoleMapper.selectList(entityWrapper);
    }

    @Override
    @CacheEvict(value = {UmpConstants.UmpCacheName.USER,UmpConstants.UmpCacheName.MENU }, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @DistributedLock
    public SysUser add(SysUser sysUserModel) {
        sysUserModel.setCreateTime(new Date());
        sysUserModel.setUpdateTime(new Date());
        if (super.insert(sysUserModel)) {
            insertUserRole(sysUserModel);
            return sysUserModel;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {UmpConstants.UmpCacheName.USER,UmpConstants.UmpCacheName.MENU }, allEntries = true)
    public boolean update(SysUser sysUser) {
        boolean result = false;
        EntityWrapper<SysUserRole> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", sysUser.getId());
        sysUserRoleMapper.delete(wrapper);
        insertUserRole(sysUser);
        result = super.updateById(sysUser);

        return result;
    }

    @Override
    public List<SysUserRole> selectUserRoles(Long userId) {
        Assert.notNull(userId);
        EntityWrapper<SysUserRole> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        return sysUserRoleMapper.selectList(wrapper);
    }

    @Override
    public List<SysTree> queryTree() {
        List<SysTree> sysTreeModelList = sysTreeMapper.selectSysUserTree();
        return sysTreeModelList;
    }

    @Override
    public List<SysTree> queryGuaranteeUsertree() {
        return sysTreeMapper.selectGuaranteeUsertree();
    }


    private void insertUserRole(SysUser sysUserModel) {
        if (sysUserModel.getRole() != null && sysUserModel.getRole().length != 0) {
            for (Long roleId : sysUserModel.getRole()) {
                SysUserRole sysUserRoleModel = new SysUserRole();
                sysUserRoleModel.setUserId(sysUserModel.getId());
                sysUserRoleModel.setCreateTime(new Date());
                sysUserRoleModel.setUpdateTime(new Date());
                sysUserRoleModel.setCreateBy(sysUserModel.getCreateBy());
                sysUserRoleModel.setUpdateBy(sysUserModel.getCreateBy());
                sysUserRoleModel.setRoleId(roleId);
                sysUserRoleMapper.insert(sysUserRoleModel);
            }
        }
    }



}
