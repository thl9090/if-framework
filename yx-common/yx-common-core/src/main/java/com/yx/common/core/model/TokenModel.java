package com.yx.common.core.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 令牌
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public class TokenModel implements Serializable {
    private static final long serialVersionUID = 8238083454490855723L;

    @Getter
    @Setter
    private String value;

    @Getter
    @Setter
    private Long time;
}
