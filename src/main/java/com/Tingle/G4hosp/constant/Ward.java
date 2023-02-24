package com.Tingle.G4hosp.constant;

public enum Ward {
	ONE(301),
	TWO(302),
	THREE(303),
	FOUR(304),
	FIVE(305),
	SIX(306),
	SEVEN(307),
	EIGHT(308),
	NINE(309),
	TEN(310);
	
	private int number;
	
	Ward(int number){
		this.number= number;
	}

	public int getNum() {
		return number;
	}
	
}
