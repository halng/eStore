package com.e.store.product.services;

import com.e.store.product.viewmodel.req.ProductOptionCreateReqVm;
import com.e.store.product.viewmodel.res.CommonProductResVm;
import com.e.store.product.viewmodel.res.PagingResVm;
import com.e.store.product.viewmodel.res.ProductOptionResVm;
import com.e.store.product.viewmodel.res.ResVm;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface IProductOptionService {

	ResponseEntity<ResVm> createNewProductOption(ProductOptionCreateReqVm productOptionCreateReqVm);

	ResponseEntity<PagingResVm<ProductOptionResVm>> getAllOption(int page);

	ResponseEntity<ResVm> updateOption(String optionId, ProductOptionCreateReqVm req);

	ResponseEntity<ResVm> deleteOption(String optionId);

	ResponseEntity<List<CommonProductResVm>> getAllOption();

}
