package com.example.hitdemo.core;

import lombok.extern.slf4j.Slf4j;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.hitdemo.exception.SearchException;
import com.example.hitdemo.model.query.FilterMetadata;
import com.example.hitdemo.model.query.SearchEvent;
import com.example.hitdemo.util.ErpDateUtil;


@Slf4j
public class QueryBuilder {
	 public static Query buildSearchQuery(SearchEvent search, Pageable pageable)
		      throws SearchException {

		    Query query = new Query();
		    Map<String, FilterMetadata> filters = search.getFilters();
		    if (!Objects.isNull(filters)) {
		      for (Entry<String, FilterMetadata> entry : filters.entrySet()) {
		        String key = entry.getKey();
		        FilterMetadata value = entry.getValue();
		        try {
		          buildQuery(key, value, query);
		        } catch (ParseException e) {
		          log.error(e.getMessage());
		          throw new SearchException(e.getMessage());
		        }
		      }
		    } /*
		       * else { query.limit(pageable.getPageSize()); }
		       */
		    if (!StringUtils.isEmpty(search.getSortField())) {
		      if (search.getSortOrder() == -1) {
		        // if the order is -1 then direction is ascending.
		    	  query.with(Sort.by(Sort.Direction.ASC, search.getSortField()));
		      } else if (search.getSortOrder() == 1) {
		        // if the order is 1 then direction is descending.
		    	  query.with(Sort.by(Sort.Direction.DESC, search.getSortField()));
		      }
		    }
		    if (Objects.nonNull(pageable)) {
		      return query.with(pageable);
		    }
		    return query;
		  }

		  @SuppressWarnings("unchecked")
		  private static Query buildQuery(String key, FilterMetadata value, Query query)
		      throws ParseException {

		    switch (value.getMatchMode()) {
		      case CONTAINS:
		        query.addCriteria(Criteria.where(key)
		            .regex(Pattern.compile(Pattern.quote(String.valueOf(value.getValue())),
		                Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		        break;
		      case GTE:
		        query.addCriteria(Criteria.where(key)
		            .gte(Double.valueOf(String.valueOf(value.getValue()))));
		        break;
		      case LTE:
		        query.addCriteria(Criteria.where(key)
		            .lte(Double.valueOf(String.valueOf(value.getValue()))));
		        break;
		      case GT:
		        query.addCriteria(Criteria.where(key)
		            .gt(Double.valueOf(String.valueOf(value.getValue()))));
		        break;
		      case LT:
		        query.addCriteria(Criteria.where(key)
		            .lt(Double.valueOf(String.valueOf(value.getValue()))));
		        break;
		      case DATE_LT:
		        query.addCriteria(Criteria.where(key)
		            .lt(value.getValue()));
		        break;
		      case DATE_GT:
		        query.addCriteria(Criteria.where(key)
		            .gt(value.getValue()));
		        break;
		      case AFTER:
		        query.addCriteria(Criteria.where(key)
		            .gte(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
		                .parse(String.valueOf(value.getValue()))));
		        break;
		      case BEFORE:
		        query.addCriteria(Criteria.where(key)
		            .lte(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
		                .parse(String.valueOf(value.getValue()))));
		        break;
		      case BINARY:
		        query.addCriteria(Criteria.where(key)
		            .is(value.getValue()));
		        break;
		      case NULL:
		        query.addCriteria(Criteria.where(key)
		            .is(null));
		        break;
		      case NOT_NULL:
		        query.addCriteria(Criteria.where(key)
		            .ne(null));
		        break;
		      case IS:
		        query.addCriteria(Criteria.where(key)
		            .is(value.getValue()));
		        break;
		      case IN:
		        Object obj = value.getValue();
		        if (!CollectionUtils.isEmpty((List) obj)) {
		          if (((List) obj).get(0) instanceof ObjectId) {
		            query.addCriteria(Criteria.where(key)
		                .in((List<ObjectId>) obj));
		          } else if (((List) obj).get(0) instanceof String) {
		            query.addCriteria(Criteria.where(key)
		                .in((List<String>) obj));
		          } else {
		            log.error("Cast type of object is not known for IN criteria of search");
		          }
		        } else {
		          log.error("Object is empty for IN criteria of search");
		        }
		        break;
		      case NOT_EXISTS_OR_LTE:
		        query.addCriteria(new Criteria().orOperator(Criteria.where(key)
		            .exists(false),
		            Criteria.where(key)
		                .lte(Double.valueOf(String.valueOf(value.getValue())))));
		        break;
		      case DATE_BETWEEN:
		          List<String> date = (List<String>) value.getValue();
		          query.addCriteria(Criteria.where(key)
		              .gte(
		                  ErpDateUtil.getUTCDate(String.valueOf(date.get(0)), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
		              .lte(ErpDateUtil.getUTCDate(String.valueOf(date.get(1)),
		                  "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
		          break;
		      case BETWEEN:
		        List<String> dateList = (List<String>) value.getValue();
		        query.addCriteria(Criteria.where(key)
		            .gte(dateList.get(0))
		            .lte(dateList.get(1)));
		        break;
		      case NOT_EQUAL:
		        query.addCriteria(Criteria.where(key)
		            .ne(value.getValue()));
		        break;
		      case NOT_IN:
		        query.addCriteria(Criteria.where(key)
		            .nin((List) value.getValue()));
		        break;
		      case NOT_EXISTS_OR_IS:
		        query.addCriteria(new Criteria().orOperator(Criteria.where(key)
		            .exists(false),
		            Criteria.where(key)
		                .is(value.getValue())));
		        break;
		      case DATE_BETWEEN_WITH_LT:
		        List<Date> dates = (List<Date>) value.getValue();
		        query.addCriteria(Criteria.where(key)
		            .gte(dates.get(0))
		            .lt(dates.get(1)));
		        break;
		      case STARTWITH:
		        query.addCriteria(Criteria.where(key)
		            .regex(Pattern.compile("^".concat(String.valueOf(value.getValue())),
		                Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		        break;
		      case OBJECT_ID:
		        try {
		          query.addCriteria(Criteria.where(key)
		              .is(new ObjectId(String.valueOf(value.getValue()))));
		        } catch (Exception ex) {
		          log.info("Hex value not sent in Criteria-search through objectId.");
		          query.addCriteria(Criteria.where(key)
		              .is(null));
		        }
		        break;
		      case DATE_BETWEEN_PERIOD:
		          List<String> datesWithoutTimestamp = (List<String>) value.getValue();
		          query.addCriteria(Criteria.where(key)
		              .gte(ErpDateUtil.getDate(String.valueOf(datesWithoutTimestamp.get(0)),
		                  "yyyy-MM-dd'T'HH:mm:ss"))
		              .lte(ErpDateUtil.getDate(String.valueOf(datesWithoutTimestamp.get(1)),
		                  "yyyy-MM-dd'T'HH:mm:ss")));
		          break;
		      default:
		        log.error("Match-Mode not defined for {}",
		            value.getMatchMode());
		        break;
		    }
		    return query;
		  }

}
