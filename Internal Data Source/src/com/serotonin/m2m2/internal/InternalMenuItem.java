package com.serotonin.m2m2.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.infiniteautomation.mango.monitor.ValueMonitor;
import com.serotonin.m2m2.Common;
import com.serotonin.m2m2.Common.TimePeriods;
import com.serotonin.m2m2.ILifecycle;
import com.serotonin.m2m2.db.dao.DataPointDao;
import com.serotonin.m2m2.db.dao.DataSourceDao;
import com.serotonin.m2m2.db.dao.EventDetectorDao;
import com.serotonin.m2m2.db.dao.EventHandlerDao;
import com.serotonin.m2m2.db.dao.MailingListDao;
import com.serotonin.m2m2.db.dao.PublisherDao;
import com.serotonin.m2m2.db.dao.SystemSettingsDao;
import com.serotonin.m2m2.db.dao.TemplateDao;
import com.serotonin.m2m2.db.dao.UserDao;
import com.serotonin.m2m2.i18n.ProcessMessage;
import com.serotonin.m2m2.i18n.ProcessResult;
import com.serotonin.m2m2.module.DataSourceDefinition;
import com.serotonin.m2m2.module.MenuItemDefinition;
import com.serotonin.m2m2.module.ModuleRegistry;
import com.serotonin.m2m2.rt.maint.UpgradeCheck;
import com.serotonin.m2m2.rt.maint.WorkItemMonitor;
import com.serotonin.m2m2.vo.DataPointVO;
import com.serotonin.m2m2.vo.dataSource.DataSourceVO;
import com.serotonin.m2m2.vo.event.detector.AbstractPointEventDetectorVO;
import com.serotonin.m2m2.vo.permission.Permissions;
import com.serotonin.m2m2.vo.template.DataPointPropertiesTemplateVO;
import com.serotonin.provider.Providers;

public class InternalMenuItem extends MenuItemDefinition {
	
	private static Log LOG = LogFactory.getLog(InternalMenuItem.class);
	
    @Override
    public Visibility getVisibility() {
        return Visibility.USER;
    }

    @Override
    public String getTextKey(HttpServletRequest request, HttpServletResponse response) {
        return "internal.status";
    }

    @Override
    public String getImage(HttpServletRequest request, HttpServletResponse response) {
        return "web/system-monitor.png";
    }

    @Override
    public String getHref(HttpServletRequest request, HttpServletResponse response) {
        return "/internal/status.shtm";
    }

    @Override
    public boolean isVisible(HttpServletRequest request, HttpServletResponse response) {
        return Permissions.hasPermission(Common.getUser(request), SystemSettingsDao.getValue(StatusPermissionDef.PERMISSION));
    }
    /* (non-Javadoc)
     * @see com.serotonin.m2m2.module.ModuleElementDefinition#install()
     */
    @Override
    public void install() {
    	Providers.get(ILifecycle.class).addStartupTask(new Runnable() {
            @Override
            public void run() {
            	try{
            		maybeInstallSystemMonitor();
            	}catch(Exception e){
            		LOG.error(e.getMessage(), e);
            	}
            }
        });
    }
    /* (non-Javadoc)
     * @see com.serotonin.m2m2.module.ModuleElementDefinition#upgrade()
     */
    @Override
    public void upgrade() {
    	Providers.get(ILifecycle.class).addStartupTask(new Runnable() {
            @Override
            public void run() {
            	try{
            		maybeInstallSystemMonitor();
            	}catch(Exception e){
            		LOG.error(e.getMessage(), e);
            	}
            }
        });
    }

    //Module Monitor IDs
    public static final String POINT_LINK_COUNT_MONITOR_ID = "com.serotonin.m2m2.pointLinks.PointLinkDao.COUNT";
    public static final String WATCHLIST_COUNT_MONITOR_ID = "com.serotonin.m2m2.watchlist.WatchistDao.COUNT";
    public static final String SCHEDULED_EVENTS_COUNT_MONITOR_ID = "com.serotonin.m2m2.scheduledEvents.ScheduledEventDao.COUNT";
    public static final String REPORTS_COUNT_MONITOR_ID = "com.serotonin.m2m2.reports.ReportDao.COUNT";
    public static final String REPORT_INSTANCES_COUNT_MONITOR_ID = "com.serotonin.m2m2.reports.ReportInstanceDao.COUNT";
    public static final String EXCEL_REPORTS_COUNT_MONITOR_ID = "com.infiniteautomation.mango.excelreports.dao.ExcelReportDao.COUNT";
    public static final String EXCEL_REPORT_TEMPLATES_COUNT_MONITOR_ID = "com.infiniteautomation.mango.excelreports.dao.ExcelReportTemplateDao.COUNT";
    
