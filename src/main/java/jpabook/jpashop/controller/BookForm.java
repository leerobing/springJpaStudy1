package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookForm {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;


    public static BookForm creatBookForm(Book book) {
        BookForm bookForm = new BookForm();
        bookForm.id = book.getId();
        bookForm.name = book.getName();
        bookForm.price = book.getPrice();
        bookForm.stockQuantity = book.getStockQuantity();
        bookForm.author = book.getAuthor();
        bookForm.isbn = book.getIsbn();
        return bookForm;
    }
}
