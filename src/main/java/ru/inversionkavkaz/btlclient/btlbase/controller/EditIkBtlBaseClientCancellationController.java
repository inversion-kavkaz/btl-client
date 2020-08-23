package ru.inversionkavkaz.btlclient.btlbase.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ru.inversion.bicomp.util.ParamMap;
import ru.inversion.dataset.DataSetException;
import ru.inversion.fx.app.AppException;
import ru.inversion.fx.form.AbstractBaseController;
import ru.inversion.fx.form.Alerts;
import ru.inversion.fx.form.FXEntity;
import ru.inversion.fx.form.JInvFXFormController;
import ru.inversion.fx.form.controls.JInvComboBox;
import ru.inversion.fx.form.controls.JInvLongField;
import ru.inversion.fx.form.controls.JInvTextField;
import ru.inversionkavkaz.btlclient.btlbase.entity.PIkBtlBaseClient;
import ru.inversionkavkaz.btlclient.cancelreason.entity.PIkBtlCancelReason;
import ru.inversionkavkaz.btlclient.utils.LovUtils;

import java.math.BigDecimal;

/**
 * @author  porche
 * @since   Fri Aug 14 12:38:15 MSK 2020
 */
public class EditIkBtlBaseClientCancellationController extends JInvFXFormController<PIkBtlBaseClient>
{

    public static final String DEF_XML = "/ru/inversionkavkaz/btlclient/btlbase/controller/res/plsql/def.xml";
    @FXML
    public JInvComboBox REQ_TYPE_NAME;
//
//
//
//    @FXML JInvLongField ITRNNUM;
//    @FXML JInvLongField ICANCELPART;
//    @FXML JInvTextField CREATED;
//    @FXML JInvLongField NUM;
//    @FXML JInvLongField REQ_TYPE;
//    @FXML JInvTextField REQ_DATE;
    @FXML JInvTextField MSUMM;
//    @FXML JInvTextField MSUMMR;
//    @FXML JInvTextField CCURCODE;
//    @FXML JInvTextField LIMIT_CODE;
//    @FXML JInvTextField PAYER_NAME;
//    @FXML JInvTextField PAYER_INN;
//    @FXML JInvTextField PAYER_BANK_BIC;
//    @FXML JInvTextField RECIP_NAME;
//    @FXML JInvTextField RECIP_INN;
//    @FXML JInvTextField CPURPPLATVO;
//    @FXML JInvTextField CREATE_USR;
//    @FXML JInvTextField AGREE_USR;
//    @FXML JInvLongField CANCEL_AGREE_NUM;
//    @FXML JInvLongField CANCEL_TRANSACTION_ID;
    @FXML
    JInvLongField CANCEL_REASON_CODE;
    @FXML
    JInvTextField CANCEL_REASON_MESSAGE;
//    @FXML JInvTextField STATUS_CODE;
//    @FXML JInvTextField STATUS_MESSAGE;
//    @FXML JInvTextField STATUS_DATE;
//    @FXML JInvLongField REMOTE_TRANSACTION_ID;

//
// Initializes the controller class.
//
    @Override
    protected void init () throws Exception
    {
        super.init ();
        LovUtils.bindLov(PIkBtlCancelReason.class, Long.class, CANCEL_REASON_CODE, CANCEL_REASON_MESSAGE, getResourceBundle(PIkBtlCancelReason.class));
    }

    @Override
    protected void afterInit() throws AppException {
        super.afterInit();
    }

    void doCancel(Long pITrnNum, Long pCancelPart, BigDecimal pCancelSumm, String pCurrCode, Long pCancelReasonCode, String pCancelReasonMessage) {
        //создаем новую отмену
        ParamMap pm = new ParamMap();
        pm.put("pITrnNum", pITrnNum);
        pm.put("pCancelPart", pCancelPart);
        pm.put("pCancelSumm", Math.abs(pCancelSumm.doubleValue()) * -1);
        pm.put("pCancelCurrCode",pCurrCode);
        pm.put("pCancelReasonCode", pCancelReasonCode);
        pm.put("pCancelReasonMessage", pCancelReasonMessage);
        pm.execParallel(this, getClass().getResource(DEF_XML), "ik_btl_client.registerPartCancel", this::onSendCancelRequest);
    }

    private void onSendCancelRequest(ParamMap pm) {
        String errorMessage = (String) pm.get("outErrorMessage");
        Long newCancelPart = (Long) pm.get("outNewCancelPart");
        Long statusCode = (Long) pm.get("statusCode");
        Alerts.info(this, "Обработка запроса", errorMessage);
        close(FormReturnEnum.RET_OK);
    }

    @Override
    protected boolean onOK() {
        return true;
        //return super.onOK();
    }

    public void onSendCancelRequest(ActionEvent actionEvent) {
        getFXEntity().commit();
        if(!this.validMan.validate()){
            return;
        }
        //if validate() ...
        doCancel(getDataObject().getITRNNUM(), getDataObject().getICANCELPART(), getDataObject().getMSUMM(), getDataObject().getCCURCODE(), getDataObject().getCANCEL_REASON_CODE(), getDataObject().getCANCEL_REASON_MESSAGE());
    }
}



