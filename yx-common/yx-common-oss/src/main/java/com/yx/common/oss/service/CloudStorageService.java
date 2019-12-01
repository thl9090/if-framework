package com.yx.common.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 云储存服务接口
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 **/
public interface CloudStorageService {

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @return 返回http地址
     */
    String upload(byte[] data, String path);

    /**
     * 文件上传
     *
     * @param data   文件字节数组
     * @param suffix 后缀
     * @return 返回http地址
     */
    String uploadSuffix(byte[] data, String suffix);

    String uploadByFileName(byte[] data, String fileName);

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param path        文件路径，包含文件名
     * @return 返回http地址
     */
    String upload(InputStream inputStream, String path);

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param suffix      后缀
     * @return 返回http地址
     */
    String uploadSuffix(InputStream inputStream, String suffix);

    /**
     * 文件上传
     * @param file
     * @return
     */
    String upload(File file) throws IOException;

    /**
     * 文件上传
     * @param file
     * @return
     */
    String upload(File file, String path) throws IOException;

    /**
     * 文件上传
     * @param file
     * @return
     */
    String upload(MultipartFile file) throws IOException;

    /**
     * 下载文件
     *
     * @param path 文件路径
     * @return InputStream
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    InputStream download(String path);

    /**
     * 删除文件
     *
     * @param path 文件路径
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    void delete(String path);
}
