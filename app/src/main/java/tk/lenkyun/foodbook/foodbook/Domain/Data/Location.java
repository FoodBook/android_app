package tk.lenkyun.foodbook.foodbook.Domain.Data;

/**
 * Created by lenkyun on 16/10/2558.
 */
public class Location implements FoodbookType {
    public static class LatLng{
        public double latitude, longitude;

        public LatLng(){}

        public LatLng(double latitude, double longitude){
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    private String name;
    private LatLng coordinate;
    private Restaurant restaurant;

    public Location(){}

    public Location(String name, LatLng coordinate){
        this(name, coordinate, null);
    }

    public Location(String name, LatLng coordinate, Restaurant restaurant){
        this.name = name;
        this.coordinate = coordinate;
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        // TODO : Implement real
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        // TODO : Implement real
        this.restaurant = restaurant;
    }

    public LatLng getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(LatLng coordinate) {
        this.coordinate = coordinate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
