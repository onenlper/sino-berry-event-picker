package model.syntaxTree;

import java.util.ArrayList;

import util.Common;


public class MyTreeNode {
	public String value;
	
	public ArrayList<MyTreeNode> children;
	
	public boolean mark = false;
	
	public MyTreeNode parent;
	
	public int leafIdx=0;
	
	public boolean isNNP=false;
	
	public MyTreeNode() {
		children = new ArrayList<MyTreeNode>();
	}
	
	public int childIndex;
	
	public MyTreeNode(String value) {
		this.value = value;
		this.children = new ArrayList<MyTreeNode>();
	}
	
	public void addChild(MyTreeNode node) {
		node.childIndex = this.children.size();
		this.children.add(node);
		node.parent = this;
	}
	
	/*
	 * get all ancestors of one tree node, 0 element is the root, also include itself
	 */
	public ArrayList<MyTreeNode> getAncestors() {
		ArrayList<MyTreeNode> ancestors = new ArrayList<MyTreeNode>();
		MyTreeNode tmp = this;
		while(tmp!=null) {
			ancestors.add(0,tmp);
			tmp = tmp.parent;
		}
		return ancestors;
	}
	
	public static void main(String args[]) {
		String treeStr = "(ROOT" +
"  (IP" + 
"    (VP (VV 请)" +
"      (IP" +
"        (VP (VV 听)" +
"          (NP" +
"            (DNP" +
"              (NP (NN 记者) (NN 宫能惠))" +
"              (DEG 的))" +
"            (NP (NN 报导))))))" +
"    (PU 。)))";
		MyTree tree = Common.constructTree(treeStr);
//		for(int i=1;i<tree.leaves.size();i++) {
//			TreeNode leaf = tree.leaves.get(i);
//			TreeNode parent = leaf;
//			while(parent!=tree.root) {
//				System.out.print(parent.value+" ");
//				parent = parent.parent;
//			}
//			System.out.println(leaf.value);
//		}
		MyTreeNode leaf = tree.leaves.get(4);
		MyTreeNode parent = leaf.parent.parent.parent.parent;
		MyTreeNode child = parent;
		StringBuilder sb = new StringBuilder();
		while(child.children!=null && child.children.size()!=0) {
			child = child.children.get(0);
		}
		System.out.println(child.value);
		System.out.println(child.children.size());
		for(int i=child.leafIdx; i<=4; i++) {
			sb.append(tree.leaves.get(i).value);
		}
		System.out.println(sb.toString());
		
		System.out.println(tree.root.getPlainText(true));
		
		MyTreeNode copyNode = tree.root.copy();
		System.out.println(copyNode.getPlainText(true));
	}
	
	public ArrayList<MyTreeNode> getLeaves() {
		ArrayList<MyTreeNode> leaves = new ArrayList<MyTreeNode>();
		ArrayList<MyTreeNode> frontiers = new ArrayList<MyTreeNode>();
		frontiers.add(this);
		while(frontiers.size()>0) {
			MyTreeNode tn = frontiers.remove(frontiers.size()-1);
			if(tn.children.size()==0) {
				leaves.add(tn);
			}
			for(int i=tn.children.size()-1;i>=0;i--) {
				frontiers.add(tn.children.get(i));
			}
		}
		return leaves;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		ArrayList<MyTreeNode> frontiers = new ArrayList<MyTreeNode>();
		frontiers.add(this);
		while(frontiers.size()>0) {
			MyTreeNode tn = frontiers.remove(frontiers.size()-1);
			if(tn.children.size()==0 && !tn.parent.value.equalsIgnoreCase("-none-")) {
				sb.append(tn.value);
			}
			for(int i=tn.children.size()-1;i>=0;i--) {
				frontiers.add(tn.children.get(i));
			}
		}
		return sb.toString();
	}
	
