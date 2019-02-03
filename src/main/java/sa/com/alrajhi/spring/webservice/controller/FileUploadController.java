package sa.com.alrajhi.spring.webservice.controller;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileUploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleMapping(Model model, @RequestParam("fileupload") MultipartFile file) {
//        Model model;
        String FileName = file.getName();
        String x1 = null;
        List list = new ArrayList<String>();
//        try {
//            byte[] bytes = file.getBytes();
//            String membersId = new String(bytes);
//            String[] id = membersId.split(",");
//
//            for (String x : id) {
//                System.out.println(x);
//                list.add(x);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        System.out.println(FileName);

        try {

            Configuration conf = new Configuration();
            System.setProperty("sun.security.krb5.debug", "true");
            System.setProperty("java.security.krb5.conf", "D:/Development/GIT/Compliance-Projects/alrajhi-webservice/src/main/resources/security/krb5.conf");
            //System.setProperty("java.security.auth.login.config", "D:/Development/GIT/Compliance-Projects/alrajhi-webservice/src/main/resources/security/jaas.conf");
            System.setProperty("java.security.auth.login.config", "D:/Development/GIT/Compliance-Projects/alrajhi-webservice/src/main/resources/security/jaas.conf");
            //System.setProperty("java.security.krb5.conf", "D:/Development/GIT/Compliance-Projects/alrajhi-webservice/src/main/resources/security/krb5.conf");
            System.setProperty("hadoop.security.authentication", "Kerberos");
            //conf.set("javax.security.auth.useSubjectCredsOnly", "true");

            UserGroupInformation.setConfiguration(conf);

            Class.forName("com.cloudera.impala.jdbc41.Driver");
            Connection connection = null;
            String CONNECTION_URL = "jdbc:impala://quickstart.cloudera:21050;AuthMech=1;KrbHostFQDN=quickstart.cloudera;KrbServiceName=impala";
            connection = DriverManager.getConnection(CONNECTION_URL);
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("Select * from account order by id");
            while (results.next()) {

                list.add(results.getString("id"));
                list.add(results.getString("cic"));
                list.add(results.getString("number"));
            }
            System.out.println("Connection From IMPALA: " + connection);
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("list", list).toString();
        model.addAttribute("name", "Jitender");
        return "icsr1221";
    }
}