package org.ro.tuc.pt.business;

import java.util.HashSet;



/**
 *Interface determins the methods which should be implemented in order for the application to meet all the specifications.
 */
public interface IDeliveryServiceProcessing {

    /**
     * Generates the report requested by the administrator in a .txt file. It also checks for the input to be correct.
     * @param time1 in the format "hh:mm"
     * @param time2 in the format "hh:mm"
     * @param times integer greater than 0
     * @param nrOrders integer greater than 0
     * @param value integer greater than 0
     * @param date in the format "yyyy-mm-dd"
     * @param users the set of users from the application
     * @return 0 if the report was generated and -1 if the input was incorrect
     * @pre users!=null
     * @post (time1==null || time1.isEmpty()) && (time2==null || time2.isEmpty()) && (times==null || times.isEmpty()) && (nrOrders==null || nrOrders.isEmpty()) && (value==null || value.isEmpty()) && (date==null || date.isEmpty()) => @result==-1
     */
    int generateReport(String time1, String time2, String times, String nrOrders, String value, String date, HashSet<User> users);

    /**
     * Reads the base products from the file "products.csv" and deletes all existent composite products.
     * @pre true
     * @post cart.isEmpty() && totalPrice==0 && menuItemsSearched.isEmpty()
     * @post @forall k:[0 .. menuItems.getSize()-1] @
     *          !(menuItems.getElement(k) instanceof CompositeProduct)
     */
    void importProducts();

    /**
     * Searches for the products using the filters introduced by the user.
     * @param title any String
     * @param rating greater than 0
     * @param calories greater than 0
     * @param protein greater than 0
     * @param fat greater than 0
     * @param sodium greater than 0
     * @param price greater than 0
     * @return -1 if the searched was performed, 0 if the input was incorrect and 1 if no filter was given by the user
     * @pre true
     * @post (title==null || title.isEmpty()) && (rating==null || rating.isEmpty()) && (calories==null || calories.isEmpty()) && (protein==null || protein.isEmpty()) && (fat==null || fat.isEmpty()) && (sodium==null || sodium.isEmpty()) && (price==null || price.isEmpty())
     *      => (menuItemsSearched @pre == menuItemsSearched && @result==1)
     * @post @result==0 => menuItemsSearched @pre == menuItemsSearched
     */
    int searchProducts(String title, String rating, String calories, String protein, String fat, String sodium, String price);

    /**
     * Creates a new order and adds it to the set of orders.
     * Creates a bill in a .txt file.
     * Notifies the employees that a new order was placed.
     * @param user the user that placed the order.
     * @pre user!=null
     * @post cart.isEmpty() && totalPrice==0
     */
    void placeOrder(User user);

    /**
     * Creates a new base product and adds it to the products set if the input is correct.
     * @param title any String
     * @param rating greater than 0
     * @param calories greater than 0
     * @param protein greater than 0
     * @param fat greater than 0
     * @param sodium greater than 0
     * @param price greater than 0
     * @return -1 if the product was successfully added, 0 if the title was empty, 1 if the rating couldn't be converted into a float,
     * 2 if the calories couldn't be converted into an integer, 3 if the protein couldn't be converted into an integer,
     * 4 if the fat couldn't be converted into an integer, 5 if the sodium couldn't be converted into an integer,
     * 6 if the price couldn't be converted into an integer
     * @pre true
     * @post @result == -1 <=> menuItems.contains(new BaseProduct(title, rating, calories, fat,protein, sodium, price)
     */
    int addBaseProduct(String title, String rating, String calories, String protein, String fat, String sodium, String price);

    /**
     * Modifies the values of the oldProduct to the ones given as parameters if they are correct, and also modifies
     * all composite products containing it.
     * @param oldProduct product to be modified
     * @param title any String
     * @param rating greater than 0
     * @param calories greater than 0
     * @param protein greater than 0
     * @param fat greater than 0
     * @param sodium greater than 0
     * @param price greater than 0
     * @return -1 if the product was successfully updated, 0 if the title was empty, 1 if the rating couldn't be converted into a float,
     * 2 if the calories couldn't be converted into an integer, 3 if the protein couldn't be converted into an integer,
     * 4 if the fat couldn't be converted into an integer, 5 if the sodium couldn't be converted into an integer,
     * 6 if the price couldn't be converted into an integer
     * @pre oldProduct!=null
     * @post @result==-1 => menuItems.contains(new BaseProduct(title, rating, calories, protein, fat, sodium, price))
     */
    int updateBaseProduct(BaseProduct oldProduct, String title, String rating, String calories, String protein, String fat, String sodium, String price);

    /**
     * Deletes the base product from the set of products and updates every composite product containing it.
     * @param baseProduct the product to be deleted
     * @pre baseProduct!=null
     * @post !menuItems.contains(baseProduct)
     */
    void deleteBaseProduct(BaseProduct baseProduct);

    /**
     * Adds a new composite prduct to the set of products.
     * @param compositeProduct the product to be added. It was previously created and added a new base product each time the
     * administrator performed the operation, prior to requesting the save of the resulted composite.
     * @pre compositeProduct!=null
     * @post menuItems.contains(compositeProduct)
     */
    void addCompositeProduct(CompositeProduct compositeProduct);

    /**
     * Updates a composite product, modifying its name
     * @param oldProduct the product to be updated
     * @param title any String
     * @return -1 if the product was successfully updated, 0 if the title is empty
     * @pre oldProduct!=null
     * @post @result==-1 <=> menuItems.contains(new CompositeProduct(title, oldProduct))
     */
    int updateCompositeProduct(CompositeProduct oldProduct, String title);

    /**
     * Removes the product from the products set.
     * @param compositeProduct the product to be deleted.
     * @pre compositeProduct!=null
     * @post !menuItems.contains(compositeProduct)
     */
    void deleteCompositeProduct(CompositeProduct compositeProduct);

}
