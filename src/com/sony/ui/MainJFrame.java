/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sony.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import model.Callback;
import model.Executor;
import model.Status;
import model.ToolChain;
import model.ToolScript;
import model.Worker;
import model.xml.ConfigParser;

/**
 *
 * @author jiangzhen
 */
public class MainJFrame extends javax.swing.JFrame implements Callback {

    private DefaultTreeModel mToolScriptTreeModel;

    private MainJFrame mainFrame;

    private List<ToolChain> toolGroupList;

    private ProgressDlg dialog;

    private StringBuffer runResult;

    /**
     * Creates new form MainJFrame
     */
    public MainJFrame() {
        initData();
        initComponents();
        mainFrame = this;
    }

    private void initData() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new ToolChain("Tool Script", ToolChain.Platform.UNKNOW, null));
        try {
            toolGroupList = new ConfigParser().loadToolScript();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        for (ToolChain tc : toolGroupList) {
            root.add(new DefaultMutableTreeNode(tc));
        }

        this.mToolScriptTreeModel = new DefaultTreeModel(root);

    }

    private MouseAdapter selectionListener = new MouseAdapter() {

        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            try {
                if (evt.getButton() == evt.BUTTON1) {
                } else if (evt.getButton() == evt.BUTTON3) {
                    TreePath path = jTree1.getPathForLocation(evt.getX(), evt.getY());
                    if (path != null) {
                        jTree1.setSelectionPath(path);
                    } else {
                        return;
                    }
                    DefaultMutableTreeNode sNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
                    ToolChain tc = (ToolChain) sNode.getUserObject();

                    JPopupMenu popup = new JPopupMenu();
                    setMenuItem(popup, tc);
                    popup.show(jTree1, evt.getX(), evt.getY());

                }
            } catch (Exception e) {
            }
        }
    };

    @Override
    public void preToolChain(ToolChain toolChain) {
        runResult = new StringBuffer();        
    }

    @Override
    public void afterToolChain(ToolChain toolChain) {
        if (dialog != null) {
            dialog.setVisible(false);
            dialog.dispose();
        }

        if (!runResult.toString().isEmpty()) {
            JOptionPane.showMessageDialog(null, runResult.toString(), "", JOptionPane.INFORMATION_MESSAGE);
            runResult = null;
        }
    }

    @Override
    public void preToolScript(ToolScript ts) {

    }

    @Override
    public void afterToolScript(ToolScript ts) {

    }

    @Override
    public void finished(ToolScript ts, Status st) {
        if (runResult == null) {
            runResult = new StringBuffer();
        }
        runResult.append(ts.getName() + "  executed result: " + st.getSt()).append("\n");
        if (st.getSt() != Status.ExitStats.SUCCED) {
            runResult.append("   " + ts.getFailedSuggestion()).append("\n");
        }
    }

    @Override
    public void setProgress(int cent) {
        if (dialog != null && dialog.isVisible()) {
            dialog.setProgress(cent);
        }
    }

    class RunToolActionListener implements ActionListener {

        private Executor exectuor;

        public RunToolActionListener(Executor exectuor) {
            this.exectuor = exectuor;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
           
             if (exectuor instanceof ToolChain && ((ToolChain)exectuor).isSyn() == false) {
                dialog = new ProgressDlg(MainJFrame.this, true);
                dialog.setProgress(0);
                dialog.setVisible(true);
            }
              new Worker(exectuor, mainFrame).start();
        }
    };

    private void setMenuItem(JPopupMenu popup, ToolChain tc) {
        JMenuItem item = new JMenuItem("Run");
        item.addActionListener(new RunToolActionListener(tc));
        popup.add(item);

        ToolScript[] ts = tc.getChains();
        for (int i = 0; i < ts.length; i++) {
            JMenuItem subItem = new JMenuItem("   " + ts[i].toString());
            subItem.addActionListener(new RunToolActionListener(ts[i]));
            popup.add(subItem);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jTree1.addMouseListener(selectionListener);
        jMenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jToolBar1.setRollover(true);

        jTree1.setModel(mToolScriptTreeModel);
        jScrollPane1.setViewportView(jTree1);

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem1.setText("Exit");
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exit(evt);
            }
        });
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar.add(jMenu1);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void exit(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exit

    }//GEN-LAST:event_exit

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
