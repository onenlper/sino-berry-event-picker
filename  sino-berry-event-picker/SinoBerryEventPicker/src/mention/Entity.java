package mention;

import java.util.*;

public class Entity implements Comparable<Entity> {

	public ArrayList<EntityMention> mentions;
	public String type;
	public String subType;
	public int entityIdx;

	public ArrayList<EntityMention> getMentions() {
		return mentions;
	}

	public void addMention(EntityMention em) {
		em.entity = this;
		this.mentions.add(em);
	}

	public void setMentions(ArrayList<EntityMention> mentions) {
		this.mentions = mentions;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public Entity() {
		mentions = new ArrayList<EntityMention>();
	}

	public int compareTo(Entity emp2) {
		Collections.sort(mentions);
		Collections.sort(emp2.mentions);
		int diff = mentions.get(0).headStart - emp2.mentions.get(0).headStart;
		if (diff == 0)
			return mentions.get(0).headEnd - emp2.mentions.get(0).headEnd;
		return diff;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type: ").append(type).append(" SubType: ").append(subType)
				.append("\n");
		for (EntityMention em : mentions) {
			sb.append(em.toString()).append("\n");
		}
		return sb.toString();
	}
}
