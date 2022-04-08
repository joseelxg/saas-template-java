package com.hailas.common.core.domain;

/**
 * 城市实体
 * Created by wulin on 2017/6/13.
 */
public class Town {

    private int id;
    private int countyId;
    private String countyName;
    private int cityId;
    private String cityCname;
    private int provinceId;
    private String provinceCname;
    private int countryId;
    private String name;
    private String cname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCountyId() {
        return countyId;
    }

    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityCname() {
        return cityCname;
    }

    public void setCityCname(String cityCname) {
        this.cityCname = cityCname;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceCname() {
        return provinceCname;
    }

    public void setProvinceCname(String provinceCname) {
        this.provinceCname = provinceCname;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
