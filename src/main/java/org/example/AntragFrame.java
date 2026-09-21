package org.example;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class AntragFrame extends JFrame {
    private JTextField txtName;
    private JTextField txtEinkommen;
    private JButton btnStartAntrag;
    private JLabel lblStatus;

    private ProcessEngine processEngine;

    public AntragFrame(ProcessEngine processEngine) {
        this.processEngine = processEngine;

        setTitle("Wohngeld-Antrag Sandbox");
        setSize(450, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        // Formular-Felder
        add(new JLabel(" Name Antragsteller:"));
        txtName = new JTextField("Max Mustermann");
        add(txtName);

        add(new JLabel(" Monatliches Einkommen (€):"));
        txtEinkommen = new JTextField("1800");
        add(txtEinkommen);

        btnStartAntrag = new JButton("Antrag einreichen");
        lblStatus = new JLabel("Status: Bereit", SwingConstants.CENTER);

        add(btnStartAntrag);
        add(lblStatus);

        btnStartAntrag.addActionListener(this::onStartAntragClicked);
    }

    private void onStartAntragClicked(ActionEvent e) {
        lblStatus.setText("Status: Prozess wird gestartet...");
        btnStartAntrag.setEnabled(false);

        String name = txtName.getText();
        double einkommen = Double.parseDouble(txtEinkommen.getText());

        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() throws Exception {
                // 1. Prozessvariablen für Camunda vorbereiten
                Map<String, Object> variables = new HashMap<>();
                variables.put("antragstellerName", name);
                variables.put("monatsEinkommen", einkommen);

                // 2. Prozess mit Variablen starten
                ProcessInstance instance = processEngine.getRuntimeService()
                        .startProcessInstanceByKey("Process_1", variables);

                return instance.getId();
            }

            @Override
            protected void done() {
                try {
                    String instanceId = get();
                    lblStatus.setText("Erfolg! Instanz-ID: " + instanceId.substring(0, 8) + "...");
                } catch (Exception ex) {
                    lblStatus.setText("Fehler beim Starten!");
                    ex.printStackTrace();
                } finally {
                    btnStartAntrag.setEnabled(true);
                }
            }
        };

        worker.execute();
    }
}