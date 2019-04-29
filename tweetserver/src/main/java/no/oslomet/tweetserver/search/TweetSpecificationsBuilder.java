package no.oslomet.tweetserver.search;

import no.oslomet.tweetserver.model.Tweet;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

//Search implementation based on:
//https://www.baeldung.com/rest-api-query-search-language-more-operations
//https://github.com/eugenp/tutorials/tree/master/spring-rest-query-language

public final class TweetSpecificationsBuilder {

    private final List<SearchCriteria> params;

    public TweetSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    // API

    public final TweetSpecificationsBuilder with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public final TweetSpecificationsBuilder with(final String orPredicate, final String key, final String operation, final Object value, final String prefix, final String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new SearchCriteria(orPredicate, key, op, value));
        }
        return this;
    }

    public Specification<Tweet> build() {
        if (params.size() == 0)
            return null;

        Specification<Tweet> result = new TweetSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new TweetSpecification(params.get(i)))
                    : Specification.where(result).and(new TweetSpecification(params.get(i)));
        }

        return result;
    }

    public final TweetSpecificationsBuilder with(TweetSpecification spec) {
        params.add(spec.getCriteria());
        return this;
    }

    public final TweetSpecificationsBuilder with(SearchCriteria criteria) {
        params.add(criteria);
        return this;
    }
}