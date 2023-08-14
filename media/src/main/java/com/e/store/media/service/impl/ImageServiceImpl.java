package com.e.store.media.service.impl;

import com.e.store.media.entity.Image;
import com.e.store.media.reposities.IImageRepository;
import com.e.store.media.service.IImageService;
import com.e.store.media.viewmodel.req.ImagesReqVm;
import com.e.store.media.viewmodel.res.ResVm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl implements IImageService {
    private final IImageRepository iImageRepository;

    @Autowired
    public ImageServiceImpl(IImageRepository iImageRepository) {
        this.iImageRepository = iImageRepository;
    }

    @Override
    public ResponseEntity<ResVm> uploadImage(ImagesReqVm imagesReqVm) {
        List<String> ids = new ArrayList<>();
        for(MultipartFile file: imagesReqVm.listImage()) {
            try {
                Image image = Image.builder().caption("").data(file.getBytes()).build();
                Image newImage = this.iImageRepository.save(image);
                ids.add(newImage.getId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        ResVm res = new ResVm("Upload Image success", ids);
        return ResponseEntity.status(201).body(res);
    }
}
