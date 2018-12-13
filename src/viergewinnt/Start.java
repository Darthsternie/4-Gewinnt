package viergewinnt;

import javax.swing.JOptionPane;
/**
 * Diese Klasse wird beim Starten des Programms benutzt und leitet das gesamte Programm ein.
 *
 * @author (Nick Joraschky & Lennart Tschorn)
 * @version (12.12.2018)
 */
public class Start
{
    // Konstruktor
    
    public Start(){
        
        // Erstellt einen Dialog, um zu entscheiden, ob man Server und Client oder nur Client sein möchte.
        
        Object[] optionen = {"Server starten + als Client mitspielen", "Nur als Client mitspielen","Abbrechen"};

        int eingabe =   JOptionPane.showOptionDialog(null, "Was möchtest du machen?", "Vier gewinnt! Spielstart", 
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, optionen, optionen[1]);

        switch (eingabe) {
            // Neuer Server wird erstellt (Clinet wird in der Klasse SpielServer erstellt).
            case 0:
                SpielServer s = new SpielServer();
                break;
            // Erstellt Dialog zur Eingabe der Server-IP und erstellt neues Fenster mit entsprechender IP (Client wird in der Klasse SpielFenster erstellt).
            case 1:
                String serverIP = (String)JOptionPane.showInputDialog( null, "Wie lautet die Server-IP?", "Gleich geht's los!", JOptionPane.QUESTION_MESSAGE,null, null, "127.0.0.1");
                if ((serverIP != null) && (serverIP.length() >6)) {
                    SpielFenster f = new SpielFenster(serverIP);
                }   break;
            default:
                System.exit(0);
        }
    }
    
    // Methoden

    public static void main(String[] args){
        Start s = new Start();
    }
}
