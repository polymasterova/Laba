package Function;

public class FunctionPoint {
    public double pointX;
    public double pointY;

    public FunctionPoint(double pointX, double pointY) {
        this.pointX = pointX;
        this.pointY = pointY;
    }

    public FunctionPoint(FunctionPoint point) {
        this.pointX= point.pointX;
        this.pointY = point.pointY;
    }

    public FunctionPoint() {
        this.pointX = 0;
        this.pointY = 0;
    }
}
