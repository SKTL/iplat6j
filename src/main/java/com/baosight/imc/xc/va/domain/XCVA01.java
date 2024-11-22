package com.baosight.imc.xc.va.domain;

import com.baosight.iplat4j.core.ei.EiColumn;
import com.baosight.iplat4j.core.data.DaoEPBase;

import java.util.HashMap;
import java.util.Map;

import com.baosight.iplat4j.core.util.StringUtils;

public class XCVA01 extends DaoEPBase {

    private static final long serialVersionUID = 1L;

    public static final String FIELD_REC_CREATOR = "recCreator";
    public static final String FIELD_REC_CREATOR_NAME = "recCreatorName";
    public static final String FIELD_REC_CREATE_TIME = "recCreateTime";
    public static final String FIELD_REC_REVISOR = "recRevisor";
    public static final String FIELD_REC_REVISOR_NAME = "recRevisorName";
    public static final String FIELD_REC_REVISE_TIME = "recReviseTime";
    public static final String FIELD_ARCHIVE_FLAG = "archiveFlag";
    public static final String FIELD_ARCHIVE_TIME = "archiveTime";
    public static final String FIELD_ARCHIVE_STAMP_NO = "archiveStampNo";
    public static final String FIELD_VIOLATION_RECORD_NO = "violationRecordNo";
    public static final String FIELD_VIOLATION_TYPE = "violationType";
    public static final String FIELD_VIOLATION_TIME = "violationTime";
    public static final String FIELD_VIOLATION_IMG_URL = "violationImgUrl";
    public static final String FIELD_FEEDBACK_OPINION = "feedbackOpinion";
    public static final String FIELD_CONFIRM_OPINION_TYPE = "confirmOpinionType";
    public static final String FIELD_VIDEO_ID = "videoId";
    public static final String FIELD_SHIP_LOT_NO = "shipLotNo";
    public static final String FIELD_SHIP_ID = "shipId";
    public static final String FIELD_MODIFY_FLAG = "modifyFlag";

    public static final String COL_REC_CREATOR = "REC_CREATOR";
    public static final String COL_REC_CREATOR_NAME = "REC_CREATOR_NAME";
    public static final String COL_REC_CREATE_TIME = "REC_CREATE_TIME";
    public static final String COL_REC_REVISOR = "REC_REVISOR";
    public static final String COL_REC_REVISOR_NAME = "REC_REVISOR_NAME";
    public static final String COL_REC_REVISE_TIME = "REC_REVISE_TIME";
    public static final String COL_ARCHIVE_FLAG = "ARCHIVE_FLAG";
    public static final String COL_ARCHIVE_TIME = "ARCHIVE_TIME";
    public static final String COL_ARCHIVE_STAMP_NO = "ARCHIVE_STAMP_NO";
    public static final String COL_VIOLATION_RECORD_NO = "VIOLATION_RECORD_NO";
    public static final String COL_VIOLATION_TYPE = "VIOLATION_TYPE";
    public static final String COL_VIOLATION_TIME = "VIOLATION_TIME";
    public static final String COL_VIOLATION_IMG_URL = "VIOLATION_IMG_URL";
    public static final String COL_FEEDBACK_OPINION = "FEEDBACK_OPINION";
    public static final String COL_CONFIRM_OPINION_TYPE = "CONFIRM_OPINION_TYPE";
    public static final String COL_VIDEO_ID = "VIDEO_ID";
    public static final String COL_SHIP_LOT_NO = "SHIP_LOT_NO";
    public static final String COL_SHIP_ID = "SHIP_ID";
    public static final String COL_MODIFY_FLAG = "MODIFY_FLAG";

    public static final String QUERY = "XCVA01.query";
    public static final String COUNT = "XCVA01.count";
    public static final String INSERT = "XCVA01.insert";
    public static final String UPDATE = "XCVA01.update";
    public static final String DELETE = "XCVA01.delete";

    private String recCreator = " ";
    private String recCreatorName = " ";
    private String recCreateTime = " ";
    private String recRevisor = " ";
    private String recRevisorName = " ";
    private String recReviseTime = " ";
    private String archiveFlag = " ";
    private String archiveTime = " ";
    private String archiveStampNo = " ";
    private String violationRecordNo = " ";
    private String violationType = " ";
    private String violationTime = " ";
    private String violationImgUrl = " ";
    private String feedbackOpinion = " ";
    private String confirmOpinionType = " ";
    private String videoId = " ";
    private String shipLotNo = " ";
    private String shipId = " ";
    private String modifyFlag = "0";

