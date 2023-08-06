package com.e.store.product.services;

import com.e.store.product.viewmodel.req.ProductOptionCreateReqVm;
import com.e.store.product.viewmodel.res.ResVm;
import javax.xml.ws.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IProductOptionService {
    ResponseEntity<ResVm> createNewProductOption(ProductOptionCreateReqVm productOptionCreateReqVm);
}
