package com.yx.sys.model.vo;

import com.yx.sys.model.product.ProductCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 描述：标的审核扩展类
 * @author  TangHuaLiang
 * @date 2018-07-13
 */
@ApiModel(value="ProductCheckVO",description="标的审核扩展类")
public class ProductCheckVO extends ProductCheck {

    @ApiModelProperty(value="初审状态名称")
    private String firstCheckStatusStr;

    @ApiModelProperty(value="终审状态名称")
    private String finalCheckStatusStr;

    @ApiModelProperty(value="初审用户")
    private String createUserName;

    @ApiModelProperty(value="初审用户")
    private String updateUserName;


    public String getFirstCheckStatusStr() {
        return firstCheckStatusStr;
    }

    public void setFirstCheckStatusStr(String firstCheckStatusStr) {
        this.firstCheckStatusStr = firstCheckStatusStr;
    }

    public String getFinalCheckStatusStr() {
        return finalCheckStatusStr;
    }

    public void setFinalCheckStatusStr(String finalCheckStatusStr) {
        this.finalCheckStatusStr = finalCheckStatusStr;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }
}
