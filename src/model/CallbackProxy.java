/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 28851274
 */
public class CallbackProxy extends Thread implements Callback {

    private Callback callback;

    private Vector<Wrapper> que;

    public CallbackProxy(Callback callback) {
        this.callback = callback;
        que = new Vector<Wrapper>(20);
        this.start();
    }

    @Override
    public void preToolChain(ToolChain toolChain) {
        que.add(new Wrapper(WrapperType.PRE_CHAIN, toolChain));
        synchronized (this) {
            this.notify();
        }
    }

    @Override
    public void afterToolChain(ToolChain toolChain) {
        que.add(new Wrapper(WrapperType.AFTER_CHAIN, toolChain));
        synchronized (this) {            
            this.notify();
        }
    }

    @Override
    public void preToolScript(ToolScript ts) {
        que.add(new Wrapper(WrapperType.PRE_SCRIPT, ts));
        synchronized (this) {
            this.notify();
        }
    }

    @Override
    public void afterToolScript(ToolScript ts) {
        que.add(new Wrapper(WrapperType.AFTER_SCRIPT, ts));
        synchronized (this) {
            this.notify();
        }
    }

    @Override
    public void finished(ToolScript ts,Status st) {
        que.add(new Wrapper(WrapperType.FINISHED, ts, st));
        synchronized (this) {
             System.out.println("notify finish");
            this.notify();
        }
    }

    @Override
    public void setProgress(int cent) {
        que.add(new Wrapper(WrapperType.PROGRESS, cent));
        synchronized (this) {
            this.notify();
        }
    }

    @Override
    public void run() {

        Wrapper obj;
        synchronized (this) {
            while (true) {                
                if (que.isEmpty()) {
                    try {
                        System.out.println("start to wait");
                        wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                        Logger.getLogger(CallbackProxy.class.getName()).log(Level.SEVERE, null, ex);
                    }                   
                }
                System.out.println("get notify");
                obj = que.remove(0);
                switch (obj.getType()) {
                    case PRE_CHAIN:
                        callback.preToolChain((ToolChain) obj.getObj());
                        break;
                    case AFTER_CHAIN:
                        callback.afterToolChain((ToolChain) obj.getObj());
                        break;
                    case PRE_SCRIPT:
                        callback.preToolScript((ToolScript) obj.getObj());
                        break;
                    case AFTER_SCRIPT:
                        callback.afterToolScript((ToolScript) obj.getObj());
                         que.clear();
                        return;
                    case FINISHED:
                       callback.finished((ToolScript) obj.getObj(), (Status) obj.getObj1());
                       
                        break;
                    case PROGRESS:
                        callback.setProgress((int) obj.getObj());
                        break;
                    default:
                        Logger.getLogger(CallbackProxy.class.getName()).log(Level.SEVERE, "unkown type");
                }
            }
        }

    }

    enum WrapperType {

        PRE_CHAIN,
        AFTER_CHAIN,
        PRE_SCRIPT,
        AFTER_SCRIPT,
        FINISHED,
        PROGRESS
    }

    class Wrapper {

        private WrapperType type;
        private Object obj;
        private Object obj1;

        public Wrapper(WrapperType type, Object obj) {
            this.type = type;
            this.obj = obj;
        }
        
        public Wrapper(WrapperType type, Object obj, Object obj1) {
            this.type = type;
            this.obj = obj;
            this.obj1 = obj1;
        }

        public WrapperType getType() {
            return type;
        }

        public void setType(WrapperType type) {
            this.type = type;
        }

        public Object getObj() {
            return obj;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }
        
         public Object getObj1() {
            return obj1;
        }


    }

}
