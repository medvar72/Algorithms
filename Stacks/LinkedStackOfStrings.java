package Stacks;

public class LinkedStackOfStrings {
	private Node first = null;
	
	private class Node {
		String item;
		Node next;
	}
	
	public boolean isEmpty()
	{
		return first == null;
	}
	
	public void push(String item){
		Node oldFirst = this.first;
		this.first = new Node();
		first.item = item;
		first.next = oldFirst;
	}
	public String pop(){
		String sItem = first.item;
		first = first.next;
		return sItem;
	}

}
