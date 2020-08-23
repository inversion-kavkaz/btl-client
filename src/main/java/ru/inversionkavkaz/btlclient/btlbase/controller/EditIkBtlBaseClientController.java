package ru.inversionkavkaz.btlclient.btlbase.controller;

import javafx.fxml.FXML;
import ru.inversion.fx.form.JInvFXFormController;
import ru.inversion.fx.form.controls.JInvComboBox;
import ru.inversion.fx.form.controls.JInvLongField;
import ru.inversion.fx.form.controls.JInvTextField;
import ru.inversionkavkaz.btlclient.btlbase.entity.PIkBtlBaseClient;
import ru.inversionkavkaz.btlclient.cancelreason.entity.PIkBtlCancelReason;
import ru.inversionkavkaz.btlclient.utils.LovUtils;

import java.util.ResourceBundle;

/**
 * @author  porche
 * @since   Fri Aug 14 12:38:15 MSK 2020
 */
public class EditIkBtlBaseClientController extends JInvFXFormController <PIkBtlBaseClient>
{

    @FXML public JInvComboBox REQ_TYPE_NAME;

    //
//
//
//    @FXML JInvLongField ITRNNUM;
//    @FXML JInvLongField ICANCELPART;
//    @FXML JInvTextField CREATED;
//    @FXML JInvLongField NUM;
//    @FXML JInvLongField REQ_TYPE;
//    @FXML JInvTextField REQ_DATE;
//    @FXML JInvTextField MSUMM;
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
    @FXML JInvLongField CANCEL_REASON_CODE;
    @FXML JInvTextField CANCEL_REASON_MESSAGE;
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

}

