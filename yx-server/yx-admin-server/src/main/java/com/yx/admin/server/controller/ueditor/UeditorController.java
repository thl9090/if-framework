package com.yx.admin.server.controller.ueditor;

import com.alibaba.fastjson.JSON;
import com.yx.admin.server.controller.ueditor.define.ActionEnterService;
import com.yx.admin.server.controller.ueditor.define.CommonUtil;
import com.yx.sys.rpc.api.SysParamService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Api(value = "百度富文本编辑器--文件上传", description = "百度富文本编辑器文件上传")
@RestController
@RequestMapping("/ueditor")
public class UeditorController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ActionEnterService actionEnterService;
    @Autowired
    private SysParamService sysParamService;

    //ueditor初始化配置文件路径
    private static String UEDITOR_CONFIG="UEDITOR_CONFIG";


    @GetMapping("/upload")
    public Map<String,Object> get(HttpServletResponse response,HttpServletRequest request) throws Exception {
        logger.info("--------百度富文本编辑器-----------------GET---------");
        try {
            logger.info(JSON.toJSONString(CommonUtil.resquestParameter2Map(request)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //String rootPath = ResourceUtils.getURL("classpath:").getPath();
        //String configJsonPath = rootPath + "config.json";//获取文件路径
        String rootPath="";
        String configJsonPath = sysParamService.queryStringByKey(UEDITOR_CONFIG);//获取文件路径
        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "text/html");
        actionEnterService.init(request, rootPath, configJsonPath);
        //String result = actionEnterService.exec();
        return actionEnterService.exec();
    }

    @PostMapping("/upload")
    public Map<String,Object> post( @RequestParam(value = "upfile", required = false) MultipartFile uploadfile,HttpServletRequest request) throws Exception {
        logger.info("---------百度富文本编辑器------------POST----------------");
        try {
            logger.info(JSON.toJSONString(CommonUtil.resquestParameter2Map(request)));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String rootPath = ResourceUtils.getURL("classpath:").getPath();
//        String configJsonPath = rootPath + "config.json";//获取文件路径
        String rootPath="";
        String configJsonPath = sysParamService.queryStringByKey(UEDITOR_CONFIG);
        if(uploadfile != null){
            actionEnterService.init(request, uploadfile, rootPath, configJsonPath);
        }else{
            String source = request.getParameter("source[]");
            actionEnterService.init(request, source, rootPath, configJsonPath);
        }
        return actionEnterService.exec();
    }




}
