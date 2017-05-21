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

package logic;

//This class keeps track of the which tab is showing in the center JTabbed pane
public class TabState {
    //bruteforce panel is showing
    public static final int BRUTEFORCE_TAB = 0;
    //Dictionary panel is showing
    public static final int DICTIONARY_TAB = 1;
    //Stats panel  is showing and the application
    public static final int STATUS_TAB = 2;

    //by default when the application starts it should be in dictionary current_tab
    static private int current_tab = TabState.BRUTEFORCE_TAB;

    //no object needs to be made of this class
    private TabState(){}

    public static int getTabState() {
        return current_tab;
    }

    //should only be accessed by JTabbed Panel Change Listener
    public static void updateTabState(int new_tab) {
        TabState.current_tab = new_tab;
    }
}
