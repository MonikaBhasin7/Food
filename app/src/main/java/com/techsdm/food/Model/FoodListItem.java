package com.techsdm.food.Model;

/**
 * Created by Monika Bhasin on 07-08-2018.
 */

public class FoodListItem {

    public String name;
    public String imageLink;

    public FoodListItem() {
    }

    public FoodListItem(String name, String imageLink) {
        this.name = name;
        this.imageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
