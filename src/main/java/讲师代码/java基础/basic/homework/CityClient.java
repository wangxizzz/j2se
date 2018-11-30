/* CityClient.java
 * 
 * Copyright (c) 2014 Qunar.com. All Rights Reserved. */
package 讲师代码.java基础.basic.homework;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 实现CityClient里面TODO的方法（每个方法的含义，参数，返回值都在代码的注释里面了）
 * 
 * * CityClient is used to query information about a city. <br/>
 * * A city can be identified by either a cityId or a cityUrl, and both of them are overall unique. Every city belongs
 * to a single province, i.e. there is an one to many relationship between provinces and cites
 */
public class CityClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityClient.class);
    
    private static final City NULL_CITY = new City(-1, null, null);
    private static final Function<CityEntry, City> CITYENTRY_TO_CITY = 
            cityEntry -> new City(cityEntry.getCityId(), cityEntry.getCityUrl(), cityEntry.getCityName());
    private static final Function<CityEntry, Province> CITYENTRY_TO_PROVINCE = 
            cityEntry -> new Province(cityEntry.getProvinceId(), cityEntry.getProvinceName());
    
    @SuppressWarnings("unused") private Set<City> citys;
    private Set<Province> provinces;
    private Map<Integer, City> cityIdToCity;
    private Map<String, City> cityUrlToCity;
    private Map<Integer, Set<City>> provinceIdToCitys;
    private Map<Integer, Province> cityIdToProvince;
    private Map<Integer, Province> provinceIdToProvince;
    
    public static Builder create() {
        return new Builder();
    }

    private CityClient(Builder builder) {
        Set<CityEntry> cityEntries = builder.cityEntries;
        Map<Integer, Set<City>> provinceIdToCitys = cityEntries.stream().collect(
                Collectors.groupingBy(CityEntry::getProvinceId, Collectors.collectingAndThen(Collectors.toList(),
                        list -> list.stream().map(CITYENTRY_TO_CITY).collect(Collectors.toSet()))));
        Set<City> citys = provinceIdToCitys.values().stream().flatMap(Set::stream).collect(Collectors.toSet());
        Map<Integer, City> cityIdToCity = citys.stream()
                .collect(Collectors.groupingBy(City::getCityId,
                        Collectors.collectingAndThen(Collectors.toList(), list -> list.get(0))));
        Map<String, City> cityUrlToCity = citys.stream()
                .collect(Collectors.groupingBy(City::getCityUrl,
                        Collectors.collectingAndThen(Collectors.toList(), list -> list.get(0))));
        Map<Integer, Province> cityIdToProvince = cityEntries.stream()
                .collect(Collectors.toMap(CityEntry::getCityId, CITYENTRY_TO_PROVINCE));
        Set<Province> provinces = cityIdToProvince.values().stream().collect(Collectors.toSet());
        Map<Integer, Province> provinceIdToProvince = provinces.stream()
                .collect(Collectors.toMap(Province::getProvinceId, Function.identity()));
        this.citys = Collections.unmodifiableSet(citys);
        this.provinces = Collections.unmodifiableSet(provinces);
        this.cityIdToCity = Collections.unmodifiableMap(cityIdToCity);
        this.cityUrlToCity = Collections.unmodifiableMap(cityUrlToCity);
        this.provinceIdToCitys = Collections.unmodifiableMap(provinceIdToCitys);
        this.cityIdToProvince = Collections.unmodifiableMap(cityIdToProvince);
        this.provinceIdToProvince = Collections.unmodifiableMap(provinceIdToProvince);
    }

    /**
     * get all cities under an province identified by the given provinceId
     *
     * @param provinceId used to identify the target province
     * @return the set of cities in the target province, or empty city if the target province doesn't exist
     */
    public ImmutableSet<City> getCities(int provinceId) {
        return ImmutableSet.copyOf(Optional.ofNullable(provinceIdToCitys.get(provinceId)).orElse(Collections.emptySet()));
    }

    /**
     * get all provinces in the CityClient
     */
    public ImmutableSet<Province> getProvinces() {
        return ImmutableSet.copyOf(provinces);
    }

    /**
     * query for the province a city belonging to
     *
     * @param cityId the id used to identify the city
     * @return the province that contains the city, or null if the city doesn't exist
     */
    public Province getProvinceFor(int cityId) {
        return cityIdToProvince.get(cityId);
    }

    /**
     * find a province by its id
     *
     * @param provinceId the id used to query Province
     * @return the target province or null
     */
    public Province getProvince(int provinceId) {
        return provinceIdToProvince.get(provinceId);
    }

    /**
     * find a city by its id
     */
    public City getCity(int cityId) {
        return cityIdToCity.get(cityId);
    }

    /**
     * get corresponding cityUrl for a cityId
     *
     * @return the corresponding cityUrl or null if not exists
     */
    public String getCityUrlFor(int cityId) {
        return Optional.ofNullable(cityIdToCity.get(cityId)).orElse(NULL_CITY).getCityUrl();
    }

    /**
     * get corresponding cityId for a city
     *
     * @return the corresponding cityId or -1 if not exists
     */
    public int getCityIdFor(String cityUrl) {
        return Optional.ofNullable(cityUrlToCity.get(cityUrl)).orElse(NULL_CITY).getCityId();
    }

    public final static class Builder {

        private Set<CityEntry> cityEntries = new HashSet<>();
        
        private Builder() {
        }

        public Builder cityEntry(CityEntry cityEntry) {
            if (this.cityEntries.remove(cityEntry)) {
                LOGGER.warn("Duplicate cityEntry, will replace with newer one:{}", cityEntry);
            }
            this.cityEntries.add(cityEntry);
            return this;
        }

        public Builder cityEntries(Iterable<CityEntry> cityEntries) {
            for (CityEntry cityEntry : cityEntries) {
                cityEntry(cityEntry);
            }
            return this;
        }

        public CityClient build() {
            return new CityClient(this);
        }
    }

    public static class CityEntry {

        private int provinceId;

        private String provinceName;

        private int cityId;

        private String cityUrl;

        private String cityName;

        public int getProvinceId() {
            return provinceId;
        }

        public CityEntry setProvinceId(int provinceId) {
            this.provinceId = provinceId;
            return this;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public CityEntry setProvinceName(String provinceName) {
            this.provinceName = provinceName;
            return this;
        }

        public int getCityId() {
            return cityId;
        }

        public CityEntry setCityId(int cityId) {
            this.cityId = cityId;
            return this;
        }

        public String getCityUrl() {
            return cityUrl;
        }

        public CityEntry setCityUrl(String cityUrl) {
            this.cityUrl = cityUrl;
            return this;
        }

        public String getCityName() {
            return cityName;
        }

        public CityEntry setCityName(String cityName) {
            this.cityName = cityName;
            return this;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + cityId;
            result = prime * result + provinceId;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            CityEntry other = (CityEntry) obj;
            if (cityId != other.cityId)
                return false;
            if (provinceId != other.provinceId)
                return false;
            return true;
        }
        
    }

    public static class City {

        private final int cityId;

        private final String cityUrl;

        private final String name;

        public City(int cityId, String cityUrl, String name) {
            this.cityId = cityId;
            this.cityUrl = cityUrl;
            this.name = name;
        }

        public int getCityId() {
            return cityId;
        }

        public String getCityUrl() {
            return cityUrl;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof City))
                return false;

            City city = (City) o;

            if (cityId != city.cityId)
                return false;
            if (cityUrl != null ? !cityUrl.equals(city.cityUrl) : city.cityUrl != null)
                return false;
            if (name != null ? !name.equals(city.name) : city.name != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = cityId;
            result = 31 * result + (cityUrl != null ? cityUrl.hashCode() : 0);
            result = 31 * result + (name != null ? name.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).add("cityId", cityId).add("cityUrl", cityUrl).add("name", name)
                    .toString();
        }
    }

    public static class Province {

        private final int provinceId;

        private final String name;

        public Province(int provinceId, String name) {
            this.provinceId = provinceId;
            this.name = name;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof Province))
                return false;

            Province province = (Province) o;

            if (provinceId != province.provinceId)
                return false;
            if (name != null ? !name.equals(province.name) : province.name != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = provinceId;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).add("provinceId", provinceId).add("name", name).toString();
        }
    }

}
