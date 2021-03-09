package com.bookshop.vct.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookshop.vct.entity.Oder;

@Repository
public interface OderRepository extends JpaRepository<Oder, Integer> {

}
