package com.uvejs.lb_m295_uvejs_hoti.services;

import com.uvejs.lb_m295_uvejs_hoti.models.Book;
import com.uvejs.lb_m295_uvejs_hoti.repository.BookRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/api")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    private final Logger logger = LogManager.getLogger(BookController.class);

    @GET
    @Path("/{number}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Book getModul(@PathParam("number") Integer number) {
        Book book = bookRepository.findById(number).orElse(null);
        if (book == null) {
            logger.warn("Course with number {} not found", number);
            throw new NotFoundException("Course with number " + number + " not found");
        }
        logger.info("Fetched course with number: {}", number);
        return book;
    }


}
