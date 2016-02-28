package com.hop.data.dynamicFilter;

import java.util.Iterator;
import javax.persistence.criteria.Root;
import org.hibernate.jpa.criteria.CriteriaQueryImpl;
import org.springframework.security.core.GrantedAuthority;

public interface RowLevelFilter {
    
    public void filter(
            Root from, 
            CriteriaQueryImpl criteriaQuery, 
            Iterator<? extends GrantedAuthority> rolesItr) 
        throws RowLevelFilterException;    
}
