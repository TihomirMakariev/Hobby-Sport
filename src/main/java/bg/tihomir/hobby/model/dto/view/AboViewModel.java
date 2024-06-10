package bg.tihomir.hobby.model.dto.view;

import java.math.BigDecimal;

public class AboViewModel {

    private Long id;
    private String clientName;
    private Long hobbyId;
    private String name;
    private BigDecimal aboPrice;
    private String imgUrl;

    public AboViewModel() {
    }

    public Long getId() {
        return id;
    }

    public AboViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getClientName() {
        return clientName;
    }

    public AboViewModel setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public Long getHobbyId() {
        return hobbyId;
    }

    public AboViewModel setHobbyId(Long hobbyId) {
        this.hobbyId = hobbyId;
        return this;
    }

    public String getName() {
        return name;
    }

    public AboViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getAboPrice() {
        return aboPrice;
    }

    public AboViewModel setAboPrice(BigDecimal aboPrice) {
        this.aboPrice = aboPrice;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public AboViewModel setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }
}
