package org.guanzon.cas.inv.warehouse.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.guanzon.appdriver.agent.services.Model;
import org.guanzon.appdriver.agent.services.ReferenceCache;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.base.SQLUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.cas.client.model.Model_Client_Master;
import org.guanzon.cas.client.services.ClientModels;
import org.guanzon.cas.parameter.model.Model_Branch;
import org.guanzon.cas.parameter.model.Model_Category;
import org.guanzon.cas.parameter.model.Model_Company;
import org.guanzon.cas.parameter.model.Model_Industry;
import org.guanzon.cas.parameter.model.Model_TownCity;
import org.guanzon.cas.parameter.services.ParamModels;
import org.json.simple.JSONObject;
import org.guanzon.cas.inv.warehouse.status.DeliveryScheduleStatus;
import org.guanzon.cas.parameter.model.Model_Branch_Cluster;

/**
 *
 * @author maynevval 08-09-2025
 */
public class Model_Cluster_Delivery_Master extends Model {

    //reference objects
    //All reference fields below are intentionally NOT constructed in initialize() - see their
    //accessors, which build them lazily on first access so opening this record never touches
    //those tables.
    Model_Industry poIndustry;
    Model_Company poCompany;
    Model_Branch poBranch;
    Model_Category poCategory;
    Model_Client_Master poClient;
    Model_Client_Master poClient01;
    Model_Client_Master poClient02;
    Model_Branch_Cluster poBranchCluster;
    Model_TownCity poTownCity;

    @Override
    public void initialize() {
        try {
            poEntity = MiscUtil.xml2ResultSet(System.getProperty("sys.default.path.metadata") + XML, getTable());

            poEntity.last();
            poEntity.moveToInsertRow();

            MiscUtil.initRowSet(poEntity);

            poEntity.insertRow();
            poEntity.moveToCurrentRow();

            poEntity.absolute(1);
            poEntity.updateObject("sBranchCd", poGRider.getBranchCode());
            poEntity.updateObject("dTransact", poGRider.getServerDate());
            poEntity.updateObject("nEntryNox", 0);
            poEntity.updateNull("sSerialID");
            poEntity.updateString("sSerialID", "");

            poEntity.updateNull("sDriverID");
            poEntity.updateNull("sEmploy01");
            poEntity.updateNull("sEmploy02");
            poEntity.updateNull("sClustrID");
            poEntity.updateNull("sTownIDxx");
            poEntity.updateNull("dDepartre");
            poEntity.updateNull("dArrivalx");
            poEntity.updateNull("sReferNox");
            poEntity.updateNull("dCancelld");
            poEntity.updateString("cCancelld", "0");
            poEntity.updateString("cTranStat", DeliveryScheduleStatus.OPEN);
            poEntity.updateObject("dModified", poGRider.getServerDate());

            ID = poEntity.getMetaData().getColumnLabel(1);

            //add model here
            pnEditMode = EditMode.UNKNOWN;

        } catch (SQLException e) {
            logwrapr.severe(e.getMessage());
            System.exit(1);
        }
    }
    //Getter & Setter 
    //sTransNox
    //sIndstCdx
    //sCompnyID
    //sBranchCd*
    //sCategrCd
    //dTransact*
    //sSerialID*
    //sDriverID*
    //sEmploy01*
    //sEmploy02
    //sClustrID
    //sTownIDxx
    //dDepartre
    //dArrivalx
    //nEntryNox
    //sRemarksx
    //sReferNox
    //cCancelld
    //dCancelld
    //cTranStat

    //sTransNox
    public JSONObject setTransactionNo(String transactionNo) {
        return setValue("sTransNox", transactionNo);
    }

    public String getTransactionNo() {
        return (String) getValue("sTransNox");
    }

    //sIndstCdx
    public JSONObject setIndustryId(String industryId) {
        return setValue("sIndstCdx", industryId);
    }

    public String getIndustryId() {
        return (String) getValue("sIndstCdx");
    }

