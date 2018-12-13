package viergewinnt;

import EgJavaLib2.egSas.*;
/**
 * Klasse zur Erzeugung von Pfeilen in der Spieloberfläche.
 *
 * @author (Nick Joraschky & Lennart Tschorn)
 * @version (12.12.2018)
 */
public class Pfeil extends Sprite{
    
    // Attribute und Beziehungen
    
    private Polygon poly;
    private Rectangle rec;
    private int spalte;
    
    // Konstruktor

    public Pfeil(){
        rec = new Rectangle(100,100, 50,50, "black");
        rec.moveTo(100,100);
        this.add(rec);
        poly = new Polygon(125, 150);
        poly.add(50,0);
        poly.add(0,50);
        poly.add(-50,0);
        this.add(poly);

        this.scale(0.4,0.4);    

    }
    
    // Gib- und Setzte-Methoden
    
    public int gibSpalte(){
        return spalte;   
    }

    public void setzeSpalte(int pSpalte){
        spalte = pSpalte;
    }

    public void setzeFarbe(String pFarbe){
        poly.setHexColor(pFarbe);
        rec.setHexColor(pFarbe);
    }

}
