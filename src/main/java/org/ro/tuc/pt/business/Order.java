package org.ro.tuc.pt.business;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class will be used for storing the data of an order;
 * @author Chereji Iulia
 */
public class Order implements Serializable {
    private String orderID;
    private String clientID;
    private String dateAndTime;
    private int totalPrice;

    /**
     * @param orderID String of format "Onr"
     * @param clientID String of format "Cnr"
     * @param dateAndTime String of format "yyyy-mm-dd hh:mm:ss"
     * @param totalPrice greater than 0
     */
    public Order(String orderID, String clientID, String dateAndTime, int totalPrice) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.dateAndTime = dateAndTime;
        this.totalPrice = totalPrice;
    }

    public String toString()
    {
        return "Order "+orderID+", client id: "+clientID+", date and time: "+dateAndTime+", total price: "+totalPrice;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getClientID() {
        return clientID;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param o object of type BaseProduct
     * @return true if o and current object have equal values, or false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return totalPrice == order.totalPrice && orderID.equals(order.orderID) && clientID.equals(order.clientID) && dateAndTime.equals(order.dateAndTime);
    }

    /**
     * @return an integer representing the hashCode of the current object, using all its attributes
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderID, clientID, dateAndTime, totalPrice);
    }


}
