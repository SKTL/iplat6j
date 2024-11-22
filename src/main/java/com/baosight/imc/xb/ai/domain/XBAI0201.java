package com.baosight.imc.xb.ai.domain;

import com.baosight.iplat4j.core.data.DaoEPBase;
import com.baosight.iplat4j.core.ei.EiColumn;
import com.baosight.iplat4j.core.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: <br>
 * Title:Txbai0201.java <br>
 * Description: 智能员工助理对话详情表<br>
 *
 * Copyrigth:Baosight Software LTD.co Copyright (c) 2019. <br>
 *
 * @version 1.0
 * @history 2024-11-11 15:10:36 create
 */
public class XBAI0201 extends DaoEPBase {

	private static final long serialVersionUID = 1L;

	public static final String FIELD_UUID = "uuid";    		
	public static final String FIELD_SESSION_ID = "sessionId";    		
	public static final String FIELD_CHAT_ID = "chatId";    		
	public static final String FIELD_ROLE = "role";    		/* 用户角色：1=用户，2=小助理*/
	public static final String FIELD_CONTENT = "content";    		
	public static final String FIELD_EVALUATE = "evaluate";    		/* 评价：0=无，1=好评，2=差评*/
	public static final String FIELD_REC_CREATE_TIME = "recCreateTime";    		
	public static final String FIELD_REC_CREATOR = "recCreator";    		
	public static final String FIELD_REC_CREATOR_NAME = "recCreatorName";    		
	public static final String FIELD_REC_REVISE_TIME = "recReviseTime";    		
	public static final String FIELD_REC_REVISOR = "recRevisor";    		
	public static final String FIELD_REC_REVISOR_NAME = "recRevisorName";    		
	public static final String FIELD_ARCHIVE_FLAG = "archiveFlag";    		
	public static final String FIELD_SEG_NO = "segNo";    		
	public static final String FIELD_DEL_FLAG = "delFlag";    		
	public static final String FIELD_EVALUATE_ID = "evaluateId";    		
	public static final String FIELD_EVALUATE_CONTENT = "evaluateContent";    		

	public static final String COL_UUID = "UUID";    		
	public static final String COL_SESSION_ID = "SESSION_ID";    		
	public static final String COL_CHAT_ID = "CHAT_ID";    		
	public static final String COL_ROLE = "ROLE";    		/* 用户角色：1=用户，2=小助理*/
	public static final String COL_CONTENT = "CONTENT";    		
	public static final String COL_EVALUATE = "EVALUATE";    		/* 评价：0=无，1=好评，2=差评*/
	public static final String COL_REC_CREATE_TIME = "REC_CREATE_TIME";    		
	public static final String COL_REC_CREATOR = "REC_CREATOR";    		
	public static final String COL_REC_CREATOR_NAME = "REC_CREATOR_NAME";    		
	public static final String COL_REC_REVISE_TIME = "REC_REVISE_TIME";    		
	public static final String COL_REC_REVISOR = "REC_REVISOR";    		
	public static final String COL_REC_REVISOR_NAME = "REC_REVISOR_NAME";    		
	public static final String COL_ARCHIVE_FLAG = "ARCHIVE_FLAG";    		
	public static final String COL_SEG_NO = "SEG_NO";    		
	public static final String COL_DEL_FLAG = "DEL_FLAG";    		
	public static final String COL_EVALUATE_ID = "EVALUATE_ID";    		
	public static final String COL_EVALUATE_CONTENT = "EVALUATE_CONTENT";    		

	public static final String QUERY = "txbai0201.query";
	public static final String COUNT = "txbai0201.count";
	public static final String INSERT = "txbai0201.insert";
	public static final String UPDATE = "txbai0201.update";
	public static final String DELETE = "txbai0201.delete";

	private String uuid = " ";		
	private String sessionId = " ";		
	private String chatId = " ";		
	private String role = "1";		/* 用户角色：1=用户，2=小助理*/
	private String content = " ";		
	private String evaluate = "0";		/* 评价：0=无，1=好评，2=差评*/
	private String recCreateTime = " ";		
	private String recCreator = " ";		
	private String recCreatorName = " ";		
	private String recReviseTime = " ";		
	private String recRevisor = " ";		
	private String recRevisorName = " ";		
	private String archiveFlag = "0";		
	private String segNo = " ";		
	private String delFlag = "0";		
	private String evaluateId = " ";		
	private String evaluateContent = " ";		

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

		eiColumn = new EiColumn(FIELD_SESSION_ID);
		eiColumn.setFieldLength(50);
		eiColumn.setDescName(" ");
		eiMetadata.addMeta(eiColumn);

		eiColumn = new EiColumn(FIELD_CHAT_ID);
		eiColumn.setFieldLength(32);
		eiColumn.setDescName(" ");
		eiMetadata.addMeta(eiColumn);

		eiColumn = new EiColumn(FIELD_ROLE);
		eiColumn.setFieldLength(1);
		eiColumn.setDescName("用户角色：1=用户，2=小助理");
		eiMetadata.addMeta(eiColumn);

		eiColumn = new EiColumn(FIELD_CONTENT);
		eiColumn.setFieldLength(2000);
		eiColumn.setDescName(" ");
		eiMetadata.addMeta(eiColumn);

		eiColumn = new EiColumn(FIELD_EVALUATE);
		eiColumn.setFieldLength(1);
		eiColumn.setDescName("评价：0=无，1=好评，2=差评");
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

		eiColumn = new EiColumn(FIELD_EVALUATE_ID);
		eiColumn.setFieldLength(50);
		eiColumn.setDescName(" ");
		eiMetadata.addMeta(eiColumn);

