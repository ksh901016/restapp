package devfun.bookstore.common.mapper;

import devfun.bookstore.common.config.AppConfig;
import devfun.bookstore.common.domain.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfig.class})
public class BookMapperTest {
    Logger logger = LoggerFactory.getLogger(BookMapperTest.class);

    @Autowired
    BookMapper bookMapper;

    @Test
    public void testBookMapper(){
        List<Book> books = bookMapper.select();
        assertEquals(3, books.size());
    }
}
