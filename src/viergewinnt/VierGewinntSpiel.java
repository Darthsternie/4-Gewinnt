package viergewinnt;


/**
 * Diese Klasse ist für die Spiellogik zuständig.
 * 
 * @author (Nick Joraschky & Lennart Tschorn) 
 * @version (12.12.2018)
 */
public class VierGewinntSpiel{
    
    // Attribute und Beziehungen
    
    private String[][] spielfeld = new String[7][7];
    private boolean sieg;
    private Spieler spielerEins;
    private Spieler spielerZwei;
    private Spieler aktuellerSpieler;
    private int anzahlSpieler;
    private int anzahlZeichen;
    private String ergebnisDesSpiels;

    private SpielServer server;

    // Konstruktor
    
    public VierGewinntSpiel()
    {
        sieg = false;
        anzahlSpieler = 0;
        anzahlZeichen = 0;
        spielerEins = new Spieler("Spieler 1");
        spielerZwei = new Spieler("Spieler 2");
        
        for(int spalte = 0; spalte < 7; spalte++){
            for(int zeile = 0; zeile < 7; zeile++){
                spielfeld[spalte][zeile] = " ";
            } 
        }

    }
    
    // Methoden
    
    // Startet das Spiel.
    public void spielStarten(){
        sieg = false;
        aktuellerSpieler = spielerEins;
    }
    
    // Setzt das entsprechende Zeichen (Spieler 1 = X, Spieler 2 = O) in die entsprechende Stelle des Spielfeld-Arrays.
    public void setzeZeichen(int pSpalte, int pZeile){
        if(spielfeld[pSpalte][pZeile].equals(" ")){
            if(aktuellerSpieler == spielerEins){
                spielfeld[pSpalte][pZeile] = "X";
                anzahlZeichen++;
                this.checkeAufEnde();
                this.spielerWechsel();
            }
            else if(aktuellerSpieler == spielerZwei){ 
                spielfeld[pSpalte][pZeile] = "O";
                anzahlZeichen++;
                this.checkeAufEnde();
                this.spielerWechsel();
            }
            else{
                System.err.println("Fehler bei Zeichensetzen: kein aktueller Spieler");
            }
        }
        else{
            System.err.println("Fehler bei Zeichensetzen: Feld schon markiert");
        }

    }
    // Sorgt dafür, dass der aktuelle Spieler getauscht wird.
    public void spielerWechsel(){
        if(aktuellerSpieler == spielerEins){
            aktuellerSpieler = spielerZwei;
        }
        else if(aktuellerSpieler == spielerZwei){ 
            aktuellerSpieler = spielerEins;
        }
        else{
            System.err.println("Fehler bei Spielerwechsel: kein aktueller Spieler");            
        }
    }
    
