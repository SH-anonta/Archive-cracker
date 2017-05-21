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

//This screen contains options for selecting the zip file to crack
//choosing the method for cracking
package gui;

import logic.TabState;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;


public class MainScreen extends JFrame{
    //panels:
    //named according placement in jframe's boxlayout
    private JPanel top_panel;       //holds directory
    private JPanel bottom_panel;

    //center panels
    //this will have card layout
    private JTabbedPane center_tabbed_panel;    //will contain the three panels below

    private DictionaryPanel center_panel_dic;
    private BruteForcePanel center_panel_brute;
    private StatusPanel center_panel_status;

    private FileChooserPanel top_panel_filechooser;

    public MainScreen(){
        this.setupGUI();

    }

    //(constructor code) setup the gui
    private void setupGUI(){
        //basic frame properties
        this.setTitle("Archive cracker");
        this.setBounds(100,100, 400,550);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        //setup panels used in this frame
        this.setupTopPanel();

        setupCenterTabbedPanel();

        //adding content panels

        //setup bootom panel
        bottom_panel = new BottomPanel(this);

        // by default the dictionary panel will be set to center panel


        this.getContentPane().add(BorderLayout.NORTH, top_panel);
        this.getContentPane().add(BorderLayout.CENTER, center_tabbed_panel);
        this.getContentPane().add(BorderLayout.SOUTH, bottom_panel);

        this.setVisible(true);
    }

    private void setupTopPanel(){
        top_panel= new JPanel();
        top_panel.setLayout(new BoxLayout(top_panel, BoxLayout.Y_AXIS));
        top_panel_filechooser = new FileChooserPanel();
        top_panel.add(top_panel_filechooser);
    }

    //this sets up the center panel
    private void setupCenterTabbedPanel(){
        center_tabbed_panel = new JTabbedPane();

        center_tabbed_panel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                TabState.updateTabState(center_tabbed_panel.getSelectedIndex());
//                bottom_panel.updatePanelstate();
            }
        });

        center_panel_dic= new DictionaryPanel();
        center_panel_brute = new BruteForcePanel();
        center_panel_status = new StatusPanel();
        center_tabbed_panel.setVisible(true);

        //the order of insertion is important as the order determines their index
        //which is later usd for switching to
        //this order of insertion is very important!!!
        center_tabbed_panel.addTab("Bruteforce", center_panel_brute);
        center_tabbed_panel.addTab("Dictionary", center_panel_dic);

        //an additional container flowlayout is added
        //so GridbagLayout would'nt put everything in the center
        Container status_container = new JPanel(new FlowLayout(FlowLayout.LEFT));
        status_container.add(center_panel_status);
        center_tabbed_panel.addTab("Status", status_container);
    }

    //switch the center panel to eitehr bruteforce, dictionary or stats panel
    public void switchMidPanel(int mode_code){

        if(mode_code == TabState.DICTIONARY_TAB){
            center_tabbed_panel.setSelectedIndex(1);
        }
        else if(mode_code == TabState.BRUTEFORCE_TAB){
            center_tabbed_panel.setSelectedIndex(0);
        }
        else if(mode_code == TabState.STATUS_TAB){
            center_tabbed_panel.setSelectedIndex(2);
        }
    }

    public void showStatusPanel(){
        center_tabbed_panel.setSelectedIndex(2);
    }


    public DictionaryPanel getCenter_panel_dic() {
        return center_panel_dic;
    }

    public BruteForcePanel getCenter_panel_brute() {
        return center_panel_brute;
    }

    public StatusPanel getCenter_panel_status() {
        return center_panel_status;
    }

    public FileChooserPanel getTop_panel_filechooser() {
        return top_panel_filechooser;
    }

    public JTabbedPane getCenter_tabbed_panel() {
        return center_tabbed_panel;
    }
}
