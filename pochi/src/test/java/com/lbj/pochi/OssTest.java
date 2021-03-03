package com.lbj.pochi;

import com.lbj.pochi.oss.OssService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OssTest {

    @Autowired
    private OssService ossService;

    @Test
    public void test() throws Exception {
        MultipartFile file = new MockMultipartFile("logo", "png", "", new FileInputStream(new File("D:\\study\\MyDev\\资源\\宠物商城\\【波奇商城】教学资源\\资料\\静态资源\\logo.png")));
        String header = ossService.upload(file, "header");
        System.out.println(header);
    }

}