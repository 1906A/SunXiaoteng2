package com.leyou.controller;

import com.netflix.discovery.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("upload")
public class UploadController {

    public static final List<String> FILE_TYPE = Arrays.asList("jpg","png");

    @Value("${user.httpImageYuming}")
    private String httpImage;

    @RequestMapping("image")
    public String uploadImage(@RequestParam("file") MultipartFile file){

        try {
            String originalFilename = file.getOriginalFilename();
            String filename = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            //String s = StringUtils.substringAfterLast(originalFilename, ".");
            if (!FILE_TYPE.contains(filename)){
                return null;
            }
            BufferedImage read = ImageIO.read(file.getInputStream());
            if (read==null){
                return null;
            }
            String filePath = System.currentTimeMillis()+originalFilename;
            //file.transferTo(new File("D:/images/"+filePath));

                //加载客户端配置文件，配置文件中指明了tracker服务器的地址
                ClientGlobal.init("fastdfs.conf");
                //验证配置文件是否加载成功
                System.out.println(ClientGlobal.configInfo());

                //创建TrackerClient对象，客户端对象
                TrackerClient trackerClient = new TrackerClient();

                //获取到调度对象，也就是与Tracker服务器取得联系
                TrackerServer trackerServer = trackerClient.getConnection();

                //创建存储客户端对象
                StorageClient storageClient = new StorageClient(trackerServer,null);
                String[] strings = storageClient.upload_appender_file(file.getBytes(), filename, null);
                for (String string : strings){
                    System.out.println(string);
                    //group/m0/xxxxx.jpn
                }

            return httpImage + strings[0]+"/"+strings[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
