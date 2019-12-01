package com.yx.common.core.base;

import com.baomidou.mybatisplus.service.IService;
import com.yx.common.core.exception.BusinessException;

/**
 * 业务处理基类接口
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public interface BaseService<T extends BaseModel> extends IService<T> {

    /**
     * 新增
     *
     * @param entity 实体
     * @return T
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    T add(T entity) throws BusinessException;

    /**
     * 更新
     *
     * @param entity 实体
     * @return T
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    T modifyById(T entity) throws BusinessException;

    /**
     * 根据ID查询
     *
     * @param id 实体主键
     * @return T
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    T queryById(Long id);

}
