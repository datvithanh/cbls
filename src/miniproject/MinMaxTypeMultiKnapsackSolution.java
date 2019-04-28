package miniproject;

import java.util.ArrayList;
import java.util.Collections;

import localsearch.constraints.basic.LessOrEqual;
import localsearch.functions.basic.FuncMult;
import localsearch.functions.basic.FuncPlus;
import localsearch.functions.sum.SumFun;
import localsearch.model.ConstraintSystem;
import localsearch.model.IFunction;
import localsearch.model.LocalSearchManager;
import localsearch.model.VarIntLS;
import localsearch.search.TabuSearch;
import localsearch.selectors.MinMaxSelector;

public class MinMaxTypeMultiKnapsackSolution {
	private int theNumberOfItems;
	private int theNumberOFBins;
	private int maxT;
	private int maxR;
	private static MinMaxTypeMultiKnapsackInputItem[] items;
	private static MinMaxTypeMultiKnapsackInputBin[] bins;
	private static ItemTemporary[] itemsTemporary;
	private static BinsTemporary[] binsTemporary;
//	private ItemTemporary[] itemsTemporary;
//	private BinsTemporary[] binsTemporary;
	LocalSearchManager mgr;
	VarIntLS[][] itemForBin;  // X[i,b] = 1,item i được xếp vào bin b, X[i,b] = 0: item i không được xếp vào bin b
	VarIntLS[][] binForItem; // Y[i,b] = 1: bin b có items thể loại i 
	VarIntLS[][] binForItemsOfClass; // Z[i,b] = 1: bin b có items thuộc lớp i
//	public static Integer[] listItemCategory;
//	public static Integer[] listItemClass;
//	
	ConstraintSystem S;
	
