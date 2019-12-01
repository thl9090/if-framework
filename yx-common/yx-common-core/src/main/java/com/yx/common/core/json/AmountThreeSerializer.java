package com.yx.common.core.json;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 金额序列化格式
 *
 * @author YanBingHao
 * @since 2018/11/20
 */
public class AmountThreeSerializer implements ObjectSerializer {

    private static final DecimalFormat AMOUNT_FORMAT = new DecimalFormat(",##0.00");

    @Override
    public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
        if (o == null) {
            jsonSerializer.write("0.00");
            return;
        }
        jsonSerializer.write(AMOUNT_FORMAT.format(o));
    }

    public static String format(BigDecimal amount){
        if (amount == null) {
            return "0.00";
        }
        return AMOUNT_FORMAT.format(amount);
    }

}
