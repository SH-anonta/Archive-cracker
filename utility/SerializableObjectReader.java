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

import javax.swing.*;
import java.io.*;

public class SerializableObjectReader {
    //intentionally private
    private SerializableObjectReader(){}

    public static Object readObjectFromFile(){

        String read_from= selectFileToReadFrom();

        //if no file was chosen
        if(read_from == null) return null;

        return getObjectFromFile(read_from);
    }


    private static String selectFileToReadFrom(){
        JFileChooser chooser = new JFileChooser();

        int val = chooser.showOpenDialog(MainClass.getMainFrame());

        //no file was chosen
        if(val != JFileChooser.APPROVE_OPTION) return null;

        return chooser.getSelectedFile().getAbsolutePath();
    }

    private static Object getObjectFromFile(String get_from){
        try{
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(get_from));

            try{
                return reader.readObject();
            }
            catch (ClassNotFoundException ex){
                ex.printStackTrace();
            }

        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        //file read faild
        return null;

    }
}
