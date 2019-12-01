package com.yx.sys.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.sys.model.appVersion.AddAppVersionModel;
import com.yx.sys.model.appVersion.AppPackageVersion;
import com.yx.sys.model.appVersion.NewestAppVersionModel;
import org.apache.ibatis.annotations.Param;

/**
 * app版本接口
 *
 * @author YanBingHao
 * @since 2018/12/5
 */
public interface AppPackageVersionService {

    /**
     * 新增app版本
     *
     * @param model
     * @throws Exception
     */
    void addVersion(AddAppVersionModel model) throws Exception;

    /**
     * 修改app版本
     *
     * @param model
     * @throws Exception
     */
    void modifyVersion(AddAppVersionModel model) throws Exception;

    /**
     * 更新版本状态
     *
     * @param versionId
     * @param versionStatus
     * @throws Exception
     */
    void updateStatusById(Long versionId, Integer versionStatus) throws Exception;

    /**
     * 删除版本
     *
     * @param versionId
     */
    void deleteById(Long versionId) throws Exception;

    /**
     * app查询升级版本信息
     *
     * @param model
     * @return
     */
    NewestAppVersionModel getNewestVersion(NewestAppVersionModel model) throws Exception;

    /**
     * 分页查询版本信息
     *
     * @param page
     * @return
     * @throws Exception
     */
    Page queryByPage(Page page) throws Exception;

    /**
     * 获取最新的已发布的版本
     * @param appType
     * @return
     */
    AppPackageVersion selectNewestByAppType(@Param("appType") Integer appType);

}
