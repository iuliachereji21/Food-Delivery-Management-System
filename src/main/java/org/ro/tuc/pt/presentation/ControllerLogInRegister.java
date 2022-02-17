package org.ro.tuc.pt.presentation;

import org.ro.tuc.pt.business.UsersManagement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The class will be the controller of the LogInGUI and RegisterGUI
 * It has 2 inner classes, ButtonsListenerLogIn - LogInGUI, ButtonsListenerRegister - RegisterGUI.
 * @author Chereji Iulia
 */
public class ControllerLogInRegister {
    private MainFrame mainFrame;
    private LogInGUI logInGUI;
    private RegisterGUI registerGUI;
    private UsersManagement usersManagement;

    /**
     * Creates a new instance of Controller.
     * @param mainFrame the main JFrame of the application, from which user input is received.
     * @param usersManagement object containing the users.
     */
    public ControllerLogInRegister(MainFrame mainFrame, UsersManagement usersManagement)
    {
        this.mainFrame=mainFrame;
        this.usersManagement=usersManagement;

        this.logInGUI=(LogInGUI) mainFrame.panels[0];
        this.registerGUI =(RegisterGUI) mainFrame.panels[1];

        for(int i=0;i<logInGUI.buttons.size();i++)
            logInGUI.addButtonListener(new ButtonsListenerLogIn(), i);
        for(int i=0;i<registerGUI.buttons.size();i++)
            registerGUI.addButtonListener(new ButtonsListenerRegister(), i);

    }

    /**
     * Class implements ActionListener interface and handles clients's request concerning clients log in, received from the logInGUI panel.
     * It calls methods of the usersManagement object and displays the appropriate messages into the GUI.
     */
    class ButtonsListenerLogIn implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object event=e.getSource(); logInGUI.setWrongLabelVisible(false,0,true);
            if(event==logInGUI.buttons.get(0))//log in
            {
                int res;
                if((res= usersManagement.CheckLogIn(logInGUI.fields.get(0).getText(),logInGUI.fields.get(1).getText()))==0)
                    logInGUI.setWrongLabelVisible(true,0,false);
                else{
                    if(res==1)//admin
                    mainFrame.setPanel(2);
                    else if(res==2)
                        mainFrame.setPanel(4);
                    else mainFrame.setPanel(3);
                }
            }
            else if(event==logInGUI.buttons.get(1))//register
            {
                mainFrame.setPanel(1);
            }
        }
    }

    /**
     * Class implements ActionListener interface and handles clients's request concerning clients register, received from the registerGUI panel.
     * It calls methods of the usersManagement object and displays the appropriate messages into the GUI.
     */
    class ButtonsListenerRegister implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object event=e.getSource(); registerGUI.setWrongLabelVisible(false,0,true);
            if(event==registerGUI.buttons.get(0)) //save
            {
                int res;
                if((res= usersManagement.checkRegister(registerGUI.fields.get(0).getText(), registerGUI.fields.get(1).getText(), registerGUI.fields.get(2).getText(), registerGUI.fields.get(3).getText(), registerGUI.fields.get(4).getText(), registerGUI.fields.get(5).getText(), registerGUI.fields.get(6).getText() ))!=-1)
                    registerGUI.setWrongLabelVisible(true, res,false);
                else
                    mainFrame.setPanel(4);

            }
            else if(event==registerGUI.buttons.get(1))//back
            {
                mainFrame.setPanel(0);
            }
        }
    }
}

