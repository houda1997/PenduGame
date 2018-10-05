package jeu;

/**
 *
 * @author TOSHIBA
 */
public class MultiChance extends Lettre implements Malus {

	private final int echec_tolere = 2;
        private final int point =1;
	
	
	public MultiChance(char Rep)
	{
		super(Rep);
	}
        @Override
	public boolean VerifTentatif ()
	{
		return (nbre_tentative > echec_tolere);
	}
        public int TentativeRestant (){
            return (echec_tolere - nbre_tentative);
        }
        @Override
        public int CalculPoint(char l){
            nbre_tentative ++;
            if (Tester(l)) return point;
            else {
                rate = VerifTentatif();
                return 0;
            }
        }
        
	public int CalculMalus (char l)
	{
		if (Tester(l)) return 0;
		else return -2;
	}
}
