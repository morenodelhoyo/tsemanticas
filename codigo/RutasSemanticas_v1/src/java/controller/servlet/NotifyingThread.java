/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.servlet;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Clase abstracta NotifyingThread, permite lanzar hilos que realicen un callback
 * al método pertinente. De esta forma se puede controlar la finalización
 * de un hilo concreto.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public abstract class NotifyingThread extends Thread {
    
    /**
     * Set de objetos de tipo ThreadCompleteListener.
     */
    private final Set<ThreadCompleteListener> listeners = new CopyOnWriteArraySet();
    
    /**
     * Método que permite añadir un Listener.
     * @param listener listener a añadir.
     */
    public final void addListener(final ThreadCompleteListener listener) {
        listeners.add(listener);

    }

    /**
     * Método que permite elimianr un Listener.
     * @param listener listener a eliminar.
     */
    public final void removeListener(final ThreadCompleteListener listener) {
        listeners.remove(listener);
    }

    /**
     * Método que permite notificar la finalización de un hilo.
     */
    private final void notifyListeners() {
        listeners.forEach((listener) -> {
            listener.notifyOfThreadComplete(this);
        });
    }

    /**
     * Ejecución del listener.
     */
    @Override
    public final void run() {

        try {
            doRun();
        } finally {
            notifyListeners();
        }

    }

    /**
     * Método abstracto a implementar por los hilos notificantes.
     */
    public abstract void doRun();
	
}
