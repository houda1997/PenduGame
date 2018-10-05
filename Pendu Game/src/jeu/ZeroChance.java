package jeu;



/*
 
 */

/**
 *
 * @author TOSHIBA
 */
public class ZeroChance extends Lettre {

	protected final int echec_tolere = 0;
        private final int point =3;
	
	public ZeroChance(char Rep)
	{
		super(Rep);
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
        @Override
	public boolean VerifTentatif ()
	{
		return (nbre_tentative > echec_tolere);
	}
}
