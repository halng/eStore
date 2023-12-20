package com.e.store.media.service.impl;

import com.e.store.media.entity.Image;
import com.e.store.media.exception.EntityNotFoundException;
import com.e.store.media.reposities.IImageRepository;
import com.e.store.media.service.IImageService;
import com.e.store.media.viewmodel.req.ImagesReqVm;
import com.e.store.media.viewmodel.res.ImagesResVm;
import com.e.store.media.viewmodel.res.ResVm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class ImageServiceImpl implements IImageService {

    private static final Logger LOG = LoggerFactory.getLogger(ImageServiceImpl.class);
    private final IImageRepository iImageRepository;

    @Autowired
    public ImageServiceImpl(IImageRepository iImageRepository) {
        this.iImageRepository = iImageRepository;
    }

    @Override
    public ResponseEntity<ResVm> uploadImage(List<MultipartFile> files, String caption) {
        List<String> ids = new ArrayList<>();
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        LOG.info(
            String.format(
                "Receive request upload image from %s - total %d file(s) - caption: %s",
                user, files.size(), caption));

        for (MultipartFile file : files) {
            try {
                String finalCaption = caption + "_" + file.getOriginalFilename();
                Image image =
                    Image.builder()
                        .caption(finalCaption)
                        .data(file.getBytes())
                        .fileType(file.getContentType())
                        .uploadBy(user)
                        .build();

                LOG.debug("Saving image have caption: " + finalCaption);
                Image newImage = this.iImageRepository.save(image);
                ids.add(newImage.getId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        LOG.info("Upload images successfully.");
        ResVm res = new ResVm("Upload Image success", ids);
        return ResponseEntity.status(201).body(res);
    }

    @Override
    public ResponseEntity<List<ImagesResVm>> getImages(ImagesReqVm imagesReqVm) {
        LOG.info("Receive request to get image info for ids: " + imagesReqVm.imageIds().toString());

        List<ImagesResVm> images = new ArrayList<>();
        List<Image> imageList = this.iImageRepository.findAllById(imagesReqVm.imageIds());

        for (Image img : imageList) {
            String downloadUrl =
                ServletUriComponentsBuilder.fromHttpUrl("http://localhost:9090/api/v1/media")
                    .path("/image/")
                    .path(img.getId())
                    .toUriString();

            images.add(
                new ImagesResVm(
                    img.getCaption(), img.getFileType(), downloadUrl, (long) img.getData().length));
        }

        return ResponseEntity.status(200).body(images);
    }

    @Override
    public ResponseEntity<byte[]> getImage(String id) {
        LOG.info("Receive request to get image for id: " + id);
        Image image =
            this.iImageRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Id: %s not found".formatted(id)));
        return ResponseEntity.status(200).body(image.getData());
    }
}
