package com.yx.sys.rpc.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yx.sys.common.UmpConstants;
import com.yx.sys.dao.mapper.SysAuthorizeMapper;
import com.yx.sys.dao.mapper.SysMenuMapper;
import com.yx.sys.model.SysMenu;
import com.yx.sys.rpc.api.SysAuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 权限业务处理实现类
 *
 * @author TangHuaLiang
 * @create 2017-11-29
 **/
@Service("sysAuthorizeService")
public class SysAuthorizeServiceImpl implements SysAuthorizeService {

    @Autowired
    private SysAuthorizeMapper sysAuthorizeMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<String> queryPermissionsByUserId(Long userId) {
        List<String> permissions = new ArrayList<String>();
        //如果是超级管理员，则查询所有权限code
        if (UmpConstants.USERID_ADMIN.equals(userId)) {
            EntityWrapper<SysMenu> wrapper = new EntityWrapper<SysMenu>();
            wrapper.eq("is_del", 0);
            List<SysMenu> list = sysMenuMapper.selectList(wrapper);
            if (CollUtil.isNotEmpty(list)) {
                for (SysMenu sysMenuModel : list) {
                    permissions.add(sysMenuModel.getPermission());
                }
            }
            permissions.add(UmpConstants.PERMISSION_ADMIN);
        } else {
            permissions = sysAuthorizeMapper.selectPermissionsByUserId(userId);
        }
        return formatPermissions(permissions);
    }

    /**
     * 格式化权限码集合：逗号分割，去重
     *
     * @param permissions
     * @return
     */
    private List<String> formatPermissions(List<String> permissions) {
        //通过set去重
        HashSet<String> set = new HashSet<>();
        for (String permission : permissions) {
            if (StrUtil.isBlank(permission)) {
                continue;
            }
            //一个菜单有多个权限标识，逗号分隔，需要拆分
            String[] perms = StrUtil.split(permission, ",");
            Arrays.stream(perms).forEach(perm -> {
                        if (StrUtil.isNotBlank(perm)) {
                            set.add(perm);
                        }
                    }
            );
        }
        return new ArrayList<>(set);
    }

}
