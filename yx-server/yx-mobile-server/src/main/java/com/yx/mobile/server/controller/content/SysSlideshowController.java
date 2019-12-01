package com.yx.mobile.server.controller.content;

import com.alibaba.fastjson.JSON;
import com.yx.common.core.Constants;
import com.yx.common.redis.util.CacheUtil;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.mobile.server.annotation.SysLogOpt;
import com.yx.mobile.server.controller.BaseController;
import com.yx.sys.common.ClientTypeEnum;
import com.yx.sys.common.SlideShowStatusEnum;
import com.yx.sys.common.SlideshowTypeEnum;
import com.yx.sys.common.constant.CacheKeyContstant;
import com.yx.sys.model.SysSlideshow;
import com.yx.sys.rpc.api.SysSlideshowService;
import com.yx.user.model.ClientInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
@RestController
@RequestMapping("/sysSlideshow")
@Api(value = "广告图片", description = "广告图片")
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
    @ApiOperation(value = "获取所有移动端轮播图", notes = "参数：{}")
    @PostMapping("/getSlideshow")
    @SysLogOpt(module = "获取所有移动端轮播图", value = "获取所有移动端轮播图", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel getSlideshow(HttpServletRequest request) {
        List<SysSlideshow> slideshowVOS =null;

        String dataStr = (String)CacheUtil.getCache().get(CacheKeyContstant.SYS_SLIDESHOW_LIST_APP);
        if(StringUtils.isNotBlank(dataStr)){
            slideshowVOS = JSON.parseArray(dataStr, SysSlideshow.class);
        }
        if(slideshowVOS==null){
            SysSlideshow slideshow = new SysSlideshow();
            //轮播图
            slideshow.setType(SlideshowTypeEnum.CAROUSEL.getStatus());
            //移动端
            slideshow.setClientType(ClientTypeEnum.APP.getStatus());
            //已发布
            slideshow.setStatus(SlideShowStatusEnum.PUBLISH.getStatus());
            slideshowVOS = sysSlideshowService.selectList(slideshow);
            //更新数据到redis
            CacheUtil.getCache().set(CacheKeyContstant.SYS_SLIDESHOW_LIST_APP,JSON.toJSONString(slideshowVOS),30*60);
        }

        if (slideshowVOS != null && !slideshowVOS.isEmpty()) {
            List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
            ClientInfo clientInfo = getClientInfo(request);
            for (SysSlideshow obj : slideshowVOS) {
                String url = obj.getUrl();
                if (StringUtils.isNotBlank(url) && !url.equals("#")) {
                    if (url.indexOf("?") != -1) {
                        url = url + "&channelType=" + clientInfo.getChannelType();
                    } else {
                        url = url + "?channelType=" + clientInfo.getChannelType();
                    }
                    url = url + "&channelVersion=" + clientInfo.getChannelVersion();
                    /*if(StringUtils.isNotBlank(clientInfo.getAccessToken())){
                        url=url+"&accessToken="+clientInfo.getAccessToken();
                    }*/
                }

                Map<String, Object> map = new HashMap<>();
                map.put("filePath", obj.getFilePath());
                //map.put("url", ChannelTypeEnum.IOS.getId().equals(clientInfo.getChannelType()) ? "#" : url);
                map.put("url", url);
                map.put("title", obj.getTitle());
                maps.add(map);
            }
            return ResultUtil.ok(maps);
        } else {
            return ResultUtil.ok("");
        }

    }

    /**
     * 获取所有移动开屏广告
     *
     * @return ResultModel
     * @author lilulu
     * @date 2018-08-09
     */
    @ApiOperation(value = "获取所有移动开屏广告", notes = "参数：{}")
    @PostMapping("/getOpenAdvertising")
    @SysLogOpt(module = "获取所有移动开屏广告", value = "获取所有移动开屏广告", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel getOpenAdvertising() {

        SysSlideshow slideshowVOS = null;
        String dataStr = (String)CacheUtil.getCache().get(CacheKeyContstant.SYS_OPEN_ADVICE);
        if(StringUtils.isNotBlank(dataStr)){
            slideshowVOS=JSON.parseObject(dataStr,SysSlideshow.class);
        }
        if(slideshowVOS==null){
            slideshowVOS = sysSlideshowService.getOpenAdvertising();
        }

        if (slideshowVOS != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("filePath", slideshowVOS.getFilePath());
            map.put("url", slideshowVOS.getUrl());
            map.put("title", slideshowVOS.getTitle());
            return ResultUtil.ok(map);
        } else {
            return ResultUtil.ok("");
        }

    }

}

