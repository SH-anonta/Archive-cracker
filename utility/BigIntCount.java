package utility;

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


//the sole purpose of this class is to provide simple
//bigInt functionality while being more efficient than BigInt class
//BigInt is inefficient because it can't change the value of an object without creating another object

//this class does not use the java BigInt class
//it serves a simillar purpose: stores very big integer values upto ~INF
//and supports only one operation over the stored value: increment by 1


import java.util.Vector;

public class BigIntCount{
    private StringBuilder value;

    public BigIntCount(String initial_val) throws NumberFormatException{
        if(onlyHasDigits(initial_val) == false)
            throw new NumberFormatException();

        value = new StringBuilder(initial_val);
    }

    //check if all characters are decimal digits or not
    //return true if so
    private boolean onlyHasDigits(String string){
        char ch;
        for(int i= 0, len= string.length(); i<len; i++){
            ch= string.charAt(i);
            if(ch < '0' || ch > '9') return false;
        }

        return true;
    }

    //increases the value by one
    public void incrementByOne(){
        char ch;
        for(int i= value.length()-1; i>= 0; i--){
            ch= value.charAt(i);

            if(ch < '9'){
                ch++;
                value.setCharAt(i, ch);
                return;
            }
            else if(i == 0){    //if i == 0 and ch < '9'
                value.setCharAt(i, '0');
                value.insert(0, '1');
            }
            else{
                value.setCharAt(i, '0');
            }

        }
    }

    @Override
    public String toString() {
        return value.toString();
    }

    //reset the value to 0
    public void reset() {
        value= new StringBuilder("0");
    }
}
