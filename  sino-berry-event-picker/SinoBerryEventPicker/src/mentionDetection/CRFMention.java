package mentionDetection;

import java.util.ArrayList;

import mention.EntityMention;
import util.ace.ACECommon;

// get mentions from a CRF test result file
public class CRFMention extends MentionDetection{
	// public static void main(String args[]) {
	// CRFMention crf = new CRFMention();
	// ArrayList<ArrayList<EntityMention>> entityMentionses = crf.getMentions();
	// int i=0;
	// for(ArrayList<EntityMention> ems:entityMentionses) {
	// for(EntityMention em:ems) {
	// System.out.println(em.getContent());
	// i++;
	// }
	// }
	// System.out.println(i);
	// }
	
	
	String crfFile;
	String content;
	
	public CRFMention(String str, String content) {
		this.crfFile = str;
		this.content = content;
	}
	
	public ArrayList<EntityMention> getMentions() {
		return ACECommon.getMentionsFromCRFFile(crfFile, content);
	}
}
