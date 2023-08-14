package com.e.store.media.controller;

import com.e.store.media.service.IImageService;
import com.e.store.media.viewmodel.req.BlogReqVm;
import com.e.store.media.viewmodel.req.ImagesReqVm;
import com.e.store.media.viewmodel.res.ResVm;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/media")
public class MediaController {
    private IImageService iImageService;

    @Autowired
    public MediaController(IImageService iImageService) {
        this.iImageService = iImageService;
    }


    @PostMapping(path = "images", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResVm> uploadMultiImage(@ModelAttribute ImagesReqVm imagesReqVm) {
        return this.iImageService.uploadImage(imagesReqVm);
    }

    @PostMapping("blog")
    void createBlog(@RequestBody BlogReqVm blogReqVm) {

    }

    @GetMapping("images")
    void getImages(@RequestParam("imageIds")List<String> imageIds) {

    }

    @GetMapping("blog")
    void getBlog(@NotBlank @RequestParam("blogId") String blogId){

    }

}
