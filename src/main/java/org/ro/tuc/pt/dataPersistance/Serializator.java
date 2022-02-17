package org.ro.tuc.pt.dataPersistance;

import org.ro.tuc.pt.business.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Class used to serialize and deserialize the data of the application from files (Menu items, Orders, Users)
 * @author Chereji Iulia
 */
public class Serializator {

    /**
     * Writes the products to the file "products.txt" using serialization.
     * @param products to be written.
     */
    public static void saveProductsToFile(HashSet<MenuItem> products)
    {
        try{
            File file1=new File("products.txt");
            FileOutputStream fileOutputStream1=new FileOutputStream(file1);
            ObjectOutputStream objectOutputStream1=new ObjectOutputStream(fileOutputStream1);
            Iterator<MenuItem> iterator1= products.iterator();
            while (iterator1.hasNext()) {
                objectOutputStream1.writeObject((MenuItem)iterator1.next());
            }
            fileOutputStream1.close();
            objectOutputStream1.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * @return the set of products read from the file "products.txt" using deserialization
     */
    public static HashSet<MenuItem> readProductsFromFile()
    {
        HashSet<MenuItem> toRet= new HashSet<>();
        try{
            File productsFile1= new File("products.txt");
            FileInputStream fileInputStream1= new FileInputStream(productsFile1);
            ObjectInputStream objectInputStream1 = new ObjectInputStream(fileInputStream1);
            MenuItem menuItem;
            while((menuItem=(MenuItem) objectInputStream1.readObject())!=null)
                toRet.add(menuItem);
            fileInputStream1.close();
            objectInputStream1.close();
        }
        catch(EOFException ex1){
            //all products were read
            return toRet;
        }
        catch (Exception e){e.printStackTrace(); return null;}
        return toRet;
    }

    /**
     * Writes the orders to the file "orders.txt" using serialization.
     * @param orders to be written.
     */
    public static void saveOrdersToFile(HashMap<Order, ArrayList<MenuItem>> orders)
    {
        try{
            File file1=new File("orders.txt");
            FileOutputStream fileOutputStream1=new FileOutputStream(file1);
            ObjectOutputStream objectOutputStream1=new ObjectOutputStream(fileOutputStream1);
            objectOutputStream1.writeObject(orders);
            objectOutputStream1.close();
            fileOutputStream1.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * @return the set of orders read from the file "orders.txt" using deserialization
     */
    public static HashMap<Order, ArrayList<MenuItem>> readOrdersFromFile()
    {
        HashMap<Order, ArrayList<MenuItem>> toRet= null;
        try
        {
            File productsFile2= new File("orders.txt");
            FileInputStream fileInputStream2= new FileInputStream(productsFile2);
            ObjectInputStream objectInputStream2 = new ObjectInputStream(fileInputStream2);


            toRet=(HashMap)objectInputStream2.readObject();

            fileInputStream2.close();
            objectInputStream2.close();
        }
        catch(EOFException ex2) {
            //all was read
            return toRet;
        }
        catch (Exception e){e.printStackTrace(); return null;}
        return toRet;
    }

    /**
     * Writes the users to the file "users.txt" using serialization.
     * @param users to be written
     */
    public static void saveUsersToFile(HashSet<User> users)
    {
        try{
            File file=new File("users.txt");
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
            Iterator<User> iterator= users.iterator();
            while (iterator.hasNext())
            {
                objectOutputStream.writeObject(iterator.next());
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * @return the set of users read from the file "users.txt" using deserialization
     */
    public static HashSet<User> readUsersFromFile()
    {
        HashSet<User> toRet=new HashSet<>();
        try{
            File usersFile= new File("users.txt");
            FileInputStream fileInputStream= new FileInputStream(usersFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            User user;
            while((user=(User) objectInputStream.readObject())!=null) {
                toRet.add(user);
            }
        }
        catch(EOFException ex){
            //all objects were read
            return toRet;
        }
        catch (Exception e){e.printStackTrace();}
        return toRet;
    }
}
