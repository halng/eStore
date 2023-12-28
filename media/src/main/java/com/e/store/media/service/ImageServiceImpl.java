package com.e.store.media.service;

import com.e.store.media.viewmodel.res.ResVm;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl implements IImageService {

  private static final Logger LOG = LoggerFactory.getLogger(ImageServiceImpl.class);
  private final GCPBucketUtils gcpBucketUtils;

  @Autowired
  public ImageServiceImpl(GCPBucketUtils g) {
    this.gcpBucketUtils = g;
  }

  @Override
  public ResponseEntity<ResVm> uploadImage(List<MultipartFile> files, String caption) {
    List<String> urls = new ArrayList<>();
    String user = SecurityContextHolder.getContext().getAuthentication().getName();
    LOG.info(
        String.format(
            "Receive request upload image from %s - total %d file(s) - caption: %s",
            user, files.size(), caption));

    for (MultipartFile file : files) {
      String finalCaption = UUID.randomUUID().toString();
      String url = gcpBucketUtils.uploadFile(file, finalCaption, file.getContentType());
      urls.add(url);
    }

    ResVm res = new ResVm("Upload Image success", urls);
    return ResponseEntity.status(201).body(res);
  }
}
