package com.graalvm.car;

import com.graalvm.car.dto.CarDTO;
import com.graalvm.car.dto.NestedDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> all() {
        return this.carRepository.findAll();
    }

    public void create (CarDTO dto) {
        for (NestedDTO nestedDTO : dto.nestedDTOS()) {
            this.carRepository.save(new Car(dto.brand(), nestedDTO.key(), nestedDTO.value()));
        }
    }

}
