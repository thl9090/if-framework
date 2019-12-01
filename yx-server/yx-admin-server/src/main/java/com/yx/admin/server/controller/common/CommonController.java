package com.yx.admin.server.controller.common;


import com.yx.admin.server.annotation.SysLogOpt;
import com.yx.common.core.Constants;
import com.yx.common.redis.util.CacheUtil;
import com.yx.common.utils.StringUtil;
import com.yx.common.utils.UUIDUtil;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.model.SysUser;
import com.yx.sys.rpc.api.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 公共的功能
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-13
 */
@Api(value = "公共功能", description = "公共功能")
@RestController
@RequestMapping("/common")
public class CommonController extends BaseController {
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "给当前用户发送短信",notes = "")
    @PostMapping("/sendSms")
    @SysLogOpt(module = "发送短信", value = "发送短信", operationType = Constants.LogOptEnum.UNKNOW)
    public ResultModel sendSms() throws Exception {
        Long currentUserId = getCurrentUserId();
        if(currentUserId==null){
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到当前登陆用户");
        }
        SysUser sysUser = sysUserService.selectById(currentUserId);
        if(sysUser==null){
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到当前登陆用户");
        }
        if(StringUtils.isBlank(sysUser.getPhone())){
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "请先完善您的手机号码");
        }

        String smscode = StringUtil.getRandom6Number();//生成验证码
        //调用发送短信的方法
//        sysSmsService.sendCodeMessage(Long.valueOf(sysUser.getPhone()), Long.valueOf(smscode));

        //将短信验证码放入缓存
        String captchaId = UUIDUtil.getUUID();

        CacheUtil.getCache().set(Constants.CacheNamespaceEnum.SMS_CAPTCHA.value() + captchaId, smscode, 5 * 60);

        Map<String, String> map = new HashMap<String, String>(2);
        map.put("captchaId", captchaId);

        return ResultUtil.ok(map);
    }



}

