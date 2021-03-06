/**
 * Copyright (C) 2014 Infinite Automation Software. All rights reserved.
 * @author Terry Packer
 */
package com.serotonin.m2m2.web.mvc.rest.test.data;

import com.serotonin.m2m2.vo.DataPointVO.PurgeTypes;
import com.serotonin.m2m2.vo.dataSource.DataSourceVO;
import com.serotonin.m2m2.vo.dataSource.mock.MockDataSourceVO;

/**
 * @author Terry Packer
 *
 */
public class DataSourceTestData {

	/**
	 * Get a test data source
	 * @return
	 */
	public static DataSourceVO<?> mockDataSource(){
		MockDataSourceVO ds = new MockDataSourceVO();
		ds.setId(1);
		ds.setXid("mock-xid");
		ds.setName("Mock Name");
		ds.setPurgeOverride(false);
		ds.setPurgeType(PurgeTypes.YEARS);
		ds.setPurgePeriod(1);
		ds.setEnabled(false);

		
		return ds;
		
	}
}
