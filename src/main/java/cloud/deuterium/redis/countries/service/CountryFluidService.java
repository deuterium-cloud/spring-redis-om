package cloud.deuterium.redis.countries.service;

import cloud.deuterium.redis.countries.model.Country;
import cloud.deuterium.redis.countries.model.Country$;
import com.redis.om.spring.search.stream.EntityStream;
import io.redisearch.aggregation.SortedField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.domain.geo.Metrics;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

/**
 * Created by Milan Stojkovic 04-Dec-2022
 */


@Slf4j
@RequiredArgsConstructor
@Service
public class CountryFluidService {

    private final EntityStream entityStream;

    public Country getById(String id) {
        return entityStream.of(Country.class)
                .filter(Country$.ID.eq(id))
                .findFirst()
                .orElse(new Country());
    }

    public Iterable<Country> findAll(String region, String continent) {

        if (isNull(region) && isNull(continent)) {
            return entityStream.of(Country.class)
                    .collect(Collectors.toList());
        }

        if (isNull(continent)) {
            return entityStream.of(Country.class)
                    .filter(Country$.REGION.eq(region))
                    .sorted(Country$.REGION, SortedField.SortOrder.DESC)
                    .collect(Collectors.toList());
        }

        if (isNull(region)) {
            return entityStream.of(Country.class)
                    .filter(Country$.CONTINENT.eq(continent))
                    .sorted(Country$.CONTINENT, SortedField.SortOrder.ASC)
                    .collect(Collectors.toList());
        }

        return entityStream.of(Country.class)
                .filter(Country$.CONTINENT.eq(continent))
                .filter(Country$.REGION.eq(region))
                .collect(Collectors.toList());
    }

    public Iterable<Country> findAllByPopulation(int min, int max) {
        return entityStream.of(Country.class)
                .filter(Country$.POPULATION.between(min, max))
                .collect(Collectors.toList());
    }

    public Iterable<Country> findByAverageTemperature(int min, int max) {
        return entityStream.of(Country.class)
                .filter(Country$.YEARLY_AVERAGE_TEMPERATURE.between(min, max))
                .collect(Collectors.toList());
    }

    public Iterable<Country> findByLocationNear(double lat, double lon, double distance) {
        return entityStream.of(Country.class)
                .filter(Country$.CAPITAL_GEOLOCATION.near(new Point(lat, lon), new Distance(distance, Metrics.KILOMETERS)))
                .collect(Collectors.toList());
    }

    public Iterable<Country> search(String q) {

        Collection<Country> byCapital = entityStream.of(Country.class)
                .filter(Country$.CAPITAL.like(q))
                .collect(Collectors.toList());

        Collection<Country> byName = entityStream.of(Country.class)
                .filter(Country$.NAME.like(q))
                .collect(Collectors.toList());

        HashSet<Country> countries = new HashSet<>(byName);
        countries.addAll(byCapital);
        return countries;
    }

    public Iterable<Country> searchByLanguages(Set<String> languages) {
        return entityStream.of(Country.class)
                .filter(Country$.LANGUAGES.in(languages.toArray(new String[0])))
                .collect(Collectors.toList());
    }


}
