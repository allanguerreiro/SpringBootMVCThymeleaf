package br.spring.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Created by allan on 05/12/16.
 */
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "employee")
public class Employee {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @NotNull
    @Getter @Setter
    private String name;

    @NotNull
    @Getter @Setter
    private String role;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    @Getter @Setter
    private User user;

    @Getter
    @Setter
    @Transient
    private Optional<User> userOptional = Optional.empty();
}
