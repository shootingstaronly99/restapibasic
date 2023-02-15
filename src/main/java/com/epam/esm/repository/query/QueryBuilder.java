package com.epam.esm.repository.query;

import org.springframework.stereotype.Component;

import java.util.Map;

/*
@author Otabek Javqochdiyev
Query Builder class for  search and filter
 */
@Component
public class QueryBuilder {
    public static final String BASE_QUERY = "SELECT gc.id," +
            "gc.name," +
            "gc.description," +
            " gc.price," +
            "gc.duration," +
            " gc.create_date," +
            " gc.last_update_date," +
            "t.tag_id," +
            "t.tag_name" +
            " FROM gift_tags gt" +
            " JOIN gift_certificates gc ON gt.gift_id = gc.id" +
            "  JOIN tag t ON gt.tag_id = t.tag_id ";

    //create query for each field checking all fields step by step and generate a common query for filter
    public String createGetQuery(Map<String, String> fields) {
        StringBuilder query = new StringBuilder(BASE_QUERY);
        if (fields.get("tagName") != null) {
            addParameter(query, "t.tag_name", fields.get("tagName"));
        }
        if (fields.get("giftName") != null) {
            addPartParameter(query, "gc.name", fields.get("giftName"));
        }
        if (fields.get("giftDescription") != null) {
            addPartParameter(query, "gc.description", fields.get("giftDescription"));
        }
        if (fields.get("sortByName") != null) {
            addSortParameter(query, "gc.name", fields.get("sortByName"));
        }
        if (fields.get("sortByDate") != null) {
            addSortParameter(query, "gc.create_date", fields.get("sortByDate"));
        }
        return query.toString();
    }

    private void addParameter(StringBuilder query, String partParameter, String value) {
        if (query.toString().contains("WHERE")) {
            query.append(" AND ");
        } else {
            query.append(" WHERE ");
        }
        query.append(partParameter).append("='").append(value).append('\'');
    }

    private void addPartParameter(StringBuilder query, String partParameter, String value) {
        if (query.toString().contains("WHERE")) {
            query.append(" AND ");
        } else {
            query.append(" WHERE ");
        }
        query.append(partParameter).append(" LIKE '%").append(value).append("%'");
    }

    private void addSortParameter(StringBuilder query, String sortParameter, String value) {
        if (query.toString().contains("ORDER BY")) {
            query.append(", ");
        } else {
            query.append(" ORDER BY ");
        }
        query.append(sortParameter).append(" ").append(value);
    }
}
