package ACE;

import java.util.ArrayList;

import model.stanford.StanfordSentence;
import model.stanford.StanfordToken;
import model.stanford.StanfordXMLReader.StanfordDep;
import model.syntaxTree.MyTree;

public class ParseResult {

	public String sentence = "";

	public ArrayList<String> words;

	public ArrayList<String> posTags;

	public MyTree tree;

	public ArrayList<String> depends;

	public ArrayList<int[]> positions;

	public ParseResult() {

	}

	public ParseResult(StanfordSentence ss, String allText) {
		int start = ss.tokens.get(0).CharacterOffsetBegin;
		int end = ss.tokens.get(1).CharacterOffsetEnd;
		this.sentence = allText.substring(start, end);
		
		this.tree = ss.parseTree;
		this.depends = new ArrayList<String>();
		
		for(StanfordDep dep : ss.basicDependencies) {
			StringBuilder sb = new StringBuilder();
			//prep(制订-11, 为了-1)
			sb.append(dep.getType()).append("(").append(dep.getGovernor()).append("-")
			.append(dep.getGovernorId()).append(", ").append(dep.getDependent()).append("-").append(dep.getDependentId()).append(")");
			this.depends.add(sb.toString());
		}
		
		this.words = new ArrayList<String>();
		this.posTags = new ArrayList<String>();
		this.positions = new ArrayList<int[]>();
		
		this.words.add("ROOT");
		this.posTags.add("ROOT");
		this.positions.add(new int[2]);
		
		for(StanfordToken tk : ss.tokens) {
			this.words.add(tk.getWord());
			this.posTags.add(tk.getPOS());
			int[] position = new int[2];
			position[0] = tk.CharacterOffsetBegin;
			position[1] = tk.CharacterOffsetEnd;
			this.positions.add(position);
		}
		
	}

	public ParseResult(String sentence, MyTree tree, ArrayList<String> depends) {
		this.sentence = sentence;
		this.tree = tree;
		this.depends = depends;
		words = new ArrayList<String>();
		posTags = new ArrayList<String>();
		String tokens[] = sentence.split(" ");
		words.add("ROOT");
		posTags.add("ROOT");
		for (String token : tokens) {
			if (token.isEmpty()) {
				continue;
			}
			int pos = token.lastIndexOf('/');
			String word = token.substring(0, pos);
			String posTag = token.substring(pos + 1);
			words.add(word);
			posTags.add(posTag);
		}
	}
}
