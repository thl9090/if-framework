package com.yx.web.server.controller.content;//package com.yx.wx.server.controller.content;
//
//
//import cn.hutool.core.lang.Assert;
//import com.yx.common.web.BaseController;
//import com.yx.common.web.model.ResultModel;
//import com.yx.common.web.util.ResultUtil;
//import com.yx.sys.common.ContentModelTypeEnum;
//import com.yx.sys.common.SysContentStatusEnum;
//import com.yx.sys.model.SysContent;
//import com.yx.sys.rpc.api.SysContentService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * <p>
// * 前端控制器
// * </p>
// *
// * @author lilulu
// * @since 2018-07-25
// */
//@RestController
//@RequestMapping("/sysRecruitment")
//@Api(value = "招贤纳士", description = "招贤纳士")
//public class SysRecruitmentController extends BaseController {
//
//    @Autowired
//    private SysContentService sysContentService;
//
//    /**
//     * 获取一个招聘信息详情
//     *
//     * @param id 内容标ID
//     * @return ResultModel
//     * @author lilulu
//     * @date 2018-08-20
//     */
//    @ApiOperation(value = "获取一个招聘信息详情", notes = "获取一个招聘信息详情")
//    @PostMapping("/select")
//    public ResultModel selectById(Long id) {
//        Assert.notNull(id);
//        SysContent entity = sysContentService.selectById(id);
//        if(entity == null){
//            entity = new SysContent();
//        }
//        return ResultUtil.ok(entity);
//    }
//
//    /**
//     * 获取全部招聘信息方法
//     * @return com.yx.common.web.model.ResultModel
//     * @author lilulu
//     * @date 2018-08-20
//     */
//    @ApiOperation(value = "获取全部招聘信息方法", notes = "获取全部招聘信息方法")
//    @PostMapping("/selectRecruitments")
//    public ResultModel selectRecruitments() {
//        //组装获取条件
//        SysContent sysContent = new SysContent();
//        sysContent.setModelType(ContentModelTypeEnum.RECRUITMENT.getStatus());
//        sysContent.setStatus(SysContentStatusEnum.PUBLISH.getStatus());
//        List<SysContent> sysContents = sysContentService.selectAll(sysContent);
//        if(sysContents.isEmpty()){
//            sysContents = new ArrayList<SysContent>();
//        }
//        return ResultUtil.ok(sysContents);
//    }
//
//}
//
