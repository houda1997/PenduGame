package jeu;


/* 
 */

/**
 *
 * @author TOSHIBA
 */
public abstract class Lettre {

	char Rep; // la reponse
	protected int nbre_tentative = 0;
        protected boolean rate=false; 
	
	public Lettre (char Rep)
	{
		this.Rep=Rep;
	}
        
        public char getReponse () {return this.Rep;}
        public int getNbre_tentative () {return this.nbre_tentative;}
        
	public boolean Tester (char l){
            return (Rep == Character.toLowerCase(l));
        }
        public boolean getRate(){return rate ;} 
        public abstract int CalculPoint (char l);
	public abstract boolean VerifTentatif ();
}
