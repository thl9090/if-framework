package com.yx.wx.server.controller.content;

import com.alibaba.fastjson.JSON;
import com.yx.common.redis.util.CacheUtil;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.common.ClientTypeEnum;
import com.yx.sys.common.SlideShowStatusEnum;
import com.yx.sys.common.SlideshowTypeEnum;
import com.yx.sys.common.constant.CacheKeyContstant;
import com.yx.sys.model.SysSlideshow;
import com.yx.sys.rpc.api.SysSlideshowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 轮播图前端控制器
 *
 * @author lilulu
 * @since 2018-08-09
 */
@RestController
@RequestMapping("/sysSlideshow")
@Api(value = "广告图片", description = "广告图片")
@CrossOrigin
public class SysSlideshowController extends BaseController {

    @Autowired
    private SysSlideshowService sysSlideshowService;

    /**
    * 获取所有移动端轮播图
    *
    * @return ResultModel
    * @author lilulu
    * @date 2018-08-09
    */
    @ApiOperation(value = "获取所有wx端轮播图", notes = "参数：{}")
    @PostMapping("/getSlideshow")
    public ResultModel getSlideshow(){
        List<SysSlideshow> slideshowVOS=null;
        String dataStr = (String) CacheUtil.getCache().get(CacheKeyContstant.SYS_SLIDESHOW_LIST_WX);
        if(StringUtils.isNotBlank(dataStr)){
            slideshowVOS = JSON.parseArray(dataStr, SysSlideshow.class);
        }
        if(slideshowVOS==null){
            SysSlideshow slideshow = new SysSlideshow();
            //轮播图
            slideshow.setType(SlideshowTypeEnum.CAROUSEL.getStatus());
            //移动端
            slideshow.setClientType(ClientTypeEnum.WX.getStatus());
            //已发布
            slideshow.setStatus(SlideShowStatusEnum.PUBLISH.getStatus());
            slideshowVOS = sysSlideshowService.selectList(slideshow);
            //更新数据到redis
            CacheUtil.getCache().set(CacheKeyContstant.SYS_SLIDESHOW_LIST_WX,JSON.toJSONString(slideshowVOS),30*60);
        }

        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        if(slideshowVOS != null && !slideshowVOS.isEmpty()){
            for (SysSlideshow obj: slideshowVOS) {
                Map<String, Object> map = new HashMap<>();
                map.put("filePath", obj.getFilePath());
                map.put("url", obj.getUrl());
                map.put("title", obj.getTitle());
                maps.add(map);
            }
        }
        return ResultUtil.ok(maps);
    }
}

