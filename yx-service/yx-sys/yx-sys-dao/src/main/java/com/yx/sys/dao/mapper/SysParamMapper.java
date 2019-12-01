package com.yx.sys.dao.mapper;

import com.yx.common.core.base.BaseMapper;
import com.yx.common.core.model.PageModel;
import com.yx.sys.model.SysParam;
import com.yx.sys.model.company.Company;
import com.yx.sys.model.company.CompanyModel;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 全局参数表 Mapper 接口
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
public interface SysParamMapper extends BaseMapper<SysParam> {

    SysParam getByKey(@Param("key") String key);

   Company selectCompany(PageModel<Company> pageModel);

    void updateCompany(@Param("paramValue")String paramValue,@Param("paramKey") String paramKey);

    String getStringByKey(@Param("key") String key);


    int updateParam(@Param("key") String key, @Param("param") String param);

    CompanyModel getCompanyInfo();

}
