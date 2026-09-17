package org.guanzon.cas.inv.warehouse.model;

import java.sql.SQLException;
import java.util.Date;
import org.guanzon.appdriver.agent.services.Model;
import org.guanzon.appdriver.agent.services.ReferenceCache;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.appdriver.constant.RecordStatus;
import org.guanzon.cas.parameter.model.Model_Branch;
import org.guanzon.cas.parameter.model.Model_Category;
import org.guanzon.cas.parameter.model.Model_Company;
import org.guanzon.cas.parameter.model.Model_Industry;
import org.guanzon.cas.parameter.services.ParamModels;
import org.json.simple.JSONObject;
import org.guanzon.cas.inv.warehouse.status.DeliveryScheduleStatus;

/**
 *
 * @author maynevval 07-24-2025
 */
public class Model_Delivery_Schedule_Master extends Model {

    //reference objects
    //All reference fields below are intentionally NOT constructed in initialize() - see their
    //accessors, which build them lazily on first access so opening this record never touches
    //those tables.
    Model_Industry poIndustry;
    Model_Company poCompany;
    Model_Branch poBranch;
    Model_Category poCategory;

    @Override
    public void initialize() {
        try {
            poEntity = MiscUtil.xml2ResultSet(System.getProperty("sys.default.path.metadata") + XML, getTable());

            poEntity.last();
            poEntity.moveToInsertRow();

            MiscUtil.initRowSet(poEntity);

            poEntity.updateObject("dTransact", poGRider.getServerDate());
            poEntity.updateObject("dSchedule", poGRider.getServerDate());
            poEntity.updateObject("dModified", poGRider.getServerDate());
            poEntity.updateString("cTranStat", DeliveryScheduleStatus.OPEN);

            poEntity.insertRow();
            poEntity.moveToCurrentRow();

            poEntity.absolute(1);

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
    //dSchedule*
    //sRemarksx*
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

    //dSchedule
    public JSONObject setScheduleDate(Date scheduleDate) {
        return setValue("dSchedule", scheduleDate);
    }

    public Date getScheduleDate() {
        return (Date) getValue("dSchedule");
    }

    //sRemarksx
    public JSONObject setRemarks(String remarks) {
        return setValue("sRemarksx", remarks);
    }

    public String getRemarks() {
        return (String) getValue("sRemarksx");
    }

    //isStockNew
    public JSONObject isStockNew(boolean isRecordActive) {
        return setValue("cStockNew", (isRecordActive == true) ? "1" : "0");
    }

    public boolean isStockNew() {
        return RecordStatus.ACTIVE.equals(getValue("cStockNew"));
    }

    //cTranStat
    public JSONObject setStockNew(String stockNew) {
        return setValue("cStockNew", stockNew);
    }

    public String getStockNew() {
        return (String) getValue("cStockNew");
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
                    .getBranchCode().equals(getValue("sBranchCd"))) {
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

}
