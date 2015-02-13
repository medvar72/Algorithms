package Stacks;
import Stacks.StackOfStrings;
import UnionFind.StdIn;
import UnionFind.StdOut;
public class ClientOfStacks {

	public static void main(String[] args) {
		StackOfStrings stack = new StackOfStrings();
		while (!StdIn.isEmpty()){
			String sCad = StdIn.readString();
			if (sCad.equals("-")) StdOut.print(stack.pop());
			else stack.push(sCad);
				
		}

	}

}
