package com.yx.sys.dao.mapper;

import com.yx.common.core.base.BaseMapper;
import com.yx.sys.model.appVersion.AppPackageDesc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * App包版本描述表 Mapper 接口
 * </p>
 *
 * @author YanBingHao
 * @since 2018-12-05
 */
public interface AppPackageDescMapper extends BaseMapper<AppPackageDesc> {

    int insertSelective(AppPackageDesc appPackageDesc);

    int batchInsert(@Param("list") List<AppPackageDesc> appPackageDescs);

    int deleteByVersionId(@Param("versionId") Long versionId);

    List<String> getDescsByVersionIdForApp(@Param("versionId") Long versionId);

    List<String> getDescsByVersionId(@Param("versionId") Long versionId);

}
