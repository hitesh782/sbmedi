package com.example.hitdemo.model.base;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Auditable {
	public Auditable(final String id, final Date createdDate, final String createdBy) {

	    super();
	    this.id = id;
	    this.createdDate = createdDate;
	    this.createdBy = createdBy;
	  }

	  @Id
	  private String id;

	  @CreatedDate
	  private Date createdDate;

	  @CreatedBy
	  private String createdBy;

	  @LastModifiedDate
	  private Date updatedDate;

	  @LastModifiedBy
	  private String updatedBy;

	  private boolean isActive = true;

	  private boolean deleted = false;
}
