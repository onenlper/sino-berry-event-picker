package mention;


public class MentionInstance extends Instance{
	int surname=0;
	
	int leftBound=0;
	
	int rightBound=0;
	
	// part of speech feature
	String posFea;
	
	public String getNerFea() {
		return nerFea;
	}

	public void setNerFea(String nerFea) {
		this.nerFea = nerFea;
	}

	// ner feature
	String nerFea="O";
	
	public int getIsInNP() {
		return isInNP;
	}

	public void setIsInNP(int isInNP) {
		this.isInNP = isInNP;
	}

	//IN_LOCATION(c-1c0)
	int inLocation1=0;
	
	//IN_LOCATION(c0c1)
	int inLocation2=0;
	
	int location_suffix=0;
	
	//IS_PRONOUN(c0)
	int isPronoun1 = 0;
	
	//IS_PRONOUN(c-1c0)
	int isPronoun2 = 0;
	
	//IS_PRONOUN(c0c1)
	int isPronoun3 = 0;

	//IS_IN_NP, whether this character is in a certain noun phrase
	int isInNP = 0;
	
	public int getIsPronoun1() {
		return isPronoun1;
	}

	public void setIsPronoun1(int isPronoun1) {
		this.isPronoun1 = isPronoun1;
	}

	public int getIsPronoun2() {
		return isPronoun2;
	}

	public void setIsPronoun2(int isPronoun2) {
		this.isPronoun2 = isPronoun2;
	}

	public int getIsPronoun3() {
		return isPronoun3;
	}

	public void setIsPronoun3(int isPronoun3) {
		this.isPronoun3 = isPronoun3;
	}

	public int getLocation_suffix() {
		return location_suffix;
	}

	public void setLocation_suffix(int locationSuffix) {
		location_suffix = locationSuffix;
	}

	public int getSurname() {
		return surname;
	}

	public int getLeftBound() {
		return leftBound;
	}

	public void setLeftBound(int leftBound) {
		this.leftBound = leftBound;
	}

	public int getRightBound() {
		return rightBound;
	}

	public void setRightBound(int rightBound) {
		this.rightBound = rightBound;
	}

	public String getPosFea() {
		return posFea;
	}

	public int getInLocation1() {
		return inLocation1;
	}

	public void setInLocation1(int inLocation1) {
		this.inLocation1 = inLocation1;
	}

	public int getInLocation2() {
		return inLocation2;
	}

	public void setInLocation2(int inLocation2) {
		this.inLocation2 = inLocation2;
	}

	public void setPosFea(String posFea) {
		this.posFea = posFea;
	}

	public void setSurname(int surname) {
		this.surname = surname;
	}

	public MentionInstance(String character) {
		super(character);
	}
	
	public MentionInstance(String character, String label) {
		super(character, label);
	}
	
	public String getIN_ORGS_INTL() {
		return IN_ORGS_INTL;
	}

	public void setIN_ORGS_INTL(String iNORGSINTL) {
		IN_ORGS_INTL = iNORGSINTL;
	}

	public String getIN_PROP_INDUSTRY() {
		return IN_PROP_INDUSTRY;
	}

	public void setIN_PROP_INDUSTRY(String iNPROPINDUSTRY) {
		IN_PROP_INDUSTRY = iNPROPINDUSTRY;
	}

	public String getIN_PROP_ORG() {
		return IN_PROP_ORG;
	}

	public void setIN_PROP_ORG(String iNPROPORG) {
		IN_PROP_ORG = iNPROPORG;
	}

	public String getIN_PROP_OTHER() {
		return IN_PROP_OTHER;
	}

	public void setIN_PROP_OTHER(String iNPROPOTHER) {
		IN_PROP_OTHER = iNPROPOTHER;
	}

	public String getIN_PROP_PEOPLE() {
		return IN_PROP_PEOPLE;
	}

	public void setIN_PROP_PEOPLE(String iNPROPPEOPLE) {
		IN_PROP_PEOPLE = iNPROPPEOPLE;
	}

	public String getIN_PROP_PRESS() {
		return IN_PROP_PRESS;
	}

