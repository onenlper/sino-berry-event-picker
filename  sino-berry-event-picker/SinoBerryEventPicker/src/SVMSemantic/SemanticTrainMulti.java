package SVMSemantic;

import java.io.FileWriter;
import java.util.ArrayList;

import mention.Element;
import mention.Entity;
import mention.EntityMention;
import util.Common;
import util.ace.ACECommon;
import ACE.PlainText;

/*
 * classify subtype directly
 */
public class SemanticTrainMulti {
	public static String baseCRFSemPath = "/shared/mlrdir1/disk1/home/yzcchen/tool/CRF/CRF++-0.54/ACE/Semantic/";

	public static void main(String args[]) throws Exception {
		if (args.length != 1) {
			System.err.println("java ~ folder");
			System.exit(1);
		}
		String train[] = new String[4];
		int k = 0;
		for (int i = 0; i <= 4; i++) {
			if (i != Integer.valueOf(args[0])) {
				train[k++] = Integer.toString(i);
			}
		}
		// String train[] = {"0"};
		SVMSemanticFeature.init();
		String baseFolder = "/users/yzcchen/ACL12/model/ACE2005/semantic3/";

		FileWriter typeFw = new FileWriter(baseFolder + "multiType.train" + args[0]);
		FileWriter subTypeFw = new FileWriter(baseFolder + "multiSubType.train" + args[0]);

		ArrayList<String> sgmFiles = ACECommon.getFileList(train);
		String concat = train[0] + "_" + train[1] + "_" + train[2] + "_" + train[3];
		ArrayList<ArrayList<Element>> nerses1 = ACECorefCommon.getSemanticsFromCRFFile(sgmFiles,
				"/users/yzcchen/tool/CRF/CRF++-0.54/ACE/Ner/" + concat + ".result");

		for (int index = 0; index < nerses1.size(); index++) {
			String sgmFile = sgmFiles.get(index);
			System.out.println(sgmFile);
			ArrayList<Element> ners = nerses1.get(index);
			String apfFile = ACECommon.getRelateApf(sgmFile);
			ArrayList<Entity> entities = ACECommon.getEntities(apfFile);
			PlainText plainText = ACECommon.getPlainText(sgmFile);
			ArrayList<EntityMention> ems = new ArrayList<EntityMention>();
			for (Entity en : entities) {
				for (EntityMention em : en.mentions) {
					em.semClass = en.type.toLowerCase();
					em.subType = en.type.substring(0, 1).toLowerCase() + "-" + en.subType.toLowerCase();
				}
				ems.addAll(en.mentions);
			}
			for (EntityMention em : ems) {
				String str = SVMSemanticFeature.semanticFeature(em, true, plainText, 0, ners, sgmFile);
				String type = em.semClass;
				String subType = em.subType;
				// System.out.println(type + " " + subType);
				typeFw.write(SVMSemanticFeature.getTypeIndex(type) + str + "\n");
				subTypeFw.write(SVMSemanticFeature.getSubTypeIndex(subType) + str + "\n");
			}
		}

		typeFw.close();
		subTypeFw.close();
		Common.outputHashMap(SVMSemanticFeature.charFeatures, "semantic_char" + args[0]);
		Common.outputHashMap(SVMSemanticFeature.semDicFeatures, "semantic_dic" + args[0]);
	}
}
