package com.baosight.imc.xb.ai.domain;

import com.baosight.iplat4j.core.data.DaoEPBase;
import com.baosight.iplat4j.core.ei.EiColumn;
import com.baosight.iplat4j.core.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: <br>
 * Title:Txbai0202.java <br>
 * Description: 智能员工助理反馈情况静态表<br>
 *
 * Copyrigth:Baosight Software LTD.co Copyright (c) 2019. <br>
 *
 * @version 1.0
 * @history 2024-10-24 14:45:02 create
 */
public class XBAI0202 extends DaoEPBase {

	private static final long serialVersionUID = 1L;

	public static final String FIELD_UUID = "uuid";
	public static final String FIELD_EVALUATE = "evaluate";
	public static final String FIELD_EVALUATE_ID = "evaluateId";
	public static final String FIELD_EVALUATE_TYPE = "evaluateType";
	public static final String FIELD_REC_CREATE_TIME = "recCreateTime";
	public static final String FIELD_REC_CREATOR = "recCreator";
	public static final String FIELD_REC_CREATOR_NAME = "recCreatorName";
	public static final String FIELD_REC_REVISE_TIME = "recReviseTime";
	public static final String FIELD_REC_REVISOR = "recRevisor";
	public static final String FIELD_REC_REVISOR_NAME = "recRevisorName";
	public static final String FIELD_ARCHIVE_FLAG = "archiveFlag";
	public static final String FIELD_SEG_NO = "segNo";
	public static final String FIELD_DEL_FLAG = "delFlag";

	public static final String COL_UUID = "UUID";
	public static final String COL_EVALUATE = "EVALUATE";
	public static final String COL_EVALUATE_ID = "EVALUATE_ID";
	public static final String COL_EVALUATE_TYPE = "EVALUATE_TYPE";
	public static final String COL_REC_CREATE_TIME = "REC_CREATE_TIME";
	public static final String COL_REC_CREATOR = "REC_CREATOR";
	public static final String COL_REC_CREATOR_NAME = "REC_CREATOR_NAME";
	public static final String COL_REC_REVISE_TIME = "REC_REVISE_TIME";
	public static final String COL_REC_REVISOR = "REC_REVISOR";
	public static final String COL_REC_REVISOR_NAME = "REC_REVISOR_NAME";
	public static final String COL_ARCHIVE_FLAG = "ARCHIVE_FLAG";
	public static final String COL_SEG_NO = "SEG_NO";
	public static final String COL_DEL_FLAG = "DEL_FLAG";

	public static final String QUERY = "txbai0202.query";
	public static final String COUNT = "txbai0202.count";
	public static final String INSERT = "txbai0202.insert";
	public static final String UPDATE = "txbai0202.update";
	public static final String DELETE = "txbai0202.delete";

	private String uuid = " ";
	private String evaluate = "0";
	private String evaluateId = " ";
	private String evaluateType = " ";
	private String recCreateTime = " ";
	private String recCreator = " ";
	private String recCreatorName = " ";
	private String recReviseTime = " ";
	private String recRevisor = " ";
	private String recRevisorName = " ";
	private String archiveFlag = "0";
	private String segNo = " ";
	private String delFlag = "0";

