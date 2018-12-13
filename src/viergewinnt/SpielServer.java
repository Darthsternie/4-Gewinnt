package viergewinnt;

import EgJavaLib2.netzwerk.*;
/**
 * Diese Klasse stellt den Server dar, mit dem sich die Clients verbinden.
 *
 * @author (Nick Joraschky & Lennart Tschorn)
 * @version (12.12.2018)
 */
public class SpielServer extends Server{

    // Attribute und Beziehungen
    
    private VierGewinntSpiel dasSpiel;
    private int clients;

    // Konstruktor
    
    public SpielServer(){
        super(1234);
        dasSpiel = new VierGewinntSpiel();
        clients = 0;
        dasSpiel.setzeServer(this);
        SpielFenster f = new SpielFenster("localhost");
    }

    // Falls noch Platz im Spiel ist, wird der Client nach seinem Namen gefragt und die Anzahl der Clinets wird erhöht.
    @Override
    public void processNewConnection(String pClientIP, int pClientPort){
        if(clients < 2){
            this.send(pClientIP, pClientPort, "NEWNAME");
            clients++;
        }
        else{
            this.send(pClientIP, pClientPort, "DISCONNECT");
        }
    }
    
    // Falls ein Client die Verbindung trennt, wird die Anzahl der Clients erniedrigt. 
    @Override
    public void processClosingConnection(String pClientIP, int pClientPort){
        if(!dasSpiel.gibSieg()){
            this.sendToAll("END Gegner hatte Angst. Du hast gewonnen!");
            dasSpiel.setzeSieg(true);
        }
        clients--;
    }
    
    // Verarbeitet Nachrichten, die der Server vom Client bekommt.
    @Override
    public void processMessage(String pClientIP, int pClientPort, String pMessage){
        // Speichert den erhaltenen Namen in der VierGewinntSpiel-Klasse und schickt die Information beider Namen
        // und dessen Spielsteinfarbe zur Aktualisierung an die Clients.
        if(pMessage.startsWith("NAME ")){
            String name = pMessage.replaceFirst("NAME ", "");
            dasSpiel.spielerHinzuFügen(name);
            // Falls der Name für Spieler 2 nach dem Hinzufügen noch nicht gesetzt wurde, handelt es sich um den ersten Spieler.
            // Ihm wird die Farbe Gelb zugewiesen.
            if(dasSpiel.gibNameSpielerZwei().equals("Spieler 2")){
                this.send(pClientIP, pClientPort, "UPDATECOLOR yellow");
            }
            // Ansonsten muss es sich um den zweiten Spieler handeln. Er bekommt die Farbe rot.
            else{
                this.send(pClientIP, pClientPort, "UPDATECOLOR red");
            }
            this.sendToAll("UPDATENAME1 " + dasSpiel.gibNameSpielerEins());
            this.sendToAll("UPDATENAME2 " + dasSpiel.gibNameSpielerZwei());
        }
        // Aktualisiert das Spielbrett nach dem Zug eines Clients in der Spiellogik 
        // und sendet eine Nachticht an die Clients, damit ihr Spielbrett in ihrer Oberfläche aktualisiert wird.
        else if(pMessage.startsWith("ACTION ")){
            // Wenn das Spiel noch nicht vorbei ist...
            if(!dasSpiel.gibSieg()){
                String s = pMessage.replaceFirst("ACTION ", "");
                String[] values = s.split(":");
            
                if(dasSpiel.gibAktuellerSpieler() != null){
                    // Wenn der entsprechende Client der aktuelle Spieler, also an der Reihe ist, wird sein Zug aktualisiert.
                    if(dasSpiel.gibAktuellerSpieler() == dasSpiel.gibSpielerEins() && values[2].equals("yellow")){
                        dasSpiel.setzeZeichen(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
                        this.sendToAll("UPDATEFELD " + Integer.parseInt(values[0]) + ":yellow");
                    }
                    else if(dasSpiel.gibAktuellerSpieler() == dasSpiel.gibSpielerZwei() && values[2].equals("red")){
                        dasSpiel.setzeZeichen(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
                        this.sendToAll("UPDATEFELD " + Integer.parseInt(values[0]) + ":red");
                    }
                }
            } 
        }
    }

}