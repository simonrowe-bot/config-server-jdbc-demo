package io.pivotal.configserverjdbcdemo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Data
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotNull
    private String propertyKey;

    @Column(length=500)
    private String propertyValue;

    private String application;

    private String profile;

    private String label;

}
