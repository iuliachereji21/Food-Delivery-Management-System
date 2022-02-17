package org.ro.tuc.pt.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The class extends JPanel. It will contain the necessary swing components for performing the register operation.
 * @author Chereji Iulia
 */
public class RegisterGUI extends JPanel{
    /** the list of buttons in the view. */
    public ArrayList<JButton> buttons;
    /** the list of text fields in the view. */
    public ArrayList<JTextField> fields;
    /** the list of labels which will be used for showing error messages */
    public ArrayList<JLabel> wrongLabels;

    /**
     * Creates a new instance of RegisterGUI.
     * @param height height of the panel
     * @param width width of the panel
     */
    public RegisterGUI(int height, int width)
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

        buttons.get(0).setText("SAVE");
        buttons.get(0).setBounds(1250,693, 150, 50);
        buttons.get(1).setText("BACK");
        buttons.get(1).setBounds(50, 50, 150, 50);

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

        dataLabels[0].setText("*Username:");
        dataLabels[0].setBounds(400,220,250,30);
        dataLabels[1].setText("Date of birth:");
        dataLabels[1].setBounds(400,300,250,30);
        dataLabels[2].setText("Address");
        dataLabels[2].setBounds(400,380,250,30);
        dataLabels[3].setText("Phone number:");
        dataLabels[3].setBounds(400,460,250,30);
        dataLabels[4].setText("*Email:");
        dataLabels[4].setBounds(400,540,250,30);
        dataLabels[5].setText("*Password:");
        dataLabels[5].setBounds(400,620,250,30);
        dataLabels[6].setText("*Confirm password:");
        dataLabels[6].setBounds(400,700,250,30);
        dataLabels[7].setText("Register");

        dataLabels[7].setBounds(350,100,500,70);
        dataLabels[7].setFont(new Font("TimesRoman",20,60));


        //nrFields=7;
        fields = new ArrayList<>();

        for(int i=0;i<5;i++)
        {
            JTextField field=new JTextField();
            field.setFont(new Font("TimesRoman",20,25));
            field.setForeground(Color.WHITE);
            field.setOpaque(false);
            this.add(field);
            fields.add(field);
        }

        for(int i=5;i<7;i++)
        {
            JPasswordField jPasswordField= new JPasswordField();
            jPasswordField.setFont(new Font("TimesRoman",20,25));
            jPasswordField.setForeground(Color.WHITE);
            jPasswordField.setOpaque(false);
            this.add(jPasswordField);
            fields.add(jPasswordField);
        }

        fields.get(0).setBounds(650,215,500,45);
        fields.get(1).setBounds(650,295,500,45);
        fields.get(2).setBounds(650,375,500,45);
        fields.get(3).setBounds(650,455,500,45);
        fields.get(4).setBounds(650,535,500,45);
        fields.get(5).setBounds(650,615,500,45);
        fields.get(6).setBounds(650,695,500,45);

        //nrWrongLabels=8;
        wrongLabels=new ArrayList<>();

        for(int i=0;i<8;i++)
        {
            JLabel wrongLabel;
            wrongLabel=new JLabel();
            wrongLabel.setFont(new Font("TimesRoman",20,20));
            wrongLabel.setForeground(Color.RED);
            this.add(wrongLabel);
            wrongLabels.add(wrongLabel);
            wrongLabel.setVisible(true);
        }

        wrongLabels.get(0).setText("*please introduce a username");
        wrongLabels.get(0).setBounds(650,260,440,30);
        wrongLabels.get(1).setText("*username already exists");
        wrongLabels.get(1).setBounds(650,260,440,30);
        wrongLabels.get(2).setText("*please introduce a valid date");
        wrongLabels.get(2).setBounds(650,340,440,30);
        wrongLabels.get(3).setText("*please introduce a valid phone number");
        wrongLabels.get(3).setBounds(650,500,440,30);
        wrongLabels.get(4).setText("*please introduce a valid email");
        wrongLabels.get(4).setBounds(650,580,440,30);
        wrongLabels.get(5).setText("*there exists an account with this email");
        wrongLabels.get(5).setBounds(650,580,440,30);
        wrongLabels.get(6).setText("*please introduce a password");
        wrongLabels.get(6).setBounds(650,660,440,30);
        wrongLabels.get(7).setText("*passwords don't match");
        wrongLabels.get(7).setBounds(650,740,440,30);

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
