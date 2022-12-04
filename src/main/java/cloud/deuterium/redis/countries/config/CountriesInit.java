package cloud.deuterium.redis.countries.config;

import cloud.deuterium.redis.countries.model.Country;
import cloud.deuterium.redis.countries.repository.CountryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by Milan Stojkovic 12-Nov-2022
 * https://github.com/bsbodden/bootiful-redis-stack
 * https://github.com/bsbodden/redis-om-spring
 * https://redis.io/docs/stack/get-started/tutorials/stack-spring/
 * <p>
 * https://www.youtube.com/watch?v=kOGHsbdQ1yg
 * https://www.youtube.com/watch?v=rjaR1PR5gVk
 * https://www.youtube.com/watch?v=r9UhiaB7hSo
 */

@Configuration
public class CountriesInit {

    @Bean
    CommandLineRunner runner(CountryRepository countryRepository) {
        return args -> {

            countryRepository.deleteAll();

            ObjectMapper objectMapper = new ObjectMapper();

            InputStream is = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream("data/countries.json");

            Country[] countries = objectMapper.readValue(is, Country[].class); //new TypeReference<List<Country>>(){}

            countryRepository.saveAll(Arrays.asList(countries));
        };
    }
}
