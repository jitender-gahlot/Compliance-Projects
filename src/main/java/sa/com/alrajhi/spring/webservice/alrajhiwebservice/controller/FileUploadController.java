package sa.com.alrajhi.spring.webservice.alrajhiwebservice.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FileUploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.ALL_VALUE)
    public String handleMapping(@RequestParam("fileupload") MultipartFile file) {
        String FileName = file.getName();
        String x1=null;
        List list = new ArrayList<String>();
        try {
            byte[] bytes = file.getBytes();
            String membersId = new String(bytes);
            String[] id = membersId.split(",");

            for (String x:id)
            {
                System.out.println(x);
                list.add(x);

            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println(FileName);
        return list.toString();
    }
}