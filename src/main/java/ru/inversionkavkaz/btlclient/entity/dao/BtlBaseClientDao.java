package ru.inversionkavkaz.btlclient.entity.dao;

import ru.inversion.bicomp.util.ParamMap;
import ru.inversion.db.expr.SQLExpressionException;
import ru.inversion.fx.form.AbstractBaseController;

import java.math.BigDecimal;
import java.sql.Connection;

public class BtlBaseClientDao extends BaseDao{
    private static final String DEF_XML = "/ru/inversionkavkaz/btlclient/btlbase/controller/res/plsql/def.xml";

    public static class CancelRestInfo {
        private BigDecimal msumm;
        private Long cancelPartCount;

        public BigDecimal getMsumm() {
            return msumm;
        }

        public Long getCancelPartCount() {
            return cancelPartCount;
        }

        CancelRestInfo(BigDecimal msumm, Long cancelPartCount) {
            this.msumm = msumm;
            this.cancelPartCount = cancelPartCount;
        }
    }

    public BtlBaseClientDao(Connection connection) {
        super(connection);
    }

    public CancelRestInfo getCancelRestInfo(Long pITrnNum, AbstractBaseController controller){
        try {
            ParamMap pm = new ParamMap();
            pm.add("pITrnNum",pITrnNum);
            pm.exec(controller, this.getClass().getResource(DEF_XML), "ik_btl_client.getCancelRestInfo");
            return new CancelRestInfo(BigDecimal.valueOf((Double)pm.get("outSumm")), (Long) pm.get("outCancelPartCount"));
        } catch (SQLExpressionException e) {
            e.printStackTrace();
            return null;
        }
    }
}
