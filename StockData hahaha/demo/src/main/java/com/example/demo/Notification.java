package com.example.demo;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.Serializable;

import javax.swing.ImageIcon;


public class Notification implements Serializable{
    public static void showNotification(String title, String message) {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported on this platform.");
            return;
        }
        System.out.println("gg");
        // Create a SystemTray instance
        SystemTray systemTray = SystemTray.getSystemTray();

        // Create an image icon for the tray icon
        ImageIcon icon = new ImageIcon(Notification.class.getResource("6785368.png"));
        Image image = icon.getImage();

        // Create a tray icon and add it to the SystemTray
        TrayIcon trayIcon = new TrayIcon(image, "Notification");
        trayIcon.setImageAutoSize(true);

        try {
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("Failed to add TrayIcon to SystemTray.");
            return;
        }
        // Display the notification message
        trayIcon.displayMessage(title, message, TrayIcon.MessageType.WARNING); 
    }
}
