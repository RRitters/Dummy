package org.example;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // 1. Embedded Camunda Engine (In-Memory H2) starten
        ProcessEngine processEngine = ProcessEngineConfiguration
                .createStandaloneInMemProcessEngineConfiguration()
                .buildProcessEngine();

        // 2. BPMN-Diagramm aus den resources laden
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("diagram.bpmn")
                .name("Rechnungsprozess")
                .deploy();

        System.out.println("Camunda Engine gestartet & BPMN geladen!");

        // 3. Swing GUI starten und ProcessEngine übergeben
        SwingUtilities.invokeLater(() -> {
//            InvoiceFrame frame = new InvoiceFrame(processEngine);
            AntragFrame frame = new AntragFrame(processEngine);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}