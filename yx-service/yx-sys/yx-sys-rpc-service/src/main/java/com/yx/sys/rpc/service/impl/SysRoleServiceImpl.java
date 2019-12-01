package com.yx.sys.rpc.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.Constants;
import com.yx.common.core.base.BaseServiceImpl;
import com.yx.common.core.exception.BusinessException;
import com.yx.sys.common.UmpConstants;
import com.yx.sys.dao.mapper.SysRoleMapper;
import com.yx.sys.model.SysRole;
import com.yx.sys.model.SysRoleMenu;
import com.yx.sys.rpc.api.SysRoleMenuService;
import com.yx.sys.rpc.api.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
@Service("sysRoleService")
@CacheConfig(cacheNames = UmpConstants.UmpCacheName.ROLE)
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public Page<SysRole> queryListPage(Page<SysRole> page) {
        SysRole sysRoleModel = new SysRole();
        sysRoleModel.setIsDel(0);
        EntityWrapper<SysRole> entityWrapper = new EntityWrapper<>(sysRoleModel);
        if (ObjectUtil.isNotNull(page.getCondition())) {
            StringBuilder conditionSql = new StringBuilder();
            Map<String, Object> paramMap = page.getCondition();
            String deptId = "dept_id";
            paramMap.forEach((k, v) -> {
                if (StrUtil.isNotBlank(v + "")) {
                    if (deptId.equals(k)) {
                        conditionSql.append(k + " = " + v + " AND ");
                    } else {
                        conditionSql.append(k + " like '%" + v + "%' AND ");
                    }
                }
            });
            entityWrapper.and(StrUtil.removeSuffix(conditionSql.toString(), "AND "));
        }
        page.setCondition(null);

        return page.setRecords(sysRoleMapper.selectRoleList(page, entityWrapper));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = UmpConstants.UmpCacheName.ROLE, allEntries = true)
    public SysRole add(SysRole sysRoleModel) throws BusinessException {
        // 根据角色名称和部门检查是否存在相同的角色
        SysRole checkModel = new SysRole();
        checkModel.setIsDel(0);
        checkModel.setRoleName(sysRoleModel.getRoleName());
        checkModel.setDeptId(sysRoleModel.getDeptId());
        EntityWrapper<SysRole> entityWrapper = new EntityWrapper<>(checkModel);
        if (ObjectUtil.isNotNull(super.selectOne(entityWrapper))) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(),"已存在相同名称的角色");
        }
        SysRole result = super.add(sysRoleModel);
        // 这里增加CollectionUtil.isNotEmpty(sysRoleModel.getMenuIdList())判断是由于新增角色时允许不选择权限
        if (result != null && CollectionUtil.isNotEmpty(sysRoleModel.getMenuIdList())) {
            sysRoleMenuService.insertBatch(getRoleMenuListByMenuIds(sysRoleModel, sysRoleModel.getMenuIdList()));
        }
        return result;
    }


    @Override
    @CacheEvict(value = {UmpConstants.UmpCacheName.ROLE,UmpConstants.UmpCacheName.MENU}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public SysRole modifyById(SysRole sysRoleModel) throws BusinessException {
        SysRole result = super.modifyById(sysRoleModel);
        // 这里增加CollectionUtil.isNotEmpty(sysRoleModel.getMenuIdList())判断是由于删除角色时实际会调用modifyById方法去更新is_del字段，只有当修改角色时menuIdList才不会为空
        if (CollectionUtil.isNotEmpty(sysRoleModel.getMenuIdList())) {
            SysRoleMenu sysRoleMenuModel = new SysRoleMenu();
            sysRoleMenuModel.setRoleId(sysRoleModel.getId());
            EntityWrapper<SysRoleMenu> entityWrapper = new EntityWrapper<>(sysRoleMenuModel);
            // 关系数据相对不重要，直接数据库删除
            sysRoleMenuService.delete(entityWrapper);
            sysRoleMenuService.insertBatch(getRoleMenuListByMenuIds(sysRoleModel, sysRoleModel.getMenuIdList()));
        }
        return result;
    }

    /**
     * 根据角色实体和角色对应的菜单ID集合获取角色菜单实体集合
     *
     * @param sysRoleModel
     * @param menuIds
     * @return
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    private List<SysRoleMenu> getRoleMenuListByMenuIds(SysRole sysRoleModel, List<Long> menuIds) {
        List<SysRoleMenu> sysRoleMenuModelList = new ArrayList<>(5);
        for (Long menuId : menuIds) {
            SysRoleMenu sysRoleMenuModel = new SysRoleMenu();
            sysRoleMenuModel.setRoleId(sysRoleModel.getId());
            sysRoleMenuModel.setMenuId(menuId);
            sysRoleMenuModel.setCreateBy(sysRoleModel.getUpdateBy());
            sysRoleMenuModel.setUpdateBy(sysRoleModel.getUpdateBy());
            sysRoleMenuModelList.add(sysRoleMenuModel);
        }
        return sysRoleMenuModelList;
    }

    @Override
    @Cacheable
    public List<SysRole> queryRoles(Long deptId) {
        Assert.notNull(deptId);
        EntityWrapper<SysRole> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("dept_id", deptId);
        return sysRoleMapper.selectList(entityWrapper);
    }

    @Override
    @CacheEvict(value = UmpConstants.UmpCacheName.ROLE, allEntries = true)
    public boolean deleteBatchIds(List<? extends Serializable> idList) {
        List<SysRole> roleModelList = new ArrayList<SysRole>();
        idList.forEach(id -> {
            SysRole entity = new SysRole();
            entity.setId((Long) id);
            entity.setIsDel(1);
            entity.setUpdateTime(new Date());
            roleModelList.add(entity);
        });
        return super.updateBatchById(roleModelList);
    }
}
