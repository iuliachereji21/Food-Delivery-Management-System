package org.ro.tuc.pt.business;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class will be used for storing the data of a menu item;
 * @author Chereji Iulia
 */
public class MenuItem implements Serializable {
    /** title of the product. It must be inherited. */
    protected String title;
    /** price of the product. It must be inherited. */
    protected int price;

    /**
     * Creates a new instance of MenuItem
     * @param title any non empty String
     * @param price greater or equal to 0
     */
    public MenuItem(String title, int price)
    {
        this.title=title;
        this.price=price;
    }

    /**
     * sets its own price correctly.
     */
    public void computePrice() { }

    /**
     * @return price of the product
     */
    public int getPrice() {
        return price;
    }

    /**
     * @return title of the product
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets the title
     * @param title any non empty String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * sets the price
     * @param price >=0
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * @param o object of type BaseProduct
     * @return true if o and current object have equal values, or false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem)) return false;
        MenuItem menuItem = (MenuItem) o;
        return price == menuItem.price && title.equals(menuItem.title);
    }
    /**
     * @return an integer representing the hashCode of the current object, using all its attributes
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, price);
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
