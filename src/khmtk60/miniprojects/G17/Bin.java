package khmtk60.miniprojects.G17;

public class Bin {
	private double capacity;
	private double minLoad;
	private double p;
	private int t;
	private int r;
	private int idx;
	
	private double _w;
	private double _p;
	private SetCount _t;
	private int _r;

	public int violations() {
		int vio = 0;
		if (_w == 0)
			vio += 1;
		if(_w != 0 && _w < minLoad)
			vio += 2;
		if (_w > capacity)
			vio += 3;
		if (_p > p)
			vio += 3;
		if (_t.size() > t)
			vio += 2;

		return vio;
	}


	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public double getMinLoad() {
		return minLoad;
	}

	public void setMinLoad(double minLoad) {
		this.minLoad = minLoad;
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
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

	public Bin(double capacity, double minLoad,
			double p, int t, int r, int idx) {
		super();
		this.capacity = capacity;
		this.minLoad = minLoad;
		this.p = p;
		this.t = t;
		this.r = r;

		this._w = 0;
		this._p = 0;
		this._t = new SetCount();
		this.set_r(0);
	}


	public int getIdx() {
		return idx;
	}


	public void setIdx(int idx) {
		this.idx = idx;
	}


	public int get_r() {
		return _r;
	}


	public void set_r(int _r) {
		this._r = _r;
	}
}

