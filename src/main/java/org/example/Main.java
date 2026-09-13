package org.example;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Swing-Komponenten immer auf dem Event Dispatch Thread initialisieren
        SwingUtilities.invokeLater(() -> {
            InvoiceFrame frame = new InvoiceFrame();
            frame.setLocationRelativeTo(null); // Zentriert das Fenster auf dem Bildschirm
            frame.setVisible(true);
        });
    }
}