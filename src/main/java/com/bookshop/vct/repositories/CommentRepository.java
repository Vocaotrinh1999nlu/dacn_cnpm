package com.bookshop.vct.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookshop.vct.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
