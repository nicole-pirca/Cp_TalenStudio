
package cp_talenstudio.job2_0_1;

import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.DataQuality;
import routines.Relational;
import routines.Mathematical;
import routines.DataQualityDependencies;
import routines.SQLike;
import routines.Numeric;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.MDM;
import routines.StringHandling;
import routines.DQTechnical;
import routines.TalendDate;
import routines.DataMasking;
import routines.DqStringHandling;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;

@SuppressWarnings("unused")

/**
 * Job: Job2 Purpose: Visualizar la interaccion de ramas del Job2<br>
 * Description: <br>
 * 
 * @author Pirca, Nicole
 * @version 8.0.1.20241219_0901-patch
 * @status
 */
public class Job2 implements TalendJob {
	static {
		System.setProperty("TalendJob.log", "Job2.log");
	}

	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(Job2.class);

	protected static void logIgnoredError(String message, Throwable cause) {
		log.error(message, cause);

	}

	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

	private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

	private final static String utf8Charset = "UTF-8";

	public static String taskExecutionId = null;

	public static String jobExecutionId = java.util.UUID.randomUUID().toString();;

	// contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

		public PropertiesWithType(java.util.Properties properties) {
			super(properties);
		}

		public PropertiesWithType() {
			super();
		}

		public void setContextType(String key, String type) {
			propertyTypes.put(key, type);
		}

		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}

	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();

	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public void synchronizeContext() {

		}

		// if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

	}

	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

	public ContextProperties getContext() {
		return this.context;
	}

	protected java.util.Map<String, String> defaultProperties = new java.util.HashMap<String, String>();
	protected java.util.Map<String, String> additionalProperties = new java.util.HashMap<String, String>();

	public java.util.Map<String, String> getDefaultProperties() {
		return this.defaultProperties;
	}

	public java.util.Map<String, String> getAdditionalProperties() {
		return this.additionalProperties;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "Job2";
	private final String projectName = "CP_TALENSTUDIO";
	public Integer errorCode = null;
	private String currentComponent = "";
	public static boolean isStandaloneMS = Boolean.valueOf("false");

	private void s(final String component) {
		try {
			org.talend.metrics.DataReadTracker.setCurrentComponent(jobName, component);
		} catch (Exception | NoClassDefFoundError e) {
			// ignore
		}
	}

	private void mdc(final String subJobName, final String subJobPidPrefix) {
		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", subJobName);
		org.slf4j.MDC.put("_subJobPid", subJobPidPrefix + subJobPidCounter.getAndIncrement());
	}

	private void sh(final String componentId) {
		ok_Hash.put(componentId, false);
		start_Hash.put(componentId, System.currentTimeMillis());
	}

	{
		s("none");
	}

	private String cLabel = null;

	private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

	private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	private final JobStructureCatcherUtils talendJobLog = new JobStructureCatcherUtils(jobName,
			"_YhFwoBl6EfCJEOKxOC-s9w", "0.1");
	private org.talend.job.audit.JobAuditLogger auditLogger_talendJobLog = null;

	private RunStat runStat = new RunStat(talendJobLog, System.getProperty("audit.interval"));

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(),
					new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	public void setDataSourceReferences(List serviceReferences) throws Exception {

		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();

		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
				.getServices(serviceReferences, javax.sql.DataSource.class).entrySet()) {
			dataSources.put(entry.getKey(), entry.getValue());
			talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
		}

		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	StatCatcherUtils talendStats_STATS = new StatCatcherUtils("_YhFwoBl6EfCJEOKxOC-s9w", "0.1");

	private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

	public String getExceptionStackTrace() {
		if ("failure".equals(this.getStatus())) {
			errorMessagePS.flush();
			return baos.toString();
		}
		return null;
	}

	private Exception exception;

	public Exception getException() {
		if ("failure".equals(this.getStatus())) {
			return this.exception;
		}
		return null;
	}

	private class TalendException extends Exception {

		private static final long serialVersionUID = 1L;

		private java.util.Map<String, Object> globalMap = null;
		private Exception e = null;

		private String currentComponent = null;
		private String cLabel = null;

		private String virtualComponentName = null;

		public void setVirtualComponentName(String virtualComponentName) {
			this.virtualComponentName = virtualComponentName;
		}

		private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
			this.currentComponent = errorComponent;
			this.globalMap = globalMap;
			this.e = e;
		}

		private TalendException(Exception e, String errorComponent, String errorComponentLabel,
				final java.util.Map<String, Object> globalMap) {
			this(e, errorComponent, globalMap);
			this.cLabel = errorComponentLabel;
		}

		public Exception getException() {
			return this.e;
		}

		public String getCurrentComponent() {
			return this.currentComponent;
		}

		public String getExceptionCauseMessage(Exception e) {
			Throwable cause = e;
			String message = null;
			int i = 10;
			while (null != cause && 0 < i--) {
				message = cause.getMessage();
				if (null == message) {
					cause = cause.getCause();
				} else {
					break;
				}
			}
			if (null == message) {
				message = e.getClass().getName();
			}
			return message;
		}

		@Override
		public void printStackTrace() {
			if (!(e instanceof TalendException || e instanceof TDieException)) {
				if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
					globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				}
				globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
			}
			if (!(e instanceof TDieException)) {
				if (e instanceof TalendException) {
					e.printStackTrace();
				} else {
					e.printStackTrace();
					e.printStackTrace(errorMessagePS);
					Job2.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(Job2.this, new Object[] { e, currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
						if (enableLogStash) {
							talendJobLog.addJobExceptionMessage(currentComponent, cLabel, null, e);
							talendJobLogProcess(globalMap);
						}
					}
				} catch (Exception e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void preStaLogCon_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		preStaLogCon_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStart_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStart_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStop_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStop_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void connectionStatsLogs_Commit_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		connectionStatsLogs_Commit_onSubJobError(exception, errorComponent, globalMap);
	}

	public void connectionStatsLogs_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		connectionStatsLogs_onSubJobError(exception, errorComponent, globalMap);
	}

	public void talendStats_STATS_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		talendStats_DB_error(exception, errorComponent, globalMap);

	}

	public void talendStats_DB_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		talendStats_STATS_onSubJobError(exception, errorComponent, globalMap);
	}

	public void talendJobLog_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		talendJobLog_onSubJobError(exception, errorComponent, globalMap);
	}

	public void preStaLogCon_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStart_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tDBInput_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStop_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void connectionStatsLogs_Commit_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void connectionStatsLogs_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void talendStats_STATS_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void talendJobLog_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void preStaLogConProcess(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("preStaLogCon_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("preStaLogCon", "BmUFcD_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [preStaLogCon begin ] start
				 */

				sh("preStaLogCon");

				s(currentComponent = "preStaLogCon");

				int tos_count_preStaLogCon = 0;

				if (enableLogStash) {
					talendJobLog.addCM("preStaLogCon", "preStaLogCon", "tPrejob");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [preStaLogCon begin ] stop
				 */

				/**
				 * [preStaLogCon main ] start
				 */

				s(currentComponent = "preStaLogCon");

				tos_count_preStaLogCon++;

				/**
				 * [preStaLogCon main ] stop
				 */

				/**
				 * [preStaLogCon process_data_begin ] start
				 */

				s(currentComponent = "preStaLogCon");

				/**
				 * [preStaLogCon process_data_begin ] stop
				 */

				/**
				 * [preStaLogCon process_data_end ] start
				 */

				s(currentComponent = "preStaLogCon");

				/**
				 * [preStaLogCon process_data_end ] stop
				 */

				/**
				 * [preStaLogCon end ] start
				 */

				s(currentComponent = "preStaLogCon");

				ok_Hash.put("preStaLogCon", true);
				end_Hash.put("preStaLogCon", System.currentTimeMillis());

				if (execStat) {
					runStat.updateStatOnConnection("after_preStaLogCon_connectionStatsLogs", 0, "ok");
				}
				connectionStatsLogsProcess(globalMap);

				/**
				 * [preStaLogCon end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [preStaLogCon finally ] start
				 */

				s(currentComponent = "preStaLogCon");

				/**
				 * [preStaLogCon finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("preStaLogCon_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStart_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStart_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tChronometerStart_1", "H1svd3_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStart_1 begin ] start
				 */

				sh("tChronometerStart_1");

				s(currentComponent = "tChronometerStart_1");

				int tos_count_tChronometerStart_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tChronometerStart_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tChronometerStart_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tChronometerStart_1 = new StringBuilder();
							log4jParamters_tChronometerStart_1.append("Parameters:");
							if (log.isDebugEnabled())
								log.debug("tChronometerStart_1 - " + (log4jParamters_tChronometerStart_1));
						}
					}
					new BytesLimit65535_tChronometerStart_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tChronometerStart_1", "tChronometerStart_1", "tChronometerStart");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				Long currentTimetChronometerStart_1 = System.currentTimeMillis();

				log.info("tChronometerStart_1 - Start time: " + currentTimetChronometerStart_1 + " milliseconds");

				globalMap.put("tChronometerStart_1", currentTimetChronometerStart_1);
				globalMap.put("tChronometerStart_1_STARTTIME", currentTimetChronometerStart_1);

				/**
				 * [tChronometerStart_1 begin ] stop
				 */

				/**
				 * [tChronometerStart_1 main ] start
				 */

				s(currentComponent = "tChronometerStart_1");

				tos_count_tChronometerStart_1++;

				/**
				 * [tChronometerStart_1 main ] stop
				 */

				/**
				 * [tChronometerStart_1 process_data_begin ] start
				 */

				s(currentComponent = "tChronometerStart_1");

				/**
				 * [tChronometerStart_1 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStart_1 process_data_end ] start
				 */

				s(currentComponent = "tChronometerStart_1");

				/**
				 * [tChronometerStart_1 process_data_end ] stop
				 */

				/**
				 * [tChronometerStart_1 end ] start
				 */

				s(currentComponent = "tChronometerStart_1");

				if (log.isDebugEnabled())
					log.debug("tChronometerStart_1 - " + ("Done."));

				ok_Hash.put("tChronometerStart_1", true);
				end_Hash.put("tChronometerStart_1", System.currentTimeMillis());

				/**
				 * [tChronometerStart_1 end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStart_1:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk1", 0, "ok");
			}

			tDBInput_1Process(globalMap);

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStart_1 finally ] start
				 */

				s(currentComponent = "tChronometerStart_1");

				/**
				 * [tChronometerStart_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStart_1_SUBPROCESS_STATE", 1);
	}

	public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_CP_TALENSTUDIO_Job2 = new byte[0];
		static byte[] commonByteArray_CP_TALENSTUDIO_Job2 = new byte[0];

		public String Date;

		public String getDate() {
			return this.Date;
		}

		public Boolean DateIsNullable() {
			return true;
		}

		public Boolean DateIsKey() {
			return false;
		}

		public Integer DateLength() {
			return 50;
		}

		public Integer DatePrecision() {
			return 0;
		}

		public String DateDefault() {

			return null;

		}

		public String DateComment() {

			return "";

		}

		public String DatePattern() {

			return "";

		}

		public String DateOriginalDbColumnName() {

			return "Date";

		}

		public Float USD__AM;

		public Float getUSD__AM() {
			return this.USD__AM;
		}

		public Boolean USD__AMIsNullable() {
			return true;
		}

		public Boolean USD__AMIsKey() {
			return false;
		}

		public Integer USD__AMLength() {
			return 6;
		}

		public Integer USD__AMPrecision() {
			return 3;
		}

		public String USD__AMDefault() {

			return null;

		}

		public String USD__AMComment() {

			return "";

		}

		public String USD__AMPattern() {

			return "";

		}

		public String USD__AMOriginalDbColumnName() {

			return "USD__AM";

		}

		public Float USD__PM;

		public Float getUSD__PM() {
			return this.USD__PM;
		}

		public Boolean USD__PMIsNullable() {
			return true;
		}

		public Boolean USD__PMIsKey() {
			return false;
		}

		public Integer USD__PMLength() {
			return 6;
		}

		public Integer USD__PMPrecision() {
			return 3;
		}

		public String USD__PMDefault() {

			return null;

		}

		public String USD__PMComment() {

			return "";

		}

		public String USD__PMPattern() {

			return "";

		}

		public String USD__PMOriginalDbColumnName() {

			return "USD__PM";

		}

		public Float GBP__AM;

		public Float getGBP__AM() {
			return this.GBP__AM;
		}

		public Boolean GBP__AMIsNullable() {
			return true;
		}

		public Boolean GBP__AMIsKey() {
			return false;
		}

		public Integer GBP__AMLength() {
			return 7;
		}

		public Integer GBP__AMPrecision() {
			return 4;
		}

		public String GBP__AMDefault() {

			return null;

		}

		public String GBP__AMComment() {

			return "";

		}

		public String GBP__AMPattern() {

			return "";

		}

		public String GBP__AMOriginalDbColumnName() {

			return "GBP__AM";

		}

		public Float GBP__PM;

		public Float getGBP__PM() {
			return this.GBP__PM;
		}

		public Boolean GBP__PMIsNullable() {
			return true;
		}

		public Boolean GBP__PMIsKey() {
			return false;
		}

		public Integer GBP__PMLength() {
			return 7;
		}

		public Integer GBP__PMPrecision() {
			return 4;
		}

		public String GBP__PMDefault() {

			return null;

		}

		public String GBP__PMComment() {

			return "";

		}

		public String GBP__PMPattern() {

			return "";

		}

		public String GBP__PMOriginalDbColumnName() {

			return "GBP__PM";

		}

		public Float EURO__AM;

		public Float getEURO__AM() {
			return this.EURO__AM;
		}

		public Boolean EURO__AMIsNullable() {
			return true;
		}

		public Boolean EURO__AMIsKey() {
			return false;
		}

		public Integer EURO__AMLength() {
			return 7;
		}

		public Integer EURO__AMPrecision() {
			return 4;
		}

		public String EURO__AMDefault() {

			return null;

		}

		public String EURO__AMComment() {

			return "";

		}

		public String EURO__AMPattern() {

			return "";

		}

		public String EURO__AMOriginalDbColumnName() {

			return "EURO__AM";

		}

		public Float EURO__PM;

		public Float getEURO__PM() {
			return this.EURO__PM;
		}

		public Boolean EURO__PMIsNullable() {
			return true;
		}

		public Boolean EURO__PMIsKey() {
			return false;
		}

		public Integer EURO__PMLength() {
			return 7;
		}

		public Integer EURO__PMPrecision() {
			return 4;
		}

		public String EURO__PMDefault() {

			return null;

		}

		public String EURO__PMComment() {

			return "";

		}

		public String EURO__PMPattern() {

			return "";

		}

		public String EURO__PMOriginalDbColumnName() {

			return "EURO__PM";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_CP_TALENSTUDIO_Job2.length) {
					if (length < 1024 && commonByteArray_CP_TALENSTUDIO_Job2.length == 0) {
						commonByteArray_CP_TALENSTUDIO_Job2 = new byte[1024];
					} else {
						commonByteArray_CP_TALENSTUDIO_Job2 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_CP_TALENSTUDIO_Job2, 0, length);
				strReturn = new String(commonByteArray_CP_TALENSTUDIO_Job2, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_CP_TALENSTUDIO_Job2.length) {
					if (length < 1024 && commonByteArray_CP_TALENSTUDIO_Job2.length == 0) {
						commonByteArray_CP_TALENSTUDIO_Job2 = new byte[1024];
					} else {
						commonByteArray_CP_TALENSTUDIO_Job2 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_CP_TALENSTUDIO_Job2, 0, length);
				strReturn = new String(commonByteArray_CP_TALENSTUDIO_Job2, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_CP_TALENSTUDIO_Job2) {

				try {

					int length = 0;

					this.Date = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.USD__AM = null;
					} else {
						this.USD__AM = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.USD__PM = null;
					} else {
						this.USD__PM = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.GBP__AM = null;
					} else {
						this.GBP__AM = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.GBP__PM = null;
					} else {
						this.GBP__PM = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.EURO__AM = null;
					} else {
						this.EURO__AM = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.EURO__PM = null;
					} else {
						this.EURO__PM = dis.readFloat();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_CP_TALENSTUDIO_Job2) {

				try {

					int length = 0;

					this.Date = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.USD__AM = null;
					} else {
						this.USD__AM = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.USD__PM = null;
					} else {
						this.USD__PM = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.GBP__AM = null;
					} else {
						this.GBP__AM = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.GBP__PM = null;
					} else {
						this.GBP__PM = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.EURO__AM = null;
					} else {
						this.EURO__AM = dis.readFloat();
					}

					length = dis.readByte();
					if (length == -1) {
						this.EURO__PM = null;
					} else {
						this.EURO__PM = dis.readFloat();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Date, dos);

				// Float

				if (this.USD__AM == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.USD__AM);
				}

				// Float

				if (this.USD__PM == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.USD__PM);
				}

				// Float

				if (this.GBP__AM == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.GBP__AM);
				}

				// Float

				if (this.GBP__PM == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.GBP__PM);
				}

				// Float

				if (this.EURO__AM == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.EURO__AM);
				}

				// Float

				if (this.EURO__PM == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.EURO__PM);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Date, dos);

				// Float

				if (this.USD__AM == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.USD__AM);
				}

				// Float

				if (this.USD__PM == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.USD__PM);
				}

				// Float

				if (this.GBP__AM == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.GBP__AM);
				}

				// Float

				if (this.GBP__PM == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.GBP__PM);
				}

				// Float

				if (this.EURO__AM == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.EURO__AM);
				}

				// Float

				if (this.EURO__PM == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.EURO__PM);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Date=" + Date);
			sb.append(",USD__AM=" + String.valueOf(USD__AM));
			sb.append(",USD__PM=" + String.valueOf(USD__PM));
			sb.append(",GBP__AM=" + String.valueOf(GBP__AM));
			sb.append(",GBP__PM=" + String.valueOf(GBP__PM));
			sb.append(",EURO__AM=" + String.valueOf(EURO__AM));
			sb.append(",EURO__PM=" + String.valueOf(EURO__PM));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Date == null) {
				sb.append("<null>");
			} else {
				sb.append(Date);
			}

			sb.append("|");

			if (USD__AM == null) {
				sb.append("<null>");
			} else {
				sb.append(USD__AM);
			}

			sb.append("|");

			if (USD__PM == null) {
				sb.append("<null>");
			} else {
				sb.append(USD__PM);
			}

			sb.append("|");

			if (GBP__AM == null) {
				sb.append("<null>");
			} else {
				sb.append(GBP__AM);
			}

			sb.append("|");

			if (GBP__PM == null) {
				sb.append("<null>");
			} else {
				sb.append(GBP__PM);
			}

			sb.append("|");

			if (EURO__AM == null) {
				sb.append("<null>");
			} else {
				sb.append(EURO__AM);
			}

			sb.append("|");

			if (EURO__PM == null) {
				sb.append("<null>");
			} else {
				sb.append(EURO__PM);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tDBInput_1", "fUQpyE_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row1Struct row1 = new row1Struct();

				/**
				 * [tLogRow_1 begin ] start
				 */

				sh("tLogRow_1");

				s(currentComponent = "tLogRow_1");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row1");

				int tos_count_tLogRow_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tLogRow_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tLogRow_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tLogRow_1 = new StringBuilder();
							log4jParamters_tLogRow_1.append("Parameters:");
							log4jParamters_tLogRow_1.append("BASIC_MODE" + " = " + "false");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("TABLE_PRINT" + " = " + "true");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("VERTICAL" + " = " + "false");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("PRINT_CONTENT_WITH_LOG4J" + " = " + "true");
							log4jParamters_tLogRow_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tLogRow_1 - " + (log4jParamters_tLogRow_1));
						}
					}
					new BytesLimit65535_tLogRow_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tLogRow_1", "tLogRow_1", "tLogRow");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				///////////////////////

				class Util_tLogRow_1 {

					String[] des_top = { ".", ".", "-", "+" };

					String[] des_head = { "|=", "=|", "-", "+" };

					String[] des_bottom = { "'", "'", "-", "+" };

					String name = "";

					java.util.List<String[]> list = new java.util.ArrayList<String[]>();

					int[] colLengths = new int[7];

					public void addRow(String[] row) {

						for (int i = 0; i < 7; i++) {
							if (row[i] != null) {
								colLengths[i] = Math.max(colLengths[i], row[i].length());
							}
						}
						list.add(row);
					}

					public void setTableName(String name) {

						this.name = name;
					}

					public StringBuilder format() {

						StringBuilder sb = new StringBuilder();

						sb.append(print(des_top));

						int totals = 0;
						for (int i = 0; i < colLengths.length; i++) {
							totals = totals + colLengths[i];
						}

						// name
						sb.append("|");
						int k = 0;
						for (k = 0; k < (totals + 6 - name.length()) / 2; k++) {
							sb.append(' ');
						}
						sb.append(name);
						for (int i = 0; i < totals + 6 - name.length() - k; i++) {
							sb.append(' ');
						}
						sb.append("|\n");

						// head and rows
						sb.append(print(des_head));
						for (int i = 0; i < list.size(); i++) {

							String[] row = list.get(i);

							java.util.Formatter formatter = new java.util.Formatter(new StringBuilder());

							StringBuilder sbformat = new StringBuilder();
							sbformat.append("|%1$-");
							sbformat.append(colLengths[0]);
							sbformat.append("s");

							sbformat.append("|%2$-");
							sbformat.append(colLengths[1]);
							sbformat.append("s");

							sbformat.append("|%3$-");
							sbformat.append(colLengths[2]);
							sbformat.append("s");

							sbformat.append("|%4$-");
							sbformat.append(colLengths[3]);
							sbformat.append("s");

							sbformat.append("|%5$-");
							sbformat.append(colLengths[4]);
							sbformat.append("s");

							sbformat.append("|%6$-");
							sbformat.append(colLengths[5]);
							sbformat.append("s");

							sbformat.append("|%7$-");
							sbformat.append(colLengths[6]);
							sbformat.append("s");

							sbformat.append("|\n");

							formatter.format(sbformat.toString(), (Object[]) row);

							sb.append(formatter.toString());
							if (i == 0)
								sb.append(print(des_head)); // print the head
						}

						// end
						sb.append(print(des_bottom));
						return sb;
					}

					private StringBuilder print(String[] fillChars) {
						StringBuilder sb = new StringBuilder();
						// first column
						sb.append(fillChars[0]);
						for (int i = 0; i < colLengths[0] - fillChars[0].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);

						for (int i = 0; i < colLengths[1] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[2] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[3] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[4] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[5] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);

						// last column
						for (int i = 0; i < colLengths[6] - fillChars[1].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[1]);
						sb.append("\n");
						return sb;
					}

					public boolean isTableEmpty() {
						if (list.size() > 1)
							return false;
						return true;
					}
				}
				Util_tLogRow_1 util_tLogRow_1 = new Util_tLogRow_1();
				util_tLogRow_1.setTableName("tLogRow_1");
				util_tLogRow_1.addRow(
						new String[] { "Date", "USD__AM", "USD__PM", "GBP__AM", "GBP__PM", "EURO__AM", "EURO__PM", });
				StringBuilder strBuffer_tLogRow_1 = null;
				int nb_line_tLogRow_1 = 0;
