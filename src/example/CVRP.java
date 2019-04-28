package example;

import localsearch.model.LocalSearchManager;

public class CVRP {
	int N; //Kho hàng trung tâm 0 và N điểm khách hàng 1,2,..,N
	int[] d; //di: lượng hàng yêu cầu của khách hàng i 
	int K; //Số xe 
	int Q; //Khả năg vận chuyển của xe 
	int[][] D; //Dij: khoang cach tu diem i den j. i = [0,N], j = [0,N]
	//Lập lộ trình vận tải cho K xe để vận chuyển hàng hoá từ kho trung tâm đến khách hàng sao cho tổng quãng đường là nhỏ nhất
	
	LocalSearchManager mgr;
}
