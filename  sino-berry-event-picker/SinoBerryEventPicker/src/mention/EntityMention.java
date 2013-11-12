package mention;

import java.util.ArrayList;

import model.syntaxTree.MyTreeNode;


public class EntityMention implements Comparable<EntityMention> {
	public String source = "";
	public int hashCode() {
		String str = this.getS() + "," + this.getE();
		return str.hashCode();
	}
	public MyTreeNode maxTreeNode;
	public boolean singleton = false; 
	public int PRONOUN_TYPE;
	public Entity entity;
	
	public int anaphoric = 0;
	
	public boolean equals(Object em2) {
		if(this.getS() == ((EntityMention)em2).getS() && this.getE() == ((EntityMention)em2).getE()) {
			return true;
		} else {
			return false;
		}
	}
	
	public double typeConfidence = Double.NEGATIVE_INFINITY;
	
	public double subTypeConfidence = Double.NEGATIVE_INFINITY;
	
	public int start;
	public int end;

	public String extent="";

	public MyTreeNode treeNode;
	
	public int sentenceId;
	public int startLeaf;
	public int endLeaf;
	
	public int headStart;
	public int headEnd;
	public String head="";
	
	public boolean isNNP=false;
	public boolean isSub=false;
	public boolean isPronoun=false;
	public ArrayList<String> modifyList = new ArrayList<String>();// record all the modifiers
	public int isAnimacy;
	public boolean isProperNoun;
	public int number=2;
	public int gender=2;
	
	public String ner = "OTHER";
	public String semClass="OTHER";
	public String subType = "O-OTHER";
	
	public int index;
	public EntityMention antecedent;
	public int entityIndex;
	
	public int position[];
	
	public String getContent() {
		return this.head;
	}
	
	public int getS() {
		return this.headStart;
	}
	
	public int getE() {
		return this.headEnd;
	}

	public boolean flag = false;

	String type;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public String getExtent() {
		return extent;
	}

	public void setExtent(String extent) {
		this.extent = extent;
	}

	public int getHeadStart() {
		return headStart;
	}

	public void setHeadStart(int headStart) {
		this.headStart = headStart;
	}

	public int getHeadEnd() {
		return headEnd;
	}

	public void setHeadEnd(int headEnd) {
		this.headEnd = headEnd;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public EntityMention getAntecedent() {
		return antecedent;
	}

	public void setAntecedent(EntityMention antecedent) {
		this.antecedent = antecedent;
	}

	public int getEntityIndex() {
		return entityIndex;
	}

	public void setEntityIndex(int entityIndex) {
		this.entityIndex = entityIndex;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EntityMention() {

	}
	
	boolean embed = true;
	
	public EntityMention(int start, int end) {
		this.start = start;
		this.end = end;
		// this.headStart = start;
		// this.headEnd = end;
	}

	// (14, 15) (20, -1) (10, 20) 
	public int compareTo(EntityMention emp2) {
//		int diff = this.getS() - emp2.getS();
//		if (diff == 0)
//			return this.getE() - emp2.getE();
//		else
//			return diff;
		if(this.getE()!=-1 && emp2.getE()!=-1) {
	 		int diff = this.getE() - emp2.getE();
			if(diff==0) {
				return this.getS() - emp2.getS();
			} else 
				return diff;
		} else if(this.getE()==-1 && emp2.headEnd!=-1){
			int diff = this.getS() - emp2.getE();
			if(diff==0) {
				return -1;
			} else 
				return diff;
		} else if(this.headEnd!=-1 && emp2.headEnd==-1){
			int diff = this.getE() - emp2.getS();
			if(diff==0) {
				return 1;
			} else 
				return diff;
		} else {
			return this.getS()-emp2.getS();
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("headstart: ").append(this.headStart).append(" headend: ").append(this.headEnd).append(
				" ").append(this.head);
		return sb.toString();
	}
}