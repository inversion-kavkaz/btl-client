package ru.inversionkavkaz.btlclient.btlbase.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import ru.inversion.bicomp.action.JInvButtonPrint;
import ru.inversion.bicomp.fxreport.ApReport;
import ru.inversion.bicomp.oradev.OracleForm;
import ru.inversion.bicomp.stringconverter.DataSetStringConverter;
import ru.inversion.bicomp.util.ParamMap;
import ru.inversion.dataset.*;
import ru.inversion.dataset.fx.DSFXAdapter;
import ru.inversion.dataset.aggr.AggrFuncEnum;
import ru.inversion.dataset.fx.F7FilterGroup;
import ru.inversion.fx.app.AppException;
import ru.inversion.fx.form.controls.dsbar.DSInfoBar;
import ru.inversion.fx.form.controls.renderer.JInvTableCell;
import ru.inversion.fx.form.controls.table.toolbar.AggregatorType;
import ru.inversion.fx.form.*;
import ru.inversion.fx.form.controls.*;
import ru.inversion.icons.enums.FontAwesome;
import ru.inversionkavkaz.btlclient.btlbase.entity.PIkBtlBaseClient;
import ru.inversionkavkaz.btlclient.btlbase.entity.PRstType;
import ru.inversionkavkaz.btlclient.cancelreason.controller.ViewIkBtlCancelReasonController;
import ru.inversionkavkaz.btlclient.cancelreason.entity.PIkBtlCancelReason;
import ru.inversionkavkaz.btlclient.limits.controller.ViewIkBtlLimController;
import ru.inversionkavkaz.btlclient.limits.entity.PIkBtlLim;
import ru.inversionkavkaz.btlclient.rejectreason.controller.ViewIkBtlRejectReasonController;
import ru.inversionkavkaz.btlclient.rejectreason.entity.PIkBtlRejectReason;
import ru.inversionkavkaz.btlclient.statusau.controller.ViewIkBtlBaseClientStatsAuController;
import ru.inversionkavkaz.btlclient.utils.AltPrintReportType;
import ru.inversionkavkaz.btlclient.utils.ButtonUtils;

import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * @author porche
 * @since Fri Aug 14 12:38:10 MSK 2020
 */
public class ViewIkBtlBaseClientController extends JInvFXBrowserController {
    public static final String STYLE_ERROR = "-fx-background-color: #ff182c;\n" +
            "    -fx-background-insets: 0 1 1 0;\n" +
            "    -fx-text-fill: #ffffff;\n";
    public static final String STYLE_REJECTED = "-fx-background-color: #ffd6e4;\n" +
            "    -fx-background-insets: 0 1 1 0;\n" +
            "    -fx-text-fill: black;\n";
    public static final String STYLE_NEW = "-fx-background-color: #f6ebbc;\n" +
            "    -fx-background-insets: 0 1 1 0;\n" +
            "    -fx-text-fill: black;\n";
    public static final String SQL_RST_WORK_ONLY = "REQ_TYPE = 1 and STATUS_CODE = 0 and MSUMM = -1 * MSUMMCANCEL";
    public static final String SQL_RST_WORK_AND_CANCELLED = "REQ_TYPE = 1 and STATUS_CODE = 0";
    public static final String SQL_RST_ALL = "REQ_TYPE = 1";

    public static final int STATUS_CODE_NEW = -1;
    public static final int STATUS_CODE_ACCEPTED = 0;
    public static final int STATUS_CODE_REJECTED = 1;

    @FXML
    private JInvTable<PIkBtlBaseClient> IK_BTL_BASE_CLIENT;
    @FXML
    private JInvTableColumn STATUS_CODE;
    @FXML
    private JInvTableColumn MSUMMCANCELPLAN;
    @FXML
    private JInvToolBar toolBar;
    private JInvComboBox<Long, String> RSTTYPE = new JInvComboBox();
    JInvButton cancelButton;
    @FXML
    private DSInfoBar IK_BTL_BASE_CLIENT$MARK;

