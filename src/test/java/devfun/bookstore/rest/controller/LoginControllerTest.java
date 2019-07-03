package devfun.bookstore.rest.controller;

import devfun.bookstore.common.config.AppConfig;
import devfun.bookstore.rest.config.RestAppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class, RestAppConfig.class})
public class LoginControllerTest {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    private MockMvc mockMvc;
    @Autowired
    LoginController loginController;

    @Before
    public void initMockMvc(){
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).addFilter(filter).build();
    }

    @Test
    public void testLogin() throws Exception{
        MockHttpServletRequestBuilder requestBuilder
                = MockMvcRequestBuilders
                .get("/login");

        this.mockMvc.perform(requestBuilder).andDo(print());
    }
}
