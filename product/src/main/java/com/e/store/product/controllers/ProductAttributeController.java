package com.e.store.product.controllers;

import com.e.store.product.services.IProductAttributeService;
import com.e.store.product.viewmodel.req.ProductAttributeCreateReqVm;
import com.e.store.product.viewmodel.res.CommonProductResVm;
import com.e.store.product.viewmodel.res.PagingResVm;
import com.e.store.product.viewmodel.res.ProductAttributeResVm;
import com.e.store.product.viewmodel.res.ResVm;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product/attribute")
public class ProductAttributeController {

	private final IProductAttributeService iProductAttributeService;

	@Autowired
	public ProductAttributeController(IProductAttributeService attributeService) {
		this.iProductAttributeService = attributeService;
	}

	@PostMapping()
	public ResponseEntity<ResVm> createNewAttribute(@RequestBody ProductAttributeCreateReqVm reqVm) {
		return this.iProductAttributeService.createNewAttribute(reqVm);
	}

	@GetMapping()
	public ResponseEntity<PagingResVm<ProductAttributeResVm>> getAttribute(@RequestParam @Min(0) int page) {
		return this.iProductAttributeService.getAllProductAttribute(page);
	}

	@PutMapping("{attId}")
	public ResponseEntity<ResVm> updateAttribute(@RequestBody ProductAttributeCreateReqVm updateModel,
			@PathVariable String attId) {
		return this.iProductAttributeService.updateAttribute(attId, updateModel);
	}

	@PatchMapping()
	public ResponseEntity<ResVm> updateStatusAtt(@RequestParam String action, @RequestParam String attId) {
		return this.iProductAttributeService.updateStatusAtt(attId, action);
	}

	@DeleteMapping("{attId}")
	public ResponseEntity<ResVm> deleteAttribute(@PathVariable String attId) {
		return this.iProductAttributeService.deleteAttribute(attId);
	}

	@GetMapping("/all")
	public ResponseEntity<List<CommonProductResVm>> getAllAttribute() {
		return this.iProductAttributeService.getAllAttribute();
	}

}
