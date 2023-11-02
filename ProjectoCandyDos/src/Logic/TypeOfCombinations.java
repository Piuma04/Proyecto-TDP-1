package Logic;

public  final  class TypeOfCombinations {

	public static Combination setTypeOfCombination(char ToC, Board b) {
		Combination comb = null;
		switch(ToC) {
		case 'L': {
			comb = new OnlyLinealCombinationsPattern(b);
			break;
		}
		case 'C': {
			comb = new ClassicPattern(b);
			break;
		}
		default: {
			comb = new ClassicPattern(b);
			break;
		}
		}
		
		
		return comb;
	}
	
}
