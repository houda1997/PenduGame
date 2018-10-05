package jeu;


/**
 *
 * @author TOSHIBA
 */
public class Proposition extends ZeroChance implements Malus {
    
        private final int point =2;
	public Proposition (char Rep)
	{
		super(Rep);
	}

	@Override
	public int CalculMalus (char l)
	{
		if (Tester(l)) return 0;
		else return (-1);
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
}
