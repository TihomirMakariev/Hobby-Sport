package bg.tihomir.hobby.model.entity;

import bg.tihomir.hobby.model.enums.LocationEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "locations")
public class LocationEntity extends BaseEntity {
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private LocationEnum name;

    public LocationEntity(LocationEnum locationEnum) {
        this.name = locationEnum;
    }

    public LocationEntity() {
    }


    public LocationEnum getName() {
        return name;
    }

    public void setName(LocationEnum name) {
        this.name = name;
    }
}
