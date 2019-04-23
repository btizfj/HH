package cn.yznu.hh.controller.fileController;

import cn.yznu.hh.service.HHService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Controller
public class FileUploadController {

    /**
     * 自动注入imssraService
     */
    @Autowired
    @Qualifier("hhService")
    private HHService hhService;

    /**
     * 上传单个文件到
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public void upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("开始");
        System.out.println(file);
        String path = request.getSession().getServletContext().getRealPath("upload");
        String fileName = file.getOriginalFilename();
        String[] strs = fileName.split("\\.");
//        System.out.println(path);
//        System.out.println(fileName);
//        System.out.println(Arrays.toString(fileName.split(".")));
        UUID uuid = UUID.randomUUID();
        fileName = uuid.toString().replace("-","")+"."+strs[strs.length-1];
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        // 保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");
        //通过response 获取pw
        PrintWriter pw = response.getWriter();
//        pw.print(request.getContextPath() + "/upload/" + fileName);//返回文件的路劲
        pw.print(fileName);//返回文件的路劲
        pw.flush();
        pw.close();
    }


}
