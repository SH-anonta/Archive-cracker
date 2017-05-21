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

import thirdparty_libraries.net.lingala.zip4j.core.ZipFile;
import thirdparty_libraries.net.lingala.zip4j.exception.ZipException;

import java.io.File;

public class ArchiveFactory {

    //convert java.io.File to zip4j.core.ZipFile object
    public static ZipFile createArchiveFromFile(File file){
        try{
            return new ZipFile(file.getAbsolutePath());
        }
        catch (ZipException ex){
            ex.printStackTrace();
        }

        //statement will not be reached
        return null;
    }

}
