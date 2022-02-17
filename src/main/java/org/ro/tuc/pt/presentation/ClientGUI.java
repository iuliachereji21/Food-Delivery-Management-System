package org.ro.tuc.pt.presentation;

import org.ro.tuc.pt.business.BaseProduct;
import org.ro.tuc.pt.business.CompositeProduct;
import org.ro.tuc.pt.business.MenuItem;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * The class extends JPanel. It will contain the necessary swing components for performing client operations
 * (search for products, add products in the cart, place orders).
 * @author Chereji Iulia
 */
public class ClientGUI extends JPanel{
    /** the list of buttons in the view. */
    public ArrayList<JButton> buttons;
    /** the list of text fields in the view. */
    public ArrayList<JTextField> fields;
    /** the list of labels which will be used for showing error messages */
    public ArrayList<JLabel> wrongLabels;
    /** the table where the base products will be displayed */
    public JTable baseProductsTable;
    private DefaultTableModel tableModel1;
    /** the table where the composite products will be displayed */
    public JTable compositeProductsTable;
    private DefaultTableModel tableModel2;
    /** the area where the contents of the cart will be displayed*/
    public JTextArea cartDisplay;

    /**
     * Creates a new instance of ClientGUI.
     * @param height height of the panel
     * @param width width of the panel
     */
    public ClientGUI(int height, int width)
    {
        super();
        this.setBounds(0,0, height, width);
        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);
        buttons=new ArrayList<>();
        //nr buttons = 6
        for(int i=0;i<6;i++)
        {
            JButton button=new JButton();
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("TimesRoman",20,20));
            buttons.add(button);
            this.add(button);
        }

        buttons.get(0).setText("LOG OUT");
        buttons.get(0).setBounds(1400,30, 150, 50);
        buttons.get(1).setText("SEARCH");
        buttons.get(1).setBounds(1060,150, 150, 50);
        buttons.get(2).setText("FULL MENU");
        buttons.get(2).setBounds(1300,150, 150, 50);
        buttons.get(3).setText("ADD TO CART");
        buttons.get(3).setBounds(950,255, 150, 50);
        buttons.get(4).setText("CLEAR CART");
        buttons.get(4).setBounds(1150,255, 150, 50);
        buttons.get(5).setText("PLACE ORDER");
        buttons.get(5).setBounds(1400,800, 150, 50);


        int nrDataLabels = 8;
        JLabel[] dataLabels = new JLabel[nrDataLabels];
        for(int i = 0; i< nrDataLabels; i++)
        {
            dataLabels[i]=new JLabel();
            dataLabels[i].setFont(new Font("TimesRoman",20,25));
            dataLabels[i].setForeground(Color.WHITE);
            this.add(dataLabels[i]);
            dataLabels[i].setVisible(true);
        }

        dataLabels[0].setText("Menu");
        dataLabels[0].setBounds(50,20,500,70);
        dataLabels[0].setFont(new Font("TimesRoman",20,60));
        dataLabels[1].setText("Title:");
        dataLabels[1].setBounds(60,105,100,70);
        dataLabels[2].setText("Rating:");
        dataLabels[2].setBounds(470,105,100,70);
        dataLabels[3].setText("Calories:");
        dataLabels[3].setBounds(707,105,100,70);
        dataLabels[4].setText("Protein:");
        dataLabels[4].setBounds(62,170,100,70);
        dataLabels[5].setText("Fat:");
        dataLabels[5].setBounds(300,170,100,70);
        dataLabels[6].setText("Sodium:");
        dataLabels[6].setBounds(490,170,100,70);
        dataLabels[7].setText("Price:");
        dataLabels[7].setBounds(740,170,100,70);

        //nrFields=7;

        fields = new ArrayList<>();
        for(int i=0;i<7;i++)
        {
            JTextField field=new JTextField();
            field.setFont(new Font("TimesRoman",20,20));
            field.setForeground(Color.WHITE);
            field.setOpaque(false);
            this.add(field);
            fields.add(field);
        }

        fields.get(0).setBounds(140,120,310,40);
        fields.get(1).setBounds(560,120,120,40);
        fields.get(2).setBounds(818,120,120,40);
        fields.get(3).setBounds(160,185,120,40);
        fields.get(4).setBounds(350,185,120,40);
        fields.get(5).setBounds(590,185,120,40);
        fields.get(6).setBounds(818,185,120,40);

        //nrWrongLabels=3;
        wrongLabels=new ArrayList<>();
        for(int i=0;i<3;i++)
        {
            JLabel wrongLabel;
            wrongLabel=new JLabel();
            wrongLabel.setFont(new Font("TimesRoman",20,20));
            wrongLabel.setForeground(Color.RED);
            this.add(wrongLabel);
            wrongLabels.add(wrongLabel);
            wrongLabel.setVisible(false);
            wrongLabel.setBounds(970,200,440,30);
        }

        wrongLabels.get(0).setText("*please introduce valid numbers");
        wrongLabels.get(1).setText("*please introduce at least 1 search filter");
        wrongLabels.get(2).setText("*please select a product");

        Border border = BorderFactory.createEmptyBorder();

        JPanel tablePanel1 = new JPanel();
        tablePanel1.setBackground(Color.DARK_GRAY);
        tablePanel1.setBounds(50,250,900, 320);
        tablePanel1.setBorder(border);

        String [] columns1={"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price"};
        tableModel1= new DefaultTableModel();

        for(int i=0;i<columns1.length;i++)
            tableModel1.addColumn(columns1[i]);

        baseProductsTable= new JTable();
        baseProductsTable.setModel(tableModel1);
        baseProductsTable.setBackground(Color.DARK_GRAY);
        baseProductsTable.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
        baseProductsTable.setForeground(Color.WHITE);
        baseProductsTable.setFont(new Font("TimesRoman",20,20));
        baseProductsTable.setRowHeight(40);
        baseProductsTable.getTableHeader().setFont(new Font("TimesRoman",20,20));
        baseProductsTable.getTableHeader().setBackground(Color.DARK_GRAY);
        baseProductsTable.getTableHeader().setForeground(Color.WHITE);
        baseProductsTable.setPreferredScrollableViewportSize(new Dimension(900,320));
        baseProductsTable.setFillsViewportHeight(true);

        JScrollPane scrollPane1 = new JScrollPane(baseProductsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane1.setPreferredSize(new Dimension(870,310));
        scrollPane1.setBorder(border);
        tablePanel1.add(scrollPane1);
        this.add(tablePanel1);

        JPanel tablePanel2 = new JPanel();
        tablePanel2.setBackground(Color.DARK_GRAY);
        tablePanel2.setBounds(50,590,900, 200);
        tablePanel2.setBorder(border);

        String [] columns2={"Title", "Contents", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price"};
        tableModel2= new DefaultTableModel();

        for(int i=0;i<columns2.length;i++)
            tableModel2.addColumn(columns2[i]);

        compositeProductsTable= new JTable();
        compositeProductsTable.setModel(tableModel2);
        compositeProductsTable.setBackground(Color.DARK_GRAY);
        compositeProductsTable.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
        compositeProductsTable.setForeground(Color.WHITE);
        compositeProductsTable.setFont(new Font("TimesRoman",20,20));
        compositeProductsTable.setRowHeight(40);
        compositeProductsTable.getTableHeader().setFont(new Font("TimesRoman",20,20));
        compositeProductsTable.getTableHeader().setBackground(Color.DARK_GRAY);
        compositeProductsTable.getTableHeader().setForeground(Color.WHITE);
        compositeProductsTable.setPreferredScrollableViewportSize(new Dimension(900,200));
        compositeProductsTable.setFillsViewportHeight(true);

        JScrollPane scrollPane2 = new JScrollPane(compositeProductsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane2.setPreferredSize(new Dimension(870,190));
        scrollPane2.setBorder(border);
        tablePanel2.add(scrollPane2);
        this.add(tablePanel2);


        JPanel scrollPanel = new JPanel();
        scrollPanel.setBackground(Color.DARK_GRAY);
        scrollPanel.setBounds(950,320,600, 465);
        scrollPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        this.add(scrollPanel);

        cartDisplay= new JTextArea(10,30);
        cartDisplay.setBorder(border);
        cartDisplay.setFont(new Font("TimesRoman",20,25));
        cartDisplay.setText("");
        cartDisplay.setLineWrap(true);
        cartDisplay.setWrapStyleWord(true);
        cartDisplay.setForeground(Color.WHITE);
        cartDisplay.setBackground(Color.DARK_GRAY);
        cartDisplay.setText("\nTotal price: 0");

        JScrollPane scrollPane = new JScrollPane(cartDisplay, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(598,458));
        scrollPane.setBorder(border);
        scrollPanel.add(scrollPane);

    }

    /**
     * attempts to add an action listener to one of the button of the panel.
     * @param listener reference to the action listener.
     * @param nrOfTheButton the index of the button in the buttons array.
     */
    public void addButtonListener(ActionListener listener, int nrOfTheButton)
    {
        if(nrOfTheButton<buttons.size())
            buttons.get(nrOfTheButton).addActionListener(listener);
    }

    /**
     * attempts to make a label in the panel visible or not.
     * @param visible true to be visible, false if not.
     * @param nrOfTheLabel the index of the label in the wrongLabels list.
     * @param all true if all the labels to be set visible/unvisible, false if only one of them.
     */
    public void setWrongLabelVisible(boolean visible, int nrOfTheLabel, boolean all)
    {
        if(all)
            for(int i=0;i< wrongLabels.size();i++)
                wrongLabels.get(i).setVisible(visible);
        else
            wrongLabels.get(nrOfTheLabel).setVisible(visible);
    }

    /**
     * receives a set of base products and adds entries in the baseProductsTable.
     * @param baseProducts the set of objects
     */
    public void updateBaseProductsTable(HashSet<org.ro.tuc.pt.business.MenuItem> baseProducts)
    {
        int nr=tableModel1.getRowCount();
        for(int i=nr-1;i>=0;i--)
            tableModel1.removeRow(i);

        Object values[] = new Object[7];
        Iterator<org.ro.tuc.pt.business.MenuItem> iterator = baseProducts.iterator();
        while(iterator.hasNext())
        {
            org.ro.tuc.pt.business.MenuItem menuItem= iterator.next();
            values[0]=menuItem.getTitle();
            values[1]=((BaseProduct)menuItem).getRating();
            values[2]=((BaseProduct)menuItem).getCalories();
            values[3]=((BaseProduct)menuItem).getProtein();
            values[4]=((BaseProduct)menuItem).getFat();
            values[5]=((BaseProduct)menuItem).getSodium();
            values[6]=menuItem.getPrice();
            tableModel1.addRow(values);
        }
        baseProductsTable.setModel(tableModel1);
    }

    /**
     * receives a set of composite products and adds entries in the compositeProductsTable.
     * @param compositeProducts the set of objects
     */
    public void updateCompositeProductsTable(HashSet<MenuItem> compositeProducts)
    {
        int nr=tableModel2.getRowCount();
        for(int i=nr-1;i>=0;i--)
            tableModel2.removeRow(i);

        Object values[] = new Object[8];
        Iterator<MenuItem> iterator = compositeProducts.iterator();
        while(iterator.hasNext())
        {
            CompositeProduct menuItem= (CompositeProduct) iterator.next();
            values[0]=menuItem.getTitle();
            values[1]=menuItem.toString().substring(menuItem.toString().indexOf(':')+2);
            values[2]=menuItem.getTotalRating();
            values[3]=menuItem.getTotalCalories();
            values[4]=menuItem.getTotalProtein();
            values[5]=menuItem.getTotalFat();
            values[6]=menuItem.getTotalSodium();
            values[7]=menuItem.getPrice();
            tableModel2.addRow(values);
        }
        compositeProductsTable.setModel(tableModel2);
    }

    /**
     * @param row selected by the user in the baseProductsTable
     * @return the base product composed of the data in the table
     */
    public BaseProduct getBaseProduct(int row)
    {
        return new BaseProduct((String)(baseProductsTable.getValueAt(row,0)),(float)(baseProductsTable.getValueAt(row,1)), (int)(baseProductsTable.getValueAt(row,2)), (int)(baseProductsTable.getValueAt(row,3)), (int)(baseProductsTable.getValueAt(row,4)), (int)(baseProductsTable.getValueAt(row,5)), (int)(baseProductsTable.getValueAt(row,6)));
    }

    /**
     * @param row selected by the user in the compositeProductsTable
     * @return the composite product composed of the data in the table
     */
    public CompositeProduct getCompositeProduct(int row)
    {
        return new CompositeProduct((String) compositeProductsTable.getValueAt(row,0),(int)compositeProductsTable.getValueAt(row,7));
    }

    /**
     * Sets all the wrong lables to not visible and all the fields to empty.
     */
    public void clean()
    {
        for(int i=0;i< wrongLabels.size();i++)
            wrongLabels.get(i).setVisible(false);
        for(int i=0;i< fields.size();i++)
            fields.get(i).setText(null);
    }

}