    //Datasource Defaults
    public static final String SYSTEM_DATASOURCE_XID = "internal_mango_monitoring_ds";
    public static final String SYSTEM_DATASOURCE_DEVICE_NAME = "Mango Internal";
    
    //Default Point XIDs
    public static final String DATASOURCE_COUNT_POINT_XID = "internal_mango_num_data_sources";
    public static final String DATAPOINT_COUNT_POINT_XID = "internal_mango_num_data_points";
    public static final String USERS_COUNT_POINT_XID = "internal_mango_num_users";
    public static final String PUBLISHER_COUNT_POINT_XID = "internal_mango_num_publishers";
    public static final String EVENT_DETECTOR_COUNT_POINT_XID = "internal_mango_num_event_detectors";
    public static final String EVENT_HANDLER_COUNT_POINT_XID = "internal_mango_num_event_handlers";
    public static final String POINT_LINK_COUNT_POINT_XID = "internal_mango_num_point_links";
    public static final String AVAILABLE_UPDATES_COUNT_POINT_XID = "internal_mango_num_updates_available";
    public static final String ACTIVE_USER_SESSION_COUNT_POINT_XID = "internal_mango_num_active_user_sessions";
    public static final String SYSTEM_UPTIME_POINT_XID = "internal_mango_uptime_hrs";
    public static final String WATCHLIST_COUNT_POINT_XID = "internal_mango_num_watchlists";
    public static final String SCHEDULED_EVENTS_COUNT_POINT_XID = "internal_mango_num_scheduled_events";
    public static final String MAILING_LIST_COUNT_POINT_XID = "internal_mango_num_mailing_lists";
    public static final String REPORTS_COUNT_POINT_XID = "internal_mango_num_reports";
    public static final String REPORT_INSTANCES_COUNT_POINT_XID = "internal_mango_num_report_instances";
    public static final String EXCEL_REPORTS_COUNT_POINT_XID = "internal_mango_num_excel_reports";
    public static final String EXCEL_REPORT_TEMPLATES_COUNT_POINT_XID = "internal_mango_num_excel_report_templates";
    
    /**
     * 
     * @return Map of Home Page Monitor XIDs to Home Page Monitors
     */
    public Map<String, ValueMonitor<?>> getAllHomePageMonitors(){
    	Map<String, ValueMonitor<?>> monitors = new HashMap<String, ValueMonitor<?>>();
    	
    	//Data Source Count
    	monitors.put(DATASOURCE_COUNT_POINT_XID, DataSourceDao.instance.getCountMonitor());
    	
    	//Data Point Count
    	monitors.put(DATAPOINT_COUNT_POINT_XID, DataPointDao.instance.getCountMonitor());
    	
    	//Users Count
    	monitors.put(USERS_COUNT_POINT_XID, UserDao.instance.getCountMonitor());
    	
    	//Publisher Count
    	monitors.put(PUBLISHER_COUNT_POINT_XID, PublisherDao.instance.getCountMonitor());
    	
    	//Event Detector Count
    	monitors.put(EVENT_DETECTOR_COUNT_POINT_XID, EventDetectorDao.instance.getCountMonitor());
    	
    	//Event Handler Count
    	monitors.put(EVENT_HANDLER_COUNT_POINT_XID, EventHandlerDao.instance.getCountMonitor());

    	//Mailing Lists Count
    	monitors.put(MAILING_LIST_COUNT_POINT_XID, MailingListDao.instance.getMonitor());

    	//Module Updates Available
    	monitors.put(AVAILABLE_UPDATES_COUNT_POINT_XID, Common.MONITORED_VALUES.getValueMonitor(UpgradeCheck.UPGRADES_AVAILABLE_MONITOR_ID));
    	
    	//Active User Sessions
    	monitors.put(ACTIVE_USER_SESSION_COUNT_POINT_XID, Common.loginManager.getSessionCountMonitor());
    	
    	//System Uptime
    	monitors.put(SYSTEM_UPTIME_POINT_XID, Common.MONITORED_VALUES.getValueMonitor(WorkItemMonitor.SYSTEM_UPTIME_MONITOR_ID));
    	
    	//Get from Modules
    	monitors.put(POINT_LINK_COUNT_POINT_XID, Common.MONITORED_VALUES.getValueMonitor(POINT_LINK_COUNT_MONITOR_ID));
    	monitors.put(WATCHLIST_COUNT_POINT_XID, Common.MONITORED_VALUES.getValueMonitor(WATCHLIST_COUNT_MONITOR_ID));
    	monitors.put(SCHEDULED_EVENTS_COUNT_POINT_XID, Common.MONITORED_VALUES.getValueMonitor(SCHEDULED_EVENTS_COUNT_MONITOR_ID));
    	monitors.put(REPORTS_COUNT_POINT_XID, Common.MONITORED_VALUES.getValueMonitor(REPORTS_COUNT_MONITOR_ID));
    	monitors.put(REPORT_INSTANCES_COUNT_POINT_XID, Common.MONITORED_VALUES.getValueMonitor(REPORT_INSTANCES_COUNT_MONITOR_ID));
    	monitors.put(EXCEL_REPORTS_COUNT_POINT_XID, Common.MONITORED_VALUES.getValueMonitor(EXCEL_REPORTS_COUNT_MONITOR_ID));
    	monitors.put(EXCEL_REPORT_TEMPLATES_COUNT_POINT_XID, Common.MONITORED_VALUES.getValueMonitor(EXCEL_REPORT_TEMPLATES_COUNT_MONITOR_ID));
    	
    	return monitors;
    }
    
