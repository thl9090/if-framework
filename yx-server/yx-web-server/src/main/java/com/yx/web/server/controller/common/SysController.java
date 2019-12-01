package com.yx.web.server.controller.common;

import com.yx.sys.common.AppTypeEnum;
import com.yx.sys.model.appVersion.AppPackageVersion;
import com.yx.sys.rpc.api.AppPackageVersionService;
import com.yx.web.server.controller.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 公共属性controller
 *
 * @author YanBingHao
 * @since 2018/9/4
 */
@Slf4j
@Api(value = "老系统特殊处理--APP扫码下载", description = "老系统特殊处理--APP扫码下载")
@RestController
@RequestMapping(value = "/")
@CrossOrigin
public class SysController extends BaseController {

    @Autowired
    private AppPackageVersionService appPackageVersionService;

    @GetMapping("/downloadapplawyer")
    public String  downloadApp(HttpServletRequest request, HttpServletResponse response) throws Exception{

        AppPackageVersion appPackageVersion = appPackageVersionService.selectNewestByAppType(AppTypeEnum.ANDROID.getId());

        if(appPackageVersion==null){
            return null;
        }
        if(StringUtils.isBlank(appPackageVersion.getPackageUrl())){
            return null;
        }


        String fileUrl = appPackageVersion.getPackageUrl();

        String agent=  request.getHeader("user-agent");
        String phoneType = request.getParameter("android");
        //如果phoneType非空,说明是PC下载
        if(phoneType!=null){
            return fileUrl;
        }
        try {
            if (agent.contains("Android")) {
                response.sendRedirect(fileUrl);
            } else if (agent.contains("iPhone") || agent.contains("iPod") || agent.contains("iPad")) {
                response.sendRedirect("https://itunes.apple.com/us/app/%E9%93%B6%E4%BF%A1%E7%A7%81%E4%BA%BA%E8%B4%A2%E8%A1%8C/id1244651095?mt=8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileUrl;
        /*String filename = "yxsrch_"+appPackageVersion.getPackageVersion()+"_android"+ ".apk";
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
        }*/
    }

}