    //sCompnyID
    public JSONObject setCompanyID(String companyID) {
        return setValue("sCompnyID", companyID);
    }

    public String getCompanyID() {
        return (String) getValue("sCompnyID");
    }

    //sBranchCd
    public JSONObject setBranchCode(String branchCode) {
        return setValue("sBranchCd", branchCode);
    }

    public String getBranchCode() {
        return (String) getValue("sBranchCd");
    }

    //sCategrCd
    public JSONObject setCategoryId(String categoryId) {
        return setValue("sCategrCd", categoryId);
    }

    public String getCategoryId() {
        return (String) getValue("sCategrCd");
    }

    //dTransact
    public JSONObject setTransactionDate(Date transactionDate) {
        return setValue("dTransact", transactionDate);
    }

    public Date getTransactionDate() {
        return (Date) getValue("dTransact");
    }

    //sSerialID
    public JSONObject setSerialId(String serialID) {
        return setValue("sSerialID", serialID);
    }

    public String getSerialId() {
        return (String) getValue("sSerialID");
    }

    //sDriverID
    public JSONObject setDriverID(String driverID) {
        return setValue("sDriverID", driverID);
    }

    public String getDriverID() {
        return (String) getValue("sDriverID");
    }

    //sEmploy01
    public JSONObject setEmploy01(String employ01) {
        return setValue("sEmploy01", employ01);
    }

    public String getEmploy01() {
        return (String) getValue("sEmploy01");
    }

    //sEmploy02
    public JSONObject setEmploy02(String employ02) {
        return setValue("sEmploy02", employ02);
    }

    public String getEmploy02() {
        return (String) getValue("sEmploy02");
    }

    //sClustrID
    public JSONObject setClusterID(String clustrID) {
        return setValue("sClustrID", clustrID);
    }

    public String getClusterID() {
        return (String) getValue("sClustrID");
    }

    //sTownIDxx
    public JSONObject setTownId(String TownId) {
        return setValue("sTownIDxx", TownId);
    }

    public String getTownId() {
        return (String) getValue("sTownIDxx");
    }

    //dDepartre
    public JSONObject setDepartreDate(Date departureDate) {
        return setValue("dDepartre", departureDate);
    }

    public Date getDepartreDate() {
        return (Date) getValue("dDepartre");
    }

    //dArrivalx
    public JSONObject setArrivalDate(Date arrivalDate) {
        return setValue("dArrivalx", arrivalDate);
    }

    public Date getArrivalDate() {
        return (Date) getValue("dArrivalx");
    }

    //nEntryNox
    public JSONObject setEntryNo(int entryNo) {
        return setValue("nEntryNox", entryNo);
    }

    public int getEntryNo() {
        return (int) getValue("nEntryNox");
    }

    //sRemarksx
    public JSONObject setRemarks(String remarks) {
        return setValue("sRemarksx", remarks);
    }

    public String getRemarks() {
        return (String) getValue("sRemarksx");
    }

    //sReferNox
    public JSONObject setReferNo(String referNo) {
        return setValue("sReferNox", referNo);
    }

    public String getReferNo() {
        return (String) getValue("sReferNox");
    }

    //cTranStat
    public JSONObject setCancelled(String cancel) {
        return setValue("cCancelld", cancel);
    }

    public String getCancelled() {
        return (String) getValue("cCancelld");
    }

    //dCancelld
    public JSONObject setCancelledDate(Date TownId) {
        return setValue("dCancelld", TownId);
    }

    public Date getCancelledDate() {
        return (Date) getValue("dCancelld");
    }

    //cTranStat
    public JSONObject setTransactionStatus(String transactionStatus) {
        return setValue("cTranStat", transactionStatus);
    }

    public String getTransactionStatus() {
        return (String) getValue("cTranStat");
    }

    //sModified
    public JSONObject setModifyingId(String modifyingId) {
        return setValue("sModified", modifyingId);
    }

    public String getModifyingId() {
        return (String) getValue("sModified");
    }

