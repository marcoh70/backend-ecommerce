package com.app.ecommerce.repository;

import com.app.ecommerce.entity.Product;
import com.app.ecommerce.entity.ProductCategory;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name = "withId", types = Product.class)
public interface ProductProjection {

    Long getId();
    String getSku();
    String getName();
    String getDescription();
    String getUnitPrice();
    String getImageUrl();
    String isActive();
    int getUnitsInStock();
    Date getDateCreated();
    Date getLastUpdated();



}