	public MinMaxTypeMultiKnapsackSolution(MinMaxTypeMultiKnapsackInputItem[] a, MinMaxTypeMultiKnapsackInputBin[] b) {
		items = a.clone();
		bins = b.clone();
	}

//	public Integer[] getListItemCategory() {
//		Integer[] tmp = new Integer[getTheNumberOfItems()];
//		for (int i = 0; i < getTheNumberOfItems() ; i++) {
//			tmp[i] = itemsTemporary[i].getT();
//		}
//		LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>( Arrays.asList(tmp));
//		listItemCategory = linkedHashSet.toArray(new Integer[] {});
//		return listItemCategory;
//	}
//	
//	public Integer[] getListItemClass() {
//		Integer[] tmp = new Integer[getTheNumberOfItems()];
//		for (int i = 0; i < getTheNumberOfItems() ; i++) {
//			tmp[i] = itemsTemporary[i].getR();
//		}
//		LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>( Arrays.asList(tmp));
//		listItemClass = linkedHashSet.toArray(new Integer[] {});
//		return listItemClass;
//	}
	public int getMaxT() {
		return maxT;
	}
	public void setMaxT(int maxT) {
		this.maxT = maxT;
	}
	public int getMaxR() {
		return maxR;
	}
	public void setMaxR(int maxR) {
		this.maxR = maxR;
	}
	public MinMaxTypeMultiKnapsackSolution() {
		int[][] D = new int[][] { {0,1},{1,2} ,{0,2} , {1,2}, {0,1} , {0,2} ,{1,2}, {1,2}, {0,2}, {0,2} ,{1,2}, {0,1}, {0,2} , {1,2} , {0,1} , {0,2}                };
		itemsTemporary = new ItemTemporary[16];
		for(int i = 0 ; i < 16 ; i++) {
			itemsTemporary[i] = new ItemTemporary();
		}
		itemsTemporary[0].setW(3);
		itemsTemporary[0].setP(1);
		itemsTemporary[0].setT(0);
		itemsTemporary[0].setR(1);
		itemsTemporary[0].setBinIndices(D[0]);
		
		itemsTemporary[1].setW(2);
		itemsTemporary[1].setP(0);
		itemsTemporary[1].setT(1);
		itemsTemporary[1].setR(0);
		itemsTemporary[1].setBinIndices(D[1]);
		
		itemsTemporary[2].setW(1);
		itemsTemporary[2].setP(0);
		itemsTemporary[2].setT(0);
		itemsTemporary[2].setR(1);
		itemsTemporary[2].setBinIndices(D[2]);
		
		itemsTemporary[3].setW(6);
		itemsTemporary[3].setP(1);
		itemsTemporary[3].setT(1);
		itemsTemporary[3].setR(0);
		itemsTemporary[3].setBinIndices(D[3]);
		
		itemsTemporary[4].setW(4);
		itemsTemporary[4].setP(1);
		itemsTemporary[4].setT(1);
		itemsTemporary[4].setR(1);
		itemsTemporary[4].setBinIndices(D[4]);
		
		itemsTemporary[5].setW(7);
		itemsTemporary[5].setP(0);
		itemsTemporary[5].setT(2);
		itemsTemporary[5].setR(2);
		itemsTemporary[5].setBinIndices(D[5]);
		
		itemsTemporary[6].setW(2);
		itemsTemporary[6].setP(1);
		itemsTemporary[6].setT(0);
		itemsTemporary[6].setR(2);
		itemsTemporary[6].setBinIndices(D[6]);
		
		itemsTemporary[7].setW(1);
		itemsTemporary[7].setP(0);
		itemsTemporary[7].setT(0);
		itemsTemporary[7].setR(1);
		itemsTemporary[7].setBinIndices(D[7]);
		
		itemsTemporary[8].setW(3);
		itemsTemporary[8].setP(0);
		itemsTemporary[8].setT(1);
		itemsTemporary[8].setR(1);
		itemsTemporary[8].setBinIndices(D[8]);

		itemsTemporary[9].setW(3);
		itemsTemporary[9].setP(0);
		itemsTemporary[9].setT(2);
		itemsTemporary[9].setR(2);
		itemsTemporary[9].setBinIndices(D[9]);
		
		itemsTemporary[10].setW(2);
		itemsTemporary[10].setP(1);
		itemsTemporary[10].setT(0);
		itemsTemporary[10].setR(2);
		itemsTemporary[10].setBinIndices(D[10]);
		
		itemsTemporary[11].setW(5);
		itemsTemporary[11].setP(1);
		itemsTemporary[11].setT(1);
		itemsTemporary[11].setR(1);
		itemsTemporary[11].setBinIndices(D[11]);
		
		itemsTemporary[12].setW(4);
		itemsTemporary[12].setP(1);
		itemsTemporary[12].setT(1);
		itemsTemporary[12].setR(1);
		itemsTemporary[12].setBinIndices(D[12]);
		
		itemsTemporary[13].setW(1);
		itemsTemporary[13].setP(0);
		itemsTemporary[13].setT(0);
		itemsTemporary[13].setR(0);
		itemsTemporary[13].setBinIndices(D[13]);
		
		itemsTemporary[14].setW(3);
		itemsTemporary[14].setP(0);
		itemsTemporary[14].setT(0);
		itemsTemporary[14].setR(0);
		itemsTemporary[14].setBinIndices(D[14]);
		
		itemsTemporary[15].setW(2);
		itemsTemporary[15].setP(2);
		itemsTemporary[15].setT(2);
		itemsTemporary[15].setR(2);
		itemsTemporary[15].setBinIndices(D[15]);
		
		binsTemporary = new BinsTemporary[3];
		for(int i = 0 ; i < 3; i++) {
			binsTemporary[i] = new BinsTemporary();
		}
		binsTemporary[0].setCapacity(22);
		binsTemporary[0].setP(5);
		binsTemporary[0].setT(2);
		binsTemporary[0].setR(1);
		
		binsTemporary[1].setCapacity(17);
		binsTemporary[1].setP(3);
		binsTemporary[1].setT(2);
		binsTemporary[1].setR(1);
		
		binsTemporary[2].setCapacity(18);
		binsTemporary[2].setP(6);
		binsTemporary[2].setT(2);
		binsTemporary[2].setR(1);
	}
	public int getTheNumberOfItems() {
		return theNumberOfItems;
	}

	public void setTheNumberOfItems(int theNumberOfItems) {
		this.theNumberOfItems = theNumberOfItems;
	}

	public int getTheNumberOFBins() {
		return theNumberOFBins;
	}

	public void setTheNumberOFBins(int theNumberOFBins) {
		this.theNumberOFBins = theNumberOFBins;
	}

