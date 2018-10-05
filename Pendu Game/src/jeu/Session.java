package jeu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TOSHIBA
 */
import java.io.*;
import java.util.Random;
import java.util.Vector;
import java.util.ArrayList;


public class Session {

	private final int nbrMot=10;
	private Mot [] mots = new Mot[nbrMot];;
	private Joueur joueur;
	private int nbrMotRate = 0;
	private int numMotCourant = 0;
        
	
	public Session (Joueur joueur) 
	{ 
	  this.joueur=joueur;
	}
        
        public int getMotRate () {return this.nbrMotRate;}
        
         public void setMotRate (int nbrMotRate) { this.nbrMotRate= nbrMotRate;}
        
        public Joueur getJoueur() {return joueur;}
        
        public int getnbrMot() {return nbrMot;}
        
        public Mot[] getTabMot () { return this.mots; }
        
        public void setTabMot (Mot [] mots) {  this.mots=mots; }
        
        public int getnumMotCourant () { return this.numMotCourant; }
        
        public void setnumMotCourant (int numMotCourant) {  this.numMotCourant = numMotCourant; }
        
        public static String getRandomElement (Vector v) {
		Random generator = new Random();
		int rnd = generator.nextInt(v.size() - 1);
		return (String)v.get(rnd); 
		}
	
        public void genererMot ()
	{
	String chaine="";
	String fichier ="mots.poo";
	int i=0;
        int k=0;
        String ligne;
        Vector<String> valeur=new Vector<String>();
        ArrayList<String> tab = new ArrayList<String>(100);

        
        
	
	try{
	BufferedReader br =new BufferedReader(new InputStreamReader(new FileInputStream("mots.poo"), "UTF-8"));
    
		
		while ((ligne=br.readLine())!=null ){
                        valeur.add(ligne);
                        i++;
                        chaine+=ligne+"\n";
		}
                for(int j=0;j<nbrMot;j++)	
               {	
               //int unique= pickRandom(1,nbrMot);
               //String string = valeur.get(unique);
               
	       String string =getRandomElement(valeur);
               String[] parts = string.split(";");
        
          
               if (parts[2].length()>6 && !tab.contains(parts[2]))
               { 
                 tab.add(parts[2]);
                 mots[j]= new Mot (parts[0],parts[1],parts[2]);}
               else {j--;}
		}
		br.close(); 
	}		
	catch (Exception e){
		System.out.println(e.toString());
	}	
		
	}
}