    //dModified
    public JSONObject setModifiedDate(Date modifiedDate) {
        return setValue("dModified", modifiedDate);
    }

    public Date getModifiedDate() {
        return (Date) getValue("dModified");
    }

    @Override
    public String getNextCode() {
        return MiscUtil.getNextCode(this.getTable(), ID, true, poGRider.getGConnection().getConnection(), poGRider.getBranchCode());
    }

    public Model_Category Category() throws SQLException, GuanzonException {
        if (poCategory == null) {
            poCategory = new ParamModels(poGRider).Category();
        }
        
        String id = (String) (getValue("sCategrCd") == null ? "" : getValue("sCategrCd"));

        if (!"".equals(id)) {
            if (this.poCategory.getEditMode() == 1 && this.poCategory
                    .getCategoryId().equals(id)) {
                return this.poCategory;
            }

            if (ReferenceCache.tryLoad("Category", id, poCategory)) {
                return poCategory;
            }

            this.poJSON = this.poCategory.openRecord(id);
            if ("success".equals(this.poJSON.get("result"))) {
                ReferenceCache.store("Category", id, poCategory);
                return this.poCategory;
            }
            this.poCategory.initialize();
            return this.poCategory;
        }
        this.poCategory.initialize();
        return this.poCategory;
    }

    public Model_Branch Branch() throws SQLException, GuanzonException {
        if (poBranch == null) {
            poBranch = new ParamModels(poGRider).Branch();
        }
        
        String id = (String) (getValue("sBranchCd") == null ? "" : getValue("sBranchCd"));

        if (!"".equals(id)) {
            if (this.poBranch.getEditMode() == 1 && this.poBranch
                    .getBranchCode().equals(id)) {
                return this.poBranch;
            }

            if (ReferenceCache.tryLoad("Branch", id, poBranch)) {
                return poBranch;
            }

            this.poJSON = this.poBranch.openRecord(id);
            if ("success".equals(this.poJSON.get("result"))) {
                ReferenceCache.store("Branch", id, poBranch);
                return this.poBranch;
            }
            this.poBranch.initialize();
            return this.poBranch;
        }
        this.poBranch.initialize();
        return this.poBranch;
    }

    public Model_Company Company() throws SQLException, GuanzonException {
        if (poCompany == null) {
            poCompany = new ParamModels(poGRider).Company();
        }
        
        String id = (String) (getValue("sCompnyID") == null ? "" : getValue("sCompnyID"));

        if (!"".equals(id)) {
            if (this.poCompany.getEditMode() == 1 && this.poCompany
                    .getCompanyId().equals(id)) {
                return this.poCompany;
            }

            if (ReferenceCache.tryLoad("Company", id, poCompany)) {
                return poCompany;
            }

            this.poJSON = this.poCompany.openRecord(id);
            if ("success".equals(this.poJSON.get("result"))) {
                ReferenceCache.store("Company", id, poCompany);
                return this.poCompany;
            }
            this.poCompany.initialize();
            return this.poCompany;
        }
        this.poCompany.initialize();
        return this.poCompany;
    }

    public Model_Industry Industry() throws SQLException, GuanzonException {
        if (poIndustry == null) {
            poIndustry = new ParamModels(poGRider).Industry();
        }
        
        String id = (String) (getValue("sIndstCdx") == null ? "" : getValue("sIndstCdx"));

        if (!"".equals(id)) {
            if (this.poIndustry.getEditMode() == 1 && this.poIndustry
                    .getIndustryId().equals(id)) {
                return this.poIndustry;
            }

            if (ReferenceCache.tryLoad("Industry", id, poIndustry)) {
                return poIndustry;
            }

            this.poJSON = this.poIndustry.openRecord(id);
            if ("success".equals(this.poJSON.get("result"))) {
                ReferenceCache.store("Industry", id, poIndustry);
                return this.poIndustry;
            }
            this.poIndustry.initialize();
            return this.poIndustry;
        }
        this.poIndustry.initialize();
        return this.poIndustry;
    }

