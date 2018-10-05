package jeu;

/*
 *
 */

/**
 *
 * 
 */
import java.util.*;
public class Mot {
	private final int coef_def = 3;
	private final int coef_syn = 2;
	private final int coef_antony = 1;
	
	
	private Indicateur indicateur;
	private String question;
	private int score = 0;
        private int NbrLettres =0; //nombre des lettres dans le mot
	private Lettre [] lettres;
	private boolean rate = false;
        private int nbreMalus=0; //nbre case Proposition +multichance
        private int malus =0; 
        private String motJuste;
       
        //Test : afficher la reponse 
        //String mot;

        public Indicateur getIndicateur() {return this.indicateur;}
        
        public String getquestion() {return this.question;}
        
        public String getReponse() {return this.motJuste;}
        
        public int getscore() {return this.score;}
        
        public Lettre [] getLettres() {return this.lettres;}
        
         public int getNbrLettres () {return NbrLettres; } //pour l'initialisation des labels
        
	public Mot (String id,  String question, String Reponse)
	{
            //mot = Reponse;
                this.motJuste=Reponse;
		char [] ReponseTab = Reponse.toCharArray();
                NbrLettres = ReponseTab.length;
		this.question = question;
		switch(id)
                 {
                    case "DEFINITION" :
                        indicateur = Indicateur.valueOf("DEFINITION");
                        break;
                    case "SYNONYME" :
                        indicateur = Indicateur.valueOf("SYNONYME");
                        break;
                    case "ANTONYME" :
                        indicateur = Indicateur.valueOf("ANTONYME");
                        break;
                 }//Indicateur.valueOf(String)
		lettres = new Lettre [NbrLettres];
		int i =0; // le parcours pour l'instanciations des lettres
		int j =0;  // pour la generation aleatoire des lettre : Multi chance, zero chance ou proposition
		int zeroChanProp=0;
		Random r = new Random();
		int bornSup =3;
		for (i=0; i< NbrLettres; i++) //instantiation des lettres
		{
			j = r.nextInt(bornSup);
			switch (j)
			{
			case 0:
				lettres [i] = new MultiChance (ReponseTab[i]);
                                this.nbreMalus++;
				break;
                        case 1:
				lettres [i] = new Proposition (ReponseTab[i]);
                                this.nbreMalus++;
                                zeroChanProp ++;
                                if (zeroChanProp > 3) bornSup =1;
				break;
			case 2:
				lettres [i] = new ZeroChance (ReponseTab[i]);
				zeroChanProp ++;
				if (zeroChanProp > 3) bornSup =1; // condition : Nombre de cases zeorchance <= 6
				break;
			}
		}
	}
	
       
       
        //public String getMot(){return mot;}
        
	public boolean ScoreLettre (char l, int i) //calcul le score en indiquant si le mot est rate ou pas
	{
		this.score = score + lettres[i].CalculPoint(l);
                this.rate = lettres[i].getRate();
                if(lettres[i] instanceof Malus ) 
                    this.malus = this.malus + ((Malus)lettres[i]).CalculMalus(l);
                return rate; 
	}
        public int CalculScore() {
            switch(this.indicateur)
                 {
                    case DEFINITION :
                        score = score * this.coef_def;
                        break;
                    case SYNONYME :
                        score = score *this.coef_syn;
                        break;
                    case ANTONYME :
                         score = score *this.coef_antony;
                        break;
                 }
            if (this.nbreMalus>5) score = score + this.malus;
            return score;
        }
}
