
package com.mycompany.p2pshare;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Peer {
    private String id;
    private String name;
    private List<Peer> peerList;
    private int port;
    private String ipAddress;

    public Peer(String id, String name, int port, List<Peer> peerList) {
        this.id = id;
        this.name = name;
        this.peerList = peerList;
        this.port = port;
        this.ipAddress = "127.0.0.1"; // Dirección IP por defecto
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Peer> getPeerList() {
        return peerList;
    }

    public void setPeerList(List<Peer> peerList) {
        this.peerList = peerList;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void connect() {
        // Implementación de la conexión
        System.out.println("Conectando a la red P2P...");
    }

    public List<Peer> discoverPeers() {
        // Implementación de la búsqueda de peers
        List<Peer> discoveredPeers = new ArrayList<>();
        // Simplemente una lista vacía para este ejemplo
        return discoveredPeers;
    }

    public void registerPeer(Peer peer) {
        // Implementación del registro de peers
        peerList.add(peer);
        System.out.println("Registrando peer: " + peer.getName());
    }

    public void sendFile(File file, Peer destinationPeer) {
        try (Socket socket = new Socket(destinationPeer.getIpAddress(), destinationPeer.getPort())) {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(file);
            System.out.println("Enviando archivo a " + destinationPeer.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveFile() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Esperando conexión para recibir archivo en el puerto " + port);
            Socket socket = serverSocket.accept();

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            File receivedFile = (File) inputStream.readObject();

            // Lógica para guardar el archivo en el sistema de archivos
            System.out.println("Archivo recibido: " + receivedFile.getName());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void registerFile(String fileName, long fileSize) {
        // Lógica para registrar el archivo
        System.out.println("Registrando archivo: " + fileName + " - Tamaño: " + fileSize + " bytes");
    }

    public Map<String, FileInfo> searchFiles(String fileName) {
        // Implementación de la búsqueda de archivos
        Map<String, FileInfo> searchResults = new HashMap<>();
        // Lógica para buscar el archivo y llenar searchResults
        return searchResults;
    }

    public static class FileInfo implements Serializable {
        private String fileName;
        private long fileSize;

        public FileInfo(String fileName, long fileSize) {
            this.fileName = fileName;
            this.fileSize = fileSize;
        }

        public String getFileName() {
            return fileName;
        }

        public long getFileSize() {
            return fileSize;
        }
    }
}
