package ru.inversionkavkaz.btlclient.btlbase.controller;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import ru.inversion.bicomp.action.JInvButtonPrint;
import ru.inversion.bicomp.fxreport.ApReport;
import ru.inversion.bicomp.util.ParamMap;
import ru.inversion.bicomp.util.WhenNewRecordInstance;
import ru.inversion.dataset.DataSetException;
import ru.inversion.dataset.XXIDataSet;
import ru.inversion.dataset.aggr.AggrFuncEnum;
import ru.inversion.dataset.fx.DSFXAdapter;
import ru.inversion.dataset.fx.F7FilterGroup;
import ru.inversion.fx.app.AppException;
import ru.inversion.fx.form.*;
import ru.inversion.fx.form.action.IAction;
import ru.inversion.fx.form.controls.JInvTable;
import ru.inversion.fx.form.controls.JInvTableColumn;
import ru.inversion.fx.form.controls.JInvToolBar;
import ru.inversion.fx.form.controls.dsbar.DSInfoBar;
import ru.inversion.fx.form.controls.renderer.JInvTableCell;
import ru.inversion.fx.form.controls.table.toolbar.AggregatorType;
import ru.inversion.icons.enums.FontAwesome;
import ru.inversionkavkaz.btlclient.btlbase.entity.PIkBtlBaseClient;
import ru.inversionkavkaz.btlclient.cancelreason.controller.ViewIkBtlCancelReasonController;
import ru.inversionkavkaz.btlclient.cancelreason.entity.PIkBtlCancelReason;
import ru.inversionkavkaz.btlclient.entity.dao.BtlBaseClientDao;
import ru.inversionkavkaz.btlclient.entity.dao.DaoFactory;
import ru.inversionkavkaz.btlclient.limits.controller.ViewIkBtlLimController;
import ru.inversionkavkaz.btlclient.limits.entity.PIkBtlLim;
import ru.inversionkavkaz.btlclient.rejectreason.controller.ViewIkBtlRejectReasonController;
import ru.inversionkavkaz.btlclient.rejectreason.entity.PIkBtlRejectReason;
import ru.inversionkavkaz.btlclient.statusau.controller.ViewIkBtlBaseClientStatsAuController;
import ru.inversionkavkaz.btlclient.utils.AltPrintReportType;
import ru.inversionkavkaz.btlclient.utils.ButtonUtils;

import java.math.BigDecimal;
import java.util.function.BiConsumer;

public class ViewIkBtlBaseClientCancellationController extends JInvFXBrowserController {
    public static final String STYLE_ERROR = "-fx-background-color: #ff182c;\n" +
            "    -fx-background-insets: 0 1 1 0;\n" +
            "    -fx-text-fill: #ffffff;\n";
    public static final String STYLE_REJECTED = "-fx-background-color: #ffd6e4;\n" +
            "    -fx-background-insets: 0 1 1 0;\n" +
            "    -fx-text-fill: black;\n";
    public static final String STYLE_NEW = "-fx-background-color: #f6ebbc;\n" +
            "    -fx-background-insets: 0 1 1 0;\n" +
            "    -fx-text-fill: black;\n";
    public static final int STATUS_CODE_NEW = -1;
    public static final int STATUS_CODE_ACCEPTED = 0;
    public static final int STATUS_CODE_REJECTED = 1;
    public static final int DELETE_STANDART_ACTION_INDEX = 3;

    @FXML
    private JInvTable<PIkBtlBaseClient> IK_BTL_BASE_CLIENT;
    @FXML
    private JInvTableColumn STATUS_CODE;
    @FXML
    private JInvToolBar toolBar;
    @FXML
    private DSInfoBar IK_BTL_BASE_CLIENT$MARK;

    private JInvButtonPrint altPrint;

    private final XXIDataSet<PIkBtlBaseClient> dsIK_BTL_BASE_CLIENT = new XXIDataSet<>();

    private void initDataSet() throws Exception {
        dsIK_BTL_BASE_CLIENT.setTaskContext(getTaskContext());
        dsIK_BTL_BASE_CLIENT.setRowClass(PIkBtlBaseClient.class);
        //Отображаем только отмены
        dsIK_BTL_BASE_CLIENT.wherePredicat("ITRNNUM=" + ((PIkBtlBaseClient) getDataObject()).getITRNNUM() + " and ICANCELPART > 0");
        dsIK_BTL_BASE_CLIENT.orderBy("ICANCELPART");
    }