    JInvButtonPrint altPrint;


    private final XXIDataSet<PIkBtlBaseClient> dsIK_BTL_BASE_CLIENT = new XXIDataSet<>();

//
//
//    
    private void initDataSet()  {
        dsIK_BTL_BASE_CLIENT.setTaskContext(getTaskContext());
        dsIK_BTL_BASE_CLIENT.setRowClass(PIkBtlBaseClient.class);
        //по умолчанию
        dsIK_BTL_BASE_CLIENT.wherePredicat(SQL_RST_WORK_ONLY);
    }

//
// Initializes the controller class.
//
    @Override
    protected void init() throws Exception {
        setTitle(getBundleString("VIEW.TITLE"));

        SQLDataSet<PRstType> dsRstType;
        dsRstType = populateDataSet(PRstType.class, null, null, "ID", 2);
        DataSetStringConverter<PRstType, Long> scRstType = new DataSetStringConverter<>(dsRstType, PRstType::getID, PRstType::getCNAME);
        RSTTYPE.setConverter(scRstType);
        RSTTYPE.getItems().addAll(scRstType.keySet());
        RSTTYPE.setValue(1L); //можно заполнить как свойство на пользователя

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
//        IK_BTL_BASE_CLIENT.setAction(ActionFactory.ActionTypeEnum.CREATE, (a) -> doOperation(FormModeEnum.VM_INS));
        IK_BTL_BASE_CLIENT.setAction(ActionFactory.ActionTypeEnum.VIEW, (a) -> doOperation(FormModeEnum.VM_SHOW));
//        IK_BTL_BASE_CLIENT.setAction(ActionFactory.ActionTypeEnum.UPDATE, (a) -> doOperation(FormModeEnum.VM_EDIT));
//        IK_BTL_BASE_CLIENT.setAction(ActionFactory.ActionTypeEnum.DELETE, (a) -> doOperation(FormModeEnum.VM_DEL));
        IK_BTL_BASE_CLIENT.setAction(ActionFactory.ActionTypeEnum.REFRESH, (a) -> doRefresh());

        IK_BTL_BASE_CLIENT.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            cancelButton.setDisable(newValue.getSTATUS_CODE()!=STATUS_CODE_ACCEPTED);
        });

        doRefresh();
    }

    @Override
    protected void afterInit() throws AppException {
        super.afterInit();
        RSTTYPE.valueProperty().addListener((v, o, n) -> {
            switch (RSTTYPE.getValue().intValue()){
                case 1:
                    dsIK_BTL_BASE_CLIENT.wherePredicat(SQL_RST_WORK_ONLY);
                    break;
                case 2:
                    dsIK_BTL_BASE_CLIENT.wherePredicat(SQL_RST_WORK_AND_CANCELLED);
                    break;
                case 3:
                    dsIK_BTL_BASE_CLIENT.wherePredicat(SQL_RST_ALL);
                    break;
            }
            IK_BTL_BASE_CLIENT.requestFocus();
            doRefresh();
        });
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
        toolBar.setStandartActions(
//                ActionFactory.ActionTypeEnum.CREATE,
                ActionFactory.ActionTypeEnum.VIEW,
//                ActionFactory.ActionTypeEnum.UPDATE,
//                ActionFactory.ActionTypeEnum.DELETE,
                ActionFactory.ActionTypeEnum.REFRESH);

//        toolBar.getItems ().add (ActionFactory.createButton(ActionFactory.ActionTypeEnum.SETTINGS, (a) -> JInvMainFrame.showSettingsPane ()));
        cancelButton = ButtonUtils.addBtn(toolBar, "Аннулировать", "Аннулирование платежа", FontAwesome.fa_arrow_circle_o_left, this::cancelPayment);
        ButtonUtils.addBtn(toolBar, "Статусы", "История изменения статусов", FontAwesome.fa_exclamation_circle, this::showStatusHistory);
        ButtonUtils.addBtn(toolBar, "Валютный контроль", "Показать документы предварительного контроля", FontAwesome.fa_archive, this::runPrecheckFMX);

        altPrint = new JInvButtonPrint(this::prePrintAp);
        altPrint.setReportTypeId(AltPrintReportType.PRINT_BTL_BASE);
        toolBar.getItems().add(altPrint);

        toolBar.getItems().add(new JInvLabel("Отображать операции:"));
        toolBar.getItems().add(RSTTYPE);
    }


