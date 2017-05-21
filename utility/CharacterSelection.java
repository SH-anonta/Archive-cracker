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

import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigInteger;

//used to pass the charecters user wants to use for a bruteforce attack
public class CharacterSelection implements Serializable {

    public boolean ALPHABETS;       //ABCD...
    public boolean alphabets;       //abcd...
    public boolean numbers;         //0123...
    public boolean special_symbols; //@#$^...
    public boolean space;           //(space)

    public int pass_length_from;
    public int pass_length_to;

    public String custom_charecters;

    public CharacterSelection(){
        //do nothing on purpose
    }

    public void reset(){
        ALPHABETS= false;
        alphabets= false;
        numbers= false;
        special_symbols= false;
        space= false;
        custom_charecters= null;
    }

    public int getCustomCharLength(){
        if(custom_charecters == null) return 0;
        else return custom_charecters.length();
    }

    public BigInteger getPossiblePasswordCount(){
        int sum= 0;     //total characters used
        BigInteger combo_count= new BigInteger("0");    //keep combination count

        sum+= getTotalCharCount();
        BigInteger big_sum= new BigInteger(""+sum);

        int from= this.pass_length_from;
        int to = this.pass_length_to;

        for(int i= from; i<= to; i++){
            combo_count= combo_count.add(big_sum.pow(i));
        }

        return  combo_count;
    }

    public int getTotalCharCount(){
        int sum = 0;
        if(this.space){   //space
            sum+= 1;
        }
        if(this.special_symbols){    //special chars
            sum+= 32;
        }
        if(this.ALPHABETS){    //A-Z
            sum+= 26;
        }
        if(this.alphabets){    //a-z
            sum+= 26;
        }
        if(this.numbers){    //0-9
            sum+= 10;
        }

        sum+= this.getCustomCharLength();

        return sum;
    }

    public char[] buildCharacterArray(){
        int total_characters= this.getTotalCharCount();

        char[] chars_set= new char[total_characters];

        int i= 0;       //index at which new elements will be inserted into

        //check if each echarecter set is selected and if so, add them to char_set
        if(this.alphabets){    //a-z
            for(char ch= 'a'; ch<= 'z'; ch++){
                chars_set[i]= ch;
                i++;
            }
        }
        if(this.ALPHABETS){    //A-Zgit
            for(char ch= 'A'; ch<= 'Z'; ch++){
                chars_set[i]= ch;
                i++;
            }
        }
        if(this.numbers){    //0-9
            for(char ch= '0'; ch<= '9'; ch++){
                chars_set[i]= ch;
                i++;
            }
        }
        if(this.special_symbols){    //special chars
            //32 special symbols
            int symbol_count= 32;
            final char[] special_symbols= {'`','~','!','@','#','$','%','^','&','*','(',')','_','+','-','=','[','{',']','}','\\','|',';',':','\'','"','<',',','>','.','?','/',};

            for(int j= 0; j< symbol_count; j++){
                chars_set[i]= special_symbols[j];
                i++;
            }
        }
        if(space){   //space
            chars_set[i]= ' ';
            i++;
        }

        //add the charecters in custom_charecters
        for(int j= 0, len= custom_charecters.length(); j<len; j++){
            chars_set[i]= custom_charecters.charAt(j);
            i++;
        }

        return chars_set;
    }


    //for debugging
    public void printAllChars() {
        System.out.println("All chars:");
        for(char ch : this.buildCharacterArray()){
            System.out.print(ch+" ");
        }
    }
}