	/**
	 * initialize the metadata.
	 */
	public void initMetaData() {
		EiColumn eiColumn;

		eiColumn = new EiColumn(FIELD_UUID);
		eiColumn.setPrimaryKey(true);
		eiColumn.setFieldLength(36);
		eiColumn.setDescName(" ");
		eiMetadata.addMeta(eiColumn);

		eiColumn = new EiColumn(FIELD_EVALUATE);
		eiColumn.setFieldLength(1);
		eiColumn.setDescName(" ");
		eiMetadata.addMeta(eiColumn);

		eiColumn = new EiColumn(FIELD_EVALUATE_ID);
		eiColumn.setFieldLength(50);
		eiColumn.setDescName(" ");
		eiMetadata.addMeta(eiColumn);

		eiColumn = new EiColumn(FIELD_EVALUATE_TYPE);
		eiColumn.setFieldLength(100);
		eiColumn.setDescName(" ");
		eiMetadata.addMeta(eiColumn);

		eiColumn = new EiColumn(FIELD_REC_CREATE_TIME);
		eiColumn.setFieldLength(17);
		eiColumn.setDescName(" ");
		eiMetadata.addMeta(eiColumn);

		eiColumn = new EiColumn(FIELD_REC_CREATOR);
		eiColumn.setFieldLength(50);
		eiColumn.setDescName(" ");
		eiMetadata.addMeta(eiColumn);

		eiColumn = new EiColumn(FIELD_REC_CREATOR_NAME);
		eiColumn.setFieldLength(100);
		eiColumn.setDescName(" ");
		eiMetadata.addMeta(eiColumn);

		eiColumn = new EiColumn(FIELD_REC_REVISE_TIME);
		eiColumn.setFieldLength(17);
		eiColumn.setDescName(" ");
		eiMetadata.addMeta(eiColumn);

		eiColumn = new EiColumn(FIELD_REC_REVISOR);
		eiColumn.setFieldLength(50);
		eiColumn.setDescName(" ");
		eiMetadata.addMeta(eiColumn);

		eiColumn = new EiColumn(FIELD_REC_REVISOR_NAME);
		eiColumn.setFieldLength(100);
		eiColumn.setDescName(" ");
		eiMetadata.addMeta(eiColumn);

		eiColumn = new EiColumn(FIELD_ARCHIVE_FLAG);
		eiColumn.setFieldLength(1);
		eiColumn.setDescName(" ");
		eiMetadata.addMeta(eiColumn);

		eiColumn = new EiColumn(FIELD_SEG_NO);
		eiColumn.setFieldLength(50);
		eiColumn.setDescName(" ");
		eiMetadata.addMeta(eiColumn);

		eiColumn = new EiColumn(FIELD_DEL_FLAG);
		eiColumn.setFieldLength(1);
		eiColumn.setDescName(" ");
		eiMetadata.addMeta(eiColumn);


	}

	/**
	 * the constructor.
	 */
	public XBAI0202() {
		initMetaData();
	}

	/**
	 * get the uuid .
	 * @return the uuid
	 */
	public String getUuid() {
		return this.uuid;
	}

	/**
	 * set the uuid .
	 *
	 * @param uuid 
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	/**
	 * get the evaluate .
	 * @return the evaluate
	 */
	public String getEvaluate() {
		return this.evaluate;
	}

	/**
	 * set the evaluate .
	 *
	 * @param evaluate 
	 */
	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	/**
	 * get the evaluateId .
	 * @return the evaluateId
	 */
	public String getEvaluateId() {
		return this.evaluateId;
	}

	/**
	 * set the evaluateId .
	 *
	 * @param evaluateId 
	 */
	public void setEvaluateId(String evaluateId) {
		this.evaluateId = evaluateId;
	}
	/**
	 * get the evaluateType .
	 * @return the evaluateType
	 */
	public String getEvaluateType() {
		return this.evaluateType;
	}

	/**
	 * set the evaluateType .
	 *
	 * @param evaluateType 
	 */
	public void setEvaluateType(String evaluateType) {
		this.evaluateType = evaluateType;
	}
	/**
	 * get the recCreateTime .
	 * @return the recCreateTime
	 */
	public String getRecCreateTime() {
		return this.recCreateTime;
	}

	/**
	 * set the recCreateTime .
	 *
	 * @param recCreateTime 
	 */
	@Override
	public void setRecCreateTime(String recCreateTime) {
		this.recCreateTime = recCreateTime;
	}
	/**
	 * get the recCreator .
	 * @return the recCreator
	 */
	public String getRecCreator() {
		return this.recCreator;
	}

	/**
	 * set the recCreator .
	 *
	 * @param recCreator 
	 */
	@Override
	public void setRecCreator(String recCreator) {
		this.recCreator = recCreator;
	}
	/**
	 * get the recCreatorName .
	 * @return the recCreatorName
	 */
	public String getRecCreatorName() {
		return this.recCreatorName;
	}

	/**
	 * set the recCreatorName .
	 *
	 * @param recCreatorName 
	 */
	public void setRecCreatorName(String recCreatorName) {
		this.recCreatorName = recCreatorName;
	}
	/**
	 * get the recReviseTime .
	 * @return the recReviseTime
	 */
	public String getRecReviseTime() {
		return this.recReviseTime;
	}

	/**
	 * set the recReviseTime .
	 *
	 * @param recReviseTime 
	 */
	@Override
	public void setRecReviseTime(String recReviseTime) {
		this.recReviseTime = recReviseTime;
	}
	/**
	 * get the recRevisor .
	 * @return the recRevisor
	 */
	public String getRecRevisor() {
		return this.recRevisor;
	}

