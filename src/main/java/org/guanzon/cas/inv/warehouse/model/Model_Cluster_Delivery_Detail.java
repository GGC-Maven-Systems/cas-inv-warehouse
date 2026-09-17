package org.guanzon.cas.inv.warehouse.model;

import java.sql.SQLException;
import java.util.Date;
import org.guanzon.appdriver.agent.services.Model;
import org.guanzon.appdriver.agent.services.ReferenceCache;
import org.guanzon.appdriver.base.GuanzonException;
import org.guanzon.appdriver.base.MiscUtil;
import org.guanzon.appdriver.constant.EditMode;
import org.guanzon.cas.inv.warehouse.InventoryStockIssuanceNeo;
import org.guanzon.cas.inv.warehouse.services.DeliveryIssuanceControllers;
import org.guanzon.cas.parameter.model.Model_Branch;
import org.guanzon.cas.parameter.services.ParamModels;
import org.json.simple.JSONObject;

/**
 *
 * @author maynevval 08-09-2025
 */
public class Model_Cluster_Delivery_Detail extends Model {

    //poBranch is intentionally NOT constructed in initialize() - see Branch() below, which
    //builds it lazily on first access so opening this record never touches Branch. (poIssuance
    //is a Transaction subclass, not a Model - out of scope for this pattern, left as-is.
    //poInventoryMaster was previously eager-constructed here too but had no accessor anywhere
    //in this class - removed as a dead field.)
    private Model_Branch poBranch;
    private InventoryStockIssuanceNeo poIssuance;

    @Override
    public void initialize() {
        try {
            this.poEntity = MiscUtil.xml2ResultSet(System.getProperty("sys.default.path.metadata") + XML, getTable());

            this.poEntity.last();
            this.poEntity.moveToInsertRow();

            MiscUtil.initRowSet(poEntity);

            this.poEntity.insertRow();
            this.poEntity.moveToCurrentRow();

            this.poEntity.absolute(1);

            this.poEntity.updateObject("nEntryNox", 1);
            this.poEntity.updateObject("nNoItemsx", 0);
            this.poEntity.updateNull("sReferNox");
            this.poEntity.updateNull("sSourceCd");
            this.poEntity.updateNull("sBranchCd");
            this.poEntity.updateString("cCancelld", "0");
            this.poEntity.updateNull("dCancelld");
            this.ID = poEntity.getMetaData().getColumnLabel(1);
            this.ID2 = poEntity.getMetaData().getColumnLabel(2);

            this.poIssuance = new DeliveryIssuanceControllers(poGRider, null).InventoryStockIssuanceNeo();
            poIssuance.initTransaction();
            poIssuance.setWithParent(true);
            pnEditMode = EditMode.UNKNOWN;
        } catch (SQLException | GuanzonException e) {
            logwrapr.severe(e.getMessage());
            System.exit(1);
        }
    }
    //Getter & Setter 
    //sTransNox
    //nEntryNox*
    //sReferNox*
    //sSourceCd*
    //sBranchCd
    //nNoItemsx
    //cCancelld
    //dCancelld
    //dModified

    //sTransNox
    public JSONObject setTransactionNo(String transactionNo) {
        return setValue("sTransNox", transactionNo);
    }

    public String getTransactionNo() {
        return (String) getValue("sTransNox");
    }

    //nEntryNox
    public JSONObject setEntryNo(int entryNo) {
        return setValue("nEntryNox", entryNo);
    }

    public int getEntryNo() {
        return (int) getValue("nEntryNox");
    }

    //sReferNox
    public JSONObject setReferNo(String referno) {
        return setValue("sReferNox", referno);
    }

    public String getReferNo() {
        return (String) getValue("sReferNox");
    }

    //sSourceCd
    public JSONObject setSourceCode(String sourceCode) {
        return setValue("sSourceCd", sourceCode);
    }

    public String getSourceCode() {
        return (String) getValue("sSourceCd");
    }

    //sBranchCd
    public JSONObject setBranchCode(String branchCode) {
        return setValue("sBranchCd", branchCode);
    }

    public String getBranchCode() {
        return (String) getValue("sBranchCd");
    }

    //nNoItemsx
    public JSONObject setNoOfItem(Double quantity) {
        return setValue("nNoItemsx", quantity);
    }

    public Double getNoOfItem() {
        return Double.valueOf(getValue("nNoItemsx").toString());
    }

    //cCancelld
    public JSONObject setCancelled(String isCancelled) {
        return setValue("cCancelld", isCancelled);
    }

    public String getCancelled() {
        return (String) getValue("cCancelld");
    }

    //dCancelld
    public JSONObject setCancelledDate(Date modifiedDate) {
        return setValue("dCancelld", modifiedDate);
    }

    public Date getCancelledDate() {
        return (Date) getValue("dCancelld");
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
        return "";
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

    public InventoryStockIssuanceNeo InventoryTransfer() throws SQLException, GuanzonException, CloneNotSupportedException {
        String id = (String) (getValue("sReferNox") == null ? "" : getValue("sReferNox"));
        
        if (!"".equals(id) && id != null) {
            if (this.poIssuance
                    .getMaster().getTransactionNo() != null) {
                if (this.poIssuance.getEditMode() == 0 && this.poIssuance
                        .getMaster().getTransactionNo().equals(id)) {

                    poIssuance.setWithParent(true);
                    return this.poIssuance;
                }
                if (this.poIssuance.getEditMode() == 1 && this.poIssuance
                        .getMaster().getTransactionNo().equals(id)) {

                    poIssuance.setWithParent(true);
                    return this.poIssuance;
                }
                if (this.poIssuance.getEditMode() == 2 && this.poIssuance
                        .getMaster().getTransactionNo().equals(id)) {

                    poIssuance.setWithParent(true);
                    return this.poIssuance;
                }
            }
            this.poJSON = this.poIssuance.OpenTransaction(id);
            if ("success".equals(this.poJSON.get("result"))) {
                return this.poIssuance;
            }
            this.poIssuance.initTransaction();
            poIssuance.NewTransaction();
            setReferNo(poIssuance.getMaster().getTransactionNo());
            return this.poIssuance;
        }
        this.poIssuance.initTransaction();
        poIssuance.NewTransaction();
        setReferNo(poIssuance.getMaster().getTransactionNo());
        return this.poIssuance;
    }
}