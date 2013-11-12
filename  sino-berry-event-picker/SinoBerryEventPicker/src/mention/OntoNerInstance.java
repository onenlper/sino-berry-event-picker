package mention;


public class OntoNerInstance extends Instance {
	
	int surname=0;
	
	public int getSurname() {
		return surname;
	}

	public void setSurname(int surname) {
		this.surname = surname;
	}

	public OntoNerInstance(String character) {
		super(character);
	}
	
	public OntoNerInstance(String character, String label) {
		super(character, label);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(character).append(" ").append(surname).append(" ").append(label);
		return sb.toString();
	}
}
