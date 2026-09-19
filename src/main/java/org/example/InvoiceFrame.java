package org.example;

import org.camunda.bpm.engine.ProcessEngine;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

public class InvoiceFrame extends JFrame {
    private JButton btnCreateInvoice;
    private JLabel lblStatus;

    // Camunda ProcessEngine als Feld
    private ProcessEngine processEngine;

    // Konstruktor nimmt die ProcessEngine aus Main.java entgegen
    public InvoiceFrame(ProcessEngine processEngine) {
        this.processEngine = processEngine;

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
        lblStatus.setText("Prozess wird gestartet...");
        btnCreateInvoice.setEnabled(false);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Camunda startet die Prozessinstanz anhand der ID im BPMN-XML
                // Falls deine Prozess-ID im BPMN anders heißt, hier anpassen (z. B. "Process_1"):
                processEngine.getRuntimeService()
                        .startProcessInstanceByKey("Process_1");
                return null;
            }

            @Override
            protected void done() {
                try {
                    get(); // Fängt Exceptions ab, falls der Prozess fehlschlägt
                    lblStatus.setText("Prozess erfolgreich durchgelaufen!");
                } catch (Exception ex) {
                    lblStatus.setText("Fehler im Prozessverlauf.");
                    ex.printStackTrace();
                } finally {
                    btnCreateInvoice.setEnabled(true);
                }
            }
        };

        worker.execute();
    }
}