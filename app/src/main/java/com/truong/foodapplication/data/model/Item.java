package com.truong.foodapplication.data.model;

public class Item {
    public Item(String foodName, String foodSize, int quatity, double foodprice) {
        this.foodName = foodName;
        this.foodSize = foodSize;
        this.quatity = quatity;
        this.foodprice = foodprice;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodSize() {
        return foodSize;
    }

    public void setFoodSize(String foodSize) {
        this.foodSize = foodSize;
    }

    public int getQuatity() {
        return quatity;
    }

    public void setQuatity(int quatity) {
        this.quatity = quatity;
    }

    public double getFoodprice() {
        return foodprice;
    }

    public void setFoodprice(double foodprice) {
        this.foodprice = foodprice;
    }

    @Override
    public String toString() {
        return "Item{" +
                "foodName='" + foodName + '\'' +
                ", foodSize='" + foodSize + '\'' +
                ", quatity=" + quatity +
                ", foodprice=" + foodprice +
                '}';
    }

    private String foodName;
    private String foodSize;
    private int quatity;
    private double foodprice;
}
