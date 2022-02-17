package org.ro.tuc.pt.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 * This class will be used for storing the data of a composite product;
 * @author Chereji Iulia
 */
public class CompositeProduct extends MenuItem implements Serializable {
    private ArrayList<MenuItem> items;

    /**
     * Creates a new composite product, having no items
     * @param title the title of the product
     * @param price the price of the product
     */
    public CompositeProduct(String title, int price)
    {
        super(title, price);
        items=new ArrayList<>();
    }

    /**
     * @return the average value of the raitings of all the items that belong to the composite
     */
    public float getTotalRating()
    {
        float ret=0;
        Iterator<MenuItem> iterator= items.iterator();
        while (iterator.hasNext())
        {
            ret+=((BaseProduct)iterator.next()).getRating();
        }
        ret=ret/items.size();
        ret=Math.round(ret*1000f)/1000f;
        return ret;
    }

    /**
     * @return the sum of the calories of all the items that belong to the composite
     */
    public int getTotalCalories()
    {
        int ret=0;
        Iterator<MenuItem> iterator= items.iterator();
        while (iterator.hasNext())
        {
            ret+=((BaseProduct)iterator.next()).getCalories();
        }
        return ret;
    }

    /**
     * @return the sum of the proteins of all the items that belong to the composite
     */
    public int getTotalProtein()
    {
        int ret=0;
        Iterator<MenuItem> iterator= items.iterator();
        while (iterator.hasNext())
        {
            ret+=((BaseProduct)iterator.next()).getProtein();
        }
        return ret;
    }

    /**
     * @return the sum of the fat of all the items that belong to the composite
     */
    public int getTotalFat()
    {
        int ret=0;
        Iterator<MenuItem> iterator= items.iterator();
        while (iterator.hasNext())
        {
            ret+=((BaseProduct)iterator.next()).getFat();
        }
        return ret;
    }

    /**
     * @return the sum of the sodium of all the items that belong to the composite
     */
    public int getTotalSodium()
    {
        int ret=0;
        Iterator<MenuItem> iterator= items.iterator();
        while (iterator.hasNext())
        {
            ret+=((BaseProduct)iterator.next()).getSodium();
        }
        return ret;
    }

    public int getNrItems(){return items.size();}

    /**
     * Searches for the oldProduct in the list of items belonging to the current product, if found, it is replaced
     * by newProduct and the price of the composite is updated.
     */
    public void modifyProduct(BaseProduct oldProduct, BaseProduct newProduct)
    {
        Iterator<MenuItem> iterator = items.iterator();
        while (iterator.hasNext())
        {
            BaseProduct it =(BaseProduct) iterator.next();
            if(it.equals(oldProduct))
            {
                iterator.remove();
                break;
            }
        }
        if(newProduct!=null)
            items.add((MenuItem) newProduct);
        computePrice();
    }

    /**
     * Computes the sum of the prices of all the items belonging to the composite.
     */
    @Override
    public void computePrice() {
        int pr=0;
        Iterator<MenuItem> iterator = items.iterator();
        while(iterator.hasNext())
        {
            pr+=iterator.next().getPrice();
        }
        this.price=pr;
    }

    /**
     * Adds the item to the list of products that belong to the composite and updates the price.
     */
    public void addProduct(MenuItem item)
    {
        items.add(item);
        computePrice();
    }

    /**
     * @param o object of type CompositeProduct
     * @return true if o and current object have equal values, or false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompositeProduct)) return false;
        if (!super.equals(o)) return false;
        CompositeProduct that = (CompositeProduct) o;
        return items.equals(that.items);
    }

    /**
     * @return an integer representing the hashCode of the current object, using all its attributes
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), items);
    }

    @Override
    public String toString() {
        String str=""+title+": ";
        Iterator<MenuItem> iterator= items.iterator();
        while (iterator.hasNext())
        {
            str=str+iterator.next().getTitle()+"; ";
        }
        return str;
    }

    public CompositeProduct copy(){
        CompositeProduct newProduct= new CompositeProduct(this.title, this.price);
        newProduct.items.addAll(this.items);
        return newProduct;
    }
}