	public MyTreeNode pruneNone() {
		ArrayList<MyTreeNode> frontie = new ArrayList<MyTreeNode>();
		frontie.add(this);
		while(frontie.size()>0) {
			MyTreeNode tn = frontie.remove(0);
			
			ArrayList<MyTreeNode> leaves = tn.getLeaves();
			boolean notNone = false;
			for(MyTreeNode leaf:leaves) {
				if(!leaf.parent.value.equalsIgnoreCase("-none-")) {
					notNone = true;
					break;
				}
			}
			if(!notNone) {
				tn.parent.children.remove(tn);
			}
			frontie.addAll(tn.children);
		}
		return this;
	}
	
	private void appendTreeNodeValue(MyTreeNode tn, StringBuilder sb, boolean leaf) {
		boolean haveMarkedChild = false;
		for(MyTreeNode child:tn.children) {
			if(child.mark) {
				haveMarkedChild = true;
				break;
			}
		}
		if(!haveMarkedChild) {
			if(tn.mark) {
				if(tn.value.equalsIgnoreCase("-none-")) {
					if(leaf) {
						sb.append(" (NP-SBJ none)");
					} else {
						sb.append(" (-none- )");
					}
				} else if(tn.children.size()>0) {
					// leaf
					if(leaf && tn.children.size()==1 && tn.children.get(0).children.size()==0) {
						sb.append(" (").append(tn.value).append(" ").append(tn.children.get(0).value).append(")");
					} else {
						sb.append(" (").append(tn.value).append(" )");
					}
				} else {
					sb.append(" ").append(tn.value);
				}
			}
		} else {
			if(tn.mark) {
				sb.append(" ").append("(").append(tn.value);
				ArrayList<MyTreeNode> children = tn.children;
				for(MyTreeNode child:children) {
					appendTreeNodeValue(child, sb, leaf);
				}
				sb.append(")");
			}
		}
	}
	
	public String getPlainText(boolean leaf) {
		StringBuilder sb = new StringBuilder();
		appendTreeNodeValue(this, sb, leaf);
		return sb.toString();
	}
	
	public MyTreeNode copy() {
		return copyNode(this);
	}
	
	public MyTreeNode copyNode(MyTreeNode oldNode) {
		MyTreeNode newNode = new MyTreeNode();
		newNode.isNNP = oldNode.isNNP;
		newNode.leafIdx = oldNode.leafIdx;
		newNode.value = oldNode.value;
		
		for(MyTreeNode child:oldNode.children) {
			MyTreeNode newChild = copyNode(child);
			newChild.parent = newNode;
			newNode.addChild(newChild);
		}
		return newNode;
	}
	
	/*
	 * set all the nodes in the subtree as mark or unmark
	 */
	public void setAllMark(boolean mark) {
		ArrayList<MyTreeNode> frontie = new ArrayList<MyTreeNode>();
		frontie.add(this);
		while(frontie.size()>0) {
			MyTreeNode tn = frontie.remove(0);
			tn.mark = mark;
			frontie.addAll(tn.children);
		}
	}
	
	public int hashCode() {
		ArrayList<MyTreeNode> leaves = this.getLeaves();
		String str = leaves.get(0).leafIdx + "_" + leaves.get(leaves.size()-1).leafIdx + "_" + this.toString();
		return str.hashCode();
	}
	
	public boolean equals(Object em2) {
		ArrayList<MyTreeNode> leaves1 = this.getLeaves();
		ArrayList<MyTreeNode> leaves2 = ((MyTreeNode)em2).getLeaves();
		if(this.toString().equals(((MyTreeNode)em2).toString()) &&leaves1.get(0).leafIdx==leaves2.get(0).leafIdx && leaves1.size()==leaves2.size()) {
			return true;
		} else {
			return false;
		}
	}
	

//	@Override
//	public int compareTo(TreeNode arg0) {
//		ArrayList<TreeNode> leaves1 = this.getLeaves();
//		ArrayList<TreeNode> leaves2 = arg0.getLeaves();
//		
//		int start1 = leaves1.get(0).leafIdx;
//		int end1 = leaves1.get(leaves1.size()-1).leafIdx;
//		
//		int start2 = leaves2.get(0).leafIdx;
//		int end2 = leaves2.get(leaves2.size()-1).leafIdx;
//		
//		if(start1!=start2) {
//			return start1-start2;
//		} else {
//			return end1-end2;
//		}
//	}
	
}
