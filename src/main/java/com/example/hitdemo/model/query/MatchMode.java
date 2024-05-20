package com.example.hitdemo.model.query;


public enum MatchMode {
	/**
	   *
	   */
	  CONTAINS("contains"),
	  /**
	   *
	   */
	  IN("in"),
	  /**
	   *
	   */
	  STARTWITH("startsWith"),
	  /**
	   *
	   */
	  ENDWITH("endsWith"),
	  /**
	   *
	   */
	  GTE("gte"),
	  /**
	   *
	   */
	  LTE("lte"),
	  /**
	  *
	  */
	  GT("gt"),
	  /**
	  *
	  */
	  LT("lt"),
	  /**
	   *
	   */
	  DATE_LT("lt"),
	  /**
	  *
	  */
	  DATE_GT("gt"),
	  /**
	  *
	  */
	  AFTER("after"),
	  /**
	   *
	   */
	  BEFORE("before"),
	  /**
	  *
	  */
	  BINARY("binary"),
	  /**
	   *
	   */
	  NULL("null"),
	  /**
	   *
	   */
	  NOT_NULL("notNull"),
	  /**
	   *
	   */
	  IS("is"),
	  /**
	   *
	   */
	  NOT_EXISTS_OR_LTE("notExistOrLte"),
	  /**
	   *
	   */
	  DATE_BETWEEN("dateBetween"),
	  /**
	   *
	   */
	  NOT_EXISTS_OR_IS("notExistOrIs"),
	  /**
	   *
	   */
	  NOT_EQUAL("neq"),
	  /**
	   *
	   */
	  NOT_IN("notIn"),
	  /**
	   *
	   */
	  BETWEEN("between"),
	  /**
	  *
	  */
	  DATE_BETWEEN_WITH_LT("date_between_with_LT"),
	  /**
	   * Used to search for the Dbref Object Id
	   */
	  OBJECT_ID("objectId"),
	  /**
	   * User for Decimal search
	   */
	  DECIMAL_IS("decimalIs"),
	  /**
	  *
	  */
	  DATE_BETWEEN_PERIOD("dateBetweenPeriod");
	
	private final String value;
	
	private MatchMode(String value) {
		this.value = value;
	}
	
	public static MatchMode get(String matchModeCode) {
		for(MatchMode matchMode: values()) {
			if(matchMode.getValue().equals(matchModeCode)) {
				return matchMode;
			}
		}
		throw new IllegalArgumentException("No matching constant for ["+ matchModeCode+"]");
	}
	
	private String getValue() {
		return this.value;
	}
	
}
