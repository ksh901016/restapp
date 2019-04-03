package devfun.bookstore.rest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"devfun.bookstore.rest.controller"}, useDefaultFilters = false, includeFilters = {@ComponentScan.Filter(Controller.class)})
public class RestAppConfig {
    
}
