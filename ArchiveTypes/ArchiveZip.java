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


import thirdparty_libraries.net.lingala.zip4j.core.ZipFile;
import thirdparty_libraries.net.lingala.zip4j.exception.ZipException;
import utility.ArchiveFactory;
import utility.TempFolder;

import java.io.File;

//this is a wrapper class for easily using the Zip4J library ZipFile class
public class ArchiveZip extends Archive{
    private ZipFile zipfile;        //the zipfile it self
    private String extract_to;      //will store temp folder's directory

    public ArchiveZip(File file){
        zipfile = ArchiveFactory.createArchiveFromFile(file);
        extract_to= TempFolder.getDirectory();
    }

    public boolean tryPassword(String password) {

        try {
            //if password fails extractAll will throw a ZipException
            zipfile.setPassword(password);
            zipfile.extractAll(extract_to);

            return true;
        }
        catch (ZipException ex){
            //password did not work
            return false;
        }

//        return false;
    }

    //return true if the zipfile is password protected
    public boolean isProtected() {
        //Intentionally try to extract file without setting any password
        //if faild, zip file is password proteceted
        //else unprotected
        try {
            //if password fails extractAll will throw a ZipException
            zipfile.extractAll(extract_to);
            return false;
        }
        catch (ZipException ex){
            //extraction faild
            return true;
        }
    }
}
