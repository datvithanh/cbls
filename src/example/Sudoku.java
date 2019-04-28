package example;


import java.util.ArrayList;
import java.util.Random;
import localsearch.constraints.alldifferent.AllDifferent;
import localsearch.model.ConstraintSystem;
import localsearch.model.LocalSearchManager;
import localsearch.model.VarIntLS;


public class Sudoku {
    class SwapMove {
        int i;
        int j1;
        int j2;

        public SwapMove(int i, int j1, int j2) {
            this.i = i;
            this.j1 = j1;
            this.j2 = j2;
        }
        
    }
    
    LocalSearchManager mgr;
    VarIntLS[][] x;
    ConstraintSystem S;
    int n = 9;
    
    public void stateModel() {
        mgr = new LocalSearchManager();
        x = new VarIntLS[n][n];
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++) {
                x[i][j] = new VarIntLS(mgr, 1, n);
                x[i][j].setValue(j+1);
            }
        S = new ConstraintSystem(mgr);
        
        for(int i = 0; i < n; i++) {
            VarIntLS[] y = new VarIntLS[n];
            for(int j = 0; j < n; j++)
                y[j] = x[i][j];
            S.post(new AllDifferent(y));
        }
        
        for(int j = 0; j < n; j++) {
            VarIntLS[] y = new VarIntLS[n];
            for(int i = 0; i < n; i ++) {
                y[i] = x[i][j];
            }
            S.post(new AllDifferent(y));
        }
        
        for (int I = 0; I <= 2; I++) {
            for(int J = 0; J <= 2; J++) {
                VarIntLS[] y = new VarIntLS[n];
                int idx = -1;
                for(int i = 0; i <= 2; i++) 
                    for(int j = 0; j <= 2; j++) {
                        idx++;
                        y[idx] = x[3*I+i][3*J+j];
                    }
                S.post(new AllDifferent(y));
            }
        }
        
        mgr.close();
    }
    
    public void print(VarIntLS[][] x) {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++)
                System.out.printf(x[i][j].getValue() + " ");
            System.out.println();
        }
//        System.out.println(S.violations());
    }
    
    
    
    public void search() {
//        HillClimbing s = new HillClimbing();
//        s.hillClimbing(S, 1000);
            
        ArrayList<SwapMove> cand = new ArrayList<SwapMove>();
        int it = 0;
        Random R = new Random();
        while(it < 100000 && S.violations() > 0) {
            cand.clear();
            int minDelta = Integer.MAX_VALUE;
            for(int i = 0; i <= n-1; i++) {
                for(int j1 =  0; j1 < n-1; j1++) {
                    for(int j2 = j1+1; j2 <= n-1; j2++) {
                        int delta = S.getSwapDelta(x[i][j1], x[i][j2]);
                        if(delta < minDelta) {
                            cand.clear();
                            cand.add(new SwapMove(i, j1, j2));
                            minDelta = delta;
                        }else if(delta == minDelta) {
                            cand.add(new SwapMove(i, j1, j2));
                        }
                    }
                }
            }
            SwapMove m = cand.get(R.nextInt(cand.size()));
            x[m.i][m.j1].swapValuePropagate(x[m.i][m.j2]);
            System.out.println("Step " + it + ", S = " + S.violations());
            it++;
        }
    }
    
    public void solve() {
        stateModel();
        search();
    }
    public static void main(String[] args) {
        Sudoku ex = new Sudoku();
        ex.solve();
        ex.print(ex.x);
    }
}
