package ru.inversionkavkaz.btlclient.btlbase.entity;

import java.math.BigDecimal;
import java.time.*;
import java.io.Serializable;
import javax.persistence.*;
import ru.inversion.dataset.mark.*;
import ru.inversion.db.entity.ProxyFor;

/**
 * @author porche
 * @since 2020/08/14 12:38:05
 */
@Entity(name = "ru.inversionkavkaz.btlclient.btlbase.entity.PIkBtlBaseClient")
@Table(name = "IK_BTL_BASE_CLIENT")
@MarkColumns(columns = {"ROWID"})
public class PIkBtlBaseClient extends RowIDMarkable implements Serializable {
    private static final long serialVersionUID = 14_08_2020_12_38_05l;
    private String ROWID;
    private Long ITRNNUM;
    private Long ICANCELPART;
    private LocalDateTime CREATED;
    private Long NUM;
    private Long REQ_TYPE;
    private LocalDateTime REQ_DATE;
    private BigDecimal MSUMM;
    private BigDecimal MSUMMR;
    private String CCURCODE;
    private String LIMIT_CODE;
    private String PAYER_NAME;
    private String PAYER_INN;
    private String PAYER_BANK_BIC;
    private String RECIP_NAME;
    private String RECIP_INN;
    private String CPURPPLATVO;
    private String CREATE_USR;
    private String AGREE_USR;
    private Long CANCEL_AGREE_NUM;
    private Long CANCEL_TRANSACTION_ID;
    private Long CANCEL_REASON_CODE;
    private String CANCEL_REASON_MESSAGE;
    private Long STATUS_CODE;
    private String STATUS_MESSAGE;
    private LocalDateTime STATUS_DATE;
    private Long REMOTE_TRANSACTION_ID;
    private BigDecimal MSUMMCANCEL;
    private BigDecimal MSUMMCANCELPLAN;
    private Long ICANCELPARTCOUNT;


    public PIkBtlBaseClient() {
    }

    public PIkBtlBaseClient(PIkBtlBaseClient source) {
        this.ROWID = source.ROWID;
        this.ITRNNUM = source.ITRNNUM;
        this.ICANCELPART = source.ICANCELPART;
        this.CREATED = source.CREATED;
        this.NUM = source.NUM;
        this.REQ_TYPE = source.REQ_TYPE;
        this.REQ_DATE = source.REQ_DATE;
        this.MSUMM = source.MSUMM;
        this.MSUMMR = source.MSUMMR;
        this.CCURCODE = source.CCURCODE;
        this.LIMIT_CODE = source.LIMIT_CODE;
        this.PAYER_NAME = source.PAYER_NAME;
        this.PAYER_INN = source.PAYER_INN;
        this.PAYER_BANK_BIC = source.PAYER_BANK_BIC;
        this.RECIP_NAME = source.RECIP_NAME;
        this.RECIP_INN = source.RECIP_INN;
        this.CPURPPLATVO = source.CPURPPLATVO;
        this.CREATE_USR = source.CREATE_USR;
        this.AGREE_USR = source.AGREE_USR;
        this.CANCEL_AGREE_NUM = source.CANCEL_AGREE_NUM;
        this.CANCEL_TRANSACTION_ID = source.CANCEL_TRANSACTION_ID;
        this.CANCEL_REASON_CODE = source.CANCEL_REASON_CODE;
        this.CANCEL_REASON_MESSAGE = source.CANCEL_REASON_MESSAGE;
        this.STATUS_CODE = source.STATUS_CODE;
        this.STATUS_MESSAGE = source.STATUS_MESSAGE;
        this.STATUS_DATE = source.STATUS_DATE;
        this.REMOTE_TRANSACTION_ID = source.REMOTE_TRANSACTION_ID;
        this.MSUMMCANCEL= source.MSUMMCANCEL;
        this.MSUMMCANCELPLAN = source.MSUMMCANCELPLAN;
        this.ICANCELPARTCOUNT = source.ICANCELPARTCOUNT;
    }

