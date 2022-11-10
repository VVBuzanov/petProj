package ru.buzanov.petproj.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ledger")
public class Ledger {

    @Id
    @Column(name = "iledid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "iaccid")
    private int accId;

    @Column(name = "icredit")
    @Min(0)
    private BigDecimal sumCredit;

    @Column(name = "idebit")
    @Min(0)
    private BigDecimal sumDebit;

    @Column(name = "ddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranDate;

    public Ledger() {
    }

    public Ledger(int accId, BigDecimal sumCredit, BigDecimal sumDebit, Date tranDate) {
        this.accId = accId;
        this.sumCredit = sumCredit;
        this.sumDebit = sumDebit;
        this.tranDate = tranDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public BigDecimal getSumCredit() {
        return sumCredit;
    }

    public void setSumCredit(BigDecimal sumCredit) {
        this.sumCredit = sumCredit;
    }

    public BigDecimal getSumDebit() {
        return sumDebit;
    }

    public void setSumDebit(BigDecimal sumDebit) {
        this.sumDebit = sumDebit;
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    @Override
    public String toString() {
        return "Ledger{" +
                "id=" + id +
                ", accId=" + accId +
                ", sumCredi=" + sumCredit +
                ", sumDebit=" + sumDebit +
                ", tranDate=" + tranDate +
                '}';
    }

    public String toJson() {
        try {
            return new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
