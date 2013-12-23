/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author jiangzhen
 */
public class ToolChain implements Executor {

    public enum Platform {

        LINUX,
        WINDOWS,
        LINUX_WINDOWS,
        UNKNOW;

    }

    private String name;
    private Platform platform;
    private boolean syn;
    

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

    public boolean isSyn() {
        return syn;
    }

    public void setSyn(boolean syn) {
        this.syn = syn;
    }
    
    

    public static Platform fromString(String str) {
        if ("linux".equalsIgnoreCase(str)) {
            return Platform.LINUX;
        } else if ("windoes".equalsIgnoreCase(str)) {
            return Platform.WINDOWS;
        } else if ("linux_windows".equalsIgnoreCase(str)) {
            return Platform.LINUX_WINDOWS;
        } else {
            return Platform.UNKNOW;
        }
    }

    @Override
    public void execute() {
        execute(null);
    }

    @Override
    public void execute(Callback cb) {
        if (cb != null) {
            cb.preToolChain(this);
        }
        if (chains == null && cb != null) {
            cb.setProgress(100);
        }
        
        int count = this.chains.length;
        for (int i=0; i < count; i++) {
            ToolScript ts = this.chains[i];
            if(ts != null) {
                ts.execute(cb);
            }
            if (cb != null) {
                cb.setProgress(i/count * 100);
            }
        }
        
        if (cb != null) {
            cb.setProgress(100);
            cb.afterToolChain(this);
        }
    }

}
