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

//create and provide a path to temprary directory

import java.io.File;
import java.io.IOException;

public class TempFolder {
    private static java.lang.String temp_directory;

    //intentionally private
    private TempFolder(){}


    //makes request to os to creates a temporary directory
    //then returns the absolute path
    //there can be only one temporary directory for the whole application
    public static java.lang.String getDirectory(){

        //if temporary directory was not created create one
        //if already created just return the path
        if(temp_directory == null) {
            temp_directory= createTempFolder();
        }

        return temp_directory;
    }

    //create a directory in temp storage and return it's absolute path
    private static java.lang.String createTempFolder(){
        try{
            File temp = File.createTempFile("zipCrackerTemp", Long.toString(System.nanoTime()));

            if(!temp.delete()){
                throw new IOException("Could not delete temp file");
            }

            if(!temp.mkdir()){
                throw new IOException("Could not create temp directory");
            }

            return temp.getAbsolutePath();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        //temp folder creation faild...
        //statement will not be reached anyways
        return null;
    }

    // delete everything in the temp directory
    static public void cleanUp(){

        //temp clean up should be run on a seperate thread in case temp file is very large
        //application will become unresponsive
        Runnable temp_cleaner= new Runnable() {
            @Override
            public void run() {
                purgeDirectory(new File(temp_directory));
            }
;        };

        Thread cleaner = new Thread(temp_cleaner);
        cleaner.start();
    }

    //delete given a directory delete everything in it, (even folders and subfolders)
    static private void purgeDirectory(File dir){
        for(File to_delete : dir.listFiles()){
            if(to_delete.isDirectory()) purgeDirectory(to_delete);
            to_delete.delete();
        }
    }
}
