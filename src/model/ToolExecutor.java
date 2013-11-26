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
    
    /**
     * Load tool script list from default source:<br/>
     * tool_script.xml
     * @return 
     */
    public List<ToolChain> loadToolScript();
    
    
    /**
     * Load tool script list from path file source: <br />
     * @param path tool script file path
     * @return 
     */
    public List<ToolChain> loadToolScript(String path);
    
    
    
    public void executeScript(ToolChain toolChain, Callback cb);
    
}