 //
// Initializes the controller class.
//
    @Override
    protected void init() throws Exception {
        setTitle(getBundleString("VIEW.TITLE"));

        initDataSet();
        DSFXAdapter<PIkBtlBaseClient> dsfx = DSFXAdapter.bind(dsIK_BTL_BASE_CLIENT, IK_BTL_BASE_CLIENT, null, true);

        dsfx.setFilterGroups(
                new F7FilterGroup("F7PAYER_GROUP", "Плательщик", 2, true),
                new F7FilterGroup("F7RECIP_GROUP", "Получатель", 3, true),
                new F7FilterGroup("F7CANCEL_GROUP", "Аннулирование", 4, true),
                new F7FilterGroup("F7USER_GROUP", "Пользователь", 5, true),
                new F7FilterGroup("F7STATUS_GROUP", "Статус", 6, true)
        );
        dsfx.setEnableFilter(true, getName(), "IK_BTL_BASE_CLIENT");

        IK_BTL_BASE_CLIENT$MARK.init(IK_BTL_BASE_CLIENT.getDataSetAdapter());
        IK_BTL_BASE_CLIENT$MARK.addAggregator("1", AggrFuncEnum.COUNT, AggregatorType.MARK, null, null);

        initToolBar();
        initCellRenderer();

        IK_BTL_BASE_CLIENT.setToolBar(toolBar);
        IK_BTL_BASE_CLIENT.setAction(ActionFactory.ActionTypeEnum.CREATE, (a) -> doOperation(AbstractBaseController.FormModeEnum.VM_INS));
        IK_BTL_BASE_CLIENT.setAction(ActionFactory.ActionTypeEnum.VIEW, (a) -> doOperation(AbstractBaseController.FormModeEnum.VM_SHOW));
        IK_BTL_BASE_CLIENT.setAction(ActionFactory.ActionTypeEnum.UPDATE, (a) -> doOperation(AbstractBaseController.FormModeEnum.VM_EDIT));
        IK_BTL_BASE_CLIENT.setAction(ActionFactory.ActionTypeEnum.DELETE, (a) -> doOperation(AbstractBaseController.FormModeEnum.VM_DEL));
        IK_BTL_BASE_CLIENT.setAction(ActionFactory.ActionTypeEnum.REFRESH, (a) -> doRefresh());

        IK_BTL_BASE_CLIENT.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            toolBar.getItems().get(DELETE_STANDART_ACTION_INDEX).setDisable(newValue.getSTATUS_CODE()!=STATUS_CODE_NEW);
        });

        doRefresh();
    }

//
//
//
    private void doRefresh() {
        IK_BTL_BASE_CLIENT.executeQuery();
    }

//
//
//
    private void initToolBar() {
        toolBar.setStandartActions(ActionFactory.ActionTypeEnum.CREATE,
                ActionFactory.ActionTypeEnum.VIEW,
                ActionFactory.ActionTypeEnum.UPDATE,
                ActionFactory.ActionTypeEnum.DELETE,
                ActionFactory.ActionTypeEnum.REFRESH);
//        toolBar.getItems ().add (ActionFactory.createButton(ActionFactory.ActionTypeEnum.SETTINGS, (a) -> JInvMainFrame.showSettingsPane ()));
//        ButtonUtils.addBtn(toolBar, "Аннулировать", "Аннулирование платежа", FontAwesome.fa_plus_circle, this::cancelPayment);
        ButtonUtils.addBtn(toolBar, "Статусы", "История изменения статусов", FontAwesome.fa_exclamation_circle, this::showStatusHistory);
        altPrint = new JInvButtonPrint(this::prePrintAp);
        altPrint.setReportTypeId(AltPrintReportType.PRINT_BTL_BASE);
        toolBar.getItems().add(altPrint);
    }

//
//
//
    private void doOperation(JInvFXFormController.FormModeEnum mode) {
        PIkBtlBaseClient p = null;

        switch (mode) {
            case VM_INS:
                PIkBtlBaseClient baseReq = dsIK_BTL_BASE_CLIENT.getCurrentRow();
                p = new PIkBtlBaseClient();
                BtlBaseClientDao.CancelRestInfo cancelRestInfo = DaoFactory.getInstance(getTaskContext().getConnection()).getBtlBaseClientDao().getCancelRestInfo(baseReq.getITRNNUM(), this);
                p.setITRNNUM(baseReq.getITRNNUM());
                p.setCCURCODE(baseReq.getCCURCODE());
                if (cancelRestInfo != null) {
                    p.setMSUMMCANCEL(cancelRestInfo.getMsumm());
                    p.setICANCELPART(cancelRestInfo.getCancelPartCount() + 1);
                }
            break;
            case VM_EDIT:
            case VM_SHOW:
            case VM_DEL:
                p = dsIK_BTL_BASE_CLIENT.getCurrentRow();
                p.setMSUMM(BigDecimal.valueOf(Math.abs(p.getMSUMM().doubleValue())));
                p.setMSUMMR(BigDecimal.valueOf(Math.abs(p.getMSUMMR().doubleValue())));
                //к сожалению тут дополнительно проверяем на возможность удаления
                //пока не разобрались с action delete
                if(mode==FormModeEnum.VM_DEL && p.getSTATUS_CODE()!=STATUS_CODE_NEW) return;
            break;
        }

        if (p != null)
            new FXFormLauncher<PIkBtlBaseClient>(getTaskContext(), getViewContext(), EditIkBtlBaseClientCancellationController.class)
                    .dataObject(p)
                    .dialogMode(mode)
                    .initProperties(getInitProperties())
                    .callback(this::doFormResult)
                    .modal(true)
                    .show();
    }

    //
