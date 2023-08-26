package com.e.store.product.services;

import com.e.store.product.viewmodel.req.ProductOptionCreateReqVm;
import com.e.store.product.viewmodel.res.ListProductOptionResVm;
import com.e.store.product.viewmodel.res.ResVm;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IProductOptionService {
    ResponseEntity<ResVm> createNewProductOption(ProductOptionCreateReqVm productOptionCreateReqVm);
    ResponseEntity<ListProductOptionResVm> getAllOption(int page);
    ResponseEntity<ResVm> updateOption(String optionId, ProductOptionCreateReqVm req);
    ResponseEntity<ResVm> deleteOption(String optionId);

}
