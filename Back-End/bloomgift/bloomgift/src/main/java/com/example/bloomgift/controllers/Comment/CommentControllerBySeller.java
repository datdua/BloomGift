package com.example.bloomgift.controllers.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloomgift.service.CommentService;

@RestController
@RequestMapping("/api/seller/comments/comment-management")
public class CommentControllerBySeller {
    @Autowired
    private CommentService commentService;

    @PostMapping("/report-comment")
    public ResponseEntity<String> reportComment(@RequestParam Integer commentID,
                                                @RequestParam Integer storeID,
                                                @RequestParam Boolean report,
                                                @RequestParam String reason) {
        try {
            commentService.report(commentID, storeID, report, reason);
            return ResponseEntity.ok("Report status updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error reporting comment: " + e.getMessage());
        }
    }
}
