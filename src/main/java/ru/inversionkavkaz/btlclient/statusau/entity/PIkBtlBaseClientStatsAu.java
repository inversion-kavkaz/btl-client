package ru.inversionkavkaz.btlclient.statusau.entity;

import java.math.BigDecimal;
import java.sql.*;
import java.time.*;
import java.io.Serializable;
import javax.persistence.*;
import ru.inversion.dataset.mark.*;
import ru.inversion.db.entity.ProxyFor;

/**
@author  porche
@since   2020/08/14 16:21:14
*/
@Entity (name="ru.inversionkavkaz.btlclient.statusau.entity.PIkBtlBaseClientStatsAu")
@Table (name="IK_BTL_BASE_CLIENT_STATS_AU")
public class PIkBtlBaseClientStatsAu implements Serializable
{
    private static final long serialVersionUID = 14_08_2020_16_21_14l;

    private Long ID;
    private Long ITRNNUM;
    private Long ICANCELPART;
    private LocalDateTime DATE_OP;
    private Long STATUS_CODE;
    private String STATUS_MESSAGE;
    private String USER_OP;

    public PIkBtlBaseClientStatsAu(){}

    @Id 
    @Column(name="ID",nullable = false,length = 0)
    public Long getID() {
        return ID;
    }
    public void setID(Long val) {
        ID = val; 
    }
    @Column(name="ITRNNUM",nullable = false,length = 0)
    public Long getITRNNUM() {
        return ITRNNUM;
    }
    public void setITRNNUM(Long val) {
        ITRNNUM = val; 
    }
    @Column(name="ICANCELPART",nullable = false,length = 0)
    public Long getICANCELPART() {
        return ICANCELPART;
    }
    public void setICANCELPART(Long val) {
        ICANCELPART = val; 
    }
    @Column(name="DATE_OP",nullable = false)
    public LocalDateTime getDATE_OP() {
        return DATE_OP;
    }
    public void setDATE_OP(LocalDateTime val) {
        DATE_OP = val; 
    }
    @Column(name="STATUS_CODE",nullable = false,length = 0)
    public Long getSTATUS_CODE() {
        return STATUS_CODE;
    }
    public void setSTATUS_CODE(Long val) {
        STATUS_CODE = val; 
    }
    @Column(name="STATUS_MESSAGE",length = 255)
    public String getSTATUS_MESSAGE() {
        return STATUS_MESSAGE;
    }
    public void setSTATUS_MESSAGE(String val) {
        STATUS_MESSAGE = val; 
    }
    @Column(name="USER_OP",nullable = false,length = 32)
    public String getUSER_OP() {
        return USER_OP;
    }
    public void setUSER_OP(String val) {
        USER_OP = val; 
    }
}