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
import java.util.ArrayList;
import java.util.List;

@RestController
public class FileUploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.ALL_VALUE)
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
            conf.set("hadoop.security.authentication", "Kerberos");
            //conf.set("javax.security.auth.useSubjectCredsOnly", "true");
            conf.set("java.security.krb5.conf", "D:\\Development\\GIT\\Compliance-Projects\\alrajhi-webservice\\src\\main\\resources\\security\\krb5.conf");
            conf.set("java.security.auth.login.config","D:\\Development\\GIT\\Compliance-Projects\\alrajhi-webservice\\src\\main\\resources\\security\\jaas.conf");

            UserGroupInformation.setConfiguration(conf);
            UserGroupInformation.loginUserFromKeytab("jeet/quickstart.cloudera@CLOUDERA","D:\\Development\\GIT\\Compliance-Projects\\alrajhi-webservice\\src\\main\\resources\\security\\jeet.keytab");

//            UserGroupInformation.loginUserFromKeytab("jeet/quickstart.cloudera@CLOUDERA","/Development/GIT/Compliance-Projects/src/main/resources/security/jeet.keytab");



            

            Class.forName("com.cloudera.impala.jdbc41.Driver");
            Connection connection = null;
            //Class.forName(DRIVER_CLASS);
            String CONNECTION_URL = "jdbc:impala://quickstart.cloudera:21050;AuthMech=1;KrbRealm=CLOUDERA;KrbHostFQDN=quickstart.cloudera;KrbServiceName=impala";
            connection = DriverManager.getConnection(CONNECTION_URL);
            System.out.println("Connection From IMPALA: " + connection);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return list.toString();
    }
}