    /**
     * initialize the metadata.
     */
    public void initMetaData() {
        EiColumn eiColumn;
        eiColumn = new EiColumn(FIELD_REC_CREATOR);
        eiColumn.setFieldLength(32);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_REC_CREATOR_NAME);
        eiColumn.setFieldLength(32);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_REC_CREATE_TIME);
        eiColumn.setFieldLength(14);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_REC_REVISOR);
        eiColumn.setFieldLength(32);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_REC_REVISOR_NAME);
        eiColumn.setFieldLength(32);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_REC_REVISE_TIME);
        eiColumn.setFieldLength(14);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_ARCHIVE_FLAG);
        eiColumn.setFieldLength(1);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_ARCHIVE_TIME);
        eiColumn.setFieldLength(14);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_ARCHIVE_STAMP_NO);
        eiColumn.setFieldLength(10);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_VIOLATION_RECORD_NO);
        eiColumn.setFieldLength(50);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_VIOLATION_TYPE);
        eiColumn.setFieldLength(1);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_VIOLATION_TIME);
        eiColumn.setFieldLength(14);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_VIOLATION_IMG_URL);
        eiColumn.setFieldLength(32);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_FEEDBACK_OPINION);
        eiColumn.setFieldLength(64);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_CONFIRM_OPINION_TYPE);
        eiColumn.setFieldLength(1);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_VIDEO_ID);
        eiColumn.setFieldLength(50);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_SHIP_LOT_NO);
        eiColumn.setFieldLength(50);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_SHIP_ID);
        eiColumn.setFieldLength(50);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_MODIFY_FLAG);
        eiColumn.setFieldLength(1);
        eiMetadata.addMeta(eiColumn);

    }

    public XCVA01() {
        initMetaData();
    }

    public String getRecCreator() {
        return this.recCreator;
    }

    public void setRecCreator(String recCreator) {
        this.recCreator = recCreator;
    }

    public String getRecCreatorName() {
        return this.recCreatorName;
    }

    public void setRecCreatorName(String recCreatorName) {
        this.recCreatorName = recCreatorName;
    }

    public String getRecCreateTime() {
        return this.recCreateTime;
    }

    public void setRecCreateTime(String recCreateTime) {
        this.recCreateTime = recCreateTime;
    }

    public String getRecRevisor() {
        return this.recRevisor;
    }

    public void setRecRevisor(String recRevisor) {
        this.recRevisor = recRevisor;
    }

    public String getRecRevisorName() {
        return this.recRevisorName;
    }

    public void setRecRevisorName(String recRevisorName) {
        this.recRevisorName = recRevisorName;
    }

    public String getRecReviseTime() {
        return this.recReviseTime;
    }

    public void setRecReviseTime(String recReviseTime) {
        this.recReviseTime = recReviseTime;
    }

    public String getArchiveFlag() {
        return this.archiveFlag;
    }

    public void setArchiveFlag(String archiveFlag) {
        this.archiveFlag = archiveFlag;
    }

    public String getArchiveTime() {
        return this.archiveTime;
    }

    public void setArchiveTime(String archiveTime) {
        this.archiveTime = archiveTime;
    }

    public String getArchiveStampNo() {
        return this.archiveStampNo;
    }

    public void setArchiveStampNo(String archiveStampNo) {
        this.archiveStampNo = archiveStampNo;
    }

    public String getViolationRecordNo() {
        return this.violationRecordNo;
    }

    public void setViolationRecordNo(String violationRecordNo) {
        this.violationRecordNo = violationRecordNo;
    }

    public String getViolationType() {
        return this.violationType;
    }

    public void setViolationType(String violationType) {
        this.violationType = violationType;
    }

    public String getViolationTime() {
        return this.violationTime;
    }

    public void setViolationTime(String violationTime) {
        this.violationTime = violationTime;
    }

    public String getViolationImgUrl() {
        return this.violationImgUrl;
    }

    public void setViolationImgUrl(String violationImgUrl) {
        this.violationImgUrl = violationImgUrl;
    }

    public String getFeedbackOpinion() {
        return this.feedbackOpinion;
    }

    public void setFeedbackOpinion(String feedbackOpinion) {
        this.feedbackOpinion = feedbackOpinion;
    }

    public String getConfirmOpinionType() {
        return this.confirmOpinionType;
    }

    public void setConfirmOpinionType(String confirmOpinionType) {
        this.confirmOpinionType = confirmOpinionType;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getShipLotNo() {
        return this.shipLotNo;
    }

    public void setShipLotNo(String shipLotNo) {
        this.shipLotNo = shipLotNo;
    }

    public String getShipId() {
        return this.shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public String getModifyFlag() {
        return modifyFlag;
    }

    public void setModifyFlag(String modifyFlag) {
        this.modifyFlag = modifyFlag;
    }

    @Override
    public void fromMap(Map map) {
        setRecCreator(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_CREATOR)), recCreator));
        setRecCreatorName(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_CREATOR_NAME)), recCreatorName));
        setRecCreateTime(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_CREATE_TIME)), recCreateTime));
        setRecRevisor(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_REVISOR)), recRevisor));
        setRecRevisorName(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_REVISOR_NAME)), recRevisorName));
        setRecReviseTime(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_REVISE_TIME)), recReviseTime));
        setArchiveFlag(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_ARCHIVE_FLAG)), archiveFlag));
        setArchiveTime(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_ARCHIVE_TIME)), archiveTime));
        setArchiveStampNo(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_ARCHIVE_STAMP_NO)), archiveStampNo));
        setViolationRecordNo(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_VIOLATION_RECORD_NO)), violationRecordNo));
        setViolationType(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_VIOLATION_TYPE)), violationType));
        setViolationTime(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_VIOLATION_TIME)), violationTime));
        setViolationImgUrl(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_VIOLATION_IMG_URL)), violationImgUrl));
        setFeedbackOpinion(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_FEEDBACK_OPINION)), feedbackOpinion));
        setConfirmOpinionType(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_CONFIRM_OPINION_TYPE)), confirmOpinionType));
        setVideoId(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_VIDEO_ID)), videoId));
        setShipLotNo(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SHIP_LOT_NO)), shipLotNo));
        setShipId(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SHIP_ID)), shipId));
        setShipId(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_MODIFY_FLAG)), modifyFlag));
    }

    @Override
    public Map toMap() {
        Map map = new HashMap();
        map.put(FIELD_REC_CREATOR, StringUtils.toString(recCreator, eiMetadata.getMeta(FIELD_REC_CREATOR)));
        map.put(FIELD_REC_CREATOR_NAME, StringUtils.toString(recCreatorName, eiMetadata.getMeta(FIELD_REC_CREATOR_NAME)));
        map.put(FIELD_REC_CREATE_TIME, StringUtils.toString(recCreateTime, eiMetadata.getMeta(FIELD_REC_CREATE_TIME)));
        map.put(FIELD_REC_REVISOR, StringUtils.toString(recRevisor, eiMetadata.getMeta(FIELD_REC_REVISOR)));
        map.put(FIELD_REC_REVISOR_NAME, StringUtils.toString(recRevisorName, eiMetadata.getMeta(FIELD_REC_REVISOR_NAME)));
        map.put(FIELD_REC_REVISE_TIME, StringUtils.toString(recReviseTime, eiMetadata.getMeta(FIELD_REC_REVISE_TIME)));
        map.put(FIELD_ARCHIVE_FLAG, StringUtils.toString(archiveFlag, eiMetadata.getMeta(FIELD_ARCHIVE_FLAG)));
        map.put(FIELD_ARCHIVE_TIME, StringUtils.toString(archiveTime, eiMetadata.getMeta(FIELD_ARCHIVE_TIME)));
        map.put(FIELD_ARCHIVE_STAMP_NO, StringUtils.toString(archiveStampNo, eiMetadata.getMeta(FIELD_ARCHIVE_STAMP_NO)));
        map.put(FIELD_VIOLATION_RECORD_NO, StringUtils.toString(violationRecordNo, eiMetadata.getMeta(FIELD_VIOLATION_RECORD_NO)));
        map.put(FIELD_VIOLATION_TYPE, StringUtils.toString(violationType, eiMetadata.getMeta(FIELD_VIOLATION_TYPE)));
        map.put(FIELD_VIOLATION_TIME, StringUtils.toString(violationTime, eiMetadata.getMeta(FIELD_VIOLATION_TIME)));
        map.put(FIELD_VIOLATION_IMG_URL, StringUtils.toString(violationImgUrl, eiMetadata.getMeta(FIELD_VIOLATION_IMG_URL)));
        map.put(FIELD_FEEDBACK_OPINION, StringUtils.toString(feedbackOpinion, eiMetadata.getMeta(FIELD_FEEDBACK_OPINION)));
        map.put(FIELD_CONFIRM_OPINION_TYPE, StringUtils.toString(confirmOpinionType, eiMetadata.getMeta(FIELD_CONFIRM_OPINION_TYPE)));
        map.put(FIELD_VIDEO_ID, StringUtils.toString(videoId, eiMetadata.getMeta(FIELD_VIDEO_ID)));
        map.put(FIELD_SHIP_LOT_NO, StringUtils.toString(shipLotNo, eiMetadata.getMeta(FIELD_SHIP_LOT_NO)));
        map.put(FIELD_SHIP_ID, StringUtils.toString(shipId, eiMetadata.getMeta(FIELD_SHIP_ID)));
        map.put(FIELD_MODIFY_FLAG, StringUtils.toString(modifyFlag, eiMetadata.getMeta(FIELD_MODIFY_FLAG)));
        return map;
    }
}
