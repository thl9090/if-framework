package com.yx.business.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseMapper;
import com.yx.business.model.CodeDemo;
import com.yx.sys.model.ProductData;
import com.yx.business.model.vo.CodeDemoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  codeDemo 接口
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-13
 */
public interface CodeDemoMapper extends BaseMapper<CodeDemo> {


    /**
     * 根据主键查询（从主库）
     * @param id
     * @return
     */
    CodeDemo getById(@Param("id") Long id);

    /**
     * 根据主键查询扩展类*（从从库）
     * @param id
     * @return
     */
    CodeDemoVO selectVOById(@Param("id") Long id);

    /**
     * 根据主键查询扩展类*（从主库）
     * @param id
     * @return
     */
    CodeDemoVO getVOById(@Param("id") Long id);

    /**
     * 分页查询（单表数据）
     * @param page
     * @param codeDemo
     * @return
     */
    List<CodeDemo> selectPage(Page<CodeDemo> page, @Param("codeDemo") CodeDemo codeDemo);

    /**
     * 分页查询（扩展）
     * @param page
     * @param codeDemoVO
     * @return
     */
    List<CodeDemoVO> selectVOPage(Page<CodeDemoVO> page, @Param("codeDemoVO") CodeDemoVO codeDemoVO);




    //=========================以下用于数据清洗===================================



    Integer selectCountByTitle(@Param("title") String title);


    /**
     * 数据更新
     * @param data
     * @return
     */
    int updateProductTitle(@Param("data") ProductData data);




}
