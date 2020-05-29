package com.pyb.oss.Service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.pyb.oss.Service.OssService;
import com.pyb.oss.utils.ConstantPropertyUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/*上传头像*/
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String upload(MultipartFile file) {
        /*地域节点*/
        String endPoint = ConstantPropertyUtil.END_POINT;
        /*id和密匙*/
        String keyId = ConstantPropertyUtil.KEY_ID;
        String keySecret = ConstantPropertyUtil.KEY_SECRET;
        String bucketName = ConstantPropertyUtil.BUCKET_NAME;
        InputStream inputStream = null;
        try {
            /*创建oss*/
            OSS ossClient = new OSSClientBuilder().build(endPoint,keyId,keySecret);
            /*上传流*/
            inputStream = file.getInputStream();
            /*文件的路径加文件名*/
            String dir = DateTime.now().toString("yyyy/MM/dd");
            String fileName = UUID.randomUUID().toString()+ file.getOriginalFilename();

            /*上传的方法*/
            /*第一个参数：bucketName,
            * 第二个参数：文件路径加名称（根据时间分类管理）
            * 第三个参数：文件流*/
            ossClient.putObject(bucketName,dir+"/"+fileName,inputStream);
            /*返回上传的路径*/
            String url = "https://"+bucketName+"."+endPoint+"/"+fileName;
            return url;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