	/**
	 * set the recRevisor .
	 *
	 * @param recRevisor 
	 */
	@Override
	public void setRecRevisor(String recRevisor) {
		this.recRevisor = recRevisor;
	}
	/**
	 * get the recRevisorName .
	 * @return the recRevisorName
	 */
	public String getRecRevisorName() {
		return this.recRevisorName;
	}

	/**
	 * set the recRevisorName .
	 *
	 * @param recRevisorName 
	 */
	public void setRecRevisorName(String recRevisorName) {
		this.recRevisorName = recRevisorName;
	}
	/**
	 * get the archiveFlag .
	 * @return the archiveFlag
	 */
	public String getArchiveFlag() {
		return this.archiveFlag;
	}

	/**
	 * set the archiveFlag .
	 *
	 * @param archiveFlag 
	 */
	@Override
	public void setArchiveFlag(String archiveFlag) {
		this.archiveFlag = archiveFlag;
	}
	/**
	 * get the segNo .
	 * @return the segNo
	 */
	public String getSegNo() {
		return this.segNo;
	}

	/**
	 * set the segNo .
	 *
	 * @param segNo 
	 */
	public void setSegNo(String segNo) {
		this.segNo = segNo;
	}
	/**
	 * get the delFlag .
	 * @return the delFlag
	 */
	public String getDelFlag() {
		return this.delFlag;
	}

	/**
	 * set the delFlag .
	 *
	 * @param delFlag 
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * get the value from Map.
	 *
	 * @param map - source data map
	 */
	@Override
	public void fromMap(Map map) {

		setUuid(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_UUID)), uuid));
		setEvaluate(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_EVALUATE)), evaluate));
		setEvaluateId(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_EVALUATE_ID)), evaluateId));
		setEvaluateType(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_EVALUATE_TYPE)), evaluateType));
		setRecCreateTime(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_CREATE_TIME)), recCreateTime));
		setRecCreator(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_CREATOR)), recCreator));
		setRecCreatorName(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_CREATOR_NAME)), recCreatorName));
		setRecReviseTime(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_REVISE_TIME)), recReviseTime));
		setRecRevisor(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_REVISOR)), recRevisor));
		setRecRevisorName(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_REVISOR_NAME)), recRevisorName));
		setArchiveFlag(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_ARCHIVE_FLAG)), archiveFlag));
		setSegNo(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SEG_NO)), segNo));
		setDelFlag(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_DEL_FLAG)), delFlag));
	}

	/**
	 * set the value to Map.
	 */
	@Override
	public Map toMap() {

		Map map = new HashMap();
		map.put(FIELD_UUID, StringUtils.toString(uuid, eiMetadata.getMeta(FIELD_UUID)));
		map.put(FIELD_EVALUATE, StringUtils.toString(evaluate, eiMetadata.getMeta(FIELD_EVALUATE)));
		map.put(FIELD_EVALUATE_ID, StringUtils.toString(evaluateId, eiMetadata.getMeta(FIELD_EVALUATE_ID)));
		map.put(FIELD_EVALUATE_TYPE, StringUtils.toString(evaluateType, eiMetadata.getMeta(FIELD_EVALUATE_TYPE)));
		map.put(FIELD_REC_CREATE_TIME, StringUtils.toString(recCreateTime, eiMetadata.getMeta(FIELD_REC_CREATE_TIME)));
		map.put(FIELD_REC_CREATOR, StringUtils.toString(recCreator, eiMetadata.getMeta(FIELD_REC_CREATOR)));
		map.put(FIELD_REC_CREATOR_NAME, StringUtils.toString(recCreatorName, eiMetadata.getMeta(FIELD_REC_CREATOR_NAME)));
		map.put(FIELD_REC_REVISE_TIME, StringUtils.toString(recReviseTime, eiMetadata.getMeta(FIELD_REC_REVISE_TIME)));
		map.put(FIELD_REC_REVISOR, StringUtils.toString(recRevisor, eiMetadata.getMeta(FIELD_REC_REVISOR)));
		map.put(FIELD_REC_REVISOR_NAME, StringUtils.toString(recRevisorName, eiMetadata.getMeta(FIELD_REC_REVISOR_NAME)));
		map.put(FIELD_ARCHIVE_FLAG, StringUtils.toString(archiveFlag, eiMetadata.getMeta(FIELD_ARCHIVE_FLAG)));
		map.put(FIELD_SEG_NO, StringUtils.toString(segNo, eiMetadata.getMeta(FIELD_SEG_NO)));
		map.put(FIELD_DEL_FLAG, StringUtils.toString(delFlag, eiMetadata.getMeta(FIELD_DEL_FLAG)));

		return map;
	}
}
