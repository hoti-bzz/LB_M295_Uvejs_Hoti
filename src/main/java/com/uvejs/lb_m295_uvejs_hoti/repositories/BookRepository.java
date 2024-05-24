package com.uvejs.lb_m295_uvejs_hoti.repositories;

import com.uvejs.lb_m295_uvejs_hoti.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface BookRepository extends JpaRepository<Book, Integer> {
    // Sie können hier zusätzliche benutzerdefinierte Abfragemethoden hinzufügen
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByWritten(LocalDate written);
}
