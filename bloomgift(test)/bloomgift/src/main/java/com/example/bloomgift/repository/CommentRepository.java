package com.example.bloomgift.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.bloomgift.model.Comment;


public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByUserID_Id(Integer userId);
    
    List<Comment> findByProductID_Id(Integer productId);
    

    List<Comment> findByReportFalse();

   @Modifying
    @Query("UPDATE Comment c SET c.status = :status WHERE c.report = false")
    void updateStatusWhereReportFalse(boolean status);


}   
