package com.yx.web.server.controller.content;


import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.Constants;
import com.yx.common.redis.util.CacheUtil;
import com.yx.common.utils.Collections3;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.common.constant.CacheKeyContstant;
import com.yx.sys.model.SysMessageCenter;
import com.yx.sys.model.vo.SysMessageCenterVO;
import com.yx.sys.rpc.api.SysMessageCenterService;
import com.yx.web.server.annotation.SysLogOpt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 消息中心表前端控制器
 * </p>
 *
 * @author lilulu
 * @since 2018-08-15
 */
@RestController
@RequestMapping("/sysMessageCenter")
@Api(value = "消息中心", description = "消息中心")
@CrossOrigin
public class SysMessageCenterController extends BaseController {


    @Autowired
    private SysMessageCenterService sysMessageCenterService;

    /**
     * 根据消息中心表ID查询
     * @param model 消息中心表ID
     * @return ResultModel
     * @author lilulu
     * @date 2018-08-15
     */
    @ApiOperation(value = "查询某一条消息", notes = "参数：{id:消息主键}")
    @PostMapping("/select")
    @SysLogOpt(module = "查询某一条消息", value = "查询某一条消息", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel selectById(SysMessageCenter model) {
        Assert.notNull(model);
        SysMessageCenterVO entity = sysMessageCenterService.selectVOById(model.getId());
        if(entity == null){
            entity = new SysMessageCenterVO();
        }
        return ResultUtil.ok(entity);
    }

    /**
     * 查询公告分页列表【参数page:获取第几页数据，默认为第一页数据，规定每页10条数据】
     * @param model
     * @return
     */
    @ApiOperation(value = "查询公告分页列表", notes = "参数：{currentPage：消息页码}")
    @PostMapping("/selectNoticeListPage")
    @SysLogOpt(module = "查询公告分页列表", value = "查询公告分页列表", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel selectNoticeListPage(RequestParam model) {
        Assert.notNull(model);
        Page<SysMessageCenter> list = sysMessageCenterService.selectNoticeListPage(model.getCurrentPage());
        return ResultUtil.ok(list);
    }

    /**
     * 查询视频专区列表【参数page:获取第几页数据，默认为第一页数据，规定每页10条数据】
     * @param model
     * @return
     */
    @ApiOperation(value = "查询视频专区分页列表", notes = "参数：{currentPage：消息页码}")
    @PostMapping("/selectVideoListPage")
    @SysLogOpt(module = "查询视频专区分页列表", value = "查询视频专区分页列表", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel selectVideoListPage(RequestParam model) {
        Assert.notNull(model);
        Page<SysMessageCenter> list = sysMessageCenterService.selectVideoListPage(model.getCurrentPage());
        return ResultUtil.ok(list);
    }

    /**
     * 查询行业质询列表【参数page:获取第几页数据，默认为第一页数据，规定每页10条数据】
     * @param model
     * @return
     */
    @ApiOperation(value = "查询行业质询分页列表", notes = "参数：{currentPage：消息页码}")
    @PostMapping("/selectIndustryInquiryListPage")
    @SysLogOpt(module = "查询行业质询分页列表", value = "查询行业质询分页列表", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel selectIndustryInquiryListPage(RequestParam model) {
        Assert.notNull(model);
        Page<SysMessageCenter> list = sysMessageCenterService.selectIndustryInquiryListPage(model.getCurrentPage());
        return ResultUtil.ok(list);
    }

    /**
     * 查询媒体报道列表【参数page:获取第几页数据，默认为第一页数据，规定每页10条数据】
     * @param model
     * @return
     */
    @ApiOperation(value = "查询媒体报道分页列表", notes = "参数：{currentPage：消息页码}")
    @PostMapping("/selectMediaListPage")
    @SysLogOpt(module = "查询媒体报道分页列表", value = "查询媒体报道分页列表", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel selectMediaListPage(RequestParam model) {
        Assert.notNull(model);
        Page<SysMessageCenter> list = sysMessageCenterService.selectMediaListPage(model.getCurrentPage());
        return ResultUtil.ok(list);
    }

    /**
     * 查询存管动态列表【参数page:获取第几页数据，默认为第一页数据，规定每页10条数据】
     * @param model
     * @return
     */
    @ApiOperation(value = "查询存管动态分页列表", notes = "参数：{currentPage：消息页码}")
    @PostMapping("/selectDepositListPage")
    @SysLogOpt(module = "查询存管动态分页列表", value = "查询存管动态分页列表", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel selectDepositListPage(RequestParam model) {
        Assert.notNull(model);
        Page<SysMessageCenter> list = sysMessageCenterService.selectDepositListPage(model.getCurrentPage());
        return ResultUtil.ok(list);
    }

    /**
     * 查询网贷知识列表【参数page:获取第几页数据，默认为第一页数据，规定每页10条数据】
     * @param model
     * @return
     */
    @ApiOperation(value = "查询网贷知识分页列表", notes = "参数：{currentPage：消息页码}")
    @PostMapping("/selectIntellectualListPage")
    @SysLogOpt(module = "查询网贷知识分页列表", value = "查询网贷知识分页列表", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel selectIntellectualListPage(RequestParam model) {
        Assert.notNull(model);
        Page<SysMessageCenter> list = sysMessageCenterService.selectIntellectualListPage(model.getCurrentPage());
        return ResultUtil.ok(list);
    }

    /**
     * 查询政策法规列表【参数page:获取第几页数据，默认为第一页数据，规定每页10条数据】
     * @param model
     * @return
     */
    @ApiOperation(value = "查询政策法规分页列表", notes = "参数：{currentPage：消息页码}")
    @PostMapping("/selectPolicieRegulationsListPage")
    @SysLogOpt(module = "查询政策法规分页列表", value = "查询政策法规分页列表", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel selectPolicieRegulationsListPage(RequestParam model) {
        Assert.notNull(model);
        Page<SysMessageCenter> list = sysMessageCenterService.selectPolicieRegulationsListPage(model.getCurrentPage());
        return ResultUtil.ok(list);
    }
	
	/**
	 * PC-首页-权威资讯数据接口
	 * @return
	 */
	@ApiOperation(value = "PC-首页-权威资讯数据接口", notes = "参数：{}")
	@PostMapping("/selectAuthorityInformation")
	@SysLogOpt(module = "PC-首页-权威资讯数据接口", value = "PC-首页-权威资讯数据接口", operationType = Constants.LogOptEnum.QUERY)
	public ResultModel selectAuthorityInformation() {
        List<SysMessageCenter> list=null;
        String s = (String) CacheUtil.getCache().get(CacheKeyContstant.INDEX_INDUSTRY_INQUIRY);
        if(StringUtils.isNotBlank(s)){
            list = JSON.parseArray(s,SysMessageCenter.class);
        }
        if(Collections3.isEmpty(list)){
            list = sysMessageCenterService.selectAuthorityInformation();
            CacheUtil.getCache().set(CacheKeyContstant.INDEX_INDUSTRY_INQUIRY,JSON.toJSONString(list),30*60);
        }
		return ResultUtil.ok(list);
	}
}

