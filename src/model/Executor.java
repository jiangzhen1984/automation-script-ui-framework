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
public interface Executor {
    
    public void execute();
    
    public void execute(Callback cb);
}
