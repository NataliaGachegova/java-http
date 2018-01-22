package Entities;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "trainings")
public class Training implements JSONable{
    private Integer id;
    private String name;
    private SportProgram program;
    private Trainer trainer;
    private List<SeasonTicket> tickets;

    public Training(){}
    public Training(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toJSON(boolean deep) {
        return String.format("{\"id\":%d,\"name\":\"%s\",\"sportProgram\":%s,\"trainer\":%s}",
                getId(),
                getName(),
                deep ? getProgram().toJSON(false) : "null",
                deep ? getTrainer().toJSON(false) : "null"
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sportProgram_id")
    public SportProgram getProgram() {
        return program;
    }

    public void setProgram(SportProgram program) {
        this.program = program;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trainer_id")
    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    @OneToMany(mappedBy = "training")
    public List<SeasonTicket> getTickets() {
        return tickets;
    }

    public void setTickets(List<SeasonTicket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
