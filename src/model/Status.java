/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author 28851274
 */
public class Status {

    private ExitStats st;

    public enum ExitStats {

        SUCCED,
        FAILED,
    }

    public static Status fromInt(int i) {
        Status st = new Status();
        if (i == 0) {

            st.st = ExitStats.SUCCED;
        } else {
            st.st = ExitStats.FAILED;
        }

        return st;
    }

}
