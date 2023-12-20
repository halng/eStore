package com.e.store.media.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.e.store.media.entity.Image;
import com.e.store.media.exception.EntityNotFoundException;
import com.e.store.media.reposities.IImageRepository;
import com.e.store.media.service.impl.ImageServiceImpl;
import com.e.store.media.viewmodel.req.ImagesReqVm;
import com.e.store.media.viewmodel.res.ImagesResVm;
import com.e.store.media.viewmodel.res.ResVm;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

public class ImageServiceTest {

    IImageRepository iImageRepository;
    IImageService iImageService;
    MockMultipartFile multipartFile1;
    List<MultipartFile> multipartFiles;

    @BeforeEach
    void setUp() {
        this.iImageRepository = mock(IImageRepository.class);
        iImageService = new ImageServiceImpl(iImageRepository);
        Authentication authentication =
            new Authentication() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return null;
                }

                @Override
                public Object getCredentials() {
                    return null;
                }

                @Override
                public Object getDetails() {
                    return null;
                }

                @Override
                public Object getPrincipal() {
                    return null;
                }

                @Override
                public boolean isAuthenticated() {
                    return false;
                }

                @Override
                public void setAuthenticated(boolean isAuthenticated)
                    throws IllegalArgumentException {
                }

                @Override
                public String getName() {
                    return "seller";
                }
            };
        SecurityContextHolder.getContext().setAuthentication(authentication);

        multipartFile1 =
            new MockMultipartFile("test", "test.jpg", MediaType.IMAGE_JPEG_VALUE,
                "test".getBytes());
        multipartFiles = List.of(multipartFile1);
    }

    @Test
    void uploadImage() {
        Image expectedImage1 = Image.builder().id("xxx-111").build();
        when(this.iImageRepository.save(any())).thenReturn(expectedImage1);

        ResponseEntity<ResVm> response = this.iImageService.uploadImage(multipartFiles, "hello");

        Assertions.assertEquals(201, response.getStatusCode().value());
        Assertions.assertNotNull(response.getBody().itemIds());
        Assertions.assertEquals("xxx-111", response.getBody().itemIds().get(0));
    }

    @Test
    void getImages() {
        ImagesReqVm imagesReqVm = new ImagesReqVm(Arrays.asList("xxx", "yyy"));
        Image expectedImage1 =
            Image.builder()
                .id("xxx")
                .caption("x-caption")
                .data("x".getBytes())
                .fileType("jpeg")
                .build();
        Image expectedImage2 =
            Image.builder()
                .id("yyy")
                .caption("y-caption")
                .data("y".getBytes())
                .fileType("jpeg")
                .build();

        when(this.iImageRepository.findAllById(imagesReqVm.imageIds()))
            .thenReturn(Arrays.asList(expectedImage1, expectedImage2));

        ResponseEntity<List<ImagesResVm>> response = this.iImageService.getImages(imagesReqVm);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatusCode().value());

        List<ImagesResVm> resVms = response.getBody();
        Assertions.assertNotNull(resVms);

        Assertions.assertEquals(expectedImage1.getCaption(), resVms.get(0).caption());
        Assertions.assertEquals(expectedImage1.getFileType(), resVms.get(0).fileType());
        Assertions.assertEquals(expectedImage1.getData().length, resVms.get(0).size());

        Assertions.assertEquals(expectedImage2.getCaption(), resVms.get(1).caption());
        Assertions.assertEquals(expectedImage2.getFileType(), resVms.get(1).fileType());
        Assertions.assertEquals(expectedImage2.getData().length, resVms.get(1).size());
    }

    @Test
    void setImage_shouldThrowException_whenIdInvalid() {
        EntityNotFoundException exception =
            Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> {
                    this.iImageService.getImage("xxx");
                });

        Assertions.assertEquals("Id: xxx not found", exception.getMessage());
    }

    @Test
    void setImage_shouldThrowException_whenIdValid() {
        Image expectedImage =
            Image.builder()
                .id("yyy")
                .caption("y-caption")
                .data("y".getBytes())
                .fileType("jpeg")
                .build();

        when(iImageRepository.findById("yyy")).thenReturn(Optional.of(expectedImage));

        ResponseEntity<byte[]> response = this.iImageService.getImage("yyy");

        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("y".getBytes().length, response.getBody().length);
    }
}
