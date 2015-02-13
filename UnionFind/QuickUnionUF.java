package UnionFind;

public class QuickUnionUF 
{
	private int id[];
	/**
	 * 
	 * @param N Size of the array
	 * set id of each object to itself
	 * (N array accesses)
	 * 
	 */   
	public QuickUnionUF(int N) 
	{
		id = new int[N];
		//Inicializamos el arreglo
		for (int i=0; i<N;i++)	id[i]=i;
	}
	
	/**
	 * 
	 * @param i elemento al que se le busca la raiz
	 * @return i that is the root of i
	 * chose parent pointers until reach root 
	 * (depth of i array accesses)
	 */
	private int root(int i)
	{
		while(i!=id[i]) i=id[i];
		return i;
	}
	
	/**
	 * 
	 * @param p primer valor a evaluar
	 * @param q segundo valor a evaluar
	 * @return verdadero en caso de que ambos elementos tengan la misma raiz.
	 * check if p and q have the samen root
	 */
	public boolean connected(int p, int q){
		return (root(p) == root(q));
	}
	
	/**
	 * 
	 * @param p
	 * @param q
	 * 
	 * Unimos p y q mediante la igualacion de sus raices.
	 * change the root of p to pont the root of q
	 * (depth of p and q array accesses)
	 */
	public void union(int p, int q){
		int root_p = root(p);
		int root_q = root(q);
		id[root_p] = root_q;
	}

	public int[] getid(){
		return id;
	}
	
}
