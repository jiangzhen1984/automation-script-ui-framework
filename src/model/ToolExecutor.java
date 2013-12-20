/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author jiangzhen
 */
public interface ToolExecutor {
    
     
    
    
    public void executeToolChain(ToolChain toolChain, Callback cb);
    
    
    public void executeToolScript(ToolScript toolScript, Callback cb);
}
