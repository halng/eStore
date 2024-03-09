package com.e.store.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "product_variation")
public class ProductVariation extends AuditEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private int quantity;

	private double price;

	private List<String> optionValueIds;

	@ManyToOne
	@JoinColumn(name = "parent_id", nullable = false)
	private Product product;

}
