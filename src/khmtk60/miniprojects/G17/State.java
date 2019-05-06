package khmtk60.miniprojects.G17;

import java.util.ArrayList;
import java.util.Random;

import localsearch.model.ConstraintSystem;
import localsearch.model.VarIntLS;

public class State {
	Bin[] bins;
	Item[] items;

	public void initialize(String filepath) {
		MinMaxTypeMultiKnapsackInput a = new MinMaxTypeMultiKnapsackInput();
		MinMaxTypeMultiKnapsackInput input = a.loadFromFile(filepath);
		MinMaxTypeMultiKnapsackInputItem[] its = input.getItems();
		MinMaxTypeMultiKnapsackInputBin[] bns = input.getBins();

		items = new Item[its.length];
		bins = new Bin[bns.length];

		int violations = 0;

		for(int i = 0; i < its.length; ++i) {
			items[i] = new Item(its[i].getW(), its[i].getP(), its[i].getT(), its[i].getR(), its[i].getBinIndices(), i);
			violations += items[i].violations();
		}
		for(int j = 0; j < bns.length; ++j) {
			bins[j] = new Bin(bns[j].getCapacity(), bns[j].getMinLoad(), bns[j].getP(), bns[j].getT(), bns[j].getR(),
					j);
			violations += items[j].violations();
		}

		System.out.println(violations);
	}

	public void hillClimbing(ConstraintSystem c, int maxIter) {
		VarIntLS[] y = c.getVariables();
		ArrayList<AssignMove> cand = new ArrayList<AssignMove>();
		Random R = new Random();
		int it = 0;
		System.out.println("variables: " + y.length);
		while (it < maxIter && c.violations() > 0) {
			// System.out.println(it);
			cand.clear();
			int minDelta = Integer.MAX_VALUE;
			for (int i = 0; i < y.length; i++) {
				for (int v = y[i].getMinValue(); v <= y[i].getMaxValue(); v++) {
					int d = c.getAssignDelta(y[i], v);
					if (d < minDelta) {
						cand.clear();
						cand.add(new AssignMove(i, v));
						minDelta = d;
					} else if (d == minDelta) {
						cand.add(new AssignMove(i, v));
					}
				}
			}
			int idx = R.nextInt(cand.size());
			AssignMove m = cand.get(idx);
			y[m.i].setValuePropagate(m.v);
			System.out.println("Step " + it + ", violations = " + c.violations());
			it++;
		}
	}

	public static void main(String[] args) {
		String filein = "src/khmtk60/miniprojects/multiknapsackminmaxtypeconstraints/MinMaxTypeMultiKnapsackInput-0.json";

		State st = new State();
		st.initialize(filein);
	}
}
