package jeu;

/*
 */

/**
 *
 * @author TOSHIBA
 */

import java.io.*;
import java.util.*;

public class Jeu  {
	private final int nbreCmptMax = 100; //nombre des comptes principals
	private Joueur [] joueurs = new Joueur [nbreCmptMax]; // Tableau des comptes crees
	private int nbreCmpt = 0; // nombre des comptes crees
	private int meilleurS; // Meilleur score du joueur courant

        public int getMeilleurS() {
		return meilleurS;
	}	
	public boolean EstValide(String pseudo) //tester si le pseudo est valide
	{
		char [] pseudoTab = pseudo.toCharArray();
		return (  'A'<= pseudoTab[0] && pseudoTab[0] <='z');
	}
	public Joueur getJoueur () {return this.joueurs[nbreCmpt];}
        
	public void SetJoueur (Joueur j)
	{
                FileWriter fichier=null;
		joueurs[nbreCmpt] = j;
		nbreCmpt ++;
                String chaine=j.getPseudo()+";"+j.getMeilleurScore();
        //Ouvrir un fichier pour sauvgarder 
                  try {
                      fichier = new FileWriter("Comptes.txt",true);
                      fichier.write(chaine);
                      fichier.write(";");
                      fichier.write("\n");
                      fichier.close();
                      // BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Comptes.txt")));
                      // writer.write(chaine);
                     //  writer.close();
                       } 
                  catch (IOException e)
                      {}
	}
	public boolean Existe(String pseudo) throws IOException //tester si le pseudo est unique 
	{
          BufferedReader in = null;
          String c;
          
        
        try {  in = new BufferedReader(new FileReader("Comptes.txt"));          
               while ((c = in.readLine()) != null )
               {
               String[] parts = c.split(";");
               if (pseudo.equals(parts[0])) {this.meilleurS=Integer.parseInt(parts[1]);
                                             return true;}
               }       
            }

       finally {
            if (in != null) 
                in.close();                  
               }

        return false; }
}