package com.yx.sys.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseService;
import com.yx.common.core.model.PageModel;
import com.yx.sys.model.SysParam;
import com.yx.sys.model.company.Company;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 全局参数表 服务类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
public interface SysParamService extends BaseService<SysParam> {

    /**
     * 分页查询参数配置明细
     *
     * @param page
     * @return Page<SysParamModel>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    Page<SysParam> queryListPage(Page<SysParam> page);

    SysParam queryByKey(String key);

    /**
     * 查询公司信息
     *
     * @param pageModel
     * @return
     */
    Company selectCompany(PageModel<Company> pageModel);

    void updateCompany(Company entity);

    /**
     * 根据key获取string值
     *
     * @param key
     * @return
     */
    String queryStringByKey(String key);

    /**
     * 根据key获取bigdecimal值
     *
     * @param key
     * @return
     */
    BigDecimal queryBigDecimalByKey(String key);

    /**
     * 根据key获取long值
     *
     * @param key
     * @return
     */
    Long queryLongByKey(String key);

    /**
     * 根据key获取integer值
     * @param key
     * @return
     */
    Integer queryIntegerByKey(String key);

    /**
     * 根据key获取date值
     * @param key
     * @return
     */
    Date queryDateByKey(String key);

    /**
     * 根据key获取分隔符的集合
     * @param key
     * @param regex
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> querySplitListByKey(String key, String regex, Class<T> clazz);



    int updateParam(String key, String param);



}
