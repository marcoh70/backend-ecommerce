package com.app.ecommerce.config;

import com.app.ecommerce.entity.Product;
import com.app.ecommerce.entity.ProductCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import java.util.Set;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {

    @Autowired
    private EntityManager entityManager;


    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration configuration) {

        HttpMethod[] unsupportedActions = {
                HttpMethod.DELETE,
                HttpMethod.PUT,
                HttpMethod.POST
        };

        configuration.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions));

        configuration.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions));

        //exposeIds(configuration);
    }

    /**
     * Method used to expose ids from categories
     * By default Spring Data Rest hide ids for security
     * This method works with spring 2.x.x and 3.x.x
     * For spring 4.x.x must be used projection
     * See ProductCategoryProjection.java and ProductCategoryRepository.java
     */
    private void exposeIds(RepositoryRestConfiguration configuration) {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        Class<?>[] domainTypes = entities.stream()
                .map(EntityType::getJavaType)
                .toArray(Class[]::new);

        configuration.exposeIdsFor(domainTypes);
    }
}
