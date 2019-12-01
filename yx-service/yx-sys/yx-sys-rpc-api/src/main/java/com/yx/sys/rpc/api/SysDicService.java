package com.yx.sys.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseService;
import com.yx.sys.model.SysDic;

/**
 * <p>
 * 字典管理 服务类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
public interface SysDicService extends BaseService<SysDic> {

    /**
     * 分页查找所有字典明细
     *
     * @param page
     * @return Page<SysDicModel>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    Page<SysDic> queryListPage(Page<SysDic> page);

    /**
     * 根据类型名称获取类型集合
     * @param type
     * @param code
     * @return
     */
    SysDic selectByTypeAndCode(String type,Integer code);
}
