
package example;

import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.variables.integer.IntegerExpressionVariable;
import choco.kernel.model.variables.integer.IntegerVariable;
import java.io.File;
import java.util.Scanner;

public class ChocoBACP {
    // input data
    int N; // so mon hoc: 0,1,2...N-1
    int M; // so hoc ky: 0,1,2...M-1
    int[] crd;  // so tin chi cua 1 mon hoc
    int minCourse;  // so mon hoc toi thieu trong mot ki
    int maxCourse;  // so mon hoc toi da trong mot ky
    int minCrd; // so tin chi toi thieu trong mot ky
    int maxCrd; // so tin chi toi da trong mot ky
    int[] I;    // I,J cho biet cac hoc phan tien quyet ( I[i] phai nho hon J[i])
    int[] J;
    
    // Input
    public void readData(String fn) {
        try{
            Scanner in = new Scanner(new File(fn));
            
            N = in.nextInt();
            M = in.nextInt();
            minCrd = in.nextInt();
            maxCrd = in.nextInt();
            minCourse = in.nextInt();
            maxCourse = in.nextInt();
            crd = new int[N];
            for(int i = 0; i < N; i++) {
                crd[i] = in.nextInt();
            }
            int K = in.nextInt();
            I = new int[K];
            J = new int[K];
            for(int k = 0; k < K; k++) {
                I[k] = in.nextInt()-1;
                J[k] = in.nextInt()-1;
            }
            in.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void search() {
        CPModel m = new CPModel();
        
        IntegerVariable[] x = new IntegerVariable[N];
        for(int i = 0; i < N; i++)
            x[i] = Choco.makeIntVar("x"+i, 0, M-1);
        
        for(int k = 0; k < I.length; k++) 
            m.addConstraint(Choco.lt(x[I[k]], x[J[k]]));
        
        IntegerExpressionVariable[] credits;    // credits[i]: so tin chi hoc ky i
        for(int i = 0; i < M; i++) {
            
        }
        
        IntegerExpressionVariable[] nbCourses;  // nbCourses[i]: so mon hoc ki i
        for(int i = 0; i < M; i++) {
        }
        
        CPSolver s = new CPSolver();
        s.read(m);
        s.solve();
        s.printRuntimeStatistics();
    }
    
    public static void main(String[] args) {
        
    }
}
