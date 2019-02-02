package sa.com.alrajhi.spring.webservice.alrajhiwebservice.controller;

import org.apache.hadoop.conf.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.hadoop.security.UserGroupInformation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FileUploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleMapping(@RequestParam("fileupload") MultipartFile file) {
        String FileName = file.getName();
        String x1 = null;
        List list = new ArrayList<String>();
        try {
            byte[] bytes = file.getBytes();
            String membersId = new String(bytes);
            String[] id = membersId.split(",");

            for (String x : id) {
                System.out.println(x);
                list.add(x);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(FileName);

        try {

            Configuration conf = new Configuration();
            System.setProperty("sun.security.krb5.debug", "true");
            System.setProperty("java.security.krb5.conf", "D:/Development/GIT/Compliance-Projects/alrajhi-webservice/src/main/resources/security/krb5.conf");
            System.setProperty("java.security.auth.login.config","D:/Development/GIT/Compliance-Projects/alrajhi-webservice/src/main/resources/security/jaas.conf");
            System.setProperty("java.security.auth.login.config","D:/Development/GIT/Compliance-Projects/alrajhi-webservice/src/main/resources/security/jaas.conf");
            System.setProperty("java.security.krb5.conf", "D:/Development/GIT/Compliance-Projects/alrajhi-webservice/src/main/resources/security/krb5.conf");
            System.setProperty("hadoop.security.authentication", "Kerberos");
            //conf.set("javax.security.auth.useSubjectCredsOnly", "true");
            System.out.println("Here");

            System.out.println("Here1");

            System.out.println("Here2");
            UserGroupInformation.setConfiguration(conf);
            System.out.println("Here3");

//            UserGroupInformation.loginUserFromKeytab("jeet/quickstart.cloudera@CLOUDERA","/Development/GIT/Compliance-Projects/src/main/resources/security/jeet.keytab");

            Class.forName("com.cloudera.impala.jdbc41.Driver");
            Connection connection = null;
            //Class.forName(DRIVER_CLASS);
            String CONNECTION_URL = "jdbc:impala://quickstart.cloudera:21050;AuthMech=1;KrbHostFQDN=quickstart.cloudera;KrbServiceName=impala";
            connection = DriverManager.getConnection(CONNECTION_URL);
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("Select * from customers");
            if(results.next()){

                System.out.println(results.getString("id"));
            }
            System.out.println("Connection From IMPALA: " + connection);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return list.toString();
    }
}