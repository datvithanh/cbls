package example;


import java.util.ArrayList;
import java.util.Random;
import localsearch.constraints.alldifferent.AllDifferent;
import localsearch.constraints.basic.Implicate;
import localsearch.constraints.basic.IsEqual;
import localsearch.constraints.basic.LessOrEqual;
import localsearch.constraints.basic.NotEqual;
import localsearch.functions.basic.FuncPlus;
import localsearch.model.ConstraintSystem;
import localsearch.model.IConstraint;
import localsearch.model.IFunction;
import localsearch.model.LocalSearchManager;
import localsearch.model.VarIntLS;

class AssignMove {
    int i;
    int v;
    public AssignMove(int i, int v) {
        this.i = i; this.v = v;
    }
}
public class Example {
    LocalSearchManager mgr;
    VarIntLS[] x;
    ConstraintSystem S;
    
    public void stateModel() {
        mgr = new LocalSearchManager();
        x = new VarIntLS[5];
        for(int i = 0; i < 5; i++)
            x[i] = new VarIntLS(mgr, 1, 5);
        S = new ConstraintSystem(mgr);
        
// tuong duong dong tren
        
//        IFunction f1 = new FuncPlus(x[2], 3);
//        IConstraint c1 = new NotEqual(f1, x[1]);
//        S.post(c1);

        S.post(new NotEqual(new FuncPlus(x[2], 3), x[1]));
        S.post(new LessOrEqual(x[3], x[4]));
        S.post(new IsEqual(new FuncPlus(x[2], x[3]), new FuncPlus(x[0], 1)));
        S.post(new LessOrEqual(x[4], 3));
        S.post(new IsEqual(new FuncPlus(x[4], x[1]), 7));
        
//        S.post(new Implicate(new IsEqual(x[2], 1), new NotEqual(x[4], 2)));
        
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
                for(int v = y[i].getValue(); v <= y[i].getMaxValue(); v++) {
                    int d = c.getAssignDelta(y[i], v);  // getAssignDelta(x[i], v):trả về sự thay đổi của vi phạm khi x[i] được gán giá trị v
//                    System.out.println("getAssignDelta(x[" + i + "], " + v + ") = " + d);
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
            // violation(): tính số vi phạm
            System.out.println("Step " + it + ", violations = " + c.violations());
            it++;
        }
    }
    
    public void hillClimbing2() {
        ArrayList<AssignMove> cand = new ArrayList<AssignMove>();
        Random R = new Random();
        int it = 0;
        while(it < 10000 && S.violations() > 0) {
            cand.clear();
            int minDelta = Integer.MAX_VALUE;
            for(int i = 0; i < x.length; i++) {
                for(int v = x[i].getValue(); v <= x[i].getMaxValue(); v++) {
                    int d = S.getAssignDelta(x[i], v);  // getAssignDelta(x[i], v):trả về sự thay đổi của vi phạm khi x[i] được gán giá trị v
                    System.out.println("getAssignDelta(x[" + i + "], " + v + ") = " + d);
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
            x[m.i].setValuePropagate(m.v);
            // violation(): tính số vi phạm
//            System.out.println("Step " + it + ", violations = " + S.violations());
            print();
            it++;
        }
    }
    
    public void print() {
        for(int i = 0; i < x.length; i++) {
//            System.out.println("x[" + i + "] = " + x[i].getValue());
            System.out.printf(x[i].getValue() + " ");
        }
        System.out.println();
    }
    public void search() {
//        hillClimbing(S, 1000);
        hillClimbing2();
    }
    
    public void solve() {
        stateModel();
        search();
        System.out.println();
    }
    public static void main(String[] args) {
        Example ex = new Example();
        ex.solve();
//        ex.print();
    }
}
