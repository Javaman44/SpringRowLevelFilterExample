package com.hop.poc.entity;

import com.hop.data.dynamicFilter.DynamicFilter;
import com.hop.poc.business.CompanyFilterOnPerson;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@DynamicFilter(value={CompanyFilterOnPerson.class})
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String firstName;	
        private String company;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}
