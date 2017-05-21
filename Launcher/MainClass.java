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
package Launcher;

import gui.MainScreen;

import javax.swing.*;

public class MainClass
{
    private static MainScreen mains;
    public static void main(String[] args){
        //by default the application starts in dictionary mode
        //go to AppMode class for details of the modes

        //launch ui
        mains = new MainScreen();

    }

    public static JFrame getMainFrame(){
        return (JFrame) mains;
    }
}
