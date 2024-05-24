package com.uvejs.lb_m295_uvejs_hoti;

import com.uvejs.lb_m295_uvejs_hoti.repositories.BookRepository;
import com.uvejs.lb_m295_uvejs_hoti.services.BookController;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookControllerGetTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private static final String SERVICE_URL = "http://localhost:8080/lb295/controller/api";

    private String getBasicAuthHeader(String username, String password) {
        String credentials = username + ":" + password;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        return "Basic " + encodedCredentials;
    }

    @Test
    void testGetBookCount_Positive() throws IOException {
        // Das ist ein Test, der nicht in die DB geht, sondern nur die Methode testet.
        when(bookRepository.count()).thenReturn(10L);
        ResponseEntity<String> response = bookController.getBookCount();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Total number of books: 10", response.getBody());

        HttpGet request = new HttpGet(SERVICE_URL + "/book-amount");
        request.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthHeader("ADMIN", "1234"));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(request);
        assertEquals(HttpStatus.OK.value(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetBookCount_Negative() throws IOException {
        HttpGet request = new HttpGet(SERVICE_URL + "/ungültiger-path");
        request.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthHeader("ADMIN", "1234"));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(request);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetBookCount_Edge() throws IOException {
        HttpGet request = new HttpGet(SERVICE_URL + "/book-amount");
        request.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthHeader("ADMIN", "1234"));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(request);
        assertEquals(HttpStatus.OK.value(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetBookById_Positive() throws IOException {
        int bookId = 2;
        String serviceUrl = SERVICE_URL + "/" + bookId;
        HttpGet request = new HttpGet(serviceUrl);
        request.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthHeader("ADMIN", "1234"));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(request);

        assertEquals(HttpStatus.OK.value(), httpResponse.getStatusLine().getStatusCode());
    }



    @Test
    public void testGetBookById_Negative() throws IOException {
        int nonExistingBookId = 9999;
        String serviceUrl = SERVICE_URL + "/" + nonExistingBookId;
        HttpGet request = new HttpGet(serviceUrl);
        request.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthHeader("ADMIN", "1234"));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(request);

        assertEquals(HttpStatus.NOT_FOUND.value(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetAllBooks_Positive() throws IOException {
        String serviceUrl = SERVICE_URL + "/all";
        HttpGet request = new HttpGet(serviceUrl);
        request.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthHeader("ADMIN", "1234"));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(request);


        assertEquals(HttpStatus.OK.value(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetAllBooks_Negative() throws IOException {
        String serviceUrl = SERVICE_URL + "/ungültiger-path";
        HttpGet request = new HttpGet(serviceUrl);
        request.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthHeader("USER", "1234"));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetAllBooksByDate_Positive() throws IOException {
        String writtenDate = "1925-04-10";
        String serviceUrl = SERVICE_URL + "/all-by-date/" + writtenDate;
        HttpGet request = new HttpGet(serviceUrl);
        request.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthHeader("ADMIN", "1234"));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(request);
        assertEquals(HttpStatus.OK.value(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetAllBooksByDate_Negative() throws IOException {
        // Datum gibt es nicht
        String writtenDate = "2006-04-10";
        String serviceUrl = SERVICE_URL + "/all-by-date/" + writtenDate;
        HttpGet request = new HttpGet(serviceUrl);
        request.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthHeader("ADMIN", "1234"));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(request);
        assertEquals(HttpStatus.OK.value(), httpResponse.getStatusLine().getStatusCode());
        // Überprüfen, ob der Body leer ist
        HttpEntity entity = httpResponse.getEntity();
        String responseBody = EntityUtils.toString(entity);
        assertTrue(responseBody.isEmpty());
    }

    @Test
    public void testGetAllBooksByTitle_Positive() throws IOException {
        String title = "The%20Great%20Gatsby";
        String serviceUrl = SERVICE_URL + "/all-by-title/" + title;
        HttpGet request = new HttpGet(serviceUrl);
        request.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthHeader("ADMIN", "1234"));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(request);
        assertEquals(HttpStatus.OK.value(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetAllBooksByTitle_Negative() throws IOException {
        // Titel gibt es nicht
        String title = "Test";
        String serviceUrl = SERVICE_URL + "/all-by-title/" + title;
        HttpGet request = new HttpGet(serviceUrl);
        request.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthHeader("ADMIN", "1234"));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(request);
        assertEquals(HttpStatus.OK.value(), httpResponse.getStatusLine().getStatusCode());
        // Überprüfen, ob der Body leer ist
        HttpEntity entity = httpResponse.getEntity();
        String responseBody = EntityUtils.toString(entity);
        assertTrue(responseBody.isEmpty());
    }

    @Test
    void testBookExists_Positive() {
        // Buch existiert
        int existingBookNumber = 2;
        when(bookRepository.existsById(existingBookNumber)).thenReturn(true);
        ResponseEntity<?> response = bookController.bookExists(existingBookNumber);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    void testBookExists_Negative() {
        int nonExistingBookNumber = 456;
        when(bookRepository.existsById(nonExistingBookNumber)).thenReturn(false);
        ResponseEntity<?> response = bookController.bookExists(nonExistingBookNumber);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse((Boolean) response.getBody());
    }

    @Test
    void testBookExists_EdgeCase() {
        // invalide Buchnummer
        int invalidBookNumber = -1;
        ResponseEntity<?> response = bookController.bookExists(invalidBookNumber);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetBookById_EdgeCase() throws IOException {
        // invalide BookId
        int invalidBookId = -1;
        String serviceUrl = SERVICE_URL + "/" + invalidBookId;
        HttpGet request = new HttpGet(serviceUrl);
        request.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthHeader("ADMIN", "1234"));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(request);

        assertEquals(HttpStatus.BAD_REQUEST.value(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetAllBooks_EdgeCase() throws IOException {
        //leerer Path
        String serviceUrl = SERVICE_URL + "/";
        HttpGet request = new HttpGet(serviceUrl);
        request.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthHeader("ADMIN", "1234"));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(request);

        assertEquals(HttpStatus.NOT_FOUND.value(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetAllBooksByDate_EdgeCase_InvalidDate() throws IOException {
        // Ungültiges Datum
        String invalidDate = "invalid-date";
        String serviceUrl = SERVICE_URL + "/all-by-date/" + invalidDate;
        HttpGet request = new HttpGet(serviceUrl);
        request.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthHeader("ADMIN", "1234"));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(request);

        assertEquals(HttpStatus.BAD_REQUEST.value(), httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetAllBooksByTitle_EdgeCase_EmptyTitle() throws IOException {
        // Leerzeichen als Titel
        String emptyTitle = "";
        String serviceUrl = SERVICE_URL + "/all-by-title/" + emptyTitle;
        HttpGet request = new HttpGet(serviceUrl);
        request.setHeader(HttpHeaders.AUTHORIZATION, getBasicAuthHeader("ADMIN", "1234"));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(request);

        assertEquals(HttpStatus.NOT_FOUND.value(), httpResponse.getStatusLine().getStatusCode());
    }








}









