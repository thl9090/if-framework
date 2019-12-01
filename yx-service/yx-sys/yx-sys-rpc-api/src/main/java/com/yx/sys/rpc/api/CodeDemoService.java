package com.yx.sys.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseService;
import com.yx.sys.model.CodeDemo;
import com.yx.sys.model.ProductData;
import com.yx.sys.model.vo.CodeDemoVO;

import java.util.List;

/**
 * <p>
 *  demo服务类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-13
 */
public interface CodeDemoService extends BaseService<CodeDemo> {


    //说明：
    // BaseService已经帮我们集成了

    //插入数据        add(T entity);   (帮我们自动赋值了创建时间和更新时间)
    //               insert(T entity);
    //
    // 根据主键查询   selectById(Id);
    //修改数据：      updateById(Id);
    //删除数据        deleteById(Long id);
    //批量删除数据    deleteBatchIds(Arrays.asList(ids)));

    //需要我们自己的手动写的方法:

    /**
     * 根据主键查询（从主库）
     * @param id
     * @return
     */
    CodeDemo getById(Long id);

    /**
     * 根据主键查询扩展类*（从从库）
     * @param id
     * @return
     */
    CodeDemoVO selectVOById(Long id);

    /**
     * 根据主键查询扩展类*（从主库）
     * @param id
     * @return
     */
    CodeDemoVO getVOById(Long id);

    /**
     * 分页查询（单表数据）
     * @param page
     * @param codeDemo
     * @return
     */
    Page<CodeDemo> selectPage(Page<CodeDemo> page, CodeDemo codeDemo);

    /**
     * 分页查询（扩展）
     * @param page
     * @param codeDemoVO
     * @return
     */
    Page<CodeDemoVO> selectVOPage(Page<CodeDemoVO> page, CodeDemoVO codeDemoVO);


    //=========================以下用于数据清洗============================
    /**
     * 查询名称重复的标的数据
     * @return
     */
    List<ProductData> selectProductData1() ;


    Integer selectCountByTitle(String title) ;


    /**
     * 数据更新
     * @param data
     * @return
     */
    int updateProductTitle(ProductData data) ;

    /**
     * 处理数据
     * @throws Exception
     */
    void dealWith() throws Exception;



}
