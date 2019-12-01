package com.yx.sys.rpc.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.yx.common.core.exception.BusinessException;
import com.yx.sys.common.AppTypeEnum;
import com.yx.sys.common.AppVersionStatusEnum;
import com.yx.sys.dao.mapper.AppPackageDescMapper;
import com.yx.sys.dao.mapper.AppPackageVersionMapper;
import com.yx.sys.model.appVersion.AddAppVersionModel;
import com.yx.sys.model.appVersion.AppPackageDesc;
import com.yx.sys.model.appVersion.AppPackageVersion;
import com.yx.sys.model.appVersion.NewestAppVersionModel;
import com.yx.sys.rpc.api.AppPackageVersionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author YanBingHao
 * @since 2018/12/5
 */
@Slf4j
@Service(value = "appPackageVersionService")
public class AppPackageVersionServiceImpl implements AppPackageVersionService {

    private static final String VERSION_SPLIT = "[.]";
    private static final String VERSION_TEMPLATE_MATCHS = "[{0-9}][.][{0-99}][.][{0-99}]";

    @Autowired
    private AppPackageVersionMapper appPackageVersionMapper;
    @Autowired
    private AppPackageDescMapper appPackageDescMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addVersion(AddAppVersionModel model) throws Exception {
        if (model == null) {
            throw new BusinessException("请求参数不能为空");
        }

        Integer appType = model.getAppType();
        String packageVersion = model.getPackageVersion();
        String packageUrl = model.getPackageUrl();
        Integer upgradeMode = model.getUpgradeMode();
        List<String> descList = model.getDescList();

        if (appType == null) {
            throw new BusinessException("app类型不能为空");
        }
        if (StringUtils.isBlank(packageVersion)) {
            throw new BusinessException("版本号不能为空");
        }
        if (StringUtils.isBlank(packageUrl)) {
            if (AppTypeEnum.ANDROID.getId().equals(appType)) {
                throw new BusinessException("包地址不能为空");
            }
        }
        if (upgradeMode == null) {
            throw new BusinessException("升级方式不能为空");
        }
        if (CollectionUtils.isEmpty(descList)) {
            throw new BusinessException("版本描述不能为空");
        }

        int outLineCount = appPackageVersionMapper.getCountByAppTypeAndOutLine(appType);
        if (outLineCount > 0) {
            throw new BusinessException("当前已有未上线的版本，无法再次新增版本");
        }

        boolean matches = packageVersion.matches(VERSION_TEMPLATE_MATCHS);
        if (!matches) {
            throw new BusinessException("版本号格式不正确");
        }

        String[] versions = packageVersion.split(VERSION_SPLIT);

        // 查询最新的上线的版本号
        String maxVersion = appPackageVersionMapper.getMaxVersionByAppTypeAndOnLine(appType);
        if (StringUtils.isNotBlank(maxVersion)) {
            String[] maxVersions = maxVersion.split(VERSION_SPLIT);
            for (int i = 0; i < versions.length; i++) {
                if (Integer.valueOf(maxVersions[i]) > Integer.valueOf(versions[i])) {
                    throw new BusinessException("版本号必须大于已上线的最大版本号：" + maxVersion);
                }
                if (Integer.valueOf(maxVersions[i]) < Integer.valueOf(versions[i])) {
                    break;
                }
            }
        }

        AppPackageVersion appPackageVersion = new AppPackageVersion();
        appPackageVersion.setAppType(model.getAppType());
        appPackageVersion.setPackageVersion(model.getPackageVersion());
        appPackageVersion.setPackageUrl(model.getPackageUrl());
        appPackageVersion.setUpgradeMode(model.getUpgradeMode());
        appPackageVersion.setVersionStatus(AppVersionStatusEnum.UN_PUBLISH.getId());
        appPackageVersionMapper.insertSelective(appPackageVersion);

        List<AppPackageDesc> packageDescs = Lists.newArrayList();
        AppPackageDesc appPackageDesc = null;
        int i = 1;
        for (String desc : descList) {
            appPackageDesc = new AppPackageDesc();
            appPackageDesc.setPackageVersionId(appPackageVersion.getId());
            appPackageDesc.setPackageDesc(desc);
            appPackageDesc.setDescIndex(i);
            packageDescs.add(appPackageDesc);
            i++;
        }
        appPackageDescMapper.batchInsert(packageDescs);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyVersion(AddAppVersionModel model) throws Exception {
        if (model == null) {
            throw new BusinessException("请求参数不能为空");
        }

        Long versionId = model.getVersionId();
        Integer appType = model.getAppType();
        String packageVersion = model.getPackageVersion();
        String packageUrl = model.getPackageUrl();
        Integer upgradeMode = model.getUpgradeMode();
        List<String> descList = model.getDescList();

        if (versionId == null) {
            throw new BusinessException("版本id不能为空");
        }
        if (appType == null) {
            throw new BusinessException("app类型不能为空");
        }
        if (StringUtils.isBlank(packageVersion)) {
            throw new BusinessException("版本不能为空");
        }
        if (StringUtils.isBlank(packageUrl)) {
            if (AppTypeEnum.ANDROID.getId().equals(appType)) {
                throw new BusinessException("包地址不能为空");
            }
        }
        if (upgradeMode == null) {
            throw new BusinessException("升级方式不能为空");
        }
        if (CollectionUtils.isEmpty(descList)) {
            throw new BusinessException("版本描述不能为空");
        }

        AppPackageVersion appPackageVersion = appPackageVersionMapper.getById(versionId);
        if (appPackageVersion == null) {
            throw new BusinessException("版本信息不存在");
        }

        if (!AppVersionStatusEnum.UN_PUBLISH.getId().equals(appPackageVersion.getVersionStatus())) {
            throw new BusinessException("版本状态不正确");
        }

        boolean matches = packageVersion.matches(VERSION_TEMPLATE_MATCHS);
        if (!matches) {
            throw new BusinessException("版本号格式不正确");
        }

        String secondMaxVersion = appPackageVersionMapper.getSecondMaxVersionByAppType(appType, versionId);
        String[] versions = packageVersion.split(VERSION_SPLIT);
        if (StringUtils.isNotBlank(secondMaxVersion)) {
            String[] maxVersions = secondMaxVersion.split(VERSION_SPLIT);
            for (int i = 0; i < versions.length; i++) {
                if (Integer.valueOf(maxVersions[i]) > Integer.valueOf(versions[i])) {
                    throw new BusinessException("版本号必须大于已上线的最大版本号：" + secondMaxVersion);
                }
                if (Integer.valueOf(maxVersions[i]) < Integer.valueOf(versions[i])) {
                    break;
                }
            }
        }
        appPackageVersion = new AppPackageVersion();
        appPackageVersion.setId(versionId);
        appPackageVersion.setPackageVersion(packageVersion);
        appPackageVersion.setPackageUrl(packageUrl);
        appPackageVersion.setUpgradeMode(upgradeMode);
        appPackageVersionMapper.updateById(appPackageVersion);

        appPackageDescMapper.deleteByVersionId(versionId);
        List<AppPackageDesc> packageDescs = Lists.newArrayList();
        AppPackageDesc appPackageDesc = null;
        int i = 1;
        for (String desc : descList) {
            appPackageDesc = new AppPackageDesc();
            appPackageDesc.setPackageVersionId(appPackageVersion.getId());
            appPackageDesc.setPackageDesc(desc);
            appPackageDesc.setDescIndex(i);
            packageDescs.add(appPackageDesc);
            i++;
        }
        appPackageDescMapper.batchInsert(packageDescs);
    }

    @Override
    public void updateStatusById(Long versionId, Integer versionStatus) throws Exception {
        AppPackageVersion version = appPackageVersionMapper.getById(versionId);
        if (version == null) {
            throw new BusinessException("版本信息不存在");
        }
        appPackageVersionMapper.updateStatusById(versionId, versionStatus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long versionId) throws Exception {
        AppPackageVersion version = appPackageVersionMapper.getById(versionId);
        if (version == null) {
            throw new BusinessException("版本信息不存在");
        }

        if (!AppVersionStatusEnum.UN_PUBLISH.getId().equals(version.getVersionStatus())) {
            throw new BusinessException("只有下线版本才能删除");
        }

        appPackageVersionMapper.deleteById(versionId);
        appPackageDescMapper.deleteByVersionId(versionId);
    }

    @Override
    public NewestAppVersionModel getNewestVersion(NewestAppVersionModel model) throws Exception {
        if (model == null) {
            throw new BusinessException("请求参数不能为空");
        }

        Integer appType = model.getAppType();
        String currentVersion = model.getCurrentVersion();

        boolean matches = currentVersion.matches(VERSION_TEMPLATE_MATCHS);
        if (!matches) {
            throw new BusinessException("版本格式不正确");
        }
        String[] versions = currentVersion.split(VERSION_SPLIT);
        AppPackageVersion maxVersionInfo = appPackageVersionMapper.getMaxVersionInfoByAppType(appType);
        if (maxVersionInfo == null) {
            return null;
        }
        String maxVersion = maxVersionInfo.getPackageVersion();
        String[] maxVersions = maxVersion.split(VERSION_SPLIT);

        boolean isHasNew = false;
        for (int i = 0; i < versions.length; i++) {
            if (Integer.valueOf(maxVersions[i]) < Integer.valueOf(versions[i])) {
                break;
            }
            if (Integer.valueOf(maxVersions[i]) > Integer.valueOf(versions[i])) {
                isHasNew = true;
                break;
            }
        }

        if (!isHasNew) {
            return null;
        }

        model.setUpgradeVersion(maxVersionInfo.getPackageVersion());
        model.setUpgradePackageUrl(maxVersionInfo.getPackageUrl());
        model.setUpgradeMode(maxVersionInfo.getUpgradeMode());
        List<String> descs = appPackageDescMapper.getDescsByVersionIdForApp(maxVersionInfo.getId());
        model.setUpgradeDescList(descs);

        StringBuilder descAppender = new StringBuilder();
        if (CollectionUtils.isNotEmpty(descs)) {
            for (String desc : descs) {
                descAppender.append(desc).append("\n");
            }
            model.setUpgradeDesc(descAppender.toString());
        }

        return model;
    }

    @Override
    public Page queryByPage(Page page) throws Exception {
        return page.setRecords(appPackageVersionMapper.queryByPage(page, page.getCondition()));
    }

    @Override
    public AppPackageVersion selectNewestByAppType(Integer appType) {
        return appPackageVersionMapper.selectNewestByAppType(appType);
    }

}
