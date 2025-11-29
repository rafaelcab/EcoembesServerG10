package com.ecoembes.EcoembesServer.integration.impl;

import com.ecoembes.EcoembesServer.integration.IRecyclingPlantGateway;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;

public class ContSocketGateway implements IRecyclingPlantGateway {

    private final String host = "localhost";
    private final int port = 9000; // Puerto del servidor ContSocket

    @Override
    public double getCapacity(LocalDate fecha) {
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            // Protocolo: CAPACITY_CHECK:YYYY-MM-DD
            out.println("CAPACITY_CHECK:" + fecha.toString());
            String response = in.readLine(); // Espera: CAPACITY_Response:500.0
            
            if (response != null && response.startsWith("CAPACITY_Response:")) {
                return Double.parseDouble(response.split(":")[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public void notifyAllocation(LocalDate fecha, int numContenedores, int totalEnvases) {
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            // Protocolo: ALLOCATION:YYYY-MM-DD:Num:Total
            out.println("ALLOCATION:" + fecha + ":" + numContenedores + ":" + totalEnvases);
            // Leer confirmación si es necesario (ALLOCATION_Response:OK)
            in.readLine(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}