		eiColumn = new EiColumn(FIELD_EVALUATE_CONTENT);
		eiColumn.setFieldLength(100);
		eiColumn.setDescName(" ");
		eiMetadata.addMeta(eiColumn);


	}

	/**
	 * the constructor.
	 */
	public XBAI0201() {
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
	 * get the sessionId .
	 * @return the sessionId
	 */
	public String getSessionId() {
		return this.sessionId;
	}

	/**
	 * set the sessionId .
	 *
	 * @param sessionId 
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	/**
	 * get the chatId .
	 * @return the chatId
	 */
	public String getChatId() {
		return this.chatId;
	}

	/**
	 * set the chatId .
	 *
	 * @param chatId 
	 */
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}
	/**
	 * get the role - 用户角色：1=用户，2=小助理.
	 * @return the role
	 */
	public String getRole() {
		return this.role;
	}

	/**
	 * set the role - 用户角色：1=用户，2=小助理.
	 *
	 * @param role - 用户角色：1=用户，2=小助理
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * get the content .
	 * @return the content
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * set the content .
	 *
	 * @param content 
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * get the evaluate - 评价：0=无，1=好评，2=差评.
	 * @return the evaluate
	 */
	public String getEvaluate() {
		return this.evaluate;
	}

	/**
	 * set the evaluate - 评价：0=无，1=好评，2=差评.
	 *
	 * @param evaluate - 评价：0=无，1=好评，2=差评
	 */
	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
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
	 * get the evaluateContent .
	 * @return the evaluateContent
	 */
	public String getEvaluateContent() {
		return this.evaluateContent;
	}

	/**
	 * set the evaluateContent .
	 *
	 * @param evaluateContent 
	 */
	public void setEvaluateContent(String evaluateContent) {
		this.evaluateContent = evaluateContent;
	}
	/**
	 * get the value from Map.
	 *
	 * @param map - source data map
	 */
	@Override
	public void fromMap(Map map) {

		setUuid(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_UUID)), uuid));
		setSessionId(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SESSION_ID)), sessionId));
		setChatId(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_CHAT_ID)), chatId));
		setRole(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_ROLE)), role));
		setContent(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_CONTENT)), content));
		setEvaluate(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_EVALUATE)), evaluate));
		setRecCreateTime(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_CREATE_TIME)), recCreateTime));
		setRecCreator(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_CREATOR)), recCreator));
		setRecCreatorName(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_CREATOR_NAME)), recCreatorName));
		setRecReviseTime(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_REVISE_TIME)), recReviseTime));
		setRecRevisor(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_REVISOR)), recRevisor));
		setRecRevisorName(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_REVISOR_NAME)), recRevisorName));
		setArchiveFlag(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_ARCHIVE_FLAG)), archiveFlag));
		setSegNo(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SEG_NO)), segNo));
		setDelFlag(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_DEL_FLAG)), delFlag));
		setEvaluateId(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_EVALUATE_ID)), evaluateId));
		setEvaluateContent(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_EVALUATE_CONTENT)), evaluateContent));
	}

	/**
	 * set the value to Map.
	 */
	@Override
	public Map toMap() {

		Map map = new HashMap();
		map.put(FIELD_UUID, StringUtils.toString(uuid, eiMetadata.getMeta(FIELD_UUID)));
		map.put(FIELD_SESSION_ID, StringUtils.toString(sessionId, eiMetadata.getMeta(FIELD_SESSION_ID)));
		map.put(FIELD_CHAT_ID, StringUtils.toString(chatId, eiMetadata.getMeta(FIELD_CHAT_ID)));
		map.put(FIELD_ROLE, StringUtils.toString(role, eiMetadata.getMeta(FIELD_ROLE)));
		map.put(FIELD_CONTENT, StringUtils.toString(content, eiMetadata.getMeta(FIELD_CONTENT)));
		map.put(FIELD_EVALUATE, StringUtils.toString(evaluate, eiMetadata.getMeta(FIELD_EVALUATE)));
		map.put(FIELD_REC_CREATE_TIME, StringUtils.toString(recCreateTime, eiMetadata.getMeta(FIELD_REC_CREATE_TIME)));
		map.put(FIELD_REC_CREATOR, StringUtils.toString(recCreator, eiMetadata.getMeta(FIELD_REC_CREATOR)));
		map.put(FIELD_REC_CREATOR_NAME, StringUtils.toString(recCreatorName, eiMetadata.getMeta(FIELD_REC_CREATOR_NAME)));
		map.put(FIELD_REC_REVISE_TIME, StringUtils.toString(recReviseTime, eiMetadata.getMeta(FIELD_REC_REVISE_TIME)));
		map.put(FIELD_REC_REVISOR, StringUtils.toString(recRevisor, eiMetadata.getMeta(FIELD_REC_REVISOR)));
		map.put(FIELD_REC_REVISOR_NAME, StringUtils.toString(recRevisorName, eiMetadata.getMeta(FIELD_REC_REVISOR_NAME)));
		map.put(FIELD_ARCHIVE_FLAG, StringUtils.toString(archiveFlag, eiMetadata.getMeta(FIELD_ARCHIVE_FLAG)));
		map.put(FIELD_SEG_NO, StringUtils.toString(segNo, eiMetadata.getMeta(FIELD_SEG_NO)));
		map.put(FIELD_DEL_FLAG, StringUtils.toString(delFlag, eiMetadata.getMeta(FIELD_DEL_FLAG)));
		map.put(FIELD_EVALUATE_ID, StringUtils.toString(evaluateId, eiMetadata.getMeta(FIELD_EVALUATE_ID)));
		map.put(FIELD_EVALUATE_CONTENT, StringUtils.toString(evaluateContent, eiMetadata.getMeta(FIELD_EVALUATE_CONTENT)));

		return map;
	}
}
