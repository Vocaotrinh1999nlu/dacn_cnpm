package com.bookshop.vct.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookshop.vct.entity.Oder;
import com.bookshop.vct.entity.OderItem;

@Repository
public interface OderItemRepository extends JpaRepository<OderItem, Integer> {

	public List<OderItem> findByOder(Oder oder);
}
