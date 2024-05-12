package com.e.store.product.controllers;

import com.e.store.product.services.IProductGroupService;
import com.e.store.product.viewmodel.req.ProductGroupCreateReqVm;
import com.e.store.product.viewmodel.req.ProductGroupUpdateReqVm;
import com.e.store.product.viewmodel.res.CommonProductResVm;
import com.e.store.product.viewmodel.res.PagingResVm;
import com.e.store.product.viewmodel.res.ProductGroupResVm;
import com.e.store.product.viewmodel.res.ResVm;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product/group")
public class ProductGroupController {

  private final IProductGroupService iProductGroupService;

  @Autowired
  public ProductGroupController(IProductGroupService iProductGroupService) {
    this.iProductGroupService = iProductGroupService;
  }

  @PostMapping()
  public ResponseEntity<ResVm> createNewGroup(@RequestBody ProductGroupCreateReqVm groupData) {
    return this.iProductGroupService.createNewGroup(groupData);
  }

  @PutMapping()
  public ResponseEntity<ResVm> updateProductGroup(@RequestBody ProductGroupUpdateReqVm data) {
    return this.iProductGroupService.updateProductGroup(data);
  }

  @DeleteMapping("{groupId}")
  public ResponseEntity<ResVm> deleteProductGroup(@PathVariable String groupId) {
    return this.iProductGroupService.deleteProductGroup(groupId);
  }

  @GetMapping()
  public ResponseEntity<PagingResVm<ProductGroupResVm>> getProductGroup(@RequestParam int page) {
    return this.iProductGroupService.getAllGroup(page);
  }

  @GetMapping("all")
  public ResponseEntity<List<CommonProductResVm>> getAllProductGroup() {
    return this.iProductGroupService.getAllGroup();
  }
}
