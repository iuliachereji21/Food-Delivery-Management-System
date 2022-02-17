package org.ro.tuc.pt.presentation;

import org.ro.tuc.pt.business.DeliveryService;
import org.ro.tuc.pt.business.UsersManagement;
import org.ro.tuc.pt.dataPersistance.Serializator;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The class extends JFrame. It will be the main window of the application, containing the panels.
 * @author Chereji Iulia
 */
public class MainFrame extends JFrame {
    private int screenHeight = 950;
    private int screenWidth = 1600;
    private DeliveryService deliveryService;
    /**
     * the list of panels contained in the frame, from which only 1 will be displayed at a given moment
     */
    public JPanel[] panels;

    /**
     * Creates a new instance of MainFrame.
     * @param title the title of the frame
     * @param deliveryService object containing the menu items and the orders.
     * @param usersManagement object containing the users.
     */
    public MainFrame(String title, final DeliveryService deliveryService, final UsersManagement usersManagement) {
        super(title);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(150, 50, screenWidth, screenHeight);
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(false);
        this.deliveryService=deliveryService;
        panels = new JPanel[5];
        panels[0] = new LogInGUI(screenHeight, screenWidth);
        panels[1] = new RegisterGUI(screenHeight, screenWidth);
        panels[2] = new AdministratorGUI(screenHeight, screenWidth);
        panels[3] = new EmployeeGUI(screenHeight, screenWidth);
        panels[4] = new ClientGUI(screenHeight, screenWidth);


        ((AdministratorGUI)panels[2]).updateBaseProductsTable(deliveryService.getMenuItemsBase());
        ((AdministratorGUI)panels[2]).updateCompositeProductsTable(deliveryService.getMenuItemsComposite());
        ((ClientGUI)panels[4]).updateBaseProductsTable(deliveryService.getMenuItemsBase());
        ((ClientGUI)panels[4]).updateCompositeProductsTable(deliveryService.getMenuItemsComposite());

        this.setContentPane(panels[0]);
        this.revalidate();
        this.repaint();


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Serializator.saveUsersToFile(usersManagement.getUsers());
                Serializator.saveOrdersToFile(deliveryService.getOrders());
                Serializator.saveProductsToFile(deliveryService.getMenuItems());
                System.exit(0);
            }
        });
    }

    /**
     * sets the content pane of the frame to be one of the panels.
     * @param panelNumber index in the panels list of the desired panel to be shown.
     */
    public void setPanel(int panelNumber)
    {
        this.setContentPane(panels[panelNumber]);
        if(panelNumber==0)
            ((LogInGUI)panels[panelNumber]).clean();
        if(panelNumber==1)
            ((RegisterGUI)panels[panelNumber]).clean();
        if(panelNumber==2)
            ((AdministratorGUI)panels[panelNumber]).clean();
        if(panelNumber==4) {
            ((ClientGUI) panels[panelNumber]).clean();
            deliveryService.clearCart();
            ((ClientGUI)panels[4]).updateBaseProductsTable(deliveryService.getMenuItemsBase());
            ((ClientGUI)panels[4]).updateCompositeProductsTable(deliveryService.getMenuItemsComposite());
        }
        this.revalidate();
        this.repaint();
    }
}
