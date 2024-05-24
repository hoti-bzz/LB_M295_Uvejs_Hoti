package com.uvejs.lb_m295_uvejs_hoti.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_book")
    private int idBook;

    @Column(name = "bookdescription")
    private String bookDescription;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "written", precision = 10, scale = 2)
    private LocalDate written;

    @Column(name = "published")
    private boolean published;

    @ManyToOne
    @JoinColumn(name = "id_genre", nullable = false)
    private Genre genre;


    public Book(Integer idBook, String bookDescription, String title, BigDecimal price, LocalDate written, boolean published, Genre genre) {
        this.idBook = idBook;
        this.bookDescription = bookDescription;
        this.title = title;
        this.price = price;
        this.written = written;
        this.published = published;
        this.genre = genre;
    }

    public Book(){
        
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getWritten() {
        return written;
    }

    public void setWritten(LocalDate written) {
        this.written = written;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}