    public Model_Client_Master CompanyDriver() throws SQLException, GuanzonException {
        if (poClient == null) {
            poClient = new ClientModels(poGRider).ClientMaster();
        }
        
        String id = (String) (getValue("sDriverID") == null ? "" : getValue("sDriverID"));

        if (!"".equals(id)) {
            if (this.poClient.getEditMode() == 1 && this.poClient
                    .getClientId().equals(id)) {
                return this.poClient;
            }

            if (ReferenceCache.tryLoad("Client_Master", id, poClient)) {
                return poClient;
            }

            this.poJSON = this.poClient.openRecord(id);
            if ("success".equals(this.poJSON.get("result"))) {
                ReferenceCache.store("Client_Master", id, poClient);
                return this.poClient;
            }
            this.poClient.initialize();
            return this.poClient;
        }
        this.poClient.initialize();
        return this.poClient;
    }

    public Model_Client_Master CompanyEmployee01() throws SQLException, GuanzonException {
        if (poClient01 == null) {
            poClient01 = new ClientModels(poGRider).ClientMaster();
        }
        
        String id = (String) (getValue("sEmploy01") == null ? "" : getValue("sEmploy01"));

        if (!"".equals(id)) {
            if (this.poClient01.getEditMode() == 1 && this.poClient01
                    .getClientId().equals(id)) {
                return this.poClient01;
            }

            if (ReferenceCache.tryLoad("Client_Master", id, poClient01)) {
                return poClient01;
            }

            this.poJSON = this.poClient01.openRecord(id);
            if ("success".equals(this.poJSON.get("result"))) {
                ReferenceCache.store("Client_Master", id, poClient01);
                return this.poClient01;
            }
            this.poClient01.initialize();
            return this.poClient01;
        }
        this.poClient01.initialize();
        return this.poClient01;
    }

    public Model_Client_Master CompanyEmployee02() throws SQLException, GuanzonException {
        if (poClient02 == null) {
            poClient02 = new ClientModels(poGRider).ClientMaster();
        }

        String id = (String) (getValue("sEmploy02") == null ? "" : getValue("sEmploy02"));
        
        if (!"".equals(id)) {
            if (this.poClient02.getEditMode() == 1 && this.poClient02
                    .getClientId().equals(id)) {
                return this.poClient02;
            }

            if (ReferenceCache.tryLoad("Client_Master", id, poClient02)) {
                return poClient02;
            }

            this.poJSON = this.poClient02.openRecord(id);
            if ("success".equals(this.poJSON.get("result"))) {
                ReferenceCache.store("Client_Master", id, poClient02);
                return this.poClient02;
            }
            this.poClient02.initialize();
            return this.poClient02;
        }
        this.poClient02.initialize();
        return this.poClient02;
    }

    public Model_Branch_Cluster BranchCluster() throws SQLException, GuanzonException {
        if (poBranchCluster == null) {
            poBranchCluster = new ParamModels(poGRider).BranchCluster();
        }
        
        String id = (String) (getValue("sClustrID") == null ? "" : getValue("sClustrID"));

        if (!"".equals(id)) {
            if (this.poBranchCluster.getEditMode() == 1 && this.poBranchCluster
                    .getClusterID().equals(id)) {
                return this.poBranchCluster;
            }

            if (ReferenceCache.tryLoad("Branch_Cluster", id, poBranchCluster)) {
                return poBranchCluster;
            }

            this.poJSON = this.poBranchCluster.openRecord(id);
            if ("success".equals(this.poJSON.get("result"))) {
                ReferenceCache.store("Branch_Cluster", id, poBranchCluster);
                return this.poBranchCluster;
            }
            this.poBranchCluster.initialize();
            return this.poBranchCluster;
        }
        poBranchCluster.initialize();
        return this.poBranchCluster;

    }