    // Überprüft, ob ein Spieler gewonnen hat und ruft spielEnde() mit dieser Information (boolean) als Parameter auf.
    public void checkeAufEnde(){
        
        // Vertikal
        for(int zeile = 0; zeile < 7; zeile++){
            for(int position = 0; position < 4; position++){

                String zeichen1 = spielfeld[position][zeile];
                String zeichen2 = spielfeld[position + 1][zeile];
                String zeichen3 = spielfeld[position + 2][zeile];
                String zeichen4 = spielfeld[position + 3][zeile];
                
                if(!(zeichen1.equals(" ") || zeichen2.equals(" ") || zeichen3.equals(" ") || zeichen4.equals(" "))){
                    if(zeichen1.equals(zeichen2) && zeichen2.equals(zeichen3) && zeichen3.equals(zeichen4)){
                        sieg = true;
                        break;
                    }
                }
            }
        }
        // Horizontal
        if(sieg == false){
            for(int spalte = 0; spalte < 7; spalte++){
                for(int position = 0; position < 4; position++){

                    String zeichen1 = spielfeld[spalte][position];
                    String zeichen2 = spielfeld[spalte][position + 1];
                    String zeichen3 = spielfeld[spalte][position + 2];
                    String zeichen4 = spielfeld[spalte][position + 3];

                    if(!(zeichen1.equals(" ") || zeichen2.equals(" ") || zeichen3.equals(" ") || zeichen4.equals(" "))){
                        if(zeichen1.equals(zeichen2) && zeichen2.equals(zeichen3) && zeichen3.equals(zeichen4)){
                            sieg = true;
                            break;
                        }
                    }
                }
            }
        }
        // Diagonal
        if(sieg == false){
            for(int spalte = 0; spalte < 7; spalte++){
                for(int zeile = 0; zeile < 7; zeile++){
                    if(spalte + 3 < 7 && zeile + 3 < 7){
                        String zeichen1 = spielfeld[spalte][zeile];
                        String zeichen2 = spielfeld[spalte+1][zeile + 1];
                        String zeichen3 = spielfeld[spalte+2][zeile + 2];
                        String zeichen4 = spielfeld[spalte+3][zeile + 3];

                        if(!(zeichen1.equals(" ") || zeichen2.equals(" ") || zeichen3.equals(" ") || zeichen4.equals(" "))){
                            if(zeichen1.equals(zeichen2) && zeichen2.equals(zeichen3) && zeichen3.equals(zeichen4)){
                                sieg = true;
                                break;
                            }
                        }
                    }
                }
            }

        }
        // Diagonal (anders rum)
        if(sieg == false){
            for(int spalte = 6; spalte >= 0; spalte--){
                for(int zeile = 6; zeile >= 0; zeile--){
                    if(spalte - 3 >= 0 && zeile  - 3 >= 0){
                        String zeichen1 = spielfeld[spalte][zeile];
                        String zeichen2 = spielfeld[spalte-1][zeile - 1];
                        String zeichen3 = spielfeld[spalte-2][zeile - 2];
                        String zeichen4 = spielfeld[spalte-3][zeile - 3];

                        if(!(zeichen1.equals(" ") || zeichen2.equals(" ") || zeichen3.equals(" ") || zeichen4.equals(" "))){
                            if(zeichen1.equals(zeichen2) && zeichen2.equals(zeichen3) && zeichen3.equals(zeichen4)){
                                sieg = true;
                                break;
                            }
                        }
                    }
                }
            }

        }     
        this.spielEnde(sieg);       
    }

    // Wenn ein Sieger feststeht oder es untenschieden ist wird das Ergebnis an die Clients gesendet.
    public void spielEnde(boolean pSieg){
        ergebnisDesSpiels = "Spiel läuft noch!";
        if(pSieg){
            ergebnisDesSpiels = "Das Spiel ist vorbei! Sieger ist: "+ aktuellerSpieler.gibName();
            server.sendToAll("END " + ergebnisDesSpiels);
        }
        else{
            if(anzahlZeichen == 49){
                pSieg = true;
                ergebnisDesSpiels = "Das Spiel ist vorbei! Unentschieden!";
                server.sendToAll("END " + ergebnisDesSpiels);
            }

        }
    }
    
    // Setzt den Namen des noch nicht besetzten Spielers und erhöht die Anzahl der Spieler, bis bereits 2 Spieler vorhanden sind.
    public void spielerHinzuFügen(String pName){
        switch (anzahlSpieler) {
            case 0:
                spielerEins.setzeName(pName);
                anzahlSpieler++;
                break;
            case 1:
                spielerZwei.setzeName(pName);
                anzahlSpieler++;
                this.spielStarten();
                break;
            default:
                System.err.println("Fehler bei Spieler Hinzufügen: Spiel voll");
                break;
        }

    }
    
    // Gib- und Setzte-Methoden
    
    public String gibNameSpielerEins(){
        return spielerEins.gibName();
    }

    public String gibNameSpielerZwei(){
        return spielerZwei.gibName();
    }
    
    public Spieler gibSpielerEins(){
        return spielerEins;
    }

    public Spieler gibSpielerZwei(){
        return spielerZwei;
    }
    
    public Spieler gibAktuellerSpieler(){
        return aktuellerSpieler;
    }
    
    public boolean gibSieg(){
        return sieg;
    }
    
    public void setzeSieg(boolean pSieg){
        sieg = pSieg;
    }
    
    public void setzeServer(SpielServer pServer){
        server = pServer;
    }
}