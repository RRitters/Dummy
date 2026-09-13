package org.example;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

public class InvoiceFrame extends JFrame {
    private JButton btnCreateInvoice;
    private JLabel lblStatus;

    // Instanz deines Delegates
    private DummyDelegate delegate = new DummyDelegate();

    public InvoiceFrame() {
        setTitle("Rechnungsverwaltung");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        btnCreateInvoice = new JButton("Rechnung erstellen");
        lblStatus = new JLabel("Bereit");

        add(btnCreateInvoice);
        add(lblStatus);

        btnCreateInvoice.addActionListener(this::onCreateInvoiceClicked);
    }

    private void onCreateInvoiceClicked(ActionEvent e) {
        // BREAKPOINT (1): Hält beim Klick im UI-Thread an
        lblStatus.setText("Rechnung wird erstellt...");
        btnCreateInvoice.setEnabled(false);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // BREAKPOINT (2): Hält im Hintergrund-Thread an
                // Aufruf der Logik in deinem Delegate
                delegate.erstelleRechnung();
                return null;
            }

            @Override
            protected void done() {
                try {
                    get(); // Fängt Exceptions ab, falls erstelleRechnung() fehlschlägt
                    lblStatus.setText("Rechnung erfolgreich erstellt!");
                } catch (Exception ex) {
                    lblStatus.setText("Fehler bei der Erstellung.");
                    ex.printStackTrace();
                } finally {
                    btnCreateInvoice.setEnabled(true);
                }
            }
        };

        worker.execute();
    }
}