package main;

import gardens.Game;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import tatami.Tatami;

public class Main{
    
    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Name");
        
        EventQueue.invokeLater(() -> {
            tatami.Frame tat = new tatami.Frame();
        });
        
        JLabel l = new JLabel();
        l.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
       
    }
}