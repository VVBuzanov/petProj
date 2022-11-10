package ru.buzanov.petproj.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @Column(name = "iclientid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "cfio")
    @Pattern(regexp = "[A-ZА-ЯЁ][a-zа-яё]+ [A-ZА-ЯЁ][a-zа-яё]+ [A-ZА-ЯЁ][a-zа-яё]+", message = "Верный формат: Фамилия Имя Отчетсво")
    @Size(min = 0, max = 100)
    private String fio;

    @Column(name = "dbday")
    @NotNull(message = "ДД.MM.ГГГГ")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date birthday;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JsonManagedReference
    private List<Account> accounts;

    public Client() {
    }

    public Client(int id, String fio, Date birthday) {
        this.id = id;
        this.fio = fio;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public String bday2String() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (this.birthday == null) {
            return "";
        }

        return dateFormat.format(this.birthday);
    }

    @Override
    public String toString() {
        return "Client{" +
                "Id=" + id +
                ", Fio='" + fio + '\'' +
                ", Birthday=" + birthday +
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
