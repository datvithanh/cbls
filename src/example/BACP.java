
package example;

import java.io.File;
import java.util.Scanner;
import localsearch.constraints.basic.LessOrEqual;
import localsearch.constraints.basic.LessThan;
import localsearch.functions.conditionalsum.ConditionalSum;
import localsearch.model.ConstraintSystem;
import localsearch.model.IFunction;
import localsearch.model.LocalSearchManager;
import localsearch.model.VarIntLS;

public class BACP {
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
    
    
    LocalSearchManager mgr;
    VarIntLS[] x;
    IFunction[] credits;    // credits[i]: so tin chi hoc ky i
    IFunction[] nbCourses;  // nbCourses[i]: so mon hoc ki i
    ConstraintSystem S;
    
    
    public void stateModel() {
        mgr = new LocalSearchManager();
        x = new VarIntLS[N];
        for(int i = 0; i < N; i++)
            x[i] = new VarIntLS(mgr, 0, M-1);
        S = new ConstraintSystem(mgr);
        
        for(int k = 0; k < I.length; k++) {
            S.post(new LessThan(x[I[k]], x[J[k]]));
        }
        
        credits = new IFunction[M];
        for(int i = 0; i < M; i++) {
            credits[i] = new ConditionalSum(x, crd, i);
            S.post(new LessOrEqual(credits[i], maxCrd));
            S.post(new LessOrEqual(minCrd, credits[i]));
        }
        
        nbCourses = new IFunction[M];
        for(int i = 0; i < M; i++) {
            nbCourses[i] = new ConditionalSum(x, i);
            S.post(new LessOrEqual(nbCourses[i], maxCourse));
            S.post(new LessOrEqual(minCourse, nbCourses[i]));
        }
        
        mgr.close();
    }
    
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
        HillClimbing s = new HillClimbing();
        s.hillClimbing(S, 1000);
    }
    
    // Output
    public void printResult() {
        System.out.println("\n------Result------");
        for(int i = 0; i < M; i++) {
            System.out.print("Ki " + i + ": ");
            for(int j = 0; j < N; j++) {
                if(x[j].getValue() == i)
                    System.out.print(j + ", ");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        BACP s = new BACP();
        s.readData("./src/data/BACP/bacp.in02");
        s.stateModel();
        s.search();
        s.printResult();
    }
}
