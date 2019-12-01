package com.yx.admin.server.controller.sys;

import cn.hutool.core.lang.Assert;
import com.yx.admin.server.annotation.SysLogOpt;
import com.yx.common.core.Constants;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.core.model.PageModel;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.model.SysDept;
import com.yx.sys.model.SysRole;
import com.yx.sys.rpc.api.SysDeptService;
import com.yx.sys.rpc.api.SysRoleService;
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
 * 角色信息表 前端控制器
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
@RestController
@RequestMapping("/role")
@Api(value = "角色管理", description = "角色管理")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 根据角色ID查询角色
     *
     * @param roleId
     * @return ResultModel<SysRoleModel>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "查询角色", notes = "根据角色主键ID查询角色")
    @ApiImplicitParam(name = "id", value = "角色ID", required = true, dataType = "Long")
    @PostMapping("/query")
    @RequiresPermissions("sys:role:read")
    public ResultModel query(@RequestBody Long roleId) {
        Assert.notNull(roleId);
        SysRole sysRoleModel = sysRoleService.queryById(roleId);
        SysDept sysDeptModel = sysDeptService.queryById(sysRoleModel.getDeptId());
        sysRoleModel.setDeptName(sysDeptModel.getDeptName());
        return ResultUtil.ok(sysRoleModel);
    }

    /**
     * 分页查询角色列表
     *
     * @param pageModel 分页实体
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "分页查询角色列表", notes = "根据分页参数查询角色列表")
    @PostMapping("/listPage")
    @RequiresPermissions("sys:role:read")
    public ResultModel queryListPage(@RequestBody PageModel pageModel) {
        return ResultUtil.ok(sysRoleService.queryListPage(pageModel));
    }

    /**
     * 新增角色
     *
     * @param sysRoleModel 角色实体
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "新增角色", notes = "根据角色实体新增角色")
    @PostMapping("/add")
    @RequiresPermissions("sys:role:update")
    @SysLogOpt(module = "角色管理", value = "角色新增", operationType = Constants.LogOptEnum.ADD)
    public ResultModel add(@Valid @RequestBody SysRole sysRoleModel) throws Exception{
        sysRoleModel.setCreateBy(super.getCurrentUserId());
        sysRoleModel.setUpdateBy(super.getCurrentUserId());
        return ResultUtil.ok(sysRoleService.add(sysRoleModel));
    }

    /**
     * 修改角色
     *
     * @param sysRoleModel 角色实体
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "修改角色", notes = "根据角色ID修改角色")
    @PostMapping("/modify")
    @RequiresPermissions("sys:role:update")
    @SysLogOpt(module = "角色管理", value = "角色修改", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel modify(@Valid @RequestBody SysRole sysRoleModel) throws Exception{
        sysRoleModel.setUpdateBy(super.getCurrentUserId());
        sysRoleService.modifyById(sysRoleModel);
        return ResultUtil.ok();
    }

    /**
     * 根据角色ID集合批量删除
     *
     * @param ids 角色ID集合
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "批量删除角色", notes = "根据主键ID集合批量删除角色")
    @PostMapping("/delBatchByIds")
    @RequiresPermissions("sys:role:delete")
    @SysLogOpt(module = "角色管理", value = "角色批量删除", operationType = Constants.LogOptEnum.DELETE)
    public ResultModel delBatchByIds(@RequestBody List<Long> ids) throws Exception{
        if (ids.size() == 0) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "角色ID集合不能为空");
        }
        return ResultUtil.ok(sysRoleService.deleteBatchIds(ids));
    }

    /**
     * 根据部门ID查询所属角色
     *
     * @param deptId 部门ID
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "查询角色", notes = "根据部门ID查询所属角色列表")
    @GetMapping("/queryRoles/{deptId}")
    @RequiresPermissions("sys:role:read")
    public ResultModel queryRoles(@PathVariable(value = "deptId") Long deptId) {
        Assert.notNull(deptId);
        List<SysRole> list = sysRoleService.queryRoles(deptId);
        return ResultUtil.ok(list);
    }
}

