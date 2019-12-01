package com.yx.common.core.base;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yx.common.core.Constants;
import com.yx.common.core.exception.BusinessException;

import java.util.Date;

/**
 * 业务处理基类实现
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseModel>
        extends ServiceImpl<BaseMapper<T>, T> implements BaseService<T> {

    @Override
    public T modifyById(T entity) throws BusinessException {
        T resultEntity = null;
        entity.setUpdateTime(new Date());
        if (super.updateById(entity)) {
            resultEntity = entity;
        }
        return resultEntity;
    }

    @Override
    public T queryById(Long id) {
        return super.selectById(id);
    }

    @Override
    public T add(T entity) throws BusinessException {
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        try {
            if (super.insert(entity)) {
                return entity;
            }
        } catch (Exception exception) {
            String duplicateKey = "DuplicateKeyException";
            if (exception.toString().contains(duplicateKey)) {
                throw new BusinessException(Constants.ResultCodeEnum.DATA_DUPLICATE_KEY.value(), Constants.ResultCodeEnum.DATA_DUPLICATE_KEY.getMessage());
            }
            throw exception;
        }
        return null;
    }

}
