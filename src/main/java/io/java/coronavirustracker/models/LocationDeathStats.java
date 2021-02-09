package io.java.coronavirustracker.models;

public class LocationDeathStats {
    private String state;
    private String country;
    private int lastTotalCases;
    private int newCases;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLastTotalCases() {
        return lastTotalCases;
    }

    public void setLastTotalCases(int lastTotalCases) {
        this.lastTotalCases = lastTotalCases;
    }

    public int getNewCases() {
        return newCases;
    }

    public void setNewCases(int newCases) {
        this.newCases = newCases;
    }

    @Override
    public String toString() {
        return "LocationDeathStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", lastTotalCases=" + lastTotalCases +
                ", newCases=" + newCases +
                '}';
    }
}