///////////////////////    			

				/**
				 * [tLogRow_1 begin ] stop
				 */

				/**
				 * [tDBInput_1 begin ] start
				 */

				sh("tDBInput_1");

				s(currentComponent = "tDBInput_1");

				cLabel = "\"DataPrices\"";

				int tos_count_tDBInput_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tDBInput_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tDBInput_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tDBInput_1 = new StringBuilder();
							log4jParamters_tDBInput_1.append("Parameters:");
							log4jParamters_tDBInput_1.append("DB_VERSION" + " = " + "MYSQL_8");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("USE_EXISTING_CONNECTION" + " = " + "false");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("HOST" + " = " + "\"172.203.204.134\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("PORT" + " = " + "\"3306\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("DBNAME" + " = " + "\"STG_Data\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("USER" + " = " + "\"Prediqt\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("PASS" + " = " + String.valueOf(
									"enc:routine.encryption.key.v1:CBDasutD+Q3rNadfXHA/TjXXThOPJIzq7AI+Aib1ZYcwIr6PtDTq")
									.substring(0, 4) + "...");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("TABLE" + " = " + "\"DataPrices\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("QUERY" + " = "
									+ "\"SELECT    `DataPrices`.`Date`,    `DataPrices`.`USD__AM`,    `DataPrices`.`USD__PM`,    `DataPrices`.`GBP__AM`,    `DataPrices`.`GBP__PM`,    `DataPrices`.`EURO__AM`,    `DataPrices`.`EURO__PM`  FROM `DataPrices`\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("SPECIFY_DATASOURCE_ALIAS" + " = " + "false");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("PROPERTIES" + " = "
									+ "\"noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1\"");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("ENABLE_STREAM" + " = " + "false");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("TRIM_COLUMN" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Date") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("USD__AM") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("USD__PM")
									+ "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("GBP__AM") + "}, {TRIM="
									+ ("false") + ", SCHEMA_COLUMN=" + ("GBP__PM") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("EURO__AM") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("EURO__PM") + "}]");
							log4jParamters_tDBInput_1.append(" | ");
							log4jParamters_tDBInput_1.append("UNIFIED_COMPONENTS" + " = " + "tMysqlInput");
							log4jParamters_tDBInput_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tDBInput_1 - " + (log4jParamters_tDBInput_1));
						}
					}
					new BytesLimit65535_tDBInput_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tDBInput_1", "\"DataPrices\"", "tMysqlInput");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				java.util.Calendar calendar_tDBInput_1 = java.util.Calendar.getInstance();
				calendar_tDBInput_1.set(0, 0, 0, 0, 0, 0);
				java.util.Date year0_tDBInput_1 = calendar_tDBInput_1.getTime();
				int nb_line_tDBInput_1 = 0;
				java.sql.Connection conn_tDBInput_1 = null;
				String driverClass_tDBInput_1 = "com.mysql.cj.jdbc.Driver";
				java.lang.Class jdbcclazz_tDBInput_1 = java.lang.Class.forName(driverClass_tDBInput_1);
				String dbUser_tDBInput_1 = "Prediqt";

				final String decryptedPassword_tDBInput_1 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:bnvmL/vwdnuDPaxauauJQ7g5ZxThd5uzG2nO1ei+ruaC9GQZzykG");

				String dbPwd_tDBInput_1 = decryptedPassword_tDBInput_1;

				String properties_tDBInput_1 = "noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1";
				if (properties_tDBInput_1 == null || properties_tDBInput_1.trim().length() == 0) {
					properties_tDBInput_1 = "";
				}
				String url_tDBInput_1 = "jdbc:mysql://" + "172.203.204.134" + ":" + "3306" + "/" + "STG_Data" + "?"
						+ properties_tDBInput_1;

				log.debug("tDBInput_1 - Driver ClassName: " + driverClass_tDBInput_1 + ".");

				log.debug("tDBInput_1 - Connection attempt to '" + url_tDBInput_1 + "' with the username '"
						+ dbUser_tDBInput_1 + "'.");

				conn_tDBInput_1 = java.sql.DriverManager.getConnection(url_tDBInput_1, dbUser_tDBInput_1,
						dbPwd_tDBInput_1);
				log.debug("tDBInput_1 - Connection to '" + url_tDBInput_1 + "' has succeeded.");

				java.sql.Statement stmt_tDBInput_1 = conn_tDBInput_1.createStatement();

				String dbquery_tDBInput_1 = "SELECT \n  `DataPrices`.`Date`, \n  `DataPrices`.`USD__AM`, \n  `DataPrices`.`USD__PM`, \n  `DataPrices`.`GBP__AM`, \n  `Dat"
						+ "aPrices`.`GBP__PM`, \n  `DataPrices`.`EURO__AM`, \n  `DataPrices`.`EURO__PM`\n FROM `DataPrices`";

				log.debug("tDBInput_1 - Executing the query: '" + dbquery_tDBInput_1 + "'.");

				globalMap.put("tDBInput_1_QUERY", dbquery_tDBInput_1);

				java.sql.ResultSet rs_tDBInput_1 = null;

				try {
					rs_tDBInput_1 = stmt_tDBInput_1.executeQuery(dbquery_tDBInput_1);
					java.sql.ResultSetMetaData rsmd_tDBInput_1 = rs_tDBInput_1.getMetaData();
					int colQtyInRs_tDBInput_1 = rsmd_tDBInput_1.getColumnCount();

					String tmpContent_tDBInput_1 = null;

					log.debug("tDBInput_1 - Retrieving records from the database.");

					while (rs_tDBInput_1.next()) {
						nb_line_tDBInput_1++;

						if (colQtyInRs_tDBInput_1 < 1) {
							row1.Date = null;
						} else {

							row1.Date = routines.system.JDBCUtil.getString(rs_tDBInput_1, 1, false);
						}
						if (colQtyInRs_tDBInput_1 < 2) {
							row1.USD__AM = null;
						} else {

							row1.USD__AM = rs_tDBInput_1.getFloat(2);
							if (rs_tDBInput_1.wasNull()) {
								row1.USD__AM = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 3) {
							row1.USD__PM = null;
						} else {

							row1.USD__PM = rs_tDBInput_1.getFloat(3);
							if (rs_tDBInput_1.wasNull()) {
								row1.USD__PM = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 4) {
							row1.GBP__AM = null;
						} else {

							row1.GBP__AM = rs_tDBInput_1.getFloat(4);
							if (rs_tDBInput_1.wasNull()) {
								row1.GBP__AM = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 5) {
							row1.GBP__PM = null;
						} else {

							row1.GBP__PM = rs_tDBInput_1.getFloat(5);
							if (rs_tDBInput_1.wasNull()) {
								row1.GBP__PM = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 6) {
							row1.EURO__AM = null;
						} else {

							row1.EURO__AM = rs_tDBInput_1.getFloat(6);
							if (rs_tDBInput_1.wasNull()) {
								row1.EURO__AM = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 7) {
							row1.EURO__PM = null;
						} else {

							row1.EURO__PM = rs_tDBInput_1.getFloat(7);
							if (rs_tDBInput_1.wasNull()) {
								row1.EURO__PM = null;
							}
						}

						log.debug("tDBInput_1 - Retrieving the record " + nb_line_tDBInput_1 + ".");

						/**
						 * [tDBInput_1 begin ] stop
						 */

						/**
						 * [tDBInput_1 main ] start
						 */

						s(currentComponent = "tDBInput_1");

						cLabel = "\"DataPrices\"";

						tos_count_tDBInput_1++;

						/**
						 * [tDBInput_1 main ] stop
						 */

						/**
						 * [tDBInput_1 process_data_begin ] start
						 */

						s(currentComponent = "tDBInput_1");

						cLabel = "\"DataPrices\"";

						/**
						 * [tDBInput_1 process_data_begin ] stop
						 */

						/**
						 * [tLogRow_1 main ] start
						 */

						s(currentComponent = "tLogRow_1");

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "row1", "tDBInput_1", "\"DataPrices\"", "tMysqlInput", "tLogRow_1", "tLogRow_1",
								"tLogRow"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("row1 - " + (row1 == null ? "" : row1.toLogString()));
						}

///////////////////////		

						String[] row_tLogRow_1 = new String[7];

						if (row1.Date != null) { //
							row_tLogRow_1[0] = String.valueOf(row1.Date);

						} //

						if (row1.USD__AM != null) { //
							row_tLogRow_1[1] = FormatterUtils.formatUnwithE(row1.USD__AM);

						} //

						if (row1.USD__PM != null) { //
							row_tLogRow_1[2] = FormatterUtils.formatUnwithE(row1.USD__PM);

						} //

						if (row1.GBP__AM != null) { //
							row_tLogRow_1[3] = FormatterUtils.formatUnwithE(row1.GBP__AM);

						} //

						if (row1.GBP__PM != null) { //
							row_tLogRow_1[4] = FormatterUtils.formatUnwithE(row1.GBP__PM);

						} //

						if (row1.EURO__AM != null) { //
							row_tLogRow_1[5] = FormatterUtils.formatUnwithE(row1.EURO__AM);

						} //

						if (row1.EURO__PM != null) { //
							row_tLogRow_1[6] = FormatterUtils.formatUnwithE(row1.EURO__PM);

						} //

						util_tLogRow_1.addRow(row_tLogRow_1);
						nb_line_tLogRow_1++;
						log.info("tLogRow_1 - Content of row " + nb_line_tLogRow_1 + ": "
								+ TalendString.unionString("|", row_tLogRow_1));
//////

//////                    

///////////////////////    			

						tos_count_tLogRow_1++;

						/**
						 * [tLogRow_1 main ] stop
						 */

						/**
						 * [tLogRow_1 process_data_begin ] start
						 */

						s(currentComponent = "tLogRow_1");

						/**
						 * [tLogRow_1 process_data_begin ] stop
						 */

						/**
						 * [tLogRow_1 process_data_end ] start
						 */

						s(currentComponent = "tLogRow_1");

						/**
						 * [tLogRow_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 process_data_end ] start
						 */

						s(currentComponent = "tDBInput_1");

						cLabel = "\"DataPrices\"";

						/**
						 * [tDBInput_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 end ] start
						 */

						s(currentComponent = "tDBInput_1");

						cLabel = "\"DataPrices\"";

					}
				} finally {
					if (rs_tDBInput_1 != null) {
						rs_tDBInput_1.close();
					}
					if (stmt_tDBInput_1 != null) {
						stmt_tDBInput_1.close();
					}
					if (conn_tDBInput_1 != null && !conn_tDBInput_1.isClosed()) {

						log.debug("tDBInput_1 - Closing the connection to the database.");

						conn_tDBInput_1.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

						log.debug("tDBInput_1 - Connection to the database closed.");

					}

				}
				globalMap.put("tDBInput_1_NB_LINE", nb_line_tDBInput_1);
				log.debug("tDBInput_1 - Retrieved records count: " + nb_line_tDBInput_1 + " .");

				if (log.isDebugEnabled())
					log.debug("tDBInput_1 - " + ("Done."));

				ok_Hash.put("tDBInput_1", true);
				end_Hash.put("tDBInput_1", System.currentTimeMillis());

				/**
				 * [tDBInput_1 end ] stop
				 */

				/**
				 * [tLogRow_1 end ] start
				 */

				s(currentComponent = "tLogRow_1");

//////

				java.io.PrintStream consoleOut_tLogRow_1 = null;
				if (globalMap.get("tLogRow_CONSOLE") != null) {
					consoleOut_tLogRow_1 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
				} else {
					consoleOut_tLogRow_1 = new java.io.PrintStream(new java.io.BufferedOutputStream(System.out));
					globalMap.put("tLogRow_CONSOLE", consoleOut_tLogRow_1);
				}

				consoleOut_tLogRow_1.println(util_tLogRow_1.format().toString());
				consoleOut_tLogRow_1.flush();
//////
				globalMap.put("tLogRow_1_NB_LINE", nb_line_tLogRow_1);
				if (log.isInfoEnabled())
					log.info("tLogRow_1 - " + ("Printed row count: ") + (nb_line_tLogRow_1) + ("."));

///////////////////////    			

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row1", 2, 0,
						"tDBInput_1", "\"DataPrices\"", "tMysqlInput", "tLogRow_1", "tLogRow_1", "tLogRow", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tLogRow_1 - " + ("Done."));

				ok_Hash.put("tLogRow_1", true);
				end_Hash.put("tLogRow_1", System.currentTimeMillis());

				if (execStat) {
					runStat.updateStatOnConnection("OnComponentOk1", 0, "ok");
				}
				tChronometerStop_1Process(globalMap);

				/**
				 * [tLogRow_1 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_1 finally ] start
				 */

				s(currentComponent = "tDBInput_1");

				cLabel = "\"DataPrices\"";

				/**
				 * [tDBInput_1 finally ] stop
				 */

				/**
				 * [tLogRow_1 finally ] start
				 */

				s(currentComponent = "tLogRow_1");

				/**
				 * [tLogRow_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStop_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStop_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tChronometerStop_1", "Lj3uWM_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStop_1 begin ] start
				 */

				sh("tChronometerStop_1");

				s(currentComponent = "tChronometerStop_1");

				int tos_count_tChronometerStop_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tChronometerStop_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tChronometerStop_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tChronometerStop_1 = new StringBuilder();
							log4jParamters_tChronometerStop_1.append("Parameters:");
							log4jParamters_tChronometerStop_1.append("SINCE_BEGINNING" + " = " + "true");
							log4jParamters_tChronometerStop_1.append(" | ");
							log4jParamters_tChronometerStop_1.append("SINCE_STARTER" + " = " + "false");
							log4jParamters_tChronometerStop_1.append(" | ");
							log4jParamters_tChronometerStop_1.append("DISPLAY" + " = " + "true");
							log4jParamters_tChronometerStop_1.append(" | ");
							log4jParamters_tChronometerStop_1.append("DISPLAY_COMPONENT_NAME" + " = " + "true");
							log4jParamters_tChronometerStop_1.append(" | ");
							log4jParamters_tChronometerStop_1.append("CAPTION" + " = " + "\"\"");
							log4jParamters_tChronometerStop_1.append(" | ");
							log4jParamters_tChronometerStop_1.append("DISPLAY_READABLE_DURATION" + " = " + "false");
							log4jParamters_tChronometerStop_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tChronometerStop_1 - " + (log4jParamters_tChronometerStop_1));
						}
					}
					new BytesLimit65535_tChronometerStop_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tChronometerStop_1", "tChronometerStop_1", "tChronometerStop");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				long timetChronometerStop_1;

				log.info("tChronometerStop_1 - Stop time: " + System.currentTimeMillis() + " milliseconds");

				timetChronometerStop_1 = System.currentTimeMillis() - startTime;

				log.info("tChronometerStop_1 - Duration since job start: " + timetChronometerStop_1 + " milliseconds");

				System.out.print("[ tChronometerStop_1 ]  ");

				System.out.println("" + "  " + timetChronometerStop_1 + " milliseconds");

				log.info("tChronometerStop_1 - " + "" + "  " + timetChronometerStop_1 + " milliseconds");

				Long currentTimetChronometerStop_1 = System.currentTimeMillis();
				globalMap.put("tChronometerStop_1", currentTimetChronometerStop_1);

				log.info("tChronometerStop_1 - Current time " + currentTimetChronometerStop_1 + " milliseconds");

				globalMap.put("tChronometerStop_1_STOPTIME", currentTimetChronometerStop_1);
				globalMap.put("tChronometerStop_1_DURATION", timetChronometerStop_1);

				/**
				 * [tChronometerStop_1 begin ] stop
				 */

				/**
				 * [tChronometerStop_1 main ] start
				 */

				s(currentComponent = "tChronometerStop_1");

				tos_count_tChronometerStop_1++;

				/**
				 * [tChronometerStop_1 main ] stop
				 */

				/**
				 * [tChronometerStop_1 process_data_begin ] start
				 */

				s(currentComponent = "tChronometerStop_1");

				/**
				 * [tChronometerStop_1 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStop_1 process_data_end ] start
				 */

				s(currentComponent = "tChronometerStop_1");

				/**
				 * [tChronometerStop_1 process_data_end ] stop
				 */

				/**
				 * [tChronometerStop_1 end ] start
				 */

				s(currentComponent = "tChronometerStop_1");

				if (log.isDebugEnabled())
					log.debug("tChronometerStop_1 - " + ("Done."));

				ok_Hash.put("tChronometerStop_1", true);
				end_Hash.put("tChronometerStop_1", System.currentTimeMillis());

				/**
				 * [tChronometerStop_1 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStop_1 finally ] start
				 */

				s(currentComponent = "tChronometerStop_1");

				/**
				 * [tChronometerStop_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStop_1_SUBPROCESS_STATE", 1);
	}

	public void connectionStatsLogs_CommitProcess(final java.util.Map<String, Object> globalMap)
			throws TalendException {
		globalMap.put("connectionStatsLogs_Commit_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("connectionStatsLogs_Commit", "ilEOLu_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [connectionStatsLogs_Commit begin ] start
				 */

				sh("connectionStatsLogs_Commit");

				s(currentComponent = "connectionStatsLogs_Commit");

				int tos_count_connectionStatsLogs_Commit = 0;

				if (log.isDebugEnabled())
					log.debug("connectionStatsLogs_Commit - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_connectionStatsLogs_Commit {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_connectionStatsLogs_Commit = new StringBuilder();
							log4jParamters_connectionStatsLogs_Commit.append("Parameters:");
							log4jParamters_connectionStatsLogs_Commit
									.append("CONNECTION" + " = " + "connectionStatsLogs");
							log4jParamters_connectionStatsLogs_Commit.append(" | ");
							log4jParamters_connectionStatsLogs_Commit.append("CLOSE" + " = " + "false");
							log4jParamters_connectionStatsLogs_Commit.append(" | ");
							if (log.isDebugEnabled())
								log.debug(
										"connectionStatsLogs_Commit - " + (log4jParamters_connectionStatsLogs_Commit));
						}
					}
					new BytesLimit65535_connectionStatsLogs_Commit().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("connectionStatsLogs_Commit", "connectionStatsLogs_Commit", "tMysqlCommit");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				/**
				 * [connectionStatsLogs_Commit begin ] stop
				 */

				/**
				 * [connectionStatsLogs_Commit main ] start
				 */

				s(currentComponent = "connectionStatsLogs_Commit");

				java.sql.Connection conn_connectionStatsLogs_Commit = (java.sql.Connection) globalMap
						.get("conn_connectionStatsLogs");

				if (conn_connectionStatsLogs_Commit != null && !conn_connectionStatsLogs_Commit.isClosed()) {

					log.debug("connectionStatsLogs_Commit - Connection 'connectionStatsLogs' starting to commit.");

					conn_connectionStatsLogs_Commit.commit();

					log.debug("connectionStatsLogs_Commit - Connection 'connectionStatsLogs' commit has succeeded.");

				}

				tos_count_connectionStatsLogs_Commit++;

				/**
				 * [connectionStatsLogs_Commit main ] stop
				 */

				/**
				 * [connectionStatsLogs_Commit process_data_begin ] start
				 */

				s(currentComponent = "connectionStatsLogs_Commit");

				/**
				 * [connectionStatsLogs_Commit process_data_begin ] stop
				 */

				/**
				 * [connectionStatsLogs_Commit process_data_end ] start
				 */

				s(currentComponent = "connectionStatsLogs_Commit");

				/**
				 * [connectionStatsLogs_Commit process_data_end ] stop
				 */

				/**
				 * [connectionStatsLogs_Commit end ] start
				 */

				s(currentComponent = "connectionStatsLogs_Commit");

				if (log.isDebugEnabled())
					log.debug("connectionStatsLogs_Commit - " + ("Done."));

				ok_Hash.put("connectionStatsLogs_Commit", true);
				end_Hash.put("connectionStatsLogs_Commit", System.currentTimeMillis());

				/**
				 * [connectionStatsLogs_Commit end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [connectionStatsLogs_Commit finally ] start
				 */

				s(currentComponent = "connectionStatsLogs_Commit");

				/**
				 * [connectionStatsLogs_Commit finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("connectionStatsLogs_Commit_SUBPROCESS_STATE", 1);
	}

	public void connectionStatsLogsProcess(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("connectionStatsLogs_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("connectionStatsLogs", "8s2zPI_");

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [connectionStatsLogs begin ] start
				 */

				sh("connectionStatsLogs");

				s(currentComponent = "connectionStatsLogs");

				int tos_count_connectionStatsLogs = 0;

				if (log.isDebugEnabled())
					log.debug("connectionStatsLogs - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_connectionStatsLogs {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_connectionStatsLogs = new StringBuilder();
							log4jParamters_connectionStatsLogs.append("Parameters:");
							log4jParamters_connectionStatsLogs.append("DB_VERSION" + " = " + "MYSQL_8");
							log4jParamters_connectionStatsLogs.append(" | ");
							log4jParamters_connectionStatsLogs.append("HOST" + " = " + "\"172.203.204.134\"");
							log4jParamters_connectionStatsLogs.append(" | ");
							log4jParamters_connectionStatsLogs.append("PORT" + " = " + "\"3306\"");
							log4jParamters_connectionStatsLogs.append(" | ");
							log4jParamters_connectionStatsLogs.append("DBNAME" + " = " + "\"STG_Data\"");
							log4jParamters_connectionStatsLogs.append(" | ");
							log4jParamters_connectionStatsLogs.append("PROPERTIES" + " = "
									+ "\"noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1\"");
							log4jParamters_connectionStatsLogs.append(" | ");
							log4jParamters_connectionStatsLogs.append("USER" + " = " + "\"Prediqt\"");
							log4jParamters_connectionStatsLogs.append(" | ");
							log4jParamters_connectionStatsLogs.append("PASS" + " = " + String.valueOf(
									"enc:routine.encryption.key.v1:iAKGVE5Cb/r7W/b5KbJezu/xv0QCFvOPdEwPNdHq98vHrqFO0n0v")
									.substring(0, 4) + "...");
							log4jParamters_connectionStatsLogs.append(" | ");
							log4jParamters_connectionStatsLogs.append("USE_SHARED_CONNECTION" + " = " + "true");
							log4jParamters_connectionStatsLogs.append(" | ");
							log4jParamters_connectionStatsLogs.append("SHARED_CONNECTION_NAME" + " = "
									+ "\"jdbc:mysql://172.203.204.134:3306/?noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1\"+\"_StatsAndLog_Shared_Connection\"");
							log4jParamters_connectionStatsLogs.append(" | ");
							log4jParamters_connectionStatsLogs.append("AUTO_COMMIT" + " = " + "false");
							log4jParamters_connectionStatsLogs.append(" | ");
							if (log.isDebugEnabled())
								log.debug("connectionStatsLogs - " + (log4jParamters_connectionStatsLogs));
						}
					}
					new BytesLimit65535_connectionStatsLogs().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("connectionStatsLogs", "connectionStatsLogs", "tMysqlConnection");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				String properties_connectionStatsLogs = "noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1";
				if (properties_connectionStatsLogs == null || properties_connectionStatsLogs.trim().length() == 0) {
					properties_connectionStatsLogs = "rewriteBatchedStatements=true&allowLoadLocalInfile=true";
				} else {
					if (!properties_connectionStatsLogs.contains("rewriteBatchedStatements=")) {
						properties_connectionStatsLogs += "&rewriteBatchedStatements=true";
					}

					if (!properties_connectionStatsLogs.contains("allowLoadLocalInfile=")) {
						properties_connectionStatsLogs += "&allowLoadLocalInfile=true";
					}
				}

				String url_connectionStatsLogs = "jdbc:mysql://" + "172.203.204.134" + ":" + "3306" + "/" + "STG_Data"
						+ "?" + properties_connectionStatsLogs;
				String dbUser_connectionStatsLogs = "Prediqt";

				final String decryptedPassword_connectionStatsLogs = routines.system.PasswordEncryptUtil
						.decryptPassword(
								"enc:routine.encryption.key.v1:8ntUqixWp7/vD/RBSNzv3bMHHtLhd0LVvwPOqQ8ieM2csdgjzEsZ");
				String dbPwd_connectionStatsLogs = decryptedPassword_connectionStatsLogs;

				java.sql.Connection conn_connectionStatsLogs = null;

				SharedDBConnectionLog4j.initLogger(log.getName(), "connectionStatsLogs");
				String sharedConnectionName_connectionStatsLogs = "jdbc:mysql://172.203.204.134:3306/?noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1"
						+ "_StatsAndLog_Shared_Connection";
				conn_connectionStatsLogs = SharedDBConnectionLog4j.getDBConnection("com.mysql.cj.jdbc.Driver",
						url_connectionStatsLogs, dbUser_connectionStatsLogs, dbPwd_connectionStatsLogs,
						sharedConnectionName_connectionStatsLogs);
				globalMap.put("conn_connectionStatsLogs", conn_connectionStatsLogs);
				if (null != conn_connectionStatsLogs) {

					log.debug("connectionStatsLogs - Connection is set auto commit to 'false'.");
					conn_connectionStatsLogs.setAutoCommit(false);
				}

				globalMap.put("db_connectionStatsLogs", "STG_Data");

				/**
				 * [connectionStatsLogs begin ] stop
				 */

				/**
				 * [connectionStatsLogs main ] start
				 */

				s(currentComponent = "connectionStatsLogs");

				tos_count_connectionStatsLogs++;

				/**
				 * [connectionStatsLogs main ] stop
				 */

				/**
				 * [connectionStatsLogs process_data_begin ] start
				 */

				s(currentComponent = "connectionStatsLogs");

				/**
				 * [connectionStatsLogs process_data_begin ] stop
				 */

				/**
				 * [connectionStatsLogs process_data_end ] start
				 */

				s(currentComponent = "connectionStatsLogs");

				/**
				 * [connectionStatsLogs process_data_end ] stop
				 */

				/**
				 * [connectionStatsLogs end ] start
				 */

				s(currentComponent = "connectionStatsLogs");

				if (log.isDebugEnabled())
					log.debug("connectionStatsLogs - " + ("Done."));

				ok_Hash.put("connectionStatsLogs", true);
				end_Hash.put("connectionStatsLogs", System.currentTimeMillis());

				/**
				 * [connectionStatsLogs end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [connectionStatsLogs finally ] start
				 */

				s(currentComponent = "connectionStatsLogs");

				/**
				 * [connectionStatsLogs finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("connectionStatsLogs_SUBPROCESS_STATE", 1);
	}

	public static class row_talendStats_STATSStruct
			implements routines.system.IPersistableRow<row_talendStats_STATSStruct> {
		final static byte[] commonByteArrayLock_CP_TALENSTUDIO_Job2 = new byte[0];
		static byte[] commonByteArray_CP_TALENSTUDIO_Job2 = new byte[0];

		public java.util.Date moment;

		public java.util.Date getMoment() {
			return this.moment;
		}

		public Boolean momentIsNullable() {
			return true;
		}

		public Boolean momentIsKey() {
			return false;
		}

		public Integer momentLength() {
			return 0;
		}

		public Integer momentPrecision() {
			return 0;
		}

		public String momentDefault() {

			return "";

		}

		public String momentComment() {

			return null;

		}

		public String momentPattern() {

			return "yyyy-MM-dd HH:mm:ss";

		}

		public String momentOriginalDbColumnName() {

			return "moment";

		}

		public String pid;

		public String getPid() {
			return this.pid;
		}

		public Boolean pidIsNullable() {
			return true;
		}

		public Boolean pidIsKey() {
			return false;
		}

		public Integer pidLength() {
			return 20;
		}

		public Integer pidPrecision() {
			return 0;
		}

		public String pidDefault() {

			return "";

		}

		public String pidComment() {

			return null;

		}

		public String pidPattern() {

			return null;

		}

		public String pidOriginalDbColumnName() {

			return "pid";

		}

		public String father_pid;

		public String getFather_pid() {
			return this.father_pid;
		}

		public Boolean father_pidIsNullable() {
			return true;
		}

		public Boolean father_pidIsKey() {
			return false;
		}

		public Integer father_pidLength() {
			return 20;
		}

		public Integer father_pidPrecision() {
			return 0;
		}

		public String father_pidDefault() {

			return "";

		}

		public String father_pidComment() {

			return null;

		}

		public String father_pidPattern() {

			return null;

		}

		public String father_pidOriginalDbColumnName() {

			return "father_pid";

		}

		public String root_pid;

		public String getRoot_pid() {
			return this.root_pid;
		}

		public Boolean root_pidIsNullable() {
			return true;
		}

		public Boolean root_pidIsKey() {
			return false;
		}

		public Integer root_pidLength() {
			return 20;
		}

		public Integer root_pidPrecision() {
			return 0;
		}

		public String root_pidDefault() {

			return "";

		}

		public String root_pidComment() {

			return null;

		}

		public String root_pidPattern() {

			return null;

		}

		public String root_pidOriginalDbColumnName() {

			return "root_pid";

		}

		public Long system_pid;

		public Long getSystem_pid() {
			return this.system_pid;
		}

		public Boolean system_pidIsNullable() {
			return true;
		}

		public Boolean system_pidIsKey() {
			return false;
		}

		public Integer system_pidLength() {
			return 8;
		}

		public Integer system_pidPrecision() {
			return 0;
		}

		public String system_pidDefault() {

			return "";

		}

		public String system_pidComment() {

			return null;

		}

		public String system_pidPattern() {

			return null;

		}

		public String system_pidOriginalDbColumnName() {

			return "system_pid";

		}

		public String project;

		public String getProject() {
			return this.project;
		}

		public Boolean projectIsNullable() {
			return true;
		}

		public Boolean projectIsKey() {
			return false;
		}

		public Integer projectLength() {
			return 50;
		}

		public Integer projectPrecision() {
			return 0;
		}

		public String projectDefault() {

			return "";

		}

		public String projectComment() {

			return null;

		}

		public String projectPattern() {

			return null;

		}

		public String projectOriginalDbColumnName() {

			return "project";

		}

		public String job;

		public String getJob() {
			return this.job;
		}

		public Boolean jobIsNullable() {
			return true;
		}

		public Boolean jobIsKey() {
			return false;
		}

		public Integer jobLength() {
			return 255;
		}

		public Integer jobPrecision() {
			return 0;
		}

		public String jobDefault() {

			return "";

		}

		public String jobComment() {

			return null;

		}

		public String jobPattern() {

			return null;

		}

		public String jobOriginalDbColumnName() {

			return "job";

		}

		public String job_repository_id;

		public String getJob_repository_id() {
			return this.job_repository_id;
		}

		public Boolean job_repository_idIsNullable() {
			return true;
		}

		public Boolean job_repository_idIsKey() {
			return false;
		}

		public Integer job_repository_idLength() {
			return 255;
		}

		public Integer job_repository_idPrecision() {
			return 0;
		}

		public String job_repository_idDefault() {

			return "";

		}

		public String job_repository_idComment() {

			return null;

		}

		public String job_repository_idPattern() {

			return null;

		}

		public String job_repository_idOriginalDbColumnName() {

			return "job_repository_id";

		}

		public String job_version;

		public String getJob_version() {
			return this.job_version;
		}

		public Boolean job_versionIsNullable() {
			return true;
		}

		public Boolean job_versionIsKey() {
			return false;
		}

		public Integer job_versionLength() {
			return 255;
		}

		public Integer job_versionPrecision() {
			return 0;
		}

		public String job_versionDefault() {

			return "";

		}

		public String job_versionComment() {

			return null;

		}

		public String job_versionPattern() {

			return null;

		}

		public String job_versionOriginalDbColumnName() {

			return "job_version";

		}

		public String context;

		public String getContext() {
			return this.context;
		}

		public Boolean contextIsNullable() {
			return true;
		}

		public Boolean contextIsKey() {
			return false;
		}

		public Integer contextLength() {
			return 50;
		}

		public Integer contextPrecision() {
			return 0;
		}

		public String contextDefault() {

			return "";

		}

		public String contextComment() {

			return null;

		}

		public String contextPattern() {

			return null;

		}

		public String contextOriginalDbColumnName() {

			return "context";

		}

		public String origin;

		public String getOrigin() {
			return this.origin;
		}

		public Boolean originIsNullable() {
			return true;
		}

		public Boolean originIsKey() {
			return false;
		}

		public Integer originLength() {
			return 255;
		}

		public Integer originPrecision() {
			return 0;
		}

		public String originDefault() {

			return "";

		}

		public String originComment() {

			return null;

		}

		public String originPattern() {

			return null;

		}

		public String originOriginalDbColumnName() {

			return "origin";

		}

		public String message_type;

		public String getMessage_type() {
			return this.message_type;
		}

		public Boolean message_typeIsNullable() {
			return true;
		}

		public Boolean message_typeIsKey() {
			return false;
		}

		public Integer message_typeLength() {
			return 255;
		}

		public Integer message_typePrecision() {
			return 0;
		}

		public String message_typeDefault() {

			return "";

		}

		public String message_typeComment() {

			return null;

		}

		public String message_typePattern() {

			return null;

		}

		public String message_typeOriginalDbColumnName() {

			return "message_type";

		}

		public String message;

		public String getMessage() {
			return this.message;
		}

		public Boolean messageIsNullable() {
			return true;
		}

		public Boolean messageIsKey() {
			return false;
		}

		public Integer messageLength() {
			return 255;
		}

		public Integer messagePrecision() {
			return 0;
		}

		public String messageDefault() {

			return "";

		}

		public String messageComment() {

			return null;

		}

		public String messagePattern() {

			return null;

		}

		public String messageOriginalDbColumnName() {

			return "message";

		}

		public Long duration;

		public Long getDuration() {
			return this.duration;
		}

		public Boolean durationIsNullable() {
			return true;
		}

		public Boolean durationIsKey() {
			return false;
		}

		public Integer durationLength() {
			return 8;
		}

		public Integer durationPrecision() {
			return 0;
		}

		public String durationDefault() {

			return "";

		}

		public String durationComment() {

			return null;

		}

		public String durationPattern() {

			return null;

		}

		public String durationOriginalDbColumnName() {

			return "duration";

		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_CP_TALENSTUDIO_Job2.length) {
					if (length < 1024 && commonByteArray_CP_TALENSTUDIO_Job2.length == 0) {
						commonByteArray_CP_TALENSTUDIO_Job2 = new byte[1024];
					} else {
						commonByteArray_CP_TALENSTUDIO_Job2 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_CP_TALENSTUDIO_Job2, 0, length);
				strReturn = new String(commonByteArray_CP_TALENSTUDIO_Job2, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_CP_TALENSTUDIO_Job2.length) {
					if (length < 1024 && commonByteArray_CP_TALENSTUDIO_Job2.length == 0) {
						commonByteArray_CP_TALENSTUDIO_Job2 = new byte[1024];
					} else {
						commonByteArray_CP_TALENSTUDIO_Job2 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_CP_TALENSTUDIO_Job2, 0, length);
				strReturn = new String(commonByteArray_CP_TALENSTUDIO_Job2, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_CP_TALENSTUDIO_Job2) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.father_pid = readString(dis);

					this.root_pid = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.system_pid = null;
					} else {
						this.system_pid = dis.readLong();
					}

					this.project = readString(dis);

					this.job = readString(dis);

					this.job_repository_id = readString(dis);

					this.job_version = readString(dis);

					this.context = readString(dis);

					this.origin = readString(dis);

					this.message_type = readString(dis);

					this.message = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_CP_TALENSTUDIO_Job2) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.father_pid = readString(dis);

					this.root_pid = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.system_pid = null;
					} else {
						this.system_pid = dis.readLong();
					}

					this.project = readString(dis);

					this.job = readString(dis);

					this.job_repository_id = readString(dis);

					this.job_version = readString(dis);

					this.context = readString(dis);

					this.origin = readString(dis);

					this.message_type = readString(dis);

					this.message = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.root_pid, dos);

				// Long

				if (this.system_pid == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.system_pid);
				}

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.job_repository_id, dos);

				// String

				writeString(this.job_version, dos);

				// String

				writeString(this.context, dos);

				// String

				writeString(this.origin, dos);

				// String

				writeString(this.message_type, dos);

				// String

				writeString(this.message, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.root_pid, dos);

				// Long

				if (this.system_pid == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.system_pid);
				}

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.job_repository_id, dos);

				// String

				writeString(this.job_version, dos);

				// String

				writeString(this.context, dos);

				// String

				writeString(this.origin, dos);

				// String

				writeString(this.message_type, dos);

				// String

				writeString(this.message, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("moment=" + String.valueOf(moment));
			sb.append(",pid=" + pid);
			sb.append(",father_pid=" + father_pid);
			sb.append(",root_pid=" + root_pid);
			sb.append(",system_pid=" + String.valueOf(system_pid));
			sb.append(",project=" + project);
			sb.append(",job=" + job);
			sb.append(",job_repository_id=" + job_repository_id);
			sb.append(",job_version=" + job_version);
			sb.append(",context=" + context);
			sb.append(",origin=" + origin);
			sb.append(",message_type=" + message_type);
			sb.append(",message=" + message);
			sb.append(",duration=" + String.valueOf(duration));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (moment == null) {
				sb.append("<null>");
			} else {
				sb.append(moment);
			}

			sb.append("|");

			if (pid == null) {
				sb.append("<null>");
			} else {
				sb.append(pid);
			}

			sb.append("|");

			if (father_pid == null) {
				sb.append("<null>");
			} else {
				sb.append(father_pid);
			}

			sb.append("|");

			if (root_pid == null) {
				sb.append("<null>");
			} else {
				sb.append(root_pid);
			}

			sb.append("|");

			if (system_pid == null) {
				sb.append("<null>");
			} else {
				sb.append(system_pid);
			}

			sb.append("|");

			if (project == null) {
				sb.append("<null>");
			} else {
				sb.append(project);
			}

			sb.append("|");

			if (job == null) {
				sb.append("<null>");
			} else {
				sb.append(job);
			}

			sb.append("|");

			if (job_repository_id == null) {
				sb.append("<null>");
			} else {
				sb.append(job_repository_id);
			}

			sb.append("|");

			if (job_version == null) {
				sb.append("<null>");
			} else {
				sb.append(job_version);
			}

			sb.append("|");

			if (context == null) {
				sb.append("<null>");
			} else {
				sb.append(context);
			}

			sb.append("|");

			if (origin == null) {
				sb.append("<null>");
			} else {
				sb.append(origin);
			}

			sb.append("|");

			if (message_type == null) {
				sb.append("<null>");
			} else {
				sb.append(message_type);
			}

			sb.append("|");

			if (message == null) {
				sb.append("<null>");
			} else {
				sb.append(message);
			}

			sb.append("|");

			if (duration == null) {
				sb.append("<null>");
			} else {
				sb.append(duration);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row_talendStats_STATSStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void talendStats_STATSProcess(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("talendStats_STATS_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("talendStats_STATS", "HirRVY_");

		String currentVirtualComponent = null;

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row_talendStats_STATSStruct row_talendStats_STATS = new row_talendStats_STATSStruct();

				/**
				 * [talendStats_DB begin ] start
				 */

				sh("talendStats_DB");

				currentVirtualComponent = "talendStats_DB";

				s(currentComponent = "talendStats_DB");

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "Main");
				}

				int tos_count_talendStats_DB = 0;

				if (log.isDebugEnabled())
					log.debug("talendStats_DB - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_talendStats_DB {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_talendStats_DB = new StringBuilder();
							log4jParamters_talendStats_DB.append("Parameters:");
							log4jParamters_talendStats_DB.append("USE_EXISTING_CONNECTION" + " = " + "true");
							log4jParamters_talendStats_DB.append(" | ");
							log4jParamters_talendStats_DB.append("CONNECTION" + " = " + "connectionStatsLogs");
							log4jParamters_talendStats_DB.append(" | ");
							log4jParamters_talendStats_DB.append("TABLE" + " = " + "\"Auditoria\"");
							log4jParamters_talendStats_DB.append(" | ");
							log4jParamters_talendStats_DB.append("TABLE_ACTION" + " = " + "CREATE_IF_NOT_EXISTS");
							log4jParamters_talendStats_DB.append(" | ");
							log4jParamters_talendStats_DB.append("DATA_ACTION" + " = " + "INSERT");
							log4jParamters_talendStats_DB.append(" | ");
							log4jParamters_talendStats_DB.append("DIE_ON_ERROR" + " = " + "false");
							log4jParamters_talendStats_DB.append(" | ");
							log4jParamters_talendStats_DB.append("USE_BATCH_SIZE" + " = " + "true");
							log4jParamters_talendStats_DB.append(" | ");
							log4jParamters_talendStats_DB.append("BATCH_SIZE" + " = " + "10000");
							log4jParamters_talendStats_DB.append(" | ");
							log4jParamters_talendStats_DB.append("ADD_COLS" + " = " + "[]");
							log4jParamters_talendStats_DB.append(" | ");
							log4jParamters_talendStats_DB.append("USE_FIELD_OPTIONS" + " = " + "false");
							log4jParamters_talendStats_DB.append(" | ");
							log4jParamters_talendStats_DB.append("USE_HINT_OPTIONS" + " = " + "false");
							log4jParamters_talendStats_DB.append(" | ");
							log4jParamters_talendStats_DB.append("ENABLE_DEBUG_MODE" + " = " + "false");
							log4jParamters_talendStats_DB.append(" | ");
							log4jParamters_talendStats_DB.append("ON_DUPLICATE_KEY_UPDATE" + " = " + "false");
							log4jParamters_talendStats_DB.append(" | ");
							if (log.isDebugEnabled())
								log.debug("talendStats_DB - " + (log4jParamters_talendStats_DB));
						}
					}
					new BytesLimit65535_talendStats_DB().limitLog4jByte();
				}

				int nb_line_talendStats_DB = 0;
				int nb_line_update_talendStats_DB = 0;
				int nb_line_inserted_talendStats_DB = 0;
				int nb_line_deleted_talendStats_DB = 0;
				int nb_line_rejected_talendStats_DB = 0;

				int deletedCount_talendStats_DB = 0;
				int updatedCount_talendStats_DB = 0;
				int insertedCount_talendStats_DB = 0;
				int rowsToCommitCount_talendStats_DB = 0;
				int rejectedCount_talendStats_DB = 0;

				String tableName_talendStats_DB = "Auditoria";
				boolean whetherReject_talendStats_DB = false;

				java.util.Calendar calendar_talendStats_DB = java.util.Calendar.getInstance();
				calendar_talendStats_DB.set(1, 0, 1, 0, 0, 0);
				long year1_talendStats_DB = calendar_talendStats_DB.getTime().getTime();
				calendar_talendStats_DB.set(10000, 0, 1, 0, 0, 0);
				long year10000_talendStats_DB = calendar_talendStats_DB.getTime().getTime();
				long date_talendStats_DB;

				java.sql.Connection conn_talendStats_DB = null;
				conn_talendStats_DB = (java.sql.Connection) globalMap.get("conn_connectionStatsLogs");

				if (log.isDebugEnabled())
					log.debug("talendStats_DB - " + ("Uses an existing connection with username '")
							+ (conn_talendStats_DB.getMetaData().getUserName()) + ("'. Connection URL: ")
							+ (conn_talendStats_DB.getMetaData().getURL()) + ("."));

				if (log.isDebugEnabled())
					log.debug("talendStats_DB - " + ("Connection is set auto commit to '")
							+ (conn_talendStats_DB.getAutoCommit()) + ("'."));

				int count_talendStats_DB = 0;

				// [%connection%][checktable][tableName]
				String keyCheckTable_talendStats_DB = conn_talendStats_DB + "[checktable]" + "[" + "Auditoria" + "]";

				if (GlobalResource.resourceMap.get(keyCheckTable_talendStats_DB) == null) {// }

					synchronized (GlobalResource.resourceLockMap.get(keyCheckTable_talendStats_DB)) {// }
						if (GlobalResource.resourceMap.get(keyCheckTable_talendStats_DB) == null) {// }
							java.sql.DatabaseMetaData dbMetaData_talendStats_DB = conn_talendStats_DB.getMetaData();
							java.sql.ResultSet rsTable_talendStats_DB = dbMetaData_talendStats_DB.getTables("STG_Data",
									null, null, new String[] { "TABLE" });
							boolean whetherExist_talendStats_DB = false;
							while (rsTable_talendStats_DB.next()) {
								String table_talendStats_DB = rsTable_talendStats_DB.getString("TABLE_NAME");
								if (table_talendStats_DB.equalsIgnoreCase("Auditoria")) {
									whetherExist_talendStats_DB = true;
									break;
								}
							}
							if (!whetherExist_talendStats_DB) {
								try (java.sql.Statement stmtCreate_talendStats_DB = conn_talendStats_DB
										.createStatement()) {
									if (log.isDebugEnabled())
										log.debug("talendStats_DB - " + ("Creating") + (" table '")
												+ (tableName_talendStats_DB) + ("'."));
									stmtCreate_talendStats_DB.execute("CREATE TABLE `" + tableName_talendStats_DB
											+ "`(`moment` DATETIME ,`pid` VARCHAR(20)  ,`father_pid` VARCHAR(20)  ,`root_pid` VARCHAR(20)  ,`system_pid` BIGINT(8)  ,`project` VARCHAR(50)  ,`job` VARCHAR(255)  ,`job_repository_id` VARCHAR(255)  ,`job_version` VARCHAR(255)  ,`context` VARCHAR(50)  ,`origin` VARCHAR(255)  ,`message_type` VARCHAR(255)  ,`message` VARCHAR(255)  ,`duration` BIGINT(8)  )");
									if (log.isDebugEnabled())
										log.debug("talendStats_DB - " + ("Create") + (" table '")
												+ (tableName_talendStats_DB) + ("' has succeeded."));
								}
							}
							GlobalResource.resourceMap.put(keyCheckTable_talendStats_DB, true);
							// {{{
						} // end of if
					} // end synchronized
				}

				String insert_talendStats_DB = "INSERT INTO `" + "Auditoria"
						+ "` (`moment`,`pid`,`father_pid`,`root_pid`,`system_pid`,`project`,`job`,`job_repository_id`,`job_version`,`context`,`origin`,`message_type`,`message`,`duration`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				java.sql.PreparedStatement pstmt_talendStats_DB = null;
				// [%connection%][psmt][tableName]
				String keyPsmt_talendStats_DB = conn_talendStats_DB + "[psmt]" + "[" + "Auditoria" + "]";
				pstmt_talendStats_DB = SharedDBPreparedStatement.getSharedPreparedStatement(conn_talendStats_DB,
						insert_talendStats_DB, keyPsmt_talendStats_DB);
				resourceMap.put("pstmt_talendStats_DB", pstmt_talendStats_DB);
				resourceMap.put("keyPsmt_talendStats_DB", keyPsmt_talendStats_DB);

				/**
				 * [talendStats_DB begin ] stop
				 */

				/**
				 * [talendStats_STATS begin ] start
				 */

				sh("talendStats_STATS");

				currentVirtualComponent = "talendStats_STATS";

				s(currentComponent = "talendStats_STATS");

				int tos_count_talendStats_STATS = 0;

				if (log.isDebugEnabled())
					log.debug("talendStats_STATS - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_talendStats_STATS {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_talendStats_STATS = new StringBuilder();
							log4jParamters_talendStats_STATS.append("Parameters:");
							if (log.isDebugEnabled())
								log.debug("talendStats_STATS - " + (log4jParamters_talendStats_STATS));
						}
					}
					new BytesLimit65535_talendStats_STATS().limitLog4jByte();
				}

				for (StatCatcherUtils.StatCatcherMessage scm : talendStats_STATS.getMessages()) {
					row_talendStats_STATS.pid = pid;
					row_talendStats_STATS.root_pid = rootPid;
					row_talendStats_STATS.father_pid = fatherPid;
					row_talendStats_STATS.project = projectName;
					row_talendStats_STATS.job = jobName;
					row_talendStats_STATS.context = contextStr;
					row_talendStats_STATS.origin = (scm.getOrigin() == null || scm.getOrigin().length() < 1 ? null
							: scm.getOrigin());
					row_talendStats_STATS.message = scm.getMessage();
					row_talendStats_STATS.duration = scm.getDuration();
					row_talendStats_STATS.moment = scm.getMoment();
					row_talendStats_STATS.message_type = scm.getMessageType();
					row_talendStats_STATS.job_version = scm.getJobVersion();
					row_talendStats_STATS.job_repository_id = scm.getJobId();
					row_talendStats_STATS.system_pid = scm.getSystemPid();

					/**
					 * [talendStats_STATS begin ] stop
					 */

					/**
					 * [talendStats_STATS main ] start
					 */

					currentVirtualComponent = "talendStats_STATS";

					s(currentComponent = "talendStats_STATS");

					tos_count_talendStats_STATS++;

					/**
					 * [talendStats_STATS main ] stop
					 */

					/**
					 * [talendStats_STATS process_data_begin ] start
					 */

					currentVirtualComponent = "talendStats_STATS";

					s(currentComponent = "talendStats_STATS");

					/**
					 * [talendStats_STATS process_data_begin ] stop
					 */

					/**
					 * [talendStats_DB main ] start
					 */

					currentVirtualComponent = "talendStats_DB";

					s(currentComponent = "talendStats_DB");

					if (execStat) {
						runStat.updateStatOnConnection(iterateId, 1, 1

								, "Main"

						);
					}

					whetherReject_talendStats_DB = false;
					if (row_talendStats_STATS.moment != null) {
						date_talendStats_DB = row_talendStats_STATS.moment.getTime();
						if (date_talendStats_DB < year1_talendStats_DB
								|| date_talendStats_DB >= year10000_talendStats_DB) {
							pstmt_talendStats_DB.setString(1, "0000-00-00 00:00:00");
						} else {
							pstmt_talendStats_DB.setTimestamp(1, new java.sql.Timestamp(date_talendStats_DB));
						}
					} else {
						pstmt_talendStats_DB.setNull(1, java.sql.Types.DATE);
					}

					if (row_talendStats_STATS.pid == null) {
						pstmt_talendStats_DB.setNull(2, java.sql.Types.VARCHAR);
					} else {
						pstmt_talendStats_DB.setString(2, row_talendStats_STATS.pid);
					}

					if (row_talendStats_STATS.father_pid == null) {
						pstmt_talendStats_DB.setNull(3, java.sql.Types.VARCHAR);
					} else {
						pstmt_talendStats_DB.setString(3, row_talendStats_STATS.father_pid);
					}

					if (row_talendStats_STATS.root_pid == null) {
						pstmt_talendStats_DB.setNull(4, java.sql.Types.VARCHAR);
					} else {
						pstmt_talendStats_DB.setString(4, row_talendStats_STATS.root_pid);
					}

					if (row_talendStats_STATS.system_pid == null) {
						pstmt_talendStats_DB.setNull(5, java.sql.Types.INTEGER);
					} else {
						pstmt_talendStats_DB.setLong(5, row_talendStats_STATS.system_pid);
					}

					if (row_talendStats_STATS.project == null) {
						pstmt_talendStats_DB.setNull(6, java.sql.Types.VARCHAR);
					} else {
						pstmt_talendStats_DB.setString(6, row_talendStats_STATS.project);
					}

					if (row_talendStats_STATS.job == null) {
						pstmt_talendStats_DB.setNull(7, java.sql.Types.VARCHAR);
					} else {
						pstmt_talendStats_DB.setString(7, row_talendStats_STATS.job);
					}

					if (row_talendStats_STATS.job_repository_id == null) {
						pstmt_talendStats_DB.setNull(8, java.sql.Types.VARCHAR);
					} else {
						pstmt_talendStats_DB.setString(8, row_talendStats_STATS.job_repository_id);
					}

					if (row_talendStats_STATS.job_version == null) {
						pstmt_talendStats_DB.setNull(9, java.sql.Types.VARCHAR);
					} else {
						pstmt_talendStats_DB.setString(9, row_talendStats_STATS.job_version);
					}

					if (row_talendStats_STATS.context == null) {
						pstmt_talendStats_DB.setNull(10, java.sql.Types.VARCHAR);
					} else {
						pstmt_talendStats_DB.setString(10, row_talendStats_STATS.context);
					}

					if (row_talendStats_STATS.origin == null) {
						pstmt_talendStats_DB.setNull(11, java.sql.Types.VARCHAR);
					} else {
						pstmt_talendStats_DB.setString(11, row_talendStats_STATS.origin);
					}

					if (row_talendStats_STATS.message_type == null) {
						pstmt_talendStats_DB.setNull(12, java.sql.Types.VARCHAR);
					} else {
						pstmt_talendStats_DB.setString(12, row_talendStats_STATS.message_type);
					}

					if (row_talendStats_STATS.message == null) {
						pstmt_talendStats_DB.setNull(13, java.sql.Types.VARCHAR);
					} else {
						pstmt_talendStats_DB.setString(13, row_talendStats_STATS.message);
					}

					if (row_talendStats_STATS.duration == null) {
						pstmt_talendStats_DB.setNull(14, java.sql.Types.INTEGER);
					} else {
						pstmt_talendStats_DB.setLong(14, row_talendStats_STATS.duration);
					}

					try {
						nb_line_talendStats_DB++;
						int processedCount_talendStats_DB = pstmt_talendStats_DB.executeUpdate();
						insertedCount_talendStats_DB += processedCount_talendStats_DB;
						rowsToCommitCount_talendStats_DB += processedCount_talendStats_DB;
						if (log.isDebugEnabled())
							log.debug("talendStats_DB - " + ("Inserting") + (" the record ") + (nb_line_talendStats_DB)
									+ ("."));
					} catch (java.lang.Exception e) {
						globalMap.put("talendStats_DB_ERROR_MESSAGE", e.getMessage());
						whetherReject_talendStats_DB = true;
						log.error("talendStats_DB - " + (e.getMessage()));
						System.err.print(e.getMessage());
					}

					tos_count_talendStats_DB++;

					/**
					 * [talendStats_DB main ] stop
					 */

					/**
					 * [talendStats_DB process_data_begin ] start
					 */

					currentVirtualComponent = "talendStats_DB";

					s(currentComponent = "talendStats_DB");

					/**
					 * [talendStats_DB process_data_begin ] stop
					 */

					/**
					 * [talendStats_DB process_data_end ] start
					 */

					currentVirtualComponent = "talendStats_DB";

					s(currentComponent = "talendStats_DB");

					/**
					 * [talendStats_DB process_data_end ] stop
					 */

					/**
					 * [talendStats_STATS process_data_end ] start
					 */

					currentVirtualComponent = "talendStats_STATS";

					s(currentComponent = "talendStats_STATS");

					/**
					 * [talendStats_STATS process_data_end ] stop
					 */

					/**
					 * [talendStats_STATS end ] start
					 */

					currentVirtualComponent = "talendStats_STATS";

					s(currentComponent = "talendStats_STATS");

				}

				if (log.isDebugEnabled())
					log.debug("talendStats_STATS - " + ("Done."));

				ok_Hash.put("talendStats_STATS", true);
				end_Hash.put("talendStats_STATS", System.currentTimeMillis());

				/**
				 * [talendStats_STATS end ] stop
				 */

				/**
				 * [talendStats_DB end ] start
				 */

				currentVirtualComponent = "talendStats_DB";

				s(currentComponent = "talendStats_DB");

				if (pstmt_talendStats_DB != null) {

					SharedDBPreparedStatement.releasePreparedStatement(keyPsmt_talendStats_DB);
					resourceMap.remove("keyPsmt_talendStats_DB");

				}

				resourceMap.put("statementClosed_talendStats_DB", true);

				nb_line_deleted_talendStats_DB = nb_line_deleted_talendStats_DB + deletedCount_talendStats_DB;
				nb_line_update_talendStats_DB = nb_line_update_talendStats_DB + updatedCount_talendStats_DB;
				nb_line_inserted_talendStats_DB = nb_line_inserted_talendStats_DB + insertedCount_talendStats_DB;
				nb_line_rejected_talendStats_DB = nb_line_rejected_talendStats_DB + rejectedCount_talendStats_DB;

				globalMap.put("talendStats_DB_NB_LINE", nb_line_talendStats_DB);
				globalMap.put("talendStats_DB_NB_LINE_UPDATED", nb_line_update_talendStats_DB);
				globalMap.put("talendStats_DB_NB_LINE_INSERTED", nb_line_inserted_talendStats_DB);
				globalMap.put("talendStats_DB_NB_LINE_DELETED", nb_line_deleted_talendStats_DB);
				globalMap.put("talendStats_DB_NB_LINE_REJECTED", nb_line_rejected_talendStats_DB);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "Main");
				}

				if (log.isDebugEnabled())
					log.debug("talendStats_DB - " + ("Done."));

				ok_Hash.put("talendStats_DB", true);
				end_Hash.put("talendStats_DB", System.currentTimeMillis());

				/**
				 * [talendStats_DB end ] stop
				 */

			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT",
						"CONNECTION:SUBJOB_OK:talendStats_STATS:sub_ok_talendStats_connectionStatsLogs_Commit", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("sub_ok_talendStats_connectionStatsLogs_Commit", 0, "ok");
			}

			connectionStatsLogs_CommitProcess(globalMap);

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			te.setVirtualComponentName(currentVirtualComponent);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [talendStats_STATS finally ] start
				 */

				currentVirtualComponent = "talendStats_STATS";

				s(currentComponent = "talendStats_STATS");

				/**
				 * [talendStats_STATS finally ] stop
				 */

				/**
				 * [talendStats_DB finally ] start
				 */

				currentVirtualComponent = "talendStats_DB";

				s(currentComponent = "talendStats_DB");

				String keyPsmt_talendStats_DB = null;
				if ((keyPsmt_talendStats_DB = (String) resourceMap.remove("keyPsmt_talendStats_DB")) != null) {
					SharedDBPreparedStatement.releasePreparedStatement(keyPsmt_talendStats_DB);
				}
				if (resourceMap.get("statementClosed_talendStats_DB") == null) {
					java.sql.PreparedStatement pstmtToClose_talendStats_DB = null;
					if ((pstmtToClose_talendStats_DB = (java.sql.PreparedStatement) resourceMap
							.remove("pstmt_talendStats_DB")) != null) {
						pstmtToClose_talendStats_DB.close();
					}
				}

				/**
				 * [talendStats_DB finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("talendStats_STATS_SUBPROCESS_STATE", 1);
	}

	public void talendJobLogProcess(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("talendJobLog_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [talendJobLog begin ] start
				 */

				sh("talendJobLog");

				s(currentComponent = "talendJobLog");

				int tos_count_talendJobLog = 0;

				for (JobStructureCatcherUtils.JobStructureCatcherMessage jcm : talendJobLog.getMessages()) {
					org.talend.job.audit.JobContextBuilder builder_talendJobLog = org.talend.job.audit.JobContextBuilder
							.create().jobName(jcm.job_name).jobId(jcm.job_id).jobVersion(jcm.job_version)
							.custom("process_id", jcm.pid).custom("thread_id", jcm.tid).custom("pid", pid)
							.custom("father_pid", fatherPid).custom("root_pid", rootPid);
					org.talend.logging.audit.Context log_context_talendJobLog = null;

					if (jcm.log_type == JobStructureCatcherUtils.LogType.PERFORMANCE) {
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.sourceId(jcm.sourceId)
								.sourceLabel(jcm.sourceLabel).sourceConnectorType(jcm.sourceComponentName)
								.targetId(jcm.targetId).targetLabel(jcm.targetLabel)
								.targetConnectorType(jcm.targetComponentName).connectionName(jcm.current_connector)
								.rows(jcm.row_count).duration(duration).build();
						auditLogger_talendJobLog.flowExecution(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBSTART) {
						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).build();
						auditLogger_talendJobLog.jobstart(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBEND) {
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).duration(duration)
								.status(jcm.status).build();
						auditLogger_talendJobLog.jobstop(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.RUNCOMPONENT) {
						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment)
								.connectorType(jcm.component_name).connectorId(jcm.component_id)
								.connectorLabel(jcm.component_label).build();
						auditLogger_talendJobLog.runcomponent(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWINPUT) {// log current component
																							// input line
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
								.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
								.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
								.rows(jcm.total_row_number).duration(duration).build();
						auditLogger_talendJobLog.flowInput(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWOUTPUT) {// log current component
																								// output/reject line
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
								.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
								.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
								.rows(jcm.total_row_number).duration(duration).build();
						auditLogger_talendJobLog.flowOutput(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBERROR) {
						java.lang.Exception e_talendJobLog = jcm.exception;
						if (e_talendJobLog != null) {
							try (java.io.StringWriter sw_talendJobLog = new java.io.StringWriter();
									java.io.PrintWriter pw_talendJobLog = new java.io.PrintWriter(sw_talendJobLog)) {
								e_talendJobLog.printStackTrace(pw_talendJobLog);
								builder_talendJobLog.custom("stacktrace", sw_talendJobLog.getBuffer().substring(0,
										java.lang.Math.min(sw_talendJobLog.getBuffer().length(), 512)));
							}
						}

						if (jcm.extra_info != null) {
							builder_talendJobLog.connectorId(jcm.component_id).custom("extra_info", jcm.extra_info);
						}

						log_context_talendJobLog = builder_talendJobLog
								.connectorType(jcm.component_id.substring(0, jcm.component_id.lastIndexOf('_')))
								.connectorId(jcm.component_id)
								.connectorLabel(jcm.component_label == null ? jcm.component_id : jcm.component_label)
								.build();

						auditLogger_talendJobLog.exception(log_context_talendJobLog);
					}

				}

				/**
				 * [talendJobLog begin ] stop
				 */

				/**
				 * [talendJobLog main ] start
				 */

				s(currentComponent = "talendJobLog");

				tos_count_talendJobLog++;

				/**
				 * [talendJobLog main ] stop
				 */

				/**
				 * [talendJobLog process_data_begin ] start
				 */

				s(currentComponent = "talendJobLog");

				/**
				 * [talendJobLog process_data_begin ] stop
				 */

				/**
				 * [talendJobLog process_data_end ] start
				 */

				s(currentComponent = "talendJobLog");

				/**
				 * [talendJobLog process_data_end ] stop
				 */

				/**
				 * [talendJobLog end ] start
				 */

				s(currentComponent = "talendJobLog");

				ok_Hash.put("talendJobLog", true);
				end_Hash.put("talendJobLog", System.currentTimeMillis());

				/**
				 * [talendJobLog end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [talendJobLog finally ] start
				 */

				s(currentComponent = "talendJobLog");

				/**
				 * [talendJobLog finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("talendJobLog_SUBPROCESS_STATE", 1);
	}

	public String resuming_logs_dir_path = null;
	public String resuming_checkpoint_path = null;
	public String parent_part_launcher = null;
	private String resumeEntryMethodName = null;
	private boolean globalResumeTicket = false;

	public boolean watch = false;
	// portStats is null, it means don't execute the statistics
	public Integer portStats = null;
	public int portTraces = 4334;
	public String clientHost;
	public String defaultClientHost = "localhost";
	public String contextStr = "Default";
	public boolean isDefaultContext = true;
	public String pid = "0";
	public String rootPid = null;
	public String fatherPid = null;
	public String fatherNode = null;
	public long startTime = 0;
	public boolean isChildJob = false;
	public String log4jLevel = "";

	private boolean enableLogStash;

	private boolean execStat = true;

	private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
		protected java.util.Map<String, String> initialValue() {
			java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
			threadRunResultMap.put("errorCode", null);
			threadRunResultMap.put("status", "");
			return threadRunResultMap;
		};
	};

	protected PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	private final static java.util.Properties jobInfo = new java.util.Properties();
	private final static java.util.Map<String, String> mdcInfo = new java.util.HashMap<>();
	private final static java.util.concurrent.atomic.AtomicLong subJobPidCounter = new java.util.concurrent.atomic.AtomicLong();

	public static void main(String[] args) {
		final Job2 Job2Class = new Job2();

		int exitCode = Job2Class.runJobInTOS(args);
		if (exitCode == 0) {
			log.info("TalendJob: 'Job2' - Done.");
		}

		System.exit(exitCode);
	}

	private void getjobInfo() {
		final String TEMPLATE_PATH = "src/main/templates/jobInfo_template.properties";
		final String BUILD_PATH = "../jobInfo.properties";
		final String path = this.getClass().getResource("").getPath();
		if (path.lastIndexOf("target") > 0) {
			final java.io.File templateFile = new java.io.File(
					path.substring(0, path.lastIndexOf("target")).concat(TEMPLATE_PATH));
			if (templateFile.exists()) {
				readJobInfo(templateFile);
				return;
			}
		}
		readJobInfo(new java.io.File(BUILD_PATH));
	}

	private void readJobInfo(java.io.File jobInfoFile) {

		if (jobInfoFile.exists()) {
			try (java.io.InputStream is = new java.io.FileInputStream(jobInfoFile)) {
				jobInfo.load(is);
			} catch (IOException e) {

				log.debug("Read jobInfo.properties file fail: " + e.getMessage());

			}
		}
		log.info(String.format("Project name: %s\tJob name: %s\tGIT Commit ID: %s\tTalend Version: %s", projectName,
				jobName, jobInfo.getProperty("gitCommitId"), "8.0.1.20241219_0901-patch"));

	}

	public String[][] runJob(String[] args) {

		int exitCode = runJobInTOS(args);
		String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

		return bufferValue;
	}

	public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;

		return hastBufferOutput;
	}

	public int runJobInTOS(String[] args) {
		// reset status
		status = "";

		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}

		final boolean enableCBP = false;
		boolean inOSGi = routines.system.BundleUtils.inOSGi();

		if (!inOSGi) {
			if (org.talend.metrics.CBPClient.getInstanceForCurrentVM() == null) {
				try {
					org.talend.metrics.CBPClient.startListenIfNotStarted(enableCBP, true);
				} catch (java.lang.Exception e) {
					errorCode = 1;
					status = "failure";
					e.printStackTrace();
					return 1;
				}
			}
		}

		enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));

		if (!"".equals(log4jLevel)) {

			if ("trace".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.TRACE);
			} else if ("debug".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.DEBUG);
			} else if ("info".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.INFO);
			} else if ("warn".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.WARN);
			} else if ("error".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.ERROR);
			} else if ("fatal".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.FATAL);
			} else if ("off".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.OFF);
			}
			org.apache.logging.log4j.core.config.Configurator
					.setLevel(org.apache.logging.log4j.LogManager.getRootLogger().getName(), log.getLevel());

		}

		getjobInfo();
		log.info("TalendJob: 'Job2' - Start.");

		java.util.Set<Object> jobInfoKeys = jobInfo.keySet();
		for (Object jobInfoKey : jobInfoKeys) {
			org.slf4j.MDC.put("_" + jobInfoKey.toString(), jobInfo.get(jobInfoKey).toString());
		}
		org.slf4j.MDC.put("_pid", pid);
		org.slf4j.MDC.put("_rootPid", rootPid);
		org.slf4j.MDC.put("_fatherPid", fatherPid);
		org.slf4j.MDC.put("_projectName", projectName);
		org.slf4j.MDC.put("_startTimestamp", java.time.ZonedDateTime.now(java.time.ZoneOffset.UTC)
				.format(java.time.format.DateTimeFormatter.ISO_INSTANT));
		org.slf4j.MDC.put("_jobRepositoryId", "_YhFwoBl6EfCJEOKxOC-s9w");
		org.slf4j.MDC.put("_compiledAtTimestamp", "2025-04-14T22:26:23.289503800Z");

		java.lang.management.RuntimeMXBean mx = java.lang.management.ManagementFactory.getRuntimeMXBean();
		String[] mxNameTable = mx.getName().split("@"); //$NON-NLS-1$
		if (mxNameTable.length == 2) {
			org.slf4j.MDC.put("_systemPid", mxNameTable[0]);
		} else {
			org.slf4j.MDC.put("_systemPid", String.valueOf(java.lang.Thread.currentThread().getId()));
		}

		if (enableLogStash) {
			java.util.Properties properties_talendJobLog = new java.util.Properties();
			properties_talendJobLog.setProperty("root.logger", "audit");
			properties_talendJobLog.setProperty("encoding", "UTF-8");
			properties_talendJobLog.setProperty("application.name", "Talend Studio");
			properties_talendJobLog.setProperty("service.name", "Talend Studio Job");
			properties_talendJobLog.setProperty("instance.name", "Talend Studio Job Instance");
			properties_talendJobLog.setProperty("propagate.appender.exceptions", "none");
			properties_talendJobLog.setProperty("log.appender", "file");
			properties_talendJobLog.setProperty("appender.file.path", "audit.json");
			properties_talendJobLog.setProperty("appender.file.maxsize", "52428800");
			properties_talendJobLog.setProperty("appender.file.maxbackup", "20");
			properties_talendJobLog.setProperty("host", "false");

			System.getProperties().stringPropertyNames().stream().filter(it -> it.startsWith("audit.logger."))
					.forEach(key -> properties_talendJobLog.setProperty(key.substring("audit.logger.".length()),
							System.getProperty(key)));

			org.apache.logging.log4j.core.config.Configurator
					.setLevel(properties_talendJobLog.getProperty("root.logger"), org.apache.logging.log4j.Level.DEBUG);

			auditLogger_talendJobLog = org.talend.job.audit.JobEventAuditLoggerFactory
					.createJobAuditLogger(properties_talendJobLog);
		}

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		if (pid == null || "0".equals(pid)) {
			pid = TalendString.getAsciiRandomString(6);
		}

		org.slf4j.MDC.put("_pid", pid);

		if (rootPid == null) {
			rootPid = pid;
		}

		org.slf4j.MDC.put("_rootPid", rootPid);

		if (fatherPid == null) {
			fatherPid = pid;
		} else {
			isChildJob = true;
		}
		org.slf4j.MDC.put("_fatherPid", fatherPid);

		if (portStats != null) {
			// portStats = -1; //for testing
			if (portStats < 0 || portStats > 65535) {
				// issue:10869, the portStats is invalid, so this client socket can't open
				System.err.println("The statistics socket port " + portStats + " is invalid.");
				execStat = false;
			}
		} else {
			execStat = false;
		}

		try {
			java.util.Dictionary<String, Object> jobProperties = null;
			if (inOSGi) {
				jobProperties = routines.system.BundleUtils.getJobProperties(jobName);

				if (jobProperties != null && jobProperties.get("context") != null) {
					contextStr = (String) jobProperties.get("context");
				}

				if (jobProperties != null && jobProperties.get("taskExecutionId") != null) {
					taskExecutionId = (String) jobProperties.get("taskExecutionId");
				}

				// extract ids from parent route
				if (null == taskExecutionId || taskExecutionId.isEmpty()) {
					for (String arg : args) {
						if (arg.startsWith("--context_param")
								&& (arg.contains("taskExecutionId") || arg.contains("jobExecutionId"))) {

							String keyValue = arg.replace("--context_param", "");
							String[] parts = keyValue.split("=");
							String[] cleanParts = java.util.Arrays.stream(parts).filter(s -> !s.isEmpty())
									.toArray(String[]::new);
							if (cleanParts.length == 2) {
								String key = cleanParts[0];
								String value = cleanParts[1];
								if ("taskExecutionId".equals(key.trim()) && null != value) {
									taskExecutionId = value.trim();
								} else if ("jobExecutionId".equals(key.trim()) && null != value) {
									jobExecutionId = value.trim();
								}
							}
						}
					}
				}
			}

			// first load default key-value pairs from application.properties
			if (isStandaloneMS) {
				context.putAll(this.getDefaultProperties());
			}
			// call job/subjob with an existing context, like: --context=production. if
			// without this parameter, there will use the default context instead.
			java.io.InputStream inContext = Job2.class.getClassLoader()
					.getResourceAsStream("cp_talenstudio/job2_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = Job2.class.getClassLoader()
						.getResourceAsStream("config/contexts/" + contextStr + ".properties");
			}
			if (inContext != null) {
				try {
					// defaultProps is in order to keep the original context value
					if (context != null && context.isEmpty()) {
						defaultProps.load(inContext);
						if (inOSGi && jobProperties != null) {
							java.util.Enumeration<String> keys = jobProperties.keys();
							while (keys.hasMoreElements()) {
								String propKey = keys.nextElement();
								if (defaultProps.containsKey(propKey)) {
									defaultProps.put(propKey, (String) jobProperties.get(propKey));
								}
							}
						}
						context = new ContextProperties(defaultProps);
					}
					if (isStandaloneMS) {
						// override context key-value pairs if provided using --context=contextName
						defaultProps.load(inContext);
						context.putAll(defaultProps);
					}
				} finally {
					inContext.close();
				}
			} else if (!isDefaultContext) {
				// print info and job continue to run, for case: context_param is not empty.
				System.err.println("Could not find the context " + contextStr);
			}
			// override key-value pairs if provided via --config.location=file1.file2 OR
			// --config.additional-location=file1,file2
			if (isStandaloneMS) {
				context.putAll(this.getAdditionalProperties());
			}

			// override key-value pairs if provide via command line like
			// --key1=value1,--key2=value2
			if (!context_param.isEmpty()) {
				context.putAll(context_param);
				// set types for params from parentJobs
				for (Object key : context_param.keySet()) {
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
			}
			class ContextProcessing {
				private void processContext_0() {
				}

				public void processAllContext() {
					processContext_0();
				}
			}

			new ContextProcessing().processAllContext();
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		// get context value from parent directly
		if (parentContextMap != null && !parentContextMap.isEmpty()) {
		}

		// Resume: init the resumeUtil
		resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
		resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
		resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
		// Resume: jobStart
		resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "",
				"", "", "", "", resumeUtil.convertToJsonText(context, ContextProperties.class, parametersToEncrypt));

		org.slf4j.MDC.put("_context", contextStr);
		log.info("TalendJob: 'Job2' - Started.");
		java.util.Optional.ofNullable(org.slf4j.MDC.getCopyOfContextMap()).ifPresent(mdcInfo::putAll);

		if (execStat) {
			try {
				runStat.openSocket(!isChildJob);
				runStat.setAllPID(rootPid, fatherPid, pid, jobName);
				runStat.startThreadStat(clientHost, portStats);
				runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
			} catch (java.io.IOException ioException) {
				ioException.printStackTrace();
			}
		}

		java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
		globalMap.put("concurrentHashMap", concurrentHashMap);

		long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;

		startTime = System.currentTimeMillis();
		talendStats_STATS.addMessage("begin");

		this.globalResumeTicket = true;// to run tPreJob

		try {
			errorCode = null;
			preStaLogConProcess(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_preStaLogCon) {
			globalMap.put("preStaLogCon_SUBPROCESS_STATE", -1);

			e_preStaLogCon.printStackTrace();

		}

		try {
			talendStats_STATSProcess(globalMap);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
		if (enableLogStash) {
			talendJobLog.addJobStartMessage();
			try {
				talendJobLogProcess(globalMap);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		this.globalResumeTicket = false;// to run others jobs

		try {
			errorCode = null;
			tChronometerStart_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tChronometerStart_1) {
			globalMap.put("tChronometerStart_1_SUBPROCESS_STATE", -1);

			e_tChronometerStart_1.printStackTrace();

		} catch (Error error_tChronometerStart_1) {
			end = System.currentTimeMillis();
			talendStats_STATS.addMessage("failure", (end - startTime));
			try {
				talendStats_STATSProcess(globalMap);
			} catch (Exception e_talendStats_STATS) {
				e_talendStats_STATS.printStackTrace();
			}
			throw error_tChronometerStart_1;
		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : Job2");
		}
		talendStats_STATS.addMessage(status == "" ? "end" : status, (end - startTime));
		try {
			talendStats_STATSProcess(globalMap);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
		if (enableLogStash) {
			talendJobLog.addJobEndMessage(startTime, end, status);
			try {
				talendJobLogProcess(globalMap);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		if (execStat) {
			runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
			runStat.stopThreadStat();
		}
		if (!inOSGi) {
			if (org.talend.metrics.CBPClient.getInstanceForCurrentVM() != null) {
				s("none");
				org.talend.metrics.CBPClient.getInstanceForCurrentVM().sendData();
			}
		}

		int returnCode = 0;

		if (errorCode == null) {
			returnCode = status != null && status.equals("failure") ? 1 : 0;
		} else {
			returnCode = errorCode.intValue();
		}
		resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
				"" + returnCode, "", "", "");
		resumeUtil.flush();

		org.slf4j.MDC.remove("_subJobName");
		org.slf4j.MDC.remove("_subJobPid");
		org.slf4j.MDC.remove("_systemPid");
		log.info("TalendJob: 'Job2' - Finished - status: " + status + " returnCode: " + returnCode);

		return returnCode;

	}

	// only for OSGi env
	public void destroy() {
		// add CBP code for OSGI Executions
		if (null != taskExecutionId && !taskExecutionId.isEmpty()) {
			try {
				org.talend.metrics.DataReadTracker.setExecutionId(taskExecutionId, jobExecutionId, false);
				org.talend.metrics.DataReadTracker.sealCounter();
				org.talend.metrics.DataReadTracker.reset();
			} catch (Exception | NoClassDefFoundError e) {
				// ignore
			}
		}

		closeSqlDbConnections();

	}

	private void closeSqlDbConnections() {
		try {
			Object obj_conn;
			obj_conn = globalMap.remove("conn_connectionStatsLogs");
			if (null != obj_conn) {
				((java.sql.Connection) obj_conn).close();
			}
		} catch (java.lang.Exception e) {
		}
	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();
		connections.put("conn_connectionStatsLogs", globalMap.get("conn_connectionStatsLogs"));

		return connections;
	}

	private void evalParam(String arg) {
		if (arg.startsWith("--resuming_logs_dir_path")) {
			resuming_logs_dir_path = arg.substring(25);
		} else if (arg.startsWith("--resuming_checkpoint_path")) {
			resuming_checkpoint_path = arg.substring(27);
		} else if (arg.startsWith("--parent_part_launcher")) {
			parent_part_launcher = arg.substring(23);
		} else if (arg.startsWith("--watch")) {
			watch = true;
		} else if (arg.startsWith("--stat_port=")) {
			String portStatsStr = arg.substring(12);
			if (portStatsStr != null && !portStatsStr.equals("null")) {
				portStats = Integer.parseInt(portStatsStr);
			}
		} else if (arg.startsWith("--trace_port=")) {
			portTraces = Integer.parseInt(arg.substring(13));
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
			isDefaultContext = false;
		} else if (arg.startsWith("--father_pid=")) {
			fatherPid = arg.substring(13);
		} else if (arg.startsWith("--root_pid=")) {
			rootPid = arg.substring(11);
		} else if (arg.startsWith("--father_node=")) {
			fatherNode = arg.substring(14);
		} else if (arg.startsWith("--pid=")) {
			pid = arg.substring(6);
		} else if (arg.startsWith("--context_type")) {
			String keyValue = arg.substring(15);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.setContextType(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
				}

			}

		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
				}
			}
		} else if (arg.startsWith("--context_file")) {
			String keyValue = arg.substring(15);
			String filePath = new String(java.util.Base64.getDecoder().decode(keyValue));
			java.nio.file.Path contextFile = java.nio.file.Paths.get(filePath);
			try (java.io.BufferedReader reader = java.nio.file.Files.newBufferedReader(contextFile)) {
				String line;
				while ((line = reader.readLine()) != null) {
					int index = -1;
					if ((index = line.indexOf('=')) > -1) {
						if (line.startsWith("--context_param")) {
							if ("id_Password".equals(context_param.getContextType(line.substring(16, index)))) {
								context_param.put(line.substring(16, index),
										routines.system.PasswordEncryptUtil.decryptPassword(line.substring(index + 1)));
							} else {
								context_param.put(line.substring(16, index), line.substring(index + 1));
							}
						} else {// --context_type
							context_param.setContextType(line.substring(15, index), line.substring(index + 1));
						}
					}
				}
			} catch (java.io.IOException e) {
				System.err.println("Could not load the context file: " + filePath);
				e.printStackTrace();
			}
		} else if (arg.startsWith("--log4jLevel=")) {
			log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {// for trunjob call
			final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
	}

	private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

	private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" }, { "\\'", "\'" }, { "\\r", "\r" },
			{ "\\f", "\f" }, { "\\b", "\b" }, { "\\t", "\t" } };

	private String replaceEscapeChars(String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0], currIndex);
				if (index >= 0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0],
							strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the
			// result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getStatus() {
		return status;
	}

	ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 * 166686 characters generated by Talend Cloud Data Fabric on the 14 de abril de
 * 2025, 17:26:23 ECT
 ************************************************************************************************/