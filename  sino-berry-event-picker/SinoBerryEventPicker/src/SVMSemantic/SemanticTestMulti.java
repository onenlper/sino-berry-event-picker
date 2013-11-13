package SVMSemantic;

import java.io.FileWriter;
import java.util.ArrayList;

import mention.Element;
import mention.EntityMention;
import mentionDetection.CRFMention;
import model.stanford.StanfordXMLReader;
import util.Common;
import util.ace.ACECommon;
import ACE.ParseResult;

/*
 * classify subtype directly
 */
public class SemanticTestMulti {

	public static void main(String args[]) throws Exception {
		if (args.length != 1) {
			System.err.println("java ~ file");
			System.exit(1);
		}
		SVMSemanticFeature.init();

		String filename = args[0];
		String content = Common.getFileContent(filename);
		ArrayList<ParseResult> parseResults = StanfordXMLReader.readPR(filename
				+ ".xml", content);

		ArrayList<Element> ner = ACECommon.getSemanticsFromOneCRFFile(filename
				+ ".ner", content);

		CRFMention crfMention = new CRFMention(filename + ".mention", content);
		ArrayList<EntityMention> ems = crfMention.getMentions();
		SVMSemanticFeature.semDicFeatures = Common.readFile2Map("lm/semantic_dic1");
		SVMSemanticFeature.charFeatures = Common.readFile2Map("lm/semantic_char1");

		FileWriter mentionFw = new FileWriter(filename + ".mention");
		FileWriter typeFw = new FileWriter(filename + ".mention.type.svm");
		
		// gold mentions
		// ArrayList<EntityMention> ems = new ArrayList<EntityMention>();
		// for (Entity en : entities) {
		// for (EntityMention em : en.mentions) {
		// em.semClass = en.type.toLowerCase();
		// em.subType = en.type.substring(0, 1).toLowerCase() + "-" +
		// en.subType.toLowerCase();
		// }
		// ems.addAll(en.mentions);
		// }

		for (EntityMention em : ems) {
			String str = SVMSemanticFeature.semanticFeature(em, false, content,
					0, ner, parseResults);

			typeFw.write("1 " + str + "\n");
			mentionFw.write(em.headStart + "," + em.headEnd + " " + "\n");
		}
		mentionFw.close();
		typeFw.close();
	}
}
