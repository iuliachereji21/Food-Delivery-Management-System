package org.ro.tuc.pt.business;

import org.ro.tuc.pt.dataPersistance.Serializator;
import java.io.*;
import java.nio.file.Files;
import java.util.Observable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class is used to manage the application functionality for managing products and orders.
 * It extends Observable (to notify the employees when new orders are placed), and implements IDeliveryServiceProcessing.
 * @author Chereji Iulia
 * @invariant wellFormed()
 */
public class DeliveryService extends Observable implements IDeliveryServiceProcessing {
    private HashSet<MenuItem> menuItems;
    private HashMap<Order,ArrayList<MenuItem>> orders;
    private ArrayList<MenuItem> cart;
    private HashSet<MenuItem> menuItemsSearched;

    private int totalPrice;
    private String nextIDOrder;
    private int nextReport;

    private boolean wellFormed(){
        if(menuItems==null || orders==null || cart==null || menuItemsSearched==null) return false;
        if(totalPrice<0 || nextReport<1 || Integer.parseInt(nextIDOrder.substring(1))<1) return false;

        int price=0;
        for(MenuItem menuItem: cart){
            price+=menuItem.getPrice();
        }
        if(totalPrice!=price) return false;

        for(Map.Entry<Order, ArrayList<MenuItem>> entry: orders.entrySet()){
            int priceItems=0;
            for(MenuItem menuItem1: entry.getValue()){
                priceItems+=menuItem1.getPrice();
            }
            if(priceItems!=entry.getKey().getTotalPrice()) return false;
        }
        return true;
    }


