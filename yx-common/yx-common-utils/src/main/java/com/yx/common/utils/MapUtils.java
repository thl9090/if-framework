
package com.yx.common.utils;

import java.util.HashMap;


/**
 * Map工具类
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
public class MapUtils extends HashMap<String, Object> {

    @Override
    public MapUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
