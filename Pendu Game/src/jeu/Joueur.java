package jeu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;


/*

 */

/**
 *
 * @author TOSHIBA
 */

public class Joueur {
        final int nbr=100; // nombre des scores possible
	private String pseudo;
	 private int meilleurScore;
	 private int sessionScore;
	 private int [] scores;
	  
	/**==================== Getters and Setters =======================**/
	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public int getMeilleurScore() {
		return meilleurScore;
	}

	public void setMeilleurScore(int meilleurScore) {
		this.meilleurScore = meilleurScore;
	}

	public int getSessionScore() {
		return sessionScore;
	}

	public void setSessionScore(int sessionScore) {
		this.sessionScore = sessionScore;
	}
	/**===============================================================**/

	public Joueur (String pseudo,int meilleurScore, int sessionScore)
	{ 
	this.pseudo=pseudo;	 
	this.sessionScore=sessionScore; 
        this.meilleurScore=meilleurScore;
	}
        
	/**======================= Sauvgarder Joueur ====================**/
       public void Sauvgarder ()
       { 
           
           
           BufferedReader in =null;
           BufferedWriter out =null;
           String ligne; //Contient la ligne
           int i=0;
           Vector<String> valeur=new Vector<String>();
           
                try {
                    in = new BufferedReader(new FileReader("Comptes.txt"));
                    while ((ligne=in.readLine())!= null)
                            {
                                
                                String[] parts = ligne.split(";");
                                if( parts[0].equals(this.pseudo)) // Tester si c'est le qui joue actuellement
                                { 
                                    parts[1]=Integer.toString(this.meilleurScore);
                                }
                               ligne=parts[0]+";"+parts[1]+";";
                               valeur.add(ligne);
                               System.out.printf(ligne);
                               i++;
                           }
                    in.close();
                    out = new BufferedWriter(new FileWriter("Comptes.txt"));
                    for (int k=0;k<i;k++)
                    {  
                    ligne=valeur.elementAt(k); // Recuperer la ligne
                    System.out.printf(ligne);
                    out.write(ligne);// Reecrire la ligne dans le fichier
                    out.write("\n");
                    }
                  out.close();
                    } 
                  catch (IOException e)
                      {}
       
       }
        
      /**==============================================================**/
}
