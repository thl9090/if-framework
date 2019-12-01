package com.yx.sys.model.company;

import com.yx.common.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 公司信息model
 *
 * @author YanBingHao
 * @since 2018/9/4
 */
@ApiModel(value = "公司信息model")
public class CompanyModel extends BaseModel {

    private static final long serialVersionUID = 2144942786290870476L;

    @ApiModelProperty(value = "客服热线")
    private String servicePhone;

    @ApiModelProperty(value = "工作日服务时间")
    private String workingDaysServiceTime;

    @ApiModelProperty(value = "周末服务时间")
    private String weekendServiceTime;

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getWorkingDaysServiceTime() {
        return workingDaysServiceTime;
    }

    public void setWorkingDaysServiceTime(String workingDaysServiceTime) {
        this.workingDaysServiceTime = workingDaysServiceTime;
    }

    public String getWeekendServiceTime() {
        return weekendServiceTime;
    }

    public void setWeekendServiceTime(String weekendServiceTime) {
        this.weekendServiceTime = weekendServiceTime;
    }
}
