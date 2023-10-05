package com.graalvm.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("""
    SELECT
    c.brand as brand,
    c.value as value
    FROM Car c
    """)
    List<CarProjection> all();

}
