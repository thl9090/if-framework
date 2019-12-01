package com.yx.admin.server.controller.ueditor.define;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 百度ueditor文件上传
 * @author TangHuaLiang
 * @since 2018-12-22
 */
public interface ActionEnterService {
    void init(HttpServletRequest request, String rootPath, String configJsonPath);

    void init(HttpServletRequest request, MultipartFile uploadfile, String rootPath, String configJsonPath);

    void init(HttpServletRequest request, String source, String rootPath, String configJsonPath);

    Map<String,Object> exec();
}
