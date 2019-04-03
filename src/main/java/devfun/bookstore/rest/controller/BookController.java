package devfun.bookstore.rest.controller;

import devfun.bookstore.common.domain.Book;
import devfun.bookstore.common.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    BookService bookService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getBook(@PathVariable("id") Long id){
        Book book = bookService.getBook(id);
        return String.format("결과값은 %s 입니다.", String.valueOf(book));
    }
}
