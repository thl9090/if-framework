package com.yx.admin.server.controller.user;


import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.yx.business.rpc.api.UserBusinessService;
import com.yx.common.core.Constants;
import com.yx.common.core.constant.AuthorizationConstant;
import com.yx.common.core.model.PageModel;
import com.yx.common.redis.util.CacheUtil;
import com.yx.common.utils.DateUtils;
import com.yx.common.utils.EncodeUtil;
import com.yx.common.utils.UUIDUtil;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.model.SysTree;
import com.yx.sys.rpc.api.SysUserService;
import com.yx.user.common.enums.UserIdTypeEnum;
import com.yx.user.common.enums.UserSourceEnum;
import com.yx.user.common.enums.UserStatusEnum;
import com.yx.user.common.enums.UserTypeEnum;
import com.yx.user.model.UserInfo;
import com.yx.user.model.vo.UserInfoVO;
import com.yx.user.rpc.api.UserInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * <p>
 * 用户信息表前端控制器
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-19
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController {
    @Autowired
    private UserInfoService service;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserBusinessService userBusinessService;
//    @Autowired
//    private FreezeRecordService freezeRecordService;


    /**
    * 根据用户信息表ID查询
    *
    * @param id 用户信息表ID
    * @return ResultModel
    * @author TangHuaLiang
    * @date 2018-07-19
    */
    @GetMapping("/select/{id}")
    @RequiresPermissions("sys:userInfo:read")
    public ResultModel selectById(@PathVariable Long id) {
            Assert.notNull(id);
            UserInfo entity = service.selectById(id);
            return ResultUtil.ok(entity);
    }

    /**
    * 查询用户信息表分页方法
    *
    * @param pageModel 分页实体
    * @return com.yx.common.web.model.ResultModel
    * @author TangHuaLiang
    * @date 2018-07-19
    */
    @PostMapping("/selectPage")
    @RequiresPermissions("sys:userInfo:read")
    public ResultModel queryListPage(@RequestBody PageModel pageModel) {

        UserInfoVO userInfoVO=new UserInfoVO();
        final Map condition = pageModel.getCondition();
        if(condition!=null) {
            String jsonStr = JSON.toJSONString(condition);
            userInfoVO = JSON.parseObject(jsonStr, UserInfoVO.class);
        }
        if(userInfoVO.getCreateTimeStart()!=null){
            String dateStart = DateUtils.formatDate(userInfoVO.getCreateTimeStart());
            dateStart=dateStart+" 00:00:00";
            userInfoVO.setCreateTimeStart(DateUtils.parseDate(dateStart));
        }
        if(userInfoVO.getCreateTimeEnd()!=null){
            String dateEnd = DateUtils.formatDate(userInfoVO.getCreateTimeEnd());
            dateEnd=dateEnd+" 23:59:59";
            userInfoVO.setCreateTimeEnd(DateUtils.parseDate(dateEnd));
        }

        Page page = service.selectVOPage(pageModel, userInfoVO);
        return ResultUtil.ok(page);
    }

    /**
     * 新增用户信息表方法
     *
     * @param entity 用户信息表实体
     * @return com.yx.common.web.model.ResultModel
     * @author TangHuaLiang
     * @date 2018-07-19
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:userInfo:add")
    public ResultModel add(@Valid @RequestBody UserInfo entity) throws Exception {
        if(entity.getPhone()==null){
           return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "手机号码为null");
        }
        if(entity.getUserType()== UserTypeEnum.COMPANY.getStatus()){
            if(StringUtils.isBlank(entity.getCompanyName())){
                return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "企业用户,请填写公司名称");
            }else{
                entity.setCompanyName("");
            }
        }
        if (entity.getRefPhone() != null) {
            if (entity.getRefPhone().toString().length() != 11) {
                return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR.value(), "推荐人手机号码格式有误！");
            }
            UserInfo refUser = service.selectByPhone(entity.getRefPhone());
            if (refUser == null) {
                return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR.value(), "推荐人不存在！");
            }
        }
        UserInfo userInfo = service.selectByPhone(entity.getPhone());
        if(userInfo!=null){
           return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "手机号码已被注册");
        }
        entity.setUserName("YX" + entity.getPhone());
        //生成密钥和加密后的密码
        String key = UUIDUtil.getUUID().substring(0, 10);
        String encodePassword = EncodeUtil.getEncodedPassword("123456", key);
        entity.setExkey(key);
        entity.setPassword(encodePassword);
        entity.setSource(UserSourceEnum.PC.getStatus());
        entity.setStatus(UserStatusEnum.YES_STATUS.getStatus());
        if(entity.getUserType()== UserTypeEnum.COMPANY.getStatus()){
            entity.setIdType(UserIdTypeEnum.ORG_CODE.getStatus());
        }else{
            entity.setIdType(UserIdTypeEnum.ID_CARD.getStatus());
        }

        if (entity != null) {
            entity.setCreateBy(this.getCurrentUserId());
            entity.setUpdateBy(this.getCurrentUserId());
        }
        return ResultUtil.ok(service.add(entity));
    }

    /**
     * 修改用户信息表方法
     *
     * @param entity 用户信息表实体
     * @return com.yx.common.web.model.ResultModel
     * @author TangHuaLiang
     * @date 2018-07-19
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:userInfo:update")
    public ResultModel update(@RequestBody UserInfo entity) {
        entity.setUpdateBy(this.getCurrentUserId());
        entity.setUpdateTime(new Date());
        service.updateById(entity);
        return ResultUtil.ok();
    }

    /**
     * 批量删除用户信息表方法
     *
     * @param ids 用户信息表ID集合
     * @return com.yx.common.web.model.ResultModel
     * @author TangHuaLiang
     * @date 2018-07-19
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public ResultModel delete(@RequestBody Long[] ids) {
        Assert.notNull(ids);
        return ResultUtil.ok(service.deleteBatchIds(Arrays.asList(ids)));
    }

    /**
     * 将用户设置为借款人
     * @param ids
     * @return
     */
    /*@PostMapping("/setBorrower")
    public ResultModel setBorrower(@RequestBody Long[] ids) {
        Assert.notNull(ids);
        List<UserInfo> updateList=new ArrayList<>();
        boolean b=false;
        for(Long id:Arrays.asList(ids)){
            if(id!=null){
                //必须开通存管账户后才能被添加
                UserInfo userInfo = service.selectById(id);
                if(userInfo==null||userInfo.getAccountStatus()!=AccountStatusEnum.OPEN.getStatus()){
                    b=true;
                    continue;
                }

                UserInfo update=new UserInfo();
                update.setId(id);
                update.setIsBorrower(IsBorrowerEnum.YES.getStatus());
                update.setUpdateTime(new Date());
                update.setUpdateBy(getCurrentUserId());
                updateList.add(update);
            }
        }
        if(b){
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "用户未开通存管账户！");
        }
        if(updateList.size()>0){
            service.updateBatchById(updateList);
        }
        return ResultUtil.ok(true);
    }*/


    /**
     * 取消借款人
     * @param ids
     * @return
     */
   /* @PostMapping("/cancelBorrower")
    public ResultModel cancelBorrower(@RequestBody Long[] ids) {
        Assert.notNull(ids);
        List<UserInfo> updateList=new ArrayList<>();
        for(Long id:Arrays.asList(ids)){
            if(id!=null){
                UserInfo update=new UserInfo();
                update.setId(id);
                update.setIsBorrower(IsBorrowerEnum.NO.getStatus());
                update.setUpdateTime(new Date());
                update.setUpdateBy(getCurrentUserId());
                updateList.add(update);
            }
        }
        if(updateList.size()>0){
            service.updateBatchById(updateList);
        }
        return ResultUtil.ok(true);
    }
*/

    /**
     * 设为受托人
     * @param ids
     * @return
     */
    /*@PostMapping("/setDepositary")
    public ResultModel setDepositary(@RequestBody Long[] ids) {
        Assert.notNull(ids);
        boolean b=false;
        List<UserInfo> updateList=new ArrayList<>();
        for(Long id:Arrays.asList(ids)){
            if(id!=null){
                //必须开通存管账户后才能被添加
                UserInfo userInfo = service.selectById(id);
                if(userInfo==null||userInfo.getAccountStatus()!=AccountStatusEnum.OPEN.getStatus()){
                    b=true;
                    continue;
                }

                UserInfo update=new UserInfo();
                update.setId(id);
                update.setIsDepositary(IsBorrowerEnum.YES.getStatus());
                update.setUpdateTime(new Date());
                update.setUpdateBy(getCurrentUserId());
                updateList.add(update);
            }
        }
        if(b){
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "用户未开通存管账户！");
        }
        if(updateList.size()>0){
            service.updateBatchById(updateList);
        }
        return ResultUtil.ok(true);
    }*/

    /**
     * 取消受托人
     * @param ids
     * @return
     */
   /* @PostMapping("/cancelDepositary")
    public ResultModel cancelDepositary(@RequestBody Long[] ids) {
        Assert.notNull(ids);
        List<UserInfo> updateList=new ArrayList<>();
        for(Long id:Arrays.asList(ids)){
            if(id!=null){
                UserInfo update=new UserInfo();
                update.setId(id);
                update.setIsDepositary(IsBorrowerEnum.NO.getStatus());
                update.setUpdateTime(new Date());
                update.setUpdateBy(getCurrentUserId());
                updateList.add(update);
            }
        }
        if(updateList.size()>0){
            service.updateBatchById(updateList);
        }
        return ResultUtil.ok(true);
    }*/


    /**
     * 借款人列表
     * @param pageModel
     * @return
     */
    @PostMapping("/selectBorrowerPage")
    @RequiresPermissions("sys:userInfo:read")
    public ResultModel selectBorrowerPage(@RequestBody PageModel pageModel) {

        UserInfoVO userInfoVO=new UserInfoVO();

        final Map condition = pageModel.getCondition();
        if(condition!=null) {
            String jsonStr = JSON.toJSONString(condition);
            userInfoVO = JSON.parseObject(jsonStr, UserInfoVO.class);
        }
        if(userInfoVO.getCreateTimeStart()!=null){
            String dateStart = DateUtils.formatDate(userInfoVO.getCreateTimeStart());
            dateStart=dateStart+" 00:00:00";
            userInfoVO.setCreateTimeStart(DateUtils.parseDate(dateStart));
        }
        if(userInfoVO.getCreateTimeEnd()!=null){
            String dateEnd = DateUtils.formatDate(userInfoVO.getCreateTimeEnd());
            dateEnd=dateEnd+" 23:59:59";
            userInfoVO.setCreateTimeEnd(DateUtils.parseDate(dateEnd));
        }
        //userInfoVO.setIsBorrower(IsBorrowerEnum.YES.getStatus());
        // Page page = service.selectVOPage(pageModel, userInfoVO);
        Page page = service.selectVOIsDepositaryPage(pageModel, userInfoVO);
        return ResultUtil.ok(page);
    }


    /**
     * 受托人列表
     * @param pageModel
     * @return
     */
    @PostMapping("/selectDepositaryPage")
    @RequiresPermissions("sys:userInfo:read")
    public ResultModel selectDepositaryPage(@RequestBody PageModel pageModel) {

        UserInfoVO userInfoVO=new UserInfoVO();

        final Map condition = pageModel.getCondition();
        if(condition!=null) {
            String jsonStr = JSON.toJSONString(condition);
            userInfoVO = JSON.parseObject(jsonStr, UserInfoVO.class);
        }
        if(userInfoVO.getCreateTimeStart()!=null){
            String dateStart = DateUtils.formatDate(userInfoVO.getCreateTimeStart());
            dateStart=dateStart+" 00:00:00";
            userInfoVO.setCreateTimeStart(DateUtils.parseDate(dateStart));
        }
        if(userInfoVO.getCreateTimeEnd()!=null){
            String dateEnd = DateUtils.formatDate(userInfoVO.getCreateTimeEnd());
            dateEnd=dateEnd+" 23:59:59";
            userInfoVO.setCreateTimeEnd(DateUtils.parseDate(dateEnd));
        }
        //userInfoVO.setIsDepositary(IsBorrowerEnum.YES.getStatus());
        //Page page = service.selectVOPage(pageModel, userInfoVO);
        Page page = service.selectVOIsDepositaryPage(pageModel, userInfoVO);
        return ResultUtil.ok(page);
    }

    /**
     * 重置密码  缺少各个端重新登陆的功能
     * @param id
     * @return
     */
    @PostMapping("resetPassWord")
    @RequiresPermissions("sys:userInfo:restPassword")
    public ResultModel resetPassWord(@RequestBody Long id){
        Random random = new Random();
        String password="";
        for(int i=0;i<6;i++){
            password+=random.nextInt(10);
        }
        UserInfo userInfo = service.resetPassWord(id);
        String key = UUIDUtil.getUUID().substring(0, 10);
        String encodePassword = EncodeUtil.getEncodedPassword(password, key);
        userInfo.setExkey(key);
        userInfo.setPassword(encodePassword);
        userInfo.setUpdateTime(new Date());
        service.updateById(userInfo);
        /** 修改密码后此用户各个端重新登陆*/

        return ResultUtil.ok(password);
    }

    /**
     * 用户启用
     * @param ids
     * @return
     */
    @PostMapping("/enable")
    @RequiresPermissions("sys:userInfo:enableAndDisable")
    public ResultModel enable(@RequestBody Long[] ids) {
        Assert.notNull(ids);
        List<UserInfo> updateList=new ArrayList<>();
        for(Long id:ids){
            if(id!=null){
                UserInfo update=new UserInfo();
                update.setId(id);
                update.setStatus(UserStatusEnum.YES_STATUS.getStatus());
                update.setUpdateTime(new Date());
                updateList.add(update);
            }
        }
        if(updateList.size()>0){
            service.updateBatchById(updateList);

        }

        return ResultUtil.ok(true);
    }

    /**
     * 用户禁用
     * @param ids
     * @return
     */
    @PostMapping("/disable")
    @RequiresPermissions("sys:userInfo:enableAndDisable")
    public ResultModel disable(@RequestBody Long[] ids) {
        Assert.notNull(ids);
        List<UserInfo> updateList=new ArrayList<>();
        for(Long id:ids){
            if(id!=null){
                UserInfo update=new UserInfo();
                update.setId(id);
                update.setStatus(UserStatusEnum.NO_STATUS.getStatus());
                update.setUpdateTime(new Date());
                updateList.add(update);
            }
        }
        if(updateList.size()>0){
            service.updateBatchById(updateList);
            //禁用之后，用户将被强制下线
            //清空用户的token
            for(UserInfo userInfo:updateList){
                /*Object o = CacheUtil.getCache().get(Constants.CURRENT_USER_TOKEN + userInfo.getId());
                if(o!=null){
                    String oldJwt=(String)o;
                    if(com.yx.common.utils.StringUtils.isNotBlank(oldJwt)){
                        CacheUtil.getCache().del(oldJwt);
                    }
                }*/

                // 移除用户唯一的token
                CacheUtil.getCache().del(AuthorizationConstant.ALIVE_ACCESS_TOKEN_PREFIX + userInfo.getId());

            }
        }

        return ResultUtil.ok(true);
    }

    @GetMapping("/queryTree")
    public ResultModel queryGuaranteeUsertree() {
        List<SysTree> list = sysUserService.queryGuaranteeUsertree();
        return ResultUtil.ok(list);
    }


    @PostMapping("/updateUserReferee")
    @RequiresPermissions("sys:userInfo:updateRefphone")
    public ResultModel updateUserReferee(@RequestBody UserInfoVO userInfoVO) throws Exception {
        service.updateUserReferee(userInfoVO);
        return ResultUtil.ok();
    }

    @PostMapping("/aliveLoginCount")
    public ResultModel aliveLoginCount() {
        Set<Object> all = CacheUtil.getCache().getAll(AuthorizationConstant.ALIVE_ACCESS_TOKEN_PREFIX + "*");
        return ResultUtil.ok(CollectionUtils.isEmpty(all) ? 0 : all.size());
    }

    @PostMapping("/queryByPhones")
    public ResultModel queryByPhones(@RequestBody List<String> userPhones) {
        return ResultUtil.ok(service.queryByPhones(userPhones));
    }



//    @PostMapping("/accountFreeze")
//    @RequiresPermissions("sys:userInfo:accountFreeze")
//    public ResultModel accountFreeze(@RequestBody FreezeOrUnFreezeModel freezeOrUnFreezeModel) throws Exception {
//        if (freezeOrUnFreezeModel.getUserId() == null) {
//            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到指定的用户");
//        }
//        UserInfoVO userInfoVO = service.selectVOById(freezeOrUnFreezeModel.getUserId());
//        if(userInfoVO==null){
//            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到有效的用户信息");
//        }
//        if(userInfoVO.getAccountStatus()==null||userInfoVO.getAccountStatus()!= AccountStatusEnum.OPEN.getStatus()){
//            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "用户未开通存管账户");
//        }
//
//        freezeOrUnFreezeModel.setAssociateId(System.currentTimeMillis());
//        freezeOrUnFreezeModel.setAssociateType(FreezeRecordAssociateTypeEnum.HAND.getId());
//        freezeOrUnFreezeModel.setUserId(userInfoVO.getId());
//        freezeOrUnFreezeModel.setAcNo(userInfoVO.getAcno());
//        freezeRecordService.unFreeze(freezeOrUnFreezeModel);
//        return ResultUtil.ok();
//    }

//    @PostMapping("/accountThawing")
//    @RequiresPermissions("sys:userInfo:accountThawing")
//    public ResultModel accountThawing(@RequestBody FreezeOrUnFreezeModel freezeOrUnFreezeModel) throws Exception {
//        if (freezeOrUnFreezeModel.getUserId() == null) {
//            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到指定的用户");
//        }
//        UserInfoVO userInfoVO = service.selectVOById(freezeOrUnFreezeModel.getUserId());
//        if(userInfoVO==null){
//            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到有效的用户信息");
//        }
//        if(userInfoVO.getAccountStatus()==null||userInfoVO.getAccountStatus()!= AccountStatusEnum.OPEN.getStatus()){
//            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "用户未开通存管账户");
//        }
//
//        freezeOrUnFreezeModel.setAssociateId(System.currentTimeMillis());
//        freezeOrUnFreezeModel.setAssociateType(FreezeRecordAssociateTypeEnum.HAND.getId());
//        freezeOrUnFreezeModel.setUserId(userInfoVO.getId());
//        freezeOrUnFreezeModel.setAcNo(userInfoVO.getAcno());
//        freezeRecordService.freeze(freezeOrUnFreezeModel);
//        return ResultUtil.ok();
//    }


    /**
     * 数据统计费用统计--开户绑卡用户费用
     * @param pageModel
     * @return
     */
    @PostMapping("/selectOpenAccountUserVOPage")
    public ResultModel selectOpenAccountUserVOPage(@RequestBody PageModel pageModel) {

        UserInfoVO userInfoVO=new UserInfoVO();
        final Map condition = pageModel.getCondition();
        if(condition!=null) {
            String jsonStr = JSON.toJSONString(condition);
            userInfoVO = JSON.parseObject(jsonStr, UserInfoVO.class);
        }
        if(userInfoVO.getCreateTimeStart()!=null){
            String dateStart = DateUtils.formatDate(userInfoVO.getCreateTimeStart());
            dateStart=dateStart+" 00:00:00";
            userInfoVO.setCreateTimeStart(DateUtils.parseDate(dateStart));
        }
        if(userInfoVO.getCreateTimeEnd()!=null){
            String dateEnd = DateUtils.formatDate(userInfoVO.getCreateTimeEnd());
            dateEnd=dateEnd+" 23:59:59";
            userInfoVO.setCreateTimeEnd(DateUtils.parseDate(dateEnd));
        }

        Page page = service.selectOpenAccountUserVOPage(pageModel, userInfoVO);
        return ResultUtil.ok(page);
    }


}

