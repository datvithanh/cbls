package miniproject;


import java.util.HashSet;
import java.util.Set;

import example.HillClimbing;
import localsearch.constraints.basic.LessOrEqual;
import localsearch.functions.conditionalsum.ConditionalSum;
import localsearch.model.ConstraintSystem;
import localsearch.model.IFunction;
import localsearch.model.LocalSearchManager;
import localsearch.model.VarIntLS;

public class Solution2 {
	private MinMaxTypeMultiKnapsackInputItem[] items;
	private MinMaxTypeMultiKnapsackInputBin[] bins;
	private int maxCategories;
	private int maxClasses;
	LocalSearchManager mgr;
	ConstraintSystem S;

    VarIntLS[] x;
    IFunction[] loadedW;
    IFunction[] loadedP;
    IFunction[] selectedT;
    IFunction[] selectedR;
    
    int[][] D;
    int[] W;
    int[] P;
    int[] T;
    int[] R;
    
    public static VarIntLS[] getColumn(VarIntLS[][] array, int index){
    	VarIntLS[] column = new VarIntLS[array.length];
        for(int i=0; i < array.length; i++){
        	column[i] = array[i][index];
        }
        return column;
    }
    
    public static int[] getColumn(int[][] array, int index){
    	int[] column = new int[array.length];
        for(int i=0; i < array.length; i++){
        	column[i] = array[i][index];
        }
        return column;
    }
    
    public void stateModel() {
    	mgr = new LocalSearchManager();
        S = new ConstraintSystem(mgr);

    	x = new VarIntLS[items.length];
    	loadedW = new IFunction[bins.length];
    	loadedP = new IFunction[bins.length];
    	selectedT = new IFunction[bins.length];
    	selectedR = new IFunction[bins.length];
    	
    	for(int i = 0; i < items.length; ++i) {
    		Set<Integer> set = new HashSet<Integer>();
    		for(int v: items[i].getBinIndices())
    			set.add(v);
    		x[i] = new VarIntLS(mgr, set);
    	}
   	        
        S = new ConstraintSystem(mgr);        	
        
        for(int j = 0; j < bins.length; ++j)
        {
        	loadedW[j] = new ConditionalSum(x, W, j);
        	loadedP[j] = new ConditionalSum(x, P, j);
        	selectedT[j] = new CountDistinct(x, T, j);
        	selectedR[j] = new CountDistinct(x, R, j);
        	
        	S.post(new LessOrEqual(loadedW[j], (int) bins[j].getCapacity()));
        	S.post(new LessOrEqual((int) bins[j].getMinLoad(), loadedW[j]));
        	S.post(new LessOrEqual(loadedP[j], (int) bins[j].getP()));
        	S.post(new LessOrEqual(selectedT[j], (int) bins[j].getT()));
        	S.post(new LessOrEqual(selectedR[j], (int) bins[j].getR()));
        }
        
        mgr.close();
    }
    
    public void prepareData(String fn) {
		MinMaxTypeMultiKnapsackInput a = new MinMaxTypeMultiKnapsackInput();
		MinMaxTypeMultiKnapsackInput b;
		b = a.loadFromFile(fn);
		
		items = b.getItems();
		bins = b.getBins();
		maxClasses = 0;
		maxCategories = 0;
				
		D = new int[items.length][bins.length];
		W = new int[items.length];
		P = new int[items.length];
		T = new int[items.length];
		R = new int[items.length];
		
		for(int i = 0; i < items.length; ++i) {
			maxCategories = Math.max(maxCategories, items[i].getT()+1);
			maxClasses = Math.max(maxClasses, items[i].getR()+1);
			int[] d = items[i].getBinIndices();
			for(int j = 0; j < d.length; ++j)
				D[i][d[j]] = 1;
			W[i] = (int) items[i].getW();
			P[i] = (int) items[i].getP();
			T[i] = (int) items[i].getT();
			R[i] = (int) items[i].getR();
		}
    }
    
    public void search() {
//		TabuSearch ts = new TabuSearch();
//		ts.search(S, 30, 10, 10000, 100);
    	HillClimbing s = new HillClimbing();
        s.hillClimbing(S, 1000);
	}
    
    public static void main(String[] args) {
    	String fn = "data/miniproject/MinMaxTypeMultiKnapsackInput-1000.json";
    	Solution1 s = new Solution1();
    	s.prepareData(fn);
    	s.stateModel();
    	s.search();
    }
}
