/**
 * Copyright (C) 2015 Infinite Automation Software. All rights reserved.
 * @author Terry Packer
 */
package com.serotonin.m2m2.web.mvc.rest.v1.model.system;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Terry Packer
 *
 */
public class TimezoneModel implements Comparable<TimezoneModel>{

	@JsonProperty
	private String id; //Timezone id
	
	@JsonProperty
	private String name; //Nice name
	
	@JsonProperty
	private int offset; //Offset in ms from UTC
	
	public TimezoneModel(){ }
	
	public TimezoneModel(String id, String name, int offset){
		this.id = id;
		this.name = name;
		this.offset = offset;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(TimezoneModel o) {
		if(offset != o.offset)
			return offset - o.offset;
		return id.compareTo(o.id);
	}
	
	
}