//
//
    private void doFormResult(JInvFXFormController.FormReturnEnum ok, JInvFXFormController<PIkBtlBaseClient> dctl) {
        if (JInvFXFormController.FormReturnEnum.RET_OK == ok) {
            switch (dctl.getFormMode()) {
                case VM_INS:
                    //dsIK_BTL_BASE_CLIENT.insertRow (dctl.getDataObject (), IDataSet.InsertRowModeEnum.AFTER_CURRENT, true);
                    doRefresh();
                    break;
                case VM_EDIT:
                    dsIK_BTL_BASE_CLIENT.updateCurrentRow(dctl.getDataObject());
                    break;
                case VM_DEL:
                    dsIK_BTL_BASE_CLIENT.removeCurrentRow();
                    break;
                default:
                    break;
            }
        }

        IK_BTL_BASE_CLIENT.requestFocus();
    }


    public void onPrint(ActionEvent actionEvent) {
        if (altPrint != null) altPrint.fire();
    }

    public void onShowLimits(ActionEvent actionEvent) {
        new FXFormLauncher<PIkBtlLim>(getTaskContext(), getViewContext(), ViewIkBtlLimController.class)
                .dialogMode(AbstractBaseController.FormModeEnum.VM_SHOW)
                .modal(true)
                .show();
    }

    public void onShowRejectReasons(ActionEvent actionEvent) {
        new FXFormLauncher<PIkBtlRejectReason>(getTaskContext(), getViewContext(), ViewIkBtlRejectReasonController.class)
                .dialogMode(AbstractBaseController.FormModeEnum.VM_SHOW)
                .modal(true)
                .show();
    }

    public void onShowCancelReasons(ActionEvent actionEvent) {
        new FXFormLauncher<PIkBtlCancelReason>(getTaskContext(), getViewContext(), ViewIkBtlCancelReasonController.class)
                .dialogMode(AbstractBaseController.FormModeEnum.VM_SHOW)
                .modal(true)
                .show();
    }

    private void prePrintAp(ApReport apReport) {
        String p1 = dsIK_BTL_BASE_CLIENT.getCurrentRow() == null ? "" : dsIK_BTL_BASE_CLIENT.getCurrentRow().getITRNNUM().toString();
        String p2 = dsIK_BTL_BASE_CLIENT.getCurrentRow() == null ? "" : dsIK_BTL_BASE_CLIENT.getCurrentRow().getICANCELPART().toString();
        String p3 = dsIK_BTL_BASE_CLIENT.getMarkerID() == null ? "" : dsIK_BTL_BASE_CLIENT.getMarkerID().toString();
        apReport.setParam(p1, p2, p3);
    }

    private void initCellRenderer() {
        STATUS_CODE.setCellRenderer((BiConsumer<JInvTableCell<PIkBtlBaseClient, Long>, Long>) (cell, aString) -> {
            PIkBtlBaseClient ikBtlBaseClient = (PIkBtlBaseClient) cell.getTableRow().getItem();
            if (ikBtlBaseClient != null) {
                switch (ikBtlBaseClient.getSTATUS_CODE().intValue()) {
                    case STATUS_CODE_NEW:
                        cell.setStyle(STYLE_NEW);
                        break;
                    case STATUS_CODE_ACCEPTED:
                        break;
                    case STATUS_CODE_REJECTED:
                        cell.setStyle(STYLE_REJECTED);
                        break;
                    default:
                        cell.setStyle(STYLE_ERROR);
                }
            }
        });
    }

    private void showStatusHistory(Event event) {
        if (!dsIK_BTL_BASE_CLIENT.isEmpty()) {
            ParamMap pm = new ParamMap();
            pm.put(ViewIkBtlBaseClientStatsAuController.INIT_PARAM_ITRNNUM, dsIK_BTL_BASE_CLIENT.getCurrentRow().getITRNNUM());
            pm.put(ViewIkBtlBaseClientStatsAuController.INIT_PARAM_ICANCELPART, dsIK_BTL_BASE_CLIENT.getCurrentRow().getICANCELPART());
            new FXFormLauncher<>(getTaskContext(), getViewContext(), ViewIkBtlBaseClientStatsAuController.class)
                    .initProperties(pm)
                    .modal(true)
                    .show();
        }
    }


    public void onExit(ActionEvent actionEvent) {
        if (dsIK_BTL_BASE_CLIENT.getMarkerID() != null) {
            try {
                dsIK_BTL_BASE_CLIENT.clearMark();
            } catch (DataSetException e) {
                e.printStackTrace();
            }
        }
        getTaskContext().commit();
        this.close();
    }
}

