package com.uvejs.lb_m295_uvejs_hoti;

import com.uvejs.lb_m295_uvejs_hoti.models.Book;
import com.uvejs.lb_m295_uvejs_hoti.repositories.BookRepository;
import com.uvejs.lb_m295_uvejs_hoti.services.BookController;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookControllerDeleteTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private static final String SERVICE_URL = "http://localhost:8080/lb295/controller/api";

    @Test
    public void testDeleteBook_Positive() {
        int existingBookId = 3;
        Response response = bookController.deleteBook(existingBookId);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDeleteBook_Negative() {
        int nonExistingBookId = 9999;
        assertThrows(NotFoundException.class, () -> {
            bookController.deleteBook(nonExistingBookId);
        });
    }






}









