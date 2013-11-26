/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author jiangzhen
 */
public class ToolChain {
    
    private String name;
    private ToolScript[] chains;

    public ToolChain(String name, ToolScript[] chains) {
        this.name = name;
        this.chains = chains;
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ToolScript[] getChains() {
        return chains;
    }

    public void setChains(ToolScript[] chains) {
        this.chains = chains;
    }
    
    
    public String toString() {
        return name;
    }
    
}