    /**
     * The constructor initalizes the set of products and the set of orders.
     * The wellFormed() method must return true
     */
    public DeliveryService() {
        menuItems=new HashSet<>(); menuItems=Serializator.readProductsFromFile(); if(menuItems==null) menuItems=new HashSet<>();
        menuItemsSearched=new HashSet<>();
        orders=new HashMap<>(); orders=Serializator.readOrdersFromFile(); if(orders==null) orders=new HashMap<>();
        nextReport=1;
        cart=new ArrayList<>();
        totalPrice=0;
        int maxID=0;
        if(orders!=null){
            Iterator iterator= orders.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry pair=(Map.Entry)iterator.next();
                int id =Integer.parseInt(((Order)pair.getKey()).getOrderID().substring(1));
                if (id>maxID) maxID=id;
            }
        }
        nextIDOrder="O"+(maxID+1); System.out.println(nextIDOrder);
        assert wellFormed();
    }

    @Override
    public int generateReport(String time1, String time2, String times, String nrOrders, String value, String date, HashSet<User> users){
        assert wellFormed();
        assert users!=null;
        if(time1!=null && !time1.isEmpty()){
            if(time2!=null && !time2.isEmpty()) {
                try {
                    java.sql.Time time01 = java.sql.Time.valueOf(time1 + ":00");
                    java.sql.Time time02 = java.sql.Time.valueOf(time2 + ":00");
                    int i = Integer.parseInt(time1.substring(0, 2));
                    int j = Integer.parseInt(time2.substring(0, 2));
                    int x = Integer.parseInt(time1.substring(3, 5));
                    int y = Integer.parseInt(time2.substring(3, 5));
                    if (i < 0 || i > 23 || j < 0 || j > 23 || i > j || (i == j && y <= x) || x < 0 || x > 59 || y < 0 || y > 59)
                        return -1;
                    generateTimeIntervalReport(time1, time2);
                    return 0;
                } catch (Exception e) {
                    return -1;
                }
            }else return -1;
        }
        else if(time2!=null && !time2.isEmpty()) return -1;

        if(times!=null && !times.isEmpty()){
            try{
                int nr=Integer.parseInt(times);
                if(nr<0) return -1;
                generateReportProductsOrdered(nr);
                return 0;
            }catch (Exception e){return -1;}
        }
        if(nrOrders!=null && !nrOrders.isEmpty()){
            if(value!=null && !value.isEmpty()) {
                try {
                    int nr = Integer.parseInt(nrOrders);
                    int nr2 = Integer.parseInt(value);
                    if (nr < 0 || nr2 < 0) return -1;
                    generateReportClients(nr, nr2, users);
                    return 0;
                } catch (Exception e) {
                    return -1;
                }
            }else return -1;
        }
        else if(value!=null && !value.isEmpty()) return -1;
        if(date!=null && !date.isEmpty()){
            if(date.length()!=10) return -1; char[] chars2 = date.toCharArray(); if (chars2[4]!='-' || chars2[7]!='-') return -1;
            try {
                int year=Integer.parseInt(date.substring(0,4)); int month=Integer.parseInt(date.substring(5,7)); int day=Integer.parseInt(date.substring(8,10));
                if(year>2021 || year<1920 || month>12 || month<1 || day>31 || day<1) return -1;
                generateReportProductsOnDate(date);
                return 0;
            } catch (NumberFormatException e) { return -1; }
        }

        assert (time1==null || time1.isEmpty()) && (time2==null || time2.isEmpty()) && (times==null || times.isEmpty()) && (nrOrders==null || nrOrders.isEmpty()) && (value==null || value.isEmpty()) && (date==null || date.isEmpty());
        assert wellFormed();
        return -1; //no input
    }

    @Override
    public void importProducts(){
        assert wellFormed();
        menuItems=new HashSet<>();
        menuItemsSearched=new HashSet<>();
        clearCart();
        try {
            File productsFile = new File("products.csv");
            List<String[]> lines=Files.lines(productsFile.toPath()).map(l->l.split(",")).collect(Collectors.toList());
            for(int i=1;i< lines.size();i++){
                String[] values= lines.get(i);
                BaseProduct baseProduct= new BaseProduct(values[0], Float.parseFloat(values[1]), Integer.parseInt(values[2]),Integer.parseInt(values[3]),Integer.parseInt(values[4]),Integer.parseInt(values[5]),Integer.parseInt(values[6]));
                menuItems.add(baseProduct);
            }
        }
        catch(Exception e) {e.printStackTrace();}
        assert cart.isEmpty() && totalPrice==0 && menuItemsSearched.isEmpty();
        for(MenuItem menuItem: menuItems){
            if(menuItem instanceof CompositeProduct) assert false;
        }
        assert wellFormed();
    }

    @Override
    public int searchProducts(String title, String rating, String calories, String protein, String fat, String sodium, String price) {
        assert wellFormed();
        HashSet<MenuItem> menuItemsSearchedPre= new HashSet<>(menuItemsSearched);
        if((title==null || title.isEmpty()) && (rating==null || rating.isEmpty()) && (calories==null || calories.isEmpty()) && (protein==null || protein.isEmpty()) && (fat==null || fat.isEmpty()) && (sodium==null || sodium.isEmpty()) && (price==null || price.isEmpty()))
        {
            assert menuItemsSearchedPre.containsAll(menuItemsSearched) && menuItemsSearched.containsAll(menuItemsSearchedPre);
            return 1;
        }
        int cal=-1, prot=-1, fa=-1, sod=-1, pri=-1; float rat=-1;
        try{
            if(rating!=null && !rating.isEmpty()) rat=Float.parseFloat(rating);
            if(calories!=null && !calories.isEmpty()) cal=Integer.parseInt(calories);
            if(protein!=null && !protein.isEmpty()) prot=Integer.parseInt(protein);
            if(fat!=null && !fat.isEmpty()) fa=Integer.parseInt(fat);
            if(sodium!=null && !sodium.isEmpty()) sod=Integer.parseInt(sodium);
            if(price!=null && !price.isEmpty()) pri=Integer.parseInt(price);
        }
        catch (NumberFormatException e){
            assert menuItemsSearchedPre.containsAll(menuItemsSearched) && menuItemsSearched.containsAll(menuItemsSearchedPre);
            return 0;
        }
        List<MenuItem> menuItemsAfterFilter=new ArrayList<MenuItem>(getMenuItemsBase());
        List<MenuItem> menuItemsAfterFilterComposite=new ArrayList<MenuItem>(getMenuItemsComposite());

        float finalRat = rat; int finalCal = cal, finalProt = prot, finalFa = fa, finalSod = sod, finalPri = pri;
        Stream<BaseProduct> stream = menuItemsAfterFilter.stream().map(x -> (BaseProduct)x);
        Stream<CompositeProduct> stream2 = menuItemsAfterFilterComposite.stream().map(x -> (CompositeProduct) x);

        if(title!=null && !title.isEmpty()) {
            stream = stream.filter(x -> x.getTitle().toLowerCase(Locale.ROOT).contains(title.toLowerCase(Locale.ROOT)));
            stream2=stream2.filter(x->x.getTitle().toLowerCase(Locale.ROOT).contains(title.toLowerCase(Locale.ROOT)));
        }
        if(rat!=-1) {
            stream=stream.filter(x->x.getRating()== finalRat);
            stream2=stream2.filter(x->x.getTotalRating()== finalRat);
        }
        if(cal!=-1) {
            stream=stream.filter(x->x.getCalories()== finalCal);
            stream2=stream2.filter(x->x.getTotalCalories()== finalCal);
        }
        if(prot!=-1){
            stream=stream.filter(x->x.getProtein()== finalProt);
            stream2=stream2.filter(x->x.getTotalProtein()== finalProt);
        }
        if(fa!=-1) {
            stream=stream.filter(x->x.getFat()== finalFa);
            stream2=stream2.filter(x->x.getTotalFat()== finalFa);
        }
        if(sod!=-1) {
            stream=stream.filter(x->x.getSodium()== finalSod);
            stream2=stream2.filter(x->x.getTotalSodium()== finalSod);
        }
        if(pri!=-1) {
            stream=stream.filter(x->x.getPrice()== finalPri);
            stream2=stream2.filter(x->x.getPrice()== finalPri);
        }

        menuItemsSearched=new HashSet<>();
        menuItemsAfterFilter=stream.collect(Collectors.toList());
        menuItemsSearched.addAll(menuItemsAfterFilter);
        menuItemsAfterFilterComposite=stream2.collect(Collectors.toList());
        menuItemsSearched.addAll(menuItemsAfterFilterComposite);

        assert wellFormed();
        return -1;
    }

    @Override
    public void placeOrder(User user) {
        assert wellFormed();
        assert user!=null;
        if(!cart.isEmpty()){
            long millis= System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            java.sql.Time time = new java.sql.Time(millis);
            String dateAndTime= date + " " + time;
            Order order=new Order(nextIDOrder, user.getId(), dateAndTime, totalPrice);
            orders.put(order,cart);
            notifyEmplyees(order);
            createBill(user, dateAndTime);
            nextIDOrder="O"+(Integer.parseInt(nextIDOrder.substring(1))+1);
            cart=new ArrayList<>();
            totalPrice=0;
        }
        assert cart.isEmpty() && totalPrice==0;
        assert wellFormed();
    }

    @Override
    public int addBaseProduct(String title, String rating, String calories, String protein, String fat, String sodium, String price) {
        assert wellFormed();
        int ret;
        if((ret=verifyDataBaseProduct(title, rating, calories, protein, fat, sodium, price))!=-1) return ret;
        BaseProduct baseProduct=new BaseProduct(title, Float.parseFloat(rating),Integer.parseInt(calories),Integer.parseInt(protein),Integer.parseInt(fat),Integer.parseInt(sodium),Integer.parseInt(price));
        menuItems.add(baseProduct);
        assert menuItems.contains(baseProduct);
        assert wellFormed();
        return -1;
    }

    @Override
    public int updateBaseProduct(BaseProduct oldProduct, String title, String rating, String calories, String protein, String fat, String sodium, String price) {
        assert wellFormed();
        assert oldProduct!=null;
        int ret;
        if((ret=verifyDataBaseProduct(title, rating, calories, protein, fat, sodium, price))!=-1) return ret;
        BaseProduct product=new BaseProduct(title, Float.parseFloat(rating),Integer.parseInt(calories),Integer.parseInt(protein),Integer.parseInt(fat),Integer.parseInt(sodium),Integer.parseInt(price));
        menuItems.remove(oldProduct);
        menuItems.add(product);
        modifyComposite(oldProduct,product);
        assert menuItems.contains(product);
        assert wellFormed();
        return -1;
    }

    @Override
    public void deleteBaseProduct(BaseProduct baseProduct) {
        assert wellFormed();
        assert baseProduct!=null;
        menuItems.remove(baseProduct);
        modifyComposite(baseProduct,null);
        assert !menuItems.contains(baseProduct);
        assert wellFormed();
    }

    @Override
    public void addCompositeProduct(CompositeProduct compositeProduct) {
        assert wellFormed();
        assert compositeProduct!=null;
        menuItems.add(compositeProduct);
        assert menuItems.contains(compositeProduct);
        assert wellFormed();
    }

    @Override
    public int updateCompositeProduct(CompositeProduct oldProduct, String title) {
        assert wellFormed();
        assert oldProduct!=null;
        if(title==null || title.isEmpty()) return 0;
        CompositeProduct newProduct=oldProduct.copy();
        newProduct.setTitle(title);
        menuItems.remove(oldProduct);
        menuItems.add(newProduct);
        assert menuItems.contains(newProduct);
        assert wellFormed();
        return -1;
    }

    @Override
    public void deleteCompositeProduct(CompositeProduct compositeProduct) {
        assert wellFormed();
        assert compositeProduct!=null;
        menuItems.remove(compositeProduct);
        assert !menuItems.contains(compositeProduct);
        assert wellFormed();
    }

    private void generateTimeIntervalReport(String time1, String time2) {
        java.sql.Time time01= java.sql.Time.valueOf(time1+":00"); java.sql.Time time02= java.sql.Time.valueOf(time2+":00");
        Set<Order> ordersAfterFilter= orders.keySet();
        ordersAfterFilter= ordersAfterFilter
                .stream()
                .filter(x->(java.sql.Time.valueOf(x.getDateAndTime().substring(11))).after(time01) && java.sql.Time.valueOf(x.getDateAndTime().substring(11)).before(time02))
                .collect(Collectors.toSet());
        String str="Report "+nextReport +"\nOrders performed between "+time1+" and "+time2+":\n";
        Iterator<Order> iterator=ordersAfterFilter.iterator();
        while (iterator.hasNext())
            str=str+iterator.next().toString()+"\n";
        createReport(str);
        nextReport++;
    }

    private void generateReportProductsOrdered(int times) {
        ArrayList<ArrayList<MenuItem>> itemsOrdered =new ArrayList<>(orders.values());
        ArrayList<MenuItem> items=new ArrayList<>();
        Iterator<ArrayList<MenuItem>> iterator1= itemsOrdered.iterator();
        while (iterator1.hasNext()){
            Iterator<MenuItem> iterator2=iterator1.next().iterator();
            while (iterator2.hasNext()){ items.add(iterator2.next()); }
        }
        Map<MenuItem, Long> counts= items
                .stream()
                .collect(Collectors.groupingBy(e-> e,Collectors.counting()));

        ArrayList<MenuItem> products=new ArrayList<>();
        for(Map.Entry<MenuItem, Long> entry: counts.entrySet()){
            if(entry.getValue()>times)
                products.add(entry.getKey());
        }
        String str="Report "+nextReport +"\nProducts ordered more than "+times+" times:\n";
        Iterator<MenuItem> iterator=products.iterator();
        while (iterator.hasNext()) str=str+iterator.next().toString()+"\n";
        createReport(str);
        nextReport++;
    }

    private void generateReportClients(int nrTimes, int minValue, HashSet<User> users) {
        Set<Order> ordersAfterFilter= orders.keySet();
        Map<String, Long> counts=ordersAfterFilter
                .stream()
                .filter(x->x.getTotalPrice()>minValue)
                .collect(Collectors.groupingBy(e->e.getClientID(),Collectors.counting()));

        ArrayList<String> ids= new ArrayList<>();
        for(Map.Entry<String, Long> entry: counts.entrySet()) {
            if (entry.getValue() > nrTimes)
                ids.add(entry.getKey());
        }

        String str="Report "+nextReport +"\nClients who made more than "+nrTimes+" orders with a value greater than "+minValue+":\n";
        Iterator<User> iterator=users.iterator();
        while (iterator.hasNext()){
            User usr= iterator.next();
            if(ids.contains(usr.getId())) str=str+usr.toString()+"\n";
        }
        createReport(str);
        nextReport++;
    }

    private void generateReportProductsOnDate(String date) {
        Map<Order,ArrayList<MenuItem>> ordersAfterFilter= orders.entrySet()
                .stream()
                .filter(e->e.getKey().getDateAndTime().substring(0,10).equals(date))
                .collect(Collectors.toMap(entry->entry.getKey(), entry->entry.getValue()));

        ArrayList<ArrayList<MenuItem>> itemsOrdered =new ArrayList<>(ordersAfterFilter.values());
        ArrayList<MenuItem> items=new ArrayList<>();
        Iterator<ArrayList<MenuItem>> iterator1= itemsOrdered.iterator();

        while (iterator1.hasNext()){
            Iterator<MenuItem> iterator2=iterator1.next().iterator();
            while (iterator2.hasNext()){ items.add(iterator2.next()); }
        }

        Map<MenuItem, Long> counts= items
                .stream()
                .collect(Collectors.groupingBy(e-> e,Collectors.counting()));

        String str="Report "+nextReport +"\nProducts order on "+date+":\n";
        for(Map.Entry<MenuItem, Long> entry: counts.entrySet()) { str=str+entry.getKey().toString()+",\nordered "+entry.getValue()+" times\n"; }
        createReport(str);
        nextReport++;
    }

    private void createBill(User user, String dateAndTime) {
        File billFile = new File("Bill_"+nextIDOrder+".txt");
        try {
            FileWriter fw = new FileWriter(billFile);
            PrintWriter pw = new PrintWriter(fw);
            String str= "Order with id: "+nextIDOrder +"\n\nClient: "+user.toString()+"\n\nDate and time: "+dateAndTime+"\n\nProducts:\n\n"+cartToString();
            pw.append(str);
            pw.close();
        } catch (IOException e) {System.out.println("ERROR writing to bill\n"); e.printStackTrace();}
    }

    private void notifyEmplyees(Order order){
        setChanged();
        String str="Order "+order.getOrderID()+": "+cartToString()+", Date and time: "+order.getDateAndTime();
        notifyObservers(str);
    }

    /**
     * Deletes all products previously added to the cart.
     */
    public void clearCart() { cart=new ArrayList<>();totalPrice=0; }

    /**
     * Adds a product to the cart and updates the total price.
     * @param item the product to be added.
     */
    public void addItemToCart(MenuItem item) {
        cart.add(item);
        totalPrice+=item.getPrice();
    }

    /**
     * @return a String containing the products that are in the cart and the total price.
     */
    public String cartToString() {
        String toRet=""; Iterator<MenuItem> iterator= cart.iterator();
        while (iterator.hasNext()) {
            MenuItem item=iterator.next(); toRet=toRet+item.getTitle()+"\n*Price: "+item.getPrice()+"\n"; }
        toRet=toRet+"\nTotal price: "+totalPrice; return toRet;
    }

    public HashMap<Order, ArrayList<MenuItem>> getOrders() { return orders; }

    /**
     * @return the products to be displayed in the base products table
     */
    public HashSet<MenuItem> getMenuItemsBase()
    {
        HashSet<MenuItem> toRet= new HashSet<>();
        Iterator<MenuItem> iterator= menuItems.iterator();
        while (iterator.hasNext()){
            MenuItem menuItem= iterator.next();
            if(menuItem instanceof BaseProduct)
                toRet.add(menuItem);
        }
        return toRet;
    }

    /**
     * @return the products to be displayed in the composite products table
     */
    public HashSet<MenuItem> getMenuItemsComposite()
    {
        HashSet<MenuItem> toRet= new HashSet<>();
        Iterator<MenuItem> iterator= menuItems.iterator();
        while (iterator.hasNext()){
            MenuItem menuItem= iterator.next();
            if(menuItem instanceof CompositeProduct)
                toRet.add(menuItem);
        }
        return toRet;
    }

    /**
     * @return the products to be displayed in the base products table after the client has performed a search operation.
     */
    public HashSet<MenuItem> getSearchedBase() {
        HashSet<MenuItem> toRet= new HashSet<>();
        Iterator<MenuItem> iterator = menuItemsSearched.iterator();
        while (iterator.hasNext()){
            MenuItem it = iterator.next();
            if(it instanceof BaseProduct){
                toRet.add(it);
            }
        }
        return toRet;
    }

    /**
     * @return the products to be displayed in the composite products table after the client has performed a search operation.
     */
    public HashSet<MenuItem> getSearchedComposite() {
        HashSet<MenuItem> toRet= new HashSet<>();
        Iterator<MenuItem> iterator = menuItemsSearched.iterator();
        while (iterator.hasNext()){
            MenuItem it = iterator.next();
            if(it instanceof CompositeProduct){
                toRet.add(it);
            }
        }
        return toRet;
    }

    //-1 if ok, 0 wrong title, 1 wrong rating, 2 wrong calories, 3 wrong protein, 4 wrong fat, 5 wrong sodium, 6 wrong price
    private int verifyDataBaseProduct(String title, String rating, String calories, String protein, String fat, String sodium, String price) {
        if(title==null || title.isEmpty()) return 0;
        int ret=1;
        try{
            if(rating==null || rating.isEmpty()) return 1;
            float rt = Float.parseFloat(rating); if(rt<0) return 1;
            ret=2;
            if(calories==null || calories.isEmpty()) return 2;
            int cal=Integer.parseInt(calories); if(cal<0) return 2;
            ret=3;
            if(protein==null || protein.isEmpty()) return 3;
            int prot= Integer.parseInt(protein); if(prot<0) return 3;
            ret=4;
            if(fat==null || fat.isEmpty()) return 4;
            int f= Integer.parseInt(fat); if(f<0) return 4;
            ret=5;
            if(sodium==null || sodium.isEmpty()) return 5;
            int sod= Integer.parseInt(sodium); if(sod<0) return 5;
            ret=6;
            if(price==null || price.isEmpty()) return 6;
            int pri= Integer.parseInt(price); if(pri<0) return 6;
            return -1;
        } catch(NumberFormatException e) { return ret; }
    }

    private void modifyComposite(BaseProduct oldProduct, BaseProduct newProduct) {
        Iterator<MenuItem> iterator = menuItems.iterator();
        while(iterator.hasNext()){
            MenuItem it = iterator.next();
            if(it instanceof CompositeProduct){
                ((CompositeProduct) it).modifyProduct(oldProduct, newProduct);
            }
        }
    }

    private void createReport(String str) {
        File file = new File("Report_"+nextReport+".txt");
        try {
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);
            pw.append(str);
            pw.close();
        } catch (IOException e) {System.out.println("ERROR writing to bill\n"); e.printStackTrace();}
    }

    /**
     * @return the product that was selected by the user
     */
    public BaseProduct getBaseItem(BaseProduct baseProduct) {
        Iterator<MenuItem> iterator=menuItems.iterator();
        while(iterator.hasNext()) {
            MenuItem product = iterator.next();
            if(product instanceof BaseProduct){
                if(product.equals(baseProduct))
                    return (BaseProduct) product; }
            }
        return null;
    }

    /**
     * @return the product that was selected by the user
     */
    public CompositeProduct getCompositeItem(CompositeProduct compositeProduct ) {
        Iterator<MenuItem> iterator=menuItems.iterator();
        while(iterator.hasNext()) {
            MenuItem product = iterator.next();
            if(product instanceof CompositeProduct){
                if(product.getTitle().equals(compositeProduct.getTitle()) && product.getPrice()==compositeProduct.getPrice())
                    return (CompositeProduct) product; }
        }
        return null;
    }

    public HashSet<MenuItem> getMenuItems() {
        return menuItems;
    }
}
