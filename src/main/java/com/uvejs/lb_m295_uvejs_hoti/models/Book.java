package com.uvejs.lb_m295_uvejs_hoti.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;

import java.time.LocalDate;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_book")
    private Integer idbook;

    @Column(name = "description")
    private String description;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    @DecimalMin(value = "0.01", message = "Cost must be at least 0.01.")
    private float price;

    @Column(name = "written")
    private LocalDate written;

    @Column(name = "published")
    private boolean published;

    @ManyToOne
    @JoinColumn(name = "id_genre", nullable = false)
    private Genre genre;



}

