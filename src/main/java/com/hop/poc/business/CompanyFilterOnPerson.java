package com.hop.poc.business;

import com.hop.data.dynamicFilter.RowLevelFilter;
import com.hop.data.dynamicFilter.RowLevelFilterException;
import java.util.Iterator;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.jpa.criteria.CriteriaBuilderImpl;
import org.hibernate.jpa.criteria.CriteriaQueryImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class CompanyFilterOnPerson implements RowLevelFilter {

    @Override
    public void filter(Root from, CriteriaQueryImpl criteriaQuery, Iterator<? extends GrantedAuthority> rolesItr) throws RowLevelFilterException {
        
        String entityName = from.getModel().getName();
        if (entityName.equals("Person")) {
            while (rolesItr.hasNext()) {
                GrantedAuthority currentRole = rolesItr.next();
                String filter = null;

                switch (currentRole.getAuthority()) {
                    case "ROLE_PROFIL_A":
                        filter = "A";
                        break;
                    case "ROLE_PROFIL_B":
                        filter = "B";
                        break;
                    case "ROLE_PROFIL_C":
                        filter = "C";
                        break;
                    case "ROLE_ADMIN":
                        break;
                }

                if (filter != null) {
                    CriteriaBuilderImpl cb = criteriaQuery.criteriaBuilder();
                    Expression<String> literal = cb.upper(cb.literal(filter));
                    Predicate predicate = cb.like(cb.upper(from.get("company")), literal);
                    criteriaQuery.where(predicate);
                }
            }
        } else {
            throw new RowLevelFilterException("CompanyFilterOnPerson can filter on "+entityName+" entity");
        }
    }

}
