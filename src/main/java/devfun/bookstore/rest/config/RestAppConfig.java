package devfun.bookstore.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"devfun.bookstore.rest.controller"}, useDefaultFilters = false, includeFilters = {@ComponentScan.Filter(Controller.class)})
public class RestAppConfig extends WebMvcConfigurerAdapter {
    // WebMvcCongifureAdapter를 상속받는다.
    // 새로운 HttpMessageConverter를 추가하려면 WebMvcConfigurerAdapter 클래스의 configureMesssageConverters를 사용한다.

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // mappingJackson2HttpMessageConverter를 추가한다.
        converters.add(mappingJackson2HttpMessageConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        return converter;
    }
}
