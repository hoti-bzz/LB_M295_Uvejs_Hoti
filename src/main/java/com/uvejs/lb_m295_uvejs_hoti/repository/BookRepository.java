package com.uvejs.lb_m295_uvejs_hoti.repository;

import com.uvejs.lb_m295_uvejs_hoti.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface BookRepository extends JpaRepository<Book, Integer> {
    // Sie können hier zusätzliche benutzerdefinierte Abfragemethoden hinzufügen
}
