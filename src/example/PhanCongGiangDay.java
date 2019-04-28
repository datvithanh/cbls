
package example;

import choco.Choco;
import choco.Options;
import choco.kernel.model.variables.integer.IntegerVariable;
import java.util.ArrayList;

public class PhanCongGiangDay {
    int N;  // so mon hoc
    int M;  // so giao vien
    int[] t; // so tiet cua mot mon hoc i
    int alpha;
    int beta;
    
    // mo hinh
    IntegerVariable[][] Y;
    IntegerVariable f;
    
    public void solve() {
        Y = new IntegerVariable[N][M];
        f = Choco.makeIntVar("obj", 0, 0, Options.V_OBJECTIVE);
    }
    
    public static void main(String[] args) {
        
    }
}
