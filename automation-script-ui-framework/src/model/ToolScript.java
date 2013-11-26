/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author jiangzhen
 */
public class ToolScript {
    
    private String name;
    private String command;
    private String[] parameters;

    public ToolScript(String name, String command, String[] parameters) {
        this.name = name;
        this.command = command;
        this.parameters = parameters;
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
    
    
    public String toString() {
        return name;
    }
    
}