	public void setIN_PROP_PRESS(String iNPROPPRESS) {
		IN_PROP_PRESS = iNPROPPRESS;
	}

	public String getIN_WHOWHO_CHINA() {
		return IN_WHOWHO_CHINA;
	}

	public void setIN_WHOWHO_CHINA(String iNWHOWHOCHINA) {
		IN_WHOWHO_CHINA = iNWHOWHOCHINA;
	}

	public String getIN_WHOWHO_INTER() {
		return IN_WHOWHO_INTER;
	}

	public void setIN_WHOWHO_INTER(String iNWHOWHOINTER) {
		IN_WHOWHO_INTER = iNWHOWHOINTER;
	}

	public String getIN_PROP_PLACE() {
		return IN_PROP_PLACE;
	}

	public void setIN_PROP_PLACE(String iNPROPPLACE) {
		IN_PROP_PLACE = iNPROPPLACE;
	}

	String IN_ORGS_INTL="O";
	String IN_PROP_INDUSTRY="O";
	String IN_PROP_ORG="O";
	String IN_PROP_OTHER="O";
	String IN_PROP_PEOPLE="O";
	String IN_PROP_PLACE="O";
	String IN_PROP_PRESS="O";
	String IN_WHOWHO_CHINA="O";
	String IN_WHOWHO_INTER="O";
	
	//is English character
	int isEnglish = 0;
	
	// is capitalized English character
	int isUpcaseEnglish = 0;
	
	public int getIsEnglish() {
		return isEnglish;
	}

	public void setIsEnglish(int isEnglish) {
		this.isEnglish = isEnglish;
	}

	public int getIsUpcaseEnglish() {
		return isUpcaseEnglish;
	}

	public void setIsUpcaseEnglish(int isUpcaseEnglish) {
		this.isUpcaseEnglish = isUpcaseEnglish;
	}
	
	String POS09;
	
	String POS10;

	public String getPOS09() {
		return POS09;
	}

	public void setPOS09(String pOS09) {
		POS09 = pOS09;
	}

	public String getPOS10() {
		return POS10;
	}

	public void setPOS10(String pOS10) {
		POS10 = pOS10;
	}

	// feature format: CHAR IS_SURNAME IS_LEFT_BOUND IS_RIGHT_BOUND POS LOCAION_SUFFIX IN_LOCATION1 IN_LOCATION2 IS_PRONOUN1 
	// IS_PRONOUN2 IS_PRONOUN3 IS_IN_NP NER_FEATURE IN_ORGS_INTL IN_PROP_INDUSTRY IN_PROP_ORG IN_PROP_OTHER IN_PROP_PEOPLE IN_PROP_PLACE
	// IN_PROP_PRESS IN_WHOWHO_CHINA IN_WHOHWO_INTER IS_ENGLISH ISUPCASEENGLISH POS09 POS10 LABEL
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(character).append(" ").append(surname).append(" ").
		append(leftBound).append(' ').append(rightBound).append(' ').append(posFea).append(" ")
		.append(this.location_suffix).append(" ").append(this.inLocation1).append(" ").append(this.inLocation2).append(" ")
		.append(this.isPronoun1).append(" ").append(this.isPronoun2).append(" ").append(this.isPronoun3).append(" ")
		.append(this.isInNP).append(" ").append(this.nerFea).append(" ").append(this.IN_ORGS_INTL).append(" ")
		.append(this.IN_PROP_INDUSTRY).append(" ").append(this.IN_PROP_ORG).append(" ").append(this.IN_PROP_OTHER).append(" ")
		.append(this.IN_PROP_PEOPLE).append(" ").append(this.IN_PROP_PLACE).append(" ").append(this.IN_PROP_PRESS).append(" ")
		.append(this.IN_WHOWHO_CHINA).append(" ").append(this.IN_WHOWHO_INTER).append(" ").append(this.isEnglish).append(" ").
		append(this.isUpcaseEnglish).append(" ").append(this.POS09).append(" ").append(this.POS10).append(" ").append(label);
		return sb.toString();
	}
}
