# Spring Row Level Filter Example
Example how to filter data based on custom filter without modify default spring repository and keeping paging capabilities
This example use AOP(AspectJ) and custom annotation

The project is a Rest Service (with Spring Data Rest Repository) with only one entity (Person)
The goal is to filter on company field depending of role of current user.

The main idea is to instercept the entityManager Query (Criteria) when it apply to annotated Entity
To filter you have to:
- annotate the entity with: @DynamicFilter(value={MyCustomFilterClass.class})
- implement the custom filter class (with RowLevelFilter interface)

For start the example: mvn spring-boot:run

- Go to browser: http://localhost:8080/people
- Put the right credential:
  - userA/userA -> list person of domain A
  - userB/userB -> list person of domain B
  - userC/userC -> list person of domain C
  - admin/admin -> list all person

- logout : http://localhost:8080/logout

pros:
- clean (AOP + annotations)
- isolation of business rules
- keep repository light

cons:
- filter based on entity and not on service