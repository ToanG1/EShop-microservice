package com.sheppo.eshopproduct.User.controller;

import com.sheppo.eshopproduct.User.dto.Comment.Request.CreateCommentRequest;
import com.sheppo.eshopproduct.User.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("userCommentController")
@RequestMapping("api/product/user/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void comment(@RequestBody CreateCommentRequest request){
        commentService.comment(request);
    }
}
