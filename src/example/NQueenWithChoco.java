package example;

import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.variables.integer.IntegerVariable;

public class NQueenWithChoco {
	public void  test() {
		double t0 = System.currentTimeMillis();
		CPModel m = new CPModel();
		
		int n = 100;
		IntegerVariable[] x = new IntegerVariable[n];
		for(int i = 0 ; i < n ; i++) {
			x[i] = Choco.makeIntVar("x" +i, 0 , n-1);
		}
		
		m.addConstraint(Choco.allDifferent(x));
                
		for( int i = 0 ; i < n-1 ; i++) {
                    for(int j = i + 1 ; j < n ; j++) {
			m.addConstraint(Choco.neq(Choco.plus(x[i],i) , Choco.plus(x[j],j))); // x[i]+i != x[j]+j
			m.addConstraint(Choco.neq(Choco.minus(x[i],i) , Choco.minus(x[j],j))); // x[i]-i != x[j]-j
                    }
		}
                
		CPSolver s = new CPSolver();
		s.read(m);
		s.solve();
                s.printRuntimeStatistics();
		
		for( int i = 0 ; i < n ; i++) {
			System.out.println("x[" + i + "] = " + s.getVar(x[i]).getVal());
		}
		double t1 = System.currentTimeMillis() - t0;
		
		System.out.println("time = " + t1);
	}

	public static void main(String[] args) {
		NQueenWithChoco t = new NQueenWithChoco();
                t.test();
        }        
}