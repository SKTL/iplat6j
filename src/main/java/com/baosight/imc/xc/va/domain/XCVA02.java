package com.baosight.imc.xc.va.domain;

/**
 * @author: lx
 * @date: 2024/10/21 14:19
 * @description:
 */

import com.baosight.iplat4j.core.ei.EiColumn;
import com.baosight.iplat4j.core.data.DaoEPBase;

import java.util.HashMap;
import java.util.Map;

import com.baosight.iplat4j.core.util.StringUtils;

public class XCVA02 extends DaoEPBase {

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
    public static final String FIELD_SHIP_LOT_NO = "shipLotNo";
    public static final String FIELD_SHIP_NAME = "shipName";
    public static final String FIELD_WHARF_NAME = "wharfName";
    public static final String FIELD_WHARF_CODE = "wharfCode";
    public static final String FIELD_START_TIME = "startTime";
    public static final String FIELD_END_TIME = "endTime";
    public static final String FIELD_CARRIERS_NAME = "carriersName";
    public static final String FIELD_SHIP_ID = "shipId";

    public static final String COL_REC_CREATOR = "REC_CREATOR";
    public static final String COL_REC_CREATOR_NAME = "REC_CREATOR_NAME";
    public static final String COL_REC_CREATE_TIME = "REC_CREATE_TIME";
    public static final String COL_REC_REVISOR = "REC_REVISOR";
    public static final String COL_REC_REVISOR_NAME = "REC_REVISOR_NAME";
    public static final String COL_REC_REVISE_TIME = "REC_REVISE_TIME";
    public static final String COL_ARCHIVE_FLAG = "ARCHIVE_FLAG";
    public static final String COL_ARCHIVE_TIME = "ARCHIVE_TIME";
    public static final String COL_ARCHIVE_STAMP_NO = "ARCHIVE_STAMP_NO";
    public static final String COL_SHIP_LOT_NO = "SHIP_LOT_NO";
    public static final String COL_SHIP_NAME = "SHIP_NAME";
    public static final String COL_WHARF_NAME = "WHARF_NAME";
    public static final String COL_WHARF_CODE = "WHARF_CODE";
    public static final String COL_START_TIME = "START_TIME";
    public static final String COL_END_TIME = "END_TIME";
    public static final String COL_CARRIERS_NAME = "CARRIERS_NAME";
    public static final String COL_SHIP_ID = "SHIP_ID";

    public static final String QUERY = "txcva02.query";
    public static final String COUNT = "txcva02.count";
    public static final String INSERT = "txcva02.insert";
    public static final String UPDATE = "txcva02.update";
    public static final String DELETE = "txcva02.delete";

    private String recCreator = " ";
    private String recCreatorName = " ";
    private String recCreateTime = " ";
    private String recRevisor = " ";
    private String recRevisorName = " ";
    private String recReviseTime = " ";
    private String archiveFlag = " ";
    private String archiveTime = " ";
    private String archiveStampNo = " ";
    private String shipLotNo = " ";
    private String shipName = " ";
    private String wharfName = " ";
    private String wharfCode = " ";
    private String startTime = " ";
    private String endTime = " ";
    private String carriersName = " ";
    private String shipId = " ";

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

        eiColumn = new EiColumn(FIELD_SHIP_LOT_NO);
        eiColumn.setFieldLength(50);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_SHIP_NAME);
        eiColumn.setFieldLength(50);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_WHARF_NAME);
        eiColumn.setFieldLength(50);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_WHARF_CODE);
        eiColumn.setFieldLength(50);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_START_TIME);
        eiColumn.setFieldLength(14);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_END_TIME);
        eiColumn.setFieldLength(14);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_CARRIERS_NAME);
        eiColumn.setFieldLength(100);
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_SHIP_ID);
        eiColumn.setFieldLength(50);
        eiMetadata.addMeta(eiColumn);

    }

    public XCVA02() {
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

    public String getShipLotNo() {
        return this.shipLotNo;
    }

    public void setShipLotNo(String shipLotNo) {
        this.shipLotNo = shipLotNo;
    }

    public String getShipName() {
        return this.shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getWharfName() {
        return this.wharfName;
    }

    public void setWharfName(String wharfName) {
        this.wharfName = wharfName;
    }

    public String getWharfCode() {
        return this.wharfCode;
    }

    public void setWharfCode(String wharfCode) {
        this.wharfCode = wharfCode;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCarriersName() {
        return this.carriersName;
    }

    public void setCarriersName(String carriersName) {
        this.carriersName = carriersName;
    }

    public String getShipId() {
        return this.shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
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
        setShipLotNo(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SHIP_LOT_NO)), shipLotNo));
        setShipName(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SHIP_NAME)), shipName));
        setWharfName(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_WHARF_NAME)), wharfName));
        setWharfCode(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_WHARF_CODE)), wharfCode));
        setStartTime(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_START_TIME)), startTime));
        setEndTime(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_END_TIME)), endTime));
        setCarriersName(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_CARRIERS_NAME)), carriersName));
        setShipId(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SHIP_ID)), shipId));
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
        map.put(FIELD_SHIP_LOT_NO, StringUtils.toString(shipLotNo, eiMetadata.getMeta(FIELD_SHIP_LOT_NO)));
        map.put(FIELD_SHIP_NAME, StringUtils.toString(shipName, eiMetadata.getMeta(FIELD_SHIP_NAME)));
        map.put(FIELD_WHARF_NAME, StringUtils.toString(wharfName, eiMetadata.getMeta(FIELD_WHARF_NAME)));
        map.put(FIELD_WHARF_CODE, StringUtils.toString(wharfCode, eiMetadata.getMeta(FIELD_WHARF_CODE)));
        map.put(FIELD_START_TIME, StringUtils.toString(startTime, eiMetadata.getMeta(FIELD_START_TIME)));
        map.put(FIELD_END_TIME, StringUtils.toString(endTime, eiMetadata.getMeta(FIELD_END_TIME)));
        map.put(FIELD_CARRIERS_NAME, StringUtils.toString(carriersName, eiMetadata.getMeta(FIELD_CARRIERS_NAME)));
        map.put(FIELD_SHIP_ID, StringUtils.toString(shipId, eiMetadata.getMeta(FIELD_SHIP_ID)));
        return map;
    }
}
