
package example;

import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.variables.integer.IntegerVariable;
import java.io.File;
import java.util.Scanner;

public class ChocoMTDT {
    int n;  // juries
    int[][] table;  // result table
    int[] ip;     // internal professorsin.nextLine();  in.nextLine();
    int[] ep;     // external professors
    int[][] match;  // subject match between professors and student
    int slots;  // number of slots
    int rooms;  // number of rooms
    
    // Input
    public void readData(String fn) {
        try{
            Scanner in = new Scanner(new File(fn));
            
            in.nextLine();
            n = in.nextInt(); in.nextLine();
            in.nextLine();
            
            table = new int[n][9];
            for(int i = 0; i < n; i++) 
                for(int j = 0; j < 9; j++) 
                    table[i][j] = in.nextInt();
            in.nextLine();  in.nextLine();
            
            ip = new int[in.nextInt()];
            for(int i = 0; i < ip.length; i++)
                ip[i] = in.nextInt();
            in.nextLine();  in.nextLine();
            
            ep = new int[in.nextInt()];
            for(int i = 0; i < ep.length; i++)
                ep[i] = in.nextInt();
            in.nextLine();  in.nextLine();
            
            match = new int[494][3];
            for(int i = 0; i < 494; i++) 
                for(int j = 0; j < 3; j++) 
                    match[i][j] = in.nextInt();
            in.nextLine();  in.nextLine();
            
            slots = in.nextInt();
            rooms = in.nextInt();
            
            in.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void solve() {
        CPModel m = new CPModel();
        
        IntegerVariable[][] x = new IntegerVariable[n][9];
        for(int i = 0; i < n; i++) {
            x[i][0] = Choco.makeIntVar("StudentID", i, i);
            x[i][1] = Choco.makeIntVar("SupervisorID", ip);
            x[i][2] = Choco.makeIntVar("Examiner1ID", ip);
            x[i][3] = Choco.makeIntVar("Examiner2ID", ep);
            x[i][4] = Choco.makeIntVar("PresidentID", ip);
            x[i][5] = Choco.makeIntVar("SecretaryID", ip);
            x[i][6] = Choco.makeIntVar("AdditionalMemberID", ep);
            x[i][7] = Choco.makeIntVar("Slot", 0, slots-1);
            x[i][8] = Choco.makeIntVar("Room", 0, rooms-1);
        }
        
        // xep giang vien huong dan cho hoc vien
        for(int i = 0; i < n; i++) {
            m.addConstraint(Choco.eq(x[i][0], table[i][0]));
            m.addConstraint(Choco.eq(x[i][1], table[i][1]));
        }
        
        // 5 thanh vien hoi dong khac nhau
        for(int i = 0 ; i < n; i++) {
            IntegerVariable[] y = new IntegerVariable[6];
            for(int j = 0; j < 6; j++) {
                y[j] = x[i][j+1];
            }
            m.addConstraint(Choco.allDifferent(y));
        }        
        
        // 2 hoc vien cung phong khac kip
        for(int i = 0; i < n-1; i++) {
            for(int j = i+1; j < n; j++) {
                m.addConstraint(Choco.implies(Choco.eq(x[i][8], x[j][8]), Choco.neq(x[i][7], x[j][7])));
            }
        }
        
        // moi giang vien tai moi kip khong duoc tham gia qua 1 hoi dong
//        for(int i = 0; i < n-1; i++) {
//            for(int j = i+1; j < n; j++) {
//                IntegerVariable[] z = new IntegerVariable[2];
//                for(int l = 2; l <= 6; l++) {
//                    z[0] = x[0][2];
//                    z[1] = x[1][2];
//                }
//                m.addConstraint(Choco.implies(Choco.eq(x[i][7], x[i][7]), Choco.allDifferent(z)));
//            }
//        }
        
        CPSolver s = new CPSolver();
        s.read(m);
        s.solve();
        s.printRuntimeStatistics();
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < 9; j++) {
                System.out.printf("%3d ", s.getVar(x[i][j]).getVal()); 
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        ChocoMTDT t = new ChocoMTDT();
        t.readData("/home/duy/NetBeansProjects/TimKiemCucBoDuaTrenRangBuoc/src/data/MTDT/jury-data-10-4-3.txt");
        t.solve();
    }
}
