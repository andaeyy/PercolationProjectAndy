import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    public final int size;
    public final boolean[] opensites;
    public final WeightedQuickUnionUF pconnections;
    public final WeightedQuickUnionUF fullconnections;
    public final int top;
    public final int bottom;
    public int opencount;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Size must be positive");
        }
        size = n;
        opensites = new boolean[n * n];
        top = n * n;
        bottom = n * n + 1;
        pconnections = new WeightedQuickUnionUF(n * n + 2);
        fullconnections = new WeightedQuickUnionUF(n * n + 1);
        opencount = 0;
    }
     public int index(int row, int col) {
        return (row - 1) * size + col - 1;
    }

    public void valid(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException("Row, col must be between 1 and " + size);
        }
    }
    public void connectopen(int site, int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            return;
        }
        int neighbor = index(row, col);
        if (opensites[neighbor]) {
            pconnections.union(site, neighbor);
            fullconnections.union(site, neighbor);
        }
    }

    public void open(int row, int col) {
        valid(row, col);
        int site = index(row, col);
        if (opensites[site]) {
            return;
        }
        opensites[site] = true;
        opencount++;

        if (row == 1) {
            pconnections.union(site, top);
            fullconnections.union(site, top);
        }
        if (row == size) {
            pconnections.union(site, bottom);
        }

        connectopen(site, row - 1, col);
        connectopen(site, row + 1, col);
        connectopen(site, row, col - 1);
        connectopen(site, row, col + 1);
    }

    public boolean isOpen(int row, int col) {
        valid(row, col);
        return opensites[index(row, col)];
    }

    public boolean isFull(int row, int col) {
        valid(row, col);
        int site = index(row, col);
        return opensites[site]
                && fullconnections.find(site) == fullconnections.find(top);
    }

    public int numberOfOpenSites() {
        return opencount;
    }

    public boolean percolates() {
        return pconnections.find(top)== pconnections.find(bottom);
    }



    public static void main(String[] args) {

    }
}
