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
		SVMSemanticFeature.semDicFeatures = Common.readFile2Map("semantic_dic"
				+ args[0]);
		SVMSemanticFeature.charFeatures = Common.readFile2Map("semantic_char"
				+ args[0]);
		String test[] = new String[1];
		test[0] = args[0];
		String baseFolder = "/users/yzcchen/ACL12/model/ACE2005/semantic3/";

		FileWriter mentionFw = new FileWriter(baseFolder + "mention.test"
				+ args[0]);

		FileWriter typeFw = new FileWriter(baseFolder + "multiType.test"
				+ args[0]);

		FileWriter subTypeFw = new FileWriter(baseFolder + "multiSubType"
				+ ".test" + args[0]);

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
			subTypeFw.write("1 " + str + "\n");
			mentionFw.write(em.headStart + "," + em.headEnd + " " + "\n");
		}
		mentionFw.close();

		typeFw.close();
		subTypeFw.close();
	}
}