//
//
//    
    private void doOperation(JInvFXFormController.FormModeEnum mode) {
        PIkBtlBaseClient p = null;

        switch (mode) {
            case VM_INS:
                p = new PIkBtlBaseClient();
                break;
            case VM_EDIT:
            case VM_SHOW:
            case VM_DEL:
                p = dsIK_BTL_BASE_CLIENT.getCurrentRow();
                break;
        }

        if (p != null)
            new FXFormLauncher<PIkBtlBaseClient>(getTaskContext(), getViewContext(), EditIkBtlBaseClientController.class)
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
                    dsIK_BTL_BASE_CLIENT.insertRow(dctl.getDataObject(), IDataSet.InsertRowModeEnum.AFTER_CURRENT, true);
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
                .dialogMode(FormModeEnum.VM_SHOW)
                .modal(true)
                .show();
    }

    public void onShowRejectReasons(ActionEvent actionEvent) {
        new FXFormLauncher<PIkBtlRejectReason>(getTaskContext(), getViewContext(), ViewIkBtlRejectReasonController.class)
                .dialogMode(FormModeEnum.VM_SHOW)
                .modal(true)
                .show();
    }

    public void onShowCancelReasons(ActionEvent actionEvent) {
        new FXFormLauncher<PIkBtlCancelReason>(getTaskContext(), getViewContext(), ViewIkBtlCancelReasonController.class)
                .dialogMode(FormModeEnum.VM_SHOW)
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
        MSUMMCANCELPLAN.setCellRenderer((BiConsumer<JInvTableCell<PIkBtlBaseClient, Long>, Long>) (cell, aString) -> {
            PIkBtlBaseClient ikBtlBaseClient = (PIkBtlBaseClient) cell.getTableRow().getItem();
            if (ikBtlBaseClient != null && ikBtlBaseClient.getMSUMMCANCELPLAN()!=null&&ikBtlBaseClient.getMSUMMCANCELPLAN().doubleValue()!=0) {
               cell.setStyle(STYLE_REJECTED);
        };});
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

    private void cancelPayment(Event event) {
        PIkBtlBaseClient dataObject = dsIK_BTL_BASE_CLIENT.getCurrentRow();
        if (dataObject == null) return;
        new FXFormLauncher<PIkBtlBaseClient>(getTaskContext(), getViewContext(), ViewIkBtlBaseClientCancellationController.class)
                .dataObject(dataObject)
                .dialogMode(FormModeEnum.VM_EDIT)
                .initProperties(getInitProperties())
                .callback(this::doFormResult)
                .modal(true)
                .show();
    }

    private void runPrecheckFMX(Event event) {
        if (dsIK_BTL_BASE_CLIENT.getMarkerID() == null) {
            return;
        }
        getTaskContext().commit();
        HashMap<String, Object> param = new HashMap<>();
        param.put("WHERE", "\"(V_DPC.itrnnum, V_DPC.itrnanum) in (select bc.itrnnum, 0 from ik_btl_base_client bc, mrk bm where bc.rowid = bm.rmrkrowid and bm.imrkmarkerid=" + dsIK_BTL_BASE_CLIENT.getMarkerID() + ")\"");
//        param.put ("ID_Group", "1");
        OracleForm.RunProduct(getTaskContext(), "precheck", param);
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

