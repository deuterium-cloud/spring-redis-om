package cloud.deuterium.redis.countries.model;

import cloud.deuterium.redis.countries.config.PointDeserializer;
import cloud.deuterium.redis.countries.config.PointSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.Searchable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;

import java.util.Objects;
import java.util.Set;

/**
 * Created by Milan Stojkovic 13-Nov-2022
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Country {

    @Id
    @Indexed
    String id;

    @Searchable
    String name;

    String nativeName;

    @Searchable
    String capital;

    @Indexed
    String region;
    @Indexed
    String continent;
    @Indexed
    String abbreviation;
    String currency;
    String currencyCode;
    int callingCode;
    @Indexed
    Set<String> languages;
    @Indexed
    int population;
    @Indexed
    int yearlyAverageTemperature;
    String flag;
    String emoji;
    String emojiU;
    @Indexed
    @JsonSerialize(using = PointSerializer.class)
    @JsonDeserialize(using = PointDeserializer.class)
    Point capitalGeolocation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        return Objects.equals(id, country.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
