package com.cine.config;

import com.cine.models.Sala;
import com.cine.repositories.SalaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatosIniciales {
    @Bean
    CommandLineRunner cargarSalas(SalaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                for (int numero = 1; numero <= 6; numero++) repository.save(new Sala(numero, 100));
            }
        };
    }
}
