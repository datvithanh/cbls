package example;

import localsearch.model.LocalSearchManager;
import localsearch.model.VarIntLS;
import localsearch.selectors.MinMaxSelector;

import java.io.PrintWriter;

import localsearch.constraints.alldifferent.AllDifferent;
import localsearch.functions.basic.FuncPlus;
import localsearch.model.ConstraintSystem;
import localsearch.model.IFunction;
public class NQueen {
	LocalSearchManager mgr;
	VarIntLS[] x;
	ConstraintSystem S;
	int n;
	public void stateModel() {
		mgr = new LocalSearchManager();
		x = new VarIntLS[n];
		for(int i=0;i<n;i++) {
			x[i] = new VarIntLS(mgr,0,n-1);
		}
		S = new ConstraintSystem(mgr);
                
		S.post(new AllDifferent(x));
                
		IFunction[] f1= new IFunction[n];
		for(int i =0;i<n;i++) {
			f1[i] = new FuncPlus(x[i],i);
		}
		S.post(new AllDifferent(f1));
		
		IFunction[] f2= new IFunction[n];
		for(int i =0;i<n;i++) {
			f2[i] = new FuncPlus(x[i],-i);
		}
		S.post(new AllDifferent(f2));
		
		mgr.close();
	}
	public void printSolution() {
		for(int i=0;i<n;i++) {
			System.out.print(x[i].getValue()+" " );
		}
		System.out.println();
	}
	public NQueen(int n) {
		this.n=n;
	}
	public void Search() {
		System.out.print("Init violations = "+S.violations()+"\n");
		printSolution();
		int it=0;
		MinMaxSelector mms = new MinMaxSelector(S);
		while(it<100000 && S.violations()>0) {
			VarIntLS sel_x = mms.selectMostViolatingVariable();
			int sel_v = mms.selectMostPromissingValue(sel_x);
			sel_x.setValuePropagate(sel_v);
			System.out.print("Init violations = "+S.violations()+"\n");
			it++;
		}
	}
	public void solve() {
		stateModel();
		Search();
		//hillClimbing();
		printSolution();
		printHTML("./NQueen.html");
	}
	public void printHTML(String fn) {
		try {
			PrintWriter out= new PrintWriter(fn);
			out.print("<Table border=1>");
			for(int i=0;i<n;i++) {
				out.println("<tr>");
				for(int j=0;j<n;j++) {
					if(x[j].getValue() == i) {
						out.println("<td width = '20px' height='20px' bgcolor='red'></td>");
					}else {
						out.println("<td width = '20px' height='20px' bgcolor='blue'></td>");
					}
				}
				out.print("</tr>");
			}
			out.print("</table>");
			out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void hillClimbing() {
		int it=0;
		while(it<100000 &&S.violations()>0) {
			int mindelta = Integer.MAX_VALUE;
			int seli = -1;
			int selv = -1;
			for(int i = 0;i<n;i++) {
				for(int v = x[i].getMinValue();v<=x[i].getMaxValue();v++)
				{
					int delta = S.getAssignDelta(x[i], v);
					if(delta<mindelta) {
						mindelta = delta;
						seli = i;
						selv = v;
					}
				}
			}
			x[seli].setValue(selv);
			System.out.print("time "+it+" vio:"+S.violations()+"\n");
			it++;
		}
	}
	public static void main(String ...args) {
		NQueen nq = new NQueen(8);
		nq.solve();
	}
}
