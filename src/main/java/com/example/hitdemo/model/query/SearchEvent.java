package com.example.hitdemo.model.query;

import java.util.Map;

public class SearchEvent {
	private Integer first ;
	
	private Integer last;
	
	private String sortField;
	
	private Integer sortOrder;
	
	private SortMeta multiSortMeta;
	
	private Map<String, FilterMetadata> filters ;
	
	public Integer getFirst() {
		return first;
	}

	public void setFirst(Integer first) {
		this.first = first;
	}

	public Integer getLast() {
		return last;
	}

	public void setLast(Integer last) {
		this.last = last;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Map<String, FilterMetadata> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, FilterMetadata> filters) {
		this.filters = filters;
	}

	public String getGlobalFilter() {
		return globalFilter;
	}

	public void setGlobalFilter(String globalFilter) {
		this.globalFilter = globalFilter;
	}

	private String globalFilter;
}
