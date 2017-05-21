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

import ArchiveTypes.Archive;
import ArchiveTypes.ArchiveUtility;
import utility.ArchiveFactory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FileChooserPanel extends JPanel{
    private JLabel label_choose_zip;
    private JFileChooser zip_fileChooser;
    private JButton choose_zip_button;
    private File chosen_zip;

    private JLabel chosen_zip_name_label;

    private void initializeButtons(){
        choose_zip_button = new JButton("Choose zip");

        choose_zip_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnval = zip_fileChooser.showOpenDialog(null);
//                System.out.println("AA: "+ returnval);
                if(returnval == JFileChooser.APPROVE_OPTION){
                    chosen_zip= zip_fileChooser.getSelectedFile();
                    chosen_zip_name_label.setText(chosen_zip.getName());
                }
            }
        });

    }

    public FileChooserPanel(){
        int border_len= 10;
        this.setBorder(BorderFactory.createEmptyBorder(border_len,border_len,border_len,border_len));

        label_choose_zip = new JLabel("Choose zip file");
        zip_fileChooser = new JFileChooser("Choose zip");
        FileNameExtensionFilter file_filter = new FileNameExtensionFilter("Zip files", "zip");
        zip_fileChooser.setFileFilter(file_filter);

        initializeButtons();

        chosen_zip_name_label = new JLabel("No file chosen");
        //setup file chooser panel:
        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        this.add(label_choose_zip);

        this.add(Box.createVerticalStrut(10));
        this.add(chosen_zip_name_label);

        this.add(Box.createVerticalStrut(5));
        this.add(choose_zip_button);
    }

    //checks if all user inputs in this panel are valid
    //returns true if everything is ok else returns flase
    public boolean validateInput(){
        //if no zip file was chosen then the input is invalid
        if(chosen_zip == null) return false;

        try{
            Archive temp = ArchiveUtility.fileToArchive(chosen_zip);
            if(temp.isProtected() == false) return false;
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return true;
    }

    public File getTargetFile() {
        return chosen_zip;
    }
}
