package com.lbj.pochi.oss;

import com.aliyun.oss.OSS;
import com.lbj.pochi.enums.ResultEnum;
import com.lbj.pochi.exception.PochiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * OSS文件接口默认的实现类
 */
@Service
@Slf4j
public class OssServiceImpl implements OssService {

    @Autowired
    private OssConfig ossConfig;
    @Autowired
    private OSS oss;

    @Override
    public String upload(MultipartFile uploadFile, String dir) {
        //1.检验文件类型
        List<String> imageTypes = ossConfig.getImageTypes();
        boolean isLegal = false;
        String ext = uploadFile.getOriginalFilename();
        for (String imageType : imageTypes) {
            //忽略大小写
            if (StringUtils.endsWithIgnoreCase(ext, imageType)) {
                isLegal = true;
                break;
            }
        }
        if (!isLegal) {
            throw new PochiException(ResultEnum.FILE_TYPE_ERROR);
        }
        //2.检验文件夹是否存在
        boolean dirFlag = false;
        List<String> uploadDirs = ossConfig.getUploadDirs();
        for (String uploadDir : uploadDirs) {
            if (dir.equals(uploadDir)) {
                dirFlag = true;
                break;
            }
        }
        if (!dirFlag) {
            throw new PochiException(ResultEnum.DIR_NOT_FOUND);
        }
        //3.计算最大上传文件大小,并进行检验
        long maxSize = ossConfig.getMaxSize() * 1024 * 1024L;//转换成 B
        if (maxSize < uploadFile.getSize()) {
            throw new PochiException(ResultEnum.FILE_TOO_LARGE);
        }
        //4.上传文件
        //  4.1获取文件名
        String fileName = getPath(ext, dir);
        //  4.2上传文件putObject
        try {
            oss.putObject(ossConfig.getBucketName(), fileName, new ByteArrayInputStream(uploadFile.getBytes()));
        } catch (IOException e) {
            //失败记录日志
            log.error("文件上传失败，", e);
            throw new PochiException("文件上传失败");
        }
        //5.返回文件名  根路径+方法获取的文件名
        return ossConfig.getViewUrl() + "/" + fileName;
    }

    /**
     * 获取文件名
     *
     * @param ext
     * @param dir
     * @return
     */
    private String getPath(String ext, String dir) {
        return dir + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/"
                + UUID.randomUUID().toString() + "." + ext;
    }

}