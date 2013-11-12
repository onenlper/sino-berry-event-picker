package mentionDetection;

import java.util.ArrayList;

import mention.EntityMention;

public abstract class MentionDetection {
	 public abstract ArrayList<EntityMention> getMentions();
	 
	 public void printEmses(ArrayList<ArrayList<EntityMention>> emses) {
		for(ArrayList<EntityMention> ems : emses) {
			for(EntityMention em:ems) {
				System.out.println(em.headStart+","+em.headEnd+" "+em.head);
			}
		}
	 }
}
