package bg.tihomir.hobby.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "entries")
public class Entry extends BaseEntity{

    @Column
    private String date;
    @ManyToOne(cascade = CascadeType.ALL)
    private Abo abo;
    private boolean isInProcess;

    public Entry() {
    }


    public String getDate() {
        return date;
    }

    public Entry setDate(String date) {
        this.date = date;
        return this;
    }

    public Abo getAbo() {
        return abo;
    }

    public Entry setAbo(Abo abo) {
        this.abo = abo;
        return this;
    }

    public boolean isInProcess() {
        return isInProcess;
    }

    public Entry setInProcess(boolean inProcess) {
        isInProcess = inProcess;
        return this;
    }
}
