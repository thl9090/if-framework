package com.yx.common.oss.service.impl;

import com.aliyun.oss.OSSClient;
import com.yx.common.oss.OssProperties;
import com.yx.common.oss.service.CloudStorageService;
import com.yx.common.utils.DateUtils;
import com.yx.common.utils.StreamUtils;
import com.yx.common.utils.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.UUID;

/**
 * 阿里云对象存储服务类
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public class AliCloudStorageServiceImpl implements CloudStorageService {

    private OssProperties ossProperties;
    private OSSClient client;

    public AliCloudStorageServiceImpl(OssProperties ossProperties) {
        this.ossProperties = ossProperties;
        // 初始化
        init();
    }

    private void init() {
        client = new OSSClient(ossProperties.getEndPoint(), ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret());
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            client.putObject(ossProperties.getBucketName(), path, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            //throw new BusinessException(INTERNAL_SERVER_ERROR.value(), "上传文件失败，请检查配置信息", e);
        } finally {
            client.shutdown();
        }


        return ossProperties.getDomain() == null ? ossProperties.getEndPoint() : ossProperties.getDomain() + "/" + path;
    }

    /**
     * 文件路径
     *
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回上传路径
     */
    public String getPath(String prefix, String suffix) {
        //生成uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //文件路径
        String path = DateUtils.formatDate(new Date(), "yyyyMMdd") + "/" + uuid;

        if (StringUtils.isNotBlank(prefix)) {
            path = prefix + "/" + path;
        }

        return path + suffix;
    }

    public String getPathByFileName(String prefix, String fileName) {
        //文件路径
        String path = DateUtils.formatDate(new Date(), "yyyyMMdd") + "/" + fileName;
        if (StringUtils.isNotBlank(prefix)) {
            path = prefix + "/" + path;
        }
        return path;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(ossProperties.getPrefix(), suffix));
    }

    @Override
    public String uploadByFileName(byte[] data, String fileName) {
        return upload(data, getPathByFileName(ossProperties.getPrefix(), fileName));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(ossProperties.getPrefix(), suffix));
    }

    @Override
    public String upload(File file) throws IOException {
        String suffix = file.getPath().substring(file.getPath().lastIndexOf("."));
        return upload(file, suffix);
    }

    @Override
    public String upload(File file, String path) throws IOException {
        FileInputStream input = null;
        byte[] bytes = null;

        try {
            input = new FileInputStream(file);
            bytes = StreamUtils.InputStreamTOByte(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadSuffix(bytes, path);
    }

    @Override
    public String upload(MultipartFile file) {
        byte[] bytes = null;
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadSuffix(bytes, suffix);
    }

    @Override
    public InputStream download(String path) {
        return client.getObject(ossProperties.getBucketName(), path).getObjectContent();
    }

    @Override
    public void delete(String path) {
        client.deleteObject(ossProperties.getBucketName(), path);
    }
}
