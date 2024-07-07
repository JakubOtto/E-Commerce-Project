package pl.otto.ecommerce.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController {
    @GetMapping("/hello-world")
    String helloWorld() {
        var name = "Jakub";
        return String.format("Hello %s",name);
    }
}
