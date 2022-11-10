package ru.buzanov.petproj.models;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "audit_table")
public class Audit {

    @Id
    @Column(name = "iaudid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "iidentity")
    private int entityId;

    @Column(name = "centity")
    private String entity;

    @Column(name = "caction")
    private String action;


    @Column(name = "cnewvalue")
    private String newValue;

    @Column(name = "ddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Audit() {
    }

    public Audit(int entityId, String entity, String action, String newValue) {
        this.entityId = entityId;
        this.entity = entity;
        this.action = action;
        this.newValue = newValue;
        this.date = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
}
