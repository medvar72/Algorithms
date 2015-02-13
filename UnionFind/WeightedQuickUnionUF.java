package UnionFind;

public class WeightedQuickUnionUF {

	private int id[];
	private int sz[];

	public WeightedQuickUnionUF(int N) {
		id = new int[N];
		sz = new int[N];
		//Inicializamos el arreglo
		for (int i=0; i<N;i++){
			id[i] = i;
			sz[i] = 1;
		}	
	}
	
	/**
	 * Devuelve la raiz de i
	 * @param i elemento al que se le busca la raiz
	 * @return i con el valor de root
	 */
	private int root(int i){
		while(i!=id[i]) {
			i=id[i];
		}
		return i;
	}
	
	/**
	 * 
	 * @param p primer valor a evaluar
	 * @param q segundo valor a evaluar
	 * @return verdadero en caso de que ambos elementos tengan la misma raiz.
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
	 */
	public void union(int p, int q){
		//Link root of the smaller tree to root of the larger tree
		//Update the sz[] array
		int root_p = root(p);
		int root_q = root(q);
		if (root_q == root_p) return;
		if (sz[root_p] < sz[root_q]) {
			id[root_p] = root_q;
			sz[root_q] += sz[root_p];
		}
		else{
			id[root_q] = root_p;
			sz[root_p] += sz[root_q];
		}
	}
	
	public int[] getid(){
		return id;
	}
	

}
