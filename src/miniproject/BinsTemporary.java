package miniproject;

public class BinsTemporary {
	private int capacity;
	private int minLoad;
	private int p;
	private int t;
	private int r;

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getMinLoad() {
		return minLoad;
	}

	public void setMinLoad(int minLoad) {
		this.minLoad = minLoad;
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

	public BinsTemporary(int capacity, int minLoad,
			int p, int t, int r) {
		super();
		this.capacity = capacity;
		this.minLoad = minLoad;
		this.p = p;
		this.t = t;
		this.r = r;
	}

	public BinsTemporary() {
		super();
		// TODO Auto-generated constructor stub
	}

}
