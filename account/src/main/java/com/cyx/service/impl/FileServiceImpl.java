package com.cyx.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.cyx.config.OSSConfig;
import com.cyx.service.FileService;
import com.cyx.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Autowired
    private OSSConfig ossConfig;

    @Override
    public String uploadUserImage(MultipartFile file) {
        //获取相关配置
        String bucketName = ossConfig.getBucketname();
        String endpoint = ossConfig.getEndpoint();
        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getAccessKeySecret();
        //创建OSS对象
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String originalName = file.getOriginalFilename();

        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String folder = formatter.format(ldt);
        String fileName = CommonUtil.generateUUID();
        String extend = originalName.substring(originalName.lastIndexOf("."));

        String newFileName = "user/" + folder + "/" + fileName + extend;
        try {
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, newFileName, file.getInputStream());
            if (putObjectResult == null) {
                return null;
            }
            String imgUrl = "https://" + bucketName + "." + endpoint + "/" + newFileName;
            return imgUrl;
        } catch (IOException e) {
            log.error("文件上传失败");
        } finally {
            ossClient.shutdown();
        }
        return null;
    }
}
