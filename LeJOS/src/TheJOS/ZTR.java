package TheJOS;

public class ZTR {
	double wheelSize = 0;
	double trackWidth = 0;
	
	ZTR(double wheelSize, double trackWidth){
		wheelSize = wheelSize;
		trackWidth = trackWidth;
	}
	
	int vL(double r) {
		return (int) (r-0.5*trackWidth);
	}
	
	int vR(double r) {
		return (int) (r+0.5*trackWidth);
	}

}
