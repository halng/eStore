package com.e.store.product.services;

import com.e.store.product.viewmodel.res.ListProductGroupResVm;
import com.e.store.product.viewmodel.res.ResVm;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IProductGroupService {
    ResponseEntity<ResVm> createNewGroup(String groupName);

    ResponseEntity<ResVm> updateProductGroup(String newName, int groupId);

    ResponseEntity<ResVm> disableEnableGroup(int groupId, String action);

    ResponseEntity<ListProductGroupResVm> getAllGroup(int page);

    ResponseEntity<ResVm> deleteProductGroup(int groupId);

}
