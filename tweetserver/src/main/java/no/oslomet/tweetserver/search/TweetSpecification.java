package no.oslomet.tweetserver.search;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.oslomet.tweetserver.model.Tweet;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

//Search implementation based on:
//https://www.baeldung.com/rest-api-query-search-language-more-operations
//https://github.com/eugenp/tutorials/tree/master/spring-rest-query-language

@Data
@NoArgsConstructor
public class TweetSpecification implements Specification<Tweet> {

    private SearchCriteria criteria;

    public TweetSpecification(final SearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(
            Root<Tweet> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        switch (criteria.getOperation()) {
            case EQUALITY:
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            case NEGATION:
                return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
            case GREATER_THAN:
                return builder.greaterThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN:
                return builder.lessThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
            case LIKE:
                return builder.like(root.<String> get(criteria.getKey()), criteria.getValue().toString());
            case STARTS_WITH:
                return builder.like(root.<String> get(criteria.getKey()), criteria.getValue() + "%");
            case ENDS_WITH:
                return builder.like(root.<String> get(criteria.getKey()), "%" + criteria.getValue());
            case CONTAINS:
                return builder.like(root.<String> get(criteria.getKey()), "%" + criteria.getValue() + "%");
            default:
                return null;
        }
    }

}
