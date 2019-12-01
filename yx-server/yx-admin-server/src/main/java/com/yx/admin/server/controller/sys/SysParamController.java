package com.yx.admin.server.controller.sys;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.yx.admin.server.annotation.SysLogOpt;
import com.yx.common.core.Constants;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.core.model.PageModel;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.model.SysParam;
import com.yx.sys.rpc.api.SysParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 参数管理 前端控制器
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
@RestController
@RequestMapping("/param")
@Api(value = "参数管理", description = "参数管理")
public class SysParamController extends BaseController {

    @Autowired
    private SysParamService sysParamService;

    /**
     * 根据参数ID查询参数
     *
     * @param paramId 参数主键
     * @return ResultModel<SysParamModel>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "查询参数", notes = "根据参数主键ID查询参数")
    @ApiImplicitParam(name = "id", value = "参数主键ID", required = true, dataType = "Long")
    @PostMapping("/query")
    @RequiresPermissions("sys:param:read")
    public ResultModel query(@RequestBody Long paramId) {
        Assert.notNull(paramId);
        SysParam sysParamModel = sysParamService.queryById(paramId);
        return ResultUtil.ok(sysParamModel);
    }

    /**
     * 分页查询参数列表
     *
     * @param pageModel 分页对象
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "分页查询参数", notes = "根据分页参数查询参数列表")
    @PostMapping("/listPage")
    @RequiresPermissions("sys:param:read")
    public ResultModel queryListPage(@RequestBody PageModel pageModel) {
        pageModel = (PageModel) sysParamService.queryListPage(pageModel);
        return ResultUtil.ok(pageModel);
    }

    /**
     * 新增参数
     *
     * @param sysParamModel 参数实体
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "新增参数", notes = "根据参数实体新增参数")
    @PostMapping("/add")
    @RequiresPermissions("sys:param:update")
    @SysLogOpt(module = "参数管理", value = "参数新增", operationType = Constants.LogOptEnum.ADD)
    public ResultModel add(@Valid @RequestBody SysParam sysParamModel) throws BusinessException {
        sysParamModel.setCreateBy(super.getCurrentUserId());
        sysParamModel.setUpdateBy(super.getCurrentUserId());
        return ResultUtil.ok(sysParamService.add(sysParamModel));
    }

    /**
     * 修改参数
     *
     * @param sysParamModel 参数实体
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "修改参数", notes = "根据参数ID修改参数")
    @PostMapping("/modify")
    @RequiresPermissions("sys:param:update")
    @SysLogOpt(module = "参数管理", value = "参数修改", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel modify(@RequestBody SysParam sysParamModel) throws BusinessException {
        sysParamModel.setUpdateBy(super.getCurrentUserId());
        sysParamService.modifyById(sysParamModel);
        return ResultUtil.ok();
    }

    /**
     * 根据参数ID集合批量删除
     *
     * @param ids 主键集合
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "批量删除参数", notes = "根据主键ID集合批量删除参数")
    @DeleteMapping("/deleteBatchByIds")
    @RequiresPermissions("sys:param:delete")
    @SysLogOpt(module = "参数管理", value = "参数批量删除", operationType = Constants.LogOptEnum.DELETE)
    public ResultModel deleteBatchByIds(@RequestBody List<Long> ids) throws BusinessException {
        if (CollectionUtil.isEmpty(ids)) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "参数ID集合不能为空");
        }
        return ResultUtil.ok(sysParamService.deleteBatchIds(ids));
    }
}

