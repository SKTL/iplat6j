package com.baosight.imc.xb.ai.domain;

import com.baosight.iplat4j.core.data.DaoEPBase;
import com.baosight.iplat4j.core.ei.EiColumn;
import com.baosight.iplat4j.core.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: <br>
 * Title:XBAI02.java <br>
 * Description: <br>
 * <p>
 * Copyrigth:Baosight Software LTD.co Copyright (c) 2019. <br>
 *
 * @version 1.0
 * @history 2024-10-21 14:49:04 create
 */
public class XBAI02 extends DaoEPBase {

    private static final long serialVersionUID = 1L;

    public static final String FIELD_UUID = "uuid";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_USER_NAME = "userName";
    public static final String FIELD_SESSION_ID = "sessionId";
    public static final String FIELD_SESSION_TITLE = "sessionTitle";
    public static final String FIELD_REC_CREATE_TIME = "recCreateTime";
    public static final String FIELD_REC_CREATOR = "recCreator";
    public static final String FIELD_REC_CREATOR_NAME = "recCreatorName";
    public static final String FIELD_REC_REVISE_TIME = "recReviseTime";
    public static final String FIELD_REC_REVISOR = "recRevisor";
    public static final String FIELD_REC_REVISOR_NAME = "recRevisorName";
    public static final String FIELD_ARCHIVE_FLAG = "archiveFlag";
    public static final String FIELD_SEG_NO = "segNo";
    public static final String FIELD_DEL_FLAG = "delFlag";
    public static final String FIELD_INTENTION = "intention";

    public static final String COL_UUID = "UUID";
    public static final String COL_USER_ID = "USER_ID";
    public static final String COL_USER_NAME = "USER_NAME";
    public static final String COL_SESSION_ID = "SESSION_ID";
    public static final String COL_SESSION_TITLE = "SESSION_TITLE";
    public static final String COL_REC_CREATE_TIME = "REC_CREATE_TIME";
    public static final String COL_REC_CREATOR = "REC_CREATOR";
    public static final String COL_REC_CREATOR_NAME = "REC_CREATOR_NAME";
    public static final String COL_REC_REVISOR_TIME = "REC_REVISOR_TIME";
    public static final String COL_REC_REVISOR = "REC_REVISOR";
    public static final String COL_REC_REVISOR_NAME = "REC_REVISOR_NAME";
    public static final String COL_ARCHIVE_FLAG = "ARCHIVE_FLAG";
    public static final String COL_SEG_NO = "SEG_NO";
    public static final String COL_DEL_FLAG = "DEL_FLAG";
    public static final String COL_INTENTION = "INTENTION";

    public static final String QUERY = "txbai02.query";
    public static final String COUNT = "txbai02.count";
    public static final String INSERT = "txbai02.insert";
    public static final String UPDATE = "txbai02.update";
    public static final String DELETE = "txbai02.delete";

    private String uuid = " ";
    private String userId = " ";
    private String userName = " ";
    private String sessionId = " ";
    private String sessionTitle = " ";
    private String recCreateTime = " ";
    private String recCreator = " ";
    private String recCreatorName = " ";
    private String recReviseTime = " ";
    private String recRevisor = " ";
    private String recRevisorName = " ";
    private String archiveFlag = "0";
    private String segNo = " ";
    private String delFlag = "0";
    private String intention = " ";

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

