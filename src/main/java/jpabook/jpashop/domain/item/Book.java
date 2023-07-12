package jpabook.jpashop.domain.item;

import jpabook.jpashop.controller.BookForm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("B")
public class Book extends Item {

  private String author;
  private String isbn;

  public static Book craetBook(BookForm bookForm) {
   Book book = new Book();
   book.id = bookForm.getId();
   book.name = bookForm.getName();
   book.price = bookForm.getPrice();
   book.stockQuantity = bookForm.getStockQuantity();
   book.author = bookForm.getAuthor();
   book.isbn = bookForm.getIsbn();
   return book;
  }
}
