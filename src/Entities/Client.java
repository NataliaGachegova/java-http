package Entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="clients")
public class Client implements JSONable {
    private Integer id;
    private String name;
    private String phoneNumber;
    private Set<Order> orders;

    public Client(){}
    public Client(Integer id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
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
        return String.format("{\"id\":%d,\"name\":\"%s\",\"phoneNumber\":\"%s\",\"orders\":%s}",
                    getId(),
                    getName(),
                    getPhoneNumber(),
                    orders
                );
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

    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name="phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
