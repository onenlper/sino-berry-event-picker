package mentionBoundary;

import java.util.ArrayList;

import mention.Element;
import mention.MentionInstance;
import model.stanford.StanfordXMLReader;
import util.Common;
import util.ace.ACECommon;
import ACE.ParseResult;

/*
 * java ~ goldParse
 */
public class OntoNerTest {

	public static String parseMode;

	public static void main(String args[]) {
		if (args.length < 1) {
			System.out.println("java ~ file");
			return;
		}
		produceInstance(args[0], args[0] + ".crf");
	}

	public static void produceInstance(String filename, String feaFile) {
		System.out.println(filename);

		String content = Common.getFileContent(filename);
		
		ArrayList<MentionInstance> instances = new ArrayList<MentionInstance>();
		for (int i = 0; i < content.length(); i++) {
			MentionInstance instance = new MentionInstance(content.substring(i,
					i + 1));
			instances.add(instance);
		}

		ArrayList<ParseResult> parseResults = StanfordXMLReader.readPR(filename
				+ ".xml", content);

		ACECommon.genACENounPhraseFea(instances, parseResults);
		ACECommon.genPronounFea(instances);
		ACECommon.genInLocationFea(instances);
		ACECommon.genLocationSuffixFea(instances);
		ACECommon.genACESurnameFea(instances);
		ACECommon.genACEBoundPOSFea(instances, parseResults);
		ACECommon.genACEEnglishFea(instances);

		Common.outputInstances(instances, feaFile, false, 0, content.length());
	}
}