    public Model_TownCity TownCity() throws SQLException, GuanzonException {
        if (poTownCity == null) {
            poTownCity = new ParamModels(poGRider).TownCity();
        }
        
        String id = (String) (getValue("sTownIDxx") == null ? "" : getValue("sTownIDxx"));

        if (!"".equals(id)) {
            if (this.poTownCity.getEditMode() == 1 && this.poTownCity
                    .getTownId().equals(id)) {
                return this.poTownCity;
            }

            if (ReferenceCache.tryLoad("TownCity", id, poTownCity)) {
                return poTownCity;
            }

            this.poJSON = this.poTownCity.openRecord(id);
            if ("success".equals(this.poJSON.get("result"))) {
                ReferenceCache.store("TownCity", id, poTownCity);
                return this.poTownCity;
            }
            this.poTownCity.initialize();
            return this.poTownCity;
        }
        poTownCity.initialize();
        return this.poTownCity;

    }

    @Override
    public JSONObject openRecord(String id) throws SQLException, GuanzonException {
        this.poJSON = new JSONObject();
        String lsSQL = MiscUtil.makeSelect(this);
        lsSQL = MiscUtil.addCondition(lsSQL, this.ID + " = " + SQLUtil.toSQL(id));
        ResultSet loRS = this.poGRider.executeQuery(lsSQL);
        try {
            if (loRS.next()) {
                for (int lnCtr = 1; lnCtr <= loRS.getMetaData().getColumnCount(); lnCtr++) {
                    Object loValue = loRS.getObject(lnCtr);
                    int lnType = loRS.getMetaData().getColumnType(lnCtr); // get JDBC type

                    if (loValue != null) {
                        switch (lnType) {
                            case 91: // DATE
                                loValue = loRS.getDate(lnCtr);
                                break;
                            case 93: // TIMESTAMP / DATETIME
                                loValue = loRS.getTimestamp(lnCtr); // use explicit getter first
                                if (loValue == null) {
                                    loValue = toTimestamp(loRS.getString(lnCtr)); // fallback
                                }
                                break;
                            default:
                                loValue = loRS.getObject(lnCtr);
                                break;
                        }
                    }

                    setValue(lnCtr, loValue);
                    System.out.println(lnCtr + " - " + (setValue(lnCtr, loValue)).toJSONString());
                }
                MiscUtil.close(loRS);
                this.pnEditMode = 1;
                this.poJSON = new JSONObject();
                this.poJSON.put("result", "success");
                this.poJSON.put("message", "Record loaded successfully.");
            } else {
                this.poJSON = new JSONObject();
                this.poJSON.put("result", "error");
                this.poJSON.put("message", "No record to load.");
            }
        } catch (SQLException e) {
            this.poJSON = new JSONObject();
            this.poJSON.put("result", "error");
            this.poJSON.put("message", e.getMessage());
        }
        return this.poJSON;
    }

    public static java.sql.Timestamp toTimestamp(Object loValue) {
        if (loValue == null) {
            return null;
        }
        if (loValue instanceof java.sql.Timestamp) {
            return (java.sql.Timestamp) loValue;
        }
        if (loValue instanceof java.util.Date) {
            return new java.sql.Timestamp(((java.util.Date) loValue).getTime());
        }

        String lsValue = loValue.toString().trim();
        if (lsValue.isEmpty()) {
            return null;
        }

        // Try all possible formats
        String[] laFormats = {
            "yyyy-MM-dd HH:mm:ss.SSS", 
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ss.SSS", 
            "yyyy-MM-dd'T'HH:mm:ss", 
            "MM/dd/yyyy HH:mm:ss", 
            "yyyy-MM-dd", 
        };

        for (String lsFormat : laFormats) {
            try {
                java.text.SimpleDateFormat loFmt = new java.text.SimpleDateFormat(lsFormat);
                loFmt.setLenient(false);
                return new java.sql.Timestamp(loFmt.parse(lsValue).getTime());
            } catch (java.text.ParseException e) {
                // try next format
            }
        }

        // Log what value is actually coming in
        System.err.println("Cannot parse Timestamp from: [" + lsValue + "]");
        throw new IllegalArgumentException("Cannot parse datetime value: " + lsValue);
    }
}
