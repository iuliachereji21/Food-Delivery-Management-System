package org.ro.tuc.pt.presentation;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.ro.tuc.pt.business.BaseProduct;
import org.ro.tuc.pt.business.CompositeProduct;
import org.ro.tuc.pt.business.MenuItem;

/**
 * The class extends JPanel. It will contain the necessary swing components for performing administrator operations
 * (add, edit, delete products, import products, create composite products, generate reports).
 * @author Chereji Iulia
 */
public class AdministratorGUI extends JPanel{
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

    /**
     * Creates a new instance of AdministratorGUI.
     * @param height height of the panel
     * @param width width of the panel
     */
    public AdministratorGUI(int height, int width)
    {
        super();
        this.setBounds(0,0, height, width);
        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);
        buttons=new ArrayList<>();
        //nr buttons = 9
        for(int i=0;i<9;i++)
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
        buttons.get(1).setText("Add base product");
        buttons.get(1).setBounds(970,335, 250, 50);
        buttons.get(2).setText("Add product to composite");
        buttons.get(2).setBounds(1285,185, 250, 50);
        buttons.get(3).setText("Import products");
        buttons.get(3).setBounds(970,185, 250, 50);
        buttons.get(4).setText("Delete");
        buttons.get(4).setBounds(970,260, 170, 50);
        buttons.get(5).setText("Update");
        buttons.get(5).setBounds(1167,260, 170, 50);
        buttons.get(6).setText("Save");
        buttons.get(6).setBounds(1365,260, 170, 50);
        buttons.get(7).setText("Add composite product");
        buttons.get(7).setBounds(1285,335, 250, 50);
        buttons.get(8).setText("Generate report");
        buttons.get(8).setBounds(1285,800, 250, 50);

        int nrDataLabels = 10;
        JLabel[] dataLabels = new JLabel[nrDataLabels];
        for(int i = 0; i< nrDataLabels; i++)
        {
            dataLabels[i]=new JLabel();
            dataLabels[i].setFont(new Font("TimesRoman",20,25));
            dataLabels[i].setForeground(Color.WHITE);
            this.add(dataLabels[i]);
            dataLabels[i].setVisible(true);
        }

        dataLabels[0].setText("Administrator");
        dataLabels[0].setBounds(50,50,500,70);
        dataLabels[0].setFont(new Font("TimesRoman",20,60));
        dataLabels[1].setText("Base Products:");
        dataLabels[1].setBounds(50,120,500,70);
        dataLabels[2].setText("Composite Products:");
        dataLabels[2].setBounds(50,530,500,70);

        dataLabels[3].setText("Reports:");
        dataLabels[3].setBounds(970,390,500,70);
        dataLabels[4].setText("Orders between           (time) and            (time)");
        dataLabels[4].setBounds(970,430,800,70);
        dataLabels[5].setText("Products ordered more than            times");
        dataLabels[5].setBounds(970,500,800,70);
        dataLabels[6].setText("Clients who made more than            orders");
        dataLabels[6].setBounds(970,570,800,70);
        dataLabels[7].setText("with a value higher than");
        dataLabels[7].setBounds(970,610,800,70);
        dataLabels[8].setText("Products ordered on                   (date), and the");
        dataLabels[8].setBounds(970,680,800,70);
        dataLabels[9].setText("number of times they were ordered");
        dataLabels[9].setBounds(970,720,800,70);

        //nrFields=21;
        fields = new ArrayList<>();
        for(int i=0;i<21;i++)
        {
            JTextField field=new JTextField();
            field.setFont(new Font("TimesRoman",20,20));
            field.setForeground(Color.WHITE);
            field.setOpaque(false);
            this.add(field);
            fields.add(field);
        }

        fields.get(0).setBounds(64,500,123,45);
        fields.get(1).setBounds(185,500,123,45);
        fields.get(2).setBounds(306,500,123,45);
        fields.get(3).setBounds(428,500,123,45);
        fields.get(4).setBounds(550,500,123,45);
        fields.get(5).setBounds(671,500,124,45);
        fields.get(6).setBounds(792,500,123,45);
        fields.get(7).setBounds(64,790,108,45);
        fields.get(8).setBounds(172,790,108,45);
        fields.get(8).setEditable(false);
        fields.get(9).setBounds(280,790,106,45);
        fields.get(9).setEditable(false);
        fields.get(10).setBounds(386,790,104,45);
        fields.get(10).setEditable(false);
        fields.get(11).setBounds(490,790,108,45);
        fields.get(11).setEditable(false);
        fields.get(12).setBounds(598,790,106,45);
        fields.get(12).setEditable(false);
        fields.get(13).setBounds(704,790,108,45);
        fields.get(13).setEditable(false);
        fields.get(14).setBounds(812,790,106,45);
        fields.get(14).setEditable(false);
        fields.get(15).setBounds(1153,447,70,35);
        fields.get(16).setBounds(1350,447,70,35);
        fields.get(17).setBounds(1290,517,70,35);
        fields.get(18).setBounds(1300,587,70,35);
        fields.get(19).setBounds(1245,627,70,35);
        fields.get(20).setBounds(1205,697,120,35);

