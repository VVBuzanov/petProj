package ru.buzanov.petproj.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @Column(name = "iaccid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iclientid", referencedColumnName = "iclientid")
    @JsonBackReference
    private Client client;

    @Column(name = "ccur")
    @NotNull
    @Pattern(regexp = "[A-Z]{3}", message = "Валюта должна быть в формате: ZZZ")
    private String cur;

    @Column(name = "caccnumber")
    @Pattern(regexp = "\\d{20}", message = "Счет должен состоять из 20 цифр")
    private String accNumber;

    @Column(name = "dopen")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date dateOpen;

    @Column(name = "dclose")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date dateClose;

    @Transient
    private BigDecimal balance;

    public Account() {
    }

    public Account(int clientId, String accNumber, Date dateOpen) {
        this.accNumber = accNumber;
        this.dateOpen = dateOpen;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public Date getDateOpen() {
        return dateOpen;
    }

    public void setDateOpen(Date dateOpen) {
        this.dateOpen = dateOpen;
    }

    public Date getDateClose() {
        return dateClose;
    }

    public void setDateClose(Date dateClose) {
        this.dateClose = dateClose;
    }

    public String getCur() {
        return cur;
    }

    public void setCur(String cur) {
        this.cur = cur;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String dOpen2String() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (this.dateOpen == null) {
            return "";
        }
        return dateFormat.format(this.dateOpen);
    }

    public String dClose2String() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (this.dateClose == null) {
            return "";
        }

        return dateFormat.format(this.dateClose);
    }

    @Override
    public String toString() {
        return "Account{" +
                "Id=" + Id +
                "ClientId" + (client != null ? client.getId() : "") +
                "Cur=" + cur +
                ", accNumber='" + accNumber + '\'' +
                ", dateOpen=" + dateOpen +
                ", dateClose=" + dateClose +
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
