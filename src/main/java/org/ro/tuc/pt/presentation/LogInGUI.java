package org.ro.tuc.pt.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The class extends JPanel. It will contain the necessary swing components for performing the log in operation.
 * @author Chereji Iulia
 */
public class LogInGUI extends JPanel {
    /** the list of buttons in the view. */
    public ArrayList<JButton> buttons;
    /** the list of text fields in the view. */
    public ArrayList<JTextField> fields;
    /** the list of labels which will be used for showing error messages */
    public ArrayList<JLabel> wrongLabels;

    /**
     * Creates a new instance of LogInGUI.
     * @param height height of the panel
     * @param width width of the panel
     */
    public LogInGUI(int height, int width)
    {
        super();
        this.setBounds(0,0, height, width);
        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);
        buttons=new ArrayList<>();
        //nr buttons = 2
        for(int i=0;i<2;i++)
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

        buttons.get(0).setText("LOG IN");
        buttons.get(0).setBounds(950,530, 150, 50);
        buttons.get(1).setText("REGISTER");
        buttons.get(1).setBounds(600, 530, 150, 50);

        int nrDataLabels = 3;
        JLabel[] dataLabels = new JLabel[nrDataLabels];
        for(int i = 0; i< nrDataLabels; i++)
        {
            dataLabels[i]=new JLabel();
            dataLabels[i].setFont(new Font("TimesRoman",20,25));
            dataLabels[i].setForeground(Color.WHITE);
            this.add(dataLabels[i]);
            dataLabels[i].setVisible(true);
        }

        dataLabels[0].setText("Username:");
        dataLabels[0].setBounds(400,330,150,30);
        dataLabels[1].setText("Password:");
        dataLabels[1].setBounds(400,410,150,30);
        dataLabels[2].setText("Log in");
        dataLabels[2].setBounds(350,150,500,70);
        dataLabels[2].setFont(new Font("TimesRoman",20,60));

        //nrFields=2;
        fields = new ArrayList<>();

        JTextField field=new JTextField();
        field.setFont(new Font("TimesRoman",20,25));
        field.setForeground(Color.WHITE);
        field.setOpaque(false);
        this.add(field);
        fields.add(field);

        JPasswordField jPasswordField= new JPasswordField();
        jPasswordField.setFont(new Font("TimesRoman",20,25));
        jPasswordField.setForeground(Color.WHITE);
        jPasswordField.setOpaque(false);
        this.add(jPasswordField);
        fields.add(jPasswordField);

        fields.get(0).setBounds(600,325,500,45);
        fields.get(1).setBounds(600,405,500,45);

        //nrWrongLabels=2;
        wrongLabels=new ArrayList<>();

        for(int i=0;i<2;i++)
        {
            JLabel wrongLabel;
            wrongLabel=new JLabel();
            wrongLabel.setFont(new Font("TimesRoman",20,20));
            wrongLabel.setForeground(Color.RED);
            this.add(wrongLabel);
            wrongLabels.add(wrongLabel);
            wrongLabel.setVisible(false);
        }

        wrongLabels.get(0).setText("*wrong username or password");
        wrongLabels.get(0).setBounds(600,450,440,30);
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
