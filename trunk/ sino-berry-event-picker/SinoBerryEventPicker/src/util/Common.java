package util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import mention.Instance;
import mention.MentionInstance;
import mention.OntoNerInstance;
import model.syntaxTree.MyTree;
import model.syntaxTree.MyTreeNode;

import org.xml.sax.InputSource;

import util.ace.ACECommon;

public class Common {
	public static String part;
	public static boolean init = false;

	{
		if (!init) {
			init();
		}
	}

	public static void init() {
		try {
			// System.out.println("EXECUTE");
			Properties props = new Properties();
			InputStream in = new BufferedInputStream(new FileInputStream("properties"));
			props.load(in);
			// Common.dicPath = props.getProperty("dicPath");
			ACECommon.aceDataPath = props.getProperty("aceDataPath");
			ACECommon.aceModelPath = props.getProperty("aceModelPath");
//			OntoCommon.ontoDataPath = props.getProperty("ontoDataPath");
//			OntoCommon.ontoModelPath = props.getProperty("ontoModelPath");
			// System.out.println(ACECommon.aceDataPath);
			Common.loadSemanticDic();
		} catch (Exception e) {
			e.printStackTrace();
		}
		init = true;
	}
	
	public static String getXMLFile(String xmlFn) {
		StringBuilder sb = new StringBuilder();
		try {
			InputStream inputStream = new FileInputStream(new File(xmlFn));
			SAXParserFactory sf = SAXParserFactory.newInstance();
			SAXParser sp = sf.newSAXParser();
			RegularXMLReader reader = new RegularXMLReader(sb);
			sp.parse(new InputSource(inputStream), reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static void outputHashSet(HashSet<String> set, String filename) {
		try {
			FileWriter fw = new FileWriter(filename);
			for (String str : set) {
				fw.write(str + "\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static HashSet<String> pronouns = null;

	public static boolean isPronoun(String str) {
		if (pronouns == null) {
			pronouns = Common.readFile2Set(dicPath + "pronoun");
		}
		if (pronouns.contains(str)) {
			return true;
		} else {
			return false;
		}
	}

	public static int PRONOUN_ME = 0;
	public static int PRONOUN_YOU = 1;
	public static int PRONOUN_HE = 2;
	public static int PRONOUN_SHE = 3;
	public static int PRONOUN_IT = 4;
	public static int PRONOUN_HE_S = 5;
	public static int PRONOUN_ME_S = 6;
	public static int PRONOUN_SHE_S = 7;
	public static int PRONOUN_YOU_S = 8;
	public static int PRONOUN_IT_S = 9;
	public static int PRONOUN_WHO = 10;

	public static int getPronounType(String str) {
		if (str.equals("我") || str.equals("俺") || str.equals("自己") || str.equals("本身") || str.equals("本人")) {
			return PRONOUN_ME;
		} else if (str.equals("你") || str.equals("您")) {
			return PRONOUN_YOU;
		} else if (str.equals("他") || str.equals("那位") || str.equals("其他")) {
			return PRONOUN_HE;
		} else if (str.equals("她")) {
			return PRONOUN_SHE;
		} else if (str.equals("它") || str.equals("这") || str.equals("那") || str.equals("那里") || str.equals("其它")
				|| str.equals("其")) {
			return PRONOUN_IT;
		} else if (str.equals("他们") || str.equals("双方")) {
			return PRONOUN_HE_S;
		} else if (str.equals("咱们") || str.equals("我们") || str.equals("大家")) {
			return PRONOUN_ME_S;
		} else if (str.equals("她们")) {
			return PRONOUN_SHE_S;
		} else if (str.equals("你们")) {
			return PRONOUN_YOU_S;
		} else if (str.equals("它们") || str.equals("这些") || str.equals("那些") || str.equals("一些")) {
			return PRONOUN_IT_S;
		} else if (str.equals("谁") || str.equals("什么") || str.equals("哪个")) {
			return PRONOUN_WHO;
		} else
			return -1;
	}

	private static HashMap<String, Integer> abbreHash = null;

	public static boolean contain(String str1, String str2) {
		for (int i = 0; i < str2.length(); i++) {
			if (str1.indexOf(str2.charAt(i)) == -1) {
				return false;
			}
		}
		return true;
	}

	public static boolean isAbbreviation(String str1, String str2) {
		if (abbreHash == null) {
			abbreHash = new HashMap<String, Integer>();
			ArrayList<String> lines = Common.getLines(dicPath + "abbreviation");
			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				String tokens[] = line.split("\\s+");
				for (String token : tokens) {
					abbreHash.put(token, i);
					if (token.endsWith("省") || token.endsWith("市")) {
						abbreHash.put(token.substring(0, token.length() - 1), i);
					}
				}
			}
		}
		if (abbreHash.containsKey(str1) && abbreHash.containsKey(str2)) {
			return (abbreHash.get(str1).intValue() == abbreHash.get(str2).intValue());
		} else {
			String l = str1;
			String s = str2;
			if (str1.length() < str2.length()) {
				l = str2;
			} else {
				s = str1;
			}
			if (l.substring(0, l.length() - 1).equalsIgnoreCase(s)
					&& (l.charAt(l.length() - 1) == '省' || l.charAt(l.length() - 1) == '市')) {
				return true;
			} else {
				return false;
			}
		}
	}

	// cache, store file content
	public static HashMap<String, ArrayList<String>> fileCache = new HashMap<String, ArrayList<String>>();

	public static ArrayList<Double> getDoubles(String filename) {
		ArrayList<Double> doubles = new ArrayList<Double>();
		BufferedReader br = getBr(filename);
		String line;
		try {
			while ((line = br.readLine()) != null) {
				double value = Double.valueOf(line);
				doubles.add(value);
			}
			br.close();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doubles;
	}

	public static String getFileContent(String filename) {
		StringBuilder lines = new StringBuilder();
		try {
			BufferedReader br = getBr(filename);
			String line;
			while ((line = br.readLine()) != null) {
				lines.append(line).append("\n");
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines.toString();
	}

	public static ArrayList<String> getLines(String filename) {
		if (fileCache.containsKey(filename)) {
			return fileCache.get(filename);
		} else {
			ArrayList<String> lines = null;
			try {
				lines = new ArrayList<String>();
				BufferedReader br = getBr(filename);
				String line;
				while ((line = br.readLine()) != null) {
					lines.add(line);
				}
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fileCache.put(filename, lines);
			return lines;
		}
	}

	public static void outputLines(ArrayList<String> lines, String filename) {
		try {
			FileWriter fw;
			fw = new FileWriter(filename);
			for (String line : lines) {
				fw.write(line + "\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//	
	// public static void outputHashMap(HashMap<String, Integer> map,
	// String filename) {
	// try {
	// FileWriter fw = new FileWriter(filename);
	// for (String str : map.keySet()) {
	// fw.write(str + " " + map.get(str).toString() + "\n");
	// }
	// fw.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//	
	public static void outputHashMap(HashMap map, String filename) {
		try {
			FileWriter fw = new FileWriter(filename);
			for (Object str : map.keySet()) {
				Object obj = map.get(str);
				if (obj instanceof int[]) {
					int a[] = (int[]) obj;
					StringBuilder sb = new StringBuilder();
					sb.append(str.toString()).append(" ");
					for (int b : a) {
						sb.append(b).append(" ");
					}
					fw.write(sb.toString().trim() + "\n");
				} else {
					fw.write(str.toString() + " " + map.get(str).toString() + "\n");
				}
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void increaseHashValue(HashMap<String, Object> map, String key) {
		if (map.containsKey(key)) {
			Object value = map.get(key);
			if (value instanceof Integer) {
				int v = ((Integer) value).intValue();
				map.put(key, v + 1);
			} else if (value instanceof Long) {
				long v = ((Long) value).longValue();
				map.put(key, v + 1);
			}
		}
	}

	public static HashSet<String> readFile2Set(String filename) {
		HashSet<String> set = null;
		try {
			set = new HashSet<String>();
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				set.add(line.trim());
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return set;
	}

	/*
	 * read file to <String, int[]>
	 */
	public static HashMap<String, int[]> readFile2Map3(String filename) {
		HashMap<String, int[]> map = null;
		try {
			map = new HashMap<String, int[]>();
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}
				String token[] = line.split("\\s+");
				int a[] = new int[token.length - 1];
				for (int i = 0; i < token.length - 1; i++) {
					a[i] = Integer.valueOf(token[i + 1]);
				}
				map.put(token[0], a);
			}
			br.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	public static HashMap<String, Integer> readFile2Map(String filename) {
		HashMap<String, Integer> map = null;
		try {
			map = new HashMap<String, Integer>();
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}
				int pos = line.lastIndexOf(' ');
				String str = line.substring(0, pos);
				int value = Integer.valueOf(line.substring(pos + 1, line.length()));
				map.put(str, value);
			}
			br.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	public static HashMap<String, Long> readFile2Map4(String filename) {
		HashMap<String, Long> map = null;
		try {
			map = new HashMap<String, Long>();
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}
				String token[] = line.split("\\s+");
				long value = Long.valueOf(Long.valueOf(token[1]));
				map.put(token[0], value);
			}
			br.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	public static HashMap<String, String> readFile2Map2(String filename) {
		HashMap<String, String> map = null;
		try {
			map = new HashMap<String, String>();
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				int pos = line.lastIndexOf(' ');
				String str = line.substring(0, pos);
				String value = line.substring(pos + 1, line.length());
				map.put(str, value);
			}
			br.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	public static HashMap<String, Integer> combineHashMap(HashMap<String, Integer> total, HashMap<String, Integer> map) {
		for (String str : map.keySet()) {
			int value = map.get(str);
			if (total.containsKey(str)) {
				int oldValue = total.get(str);
				total.put(str, value + oldValue);
			} else {
				total.put(str, value);
			}
		}
		return total;
	}

	public static BufferedReader getBr(String filename) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return br;
	}

	// determine if this is a stop sign punctuation
	public static boolean isStopPun(char c) {
		if (c == '。' || c == '？' || c == '！')
			return true;
		return false;
	}

	// determine if this is a stop sign punctuation
	public static boolean isPun(char c) {
		if (c == '。' || c == '？' || c == '！' || c == '．' || c == '：' || c == '，' || c == '；')
			return true;
		return false;
	}

	public static HashSet<String> readSurname(String filename) {
		HashSet<String> surnames = new HashSet<String>();
		BufferedReader br = getBr(filename);
		try {
			String line = br.readLine();
			br.close();
			String tokens[] = line.split("\\s+");
			for (String token : tokens) {
				surnames.add(token);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return surnames;
	}

	// generate Ontonotes surname feature
	public static void genOntoSurnameFea(ArrayList<Instance> instances) {
		HashSet<String> surnames = readSurname("dict/surname");
		for (int i = 0; i < instances.size(); i++) {
			String c = instances.get(i).getCharacter();
			if (surnames.contains(c)) {
				((OntoNerInstance) instances.get(i)).setSurname(1);
			}
		}
	}

	// // generate ACE segmented left/right boundary feature
	// public static void genACEBoundFea(ArrayList<Instance> instances, String
	// filename) {
	// try {
	// String tokenFn = filename.substring(0,filename.length()-3) + "token";
	// BufferedReader br = getBr(tokenFn);
	// String line;
	// StringBuilder sb = new StringBuilder();
	// while((line=br.readLine())!=null) {
	// sb.append(" " + line);
	// }
	// String tokenCon=sb.toString()+" ";
	// int index=0;
	// for(int i=0;i<instances.size();i++) {
	// MentionInstance instance = (MentionInstance) instances.get(i);
	// if(instance.getCharacter().equals("\n") ||
	// instance.getCharacter().equals(" ")
	// || instance.getCharacter().equals("\r") ||
	// instance.getCharacter().equals("　")) {
	// continue;
	// }
	// while(tokenCon.charAt(index)!=instance.getCharacter().charAt(0)) {
	// index++;
	// }
	// if(tokenCon.charAt(index-1)==' ') {
	// instance.setLeftBound(1);
	// }
	// if(tokenCon.charAt(index+1)==' ') {
	// instance.setRightBound(1);
	// }
	//				
	// }
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	// find the number of whitespace before the character
	public static int findWSCount(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != ' ') {
				return i;
			}
		}
		return str.length();
	}

	// extract plane text from syntax tree, using )
	public static String extractPlainText(String syntaxTree) {
		// ArrayList<String> words = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		// the index of )
		int rightBIdx = -1;
		while ((rightBIdx = syntaxTree.indexOf(')', rightBIdx + 1)) > 0) {
			if (syntaxTree.charAt(rightBIdx - 1) == ')') {
				continue;
			}
			int start = rightBIdx;
			// special case, character is ")"
			if (syntaxTree.charAt(start - 1) == ' ') {
				sb.append(")");
				continue;
			}
			while (true) {
				start--;
				if (syntaxTree.charAt(start) == ' ') {
					break;
				}
			}
			String word = syntaxTree.substring(start + 1, rightBIdx);
			sb.append(word);
		}
		return sb.toString();
		// return words;
	}

	public static void outputInstances(ArrayList<MentionInstance> instances, String filename, boolean append,
			int start, int end) {
		try {
			FileWriter fw = new FileWriter(filename, append);
			for (int i = 0; i < instances.size(); i++) {
				if (i < start || i > end) {
					continue;
				}
				Instance instance = instances.get(i);

				if (instance.getCharacter().equals("\r") || instance.getCharacter().equals("\n")) {
					fw.write("\n");
				} else if (!instance.getCharacter().equals("\n") && !instance.getCharacter().equals(" ")
						&& !instance.getCharacter().equals("　")) {
					fw.write(instance.toString() + " \n");
					if (Common.isStopPun(instance.getCharacter().charAt(0))) {
						fw.write("\n");
					}
				}
			}
			fw.write("\n");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int[] findPosition(String big, String small, int from) {
		int[] position = new int[2];
		while (big.charAt(from) != small.charAt(0)) {
			from++;
		}
		position[0] = from;
		for (int i = 1; i < small.length(); i++) {
			if (big.charAt(from) != small.charAt(i)) {
				from++;
			}
		}
		position[1] = from;
		return position;
	}

	public static MyTree constructTree(String treeStr) {
		MyTree tree = new MyTree();
		MyTreeNode currentNode = tree.root;
		int leafIdx = 1;
		for (int i = 0; i < treeStr.length(); i++) {
			if (treeStr.charAt(i) == '(') {
				int j = i + 1;
				// try {
				while (treeStr.charAt(j) != ' ' && treeStr.charAt(j) != '\n') {
					j++;
				}
				// } catch(Exception e) {
				// System.out.println(treeStr.substring(i-2,i+2));
				// // System.out.println(treeStr.charAt(j));
				// System.out.println(treeStr);
				// System.exit(1);
				// }
				String value = treeStr.substring(i + 1, j);
				MyTreeNode node = new MyTreeNode(value);
				if (tree.root == null) {
					tree.root = node;
				} else {
					currentNode.addChild(node);
				}
				if (value.startsWith("NP")) {
					node.isNNP = true;
				} else {
					if (node != tree.root) {
						node.isNNP = node.parent.isNNP;
					}
				}
				currentNode = node;
				while (treeStr.charAt(j) == '\n' || treeStr.charAt(j) == ' ') {
					j++;
				}
				if (treeStr.charAt(j) == '(' && treeStr.charAt(j + 1) != ')') {
					i = j - 1;
				} else {
					int m = j;
					while (treeStr.charAt(m) != ')') {
						m++;
					}
					String value2 = treeStr.substring(j, m);
					MyTreeNode node2 = new MyTreeNode(value2);
					node2.leafIdx = leafIdx++;
					currentNode.addChild(node2);
					tree.leaves.add(node2);
					node2.isNNP = node2.parent.isNNP;
					// System.out.println(value2);
					i = m;
					currentNode = currentNode.parent;
				}

			} else if (treeStr.charAt(i) == ')') {
				if (currentNode != tree.root) {
					currentNode = currentNode.parent;
				}
			}
		}
		return tree;
	}

	public static String dicPath = "/users/yzcchen/workspace/ACL12/src/dict/";
	// public static String dicPath = "./dict/";

	public static HashMap<String, String[]> semanticDic = new HashMap<String, String[]>();

	public static void loadSemanticDic() {
		semanticDic = new HashMap<String, String[]>();
		ArrayList<String> lines = Common.getLines(dicPath + "TongyiciCiLin_8.txt");
		for (String line : lines) {
			String tokens[] = line.split("\\s+");
			String word = tokens[0];
			String semantic[] = new String[tokens.length - 2];
			for (int i = 0; i < semantic.length; i++) {
				semantic[i] = tokens[2 + i];
			}
			semanticDic.put(word, semantic);
		}
	}

	// determine whether is person, 1=true, -1=false, 0=NA
	public static int isSemanticPerson(String str) {
		String[] codes = semanticDic.get(str);
		if (codes == null) {
			return 0;
		}
		for (String code : codes) {
			if (code.charAt(0) == 'A') {
				return 1;
			}
		}
		return -1;
	}

	// determine whether is animal, 1=true, -1=false, 0=NA
	public static int isSemanticAnimal(String str) {
		String[] codes = semanticDic.get(str);
		if (codes == null) {
			return 0;
		}
		for (String code : codes) {
			if (code.startsWith("Bi")) {
				return 1;
			}
		}
		return -1;
	}

	// 社会 政法
	public static int isSemanticSocialEntity(String str) {
		String[] codes = semanticDic.get(str);
		if (codes == null) {
			return 0;
		}
		for (String code : codes) {
			if (code.startsWith("Di")) {
				return 1;
			}
		}
		return -1;
	}

	public static void main(String args[]) {
		// String treeStr =
		// "(ROOT  (IP    (NP      (NP (NR 台湾) (NR 陈水扁))      (NP (NN 总统)))    (VP (VV 任命)      (IP        (NP          (NP            (NP (NR 高雄))            (NP (NN 市长)))          (NP (NR 谢长廷)))        (VP (VC 为)          (NP            (ADJP (JJ 新任))            (NP (NN 行政) (NN 院长))))))    (PU -) (PU -)))";
		// Tree tree = Common.constructTree(treeStr);
		// TreeNode tn = tree.leaves.get(5);
		// TreeNode parent = tn.parent;
		// while (parent != tree.root) {
		// System.out.println(parent.value);
		// parent = parent.parent;
		// }
		String str1 = "中国人民";
		String str2 = "中华人民共和国";
		System.out.println(Common.getEditDistance(str1, str2));

		// HashMap<String, ArrayList<String>> newDic = new HashMap<String,
		// ArrayList<String>>();
		// for(String word:semanticDic.keySet()) {
		// String codes[] = semanticDic.get(word);
		// for(String code:codes) {
		// if(newDic.containsKey(code)) {
		// newDic.get(code).add(word);
		// } else {
		// ArrayList<String> words = new ArrayList<String>();
		// words.add(word);
		// newDic.put(code, words);
		// }
		// }
		// }
		// Object[] codes = newDic.keySet().toArray();
		// Arrays.sort(codes);
		// try {
		// FileWriter fw = new
		// FileWriter("D:\\workspace\\ACL12\\ACL12\\src\\dict\\Tongyici.txt");
		// for(int i=0;i<codes.length;i++) {
		// String code = (String)codes[i];
		// ArrayList<String> words = newDic.get(code);
		// StringBuilder sb = new StringBuilder();
		// sb.append(code).append(" ");
		// for(String word:words) {
		// sb.append(word).append(" ");
		// }
		// fw.write(sb.toString()+ "\n");
		// }
		// fw.close();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	private static HashSet<String> male = null;
	private static HashSet<String> female = null;

	// determine gender, 0 for male, 1 for female, 2 for unknown
	public static int getGender(String str) {
		if (male == null) {
			male = Common.readFile2Set(Common.dicPath + "male");
		}
		if (female == null) {
			female = Common.readFile2Set(Common.dicPath + "female");
		}
		if (male.contains(str)) {
			return 0;
		} else if (female.contains(str)) {
			return 1;
		} else {
			return 2;
		}
	}

	public static int getEditDistance(String s, String t) {
		int d[][]; // matrix
		int n; // length of s
		int m; // length of t
		int i; // iterates through s
		int j; // iterates through t
		char s_i; // ith character of s
		char t_j; // jth character of t
		int cost; // cost

		// Step 1

		n = s.length();
		m = t.length();
		if (n == 0) {
			return m;
		}
		if (m == 0) {
			return n;
		}
		d = new int[n + 1][m + 1];

		// Step 2

		for (i = 0; i <= n; i++) {
			d[i][0] = i;
		}

		for (j = 0; j <= m; j++) {
			d[0][j] = j;
		}

		// Step 3

		for (i = 1; i <= n; i++) {
			s_i = s.charAt(i - 1);
			// Step 4
			for (j = 1; j <= m; j++) {
				t_j = t.charAt(j - 1);
				// Step 5
				if (s_i == t_j) {
					cost = 0;
				} else {
					cost = 1;
				}
				// Step 6
				d[i][j] = Minimum(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + cost);
			}
		}
		// Step 7
		return d[n][m];
	}

	public String combineTwoString(String str1, String str2) {
		if(str1.compareTo(str2)<0) {
			return str1 + "_" + str2;
		} else {
			return str2 + "_" + str1;
		}
	}
	
	private static int Minimum(int a, int b, int c) {
		int mi;

		mi = a;
		if (b < mi) {
			mi = b;
		}
		if (c < mi) {
			mi = c;
		}
		return mi;
	}

}
