package com.yx.sys.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseMapper;
import com.yx.sys.model.SysOss;
import com.yx.sys.model.vo.SysOssVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文件上传 Mapper 接口
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-29
 */
public interface SysOssMapper extends BaseMapper<SysOss> {

    List<SysOssVO> selectVOPage(Page<SysOssVO> page, @Param("sysOssVO") SysOssVO sysOssVO);

}
