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
import utility.CharacterSelection;

import java.math.BigInteger;

//Starts
public class BruteForceEngine extends PasswordEngine {
    private char[] char_array;
    private int charset_len;
    private boolean found_flag;
    private CharacterSelection character_set;
    private StringBuilder current_pass;

    //these instances share the bruteforceJob attributes
    private BruteForceJob current_job;
    private int current_password_length;
    private int[] progress;

    public BruteForceEngine(BruteForceJob bf_job, StatusPanel status_panel){
        this.status_panel= status_panel;

        this.current_job = bf_job;

        this.character_set= bf_job.getCharSet();
        this.char_array = character_set.buildCharacterArray();
        this.charset_len = character_set.getTotalCharCount();
        this.progress = bf_job.getProgress();

        current_pass= new StringBuilder("");
    }

    private void allPerm(int depth){

        //if the password has been found
        //and the engine has been requested to stop
        //stop trying new passwords and return
        if(found_flag == true || keep_running_flag == false){

            return;
        }

        //if the depth of recursion tree is equal to currently trying password length
        if(depth == current_password_length){
            String pass = current_pass.toString();

            updateCurrentPassStatus(pass);
            if(target_archive.tryPassword(pass)){
                found_flag= true;                       //password found
                found_result= current_pass.toString();
            }
            return;
        }

        for(int i= progress[depth]; i<charset_len; i++){

            //stopping the recursive function right here is very improtant
            //for keeping progress state
            if(!keep_running_flag) return;

            current_pass.append(char_array[i]);

            allPerm(depth+1);
            current_pass.deleteCharAt(current_pass.length()-1);

            if(keep_running_flag) progress[depth]++;
        }

        //this check is important for saving the progress of this job
        //this makes sure the progress is resetted only if all chuild nodes are visited
        if(keep_running_flag) progress[depth]= 0;
    }

    void crack(){
        found_flag= false;
        int from = current_job.getCurrent_password_length();
        int to = character_set.pass_length_to;

        //try all possible permutations using IDDFS
        for(int i= from; i<= to; i++){
            //if the password engine is requested to stop
            //stop and return
            if(!keep_running_flag){
                return;
            }

            current_pass = new StringBuilder("");

            //generates all perutations of charecters in char_set and tries them ass password
            current_password_length= i;
            allPerm(0);

            //all possible passwords of length i have been tried,
            //move progress to i+1 length passwords
            //this check is important for saving the progress and not moving
            //to the i+1 length passwords without trying all passwords of length i
            if(keep_running_flag) current_job.updateCurrentPasswordLength();

        }

    }

    @Override
    protected void startAttack(){
        //this does the actual cracking

//        System.out.println("Loading job");
//        current_job.printJob();

        crack();

        current_job.setTriedPassowrds(status_panel.getTriedPasswordCount().subtract(BigInteger.ONE));
    }

}
