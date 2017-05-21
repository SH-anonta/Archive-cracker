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

import utility.CharacterSelection;

import java.io.Serializable;
import java.math.BigInteger;

public class BruteForceJob implements Serializable {
    public static final String FILE_EXTENSION = ".prog";

    //array for keeping track of current path used in recursive function
    private int[] progress;
    private int current_password_length;
    private BigInteger tried_passwords_count;

    private CharacterSelection char_set;   //number of characters used for generating permutation

    public BruteForceJob(CharacterSelection charset){
        this.char_set = charset;
        this.current_password_length= char_set.pass_length_from;
        //initially all should be false
        progress = new int[charset.pass_length_to];
        tried_passwords_count = BigInteger.ZERO;
    }

    //the array's refereance is sent intentionally
    //the bruteforce engine class must work the same object
    public int[] getProgress(){
        return progress;
    }

    public int getCurrent_password_length() {
        return current_password_length;
    }

    public CharacterSelection getCharSet() {
        return char_set;
    }

    public BigInteger getTriedPasswordsCount(){
        return tried_passwords_count;
    }

    void updateCurrentPasswordLength(){
        current_password_length++;
    }


    //for debugging
    public void printJob(){
        char_set.printAllChars();

        System.out.println("Progress :");
        int j= 0;
        for(int i : progress){
            System.out.println(j +": "+i);
            j++;
        }
        System.out.println("current pass len: "+ current_password_length);
    }

    public void setTriedPassowrds(BigInteger count){
        //deep copy the givem big int reference
        this.tried_passwords_count= new BigInteger(""+count);
    }
}