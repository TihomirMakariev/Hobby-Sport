package bg.tihomir.hobby.model.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "abos")
public class Abo extends BaseEntity{

    @Column(nullable = false)
    private Long clientId;
    @Column(nullable = false)
    private Long businessOwnerId;
    @Column(nullable = false)
    private Long hobbyId;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Entry> entries = new ArrayList<>();
    @Column
    private BigDecimal aboPrice;
    @Column
    private String name;

    private String imgUrl;
    @Column(nullable = false)
    private String clientName;

    public Abo() {
    }

    public Long getClientId() {
        return clientId;
    }

    public Abo setClientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    public Long getBusinessOwnerId() {
        return businessOwnerId;
    }

    public Abo setBusinessOwnerId(Long businessOwnerId) {
        this.businessOwnerId = businessOwnerId;
        return this;
    }

    public Long getHobbyId() {
        return hobbyId;
    }

    public Abo setHobbyId(Long hobbyId) {
        this.hobbyId = hobbyId;
        return this;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public Abo setEntries(List<Entry> entries) {
        this.entries = entries;
        return this;
    }

    public BigDecimal getAboPrice() {
        return aboPrice;
    }

    public Abo setAboPrice(BigDecimal aboPrice) {
        this.aboPrice = aboPrice;
        return this;
    }

    public String getName() {
        return name;
    }

    public Abo setName(String name) {
        this.name = name;
        return this;
    }

    public String getClientName() {
        return clientName;
    }

    public Abo setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Abo setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, businessOwnerId, hobbyId, name, clientName, aboPrice);
    }
}
