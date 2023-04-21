package com.sheppo.eshopproduct.User.service;

import com.sheppo.eshopproduct.User.dto.Comment.Request.CreateCommentRequest;
import com.sheppo.eshopproduct.model.Comment;
import com.sheppo.eshopproduct.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("userCommentService")
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    public void comment(CreateCommentRequest request) {
        commentRepository.save(Comment.builder()
                .username(request.getUsername())
                .content(request.getContent())
                .rating(request.getRating())
                .build());
    }
}
