package com.techsdm.food.Model;

/**
 * Created by Monika Bhasin on 07-08-2018.
 */

public class FoodItem {

    public String name;
    public String cost;
    public String description;

    public FoodItem() {
    }

    public FoodItem(String name, String cost, String description) {
        this.name = name;
        this.cost = cost;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
