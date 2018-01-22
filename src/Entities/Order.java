package Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order implements JSONable{
    private Integer id;
    private boolean isPaid;
    private Client client;
    private SeasonTicket ticket;

    public Order(){}
    public Order(Integer id, boolean isPaid) {
        this.id = id;
        this.isPaid = isPaid;
    }

    public String toJSON(boolean deep) {
        return String.format("{\"id\":%d,\"isPaid\":%s,\"client\":%s,\"seasonTicket\":%s}",
                getId(),
                isPaid() ? "true" : "false",
                deep ? getClient().toJSON(false) : "null",
                deep ? getTicket().toJSON(false) : "null"
                );
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "paid")
    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticket_id")
    public SeasonTicket getTicket() {
        return ticket;
    }

    public void setTicket(SeasonTicket ticket) {
        this.ticket = ticket;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
