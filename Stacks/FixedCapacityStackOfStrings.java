package Stacks;

public class FixedCapacityStackOfStrings {
	private String[] s;
	private int N = 0;
	
	
	public FixedCapacityStackOfStrings(int Capacity){
		s = new String[Capacity];
	}

	public boolean isEmpty(){
		return (N == 0);
	}
	
	public void push(String sItem){
		s[N++] = sItem; //USamos el inde N y luego incrementamos
	}
	
	public String pop(){
		String sItem = s[--N]; //Decrementamos N y luego lo usamos
		s[N] = null;
		return sItem;
	}
}
