package org.ro.tuc.pt.presentation;

import org.ro.tuc.pt.business.BaseProduct;
import org.ro.tuc.pt.business.CompositeProduct;
import org.ro.tuc.pt.business.DeliveryService;
import org.ro.tuc.pt.business.UsersManagement;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The class will be the controller of the AdministratorGUI and EmployeeGUI
 * It has 2 inner classes, ButtonsListenerAdministrator - AdministratorGUI, ButtonsListenerEmployee - EmployeeGUI.
 * @author Chereji Iulia
 */
public class ControllerAdministratorEmployee {
    private MainFrame mainFrame;
    private DeliveryService deliveryService;
    private UsersManagement usersManagement;

    private AdministratorGUI administratorGUI;
    private ClientGUI clientGUI;
    private EmployeeGUI employeeGUI;
    protected int rowBase=-1;
    protected int rowComposite=-1;

    protected CompositeProduct newComposite=new CompositeProduct("",0);

    /**
     * Creates a new instance of Controller.
     * @param mainFrame the main JFrame of the application, from which user input is received.
     * @param deliveryService object containing the menu items and the orders.
     * @param usersManagement object containing the users.
     */
    public ControllerAdministratorEmployee(MainFrame mainFrame, DeliveryService deliveryService, UsersManagement usersManagement)
    {
        this.mainFrame=mainFrame;
        this.deliveryService=deliveryService;
        this.usersManagement=usersManagement;

        this.administratorGUI = (AdministratorGUI) mainFrame.panels[2];
        this.employeeGUI = (EmployeeGUI) mainFrame.panels[3];
        this.clientGUI = (ClientGUI) mainFrame.panels[4];

        for(int i=0;i<administratorGUI.buttons.size();i++)
            administratorGUI.addButtonListener(new ButtonsListenerAdministrator(), i);
        for(int i=0;i<employeeGUI.buttons.size();i++)
            employeeGUI.addButtonListener(new ButtonsListenerEmployee(), i);

        administratorGUI.baseProductsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                rowBase=administratorGUI.baseProductsTable.getSelectedRow();
                rowComposite=-1;
            }
        });
        administratorGUI.compositeProductsTable.getSelectionModel().addListSelectionListener((new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                rowComposite=administratorGUI.compositeProductsTable.getSelectedRow();
                rowBase=-1;
            }
        }));

        deliveryService.addObserver(employeeGUI);
    }

    /**
     * Class implements ActionListener interface and handles administrator's request concerning products and orders, received from the administratorGUI panel.
     * It calls methods of the deliveryService object and displays the appropriate messages into the GUI.
     */
    class ButtonsListenerAdministrator implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object event=e.getSource(); administratorGUI.setWrongLabelVisible(false,0,true);
            if(event == administratorGUI.buttons.get(0)) //log out
            {
                usersManagement.logOut();
                mainFrame.setPanel(0);
            }
            else if(event == administratorGUI.buttons.get(1)) //add base product
            { int result;
                administratorGUI.setWrongLabelVisible(false,0, true);
                if((result=deliveryService.addBaseProduct(administratorGUI.fields.get(0).getText(), administratorGUI.fields.get(1).getText(), administratorGUI.fields.get(2).getText(), administratorGUI.fields.get(3).getText(), administratorGUI.fields.get(4).getText(), administratorGUI.fields.get(5).getText(), administratorGUI.fields.get(6).getText()))!=-1)
                    administratorGUI.setWrongLabelVisible(true,result,false);
                else {
                    administratorGUI.updateBaseProductsTable(deliveryService.getMenuItemsBase());
                    clientGUI.updateBaseProductsTable(deliveryService.getMenuItemsBase());
                    administratorGUI.updateFieldsToEdit(0,false,0);
                }

            }
            else if(event == administratorGUI.buttons.get(2)) //add product to composite
            {
                if(rowBase>=0)
                {
                    newComposite.addProduct(deliveryService.getBaseItem(administratorGUI.getBaseProduct(rowBase)));
                    administratorGUI.fields.get(8).setText(newComposite.toString());
                    administratorGUI.fields.get(9).setText(""+newComposite.getTotalRating());
                    administratorGUI.fields.get(10).setText(""+newComposite.getTotalCalories());
                    administratorGUI.fields.get(11).setText(""+newComposite.getTotalProtein());
                    administratorGUI.fields.get(12).setText(""+newComposite.getTotalFat());
                    administratorGUI.fields.get(13).setText(""+newComposite.getTotalSodium());
                    administratorGUI.fields.get(14).setText(""+newComposite.getPrice());

                }else administratorGUI.setWrongLabelVisible(true,7,false);

            }
            else if(event == administratorGUI.buttons.get(3))//import products
            {
                deliveryService.importProducts();
                administratorGUI.updateBaseProductsTable(deliveryService.getMenuItemsBase());
                administratorGUI.updateCompositeProductsTable(deliveryService.getMenuItemsComposite());
                clientGUI.updateBaseProductsTable(deliveryService.getMenuItemsBase());
                clientGUI.updateCompositeProductsTable(deliveryService.getMenuItemsComposite());
            }
            else if(event == administratorGUI.buttons.get(4))//delete
            {
                if(rowBase>=0)
                {
                    deliveryService.deleteBaseProduct(administratorGUI.getBaseProduct(rowBase));
                    administratorGUI.updateBaseProductsTable(deliveryService.getMenuItemsBase());
                    administratorGUI.updateCompositeProductsTable(deliveryService.getMenuItemsComposite());
                    clientGUI.updateBaseProductsTable(deliveryService.getMenuItemsBase());
                    clientGUI.updateCompositeProductsTable(deliveryService.getMenuItemsComposite());
                }
                else if(rowComposite>=0)
                {
                    deliveryService.deleteCompositeProduct(deliveryService.getCompositeItem(administratorGUI.getCompositeProduct(rowComposite)));
                    administratorGUI.updateCompositeProductsTable(deliveryService.getMenuItemsComposite());
                    clientGUI.updateCompositeProductsTable(deliveryService.getMenuItemsComposite());
                }
                else administratorGUI.setWrongLabelVisible(true,7,false);
            }
            else if(event == administratorGUI.buttons.get(5))//update
            {
                if(rowBase>=0)
                {
                    administratorGUI.updateFieldsToEdit(rowBase, true,0);
                }
                else if(rowComposite>=0)
                {
                    administratorGUI.updateFieldsToEdit(rowComposite,true,1);
                }
                else administratorGUI.setWrongLabelVisible(true,7,false);

            }
            else if(event == administratorGUI.buttons.get(6))//save
            { int result;
                if(rowBase>=0)
                {
                    if((result=deliveryService.updateBaseProduct(administratorGUI.getBaseProduct(rowBase), administratorGUI.fields.get(0).getText(), administratorGUI.fields.get(1).getText(), administratorGUI.fields.get(2).getText(), administratorGUI.fields.get(3).getText(), administratorGUI.fields.get(4).getText(), administratorGUI.fields.get(5).getText(), administratorGUI.fields.get(6).getText()))!=-1)
                        administratorGUI.setWrongLabelVisible(true,result,false);
                    else {
                        administratorGUI.updateBaseProductsTable(deliveryService.getMenuItemsBase());
                        administratorGUI.updateCompositeProductsTable(deliveryService.getMenuItemsComposite());
                        clientGUI.updateBaseProductsTable(deliveryService.getMenuItemsBase());
                        clientGUI.updateCompositeProductsTable(deliveryService.getMenuItemsComposite());
                        administratorGUI.updateFieldsToEdit(0,false,0);
                    }
                }
                else if(rowComposite>=0)
                {
                    if((result=deliveryService.updateCompositeProduct(deliveryService.getCompositeItem(administratorGUI.getCompositeProduct(rowComposite)), administratorGUI.fields.get(7).getText()))!=-1)
                        administratorGUI.setWrongLabelVisible(true,result,false);
                    else{
                        administratorGUI.updateCompositeProductsTable(deliveryService.getMenuItemsComposite());
                        clientGUI.updateCompositeProductsTable(deliveryService.getMenuItemsComposite());
                        administratorGUI.updateFieldsToEdit(0,false,0);
                    }
                }
            }
            else if(event == administratorGUI.buttons.get(7)) //add composite product
            {
                if(newComposite!=null)
                {
                    if(administratorGUI.fields.get(7).getText()!=null && !administratorGUI.fields.get(7).getText().isEmpty())
                    {
                        newComposite.setTitle(administratorGUI.fields.get(7).getText());
                        if(newComposite.getNrItems()>1)
                        {
                            deliveryService.addCompositeProduct(newComposite);
                            administratorGUI.updateCompositeProductsTable(deliveryService.getMenuItemsComposite());
                            clientGUI.updateCompositeProductsTable(deliveryService.getMenuItemsComposite());
                            newComposite=new CompositeProduct("",0);
                            administratorGUI.updateFieldsToEdit(0,false,0);
                            administratorGUI.setWrongLabelVisible(false,0, true);
                        }
                        else administratorGUI.setWrongLabelVisible(true,8,false);
                    }
                    else administratorGUI.setWrongLabelVisible(true,0,false);
                }
            }
            else if(event == administratorGUI.buttons.get(8)) //generate report
            {
                int res;
                if((res=deliveryService.generateReport(administratorGUI.fields.get(15).getText(),administratorGUI.fields.get(16).getText(),administratorGUI.fields.get(17).getText(),administratorGUI.fields.get(18).getText(),administratorGUI.fields.get(19).getText(),administratorGUI.fields.get(20).getText(), usersManagement.getUsers()))==-1)
                    administratorGUI.setWrongLabelVisible(true,9,false);
                else {
                    administratorGUI.setWrongLabelVisible(false,9,true);
                    administratorGUI.clean();
                }
            }

        }
    }

    /**
     * Class implements ActionListener interface and handles administrator's request received from the employeeGUI panel.
     */
    class ButtonsListenerEmployee implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object event=e.getSource();
            if(event == employeeGUI.buttons.get(0)) //log out
            {
                usersManagement.logOut();
                mainFrame.setPanel(0);
            }
        }
    }
}

