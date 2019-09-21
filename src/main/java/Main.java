import Function.FunctionPoint;
import Function.TabulatedFunction;

public class Main {
    public static void main(String[] args) {
        TabulatedFunction arrayPoint = new TabulatedFunction(0.,10.,10) ;
        System.out.println(arrayPoint.getPointX(2));
        System.out.println(arrayPoint.getPointY(5));

    }
}