	public void stateModel() {
		mgr = new LocalSearchManager();
//		itemForBin = new VarIntLS[getTheNumberOfItems()][getTheNumberOFBins()];
//		binForItem = new VarIntLS[getMaxT()+1][getTheNumberOFBins()];
//		binForItemsOfClass = new VarIntLS[getMaxR()+1][getTheNumberOFBins()];
//		int[] tmp;
//		System.out.println(getMaxR());
//		System.out.println(getMaxT());
//		for (int i = 0; i <= getMaxT(); i++) {
//			for (int j = 0; j < getTheNumberOFBins(); j++) {
//				binForItem[i][j] = new VarIntLS(mgr, 0, 1);
//			}
//		}
//		for (int i = 0; i <= getMaxR(); i++) {
//			for (int j = 0; j < getTheNumberOFBins(); j++) {
//				binForItemsOfClass[i][j] = new VarIntLS(mgr, 0, 1);
//			}
//		}	
//		for (int i = 0; i < getTheNumberOfItems(); i++) {
//			tmp = itemsTemporary[i].getBinIndices();
//			for (int j = 0; j < getTheNumberOFBins(); j++) {
//				itemForBin[i][j] = new VarIntLS(mgr, 0, 1);	
//			}
//			for (int k = 0; k < tmp.length; k++) {
//				itemForBin[i][tmp[k]] = new VarIntLS(mgr, 0, 1);
//				binForItem[itemsTemporary[i].getT()][tmp[k]] = new VarIntLS(mgr, 0, 1);
//				binForItemsOfClass[itemsTemporary[i].getR()][tmp[k]] = new VarIntLS(mgr, 0, 1);
//			}
//		}
//		S = new ConstraintSystem(mgr);
//		
//		IFunction[] f1 = new IFunction[getTheNumberOfItems()];
//		IFunction[] ff1 = new IFunction[getTheNumberOFBins()];
//		IFunction[] f2 = new IFunction[getTheNumberOfItems()];
//		IFunction[] ff2 = new IFunction[getTheNumberOFBins()];
//		IFunction[] f3 = new IFunction[getTheNumberOfItems()];
//		IFunction[] ff3 = new IFunction[getTheNumberOFBins()];
//		IFunction[] f4= new IFunction[getTheNumberOfItems()];
//		IFunction[] ff4 = new IFunction[getTheNumberOFBins()];
//		for (int  i = 0; i < getTheNumberOFBins() ; i++) {
//			for (int j = 0; j < getTheNumberOfItems(); j++) {
//				   f1[j] = new FuncMult(itemForBin[j][i],itemsTemporary[j].getW()); 	   
//				   f2[j] = new FuncMult(itemForBin[j][i], itemsTemporary[j].getP());   
//				   f3[j]= new FuncPlus(binForItem[itemsTemporary[j].getT()][i], 0);		   
//				   f4[j] = new FuncPlus(binForItemsOfClass[itemsTemporary[j].getR()][i], 0); 
//				   S.post(new LessOrEqual(itemForBin[j][i],binForItem[itemsTemporary[j].getT()][i]));
//				   S.post(new LessOrEqual(itemForBin[j][i],binForItemsOfClass[itemsTemporary[j].getR()][i]));
//			}
//			SumFun sf = new SumFun(f1);
//			ff1[i] = sf;
//			S.post(new LessOrEqual(ff1[i],binsTemporary[i].getCapacity()));
//			S.post(new LessOrEqual(binsTemporary[i].getCapacity(),ff1[i]));
//			sf = new SumFun(f2);
//			ff2[i] = sf;
//			S.post(new LessOrEqual(ff2[i], binsTemporary[i].getP()));
//			sf = new SumFun(f3);
//			ff3[i] = sf;
//			S.post(new LessOrEqual(ff3[i], binsTemporary[i].getT()));
//			sf = new SumFun(f3);
//			ff4[i] = sf;
//			S.post(new LessOrEqual(ff4[i], binsTemporary[i].getR()));
//		}	
		mgr.close();
	}

	public void search() {
//		System.out.println("Init violations = " + S.violations());
//		int it = 0;
//		MinMaxSelector mms = new MinMaxSelector(S);
//		while(it < 10000000 && S.violations() > 0 ) {		
//			VarIntLS sel_x = mms.selectMostViolatingVariable();
//			int sel_v = mms.selectMostPromissingValue(sel_x);
//			sel_x.setValuePropagate(sel_v);
//			System.out.println("Step " + it + ", violation = " + S.violations());
//			it++;
//		}
		TabuSearch ts = new TabuSearch();
		ts.search(S, 30, 10, 1000000, 100);
	}
	
