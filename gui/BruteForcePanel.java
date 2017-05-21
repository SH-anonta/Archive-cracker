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
import utility.CharacterSelection;
import utility.SerializableObjectReader;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;

public class BruteForcePanel extends JPanel{
    //checkbox section
    private JCheckBox numbers_check;
    private JCheckBox lower_alphas_check;
    private JCheckBox upper_alphas_check;
    private JCheckBox special_symbols_check;
    private JCheckBox space_check;

    //custom charecters to be used in the brute force are entered here
    private JLabel custom_char_label;
    private JTextField custom_char_txtfield;

    //this used to find which combination of characters to use
    private CharacterSelection character_set;

    //text fields
    private JTextField range_from;
    private JTextField range_to;
    private JLabel password_combo_count_label;

    private JLabel tried_password_count;

    //button for loading previously saved bruteforce job progresscheck
    private JButton loadProgress_button;

    //loaded Bruteforce progress and  are stored here
    private BruteForceJob loaded_bruteforce_Job;

    //initialize the checkboxes
    private void setupCheckboxes(){
        numbers_check = new JCheckBox("Numbers (0 - 9)");
        lower_alphas_check = new JCheckBox("alphabets (a - z)");
        upper_alphas_check = new JCheckBox("ALPHABETS (A - Z)");
        special_symbols_check = new JCheckBox("Special symbols (!@#...)");
        space_check = new JCheckBox("Space ( )");
    }

    //this sets up an action listener which listens to all check boxes and textfield
    //and updates the password_combo_count_label label for any change
    //used by ActionListener of the checkboxes and text fielsd
    private void updatePassword_combo_count_label(){
        this.resetCurrentJob();

        //if input is invalid set total password cout to 0
        if(validateInput() == false){
            password_combo_count_label.setText("Passwords: 0");
        }
        getCharacterSelection();   //keep selected char set value

        password_combo_count_label.setText("Passwords: " + character_set.getPossiblePasswordCount());
    }


