package tk.lenkyun.foodbook.foodbook.Domain.Data;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by lenkyun on 16/10/2558.
 */
public class Location implements FoodbookType {
    private String name;
    private LatLng coordinate;
    private Restaurant restaurant;

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
