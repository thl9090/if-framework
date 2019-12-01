package com.yx.sys.model.vo;

import com.yx.sys.model.SysOss;

import java.util.Date;

public class SysOssVO extends SysOss {

    private Date createTimeStart;

    private Date createTimeEnd;

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }
}
