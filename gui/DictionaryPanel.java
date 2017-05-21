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

import utility.FileInfoProvider;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class DictionaryPanel extends JPanel{
    private File chosen_target_file;
    private JLabel chosen_file_name_label;
    private JButton choose_file_button;     //lets user choose a dictionary file
    private JFileChooser dictionary_fileChooser;

    private JLabel pass_count_in_file;



    private void initializeButtonListeners(){
        choose_file_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                long returnval = dictionary_fileChooser.showOpenDialog(null);
//                System.out.println("AA: "+ returnval);

                if(returnval == JFileChooser.APPROVE_OPTION){
                    chosen_target_file = dictionary_fileChooser.getSelectedFile();
                    chosen_file_name_label.setText(chosen_target_file.getAbsolutePath());
                    pass_count_in_file.setText("Strings found: " + FileInfoProvider.getLinesInFile(chosen_target_file));
                }

            }
        });
    }

    //sets up dictionary_fileChooser
    void initializeZipFileChooser(){
        dictionary_fileChooser = new JFileChooser("Choose zip");
        FileNameExtensionFilter file_filter = new FileNameExtensionFilter("text files", "txt");
        dictionary_fileChooser.setFileFilter(file_filter);
    }


    DictionaryPanel(){
        int border_len= 10;
        this.chosen_target_file = null;    //no file is chosen

        pass_count_in_file = new JLabel("");

        this.setBorder(BorderFactory.createEmptyBorder(border_len,border_len,border_len,border_len));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel choose_file_label= new JLabel("Choose dictionary: (file containing passwords)");
        JLabel warning_label= new JLabel("Each password should be on a separate line");

        initializeZipFileChooser();

        //file chooser panel:
        chosen_file_name_label = new JLabel("No file Chosen");
        choose_file_button= new JButton("Choose file");

        initializeButtonListeners();

        this.add(choose_file_label);
        this.add(warning_label);

        this.add(Box.createVerticalStrut(20));
        this.add(chosen_file_name_label);
        this.add(choose_file_button);

        this.add(Box.createVerticalStrut(20));
        this.add(pass_count_in_file);
    }

    //checks if all user inputs in this panel are valid
    //returns true if everything is ok else returns flase
    public boolean validateInput(){
        //if no dictionary file was chosen then the input is invalid
        if(chosen_target_file == null) return false;
        return true;
    }

    public File getChosen_target_file() {
        return chosen_target_file;
    }
}