        eiColumn = new EiColumn(FIELD_USER_ID);
        eiColumn.setFieldLength(10);
        eiColumn.setDescName(" ");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_USER_NAME);
        eiColumn.setFieldLength(100);
        eiColumn.setDescName(" ");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_SESSION_ID);
        eiColumn.setFieldLength(50);
        eiColumn.setDescName(" ");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_SESSION_TITLE);
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

        eiColumn = new EiColumn(FIELD_REC_REVISOR);
        eiColumn.setFieldLength(50);
        eiColumn.setDescName(" ");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_REC_REVISOR_NAME);
        eiColumn.setFieldLength(100);
        eiColumn.setDescName(" ");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_REC_REVISE_TIME);
        eiColumn.setFieldLength(17);
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

        eiColumn = new EiColumn(FIELD_INTENTION);
        eiColumn.setFieldLength(10);
        eiColumn.setDescName(" ");
        eiMetadata.addMeta(eiColumn);

    }

    /**
     * the constructor.
     */
    public XBAI02() {
        initMetaData();
    }

    /**
     * get the uuid .
     *
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
     * get the userId .
     *
     * @return the userId
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * set the userId .
     *
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * get the userName .
     *
     * @return the userName
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * set the userName .
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * get the sessionId .
     *
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
     * get the sessionTitle .
     *
     * @return the sessionTitle
     */
    public String getSessionTitle() {
        return this.sessionTitle;
    }

    /**
     * set the sessionTitle .
     *
     * @param sessionTitle
     */
    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
    }

    /**
     * get the recCreateTime .
     *
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
     *
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
     *
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
     * get the recRevisor .
     *
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
     *
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
     * get the recReviseTime .
     *
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
    public void setRecReviseTime(String recReviseTime) {
        this.recReviseTime = recReviseTime;
    }

    /**
     * get the archiveFlag .
     *
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
     *
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
     *
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
     * get the intention .
     *
     * @return the intention
     */
    public String getIntention() {
        return this.intention;
    }

    /**
     * set the intention .
     *
     * @param intention
     */
    public void setIntention(String intention) {
        this.intention = intention;
    }

    /**
     * get the value from Map.
     *
     * @param map - source data map
     */
    @Override
    public void fromMap(Map map) {

        setUuid(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_UUID)), uuid));
        setUserId(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_USER_ID)), userId));
        setUserName(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_USER_NAME)), userName));
        setSessionId(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SESSION_ID)), sessionId));
        setSessionTitle(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SESSION_TITLE)), sessionTitle));
        setRecCreateTime(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_CREATE_TIME)), recCreateTime));
        setRecCreator(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_CREATOR)), recCreator));
        setRecCreatorName(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_CREATOR_NAME)), recCreatorName));
        setRecRevisor(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_REVISOR)), recRevisor));
        setRecRevisorName(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_REVISOR_NAME)), recRevisorName));
        setRecReviseTime(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_REVISE_TIME)), recReviseTime));
        setArchiveFlag(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_ARCHIVE_FLAG)), archiveFlag));
        setSegNo(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SEG_NO)), segNo));
        setDelFlag(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_DEL_FLAG)), delFlag));
        setIntention(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_INTENTION)), intention));
    }

    /**
     * set the value to Map.
     */
    @Override
    public Map toMap() {

        Map map = new HashMap();
        map.put(FIELD_UUID, StringUtils.toString(uuid, eiMetadata.getMeta(FIELD_UUID)));
        map.put(FIELD_USER_ID, StringUtils.toString(userId, eiMetadata.getMeta(FIELD_USER_ID)));
        map.put(FIELD_USER_NAME, StringUtils.toString(userName, eiMetadata.getMeta(FIELD_USER_NAME)));
        map.put(FIELD_SESSION_ID, StringUtils.toString(sessionId, eiMetadata.getMeta(FIELD_SESSION_ID)));
        map.put(FIELD_SESSION_TITLE, StringUtils.toString(sessionTitle, eiMetadata.getMeta(FIELD_SESSION_TITLE)));
        map.put(FIELD_REC_CREATE_TIME, StringUtils.toString(recCreateTime, eiMetadata.getMeta(FIELD_REC_CREATE_TIME)));
        map.put(FIELD_REC_CREATOR, StringUtils.toString(recCreator, eiMetadata.getMeta(FIELD_REC_CREATOR)));
        map.put(FIELD_REC_CREATOR_NAME, StringUtils.toString(recCreatorName, eiMetadata.getMeta(FIELD_REC_CREATOR_NAME)));
        map.put(FIELD_REC_REVISOR, StringUtils.toString(recRevisor, eiMetadata.getMeta(FIELD_REC_REVISOR)));
        map.put(FIELD_REC_REVISOR_NAME, StringUtils.toString(recRevisorName, eiMetadata.getMeta(FIELD_REC_REVISOR_NAME)));
        map.put(FIELD_REC_REVISE_TIME, StringUtils.toString(recReviseTime, eiMetadata.getMeta(FIELD_REC_REVISE_TIME)));
        map.put(FIELD_ARCHIVE_FLAG, StringUtils.toString(archiveFlag, eiMetadata.getMeta(FIELD_ARCHIVE_FLAG)));
        map.put(FIELD_SEG_NO, StringUtils.toString(segNo, eiMetadata.getMeta(FIELD_SEG_NO)));
        map.put(FIELD_DEL_FLAG, StringUtils.toString(delFlag, eiMetadata.getMeta(FIELD_DEL_FLAG)));
        map.put(FIELD_INTENTION, StringUtils.toString(intention, eiMetadata.getMeta(FIELD_INTENTION)));

        return map;
    }
}
