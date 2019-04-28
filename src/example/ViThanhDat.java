package example;

import java.util.ArrayList;
import java.util.Random;

import localsearch.constraints.basic.LessOrEqual;
import localsearch.constraints.basic.NotEqual;
import localsearch.constraints.basic.OR;
import localsearch.functions.conditionalsum.ConditionalSum;
import localsearch.model.ConstraintSystem;
import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.LocalSearchManager;
import localsearch.model.VarIntLS;

public class ViThanhDat {
	static int N = 20, M = 5;
	static int[] V = {20,15,10,20,20,25,30,15,10,10,20,25,20,10,30,40,25,35,10,10};
	static int[] cap = {60,70,90,80,100};
	static int[][] p = {{0,1}, {7,8}, {12,17}, {8,9}, {1,2,9}, {0,9,2}};
	
    LocalSearchManager mgr;
    VarIntLS[] x;
    IFunction[] c;
    ConstraintSystem S;
    IFunction[][] q;

    public void stateModel() {
    	mgr = new LocalSearchManager();
        x = new VarIntLS[N];
        for(int i = 0; i < N; i++)
        		x[i] = new VarIntLS(mgr, 0, M-1);
        
        S = new ConstraintSystem(mgr);
        
        c = new IFunction[M];

        for(int i = 0; i < M; ++i) {
        	c[i] = new ConditionalSum(x, V, i);
        	S.post(new LessOrEqual(c[i], cap[i]));
        }
        
        for(int i = 0; i < p.length; ++i) {
        	IConstraint con;
        	con = new NotEqual(x[p[i][0]], x[p[i][1]]);
        	if(p[i].length > 2)
	        	for(int j = 2; j < p[i].length; ++j) 
	        		con = new OR(con, new NotEqual(x[p[i][j]], x[p[i][j-1]]));
        	S.post(con);
//        	if(p[i].length == 2)
//        		S.post(new NotEqual(x[p[i][0]], x[p[i][1]]));
//        	else
//        		S.post(new OR(new NotEqual(x[p[i][0]], x[p[i][1]]), new NotEqual(x[p[i][1]], x[p[i][2]])));
        }
        
        mgr.close();
    }
    
    public void hillClimbing(ConstraintSystem c, int maxIter) {
        VarIntLS[] y = c.getVariables();
        ArrayList<AssignMove> cand = new ArrayList<AssignMove>();
        Random R = new Random();
        int it = 0;
        while(it < maxIter && c.violations() > 0) {
            cand.clear();
            int minDelta = Integer.MAX_VALUE;
            for(int i = 0; i < y.length; i++) {
                for(int v = y[i].getMinValue(); v <= y[i].getMaxValue(); v++) {
                    int d = c.getAssignDelta(y[i], v);
                    if(d < minDelta) {
                        cand.clear();
                        cand.add(new AssignMove(i, v));
                        minDelta = d;
                    }else if(d == minDelta) {
                        cand.add(new AssignMove(i, v));
                    }
                }
            }
            int idx = R.nextInt(cand.size());   
            AssignMove m = cand.get(idx);
            y[m.i].setValuePropagate(m.v);
            System.out.println("Step " + it + ", violations = " + c.violations());
            it++;
        }
    }
    
    public void search() {
        hillClimbing(S, 10000);
    }
    
    public void printResult() {
        System.out.println("\n------Result------");
        for(int i = 0; i < N; i++) {
            System.out.println("X" + i + " = " + x[i].getValue());
        }
        
        for(int j = 0; j < M; ++j) {
        	int sum = 0;
        	for(int i = 0; i < N; ++i)
        		if(x[i].getValue() == j)
        			sum += V[i];
            System.out.println("C" + j + " = " + sum);
        }
    }
    
    public static void main(String[] args) {
    	ViThanhDat d = new ViThanhDat();
        d.stateModel();
        d.search();
        d.printResult();
    }
//  Step 0, violations = 306
//	Step 1, violations = 270
//	Step 2, violations = 240
//	Step 3, violations = 210
//	Step 4, violations = 185
//	Step 5, violations = 160
//	Step 6, violations = 135
//	Step 7, violations = 113
//	Step 8, violations = 93
//	Step 9, violations = 73
//	Step 10, violations = 53
//	Step 11, violations = 37
//	Step 12, violations = 21
//	Step 13, violations = 10
//	Step 14, violations = 0
//
//	------Result------
//	X0 = 2
//	X1 = 1
//	X2 = 0
//	X3 = 4
//	X4 = 0
//	X5 = 2
//	X6 = 1
//	X7 = 3
//	X8 = 0
//	X9 = 4
//	X10 = 4
//	X11 = 3
//	X12 = 4
//	X13 = 0
//	X14 = 4
//	X15 = 3
//	X16 = 1
//	X17 = 2
//	X18 = 2
//	X19 = 0
//	C0 = 60
//	C1 = 70
//	C2 = 90
//	C3 = 80
//	C4 = 100
}