    @Id
    @Column(name = "ITRNNUM", nullable = false, length = 0)
    public Long getITRNNUM() {
        return ITRNNUM;
    }

    public void setITRNNUM(Long val) {
        ITRNNUM = val;
    }

    @Id
    @Column(name = "ICANCELPART", nullable = false, length = 0)
    public Long getICANCELPART() {
        return ICANCELPART;
    }

    public void setICANCELPART(Long val) {
        ICANCELPART = val;
    }

    @Column(name = "ROWID", nullable = false, insertable = false, updatable = false)
    public String getROWID() {
        return ROWID;
    }

    public void setROWID(String val) {
        ROWID = val;
    }

    @Column(name = "CREATED", nullable = false)
    public LocalDateTime getCREATED() {
        return CREATED;
    }

    public void setCREATED(LocalDateTime val) {
        CREATED = val;
    }

    @Column(name = "NUM", length = 0)
    public Long getNUM() {
        return NUM;
    }

    public void setNUM(Long val) {
        NUM = val;
    }

    @Column(name = "REQ_TYPE", nullable = false, length = 1)
    public Long getREQ_TYPE() {
        return REQ_TYPE;
    }

    public void setREQ_TYPE(Long val) {
        REQ_TYPE = val;
    }

    @Transient
    @ProxyFor(columnName = "REQ_TYPE")
    public ReqTypeEnum getReqTypeName() {
        return ReqTypeEnum.fromDatabase(REQ_TYPE);
    }

    public void setReqTypeName(ReqTypeEnum val) {
        setREQ_TYPE(Long.parseLong(ReqTypeEnum.toDatabase(val)));
    }

    @Column(name = "REQ_DATE", nullable = false)
    public LocalDateTime getREQ_DATE() {
        return REQ_DATE;
    }

    public void setREQ_DATE(LocalDateTime val) {
        REQ_DATE = val;
    }

    @Column(name = "MSUMM", nullable = false, length = 10)
    public BigDecimal getMSUMM() {
        return MSUMM;
    }

    public void setMSUMM(BigDecimal val) {
        MSUMM = val;
    }

    @Column(name = "MSUMMR", nullable = false, length = 10)
    public BigDecimal getMSUMMR() {
        return MSUMMR;
    }

    public void setMSUMMR(BigDecimal val) {
        MSUMMR = val;
    }

    @Column(name = "CCURCODE", nullable = false, length = 3)
    public String getCCURCODE() {
        return CCURCODE;
    }

    public void setCCURCODE(String val) {
        CCURCODE = val;
    }

    @Column(name = "LIMIT_CODE", nullable = false, length = 1)
    public String getLIMIT_CODE() {
        return LIMIT_CODE;
    }

    public void setLIMIT_CODE(String val) {
        LIMIT_CODE = val;
    }

    @Column(name = "PAYER_NAME", nullable = false, length = 160)
    public String getPAYER_NAME() {
        return PAYER_NAME;
    }

    public void setPAYER_NAME(String val) {
        PAYER_NAME = val;
    }

    @Column(name = "PAYER_INN", nullable = false, length = 12)
    public String getPAYER_INN() {
        return PAYER_INN;
    }

    public void setPAYER_INN(String val) {
        PAYER_INN = val;
    }

    @Column(name = "PAYER_BANK_BIC", nullable = false, length = 9)
    public String getPAYER_BANK_BIC() {
        return PAYER_BANK_BIC;
    }

    public void setPAYER_BANK_BIC(String val) {
        PAYER_BANK_BIC = val;
    }

    @Column(name = "RECIP_NAME", nullable = false, length = 160)
    public String getRECIP_NAME() {
        return RECIP_NAME;
    }

    public void setRECIP_NAME(String val) {
        RECIP_NAME = val;
    }

    @Column(name = "RECIP_INN", nullable = false, length = 12)
    public String getRECIP_INN() {
        return RECIP_INN;
    }

    public void setRECIP_INN(String val) {
        RECIP_INN = val;
    }