	public void solve() {
		stateModel();
		search();
		print();
	}
	
	public void print() {
		for(int i = 0; i < getTheNumberOFBins(); i++) {
			System.out.println("Bin i = " +i);
			for (int j = 0; j < getTheNumberOfItems(); j++) {
				if(itemForBin[j][i].getValue() == 1) {
					System.out.print("Item j = " +j);
				}
			}
		System.out.println();
		}
	}
	public static void main(String[] args) {
		
//		// TODO Auto-generated method stub
//		String fn = "./src/MinMaxTypeMultiKnapsackInput.json";
//		MinMaxTypeMultiKnapsackInput a = new MinMaxTypeMultiKnapsackInput();
//		MinMaxTypeMultiKnapsackInput b;
//		b = a.loadFromFile(fn);
//	
//		items = new MinMaxTypeMultiKnapsackInputItem[b.getItems().length];
//		bins = new MinMaxTypeMultiKnapsackInputBin[b.getBins().length];
//		itemsTemporary = new ItemTemporary[b.getItems().length];
//		binsTemporary = new BinsTemporary[b.getBins().length];
//		for (int i = 0; i < b.getItems().length; i++) {
//			itemsTemporary[i] = new ItemTemporary();
//		}
//		for (int i = 0; i < b.getBins().length; i++) {
//			binsTemporary[i] = new BinsTemporary();
//		}
//		items = b.getItems();
//		bins = b.getBins();
//
//		MinMaxTypeMultiKnapsackSolution c = new MinMaxTypeMultiKnapsackSolution(items,bins);
//			c.setTheNumberOFBins(b.getBins().length);
//			c.setTheNumberOfItems(b.getItems().length);
//
//		System.out.println("The number of items "+c.getTheNumberOfItems());
//		System.out.println("The number of bins " +c.getTheNumberOFBins());
//		for (int i = 0; i < b.getItems().length; i++) {
//			itemsTemporary[i].setP((int)(items[i].getP()*10e6));
//			itemsTemporary[i].setW((int)(items[i].getW()*10e6));
//			itemsTemporary[i].setT(items[i].getT());
//			itemsTemporary[i].setR(items[i].getR());
//			itemsTemporary[i].setBinIndices(items[i].getBinIndices());
//		}
//		for (int i = 0; i < b.getBins().length; i++) {
//			binsTemporary[i].setCapacity((int)(bins[i].getCapacity()*10e6));
//			binsTemporary[i].setMinLoad((int)(bins[i].getMinLoad()*10e6));
//			binsTemporary[i].setP((int)(bins[i].getP()*10000));
//			binsTemporary[i].setR(bins[i].getR());
//			binsTemporary[i].setT(bins[i].getT());
//		}
//		ArrayList<Integer> tmp1 = new ArrayList<Integer>(); 
//		ArrayList<Integer> tmp2 = new ArrayList<Integer>(); 
//		for (int i = 0; i <16; i++) {
//			tmp1.add(itemsTemporary[i].getT());
//			tmp2.add(itemsTemporary[i].getR());
//		}
//		c.setMaxT(Collections.max(tmp1));
//		c.setMaxR(Collections.max(tmp2));
//		System.out.println(c.getMaxR());
//		System.out.println(c.getMaxT());
//		Collections.max(tmp1);
//		c.solve();
		MinMaxTypeMultiKnapsackSolution a = new MinMaxTypeMultiKnapsackSolution();
		a.setTheNumberOFBins(3);
		a.setTheNumberOfItems(16);
		ArrayList<Integer> tmp1 = new ArrayList<Integer>(); 
		ArrayList<Integer> tmp2 = new ArrayList<Integer>(); 
		for (int i = 0; i <16; i++) {
			tmp1.add(itemsTemporary[i].getT());
			tmp2.add(itemsTemporary[i].getR());
		}
		a.setMaxT(Collections.max(tmp1));
		a.setMaxR(Collections.max(tmp2));
		a.solve();
	}

}