        //nrWrongLabels=10;
        wrongLabels=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            JLabel wrongLabel;
            wrongLabel=new JLabel();
            wrongLabel.setFont(new Font("TimesRoman",20,20));
            wrongLabel.setForeground(Color.RED);
            this.add(wrongLabel);
            wrongLabels.add(wrongLabel);
            wrongLabel.setVisible(false);
            wrongLabel.setBounds(970,385,440,30);
        }

        wrongLabels.get(0).setText("*wrong title");
        wrongLabels.get(1).setText("*wrong rating");
        wrongLabels.get(2).setText("*wrong calories");
        wrongLabels.get(3).setText("*wrong protein");
        wrongLabels.get(4).setText("*wrong fat");
        wrongLabels.get(5).setText("*wrong sodium");
        wrongLabels.get(6).setText("*wrong price");
        wrongLabels.get(7).setText("*please select a product");
        wrongLabels.get(8).setText("*please select 2 or more products");
        wrongLabels.get(9).setText("*wrong input format");
        wrongLabels.get(9).setBounds(970,800,440,30);

        Border border = BorderFactory.createEmptyBorder();

        JPanel tablePanel1 = new JPanel();
        tablePanel1.setBackground(Color.DARK_GRAY);
        tablePanel1.setBounds(50,180,900, 320);
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

        //***************
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
    public void updateBaseProductsTable(HashSet<MenuItem> baseProducts)
    {
        int nr=tableModel1.getRowCount();
        for(int i=nr-1;i>=0;i--)
            tableModel1.removeRow(i);

        Object values[] = new Object[7];
        Iterator<MenuItem> iterator = baseProducts.iterator();
        while(iterator.hasNext())
        {
            MenuItem menuItem= iterator.next();
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
     * sets the text shown in the fields of the panel.
     * @param row number of the row in the table from which data must be taken.
     * @param filled true if the fields must be filled with information taken from the table (in case of edit), false if the fields must be made empty.
     * @param table 0 if baseProductsTable, 1 if compositeProductsTable
     */
    public void updateFieldsToEdit(int row, boolean filled, int table)
    {
        if(!filled)
        {
            fields.get(0).setText(null);
            fields.get(1).setText(null);
            fields.get(2).setText(null);
            fields.get(3).setText(null);
            fields.get(4).setText(null);
            fields.get(5).setText(null);
            fields.get(6).setText(null);
            fields.get(7).setText(null);
            fields.get(8).setText(null);
            fields.get(9).setText(null);
            fields.get(10).setText(null);
            fields.get(11).setText(null);
            fields.get(12).setText(null);
            fields.get(13).setText(null);
            fields.get(14).setText(null);
            fields.get(15).setText(null);
            fields.get(16).setText(null);
            fields.get(17).setText(null);
            fields.get(18).setText(null);
            fields.get(19).setText(null);
            fields.get(20).setText(null);
        }
        else if(filled && table==0) //base products
        {
            fields.get(0).setText((String)(baseProductsTable.getValueAt(row,0)));
            fields.get(1).setText(String.valueOf(baseProductsTable.getValueAt(row,1)));
            fields.get(2).setText(String.valueOf(baseProductsTable.getValueAt(row,2)));
            fields.get(3).setText(String.valueOf(baseProductsTable.getValueAt(row,3)));
            fields.get(4).setText(String.valueOf(baseProductsTable.getValueAt(row,4)));
            fields.get(5).setText(String.valueOf(baseProductsTable.getValueAt(row,5)));
            fields.get(6).setText(String.valueOf(baseProductsTable.getValueAt(row,6)));
            fields.get(7).setText(null);
            fields.get(8).setText(null);
            fields.get(9).setText(null);
            fields.get(10).setText(null);
            fields.get(11).setText(null);
            fields.get(12).setText(null);
            fields.get(13).setText(null);
            fields.get(14).setText(null);
        }
        else if(filled && table==1) //composite products
        {
            fields.get(0).setText(null);
            fields.get(1).setText(null);
            fields.get(2).setText(null);
            fields.get(3).setText(null);
            fields.get(4).setText(null);
            fields.get(5).setText(null);
            fields.get(6).setText(null);
            fields.get(7).setText(String.valueOf(compositeProductsTable.getValueAt(row,0)));
            fields.get(8).setText(String.valueOf(compositeProductsTable.getValueAt(row,1)));
            fields.get(9).setText(String.valueOf(compositeProductsTable.getValueAt(row,2)));
            fields.get(10).setText(String.valueOf(compositeProductsTable.getValueAt(row,3)));
            fields.get(11).setText(String.valueOf(compositeProductsTable.getValueAt(row,4)));
            fields.get(12).setText(String.valueOf(compositeProductsTable.getValueAt(row,5)));
            fields.get(13).setText(String.valueOf(compositeProductsTable.getValueAt(row,6)));
            fields.get(14).setText(String.valueOf(compositeProductsTable.getValueAt(row,7)));
        }
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

