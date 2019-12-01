package com.yx.admin.server.controller.sys;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.yx.admin.server.annotation.SysLogOpt;
import com.yx.common.core.Constants;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.core.model.PageModel;
import com.yx.common.core.util.SecurityUtil;
import com.yx.common.redis.util.CacheUtil;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.common.SysUserEnableEnum;
import com.yx.sys.model.SysTree;
import com.yx.sys.model.SysUser;
import com.yx.sys.model.SysUserRole;
import com.yx.sys.model.vo.SysUserVO;
import com.yx.sys.rpc.api.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * 用户管理控制器
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@Slf4j
@Api(value = "用户管理", description = "用户管理")
@RestController
@RequestMapping("/user")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据用户ID查询用户
     *
     * @param id
     * @return ResultModel<SysUserModel>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "查询用户", notes = "根据用户主键ID查询用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @GetMapping("/query/{id}")
    @RequiresAuthentication
    //@RequiresPermissions("sys:user:read")
    public ResultModel query(@PathVariable(value = "id") Long id) {
        Assert.notNull(id);
        SysUserVO sysUserVO = sysUserService.selectVOById(id);
        return ResultUtil.ok(sysUserVO);
    }

    /**
     * 分页查询用户列表
     *
     * @param pageModel 分页实体
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "分页查询用户列表", notes = "根据分页参数查询用户列表")
    @PostMapping("/listPage")
    @RequiresPermissions("sys:user:read")
    public ResultModel queryListPage(@RequestBody PageModel pageModel) {
        SysUserVO sysUserVO=new SysUserVO();
        final Map condition = pageModel.getCondition();
        //组装查询条件
        if(condition!=null){
            String jsonStr = JSON.toJSONString(condition);
            sysUserVO = JSON.parseObject(jsonStr, SysUserVO.class);
        }

        Page page = sysUserService.selectVOPage(pageModel, sysUserVO);
    	return ResultUtil.ok(page);
    }

    /**
     * 新增用户
     *
     * @param sysUserModel 用户实体
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "新增用户", notes = "根据用户实体新增用户")
    @PostMapping("/add")
    @RequiresPermissions("sys:user:update")
    @SysLogOpt(module = "用户管理", value = "用户新增", operationType = Constants.LogOptEnum.ADD)
    public ResultModel add(@Valid @RequestBody SysUser sysUserModel) throws Exception{
        SysUser existSysUserModel = sysUserService.selectByAccount(sysUserModel.getAccount());
        if (ObjectUtil.isNotNull(existSysUserModel)) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "已存在相同账号的用户");
        }
        // 设置初始密码: 123456
        sysUserModel.setPassword(SecurityUtil.encryptPassword("123456"));
        sysUserModel.setCreateBy(getCurrentUserId());
        sysUserService.add(sysUserModel);
        return ResultUtil.ok();
    }

    /**
     * 根据用户ID集合批量删除用户
     *
     * @param ids 用户ID集合
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "批量删除用户", notes = "根据主键ID集合批量删除用户")
    @PostMapping("/delBatchByIds")
    @RequiresPermissions("sys:user:delete")
    @SysLogOpt(module = "用户管理", value = "用户批量删除", operationType = Constants.LogOptEnum.DELETE)
    public ResultModel delBatchByIds(@RequestBody List<Long> ids) throws Exception{
        if (ids.size() == 0) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "用户ID集合不能为空");
        }
        return ResultUtil.ok(sysUserService.delBatchByIds(ids));
    }

    /**
     * 修改用户
     *
     * @param sysUserModel 用户实体
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "修改用户", notes = "根据用户ID修改用户")
    @PostMapping("/modify")
    @RequiresPermissions("sys:user:update")
    @SysLogOpt(module = "用户管理", value = "用户修改", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel modify(@RequestBody SysUser sysUserModel) {
        sysUserModel.setCreateBy(super.getCurrentUserId());
        sysUserModel.setUpdateTime(new Date());
        return ResultUtil.ok(sysUserService.update(sysUserModel));
    }

    /**
     * 个人资料修改
     *
     * @param sysUserModel 用户实体
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "修改个人资料", notes = "根据用户ID修改用户个人资料")
    @PostMapping("/modifyMySelf")
    @SysLogOpt(module = "用户管理", value = "个人资料修改", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel modifyMySelf(@RequestBody SysUser sysUserModel) throws Exception{
        if (!sysUserModel.getId().equals(super.getCurrentUserId())) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "不能修改其他用户信息");
        }
        sysUserModel.setCreateBy(super.getCurrentUserId());
        sysUserModel.setUpdateTime(new Date());
        return ResultUtil.ok(sysUserService.modifyById(sysUserModel));
    }

    /**
     * 根据用户id查询用户角色关系
     *
     * @param userId 用户ID
     * @return com.yx.common.web.model.ResultModel
     * @author TangHuaLiang
     * @date 17/12/25 21:26:57
     */
    @ApiOperation(value = "查询用户角色关系", notes = "根据用户id查询用户角色关系")
    @GetMapping("/queryUserRoles/{userId}")
    public ResultModel queryUserRoles(@PathVariable(value = "userId") Long userId) {
        Assert.notNull(userId);
        List<SysUserRole> list = sysUserService.selectUserRoles(userId);
        return ResultUtil.ok(list);
    }

    /**
     * 修改密码
     *
     * @param sysUserModel 用户实体
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @PostMapping("/modifyPassword")
    @SysLogOpt(module = "用户管理", value = "修改密码", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel modifyPassword(@RequestBody SysUser sysUserModel) throws Exception{
        Assert.notEmpty(sysUserModel.getOldPassword());
        Assert.notEmpty(sysUserModel.getPassword());
        String encryptOldPassword = SecurityUtil.encryptPassword(sysUserModel.getOldPassword());
        SysUser currentSysUserModel = sysUserService.queryById(super.getCurrentUserId());
        if (!encryptOldPassword.equals(currentSysUserModel.getPassword())) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "旧密码不正确");
        }
        String encryptPassword = SecurityUtil.encryptPassword(sysUserModel.getPassword());
        sysUserModel.setPassword(encryptPassword);
        sysUserModel.setId(super.getCurrentUserId());
        return ResultUtil.ok(sysUserService.modifyById(sysUserModel));
    }

    @GetMapping("/queryTree")
    public ResultModel queryTree() {
        List<SysTree> list = sysUserService.queryTree();
        return ResultUtil.ok(list);
    }


    /**
     * 用户启用
     * @param ids
     * @return
     */
    @PostMapping("/enable")
    @RequiresPermissions("sys:user:enableAndDisable")
    public ResultModel enable(@RequestBody Long[] ids) {
        Assert.notNull(ids);
        List<SysUser> updateList=new ArrayList<>();
        for(Long id:ids){
            if(id!=null){
                SysUser update=new SysUser();
                update.setId(id);
                update.setEnable(SysUserEnableEnum.YES.getStatus());
                update.setUpdateTime(new Date());
                updateList.add(update);
            }
        }
        if(updateList.size()>0){
            sysUserService.updateBatchById(updateList);

        }

        return ResultUtil.ok(true);
    }

    /**
     * 用户禁用
     * @param ids
     * @return
     */
    @PostMapping("/disable")
    @RequiresPermissions("sys:user:enableAndDisable")
    public ResultModel disable(@RequestBody Long[] ids) {
        Assert.notNull(ids);
        List<SysUser> updateList=new ArrayList<>();
        for(Long id:ids){
            if(id!=null){
                SysUser update=new SysUser();
                update.setId(id);
                update.setEnable(SysUserEnableEnum.NO.getStatus());
                update.setUpdateTime(new Date());
                updateList.add(update);
            }
        }
        if(updateList.size()>0){
            sysUserService.updateBatchById(updateList);
        }

        return ResultUtil.ok(true);
    }


    @ApiOperation(value = "获取实时用户在线数", notes = "获取实时用户在线数")
    @PostMapping("/queryOnLineUserCount")
    @RequiresPermissions("sys:user:read")
    public ResultModel queryOnLineUserCount() {
        Set<Object> all = CacheUtil.getCache().getAll(Constants.CacheNamespaceEnum.USER_ID_SESSION.value()  + "*");
        return ResultUtil.ok(CollectionUtils.isEmpty(all) ? 0 : all.size());
    }


    @ApiOperation(value = "获取在线用户列表", notes = "获取在线用户列表")
    @PostMapping("/queryOnLineUserPage")
    @RequiresPermissions("sys:user:read")
    public ResultModel queryOnLineUserPage(@RequestBody PageModel pageModel) {
        Set<Object> all = CacheUtil.getCache().getAll(Constants.CacheNamespaceEnum.USER_ID_SESSION.value()  + "*");
        List<Long> userIdList=new ArrayList<>();
        List<SysUserVO> userList=new ArrayList<>();
        for(Object o:all){
            String sessionId=(String )o;
            if(StringUtils.isNotBlank(sessionId)){
                Object objUser = CacheUtil.getCache().get(Constants.CacheNamespaceEnum.SESSION_USER.value() + sessionId);
                if(objUser!=null){
                    SysUserVO sysUserVO=(SysUserVO) objUser;
                    if(sysUserVO!=null){
                        userIdList.add(sysUserVO.getId());
                        userList.add(sysUserVO);
                    }
                }
            }
        }
        if(userIdList.size()<1){
            return ResultUtil.ok(new Page());
        }
        SysUserVO sysUserVO=new SysUserVO();
        final Map condition = pageModel.getCondition();
        //组装查询条件
        if(condition!=null){
            String jsonStr = JSON.toJSONString(condition);
            sysUserVO = JSON.parseObject(jsonStr, SysUserVO.class);
        }
        sysUserVO.setUserIdList(userIdList);
        Page<SysUserVO> page = sysUserService.selectVOPage(pageModel, sysUserVO);

        //处理数据
        for(SysUserVO vo:page.getRecords()){
            for(SysUserVO userVO:userList){
                if(vo.getId().longValue()==userVO.getId().longValue()){
                    vo.setUpdateTime(userVO.getUpdateTime());
                    vo.setIp(userVO.getIp());
                    Object o = CacheUtil.getCache().get(Constants.CacheNamespaceEnum.USER_ID_SESSION.value() + vo.getId());
                    if(o!=null){
                        String sessionId=(String)o;
                        vo.setSessionId(sessionId);
                    }
                }
            }
        }
        return ResultUtil.ok(page);
    }


    /**
     * 用户强制下线
     * @param sessionIds
     * @return
     */
    @PostMapping("/kickOut")
    @RequiresPermissions("sys:user:update")
    public ResultModel kickOut(@RequestBody String[] sessionIds) throws Exception {
        Assert.notNull(sessionIds);
        for(String sessionId:sessionIds){
            Object o = CacheUtil.getCache().get(Constants.CacheNamespaceEnum.SESSION_USER_ID.value() + sessionId);
            if(o!=null){
                String userId=String.valueOf(o);
                if(userId!=null){
                    CacheUtil.getCache().del(Constants.CacheNamespaceEnum.USER_ID_SESSION.value() + userId);
                }
            }

            CacheUtil.getCache().del(Constants.CacheNamespaceEnum.SESSION_USER_ID.value() + sessionId);
            CacheUtil.getCache().del(Constants.CacheNamespaceEnum.SESSION_USER.value() + sessionId);
        }

        return ResultUtil.ok(true);
    }


}
