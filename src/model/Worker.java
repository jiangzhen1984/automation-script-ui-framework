/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 28851274
 */
public class Worker extends Thread {

    private Callback cb;

    private Executor executor;

    public Worker(Executor executor) {
        this.executor = executor;
    }

    public Worker(Executor executor, Callback cb) {
        this.cb = cb;
        this.executor = executor;
    }

    @Override
    public void run() {
        if (executor == null) {
            throw new RuntimeException(" can't run executor");
        }

        executor.execute(cb);

    }

}
