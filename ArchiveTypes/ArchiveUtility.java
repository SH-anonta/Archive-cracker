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
package ArchiveTypes;

import java.io.File;
import java.io.IOException;

public class ArchiveUtility {
    private static final String ZIP_FILE_EXTENSION= ".zip";
    //intentionally private
    private ArchiveUtility(){}

    //convert a file object to an appropriaet type wrapper class object
    //ie an abc.zip file object would be converted ArchiveZip object
    //file type is detected using it's extension
    public static Archive fileToArchive(File file) throws IOException {
        String filename= file.getName();

        if(filename.endsWith(ZIP_FILE_EXTENSION)){
            return new ArchiveZip(file);
        }
        else{
            // no types match
            throw new IOException("Invalid archive type");
        }
    }

}
