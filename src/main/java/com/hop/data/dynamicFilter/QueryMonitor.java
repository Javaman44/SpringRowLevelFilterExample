package com.hop.data.dynamicFilter;

import java.util.Iterator;
import javax.persistence.criteria.Root;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.jpa.criteria.CriteriaQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class QueryMonitor {

    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private RowLevelFilterEntityLocator entityLocator;
    
    /*
     * Intercept each call of createQuery from EntityManager
     * using aop (aspectj)
     */
    @Around("execution(* javax.persistence.EntityManager.createQuery(..))")
    public Object criteriaQueryIntercept(ProceedingJoinPoint joinPoint) throws Throwable {        
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Iterator<? extends GrantedAuthority> rolesItr = auth.getAuthorities().iterator();

        // for each query
        for (Object arg : joinPoint.getArgs()) {
            
            // get current query
            CriteriaQueryImpl criteriaQuery = (CriteriaQueryImpl) arg;
            
            // for each targeted entity
            for (Object obj : criteriaQuery.getRoots()) {                
                    
                    Root from = (Root) obj;
                    Class currentEntityClass = from.getModel().getJavaType();                                         
                    Class[] filterClasses = entityLocator.get(currentEntityClass);
                    
                    if(filterClasses != null){
                        for(Class filterClass : filterClasses){
                            RowLevelFilter filter = (RowLevelFilter) context.getBean(filterClass);                            
                            if(filter !=  null)
                                filter.filter(from, criteriaQuery, rolesItr);
                        }
                    } 
            }
        }
        return joinPoint.proceed();
    }   
}
