package viergewinnt;
/**
 * Klasse zur Erzeugung von Spielern für die Spiellogik.
 *
 * @author (Nick Joraschky & Lennart Tschorn)
 * @version (12.12.2018)
 */
public class Spieler {
    
    // Attribute und Beziehungen
    
    private String name;
    
    // Konstruktor

    public Spieler(String pName){
        name = pName;
    }
    
    // Gib- und Setzte-Methoden

    public void setzeName(String pName) {
        name = pName;
    }

    public String gibName(){
        return name;
    }
    
}