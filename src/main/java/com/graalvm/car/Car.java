package com.graalvm.car;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id", updatable = false, nullable = false, unique = true)
    private Long ID;
    private String brand;
    @Column(name = "car_key")
    private String key;
    private String value;

    public Car() { }

    public Car(String brand, String key, String value) {
        this.brand = brand;
        this.key = key;
        this.value = value;
    }

}
