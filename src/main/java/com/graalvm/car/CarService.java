package com.graalvm.car;

import com.graalvm.car.dto.CarDTO;
import com.graalvm.car.dto.NestedDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private static final Logger log = LoggerFactory.getLogger(CarService.class);

    private final CarRepository carRepository;

    public List<CarProjection> all() {
        List<CarProjection> list = this.carRepository.all();

        list.forEach(c -> {
            assert c.getBrand() != null;
            assert c.getValue() != null;

            log.info("Car brand {} and value {}", c.getBrand(), c.getValue());
        });

        return list;
    }

    public void create (CarDTO dto) {
        for (NestedDTO nestedDTO : dto.nestedDTOS()) {
            this.carRepository.save(new Car(dto.brand(), nestedDTO.key(), nestedDTO.value()));
        }
    }

}
