/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.servlet;

/**
 * Interfaz ThreadCompleteListener a implementar por las clases que deseen
 * notificar que su hilo ha finalizado.
 * @author David Moreno del  Hoyo.
 * @version 1.0
 */
public interface ThreadCompleteListener {
    
    /**
     * Método notifyThreadComplete a implementar en las clases que implementan
     * esta interfaz.
     * @param thread el hilo que ha finalizado.
     */
    void notifyOfThreadComplete(final Thread thread);
}
