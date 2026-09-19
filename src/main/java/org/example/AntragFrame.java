package org.example;

import org.camunda.bpm.engine.ProcessEngine;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

public class AntragFrame extends JFrame {
    private JButton btnStartAntrag;
    private JLabel lblStatus;

    private ProcessEngine processEngine;

    public AntragFrame(ProcessEngine processEngine) {
        this.processEngine = processEngine;

        setTitle("Antragsverwaltung");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        btnStartAntrag = new JButton("Antrag verarbeiten");
        lblStatus = new JLabel("Bereit");

        add(btnStartAntrag);
        add(lblStatus);

        btnStartAntrag.addActionListener(this::onStartAntragClicked);
    }

    private void onStartAntragClicked(ActionEvent e) {
        lblStatus.setText("Antrag wird geprüft...");
        btnStartAntrag.setEnabled(false);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Ersetze "Process_1" durch die ID deines Prozesses aus der BPMN (z. B. "Antragsprozess")
                processEngine.getRuntimeService()
                        .startProcessInstanceByKey("Process_1");
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    lblStatus.setText("Antrag erfolgreich verarbeitet!");
                } catch (Exception ex) {
                    lblStatus.setText("Fehler bei der Antragsverarbeitung.");
                    ex.printStackTrace();
                } finally {
                    btnStartAntrag.setEnabled(true);
                }
            }
        };

        worker.execute();
    }
}