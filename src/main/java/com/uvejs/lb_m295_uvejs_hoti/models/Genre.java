package com.uvejs.lb_m295_uvejs_hoti.models;

import jakarta.persistence.*;

@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_genre")
    private Integer idgenre;

    @Column(name = "name")
    private String name;

    public Genre(Integer idgenre, String name) {
        this.idgenre = idgenre;
        this.name = name;
    }

    public Genre() {

    }

    public Integer getIdgenre() {
        return idgenre;
    }

    public void setIdgenre(Integer idgenre) {
        this.idgenre = idgenre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
