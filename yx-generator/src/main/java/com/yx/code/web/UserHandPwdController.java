//package com.yx.code.web;
//
//
//import org.springframework.web.bind.annotation.*;
//
//import org.springframework.stereotype.Controller;
//import com.yx.common.web.BaseController;
//import com.yx.common.core.model.PageModel;
//import com.yx.common.core.Constants;
//import com.yx.common.web.model.ResultModel;
//import com.yx.common.web.util.ResultUtil;
//import com.yx.code.entity.UserHandPwd;
//import com.yx.code.service.IUserHandPwdService;
//import cn.hutool.core.lang.Assert;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.validation.Valid;
//import java.util.Date;
//import java.util.Arrays;
//
///**
// * <p>
// * 用户手势密码表前端控制器
// * </p>
// *
// * @author TangHuaLiang
// * @since 2018-08-31
// */
//@RestController
//@RequestMapping("/userHandPwd")
//public class UserHandPwdController extends BaseController {
//
//
//    @Autowired
//    private IUserHandPwdService service;
//
//    /**
//     * 根据用户手势密码表ID查询
//     *
//     * @param id 用户手势密码表ID
//     * @return ResultModel
//     * @author TangHuaLiang
//     * @date 2018-08-31
//     */
//    @GetMapping("/select/{id}")
//    public ResultModel selectById(@PathVariable Long id) {
//        Assert.notNull(id);
//        UserHandPwd entity = service.selectById(id);
//        return ResultUtil.ok(entity);
//    }
//
//    /**
//     * 查询用户手势密码表分页方法
//     *
//     * @param pageModel 分页实体
//     * @return com.yx.common.web.model.ResultModel
//     * @author TangHuaLiang
//     * @date 2018-08-31
//     */
//    @PostMapping("/selectPage")
//    public ResultModel selectPage(@RequestBody PageModel<UserHandPwd> pageModel) {
//        pageModel = (PageModel<UserHandPwd>) service.selectPage(pageModel);
//        return ResultUtil.ok(pageModel);
//     }
//
//    /**
//     * 新增用户手势密码表方法
//     *
//     * @param entity 用户手势密码表实体
//     * @return com.yx.common.web.model.ResultModel
//     * @author TangHuaLiang
//     * @date 2018-08-31
//     */
//    @PostMapping("/add")
//    public ResultModel add(@Valid @RequestBody UserHandPwd entity) {
//        if (entity != null) {
//            entity.setCreateBy(this.getCurrentUserId());
//            entity.setUpdateBy(this.getCurrentUserId());
//        }
//        return ResultUtil.ok(service.add(entity));
//    }
//
//    /**
//     * 修改用户手势密码表方法
//     *
//     * @param entity 用户手势密码表实体
//     * @return com.yx.common.web.model.ResultModel
//     * @author TangHuaLiang
//     * @date 2018-08-31
//     */
//    @PostMapping("/update")
//    public ResultModel update(@RequestBody UserHandPwd entity) {
//        entity.setUpdateBy(this.getCurrentUserId());
//        entity.setUpdateTime(new Date());
//        service.updateById(entity);
//        return ResultUtil.ok();
//    }
//
//    /**
//     * 批量删除用户手势密码表方法
//     *
//     * @param ids 用户手势密码表ID集合
//     * @return com.yx.common.web.model.ResultModel
//     * @author TangHuaLiang
//     * @date 2018-08-31
//     */
//    @PostMapping("/delete")
//    public ResultModel delete(@RequestBody Long[] ids) {
//        Assert.notNull(ids);
//        return ResultUtil.ok(service.deleteBatchIds(Arrays.asList(ids)));
//    }
//
//
//}
//
