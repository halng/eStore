package com.e.store.product.controllers;

import com.e.store.product.services.IProductGroupService;
import com.e.store.product.viewmodel.res.ListProductGroupResVm;
import com.e.store.product.viewmodel.res.ResVm;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product/group")
public class ProductGroupController {

    @Autowired
    IProductGroupService iProductGroupService;

    @PostMapping()
    public ResponseEntity<ResVm> createNewProduct(@RequestParam String groupName) {
        return this.iProductGroupService.createNewGroup(groupName);
    }

    @PutMapping("{groupId}")
    public ResponseEntity<ResVm> updateProductGroup(@NotBlank @RequestParam String newName, @PathVariable int groupId) {
        return this.iProductGroupService.updateProductGroup(newName, groupId);
    }

    @PatchMapping("{groupId}/{action}")
    public ResponseEntity<ResVm> disableEnableGroup(@PathVariable int groupId, @PathVariable String action) {
        return this.iProductGroupService.disableEnableGroup(groupId, action);
    }

    @DeleteMapping("{groupId}")
    public ResponseEntity<ResVm> deleteProductGroup(@PathVariable int groupId) {
        return this.iProductGroupService.deleteProductGroup(groupId);
    }

    @GetMapping()
    public ResponseEntity<ListProductGroupResVm> getAllProductGroup(@RequestParam int page) {
        return this.iProductGroupService.getAllGroup(page);
    }

}
