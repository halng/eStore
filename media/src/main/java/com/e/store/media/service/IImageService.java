package com.e.store.media.service;

import com.e.store.media.viewmodel.req.ImagesReqVm;
import com.e.store.media.viewmodel.res.ResVm;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IImageService {
    ResponseEntity<ResVm> uploadImage(ImagesReqVm imagesReqVm);

}
