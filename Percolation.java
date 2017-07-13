///////////////////////////////////////////////////////////////////////////
// Created by Arundhati Saha, KIIT University, 1405205
// Dated: 13th July, 2017
///////////////////////////////////////////////////////////////////////////

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int openSites;
    private final int n;
    private boolean[] sites;
    private final WeightedQuickUnionUF uf;
    public Percolation(int n) { // create n-by-n grid, with all sites blocked
        if (n <= 0)
            throw new IllegalArgumentException("Invalid args");
        this.n = n;
        sites = new boolean[n*n+2];
        sites[0] = true;
        sites[n*n+1] = true;
        for (int i = 1; i <= (n*n); i++)
            sites[i] = false;
        openSites = 0;
        uf = new WeightedQuickUnionUF(n*n+2);
        for (int s = 1; s <= n; s++)
            uf.union(0, s);
    }

    private int index(int row, int col) {
        return ((row-1)*n+col);
    }
    public void open(int row, int col) { // open site (row, col) if it is not open already
        if ((row < 1) || (col < 1) || (row > n) || (col > n))
            throw new IllegalArgumentException("Invalid args");
        if (!this.isOpen(row, col)) {
            int ind = index(row, col);
            sites[ind] = true;
            this.openSites++;
            if (row == n)
                uf.union(ind, (n*n+1));
            if ((row > 1) && (isOpen(row-1, col)))
                uf.union(ind, ind-n);
            if ((row < n) && (isOpen(row+1, col)))
                uf.union(ind, ind+n);
            if ((col > 1) && (isOpen(row, col-1)))
                uf.union(ind, ind-1);
            if ((col < n) && (isOpen(row, col+1)))
                uf.union(ind, ind+1);
        }
    }

    public boolean isOpen(int row, int col) { // is site (row, col) open?
        if ((row < 1) || (col < 1) || (row > n) || (col > n))
            throw new IllegalArgumentException("Invalid args");
        return sites[index(row, col)];
    }

    public boolean isFull(int row, int col) { // is site (row, col) full?
        if ((row < 1) || (col < 1) || (row > n) || (col > n))
            throw new IllegalArgumentException("Invalid args");
        return isOpen(row, col) && uf.connected(index(row, col), 0);
    }

    public int numberOfOpenSites() { // number of open sites
        return this.openSites;
    }

    public boolean percolates() { // does the system percolate?
        return uf.connected(0, n*n+1);
    }

    public static void main(String[] args) { // test client (optional)
        Percolation map = new Percolation(Integer.parseInt(args[0]));
        int row, col;
        while (!map.percolates()) {
            row = StdIn.readInt();
            col = StdIn.readInt();
            map.open(row, col);
            StdOut.println("Number of open sites:"+map.numberOfOpenSites()+", Percolates:"+map.percolates());
/*
For visualisation of percolation
    for(int i=1; i<=Integer.parseInt(args[0]); i++){
        for(int j=1; j<=Integer.parseInt(args[0]); j++)
            StdOut.print(map.isOpen(i,j)+" ");
        StdOut.println();
            }
*/
        }
    }
/*
-Implementation of WeightedQuickUnionUF
-private int root(int p)
-    {
-        int i = p, j;
-        while (i != id[i])
-            i = id[i];
-        while (id[p]!= i)
-        {
-            j = id[p];
-            id[p] = i;
-            p = j;
-        }
-        return i;
-    }
-    private void union(int p, int q)
-    {
-        int i = root(p);
-        int j = root(q);
-        if (!(i == j))
-        {
-            if (size[i] < size[j]) {
-                id[i] = j;
-                size[j] += size[i];
-            }
-            else {
-                id[j] = i;
-                size[i] += size[j];
-            }
-        }
-    }
-    private boolean connected(int p, int q)
-    {
-        return root(p) == root(q);
-    } */
}
