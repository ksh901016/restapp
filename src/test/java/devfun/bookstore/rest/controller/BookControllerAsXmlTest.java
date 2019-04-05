package devfun.bookstore.rest.controller;

import devfun.bookstore.common.config.AppConfig;
import devfun.bookstore.rest.config.RestAppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class, RestAppConfig.class})
public class BookControllerAsXmlTest {
    Logger logger = LoggerFactory.getLogger(BookControllerAsXmlTest.class);

    @Autowired
    BookController bookController;
    @Autowired
    Jaxb2Marshaller jaxb2Marshaller;
    private MockMvc mockMvc;

    @Before
    public void initMockMvc(){
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).addFilter(filter).build();
    }

    @Test
    public void testGetBooks() throws Exception{
        MockHttpServletRequestBuilder requestBuilder
                = MockMvcRequestBuilders
                .get("/books")
                .accept(MediaType.APPLICATION_XML);

        this.mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
                .andExpect(xpath("/books/book").nodeCount(3))
                .andExpect(xpath("/books/book[1]/id").string("1"));
    }

    @Test
    public void testGetBook() throws Exception{
        MockHttpServletRequestBuilder requestBuilder
                = MockMvcRequestBuilders
                .get("/books/2")
                .accept(MediaType.APPLICATION_XML);

        this.mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
                .andExpect(xpath("/book/id").string("2"))
                .andExpect(xpath("/book/title").string("바라야 내전"))
                .andExpect(xpath("/book/creator").string("로이스 맥마스터 부졸드"));
    }

}
