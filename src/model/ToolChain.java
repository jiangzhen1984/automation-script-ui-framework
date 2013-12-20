/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author jiangzhen
 */
public class ToolChain implements Executor{

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     
    public enum Platform {
        LINUX,
        WINDOWS,
        LINUX_WINDOWS,
        UNKNOW;      
       
    }
    
    private String name;
    private Platform platform;
    
    private ToolScript[] chains;

    public ToolChain(String name, Platform platform, ToolScript[] chains) {
        this.name = name;
        this.platform = platform;
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

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
    
    
     public static Platform fromString(String str) {
            if("linux".equalsIgnoreCase(str)) {
                return Platform.LINUX;
            } else if ("windoes".equalsIgnoreCase(str)) {
                return Platform.WINDOWS;
            } else if("linux_windows".equalsIgnoreCase(str)) {
                return Platform.LINUX_WINDOWS;
            } else {
                return Platform.UNKNOW;
            }
        }
    
}
