package hhertout.api.controller;

import hhertout.api.entity.Book;
import hhertout.api.repository.BookRepository;
import hhertout.api.utils.CustomErrorResponse;
import hhertout.api.utils.CustomSuccessResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    private BookRepository repository;

    @GetMapping("/api/book/{id}")
    public ResponseEntity<?> getOneBook(@PathVariable Long id) {
        Optional<Book> book = repository.findById(id);
        if (book.isPresent()) {
            return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            CustomErrorResponse error = new CustomErrorResponse("This book doesn't exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/api/books")
    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    @PostMapping("/api/book/new")
    public ResponseEntity<?> saveBook(@Valid @RequestBody Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            CustomErrorResponse error = new CustomErrorResponse("Invalid request : ");
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                error.setMessage(error.getMessage() + fieldError);
            }
            return ResponseEntity.badRequest().body(error);
        }

        Book newBook = repository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }

    @PutMapping("/api/book/update/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            CustomErrorResponse error = new CustomErrorResponse("Invalid request : ");
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                error.setMessage(error.getMessage() + fieldError);
            }
            return ResponseEntity.badRequest().body(error);
        } else {
            Optional<Book> bookToDelete = repository.findById(id);

            if (bookToDelete.isPresent()) {
                book.setId(id);
                Book savedBook = repository.save(book);
                return ResponseEntity.status(HttpStatus.OK).body(savedBook);
            } else {
                CustomErrorResponse error = new CustomErrorResponse("This book doesn't exist");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
        }
    }

    @DeleteMapping("/api/book/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        Optional<Book> bookToDelete = repository.findById(id);

        if (bookToDelete.isPresent()) {
            repository.deleteById(id);
            CustomSuccessResponse response = new CustomSuccessResponse("Book deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            CustomErrorResponse error = new CustomErrorResponse("This book doesn't exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
