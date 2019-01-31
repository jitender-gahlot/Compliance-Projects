package sa.com.alrajhi.spring.webservice.alrajhiwebservice.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class RootrestController {

    @GetMapping("/rest/api/v1")
    public String sendRoot(){
        return "Hello World!";
    }
}
