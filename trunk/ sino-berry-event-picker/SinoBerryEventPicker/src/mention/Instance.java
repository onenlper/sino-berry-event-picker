package mention;

public class Instance {
	String character;
	
	String label;
	
	// indicate the start and end of annotated file
	boolean start = false;
	boolean end = false;
	
	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Instance(String character) {
		this.character = character;
		this.label = "O";
	}
	
	public Instance(String character, String label) {
		this.character = character;
		this.label = label;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(character).append(" ").append(label);
		return sb.toString();
	}
	
	
}
