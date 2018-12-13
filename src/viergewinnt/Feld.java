package viergewinnt;

import EgJavaLib2.egSas.*;
/**
 * Klasse zur Erzeugung von Feldern in der Spieloberfläche.
 *
 * @author (Nick Joraschky & Lennart Tschorn)
 * @version (12.12.2018)
 */
public class Feld extends Sprite{
    
    // Attribute
    
    private Rectangle innen;
    private Rectangle rand;
    private Circle kreis;
    
    private int spalte;
    private int zeile;
    
    private int breite;
    
    // Konstruktor
    
    public Feld(int pX, int pY) {
        breite = 50;
        spalte = -1;
        zeile = -1;
        rand = new Rectangle(pX, pY, breite, breite, "black");
        innen = new Rectangle(pX+2, pY+2, breite-4, breite-4, "white");
        kreis = new Circle(pX+2, pY+2, (breite-4)/2, "white");
        this.add(rand, innen, kreis);
    }
    
    // Gib- und Setzte-Methoden
    
    public void setzeFarbe(String pZeichen){
        kreis.setHexColor(pZeichen);
    }
    
    public int gibBreite(){
        return breite;
    }
    
    public void setzeDaten(int pSpalte, int pZeile){
        spalte = pSpalte;
        zeile = pZeile;
    }
    
    public int gibZeile(){
        return zeile;
    }
    
    public int gibSpalte(){
        return spalte;
    }
}