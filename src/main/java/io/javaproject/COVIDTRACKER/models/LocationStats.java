package io.javaproject.COVIDTRACKER.models;

public class LocationStats {

    private String states;
    private String country;
    private int latestTotalCase;
    private int diffFromPrevDay;

    public int getDiffFromPrevDay() {

        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {

        this.states = states;
    }

    public String getCountry() {

        return country;
    }

    public void setCountry(String country) {

        this.country = country;
    }

    public int getLatestTotalCase() {

        return latestTotalCase;
    }

    public void setLatestTotalCase(int latestTotalCase) {

        this.latestTotalCase = latestTotalCase;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "states='" + states + '\'' +
                ", country='" + country + '\'' +
                ", latestTotalCase=" + latestTotalCase +
                '}';
    }


//    public void setState(String s) {
//    }
}
