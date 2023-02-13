package com.sheppo.eshopproduct.Admin.controller;

import com.sheppo.eshopproduct.Admin.dto.Style.Request.FindStyleRequest;
import com.sheppo.eshopproduct.Admin.dto.Style.Request.CreateStyleRequest;
import com.sheppo.eshopproduct.Admin.dto.Style.Response.StyleResponse;
import com.sheppo.eshopproduct.Admin.service.StyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminStyleController")
@RequestMapping("api/admin/style")
@RequiredArgsConstructor
public class StyleController {

    public final StyleService styleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createStyle(@RequestBody CreateStyleRequest createStyleRequest) {
        styleService.createStyle(createStyleRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public StyleResponse findAll(@RequestBody FindStyleRequest findStyleRequest) {
        return styleService.findAll(findStyleRequest);
    }
}
