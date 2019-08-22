package com.neuedu.controller;


import com.neuedu.common.ServerResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class UploadController {

    @RequestMapping(value = "/upload",method = RequestMethod.GET)
    public String upload(){
        return "upload";
    }

    @Value("${img.local.path}")
    private String imgPath;

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse upload(@RequestParam("file")MultipartFile uploadFile){
        File newFile=null;
        if (uploadFile!=null&&uploadFile.getOriginalFilename()!=null&&uploadFile.getOriginalFilename().length()!=0){
            String uuid= UUID.randomUUID().toString();
            String fileName=uploadFile.getOriginalFilename();
            String fileextendname=fileName.substring(fileName.lastIndexOf("."));
            String newFileName=uuid+fileextendname;

            File file=new File(imgPath);
            if (!file.exists()){
                file.mkdir();
            }
            newFile=new File(file,newFileName);

            try {
                uploadFile.transferTo(newFile);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        return ServerResponse.createServerResponseBySucess(newFile!=null?newFile.getName():"");
    }

}
