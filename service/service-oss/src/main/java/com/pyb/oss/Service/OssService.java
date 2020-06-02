package com.pyb.oss.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface OssService {

    String upload(MultipartFile file);
}
