/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jiangzhen
 */
public class ToolScript implements Executor {

    private String name;
    private String command;
    private String[] parameters;
    private String failedSuggestion;

    public ToolScript(String name, String command, String[] parameters) {
        this.name = name;
        this.command = command;
        this.parameters = parameters;
    }
    
     public ToolScript(String name, String command, String[] parameters, String failedSuggestion) {
        this.name = name;
        this.command = command;
        this.parameters = parameters;
        this.failedSuggestion = failedSuggestion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public String getFailedSuggestion() {
        return failedSuggestion;
    }

    public void setFailedSuggestion(String failedSuggestion) {
        this.failedSuggestion = failedSuggestion;
    }

    
    
    public String toString() {
        return name;
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void execute(Callback cb) {
        if (cb != null) {
            cb.preToolScript(this);
        }
        try {            
            //TODO need new thread?
            Process p = Runtime.getRuntime().exec(new String[]{"sh", "-c", command});
            System.out.println(command);
            int ret = p.waitFor();
            Status s = Status.fromInt(ret);
            if (cb != null) {
                cb.finished(this, s);
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ToolScript.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        if (cb != null) {
            cb.afterToolScript(this);
        }
    }

}
