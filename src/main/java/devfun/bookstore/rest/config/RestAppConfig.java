package devfun.bookstore.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
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
        converters.add(marshallingHttpMessageConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        return converter;
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller(){
        // XML 처리를 위한 JAXB Marshaller/Unmarshaller
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan(new String[]{
                "devfun.bookstore.common.domain",
                "devfun.bookstore.rest.domain"
        });
        return jaxb2Marshaller;
    }

    @Bean
    public MarshallingHttpMessageConverter marshallingHttpMessageConverter(){
        MarshallingHttpMessageConverter converter = new MarshallingHttpMessageConverter();
        converter.setMarshaller(jaxb2Marshaller());
        converter.setUnmarshaller(jaxb2Marshaller());
        return converter;
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        //configurer.defaultContentType(MediaType.APPLICATION_JSON);
        configurer.useJaf(true)
                .favorPathExtension(true) // 요청 경로에 파일 확장자가 포함되어 있다면 정의한 mediaTypes 정보를 사용, 적절한 미디어 타입을 찾지 못하였을 때 useJaf(true)하면 Java Activation Framework의 FileTypeMap.getContentType()값을 미디어 타입으로 사용한다.
                .favorParameter(false)    // 요청 파라미터에 미디어 타입을 정의하는 값이 포함되어 있따면 정의한 mediaTypes 정보를 사용(파라미터의 기본 이름은 'format'이고, parameterName이라는 속성으로 변경할 수 있다.)
                .ignoreAcceptHeader(false) // 이전 과정까지 못찾았다면 값이 false이면 HTTP Header값의 Accept를 사용한다.
                .defaultContentType(MediaType.APPLICATION_JSON) // 이전 과정에서도 못찾았다면 defaultContentType에 정의된 값 사용
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML);
    }
}
