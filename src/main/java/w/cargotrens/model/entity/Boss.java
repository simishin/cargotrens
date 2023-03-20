package w.cargotrens.model.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import static w.cargotrens.utilits.Loger.*;

@Entity
@Table(name = "boss_t")
public class Boss extends Person implements Party {
    @OneToMany(mappedBy = "boss", cascade = CascadeType.ALL)
    private Set<Dispatcher> listDispatcher;

    @OneToMany(mappedBy = "boss", cascade = CascadeType.ALL)
    private Set<Driver> listDriver;

    public Boss() {}

    public Boss(String name, String description) {
        super(name, description, 1);
        this.listDispatcher = new HashSet<Dispatcher>();
        this.listDriver = new HashSet<Driver>();
    }

    public Set<Dispatcher> getListDispatcher() {
        return listDispatcher;
    }
    public void setListDispatcher(Set<Dispatcher> listDispatcher) {
        this.listDispatcher = listDispatcher;
    }
    public Set<Driver> getListDriver() {
        return listDriver;
    }
    public void setListDriver(Set<Driver> listDriver) {
        this.listDriver = listDriver;
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Boss)) return false;
        return super.equals(obj);
    }
    public void merge(Boss x){
        assert prnv("+++");
        super.merge((Person)x); }
}
