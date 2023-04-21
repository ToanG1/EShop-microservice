package com.sheppo.eshopproduct.User.dto.Comment.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {
    private String username;
    private String content;
    private Integer rating;
}