    @Column(name = "CPURPPLATVO", nullable = false, length = 210)
    public String getCPURPPLATVO() {
        return CPURPPLATVO;
    }

    public void setCPURPPLATVO(String val) {
        CPURPPLATVO = val;
    }

    @Column(name = "CREATE_USR", nullable = false, length = 32)
    public String getCREATE_USR() {
        return CREATE_USR;
    }

    public void setCREATE_USR(String val) {
        CREATE_USR = val;
    }

    @Column(name = "AGREE_USR", length = 32)
    public String getAGREE_USR() {
        return AGREE_USR;
    }

    public void setAGREE_USR(String val) {
        AGREE_USR = val;
    }

    @Column(name = "CANCEL_AGREE_NUM", length = 0)
    public Long getCANCEL_AGREE_NUM() {
        return CANCEL_AGREE_NUM;
    }

    public void setCANCEL_AGREE_NUM(Long val) {
        CANCEL_AGREE_NUM = val;
    }

    @Column(name = "CANCEL_TRANSACTION_ID", length = 0)
    public Long getCANCEL_TRANSACTION_ID() {
        return CANCEL_TRANSACTION_ID;
    }

    public void setCANCEL_TRANSACTION_ID(Long val) {
        CANCEL_TRANSACTION_ID = val;
    }

    @Column(name = "CANCEL_REASON_CODE", length = 0)
    public Long getCANCEL_REASON_CODE() {
        return CANCEL_REASON_CODE;
    }

    public void setCANCEL_REASON_CODE(Long val) {
        CANCEL_REASON_CODE = val;
    }

    @Column(name = "CANCEL_REASON_MESSAGE", length = 160)
    public String getCANCEL_REASON_MESSAGE() {
        return CANCEL_REASON_MESSAGE;
    }

    public void setCANCEL_REASON_MESSAGE(String val) {
        CANCEL_REASON_MESSAGE = val;
    }

    @Column(name = "STATUS_CODE", nullable = false, length = 0)
    public Long getSTATUS_CODE() {
        return STATUS_CODE;
    }

    public void setSTATUS_CODE(Long val) {
        STATUS_CODE = val;
    }

    @Column(name = "STATUS_MESSAGE", length = 255)
    public String getSTATUS_MESSAGE() {
        return STATUS_MESSAGE;
    }

    public void setSTATUS_MESSAGE(String val) {
        STATUS_MESSAGE = val;
    }

    @Column(name = "STATUS_DATE", nullable = false)
    public LocalDateTime getSTATUS_DATE() {
        return STATUS_DATE;
    }

    public void setSTATUS_DATE(LocalDateTime val) {
        STATUS_DATE = val;
    }

    @Column(name = "REMOTE_TRANSACTION_ID", length = 0)
    public Long getREMOTE_TRANSACTION_ID() {
        return REMOTE_TRANSACTION_ID;
    }

    public void setREMOTE_TRANSACTION_ID(Long val) {
        REMOTE_TRANSACTION_ID = val;
    }

    @Column(name="MSUMMCANCEL",length = 10)
    public BigDecimal getMSUMMCANCEL() {
        return MSUMMCANCEL;
    }
    public void setMSUMMCANCEL(BigDecimal val) {
        MSUMMCANCEL = val;
    }
    @Column(name="MSUMMCANCELPLAN",length = 10)
    public BigDecimal getMSUMMCANCELPLAN() {
        return MSUMMCANCELPLAN;
    }
    public void setMSUMMCANCELPLAN(BigDecimal val) {
        MSUMMCANCELPLAN = val;
    }
    @Column(name="ICANCELPARTCOUNT",length = 0)
    public Long getICANCELPARTCOUNT() {
        return ICANCELPARTCOUNT;
    }
    public void setICANCELPARTCOUNT(Long val) {
        ICANCELPARTCOUNT = val;
    }

    @Transient
    @Override
    public String getMarkRowID() {
        return getROWID();
    }

    @Override
    public boolean isMark() {
        return super.isMark();
    }
}