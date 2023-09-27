package com.e.store.product.services;

import com.e.store.product.viewmodel.req.ProductAttributeCreateReqVm;
import com.e.store.product.viewmodel.res.CommonProductResVm;
import com.e.store.product.viewmodel.res.ListProductAttributeResVm;
import com.e.store.product.viewmodel.res.ResVm;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IProductAttributeService {
    ResponseEntity<ResVm> createNewAttribute(ProductAttributeCreateReqVm productAttributeCreateReqVm);

    ResponseEntity<ListProductAttributeResVm> getAllProductAttribute(int page);

    ResponseEntity<ResVm> updateAttribute(String attId, ProductAttributeCreateReqVm updateModel);

    ResponseEntity<ResVm> updateStatusAtt(String attId, String action);

    ResponseEntity<ResVm> deleteAttribute(String attId);

    ResponseEntity<List<CommonProductResVm>> getAllAttribute();
}
