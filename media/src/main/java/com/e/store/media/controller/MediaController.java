package com.e.store.media.controller;

import com.e.store.media.service.IImageService;
import com.e.store.media.viewmodel.res.ResVm;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/media")
public class MediaController {

  private final IImageService iImageService;

  @Autowired
  public MediaController(IImageService iImageService) {
    this.iImageService = iImageService;
  }

  /**
   * Upload multiple images to AWS S3 bucket and get image key, then send to client
   * @param files: image files to upload
   * @param caption: required caption for image => folder name on aws s3
   * @return List of image key
   */
  @PostMapping(
      path = "images",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<ResVm> uploadMultiImage(
      @RequestParam("files") List<MultipartFile> files,
      @NotBlank @RequestParam(value = "caption") String caption) {
    return this.iImageService.uploadImage(files, caption);
  }
}
