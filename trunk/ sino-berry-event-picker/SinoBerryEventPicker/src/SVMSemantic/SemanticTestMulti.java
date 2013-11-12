package SVMSemantic;

import java.io.FileWriter;
import java.util.ArrayList;

import mention.Element;
import mention.Entity;
import mention.EntityMention;
import util.Common;
import util.ace.ACECommon;
import util.ace.ACECorefCommon;
import ACE.PlainText;
import MentionDetection.CRFMention;

/*
 * classify subtype directly
 */
public class SemanticTestMulti {
	
	public static String baseCRFSemPath = "/shared/mlrdir1/disk1/home/yzcchen/tool/CRF/CRF++-0.54/ACE/Semantic/";
	public static ArrayList<ArrayList<EntityMention>> emses;
	public static void main(String args[]) throws Exception {
		if (args.length != 1) {
			System.err.println("java ~ folder");
			System.exit(1);
		}
		Common.part = args[0];
		SVMSemanticFeature.init();
		CRFMention crfMention = new CRFMention();
		emses = crfMention.getMentions();
		SVMSemanticFeature.semDicFeatures = Common.readFile2Map("semantic_dic" + args[0]);
		SVMSemanticFeature.charFeatures = Common.readFile2Map("semantic_char" + args[0]);
		String test[] = new String[1];
		test[0] = args[0];
		String baseFolder = "/users/yzcchen/ACL12/model/ACE2005/semantic3/";
		
		FileWriter mentionFw = new FileWriter(baseFolder + "mention.test" + args[0]);
		
		FileWriter typeFw = new FileWriter(baseFolder + "multiType.test" + args[0]);		

		FileWriter subTypeFw = new FileWriter(baseFolder + "multiSubType" + ".test" + args[0]);
		ArrayList<String> sgmFiles = ACECommon.getFileList(test);
		String concat = test[0];
		ArrayList<ArrayList<Element>> nerses2 = ACECorefCommon.getSemanticsFromCRFFile(sgmFiles,
				"/users/yzcchen/tool/CRF/CRF++-0.54/ACE/Ner/" + concat + ".result");
		for(int index=0;index<sgmFiles.size();index++) {
			String sgmFile = sgmFiles.get(index);
			System.out.println(sgmFile);
			ArrayList<Element> ners = nerses2.get(index);
			String apfFile = ACECommon.getRelateApf(sgmFile);
			ArrayList<Entity> entities = ACECommon.getEntities(apfFile);
			PlainText plainText = ACECommon.getPlainText(sgmFile);
			// system mentions
			ArrayList<EntityMention> ems = emses.get(index);
			
			// gold mentions
//			ArrayList<EntityMention> ems = new ArrayList<EntityMention>();
//			for (Entity en : entities) {
//				for (EntityMention em : en.mentions) {
//					em.semClass = en.type.toLowerCase();
//					em.subType = en.type.substring(0, 1).toLowerCase() + "-" + en.subType.toLowerCase();
//				}
//				ems.addAll(en.mentions);
//			}
			
			for(EntityMention em:ems) {
				String str = SVMSemanticFeature.semanticFeature(em, false, plainText, 0, ners, sgmFile);
				String type = em.semClass;
				String subType = em.subType;

				typeFw.write("1 " + str+"\n");
				subTypeFw.write("1 " + str+"\n");
				mentionFw.write(em.headStart+","+em.headEnd+" "+sgmFile+"\n");
			}
		}
		mentionFw.close();

		typeFw.close();
			subTypeFw.close();
	}
}
