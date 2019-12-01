package com.yx.wx.server.controller.content;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.Constants;
import com.yx.wx.server.controller.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.common.ClientTypeEnum;
import com.yx.sys.common.SlideshowTypeEnum;
import com.yx.sys.model.SysSlideshow;
import com.yx.sys.rpc.api.SysSlideshowService;
import com.yx.user.model.ClientInfo;
import com.yx.wx.server.annotation.SysLogOpt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName 活动消息
 * @Author zhangxiaowen
 * @Date 2018/11/8 17:01
 * @Version 1.0
 **/
@RestController
@RequestMapping("/sysActivityMsg")
@Api(value = "活动消息", description = "活动消息")
@CrossOrigin
public class SysActivityMsgController extends BaseController {


    @Autowired
    private SysSlideshowService sysSlideshowService;


    /**
     * 活动消息分页方法
     *
     * @param model 实体
     * @date 2018-11-08
     */
    @ApiOperation(value = "活动消息分页方法", notes = "传参说明:{currentPage :当前页 }")
    @PostMapping("/selectPage")
    @SysLogOpt(module = "活动消息分页方法", value = "活动消息分页方法", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel selectPage(RequestParam model, HttpServletRequest request) {
        Assert.notNull(model);
        Page page = new Page();
        if (model.getCurrentPage() != null) {
            page.setCurrent(model.getCurrentPage());
        }

        SysSlideshow sysSlideshow = new SysSlideshow();
        sysSlideshow.setType(SlideshowTypeEnum.ACTIVITY_MSG.getStatus());
        sysSlideshow.setClientType(ClientTypeEnum.WX.getStatus());

        Page<SysSlideshow> list = sysSlideshowService.selectActivityMsgPage(page, sysSlideshow);


        List<SysSlideshow> records = list.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return ResultUtil.ok(list);
        }

        StringBuilder tmpUrl = new StringBuilder();
        ClientInfo clientInfo = getClientInfo(request);
        for (SysSlideshow record : records) {
            tmpUrl.setLength(0);
            tmpUrl.append(record.getUrl());
            if (tmpUrl.length() > 0
                    && StringUtils.isNotBlank(tmpUrl.toString())
                    && !"#".equals(tmpUrl.toString())) {
                tmpUrl.append(tmpUrl.toString().indexOf("?") != -1 ? "&" : "?").append("channelType=").append(clientInfo.getChannelType());
            }
            record.setUrl(tmpUrl.toString());
        }

        

        return ResultUtil.ok(list);
    }

}
