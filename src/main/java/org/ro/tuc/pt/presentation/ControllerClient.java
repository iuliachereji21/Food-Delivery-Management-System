package org.ro.tuc.pt.presentation;

import org.ro.tuc.pt.business.DeliveryService;
import org.ro.tuc.pt.business.UsersManagement;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The class will be the controller of the ClientGUI.
 * It has 1 inner class, ButtonsListenerClient.
 * @author Chereji Iulia
 */
public class ControllerClient {
    private MainFrame mainFrame;
    private DeliveryService deliveryService;
    private UsersManagement usersManagement;
    private ClientGUI clientGUI;
    protected int rowBase=-1;
    protected int rowComposite=-1;

    /**
     * Creates a new instance of Controller.
     * @param mainFrame the main JFrame of the application, from which user input is received.
     * @param deliveryService object containing the menu items and the orders.
     * @param usersManagement object containing the users.
     */
    public ControllerClient(MainFrame mainFrame, DeliveryService deliveryService, UsersManagement usersManagement)
    {
        this.mainFrame=mainFrame;
        this.deliveryService=deliveryService;
        this.usersManagement=usersManagement;

        this.clientGUI = (ClientGUI) mainFrame.panels[4];
        for(int i=0;i<clientGUI.buttons.size();i++)
            clientGUI.addButtonListener(new ButtonsListenerClient(), i);
        clientGUI.baseProductsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                rowBase=clientGUI.baseProductsTable.getSelectedRow();
                rowComposite=-1;
            }
        });
        clientGUI.compositeProductsTable.getSelectionModel().addListSelectionListener((new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                rowComposite=clientGUI.compositeProductsTable.getSelectedRow();
                rowBase=-1;
            }
        }));
    }

    /**
     * Class implements ActionListener interface and handles client's request concerning products and orders, received from the clientGUI panel.
     * It calls methods of the deliveryService object and displays the appropriate messages into the GUI.
     */
    class ButtonsListenerClient implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object event=e.getSource(); clientGUI.setWrongLabelVisible(false,0,true);
            if(event == clientGUI.buttons.get(0)) //log out
            {
                usersManagement.logOut();
                mainFrame.setPanel(0);
            }
            else if(event == clientGUI.buttons.get(1)) //search
            {
                int res=deliveryService.searchProducts(clientGUI.fields.get(0).getText(),clientGUI.fields.get(1).getText(),clientGUI.fields.get(2).getText(),clientGUI.fields.get(3).getText(),clientGUI.fields.get(4).getText(),clientGUI.fields.get(5).getText(),clientGUI.fields.get(6).getText());
                if(res!=-1)
                {
                    clientGUI.setWrongLabelVisible(true, res, false);
                }
                else {
                    clientGUI.updateBaseProductsTable(deliveryService.getSearchedBase());
                    clientGUI.updateCompositeProductsTable(deliveryService.getSearchedComposite());
                }
            }
            else if(event == clientGUI.buttons.get(2)) //full menu
            {
                clientGUI.updateBaseProductsTable(deliveryService.getMenuItemsBase());
                clientGUI.updateCompositeProductsTable(deliveryService.getMenuItemsComposite());
            }
            else if(event == clientGUI.buttons.get(3)) //add to cart
            {
                if(rowBase>=0)
                {
                    deliveryService.addItemToCart(deliveryService.getBaseItem(clientGUI.getBaseProduct(rowBase)));
                    clientGUI.cartDisplay.setText(deliveryService.cartToString());
                }
                else if(rowComposite>=0)
                {
                    deliveryService.addItemToCart(deliveryService.getCompositeItem(clientGUI.getCompositeProduct(rowComposite)));
                    clientGUI.cartDisplay.setText(deliveryService.cartToString());
                }
                else clientGUI.setWrongLabelVisible(true,2,false);
            }
            else if(event == clientGUI.buttons.get(4)) //clear cart
            {
                deliveryService.clearCart();
                clientGUI.cartDisplay.setText(deliveryService.cartToString());
            }
            else if(event == clientGUI.buttons.get(5)) //place order
            {
                deliveryService.placeOrder(usersManagement.getCurrentUser());
                clientGUI.cartDisplay.setText(deliveryService.cartToString());
            }
        }
    }
}

