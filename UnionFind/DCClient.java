package UnionFind;
import UnionFind.QuickFindUF;
import UnionFind.QuickUnionUF;
import UnionFind.WeightedQuickUnionUF;
import UnionFind.PathCompressionQU;
import UnionFind.StdIn;
import UnionFind.StdOut;

public class DCClient {
	
	public static void main(String[] args) {
		///*
		int[] id;
		String sOutput = "";
		int N = StdIn.readInt();
		//QuickFindUF uf = new QuickFindUF(N);
		WeightedQuickUnionUF uf = new WeightedQuickUnionUF(N);
		sOutput = "";
		while (!StdIn.isEmpty()){
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			{
				uf.union(p, q);
				id = new int[10];
				for(int i=0;i<N;i++){id[i]= 0;}
				id = uf.getid();
				sOutput = "";
				for(int i=0;i<id.length;i++){sOutput = sOutput +" "+  id[i];}
				StdOut.println("("+ p + ", " + q + ")");
				System.out.println(sOutput + "\n");
				//StdOut.println(p + " " + q);
			}
		}
   
	}

}
