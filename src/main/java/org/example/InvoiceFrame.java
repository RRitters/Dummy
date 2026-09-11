package org.example;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

public class InvoiceFrame extends JFrame {
    private JButton btnCreateInvoice;
    private JLabel lblStatus;

    public InvoiceFrame() {
        setTitle("Rechnungsverwaltung");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        btnCreateInvoice = new JButton("Rechnung erstellen");
        lblStatus = new JLabel("Bereit");

        add(btnCreateInvoice);
        add(lblStatus);

        // Event-Listener verknüpfen
        btnCreateInvoice.addActionListener(this::onCreateInvoiceClicked);
    }

    private void onCreateInvoiceClicked(ActionEvent e) {
        // BREAKPOINT HERE (1): Wenn Sie hier einen Breakpoint setzen, hält die IDE beim Klick an.

        // 1. Text SOFORT anzeigen & Button sperren
        lblStatus.setText("Rechnung wird erstellt...");
        btnCreateInvoice.setEnabled(false);

        // 2. Erstellung im Hintergrund ausführen
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // BREAKPOINT HERE (2): Hält während der Hintergrundarbeit an.
                erstelleRechnung(); // Zeitintensive Operation (PDF, DB, etc.)
                return null;
            }

            @Override
            protected void done() {
                // Wird automatisch wieder im UI-Thread ausgeführt, wenn fertig
                try {
                    get(); // Prüft auf Fehler in doInBackground
                    lblStatus.setText("Rechnung erfolgreich erstellt!");
                } catch (Exception ex) {
                    lblStatus.setText("Fehler bei der Rechnungsstellung.");
                    ex.printStackTrace();
                } finally {
                    btnCreateInvoice.setEnabled(true);
                }
            }
        };

        worker.execute(); // Startet den Hintergrundprozess
    }

    private void erstelleRechnung() throws InterruptedException {
        // Simulation einer 2-sekündigen Verarbeitung
        Thread.sleep(2000);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InvoiceFrame().setVisible(true));
    }
}