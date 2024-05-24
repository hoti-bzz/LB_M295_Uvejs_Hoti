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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookControllerCreateTest {

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
    public void testAddBook_Positive() {
        // Erstellen eines Buchobjekts für den Test
        Book newBook = new Book();
        // Setzen der erforderlichen Attribute des Buchobjekts

        // Aufruf der Methode addBook
        ResponseEntity<?> response = bookController.addBook(newBook);

        // Überprüfen, ob das Buch erfolgreich hinzugefügt wurde
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testAddBook_Negative() {
        // Erstellen eines ungültigen Buchobjekts, das zu einer Exception führen könnte
        Book invalidBook = new Book();
        // Setzen ungültiger Attribute des Buchobjekts

        // Aufruf der Methode addBook und Überprüfen, ob eine Fehlerantwort zurückgegeben wird
        ResponseEntity<?> response = bookController.addBook(invalidBook);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testAddBook_EdgeCase() {
        // Füllen der Datenbank mit maximal möglicher Anzahl von Büchern (sofern vorhanden)

        // Versuch, ein weiteres Buch hinzuzufügen
        Book newBook = new Book();
        // Setzen der erforderlichen Attribute des Buchobjekts

        // Aufruf der Methode addBook und Überprüfen, ob das Buch erfolgreich hinzugefügt wurde
        ResponseEntity<?> response = bookController.addBook(newBook);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testAddBooks_Positive() {
        // Erstellen einer Liste von Buchobjekten für den Test
        List<Book> bookList = new ArrayList<>();
        // Hinzufügen von gültigen Buchobjekten zur Liste

        // Aufruf der Methode addBooks
        Response response = bookController.addBooks(bookList);

        // Überprüfen, ob die Bücher erfolgreich hinzugefügt wurden
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testAddBooks_Negative() {
        // Erstellen einer Liste von Buchobjekten, die mindestens ein ungültiges Buch enthält
        List<Book> invalidBookList = new ArrayList<>();
        // Hinzufügen gültiger und ungültiger Buchobjekte zur Liste

        // Aufruf der Methode addBooks und Überprüfen, ob eine Fehlerantwort zurückgegeben wird
        Response response = bookController.addBooks(invalidBookList);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    public void testAddBooks_EdgeCase() {
        // Füllen der Datenbank mit der maximal möglichen Anzahl von Büchern (sofern vorhanden)

        // Erstellen einer Liste von Buchobjekten, die ein weiteres Buch hinzufügen, als die Datenbank aufnehmen kann
        List<Book> bookList = new ArrayList<>();
        // Hinzufügen von Buchobjekten zur Liste

        // Aufruf der Methode addBooks und Überprüfen, ob die Bücher erfolgreich hinzugefügt wurden
        Response response = bookController.addBooks(bookList);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }











}









