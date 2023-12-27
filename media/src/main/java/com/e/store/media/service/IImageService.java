package com.e.store.media.service;

import com.e.store.media.viewmodel.req.ImagesReqVm;
import com.e.store.media.viewmodel.res.ImagesResVm;
import com.e.store.media.viewmodel.res.ResVm;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IImageService {

  ResponseEntity<ResVm> uploadImage(List<MultipartFile> files, String caption);

  ResponseEntity<List<ImagesResVm>> getImages(ImagesReqVm imagesReqVm);

  ResponseEntity<byte[]> getImage(String id);
}
