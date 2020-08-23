package ru.inversionkavkaz.btlclient.statusau.controller;

import javafx.fxml.FXML;
import ru.inversion.dataset.IDataSet;
import ru.inversion.dataset.XXIDataSet;
import ru.inversion.dataset.fx.DSFXAdapter;

import ru.inversion.fx.form.*;
import ru.inversion.fx.form.controls.*;
import ru.inversionkavkaz.btlclient.statusau.entity.PIkBtlBaseClientStatsAu;

/**
 *
 * @author  porche
 * @since   Fri Aug 14 16:21:20 MSK 2020
 */
public class ViewIkBtlBaseClientStatsAuController extends JInvFXBrowserController 
{
    public static final String INIT_PARAM_ICANCELPART = "icancelpart";
    public static String INIT_PARAM_ITRNNUM = "itrnnum";
    @FXML private JInvTable<PIkBtlBaseClientStatsAu> IK_BTL_BASE_CLIENT_STATS_AU;
    @FXML private JInvToolBar toolBar;

 
   
    private final XXIDataSet<PIkBtlBaseClientStatsAu> dsIK_BTL_BASE_CLIENT_STATS_AU = new XXIDataSet<> ();    
//
//
//    
    private void initDataSet () throws Exception 
    {
        dsIK_BTL_BASE_CLIENT_STATS_AU.setTaskContext (getTaskContext ());
        dsIK_BTL_BASE_CLIENT_STATS_AU.setRowClass (PIkBtlBaseClientStatsAu.class);
        dsIK_BTL_BASE_CLIENT_STATS_AU.setWherePredicat("itrnnum=" + getInitProperties().get(INIT_PARAM_ITRNNUM)
                        + " and icancelpart="+getInitProperties().get(INIT_PARAM_ICANCELPART));
        dsIK_BTL_BASE_CLIENT_STATS_AU.setOrderBy("ID");
    }
//
// Initializes the controller class.
//
    @Override
    protected void init() throws Exception
    {
        setTitle (getBundleString ("VIEW.TITLE"));
        
        initDataSet ();
        DSFXAdapter<PIkBtlBaseClientStatsAu> dsfx = DSFXAdapter.bind (dsIK_BTL_BASE_CLIENT_STATS_AU, IK_BTL_BASE_CLIENT_STATS_AU, null, false); 

        dsfx.setEnableFilter (true);
 
                
        initToolBar ();

        IK_BTL_BASE_CLIENT_STATS_AU.setToolBar (toolBar);       
//        IK_BTL_BASE_CLIENT_STATS_AU.setAction (ActionFactory.ActionTypeEnum.CREATE, (a) -> doOperation (FormModeEnum.VM_INS));
//        IK_BTL_BASE_CLIENT_STATS_AU.setAction (ActionFactory.ActionTypeEnum.VIEW, (a) -> doOperation (FormModeEnum.VM_SHOW));
//        IK_BTL_BASE_CLIENT_STATS_AU.setAction (ActionFactory.ActionTypeEnum.UPDATE, (a) -> doOperation (FormModeEnum.VM_EDIT));
//        IK_BTL_BASE_CLIENT_STATS_AU.setAction (ActionFactory.ActionTypeEnum.DELETE, (a) -> doOperation (FormModeEnum.VM_DEL));
        IK_BTL_BASE_CLIENT_STATS_AU.setAction (ActionFactory.ActionTypeEnum.REFRESH, (a) -> doRefresh ());

        doRefresh ();
    }        
//
//
//    
    private void doRefresh () 
    {
        IK_BTL_BASE_CLIENT_STATS_AU.executeQuery ();
    }
//
//
//    
    private void initToolBar () 
    {
        toolBar.setStandartActions (
//                ActionFactory.ActionTypeEnum.CREATE,
//                                    ActionFactory.ActionTypeEnum.VIEW,
//                                    ActionFactory.ActionTypeEnum.UPDATE,
//                                    ActionFactory.ActionTypeEnum.DELETE,
                                    ActionFactory.ActionTypeEnum.REFRESH);
        
//        toolBar.getItems ().add (ActionFactory.createButton(ActionFactory.ActionTypeEnum.SETTINGS, (a) -> JInvMainFrame.showSettingsPane ()));
    }
//
//
//    
    private void doOperation ( JInvFXFormController.FormModeEnum mode ) 
    {
//        PIkBtlBaseClientStatsAu p = null;
//
//        switch (mode) {
//            case VM_INS:
//                p = new PIkBtlBaseClientStatsAu ();
//                break;
//            case VM_EDIT:
//            case VM_SHOW:
//            case VM_DEL:
//                p = dsIK_BTL_BASE_CLIENT_STATS_AU.getCurrentRow ();
//                break;
//        }
//
//        if (p != null)
//            new FXFormLauncher<PIkBtlBaseClientStatsAu> (getTaskContext (), getViewContext (), EditIkBtlBaseClientStatsAuController.class)
//                .dataObject (p)
//                .dialogMode (mode)
//                .initProperties (getInitProperties ())
//                .callback (this::doFormResult)
//                .modal (true)
//                .show ();
    }
//
// 
//
    private void doFormResult ( JInvFXFormController.FormReturnEnum ok, JInvFXFormController<PIkBtlBaseClientStatsAu> dctl )    
    {
        if (JInvFXFormController.FormReturnEnum.RET_OK == ok)
        {
            switch (dctl.getFormMode ()) 
            {
                case VM_INS:
                    dsIK_BTL_BASE_CLIENT_STATS_AU.insertRow (dctl.getDataObject (), IDataSet.InsertRowModeEnum.AFTER_CURRENT, true);
                    break;
                case VM_EDIT:                
                    dsIK_BTL_BASE_CLIENT_STATS_AU.updateCurrentRow (dctl.getDataObject ());
                    break;
                case VM_DEL:
                    dsIK_BTL_BASE_CLIENT_STATS_AU.removeCurrentRow ();
                    break;
                default:
                    break;
            }                
        }    

        IK_BTL_BASE_CLIENT_STATS_AU.requestFocus ();
    }        
//
//
//    
}

