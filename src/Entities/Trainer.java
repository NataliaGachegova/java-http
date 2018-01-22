package Entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "trainers")
public class Trainer implements JSONable {
    private Integer id;
    private String name;
    private int salary;
    private Set<Training> trainings;

    public Trainer(){}
    public Trainer(Integer id, String name, int salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public String toJSON(boolean deep) {
        String trainings = "null";
        if(deep) {
            try {
                trainings = JSONutils.getArray(getTrainings(), false);
            } catch (Exception e) {
                trainings = "[]";
            }
        }
        return String.format("{\"id\":%d,\"name\":\"%s\",\"salary\":%d,\"trainings\":%s}",
                getId(),
                getName(),
                getSalary(),
                trainings
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

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "salary")
    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @OneToMany(mappedBy = "trainer", fetch = FetchType.EAGER)
    public Set<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(Set<Training> trainings) {
        this.trainings = trainings;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
