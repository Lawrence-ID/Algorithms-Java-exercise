import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean grid[][];
    private int n;
    private int openSize;
    private WeightedQuickUnionUF uf,uf0;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if(n <= 0)
            throw new IllegalArgumentException("illegal n!");
        this.n = n;
        openSize = 0;
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }

        uf = new WeightedQuickUnionUF((n*n) + 2);
        uf0 = new WeightedQuickUnionUF((n*n) + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if(row < 1 || row > n || col < 1 || col > n){
            throw new IllegalArgumentException("illegal argument EXCEPTION");
        }
        if(!grid[row-1][col-1]){
            grid[row-1][col-1] = true;
            openSize++;

            int pos = map2Dto1D(row, col);
            if(row == 1){
                uf.union(pos,0);
                uf0.union(pos,0);
            }
            //down
            if(row > 1 && grid[row-2][col-1]){
                uf.union(pos,pos-n);
                uf0.union(pos,pos-n);
            }
            //up
            if(row < n && grid[row][col-1]){
                uf.union(pos,pos+n);
                uf0.union(pos,pos+n);
            }
            //left
            if(col > 1 && grid[row-1][col-2]){
                uf.union(pos,pos-1);
                uf0.union(pos,pos-1);
            }
            //right
            if(col < n && grid[row-1][col]){
                uf.union(pos,pos+1);
                uf0.union(pos,pos+1);
            }
            if(row == n){
                uf.union(pos,n*n+1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if(row < 1 || row > n || col < 1 || col > n){
            throw new IllegalArgumentException("illegal argument EXCEPTION");
        }
        return grid[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if(row < 1 || row > n || col < 1 || col > n){
            throw new IllegalArgumentException("illegal argument EXCEPTION");
        }
        return uf0.connected(map2Dto1D(row, col),0);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return openSize;
    }

    // does the system percolate?
    public boolean percolates(){
        return uf.connected(0,n*n+1);
    }

    public int map2Dto1D(int row,int col){
        return (row - 1) * n + col;
    }
    // test client (optional)
    public static void main(String[] args){
        Percolation p = new Percolation(3);
        p.open(1, 2);
        p.open(2, 2);
        p.open(3, 2);
        StdOut.println(p.isOpen(1,1));
        StdOut.println(p.percolates());

    }
}
