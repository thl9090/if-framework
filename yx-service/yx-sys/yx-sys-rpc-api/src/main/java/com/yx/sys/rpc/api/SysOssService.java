package com.yx.sys.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseService;
import com.yx.sys.model.SysOss;
import com.yx.sys.model.vo.SysOssVO;

/**
 * <p>
 * 文件上传 服务类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-29
 */
public interface SysOssService extends BaseService<SysOss> {

    Page<SysOssVO> selectVOPage(Page<SysOssVO> page, SysOssVO sysOssVO);

}
