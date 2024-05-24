package com.uvejs.lb_m295_uvejs_hoti.services;

import com.uvejs.lb_m295_uvejs_hoti.models.Book;
import com.uvejs.lb_m295_uvejs_hoti.repositories.BookRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;


@Path("/api")
@Component
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    private final Logger logger = LogManager.getLogger(BookController.class);

    // GET-Method to get Book by ID
    @GET
    @Path("/{number}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<?> getBookById(@PathParam("number") Integer number) {
        Book book = bookRepository.findById(number).orElse(null);
        if (book == null) {
            logger.warn("Book with number {} not found", number);
            return new ResponseEntity<>("Book with number " + number + " not found", HttpStatus.NOT_FOUND);
        }
        logger.info("Fetched Bokk with number: {}", number);
        logger.info("Book: {}", book.getBookDescription());
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // GET-Method to get all Books
    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<?> getAllBooks() {
        try {
            List<Book> books = bookRepository.findAll();
            logger.info("Fetched all books");
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Unexpected Exception: " + e.getMessage());
            return new ResponseEntity<>("Unexpected exception" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // GET-Method to filter all books by (written-)date
    @GET
    @Path("/all-by-date/{date}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<?> getAllBooksByDate(@PathParam("date") String writtenDate) {
        try {
            List<Book> books = bookRepository.findByWritten(LocalDate.parse(writtenDate));
            logger.info("Fetched books with date: " + writtenDate);
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e){
            logger.error("Unexpected exception with date: " + writtenDate);
            return new ResponseEntity<>("Unexpected exception: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET-Method to filter all books by title
    @GET
    @Path("/all-by-title/{title}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<?> getAllBooksByTitle(@PathParam("title") String title) {
        try {
            List<Book> filteredBooks = bookRepository.findByTitleContainingIgnoreCase(title);
            logger.info("Fetched books with title: " + title);
            return new ResponseEntity<>(filteredBooks, HttpStatus.OK);
        } catch (Exception e){
            logger.error("Unexpected exception with title: " + title);
            return new ResponseEntity<>("Unexpected exception: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // GET-Method to get amount of books in DB
    @GET
    @Path("/book-amount")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<String> getBookCount() {
        try {
            long count = bookRepository.count();
            logger.info("Total number of books: {}", count);
            return ResponseEntity.ok("Total number of books: " + count);
        } catch (Exception e) {
            logger.error("Failed to fetch book count: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch book count");
        }
    }

    // PUT-Method to update single book
    @PUT
    @Path("/{number}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @RolesAllowed("ADMIN")
    public Response updateBook(@PathParam("number") Integer number, Book updatedBook) {
        Book existingBook = bookRepository.findById(number).orElse(null);
        if (existingBook == null) {
            logger.warn("Book with number {} not found for update", number);
            throw new NotFoundException("Book with number " + number + " not found for update");
        }

        existingBook.setGenre(updatedBook.getGenre());
        existingBook.setBookDescription(updatedBook.getBookDescription());
        existingBook.setPrice(updatedBook.getPrice());
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setPublished(updatedBook.isPublished());
        existingBook.setWritten(updatedBook.getWritten());


        bookRepository.save(existingBook);
        logger.info("Updated book with number: {}", number);
        return Response.status(Response.Status.OK.getStatusCode()).build();
    }

    // GET-Method looks if a book exists by ID
    @GET
    @Path("/exists/{number}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<?> bookExists(@PathParam("number") int number) {
        try {
            bookRepository.existsById(number);
            logger.info("Book exists");
            return new ResponseEntity<>(bookRepository.existsById(number), HttpStatus.OK);
        } catch (Exception e){
            logger.error("Unexpected Exception with id: " + number);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<?> addBook(@Valid @RequestBody Book book) {
        try {
            bookRepository.save(book);
            logger.info("Added a new book: {}", book);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            logger.error("Unexpected Exception with book: " + book);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    @POST
    @Path("/books")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public Response addBooks(@Valid @RequestBody List<Book> books) {
        bookRepository.saveAll(books);
        logger.info("Added a new books: {}", books);
        return Response.status(Response.Status.CREATED.getStatusCode()).build();
    }


    @DELETE
    @Path("/{number}")
    @RolesAllowed("ADMIN")
    public Response deleteBook(@PathParam("number") Integer number) {
        if (bookRepository.existsById(number)) {
            bookRepository.deleteById(number);
            logger.info("Deleted book with number: {}", number);
            return Response.status(Response.Status.OK.getStatusCode()).build();
        } else {
            logger.warn("Book with number {} not found for deletion", number);
            throw new NotFoundException("Book with number " + number + " not found for deletion");
        }
    }

    @DELETE
    @RolesAllowed("ADMIN")
    public Response deleteBooks() {
        bookRepository.deleteAll();
        logger.info("Deleted all Books!");
        return Response.status(Response.Status.OK.getStatusCode()).build();
    }
}
