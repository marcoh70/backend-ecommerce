package com.app.ecommerce.repository;

import com.app.ecommerce.entity.ProductCategory;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "withId", types = ProductCategory.class)
public interface ProductCategoryProjection {

    Long getId();
    String getCategoryName();
}
