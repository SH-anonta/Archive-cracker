/*
Copyright 2017 Sofen Hoque Anonta

This file is part of Archive cracker.

Archive cracker is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License version 3 as published by
the Free Software Foundation.

Archive cracker is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License version 3
along with Archive cracker.  If not, see <http://www.gnu.org/licenses/>.
*/

package gui;


import logic.PasswordEngines.BruteForceJob;
import utility.BigIntCount;
import utility.SerializableObjectWriter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

//this is a singelton class
//this panel shows status of running process
public class StatusPanel extends JPanel{
    private JLabel total_pass_count_val_label;  //total passwords to be tried
    private JLabel tried_pass_count_val_label;  //passwords tried so far
    private JLabel trying_password_val_label;   //currnet passwod being tried


    private BigIntCount tried_pass_count;
    private JLabel result_label;                //shows whather password was found or not
    private JLabel result_password_label;       //shows found password, empty if pass not found

    private static final Color PASS_NOT_FOUND_TEXT_COLOR= new Color(0x9E,0x0,0x31);
    private static final Color PASS_FOUND_TEXT_COLOR= new Color(0x00, 0xA5, 0x42);

    private BruteForceJob current_bruteforce_Job;
    //buttons
    private JButton saveProgress_button;

    private void setupLabels(){
        int min_label_width= 50;
        int min_label_height= 10;

        result_label= new JLabel("");
        total_pass_count_val_label= new JLabel("0");
        total_pass_count_val_label.setMinimumSize(new Dimension(min_label_width, min_label_height));

        tried_pass_count_val_label= new JLabel("0");
        tried_pass_count_val_label.setMinimumSize(new Dimension(min_label_width, min_label_height));

        trying_password_val_label= new JLabel("");
        trying_password_val_label.setMinimumSize(new Dimension(min_label_width, min_label_height));


        result_password_label = new JLabel("");
    }

    public StatusPanel(){
        tried_pass_count= new BigIntCount("0");
        setupGui();
    }

    private void setupButtons(){
        saveProgress_button = new JButton("Save Progress");

        saveProgress_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SerializableObjectWriter.saveObjectToFile(current_bruteforce_Job, BruteForceJob.FILE_EXTENSION);
            }

        });
    }


    private void setupGui(){
        //setting up the panel
        this.setupButtons();

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,0));

        //Constant labels which dont need to be accessecd later
        JLabel total_pass_count_label = new JLabel("Total Passwords:   ");
        JLabel tried_pass_count_label = new JLabel("Passwords tried: ");
        JLabel program_status_label = new JLabel("Status: ");
        JLabel trying_password_label= new JLabel("Trying: ");

        //initialize labels
        setupLabels();

        GridBagConstraints c = new GridBagConstraints();

        //Entering the components of 1st column
        c.gridx= 0;
        c.gridy= 0;

        c.weightx= 0;
        c.ipady= 5;

        c.anchor= GridBagConstraints.LINE_START;
        this.add(total_pass_count_label, c);    //row 1
        c.gridy++;
        this.add(tried_pass_count_label, c);    //row 2
        c.gridy++;
        this.add(trying_password_label,c);      //row 3
        c.gridy++;
        this.add(result_label, c);              //row 4
        c.gridy++;
        this.add(saveProgress_button, c);       //row 5

        //Done Entering the components of 1st column

        //Entering the components of 2st column
        c.gridx= 1;
        c.gridy= 0;
        c.anchor= GridBagConstraints.LINE_END;

        c.fill = GridBagConstraints.HORIZONTAL;

        this.add(total_pass_count_val_label, c);     //row 1
        c.gridy++;
        this.add(tried_pass_count_val_label, c);     //row 2
        c.gridy++;
        this.add(trying_password_val_label, c);     //row 3
        c.gridy++;
        this.add(result_password_label, c);     //row 4


        //Done Entering the components of 2nd column
    }

    //when attack finishes, show result
    public void showResult(String result){
        //cracking attempt faild
        if(result == null){
            this.result_label.setText("password not found");
            this.result_label.setForeground(PASS_NOT_FOUND_TEXT_COLOR);
            result_password_label.setText("");
        }
        else{
            this.result_label.setText("Password found: ");
            this.result_password_label.setText(result);

            this.result_label.setForeground(PASS_FOUND_TEXT_COLOR);
            this.result_password_label.setForeground(PASS_FOUND_TEXT_COLOR);
        }
    }

    //increments the tried count by 1
    public void updateTriedPasswordCount(String trying_pass){
        tried_pass_count.incrementByOne();
        tried_pass_count_val_label.setText(""+ tried_pass_count);
        trying_password_val_label.setText(trying_pass);
    }

    public void setTotalPassCount(long total_pass_count) {
        this.total_pass_count_val_label.setText(""+ total_pass_count);
    }

    public void setTotalPassCount(BigInteger total_pass_count) {
        this.total_pass_count_val_label.setText(""+ total_pass_count.toString());
    }

    //reset status values and labels so another attack can started
    public void resetStatus(){
        this.total_pass_count_val_label.setText("0");
        this.tried_pass_count.reset();
        this.tried_pass_count_val_label.setText("0");
        this.trying_password_val_label.setText("");

        this.result_label.setText("");
        result_password_label.setText("");
    }

    public void setBruteForceJob(BruteForceJob job){
        this.current_bruteforce_Job= job;
    }

    public void setTriedPasswordCount(BigInteger count) {
        tried_pass_count = new BigIntCount(""+count);
    }

    public BigInteger getTriedPasswordCount(){
        return new BigInteger(""+tried_pass_count);
    }
}
