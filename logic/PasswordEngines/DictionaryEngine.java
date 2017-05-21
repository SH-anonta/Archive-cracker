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

package logic.PasswordEngines;

// given a protected zip file and a dictionary (text file) containing password
//will try all passwords and see if it can open the zip

import gui.StatusPanel;

import java.io.*;


public class DictionaryEngine extends PasswordEngine {
    private File dictionary;


    public DictionaryEngine(File zipfile, File dictionary, StatusPanel status_panel){
        this.dictionary= dictionary;
        this.status_panel = status_panel;
        this.setTargetFile(zipfile);
    }
    //try all passwords in dictionary (text file) and return password if success
    //this function assumes each password is placed in a seperate line
    //returns null if all passwords fail
    public void crack(){

        String current_pass;
        BufferedReader pass_stream;

        try{
            pass_stream= new BufferedReader(new FileReader(dictionary));

            while((current_pass = pass_stream.readLine()) != null){
                //if the password engine is requested to stop
                //stop and return
                if(keep_running_flag == false){
                    return;
                }

                updateCurrentPassStatus(current_pass);
                //if current password worked
                if(target_archive.tryPassword(current_pass) == true){
                    //password worked and found result has the password
                    //the tryPassword method stores the found password in found_result varaible
                    //so not much needs to be done here

                    found_result = current_pass;
                    return;
                }
                else{
                    //password faild, keep trying
                    continue;
                }
            }

        }
        catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        //no password worked
        found_result= null;
    }

    public void setDictionary(File dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    protected void startAttack(){
        //this does the actual cracking
        crack();
    }
}
