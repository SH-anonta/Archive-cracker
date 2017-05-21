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

import java.io.*;

//provide information about files
public class FileInfoProvider {

    //no objects to be created
    private FileInfoProvider(){}

    //read a text file and count the number of lines in this file
    //lines are counted using the newline/line feed charecter
    public static long getLinesInFile(File file){
        InputStream is;

        try{
            is = new BufferedInputStream(new FileInputStream(file));

            try {
                byte[] c = new byte[1024];
                long count = 0;
                long readChars = 0;
                boolean empty = true;
                while ((readChars = is.read(c)) != -1) {
                    empty = false;
                    for (int i = 0; i < readChars; ++i) {
                        if (c[i] == '\n') {
                            ++count;
                        }
                    }
                }
                //this 1 has been added to count for the last password
                //incase there is a newline after the last passwod
                //this extra 1 will not be needed
                return (count == 0 && !empty) ? 1 : count + 1;
            } finally {
                is.close();
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        //will never be reached
        return 0;
    }



}
