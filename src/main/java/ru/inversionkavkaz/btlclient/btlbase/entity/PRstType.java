package ru.inversionkavkaz.btlclient.btlbase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;

/**
 * Режимы отображения информационной базы
 */
@Entity(
        name = "ru.inversionkavkaz.btlclient.btlbase.entity.PRstType"
)
@NamedNativeQuery(
        name = "ru.inversionkavkaz.btlclient.btlbase.entity.PRstType",
        query = "select '1' id, 'Рабочие' cname from dual "+
                "union all "+
                "select '2' id, 'Рабочие + Аннулированные' cname from dual "+
                "union all "+
                "select '3' id, 'Все' cname from dual"
)
public class PRstType {
    private Long ID;
    private String CNAME;

    public PRstType(){}

    @Id
    @Column(name="ID",nullable = false,length = 1)
    public Long getID() {
        return ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }

    @Column(name="CNAME",nullable = false,length = 64)
    public String getCNAME() {
        return CNAME;
    }
    public void setCNAME(String CNAME) {
        this.CNAME = CNAME;
    }
}
