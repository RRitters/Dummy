package org.example;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class DummyDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Hier steht deine Logik (z. B. PDF generieren)
        System.out.println("Service Task wird von Camunda ausgeführt!");
    }

    // Kann direkt im Code oder vom InvoiceFrame aufgerufen werden
    public void erstelleRechnung() {
        System.out.println("Service Task wird ausgeführt: Rechnung wird erstellt!");
    }
}