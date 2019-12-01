package com.yx.sys.rpc.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.Constants;
import com.yx.common.core.annotation.DistributedLock;
import com.yx.common.core.base.BaseServiceImpl;
import com.yx.common.core.exception.BusinessException;
import com.yx.sys.common.UmpConstants;
import com.yx.sys.dao.mapper.SysMenuMapper;
import com.yx.sys.dao.mapper.SysRoleMenuMapper;
import com.yx.sys.dao.mapper.SysTreeMapper;
import com.yx.sys.model.SysMenu;
import com.yx.sys.model.SysTree;
import com.yx.sys.rpc.api.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
@Slf4j
@Service("sysMenuService")
@CacheConfig(cacheNames = UmpConstants.UmpCacheName.MENU)
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysTreeMapper sysTreeMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    @Cacheable
    public List<SysMenu> queryList() {
        SysMenu sysMenuModel = new SysMenu();
        // 状态为：启用
        sysMenuModel.setEnable(1);
        // 是否删除：否
        sysMenuModel.setIsDel(0);
        EntityWrapper<SysMenu> entityWrapper = new EntityWrapper<>(sysMenuModel);
        entityWrapper.orderBy(" parent_id, sort_no ", true);
        return super.selectList(entityWrapper);
    }

    @Override
    public Page<SysMenu> queryListPage(Page<SysMenu> page) {
        SysMenu menu = new SysMenu();
        menu.setEnable(1);
        menu.setIsDel(0);
        EntityWrapper<SysMenu> wrapper = new EntityWrapper<>(menu);
        wrapper.eq("a.is_del", 0).eq("a.enable_", 1);
        if (ObjectUtil.isNotNull(page.getCondition())) {
            StringBuilder conditionSql = new StringBuilder();
            Map<String, Object> paramMap = page.getCondition();
            paramMap.forEach((k, v) -> {
                if (NumberUtil.isNumber(v + "")) {
                    conditionSql.append("a.").append(k + " = " + v + " and ");
                } else if (StrUtil.isNotBlank(v + "")) {
                    conditionSql.append("a.").append(k + " like '%" + v + "%' and ");
                }
            });
            if (StrUtil.isNotBlank(conditionSql)) {
                wrapper.where(StrUtil.removeSuffix(conditionSql.toString(), "and "));
            }
        }
        wrapper.orderBy(" parent_id, sort_no ", true);
        page.setCondition(null);
        return super.selectPage(page, wrapper);
    }

    @Override
    @Cacheable
    public List<SysTree> queryMenuTreeByUserId(Long userId) {
        List<SysMenu> sysMenuModelList = null;
        // 如果是超级管理员，则查询所有目录菜单
        if (UmpConstants.USERID_ADMIN.equals(userId)) {
            SysMenu sysMenuModel = new SysMenu();
            sysMenuModel.setEnable(1);
            sysMenuModel.setIsShow(1);
            sysMenuModel.setIsDel(0);
            EntityWrapper<SysMenu> wrapper = new EntityWrapper<>(sysMenuModel);
            wrapper.ne("menu_type", 2);
            wrapper.orderBy("parent_id, sort_no", true);
            sysMenuModelList = sysMenuMapper.selectList(wrapper);
        } else {
            sysMenuModelList = sysMenuMapper.selectMenuTreeByUserId(userId);
        }
        return convertTreeData(sysMenuModelList, null);
    }

    @Override
    @Cacheable
    public List<SysTree> queryFuncMenuTree() {
        List<SysMenu> sysMenuModelList = queryList();
        return convertTreeData(sysMenuModelList, null);
    }

    @Override
    @Cacheable(value = UmpConstants.UmpCacheName.ROLE)
    public List<SysTree> queryFuncMenuTree(Long roleId) {
        List<SysMenu> sysMenuModelList = queryList();
        List<Long> menuIdList = sysRoleMenuMapper.selectMenuIdListByRoleId(roleId);
        System.out.println("menuIdList:" + JSON.toJSONString(menuIdList));
        return convertTreeData(sysMenuModelList, menuIdList.toArray());
    }

    @Override
    @Cacheable
    public List<SysTree> queryTree(Long id, Integer menuType) {
        List<SysTree> sysTreeModelList = sysTreeMapper.selectMenuTree(id, menuType);
        List<SysTree> list = SysTree.getTree(sysTreeModelList);
        return list;
    }

    @Override
    @DistributedLock
    @CacheEvict(value = UmpConstants.UmpCacheName.MENU,allEntries = true)
    public Boolean delete(Long id) throws BusinessException {
        //查询是否有子菜单，如果有则返回false，否则允许删除
        EntityWrapper<SysMenu> entityWrapper = new EntityWrapper<SysMenu>();
        SysMenu sysMenuModel = new SysMenu();
        sysMenuModel.setParentId((Long) id);
        sysMenuModel.setIsDel(0);
        entityWrapper.setEntity(sysMenuModel);
        List<SysMenu> childs = super.selectList(entityWrapper);
        if (CollectionUtil.isNotEmpty(childs)) {
            log.error("删除菜单[id:{}]失败，请先删除子菜单", id);
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "删除菜单失败，请先删除子菜单");
        }
        sysMenuModel = new SysMenu();
        sysMenuModel.setId(id);
        sysMenuModel.setIsDel(1);
        sysMenuModel = super.modifyById(sysMenuModel);
        if (sysMenuModel != null) {
            return true;
        }
        return false;
    }

    @Override
    @CacheEvict(value = UmpConstants.UmpCacheName.MENU, allEntries = true)
    public Integer deleteBatch(Long[] ids) {
        int succ = 0;
        for (Long id : ids) {
            Boolean res = false;
            try {
                res = this.delete(id);
            } catch (Exception e) {
                log.error("删除菜单失败，id:{}", id, e);
            }
            if (res) {
                succ++;
            }
            log.debug("删除菜单完成，id:{}，执行结果：{}", id, res);
        }
        return succ;
    }

    @Override
    public Page<SysMenu> selectVOByPage(Page<SysMenu> page, SysMenu sysMenu) {
        List<SysMenu> list = sysMenuMapper.selectVOByPage(page, sysMenu);
        page.setRecords(list);
        return page;
    }

    @Override
    @CachePut
    @DistributedLock(value = "#sysMenuModel.getParentId()")
    public SysMenu add(SysMenu sysMenuModel) throws BusinessException {
        //名称重复验证，同一目录下，菜单名称不能相同
        SysMenu menuModel = new SysMenu();
        menuModel.setParentId(sysMenuModel.getParentId());
        menuModel.setMenuName(sysMenuModel.getMenuName());
        EntityWrapper<SysMenu> entityWrapper = new EntityWrapper<>(menuModel);
        List<SysMenu> sysMenuModelList = super.selectList(entityWrapper);
        if (CollUtil.isNotEmpty(sysMenuModelList)) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "同一目录下，菜单名称不能相同");
        }
        return super.add(sysMenuModel);
    }

    @Override
    @CacheEvict(value = {UmpConstants.UmpCacheName.MENU,UmpConstants.UmpCacheName.ROLE,UmpConstants.UmpCacheName.USER }, allEntries = true)
    public SysMenu modifyById(SysMenu sysMenuModel) throws BusinessException {
        if (StrUtil.isNotBlank(sysMenuModel.getMenuName())) {
            //名称重复验证，同一目录下，菜单名称不能相同（需要排除自己）
            SysMenu menuModel = new SysMenu();
            menuModel.setParentId(sysMenuModel.getParentId());
            menuModel.setMenuName(sysMenuModel.getMenuName());
            menuModel.setIsDel(0);
            EntityWrapper<SysMenu> entityWrapper = new EntityWrapper<>(menuModel);
            entityWrapper.ne("id", sysMenuModel.getId());
            List<SysMenu> sysMenuModelList = super.selectList(entityWrapper);
            if (CollUtil.isNotEmpty(sysMenuModelList)) {
                throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "同一目录下，菜单名称不能相同");
            }
        }
        return super.modifyById(sysMenuModel);
    }

    /**
     * 获取树模型结构数据
     *
     * @param sysMenuModelList
     * @param checkedMenuIds
     * @return List<SysTreeModel>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    private List<SysTree> convertTreeData(List<SysMenu> sysMenuModelList, Object[] checkedMenuIds) {
        Map<Long, List<SysTree>> map = new HashMap<>(3);
        for (SysMenu sysMenuModel : sysMenuModelList) {
            if (sysMenuModel != null && map.get(sysMenuModel.getParentId()) == null) {
                List<SysTree> children = new ArrayList<>();
                map.put(sysMenuModel.getParentId(), children);
            }
            map.get(sysMenuModel.getParentId()).add(convertTreeModel(sysMenuModel, checkedMenuIds));
        }
        List<SysTree> result = new ArrayList<>();
        for (SysMenu sysMenuModel : sysMenuModelList) {
            boolean flag = sysMenuModel != null && sysMenuModel.getParentId() == null || sysMenuModel.getParentId() == 0;
            if (flag) {
                SysTree sysTreeModel = convertTreeModel(sysMenuModel, checkedMenuIds);
                sysTreeModel.setChildren(getChild(map, sysMenuModel.getId()));
                result.add(sysTreeModel);
            }
        }
        return result;
    }

    /**
     * 递归获取子树模型结构数据
     *
     * @param map
     * @param id
     * @return List<SysTreeModel>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    private List<SysTree> getChild(Map<Long, List<SysTree>> map, Long id) {
        List<SysTree> treeModelList = map.get(id);
        if (treeModelList != null) {
            for (SysTree treeModel : treeModelList) {
                if (treeModel != null) {
                    treeModel.setChildren(getChild(map, treeModel.getId()));
                }
            }
        }
        return treeModelList;
    }

    /**
     * 把菜单模型对象转换成树模型对象
     *
     * @param sysMenuModel
     * @param checkedMenuIds
     * @return SysTreeModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    private SysTree convertTreeModel(SysMenu sysMenuModel, Object[] checkedMenuIds) {
        SysTree sysTreeModel = new SysTree();
        sysTreeModel.setId(sysMenuModel.getId());
        sysTreeModel.setName(sysMenuModel.getMenuName());
        sysTreeModel.setIcon(sysMenuModel.getIconcls());
        sysTreeModel.setSpread(sysMenuModel.getExpand() == 1);
        sysTreeModel.setHref(sysMenuModel.getRequest());
        sysTreeModel.setPermission(sysMenuModel.getPermission());
        sysTreeModel.setChecked(checkedMenuIds != null && ArrayUtil.contains(checkedMenuIds, sysMenuModel.getId()));
        sysTreeModel.setDisabled(false);
        return sysTreeModel;
    }
}
