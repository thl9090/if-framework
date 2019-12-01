package com.yx.admin.server.controller.appVersion;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.Constants;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.oss.configuration.OSSFactory;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.common.AppVersionStatusEnum;
import com.yx.sys.model.SysOss;
import com.yx.sys.model.appVersion.AddAppVersionModel;
import com.yx.sys.rpc.api.AppPackageVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * App版本管理
 *
 * @author YanBingHao
 * @since 2018/12/6
 */
@Slf4j
@RestController
@RequestMapping("/appVersion")
@Api(value = "App版本管理", description = "App版本管理")
public class AppVersionController extends BaseController {

    @Autowired
    private AppPackageVersionService appPackageVersionService;

    @ApiOperation(value = "上传apk包")
    @PostMapping("/uploadApk")
    public ResultModel upload(@RequestParam("file") MultipartFile file
            , @RequestParam("apkName") String apkName) throws Exception {
        if (file.isEmpty()) {
            return ResultUtil.fail("APK包文件不能为空");
        }
        if (StringUtils.isBlank(apkName)) {
            throw new BusinessException("APK版本号不能为空");
        }
        return ResultUtil.ok(OSSFactory.build().uploadByFileName(file.getBytes(), apkName));
    }

    @ApiOperation(value = "分页查询App版本")
    @PostMapping("/queryByPage")
    public ResultModel queryByPage(@RequestBody Page page) throws Exception {
        return ResultUtil.ok(appPackageVersionService.queryByPage(page));
    }

    @ApiOperation(value = "新增App版本")
    @PostMapping("/addVersion")
    public ResultModel addVersion(@RequestBody AddAppVersionModel model) throws Exception {
        appPackageVersionService.addVersion(model);
        return ResultUtil.ok();
    }

    @ApiOperation(value = "修改App版本")
    @PostMapping("/modifyVersion")
    public ResultModel modifyVersion(@RequestBody AddAppVersionModel model) throws Exception {
        appPackageVersionService.modifyVersion(model);
        return ResultUtil.ok();
    }

    @ApiOperation(value = "删除版本")
    @PostMapping("/delVersion/{versionId}")
    public ResultModel delVersion(@PathVariable Long versionId) throws Exception {
        appPackageVersionService.deleteById(versionId);
        return ResultUtil.ok();
    }

    @ApiOperation(value = "发布版本")
    @PostMapping("/publish/{versionId}")
    public ResultModel publish(@PathVariable Long versionId) throws Exception {
        appPackageVersionService.updateStatusById(versionId, AppVersionStatusEnum.PUBLISH.getId());
        return ResultUtil.ok();
    }

    @ApiOperation(value = "下架版本")
    @PostMapping("/obtained/{versionId}")
    public ResultModel obtained(@PathVariable Long versionId) throws Exception {
        appPackageVersionService.updateStatusById(versionId, AppVersionStatusEnum.UN_PUBLISH.getId());
        return ResultUtil.ok();
    }

}
