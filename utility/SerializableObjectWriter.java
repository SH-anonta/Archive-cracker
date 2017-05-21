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

package utility;

import Launcher.MainClass;
import com.sun.corba.se.impl.orbutil.ObjectWriter;

import javax.swing.*;
import java.io.*;

public class SerializableObjectWriter {
    //intentionally private
    private SerializableObjectWriter(){}

    //take an object to save and extension to add to the end of the saved file
    public static void saveObjectToFile(Object to_save, String extension){
        String save_to= getSaveToDirectory();

        //save to directory was not selected
        if(save_to == null) return;

        if(!save_to.endsWith(".prog")){
            save_to = new String(save_to + extension);
        }
        writeObject(to_save, save_to);
    }

    //show save to dialog and get the absolute directory of where to save file
    private static String getSaveToDirectory(){
        JFileChooser chooser = new JFileChooser();

        int val= chooser.showSaveDialog(MainClass.getMainFrame());

        //if save faild
        if(val != JFileChooser.APPROVE_OPTION){
            return null;
        }

        return chooser.getSelectedFile().getAbsolutePath();
    }

    //given an object and a path save that object in that path
    private static void writeObject(Object to_save_obj, String save_to){

        try{
            ObjectOutputStream writer= new ObjectOutputStream(new FileOutputStream(save_to));

            writer.writeObject(to_save_obj);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
