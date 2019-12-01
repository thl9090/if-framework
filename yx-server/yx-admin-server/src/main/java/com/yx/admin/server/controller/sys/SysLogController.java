package com.yx.admin.server.controller.sys;


import com.alibaba.fastjson.JSON;
import com.yx.common.core.model.PageModel;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.model.SysLog;
import com.yx.sys.rpc.api.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
@RestController
@RequestMapping("/log")
@Slf4j
@Api(value = "日志管理", description = "系统日志管理")
public class SysLogController extends BaseController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 查询日志分页方法
     *
     * @param pageModel
     * @return com.yx.common.web.model.ResultModel
     * @author TangHuaLiang
     * @date 17/12/26 12:28:13
     */
    @ApiOperation(value = "分页查询日志列表", notes = "根据分页参数查询日志列表")
    @PostMapping("/queryListPage")
    @RequiresPermissions("sys:log:read")
    public ResultModel queryListPage(@RequestBody PageModel<SysLog> pageModel) {
        SysLog sysLog=new SysLog();
        final Map condition = pageModel.getCondition();
        if(condition!=null) {
            String jsonStr = JSON.toJSONString(condition);
            sysLog = JSON.parseObject(jsonStr, SysLog.class);
        }
        pageModel = (PageModel<SysLog>) sysLogService.selectPage(pageModel,sysLog);
        return ResultUtil.ok(pageModel);
    }
}

