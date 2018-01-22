package Entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "seasonTickets")
public class SeasonTicket implements JSONable {
    private Integer id;
    private int price;
    private int mouth;
    private Training training;
    private Set<Order> orders;

    public SeasonTicket(){}
    public SeasonTicket(Integer id, int price, int mouth) {
        this.id = id;
        this.price = price;
        this.mouth = mouth;
    }

    public String toJSON(boolean deep) {
        String orders = "null";
        if(deep) {
            try {
                orders = JSONutils.getArray(getOrders(), false);
            } catch (Exception e) {
                orders = "[]";
            }
        }
        return String.format("{\"id\":%d,\"price\":%d,\"mouth\":%d,\"training\":%s,\"orders\":%s}",
                getId(),
                getPrice(),
                getMouth(),
                deep ? getTraining().toJSON(false) : "null",
                orders);
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Column(name = "mouth")
    public int getMouth() {
        return mouth;
    }

    public void setMouth(int mouth) {
        this.mouth = mouth;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "training_id")
    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    @OneToMany(mappedBy = "ticket", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
