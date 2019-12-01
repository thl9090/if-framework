package com.yx.sys.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseMapper;
import com.yx.sys.model.appVersion.AppPackageVersion;
import com.yx.sys.model.appVersion.PageAppVersionModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * App包版本表 Mapper 接口
 * </p>
 *
 * @author YanBingHao
 * @since 2018-12-05
 */
public interface AppPackageVersionMapper extends BaseMapper<AppPackageVersion> {

    int insertSelective(AppPackageVersion appPackageVersion);

    String getMaxVersionByAppTypeAndOnLine(@Param("appType") Integer appType);

    AppPackageVersion getMaxVersionInfoByAppType(@Param("appType") Integer appType);

    String getSecondMaxVersionByAppType(@Param("appType") Integer appType, @Param("versionId") Long versionId);

    Long getMaxVersionIdByAppType(@Param("appType") Integer appType);

    AppPackageVersion getById(@Param("versionId") Long versionId);

    int updateStatusById(@Param("versionId") Long versionId, @Param("versionStatus") Integer versionStatus);

    int deleteById(@Param("versionId") Long versionId);

    int getCountByAppTypeAndOutLine(@Param("appType") Integer appType);

    List<PageAppVersionModel> queryByPage(Page page, Map condition);

    AppPackageVersion selectNewestByAppType(@Param("appType") Integer appType);

}
