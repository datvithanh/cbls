package miniproject;

import java.util.Arrays;

public class ItemTemporary {
	private int w;
	private int p;
	private int t;
	private int r;

	private int[] binIndices;

	public ItemTemporary(int w, int p, int t, int r,
			int[] binIndices) {
		super();
		this.w = w;
		this.p = p;
		this.t = t;
		this.r = r;
		this.binIndices = binIndices;
	}
	
	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int[] getBinIndices() {
		return binIndices;
	}

	public void setBinIndices(int[] binIndices) {
		this.binIndices = binIndices;
	}

	public ItemTemporary() {
//		super();
		// TODO Auto-generated constructor stub
	}

	public void display() {
		System.out.println("Item: ---------------------------------------");
		System.out.println("w = " + getW() + "\np = " + getP() + "\nt = " + getT() + "\nr = " + getR());
		System.out.println(Arrays.toString(getBinIndices()));
	}
	
	public static void main(String[] args) {
		
	}

}
