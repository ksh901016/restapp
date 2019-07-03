package devfun.bookstore.rest;

import devfun.bookstore.common.domain.Book;
import devfun.bookstore.rest.controller.BookController;
import devfun.bookstore.rest.domain.BookResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class BookResourceAssembler extends ResourceAssemblerSupport<Book, BookResource> {
    public BookResourceAssembler() {
        super(BookController.class, BookResource.class);
    }

    @Override
    public BookResource toResource(Book book) {
        BookResource resource = createResourceWithId(book.getId(), book);
        resource.setBoookId(book.getId());
        resource.setTitle(book.getTitle());
        resource.setCreator(book.getCreator());
        resource.setDate(book.getDate());
        resource.setType(book.getType());
        return resource;
    }
}
