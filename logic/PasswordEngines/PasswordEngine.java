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


import ArchiveTypes.Archive;
import ArchiveTypes.ArchiveUtility;
import ArchiveTypes.ArchiveZip;
import gui.StatusPanel;
import utility.TempFolder;

import javax.annotation.processing.FilerException;
import java.io.File;
import java.io.IOException;

public abstract class PasswordEngine implements Runnable {
    protected Archive target_archive;
    protected String found_result;

    protected String extract_to;              //path where to extract to
    protected StatusPanel status_panel;       //the panel where the results will be shown


    //flag that keeps the thread/engine running,
    //setting it false stops the engine from trying any more passwords
    //NOTE: this flag must be checked by it's subclasses everytime trying a new password
    //and stop trying new passwords otherwise the engine will not stop
    protected boolean keep_running_flag;

    PasswordEngine(){
        found_result= null;
        //get temp folder direcotry for extracting file
        extract_to= TempFolder.getDirectory();
        keep_running_flag= true;
    }

    PasswordEngine(StatusPanel status_panel){
        this.status_panel = status_panel;
        found_result= null;
        keep_running_flag= true;

        extract_to= TempFolder.getDirectory();
    }

    @Override
    public void run() {
        //no password found initially
        found_result= null;

        startAttack();

        //show found result, null is passed if password was not found
        status_panel.showResult(found_result);

        TempFolder.cleanUp();
    }


    protected void updateCurrentPassStatus(String current_pass){
        status_panel.updateTriedPasswordCount(current_pass);
    }
    public String getFound_result() {
        return found_result;
    }

    //pass any type of archive file and use it as a target file
    public void setTargetFile(File file){
        try {
            target_archive = ArchiveUtility.fileToArchive(file);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }


    public void stopEngine(){
        //setting this flag false will make the engine stop
        //the flag checking is implemented in subclasses
        keep_running_flag= false;
    }

    //This method will initiate the attack
    abstract protected void startAttack();
}
