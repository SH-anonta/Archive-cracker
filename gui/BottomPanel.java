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

import logic.PasswordEngines.EngineController;
import logic.TabState;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//this panel contains the buttons for starting pausing and stopping operation

public class BottomPanel extends JPanel{
    //reference to parent panel/frame
    private MainScreen parent_ref;

    //this class has to communicatate with other panels
    private DictionaryPanel dictionary_panel;
    private BruteForcePanel bruteforce_panel;
    private StatusPanel status_panel;
    private JTabbedPane center_tabbed_pane;
    private FileChooserPanel file_chooser_panel;

    //buttons
    private JButton start_button;
    private JButton stop_button;

    //class for creating threads and carrying out attacks
    private EngineController cracker;

    //simply gets reference of other panels from the parent/main panel
    private void getOtherPanels(){
        this.center_tabbed_pane = parent_ref.getCenter_tabbed_panel();
        this.dictionary_panel= parent_ref.getCenter_panel_dic();
        this.bruteforce_panel= parent_ref.getCenter_panel_brute();
        this.status_panel = parent_ref.getCenter_panel_status();
        this.file_chooser_panel= parent_ref.getTop_panel_filechooser();
    }

    public BottomPanel(MainScreen parent){
        this.parent_ref = parent;

        getOtherPanels();
        setupAttackEngines();
        setupGUI();
    }

    private void setupGUI(){
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        initializeButtons();
        this.add(start_button);
//        this.add(pause_button);
        this.add(stop_button);
    }

    private void setupAttackEngines(){
        cracker = new EngineController(status_panel);
    }

    //initiates an attack depending on the application's mode
    private void startAttack(){
        //updateAppmodeByTabLocation is called to update tabstate (which tab is user at)
        updateAppmodeByTabLocation();
        if(TabState.getTabState() == TabState.DICTIONARY_TAB){
            //ask the other panels to check input validatation
            if(file_chooser_panel.validateInput() && dictionary_panel.validateInput()){
                parent_ref.switchMidPanel(TabState.STATUS_TAB);
                cracker.startDictionaryAttack(file_chooser_panel.getTargetFile(),dictionary_panel.getChosen_target_file());
            }
            else{
                //input was not valid
                invalidInputErrorMessage();
            }
        }
        else if(TabState.getTabState() == TabState.BRUTEFORCE_TAB){
            if(file_chooser_panel.validateInput() && bruteforce_panel.validateInput()){
                parent_ref.switchMidPanel(TabState.STATUS_TAB);
                cracker.startBruteForceAttack(bruteforce_panel.getBruteForceJob(), file_chooser_panel.getTargetFile());
            }
            else{
                //input was not valid
                invalidInputErrorMessage();
            }
        }
        else if(TabState.getTabState() == TabState.STATUS_TAB){

        }
    }

    //implement start, pause and stop button's action listeners
    private void initializeButtons(){

        start_button = new JButton("Start");
        start_button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                startAttack();
            }

        });

        stop_button = new JButton("Stop");
        stop_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                cracker.stopAttack();
            }

        });

    }

    void invalidInputErrorMessage(){
        //show msg dialog saying the input was invalid
        JOptionPane.showMessageDialog(parent_ref, "Invalid input or unprotected Archive file!");
    }

    //this method updates status according to current tab shown at the center pane
    void updateAppmodeByTabLocation(){
        int current_tab_index= center_tabbed_pane.getSelectedIndex();

        //these numbers are tab indexes, according to their order of insertion
        if(current_tab_index == 0){
            TabState.updateTabState(TabState.BRUTEFORCE_TAB);
        }
        else if(current_tab_index == 1){
            TabState.updateTabState(TabState.DICTIONARY_TAB);
        }
        else if(current_tab_index == 2){
            TabState.updateTabState(TabState.STATUS_TAB);
        }
    }


}
