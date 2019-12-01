package com.yx.common.oss.service.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import com.yx.common.oss.OssProperties;
import com.yx.common.oss.service.CloudStorageService;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 腾讯云对象存储服务类
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public class QqCloudStorageServiceImpl implements CloudStorageService {

    private OssProperties ossProperties;
    private COSClient client;

    public QqCloudStorageServiceImpl(OssProperties ossProperties) {
        this.ossProperties = ossProperties;
        // 初始化
        init();
    }

    private void init() {
        COSCredentials cred = new BasicCOSCredentials(ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
        ClientConfig clientConfig = new ClientConfig(new Region(ossProperties.getEndPoint()));
        client = new COSClient(cred, clientConfig);
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            // 腾讯云必需要以"/"开头
            String startWith = "/";
            if (!path.startsWith(startWith)) {
                path = "/" + path;
            }
            PutObjectRequest putObjectRequest = new PutObjectRequest(ossProperties.getBucketName(), path, inputStream,
                    objectMetadata);
            client.putObject(putObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
            //throw new BusinessException(Constants.ResultCodeEnum.INTERNAL_SERVER_ERROR.value(), "上传文件失败，请检查配置信息", e);
        }

        return ossProperties.getDomain() == null ? ossProperties.getEndPoint() : ossProperties.getDomain() + "/" + path;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return null;
    }

    @Override
    public String uploadByFileName(byte[] data, String fileName) {
        return null;
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return null;
    }

    @Override
    public String upload(File file) throws IOException {
        return null;
    }

    @Override
    public String upload(File file, String path) throws IOException {
        return null;
    }

    @Override
    public String upload(MultipartFile file) throws IOException {
        return null;
    }


    @Override
    public InputStream download(String path) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(ossProperties.getBucketName(), path);
        return client.getObject(getObjectRequest).getObjectContent();
    }

    @Override
    public void delete(String path) {
        client.deleteObject(ossProperties.getBucketName(), path);
    }
}