    //setus up the actionlistener for checkboxes and textfields
    //then adds the actionlistener to checkboxes and textfields
    private void setupPasswordCountUpdater(){
        ActionListener checkbox_listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePassword_combo_count_label();
            }
        };

        DocumentListener text_field_listener= new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePassword_combo_count_label();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePassword_combo_count_label();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePassword_combo_count_label();
            }
        };

        //listen to all field users can enter to
        this.range_from.addActionListener(checkbox_listener);
        this.range_to.addActionListener(checkbox_listener);
        this.custom_char_txtfield.addActionListener(checkbox_listener);
        this.numbers_check.addActionListener(checkbox_listener);
        this.lower_alphas_check.addActionListener(checkbox_listener);
        this.upper_alphas_check.addActionListener(checkbox_listener);
        this.special_symbols_check.addActionListener(checkbox_listener);
        this.space_check.addActionListener(checkbox_listener);


        this.custom_char_txtfield.getDocument().addDocumentListener(text_field_listener);
        this.range_from.getDocument().addDocumentListener(text_field_listener);
        this.range_to.getDocument().addDocumentListener(text_field_listener);
    }

    BruteForcePanel(){
        tried_password_count = new JLabel("Tried Passwords: 0");

        character_set = new CharacterSelection();
        this.setupCheckboxes();
        this.setupButtons();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel checkbox_label  = new JLabel("Select characters sets:");

        //initializes the other components
        custom_char_label= new JLabel("Custom charecters: (provide charecters to use)");
        custom_char_txtfield = new JTextField(60);
        custom_char_txtfield.setMaximumSize(custom_char_txtfield.getPreferredSize());

        JLabel password_length_label = new JLabel("Password length: (from - to)");
        range_from = new JTextField(6);
        range_from.setMaximumSize(range_from.getPreferredSize());
        range_to = new JTextField(6);
        range_to.setMaximumSize(range_to.getPreferredSize());

        password_combo_count_label = new JLabel("Passwords: 0");
        setupPasswordCountUpdater();

        int border_len= 10;
        this.setBorder(BorderFactory.createEmptyBorder(border_len,border_len,border_len,border_len));

        //password lenght range input textfiels (from and to)
        this.add(password_length_label);
        this.add(Box.createVerticalStrut(10));
        this.add(range_from);
        this.add(range_to);

        //adding the checkboxes for choosing charecter sets
        this.add(Box.createVerticalStrut(20));
        this.add(checkbox_label);
        this.add(numbers_check);
        this.add(lower_alphas_check);
        this.add(upper_alphas_check);
        this.add(special_symbols_check);
        this.add(space_check);

        //custom charecters input text field and descriptive label
        this.add(Box.createVerticalStrut(20));
        this.add(custom_char_label);
        this.add(custom_char_txtfield);

        //Possible passwords count label
        this.add(Box.createVerticalStrut(20));
        this.add(password_combo_count_label);

        this.add(Box.createVerticalStrut(10));

        this.add(tried_password_count);
        this.add(Box.createVerticalStrut(10));
        this.add(this.loadProgress_button);
    }

    private void setupButtons(){
        loadProgress_button = new JButton("Load Progress");

        loadProgress_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                loaded_bruteforce_Job = (BruteForceJob) SerializableObjectReader.readObjectFromFile();
                loadBruteForceJob((BruteForceJob)SerializableObjectReader.readObjectFromFile());
                if(loaded_bruteforce_Job == null){
                    return;
                }

                setTriedPasswordCount(""+ loaded_bruteforce_Job.getTriedPasswordsCount());
            }


        });
    }

    //checks if all user inputs in this panel are valid
    //returns true if everything is ok else returns flase
    public boolean validateInput(){
        getCharacterSelection();   //keep selected char set value

        //only check the two text fields (range_from and range_to)
        //check if both contain valid positive itegers
        int from, to;
        try{
            from= Integer.parseInt(range_from.getText());
            to= Integer.parseInt(range_to.getText());
        }
        catch (NumberFormatException ex){
            return false;
        }

        //if range lower limit is greater than upper limit
        if(from > to){
            return false;
        }

        if(from <1 || to < 1){
            return false;
        }


        //get total charecters to be used to find password
        int total_chars= character_set.getTotalCharCount();

        //if all input is valid
        return true;
    }


    CharacterSelection getCharacterSelection(){
        character_set.reset();

        if(numbers_check.isSelected()){
            character_set.numbers= true;
        }
        if(lower_alphas_check.isSelected()){
            character_set.alphabets= true;
        }
        if(upper_alphas_check.isSelected()){
            character_set.ALPHABETS= true;
        }
        if(special_symbols_check.isSelected()){
            character_set.special_symbols= true;
        }
        if(space_check.isSelected()){
            character_set.space= true;
        }

        //new method
        character_set.custom_charecters= custom_char_txtfield.getText();

        try{
            character_set.pass_length_from= Integer.parseInt(range_from.getText());
            character_set.pass_length_to= Integer.parseInt(range_to.getText());
        }
        catch (NumberFormatException ex){
            //do nothing
        }

        return character_set;
    }

    //method used by engine controller to get the brutefoce input
    public BruteForceJob getBruteForceJob(){
        if(loaded_bruteforce_Job == null){
            loaded_bruteforce_Job = new BruteForceJob(getCharacterSelection());
        }

        return loaded_bruteforce_Job;
    }

    void loadBruteForceJob(BruteForceJob job){
        loadCharacterSelections(job.getCharSet());
        loadCharacterSelections(job.getCharSet());
        loaded_bruteforce_Job = job;
    }

    private void loadCharacterSelections(CharacterSelection set){
        this.lower_alphas_check.setSelected(set.alphabets);
        this.upper_alphas_check.setSelected(set.ALPHABETS);
        this.numbers_check.setSelected(set.numbers);
        this.special_symbols_check.setSelected(set.special_symbols);
        this.space_check.setSelected(set.space);

        this.range_from.setText(""+set.pass_length_from);
        this.range_to.setText(""+set.pass_length_to);

        this.custom_char_txtfield.setText(set.custom_charecters);
    }

    private void resetCurrentJob(){
        this.loaded_bruteforce_Job = null;
        this.setTriedPasswordCount("Passwords tried: 0");
    }

    private void setTriedPasswordCount(String s) {
        tried_password_count.setText("Passwords tried: " + s);

    }
}