	/**
	 * 
	 */
	private void maybeInstallSystemMonitor() {
		DataSourceVO<?> ds = DataSourceDao.instance.getByXid(SYSTEM_DATASOURCE_XID);
		if(ds == null){
			//Create Data Source
			DataSourceDefinition def = ModuleRegistry.getDataSourceDefinition(InternalDataSourceDefinition.DATA_SOURCE_TYPE);
	        ds = def.baseCreateDataSourceVO();
	        InternalDataSourceVO vo = (InternalDataSourceVO)ds;
			vo.setXid(SYSTEM_DATASOURCE_XID);
			vo.setName(SYSTEM_DATASOURCE_DEVICE_NAME);
			
			vo.setUpdatePeriods(10);
			vo.setUpdatePeriodType(TimePeriods.SECONDS);
			
			Common.runtimeManager.saveDataSource(vo);
			
			//Setup the Points
			maybeCreatePoints(ds);
			
			//Enable the data source
			vo.setEnabled(true);
			Common.runtimeManager.saveDataSource(vo);
			
		}else{
			//Ensure all points are added
			maybeCreatePoints(ds);
		}
	}

	/**
	 * 
	 */
	private void maybeCreatePoints(DataSourceVO<?> vo) {
		Map<String, ValueMonitor<?>> monitors = getAllHomePageMonitors();
		Iterator<String> it = monitors.keySet().iterator();
		while(it.hasNext()){
			String xid = it.next();
			ValueMonitor<?> monitor = monitors.get(xid);
			if(monitor != null){
				DataPointVO dp = DataPointDao.instance.getByXid(xid);
				if(dp == null){
					InternalPointLocatorVO pl = new InternalPointLocatorVO();
					pl.setMonitorId(monitor.getId());
					
					dp = new DataPointVO();
					dp.setXid(xid);
					dp.setName(monitor.getName().translate(Common.getTranslations()));
					dp.setDataSourceId(vo.getId());
					dp.setDeviceName(vo.getName());
		            dp.setEventDetectors(new ArrayList<AbstractPointEventDetectorVO<?>>(0));
		            dp.defaultTextRenderer();
					dp.setEnabled(true);

					dp.setPointLocator(pl);
					DataPointPropertiesTemplateVO template = TemplateDao.instance.getDefaultDataPointTemplate(pl.getDataTypeId());
					if(template != null){
						template.updateDataPointVO(dp);
						dp.setTemplateId(template.getId());
					}
					
					ProcessResult result = new ProcessResult();
					dp.validate(result);
					if(!result.getHasMessages()){
						Common.runtimeManager.saveDataPoint(dp);
					}else{
						for(ProcessMessage message : result.getMessages()){
							LOG.error(message.toString(Common.getTranslations()));
						}
					}
				}
			}
		}
	}
}
