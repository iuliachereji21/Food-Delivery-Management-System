package org.ro.tuc.pt.presentation;

import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The class extends JPanel and implements Obsrved. It will contain the necessary swing components for performing employee operations
 * (see new placed orders).
 * @author Chereji Iulia
 */
public class EmployeeGUI extends JPanel implements Observer {
    /** the list of buttons in the view. */
    public ArrayList<JButton> buttons;
    private JTextArea ordersDisplay;

    /**
     * Creates a new instance of EmployeeGUI.
     * @param height height of the panel
     * @param width width of the panel
     */
    public EmployeeGUI(int height, int width)
    {
        super();
        this.setBounds(0,0, height, width);
        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);
        buttons=new ArrayList<>();
        //nr buttons = 1
        for(int i=0;i<1;i++)
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

        int nrDataLabels = 2;
        JLabel[] dataLabels = new JLabel[nrDataLabels];
        for(int i = 0; i< nrDataLabels; i++)
        {
            dataLabels[i]=new JLabel();
            dataLabels[i].setFont(new Font("TimesRoman",20,25));
            dataLabels[i].setForeground(Color.WHITE);
            this.add(dataLabels[i]);
            dataLabels[i].setVisible(true);
        }

        dataLabels[0].setText("Employee");
        dataLabels[0].setBounds(50,50,500,70);
        dataLabels[0].setFont(new Font("TimesRoman",20,60));
        dataLabels[1].setText("Orders:");
        dataLabels[1].setBounds(50,150, 150, 50);

        JPanel scrollPanel = new JPanel();
        scrollPanel.setBackground(Color.DARK_GRAY);
        scrollPanel.setBounds(50,200,1300,600);
        scrollPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        this.add(scrollPanel);

        Border border = BorderFactory.createEmptyBorder();
        ordersDisplay= new JTextArea(10,30);
        ordersDisplay.setBorder(border);
        ordersDisplay.setFont(new Font("TimesRoman",20,25));
        ordersDisplay.setText("");
        ordersDisplay.setLineWrap(true);
        ordersDisplay.setWrapStyleWord(true);
        ordersDisplay.setForeground(Color.WHITE);
        ordersDisplay.setBackground(Color.DARK_GRAY);

        JScrollPane scrollPane = new JScrollPane(ordersDisplay, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(1298,590));
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
     * Updates the panel when a new order is made.
     * @param o the object that triggered the update
     * @param arg The String containing the new order that was performed and that has to be added to the ordersDisplay.
     */
    @Override
    public void update(Observable o, Object arg) {
        ordersDisplay.setText(ordersDisplay.getText()+(String) arg+"\n\n\n");
    }
}

