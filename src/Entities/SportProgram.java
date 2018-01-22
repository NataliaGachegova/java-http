package Entities;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "sportPrograms")
public class SportProgram implements JSONable{
    private Integer id;
    private String name;
    private Set<Training> trainings;

    public SportProgram(){}
    public SportProgram(Integer id, String name) {
        this.id = id;
        this.name = name;
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

        return String.format("{\"id\":%d,\"name\":\"%s\",\"trainings\":%s}",
                getId(),
                getName(),
                trainings
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

    @OneToMany(mappedBy = "program", fetch = FetchType.EAGER)
    public Set<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(Set<Training> trainings) {
        this.trainings = trainings;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
