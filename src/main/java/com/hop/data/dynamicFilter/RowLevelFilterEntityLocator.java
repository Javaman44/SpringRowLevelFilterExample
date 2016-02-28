package com.hop.data.dynamicFilter;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

@Service
public class RowLevelFilterEntityLocator {

    @Value("${filter.entityBasePackage}")
    private String basePackageSearch;
    
    /* Contain entity with DynamicFilter annotation 
     * and list of filter implementation class */     
    private final Map<Class,Class[]> entityMap;

    public RowLevelFilterEntityLocator() {
        this.entityMap = new HashMap<>();
    }

    @PostConstruct
    public void init() throws ClassNotFoundException {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(DynamicFilter.class));
        for (BeanDefinition bd : scanner.findCandidateComponents(basePackageSearch)) {
           
            String className = bd.getBeanClassName();
            Class<?> annotedClass = Class.forName(className);    
            
            // Get the value part of @DynamicFilter(value={CompanyFilterOnPerson.class})
            DynamicFilter dynamicFilterAnnotation = annotedClass.getAnnotation(DynamicFilter.class);
            
            entityMap.put(annotedClass,dynamicFilterAnnotation.value());
        }
    }

    public String getBasePackageSearch() {
        return basePackageSearch;
    }

    public Class[] get(Class key) {
        return entityMap.get(key);
    } 
}
