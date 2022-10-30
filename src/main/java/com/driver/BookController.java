package com.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {
    private List<Book> bookList;
    private int id;

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookController(){
        this.bookList = new ArrayList<Book>();
        this.id = 1;
    }


    // post request /create-book
    // pass book as request body
    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        // Your code goes here.

        book.setId(this.id);
        bookList.add(book);
        this.id++;
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // get request /get-book-by-id/{id}
    // pass id as path variable
    @GetMapping("/get-book-by-id/{id}")
     public ResponseEntity getBookById(@PathVariable("id") int id){

        for(int i=0; i<bookList.size(); i++){
            if(bookList.get(i).getId() == id){
                return new ResponseEntity<>(bookList.get(i), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Book Not Found", HttpStatus.BAD_GATEWAY);
    }

    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    @DeleteMapping("/delete-book-by-id/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable("id")int id){

        for(int i=0; i<bookList.size(); i++){
            if(bookList.get(i).getId() == id){
                bookList.remove(i);
                return new ResponseEntity<>("Success", HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Book Not Found", HttpStatus.BAD_REQUEST);
    }

    // get request /get-all-books
    @GetMapping("/get-all-books")
     public ResponseEntity<List<Book>> getAllBooks(){

        return new ResponseEntity<>(bookList, HttpStatus.OK);
     }

    // delete request /delete-all-books
    @DeleteMapping("/delete-all-books")
    public ResponseEntity<String> deleteAllBooks(){

        bookList = new ArrayList<>();
        this.id = 1;

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    // get request /get-books-by-author
    // pass author name as request param
    @GetMapping("/get-books-by-author")
    public ResponseEntity getBooksByAuthor(@RequestParam String author){
        List<Book> byAuthor = new ArrayList<>();

        for(Book b: bookList){
            if(b.getAuthor().equals(author)){
                byAuthor.add(b);
            }
        }

        if(!byAuthor.isEmpty()){
            return new ResponseEntity<>(byAuthor, HttpStatus.OK);
        }

        return new ResponseEntity<>("Author not found", HttpStatus.BAD_REQUEST);
    }

    // get request /get-books-by-genre
    // pass genre name as request param
    @GetMapping("/get-books-by-genre")
    public ResponseEntity getBooksByGenre(@RequestParam String genre){

        List<Book> byGenre = new ArrayList<>();

        for(Book b:bookList){
            if(b.getGenre().equals(genre)){
                byGenre.add(b);
                System.out.println(b);
            }
        }

        if(!byGenre.isEmpty()){
            return new ResponseEntity<>(byGenre, HttpStatus.OK);
        }

        return new ResponseEntity<>("Genre not found", HttpStatus.BAD_REQUEST);
    }
}
