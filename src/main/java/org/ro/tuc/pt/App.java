package org.ro.tuc.pt;

import org.ro.tuc.pt.business.DeliveryService;
import org.ro.tuc.pt.business.UsersManagement;
import org.ro.tuc.pt.presentation.ControllerAdministratorEmployee;
import org.ro.tuc.pt.presentation.ControllerClient;
import org.ro.tuc.pt.presentation.ControllerLogInRegister;
import org.ro.tuc.pt.presentation.MainFrame;

/**
 * This class contains the main method of the application.
 * @author Chereji Iulia
 */
public class App 
{
    /**
     * the method creates the objects of classes UsersManagement, DeliveryService, MainFrame, ControllerAdministratorEmployee, ControllerLogInRegister and ControllerClient.
     * @param args the arguments of the application
     */
    public static void main( String[] args )
    {
        UsersManagement usersManagement=new UsersManagement();
        DeliveryService deliveryService = new DeliveryService();
        MainFrame mainFrame= new MainFrame("Food delivery management system", deliveryService,usersManagement);
        ControllerAdministratorEmployee controllerAdministrator = new ControllerAdministratorEmployee(mainFrame,deliveryService, usersManagement);
        ControllerLogInRegister controllerLogInRegister= new ControllerLogInRegister(mainFrame,usersManagement);
        ControllerClient controllerClient = new ControllerClient(mainFrame,deliveryService,usersManagement);
    }
}
