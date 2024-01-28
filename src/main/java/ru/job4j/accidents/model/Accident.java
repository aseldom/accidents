package ru.job4j.accidents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "accidents")
public class Accident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private String text;
    private String address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    private AccidentType type;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "accidents_rules",
            joinColumns = { @JoinColumn(name = "accidents_id") },
            inverseJoinColumns = { @JoinColumn(name = "rules_id") }
    )
    private Set<Rule> rules = new HashSet<>();

}