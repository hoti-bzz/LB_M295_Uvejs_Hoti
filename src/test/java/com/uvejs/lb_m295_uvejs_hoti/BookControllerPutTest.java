package com.uvejs.lb_m295_uvejs_hoti;

import com.uvejs.lb_m295_uvejs_hoti.configs.TestConfig;
import com.uvejs.lb_m295_uvejs_hoti.models.Book;
import com.uvejs.lb_m295_uvejs_hoti.repositories.BookRepository;
import com.uvejs.lb_m295_uvejs_hoti.services.BookController;
import jakarta.ws.rs.core.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookControllerPutTest {

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
    void testUpdateBook_Positive() {
        // Buch existiert
        int existingBookNumber = 3;
        Book existingBook = new Book();
        existingBook.setIdBook(existingBookNumber);
        when(bookRepository.findById(existingBookNumber)).thenReturn(Optional.of(existingBook));
        Book updatedBook = new Book();
        updatedBook.setTitle("Test");
        updatedBook.setBookDescription("Test description");
        Response response = bookController.updateBook(existingBookNumber, updatedBook);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testUpdateBook_EdgeCase() {
        // Buch existiert
        int existingBookNumber = 3;
        Book existingBook = new Book();
        existingBook.setIdBook(existingBookNumber);
        when(bookRepository.findById(existingBookNumber)).thenReturn(Optional.of(existingBook));
        // versuche mit Null-Daten zu aktualisieren
        Response response = bookController.updateBook(existingBookNumber, null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testUpdateCourse() throws IOException {
        // Buch existiert nicht
        HttpPut request = new HttpPut(TestConfig.SERVICE_URL + "/" + 2200);
        String json = "{\"idBook\":1,\"bookDescription\":\"Eine fesselnde Geschichte über...\",\"title\":\"Der Große Gatsby\",\"price\":19.99,\"written\":\"1925-04-10\",\"published\":true,\"genre\":{\"idGenre\":1,\"name\":\"Roman\"}}";
        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);
        request.setHeader("Content-type", "application/json");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        assertEquals(500, statusCode);
    }




}









