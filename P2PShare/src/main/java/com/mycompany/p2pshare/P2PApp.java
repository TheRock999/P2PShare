
package com.mycompany.p2pshare;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class P2PApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Configurar los puertos
        int portPeer1 = 4000;
        int portPeer2 = 4001;

        List<Peer> knownPeers = createKnownPeers(portPeer2);  // Crea tus peers aquí

        // Crear una instancia de Peer
        Peer myPeer = new Peer("1", "MiPeer", portPeer1, knownPeers);

        // Llamar al método connect
        myPeer.connect();

        // Llamar al método discoverPeers
        List<Peer> discoveredPeers = myPeer.discoverPeers();
        System.out.println("Peers descubiertos: " + discoveredPeers);

        // Llamar al método registerPeer
        for (Peer discoveredPeer : discoveredPeers) {
            myPeer.registerPeer(discoveredPeer);
        }

        // Llamar al método sendFile
        File myFile = new File("miArchivo.txt");
        Peer destinationPeer = knownPeers.get(0); // Supongamos que enviamos a uno de los peers conocidos
        myPeer.sendFile(myFile, destinationPeer);

        // Llamar al método searchFiles
        String fileNameToSearch = "archivoBuscado.txt";
        Map<String, Peer.FileInfo> searchResultsMap = myPeer.searchFiles(fileNameToSearch);

        // Llamar al método registerFile
        long fileSize = myFile.length();
        myPeer.registerFile(fileNameToSearch, fileSize);

        // Mostrar la interfaz gráfica
        showGUI(primaryStage, knownPeers, getSearchResultNames(searchResultsMap));
    }

    private List<Peer> createKnownPeers(int portPeer2) {
        // Crear y retornar una lista de peers conocidos
        List<Peer> knownPeers = new ArrayList<>();
        knownPeers.add(new Peer("2", "Peer 2", portPeer2, new ArrayList<>()));
        return knownPeers;
    }

    private void showGUI(Stage primaryStage, List<Peer> knownPeers, List<String> searchResults) {
        primaryStage.setTitle("P2PShare");

        // Crear una lista de peers
        ListView<String> peersListView = new ListView<>();
        peersListView.getItems().addAll(getPeerNames(knownPeers));

        // Crear una lista de resultados de búsqueda
        ListView<String> searchResultsListView = new ListView<>();
        searchResultsListView.getItems().addAll(searchResults);

        // Manejar selección de resultados de búsqueda
        searchResultsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Aquí puedes agregar la lógica para manejar la selección del resultado de búsqueda
                System.out.println("Archivo seleccionado: " + newValue);
            }
        });

        // Crear el diseño de la interfaz gráfica
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                peersListView,
                searchResultsListView
        );

        // Configurar la escena
        Scene scene = new Scene(layout, 300, 200);

        // Configurar el escenario principal
        primaryStage.setScene(scene);

        // Mostrar la ventana
        primaryStage.show();
    }

    private List<String> getPeerNames(List<Peer> peers) {
        // Obtener los nombres de los peers
        List<String> peerNames = new ArrayList<>();
        for (Peer peer : peers) {
            peerNames.add(peer.getName());
        }
        return peerNames;
    }

    private List<String> getSearchResultNames(Map<String, Peer.FileInfo> searchResultsMap) {
        // Obtener los nombres de los resultados de búsqueda
        List<String> searchResultNames = new ArrayList<>();
        for (Map.Entry<String, Peer.FileInfo> entry : searchResultsMap.entrySet()) {
            searchResultNames.add(entry.getKey());
        }
        return searchResultNames;
    }
}
