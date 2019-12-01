package com.yx.sys.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseMapper;
import com.yx.sys.model.ExportLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 导出日志记录表 Mapper 接口
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-08-13
 */
public interface ExportLogMapper extends BaseMapper<ExportLog> {

    /**
     * 分页查询
     * @param page
     * @param ExportLog
     * @return
     */
    List<ExportLog> selectPage(Page<ExportLog> page, @Param("exportLog") ExportLog ExportLog);

    /**
     * 根据用户id和类型查询最新的一条操作记录
     * @param userId
     * @param type
     * @return
     */
    ExportLog selectLateByUserIdAndType(@Param("userId") Long userId,@Param("type") Integer type);


}
