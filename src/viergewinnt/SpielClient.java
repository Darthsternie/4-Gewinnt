package viergewinnt;

import EgJavaLib2.netzwerk.*;
/**
 * Diese Klasse stellt die Clients dar, die sich mit dem Server verbinden, um zu spielen.
 *
 * @author (Nick Joraschky & Lennart Tschorn)
 * @version (12.12.2018)
 */
public class SpielClient extends Client{
    
    // Attribute und Beziehungen
    
    private SpielFenster fenster;

    // Konstruktor
    
    public SpielClient(String pServerIP){
        super(pServerIP,1234);
    }
    
    // Verarbeitet Nachrichten, die der Client vom Server bekommt.
    @Override
    public void processMessage(String pMessage){
        // Erstellt einen Eingabedialog zum Erfragen des Namens des Spielers und schickt diesen an den Server.
        if(pMessage.startsWith("NEWNAME")){
            String n = null;
            while(n == null || n.equals("")){
                n = fenster.erfrageName();
            }
            this.send("NAME " + n);
        }
        // Zeigt eine Fehlermeldung an.
        else if(pMessage.startsWith("ERROR ")){
            String error = pMessage.replaceFirst("ERROR ", "");
            fenster.myView.zeigeInfoDialog(error);
        }
        // Aktualisiert den Namen von Spieler 1 in der Oberfläche.
        else if(pMessage.startsWith("UPDATENAME1 ")){
            String name = pMessage.replaceFirst("UPDATENAME1 ", "");
            fenster.getSpielerEins().setText(name);
        }
        // Aktualisiert den Namen von Spieler 2 in der Oberfläche und zeigt an, dass das Spiel aktiv ist, wenn beide Spieler verbunden sind.
        else if(pMessage.startsWith("UPDATENAME2 ")){
            String name = pMessage.replaceFirst("UPDATENAME2 ", "");
            fenster.getSpielerZwei().setText(name);
            if(!fenster.getSpielerZwei().getText().equals("Spieler 2")){
                fenster.setzeErgebnisText("Spiel aktiv");
            }
        }
        // Aktualisiert die Farbe, mit der der Spieler spielt.
        else if(pMessage.startsWith("UPDATECOLOR ")){
            String color = pMessage.replaceFirst("UPDATECOLOR ", "");
            fenster.setzeSpielerFarbe(color);
        }
        // Aktualisiert das Spielbrett, nachdem ein Spielstein gesetzt wurde.
        else if(pMessage.startsWith("UPDATEFELD ")){
            String s = pMessage.replaceFirst("UPDATEFELD ", "");
            String[] strings = s.split(":");
            fenster.einwerfen(Integer.parseInt(strings[0]), strings[1]);
            if(strings[1].equals("yellow")){
                fenster.spielerZweiAktiv();
            }
            else{
                fenster.spielerEinsAktiv();
            }
        }
        // Zeigt das Ergebnis des Spiels an und verstekt den grünen Punkt zur Kennzeichnung des aktuellen Spielers.
        else if(pMessage.startsWith("END ")){
            String endMessage = pMessage.replaceFirst("END ", "");
            fenster.getAktiv().setHidden(true);
            fenster.setzeErgebnisText(endMessage);
        }
        // Zeigt eine Fehlermeldung, wenn bereits 2 Spieler mit dem Server verbunden sind und schließt das Fenster.
        else if(pMessage.startsWith("DISCONNECT")){
            fenster.myView.zeigeInfoDialog("Es läuft bereits ein Spiel auf diesem Server! Ihr Fenster wird nun geschlossen.");
            fenster.myView.beendeProgramm();
        }
    }
    
    // Gib- und Setzte-Methoden
    
    public void setzeFenster(SpielFenster pFenster){
        fenster = pFenster;
    }

}