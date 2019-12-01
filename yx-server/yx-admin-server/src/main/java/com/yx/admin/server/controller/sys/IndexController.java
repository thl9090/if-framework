package com.yx.admin.server.controller.sys;

import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.rpc.api.SysAuthorizeService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页前端控制器
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Autowired
    private SysAuthorizeService sysAuthorizeService;

    /**
     * 获取当前用户的权限集合
     *
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @GetMapping("/permissions")
    @RequiresAuthentication
    public ResultModel queryMyPermissions() {
        return ResultUtil.ok(sysAuthorizeService.queryPermissionsByUserId(super.getCurrentUserId()));
    }
}
