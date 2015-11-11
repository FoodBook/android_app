package tk.lenkyun.foodbook.foodbook.Domain.Data;

/**
 * Created by lenkyun on 16/10/2558.
 */
public class Restaurant implements FoodbookType {
    private String name;

    public Restaurant(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
