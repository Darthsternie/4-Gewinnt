package viergewinnt;

import EgJavaLib2.egSas.*;
/**
 * Diese Klasse stellt die Oberfläche dar.
 *
 * @author (Nick Joraschky & Lennart Tschorn)
 * @version (12.12.2018)
 */
public class SpielFenster extends SasApp{
    
    // Attribute und Beziehungen
    
    private Feld[][] felder = new Feld[7][7];
    
    private Text spielerEins = new Text(500,150,"Spieler 1");
    private Text spielerZwei = new Text(500,200,"Spieler 2");
    private Text ergebnis = new Text(500,250,"Spiel nicht aktiv");
    private Circle aktiv = new Circle(465,155,10, "green");
    private Pfeil[] pfeilArray = new Pfeil[7];
    private String spielerFarbe;
    
    private int anzahlProSpalte[] = {0,0,0,0,0,0,0};
    
    private SpielClient client;
    
    // Konstruktor
    
    public SpielFenster(String pServerIP) {
        this.zeichneSpielfeld();
        spielerFarbe = "black";
        aktiv.setHidden(false);
        this.zeichnePfeile();
        myView.setSize(1000,600);
        client = new SpielClient(pServerIP);
        client.setzeFenster(this);
    }
    
    // Zeichnet die Pfeile.
    public void zeichnePfeile(){
        int xStart = 50;
        int yStart = 85;
        
        for(int i = 0; i < 7; i++){
            pfeilArray[i] = new Pfeil();
            pfeilArray[i].setzeFarbe(spielerFarbe);
            pfeilArray[i].moveTo(xStart, yStart);
            pfeilArray[i].setzeSpalte(i);
            xStart = xStart + 50;
        }
    }
    
    // Zeichnet das Spielfeld und setzt die entsprechenden Daten für die einzelnen Felder.
    public void zeichneSpielfeld(){
        int xStart = 50;
        int yStart = 150;
        
        for(int zeile = 0; zeile < 7; zeile++){
            for(int spalte = 0; spalte < 7; spalte++){
                Feld f = new Feld(xStart, yStart);
                f.setzeDaten(spalte, zeile);
                felder[spalte][zeile] = f;
                xStart = xStart + 50;
            }
            xStart = 50;
            yStart = yStart + 50;
        }
    }
    
    // Verschiebt den grünen Punkt (zur Kennzeichnung des aktiven Spielers) zu Spieler 1.
    public void spielerEinsAktiv(){
        aktiv.moveTo(465,155);
    }
    
    // Verschiebt den grünen Punkt (zur Kennzeichnung des aktiven Spielers) zu Spieler 2.
    public void spielerZweiAktiv(){
        aktiv.moveTo(465,205);
    }
    
    // Erfragt den Namen des Spielers in einem Eingabedialog und gibt diesen zurück.
    public String erfrageName(){
        return myView.zeigeEingabeDialog("Wie heißt du?");
    }
    
    // Sendet die Nachricht an den Server, dass ein Spielstein seiner Farbe an die 
    // entsprechende Position gesetzt werden soll, wenn ein Pfeil angeklickt wird. 
    @Override
    public void mouseClicked(){
        Object o = myView.getLastClicked();
        if( o instanceof Pfeil){
            Pfeil p = (Pfeil) o;
            client.send("ACTION " + p.gibSpalte() + ":" + (6 - anzahlProSpalte[p.gibSpalte()]) + ":" + spielerFarbe);
            //myView.zeigeInfoDialog("Es wurde folgende Spalte ausgewählt: "+p.gibSpalte());
        }
    }
    
    // Verfärbt die Pfeile in die Farbe der Spielsteine des Spielers, wenn man mit der über diese fährt
    @Override
    public void mouseMoved(){
        if(pfeilArray[6] != null){
            for(int i = 0; i < 7; i++){
                if(pfeilArray[i].contains(myMouse.getX(), myMouse.getY())){
                    pfeilArray[i].setzeFarbe(spielerFarbe);
                }
                else{
                    pfeilArray[i].setzeFarbe("black");
                }
            } 
        } 
    }
    
    // Setzt die Farbe eines Felds, in der ein Spielstein eingeworfen wurde.
    public void einwerfen(int pSpalte, String pFarbe){
        int zeile = 6 - anzahlProSpalte[pSpalte];
        felder[pSpalte][zeile].setzeFarbe(pFarbe);
        anzahlProSpalte[pSpalte] = anzahlProSpalte[pSpalte] + 1;
    }
    
    // Gib- und Setze-Methoden
    
    public void setzeSpielerFarbe(String pFarbe){
        spielerFarbe = pFarbe;
    }
    
    public void setzeErgebnisText(String pText){
        ergebnis.setText(pText);
    }
    
    public Text getSpielerEins(){
        return spielerEins;
    }
    
    public Text getSpielerZwei(){
        return spielerZwei;
    }

    public Circle getAktiv() {
        return aktiv;
    }
       
}