package miniproject;


import example.HillClimbing;
import localsearch.constraints.basic.IsEqual;
import localsearch.constraints.basic.LessOrEqual;
import localsearch.functions.conditionalsum.ConditionalSum;
import localsearch.functions.sum.Sum;
import localsearch.model.ConstraintSystem;
import localsearch.model.IFunction;
import localsearch.model.LocalSearchManager;
import localsearch.model.VarIntLS;
import localsearch.search.TabuSearch;

public class Solution1 {
	private MinMaxTypeMultiKnapsackInputItem[] items;
	private MinMaxTypeMultiKnapsackInputBin[] bins;
	private int maxCategories;
	private int maxClasses;
	private int coef = 1000;
	LocalSearchManager mgr;
	ConstraintSystem S;

    VarIntLS[][] x;
    VarIntLS[][] cat;
    VarIntLS[][] cla;
    IFunction[] loadedW;
    IFunction[] loadedP;
    IFunction[] selCat;
    IFunction[] selCla;
    
    int[][] D;
    int[] W;
    int[] P;
    
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

    	x = new VarIntLS[items.length][bins.length];
    	cat = new VarIntLS[bins.length][maxCategories];
    	cla = new VarIntLS[bins.length][maxClasses];
    	loadedW = new IFunction[bins.length];
    	loadedP = new IFunction[bins.length];
    	selCat = new IFunction[bins.length];
    	selCla = new IFunction[bins.length];
    	
    	for(int i = 0; i < items.length; ++i)
    		for(int j = 0; j < bins.length; ++j)
    			x[i][j] = new VarIntLS(mgr, 0, 1);
    	
    	for(int i = 0; i < maxCategories; ++i)
    		for(int j = 0; j < bins.length; ++j)
    			cat[j][i] = new VarIntLS(mgr, 0, 1);
    	
    	for(int i = 0; i < maxClasses; ++i)
    		for(int j = 0; j < bins.length; ++j)
    			cla[j][i] = new VarIntLS(mgr, 0, 1);
   	        
        S = new ConstraintSystem(mgr);

        for(int i = 0; i < items.length; ++i) 
        	S.post(new IsEqual(new Sum(x[i]), 1));
        
        for(int i = 0; i < items.length; ++i)
    		for(int j = 0; j < bins.length; ++j)
    		{
    			S.post(new LessOrEqual(x[i][j], D[i][j]));
    			S.post(new LessOrEqual(x[i][j], cat[j][items[i].getT()]));
    			S.post(new LessOrEqual(x[i][j], cla[j][items[i].getR()]));
    		}
        
        for(int j = 0; j < bins.length; ++j)
        {
        	loadedW[j] = new ConditionalSum(getColumn(x, j), W, 1);
        	loadedP[j] = new ConditionalSum(getColumn(x, j), P, 1);
        	selCat[j] = new ConditionalSum(cat[j], 1);
        	selCla[j] = new ConditionalSum(cla[j], 1);
        	
        	S.post(new LessOrEqual(loadedW[j], (int) (coef * bins[j].getCapacity())));
        	S.post(new LessOrEqual((int) (coef*bins[j].getMinLoad()), loadedW[j]));
        	S.post(new LessOrEqual(loadedP[j], (int) (coef * bins[j].getP())));
        	
        	S.post(new LessOrEqual(selCat[j], (int) bins[j].getT()));
        	S.post(new LessOrEqual(selCla[j], (int) bins[j].getR()));
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
		
		for(int i = 0; i < items.length; ++i) {
			maxCategories = Math.max(maxCategories, items[i].getT()+1);
			maxClasses = Math.max(maxClasses, items[i].getR()+1);
			int[] d = items[i].getBinIndices();
			for(int j = 0; j < d.length; ++j)
				D[i][d[j]] = 1;
			W[i] = (int) (coef * items[i].getW());
			P[i] = (int) (coef * items[i].getP());
		}
    }
    
    public void search() {
//		TabuSearch ts = new TabuSearch();
//		ts.search(S, 30, 10, 10000, 100);
    	HillClimbing s = new HillClimbing();
        s.hillClimbing(S, 1000);
	}
    
    public static void main(String[] args) {
    	String fn = "data/miniproject/MinMaxTypeMultiKnapsackInput-0.json";
    	Solution1 s = new Solution1();
    	s.prepareData(fn);
    	s.stateModel();
    	s.search();
    }
}
