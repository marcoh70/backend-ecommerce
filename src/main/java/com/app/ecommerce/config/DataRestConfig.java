package com.app.ecommerce.config;

import com.app.ecommerce.entity.Country;
import com.app.ecommerce.entity.Product;
import com.app.ecommerce.entity.ProductCategory;
import com.app.ecommerce.entity.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Type;
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

    /**
     * Method to disable HttpMethods DELETE, PUT, POST for entities exposing
     * RestEndpoints in repositories
     * @param configuration
     */
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration configuration) {

        HttpMethod[] unsupportedActions = {
                HttpMethod.DELETE,
                HttpMethod.PUT,
                HttpMethod.POST
        };

        configureExposureConfiguration(configuration, Product.class, unsupportedActions);

        configureExposureConfiguration(configuration, ProductCategory.class, unsupportedActions);

        configureExposureConfiguration(configuration, State.class, unsupportedActions);

        configureExposureConfiguration(configuration, Country.class, unsupportedActions);

        //exposeIds(configuration);
    }

    private void configureExposureConfiguration(RepositoryRestConfiguration configuration, Class clase, HttpMethod[] unsupportedActions){
        configuration.getExposureConfiguration()
                .forDomainType(clase)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions));
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
