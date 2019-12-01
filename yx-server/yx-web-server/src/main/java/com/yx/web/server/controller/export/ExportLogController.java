package com.yx.web.server.controller.export;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.Constants;
import com.yx.common.redis.util.CacheUtil;
import com.yx.common.utils.Collections3;
import com.yx.common.utils.DateUtils;
import com.yx.common.utils.UUIDUtil;
import com.yx.common.web.annotation.Authorization;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.common.ExportTypeEnum;
import com.yx.sys.model.ExportLog;
import com.yx.sys.model.vo.ExportLogVO;
import com.yx.sys.rpc.api.ExportLogService;
import com.yx.sys.rpc.api.SysParamService;
import com.yx.web.server.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 导出日志记录表前端控制器
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-08-13
 */
@RestController
@RequestMapping("/exportLog")
@CrossOrigin
@Api(value = "EXCEL导出日志", description = "EXCEL导出日志")
public class ExportLogController extends BaseController {

    @Autowired
    private ExportLogService service;
    @Autowired
    private SysParamService sysParamService;

    private static String PC_SERVER_URL = "PC_SERVER_URL";

    private static String DOWNLOAD_EXCEL="download_excel:";

    /**
     * 根据导出日志记录表ID查询
     *
     * @param id 导出日志记录表ID
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-08-13
     */
    @GetMapping("/select/{id}")
    @Authorization
    public ResultModel selectById(@PathVariable Long id, HttpServletRequest request) {
        Long currentUserId = getCurrentUserId(request);
        if (currentUserId == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR,
                    "没有获取到当前用户");
        }
        Assert.notNull(id);
        ExportLog entity = service.selectById(id);
        return ResultUtil.ok(entity);
    }

    /**
     * 查询导出日志记录表分页方法
     *
     * @param model 分页实体
     * @return com.yx.common.web.model.ResultModel
     * @author TangHuaLiang
     * @date 2018-08-13
     */
    @ApiOperation(value = "excel导出日志列表",notes = "传参说明：{currentPage:当前页码,type:导出类型}")
    @PostMapping("/selectPage")
    @Authorization
    public ResultModel selectPage(ExportLogVO model, HttpServletRequest request) {
        Long currentUserId = getCurrentUserId(request);
        if (currentUserId == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到当前用户");
        }
        ExportLog exportLog=new ExportLog();
        Page pageModel=new Page();
        if(model.getCurrentPage()!=null){
            pageModel.setCurrent(model.getCurrentPage());
        }
        exportLog.setCreateBy(currentUserId);
        exportLog.setType(model.getType());
        Page<ExportLog> page = service.selectPage(pageModel, exportLog);
        if(!Collections3.isEmpty(page.getRecords())){
            for(ExportLog log:page.getRecords()){
                log.setFileUrl(null);
            }
        }

        return ResultUtil.ok(page);
    }


    @ApiOperation(value = "获取excel下载的url",notes = "")
    @PostMapping("/getDownloadExcelUrl")
    @Authorization
    public ResultModel getDownloadExcelUrl(ExportLogVO model, HttpServletRequest request) {
        Long currentUserId = getCurrentUserId(request);
        if (currentUserId == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到当前用户");
        }
        ExportLog entity = service.selectById(model.getId());
        if(entity==null){
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR,"没有查询到有效的数据");
        }
        String uuid = UUIDUtil.getUUID();
        CacheUtil.getCache().set(DOWNLOAD_EXCEL+uuid,1,10);
        String returnUrl = sysParamService.queryStringByKey(PC_SERVER_URL)+"/web/exportLog/downloadExcel/"+entity.getId()+"/"+uuid;
        Map<String,String> data=new HashMap<>();
        data.put("url",returnUrl);
        return ResultUtil.ok(data);
    }


    @GetMapping("/downloadExcel/{id}/{paramKey}")
    public void  downloadExcel(@PathVariable Long id,@PathVariable String paramKey, javax.servlet.http.HttpServletRequest request, HttpServletResponse response) throws Exception{
        if(StringUtils.isBlank(paramKey)){
            return;
        }
        Object o = CacheUtil.getCache().get(DOWNLOAD_EXCEL + paramKey);
        if(o==null){
            return;
        }
        CacheUtil.getCache().del(DOWNLOAD_EXCEL + paramKey);
        ExportLog entity = service.selectById(id);
        if(entity==null){
            return;
        }
        if(StringUtils.isBlank(entity.getFileUrl())){
            return;
        }
        ExportTypeEnum resolve = ExportTypeEnum.resolve(entity.getType());
        if(resolve==null){
            return;
        }

        String fileUrl = entity.getFileUrl();
        String dateStr = DateUtils.formatDate(entity.getCreateTime() == null ? new Date() : entity.getCreateTime());
        String filename = resolve.getExcelName()+"-"+ dateStr+"-"+System.currentTimeMillis()+".xls";
        ServletOutputStream out = null;
        DataInputStream input=null;
        try {// 下载网络文件
            URL url = new URL(fileUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            input= new DataInputStream(conn.getInputStream());
            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("application/octet-stream");
            //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.replaceAll(" ", "").getBytes("utf-8"),"iso8859-1"));
            //3.通过response获取ServletOutputStream对象(out)
            out = response.getOutputStream();

            int b = 0;
            byte[] buffer = new byte[4096];
            while ((b=input.read(buffer, 0, 1024)) >=0) {
                //4.写到输出流(out)中
                out.write(buffer, 0, b);
            }
            input.close();
            out.flush();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流，不可少
            try{
                input.close();
                out.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}

