package com.bookshop.vct.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookshop.vct.entity.Oder;
import com.bookshop.vct.entity.OderItem;

@Repository
public interface OderItemRepository extends JpaRepository<OderItem, Integer> {

	public List<OderItem> findByOder(Oder oder);
	
	@Query(value="SELECT SUM(quantity) FROM oder_item o1 JOIN oder o2 ON o1.oder_id=o2.id WHERE MONTH(o2.oder_date)=?1",nativeQuery = true)
	public int sumByMonth(int month);
}
