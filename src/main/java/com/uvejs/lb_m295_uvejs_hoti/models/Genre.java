package com.uvejs.lb_m295_uvejs_hoti.models;

import jakarta.persistence.*;

@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_genre")
    private int idGenre;

    @Column(name = "genrename")
    private String name;



    public Genre() {

    }

    public Genre(int idGenre, String name) {
        this.idGenre = idGenre;
        this.name = name;
    }

    public int getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(int idGenre) {
        this.idGenre = idGenre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
