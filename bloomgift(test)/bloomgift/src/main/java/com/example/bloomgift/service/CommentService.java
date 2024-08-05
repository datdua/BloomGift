package com.example.bloomgift.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bloomgift.model.Account;
import com.example.bloomgift.model.Comment;
import com.example.bloomgift.model.Product;
import com.example.bloomgift.repository.AccountRepository;
import com.example.bloomgift.repository.CommentRepository;
import com.example.bloomgift.repository.ProductRepository;
import com.example.bloomgift.request.CommentCreateRequest;

import jakarta.transaction.Transactional;
@Service
public class CommentService {
     @Autowired
    private CommentRepository commentRepository;

   
    @Autowired
    private AccountRepository accountRepository;
   

    @Autowired
    private ProductRepository productRepository;


    public List<Comment> getCommentsByUserId(Integer userId) {
        return commentRepository.findByUserID_Id(userId);
    }


    public List<Comment> getCommentsByProductId(Integer productId){
        return commentRepository.findByProductID_Id(productId);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

     public Comment createComment(CommentCreateRequest commentCreateDTO) {
        // Validate if the user exists
        Optional<Account> user = accountRepository.findById(commentCreateDTO.getUserId());
        if (!user.isPresent()) {
            throw new IllegalArgumentException("User with ID " + commentCreateDTO.getUserId() + " does not exist.");
        }

        // Validate if the product exists
        Optional<Product> product = productRepository.findById(commentCreateDTO.getProductId());
        if (!product.isPresent()) {
            throw new IllegalArgumentException("Product with ID " + commentCreateDTO.getProductId() + " does not exist.");
        }

        // Create the comment
        Comment comment = new Comment();
        comment.setProductID(product.get()); // Set the product entity
        comment.setUserID(user.get()); // Set the user entity
        comment.setContext(commentCreateDTO.getContext());
        comment.setCommentDate(new Date());
        comment.setRate(commentCreateDTO.getRate()); // Set the integer rating
        comment.setStatus(true);

        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteCommentById(Integer id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            commentRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Comment with ID " + id + " not found.");
        }
    }


    public void deleteCommentByUser(Integer id, Integer userId) {
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isPresent()) {
            Comment comment = commentOpt.get();
            if (comment.getUserID().getId().equals(userId)) {
                commentRepository.deleteById(id);
            } else {
                throw new IllegalArgumentException("User does not have permission to delete this comment.");
            }
        } else {
            throw new IllegalArgumentException("Comment not found.");
        }
    }


    public Comment updateReportStatus(Integer commentId, boolean newStatus) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isPresent()) {
            Comment comment = commentOpt.get();
            comment.setReport(newStatus);
            return commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException("Comment not found.");
        }
    }
    
    public List<Comment> getCommentsWithReportFalse() {
        return commentRepository.findByReportFalse();
    }

    public Comment updateStatus(Integer commentId, boolean newStatus) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isPresent()) {
            Comment comment = commentOpt.get();
            comment.setStatus(newStatus);
            return commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException("Comment not found.");
        }
    }
}
