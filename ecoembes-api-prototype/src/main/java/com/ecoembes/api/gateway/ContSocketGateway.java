package com.ecoembes.api.gateway;

import java.io.*;
import java.net.Socket;

public class ContSocketGateway implements RecyclingPlantGateway {
    private final String host;
    private final int port;

    public ContSocketGateway(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public double checkCapacity() {
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            out.println("GET_CAPACITY");
            String response = in.readLine();
            return Double.parseDouble(response);
        } catch (Exception e) {
            System.err.println("Error conectando a ContSocket: " + e.getMessage());
            return -1.0;
        }
    }
}