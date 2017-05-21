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

import gui.StatusPanel;
import utility.FileInfoProvider;
import java.io.File;

//a class that knows how to run a bruteforce and dictionary attack on an archive file
//creates and maintaines threads of runnable passwordEngine classes
public class EngineController {
    private boolean engine_running;
    private StatusPanel status_panel;
    private PasswordEngine currentEngine;
    private Thread attack_thread;
    private BruteForceJob current_bruteforce_job;   //only works with burteforce engine


    public EngineController(StatusPanel status_panel){
        this.status_panel = status_panel;
        engine_running= false;
    }

    public void startDictionaryAttack(File zipfile, File dictionary){
        status_panel.resetStatus();
        status_panel.setTotalPassCount(FileInfoProvider.getLinesInFile(dictionary));
        currentEngine= new DictionaryEngine(zipfile, dictionary,status_panel);

        startAttack();
    }

    public void startBruteForceAttack(BruteForceJob job, File target_file){
        status_panel.resetStatus();

        status_panel.setTotalPassCount(job.getCharSet().getPossiblePasswordCount());

        current_bruteforce_job = job;
        status_panel.setBruteForceJob(job);
        currentEngine= new BruteForceEngine(current_bruteforce_job, status_panel);

        status_panel.setTriedPasswordCount(job.getTriedPasswordsCount());

        currentEngine.setTargetFile(target_file);

        startAttack();
    }

    //the (2) callers setup the objects needed for attack
    //the common statements in the two callers go here
    private void startAttack(){
        attack_thread= new Thread(currentEngine);
        attack_thread.setPriority(Thread.MAX_PRIORITY);
        attack_thread.start();
    }

    //terminate the thread
    public void stopAttack(){
        // if the current thread is running stop it
        if(attack_thread != null && attack_thread.isAlive()){
            currentEngine.stopEngine();
            attack_thread= null;
            currentEngine= null;
        }
    }

}
