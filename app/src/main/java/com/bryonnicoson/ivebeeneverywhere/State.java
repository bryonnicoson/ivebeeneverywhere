package com.bryonnicoson.ivebeeneverywhere;

import java.io.Serializable;

public class State implements Serializable {
    private String abbreviation;
    private int area;
    private String capital;
    private String flagURL;
    private String mostPopulousCity;
    private String name;
    private int population;

    public State(){}

//    public State(String abbreviation, int area, String capital, String flagURL,
//                 String mostPopulousCity, String name, int population){
//        this.abbreviation = abbreviation;
//        this.area = area;
//        this.capital = capital;
//        this.flagURL = flagURL;
//        this.mostPopulousCity = mostPopulousCity;
//        this.name = name;
//        this.population = population;
//    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMostPopulousCity() {
        return mostPopulousCity;
    }

    public void setMostPopulousCity(String mostPopulousCity) {
        this.mostPopulousCity = mostPopulousCity;
    }

    public String getFlagURL() {
        return flagURL;
    }

    public void setFlagURL(String flagURL) {
        this.flagURL = flagURL;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }




}
