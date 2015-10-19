package tk.lenkyun.foodbook.foodbook.Domain.Data;

/**
 * Created by lenkyun on 16/10/2558.
 */
public class Location {
    private String name, coordinate;
    private Restaurant restaurant;

    public Location(String name, String coordinate){
        this(name, coordinate, null);
    }

    public Location(String name, String coordinate, Restaurant restaurant){
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

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
