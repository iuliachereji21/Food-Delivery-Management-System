package org.ro.tuc.pt.business;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class will be used for storing the data of a base product;
 * @author Chereji Iulia
 */
public class BaseProduct extends MenuItem implements Serializable {
    private float rating;
    private int calories;
    private int protein;
    private int fat;
    private int sodium;

    /**
     * Creates a new instance of BaseProduct.
     * @param title not null and not empty
     * @param rating greater than 0
     * @param calories greater than 0
     * @param protein greater than 0
     * @param fat greater than 0
     * @param sodium greater than 0
     * @param price greater than 0
     */
    public BaseProduct(String title, float rating, int calories, int protein, int fat, int sodium, int price)
    {
        super(title, price);
        this.rating=rating;
        this.calories=calories;
        this.protein=protein;
        this.fat=fat;
        this.sodium=sodium;
    }

    /**
     * @param o object of type BaseProduct
     * @return true if o and current object have equal values, or false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseProduct)) return false;
        if (!super.equals(o)) return false;
        BaseProduct that = (BaseProduct) o;
        return Float.compare(that.rating, rating) == 0 && calories == that.calories && protein == that.protein && fat == that.fat && sodium == that.sodium;
    }

    /**
     * @return an integer representing the hashCode of the current object, using all its attributes
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rating, calories, protein, fat, sodium);
    }

    @Override
    public String toString() {
        return title+", rating:" + rating +
                ", calories:" + calories +
                ", protein:" + protein +
                ", fat:" + fat +
                ", sodium:" + sodium +
                ", price:" + price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public String getTitle()
    {
        return super.getTitle();
    }

    public void setTitle(String title)
    {
        super.setTitle(title);
    }

    public int getPrice() {
        return super.getPrice();
    }

    public void setPrice(int price) {
        super.setPrice(price);
    }
}
