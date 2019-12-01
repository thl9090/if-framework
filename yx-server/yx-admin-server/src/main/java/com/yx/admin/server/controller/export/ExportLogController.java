package com.yx.admin.server.controller.export;


import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.yx.common.core.model.PageModel;
import com.yx.common.utils.DateUtils;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.common.ExportTypeEnum;
import com.yx.sys.model.ExportLog;
import com.yx.sys.rpc.api.ExportLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
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
public class ExportLogController extends BaseController {

        @Autowired
        private ExportLogService service;


        /**
         * 根据导出日志记录表ID查询
         *
         * @param id 导出日志记录表ID
         * @return ResultModel
         * @author TangHuaLiang
         * @date 2018-08-13
         */
        @GetMapping("/select/{id}")
        public ResultModel selectById(@PathVariable Long id) {
            Assert.notNull(id);
            ExportLog entity = service.selectById(id);
            return ResultUtil.ok(entity);
        }

        /**
         * 查询导出日志记录表分页方法
         *
         * @param pageModel 分页实体
         * @return com.yx.common.web.model.ResultModel
         * @author TangHuaLiang
         * @date 2018-08-13
         */
        @PostMapping("/selectPage")
        public ResultModel selectPage(@RequestBody PageModel<ExportLog> pageModel) {
            ExportLog exportLog=new ExportLog();
            Map<String, Object> condition = pageModel.getCondition();
            if(condition!=null) {
                String jsonStr = JSON.toJSONString(condition);
                exportLog = JSON.parseObject(jsonStr, ExportLog.class);
            }
            exportLog.setCreateBy(getCurrentUserId());
            pageModel = (PageModel<ExportLog>) service.selectPage(pageModel,exportLog);
            return ResultUtil.ok(pageModel);
        }

        @GetMapping("/downloadExcel/{id}")
        public void  downloadExcel(@PathVariable Long id,HttpServletRequest request, HttpServletResponse response) throws Exception{
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

