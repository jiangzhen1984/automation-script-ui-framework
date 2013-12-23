/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author jiangzhen
 */
public interface Callback {
    
    public void preToolChain(ToolChain toolChain);
    
    public void afterToolChain(ToolChain toolChain);
    
    public void preToolScript(ToolScript ts);
    
    public void afterToolScript(ToolScript ts);
    
    public void finished(ToolScript ts, Status st);
    
    public void setProgress(int cent);
   
}
