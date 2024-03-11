package com.truong.foodapplication.data.model;

import java.util.List;

public class Food {

    @Override
    public String toString() {
        return "Food{" +
                "foodName='" + foodName + '\'' +
                ", foodPrice=" + foodPrice +
                ", foodSize=" + foodSize +
                ", foodUrl=" + foodUrl +
                ", foodSmallUrl=" + foodSmallUrl +
                '}';
    }

    public Food(String foodName, List<String> foodPrice, List<String> foodSize, String foodUrl, String foodSmallUrl) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodSize = foodSize;
        this.foodUrl = foodUrl;
        this.foodSmallUrl = foodSmallUrl;
    }
    public Food(){

    }

    private String foodName;
    private List<String> foodPrice;
    private List<String> foodSize;

    public String getFoodSmallUrl() {
        return foodSmallUrl;
    }

    public void setFoodSmallUrl(String foodSmallUrl) {
        this.foodSmallUrl = foodSmallUrl;
    }

    private String foodSmallUrl;

    public String getFoodUrl() {
        return foodUrl;
    }

    public void setFoodUrl(String foodUrl) {
        this.foodUrl = foodUrl;
    }

    private String foodUrl;

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public List<String> getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(List<String> foodPrice) {
        this.foodPrice = foodPrice;
    }

    public List<String> getFoodSize() {
        return foodSize;
    }

    public void setFoodSize(List<String> foodSize) {
        this.foodSize = foodSize;
    }
}
