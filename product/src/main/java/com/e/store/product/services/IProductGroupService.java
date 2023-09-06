package com.e.store.product.services;

import com.e.store.product.viewmodel.res.CommonProductResVm;
import com.e.store.product.viewmodel.res.ListProductGroupResVm;
import com.e.store.product.viewmodel.res.ResVm;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public interface IProductGroupService {
    ResponseEntity<ResVm> createNewGroup(String groupName);

    ResponseEntity<ResVm> updateProductGroup(String newName, String groupId);

    ResponseEntity<ResVm> disableEnableGroup(String groupId, String action);

    ResponseEntity<ListProductGroupResVm> getAllGroup(int page);

    ResponseEntity<ResVm> deleteProductGroup(String groupId);

    ResponseEntity<List<CommonProductResVm>> getAllGroup();
}
