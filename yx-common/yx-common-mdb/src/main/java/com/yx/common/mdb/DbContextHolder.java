package com.yx.common.mdb;

import cn.hutool.core.util.StrUtil;
import com.yx.common.core.Constants;

/**
 * 数据源DbContextHolder
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public class DbContextHolder {
    private static final ThreadLocal<String> CONTEXTHOLDER = new ThreadLocal<String>();

    /**
     * 设置数据源
     *
     * @param dataSourceEnum
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    public static void setDbType(Constants.DataSourceEnum dataSourceEnum) {
        CONTEXTHOLDER.set(dataSourceEnum.getName());
    }

    /**
     * 取得当前数据源
     *
     * @return String
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    public static String getDbType() {
        String dataSource = CONTEXTHOLDER.get();
        // 如果没有指定数据源，使用默认数据源
        if (StrUtil.isEmpty(dataSource)) {
            DbContextHolder.setDbType(Constants.DataSourceEnum.MASTER);
        }
        return CONTEXTHOLDER.get();
    }

    /**
     * 清除上下文数据
     *
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    public static void clearDbType() {
        CONTEXTHOLDER.remove();
    }
}
