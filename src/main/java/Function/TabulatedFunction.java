package Function;

public class TabulatedFunction {
    private FunctionPoint[] valuesArray;
    private int count;

    public TabulatedFunction(double leftX, double rightX, double[] values) {
        this.count = values.length;
        FunctionPoint[] valuesArray = new FunctionPoint[count];
        double buff = leftX;
        double step = (rightX - leftX) / (count - 1);
        if (leftX != rightX) {
            for (int i = 0; i < count; i++) {
                valuesArray[i] = new FunctionPoint(buff, values[i]);
                buff += step;
            }
        }
    }

    public TabulatedFunction(double leftX, double rightX, int pointsCount) {
        this.count = pointsCount;
        this.valuesArray = new FunctionPoint[count];
        double buff = leftX;
        double step = (rightX - leftX) / (count - 1);
        if (leftX != rightX) {
            for (int i = 0; i < count; i++) {
                valuesArray[i] =new FunctionPoint(buff, 0) ;
                buff += step;
            }
        }
    }

    double getLeftDomainBorder() {
        return valuesArray[0].pointX;
    }

    double getRightDomainBorder() {
        return valuesArray[count - 1].pointX;
    }

    double getFunctionValue(double x) {
        if (x > valuesArray[count - 1].pointX || x < valuesArray[0].pointX) {
            return Double.NaN;
        } else {
            if (x < getLeftDomainBorder()) {
                return extrapolateLeft(x);
            } else if (x > getRightDomainBorder()) {
                return extrapolateRight(x);
            } else if (indexOfX(x) != -1) {
                return getPointY(indexOfX(x));
            } else {
                return interpolate(x, floorIndexOfX(x));
            }
        }
    }

    protected int floorIndexOfX(double x) {
        int i;
        if (x < getLeftDomainBorder()) {
            return 0;
        }
        for (i = 0; i < count; i++) {
            if (valuesArray[i].pointX > x) {
                return i - 1;
            }
        }
        return count;
    }

    protected double extrapolateLeft(double x) {
        if (count == 1) {
            return x;
        }
        return interpolate(x, valuesArray[0].pointX, valuesArray[1].pointX, valuesArray[0].pointY, valuesArray[1].pointY);
    }


    protected double extrapolateRight(double x) {
        if (count == 1) {
            return x;
        }
        return interpolate(x, valuesArray[count - 2].pointX, valuesArray[count - 1].pointX, valuesArray[count - 2].pointY, valuesArray[count - 1].pointY);
    }


    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return leftY + (rightY - leftY) * (x - leftX) / (rightX - leftX);
    }

    protected double interpolate(double x, int floorIndex) {
        if (count == 1) {
            return x;
        }
        return interpolate(x, valuesArray[floorIndex].pointX, valuesArray[floorIndex + 1].pointX, valuesArray[floorIndex].pointY, valuesArray[floorIndex + 1].pointY);
    }


    public int getCount() {
        return count;
    }


    public double getPointX(int index) {
        return valuesArray[index].pointX;
    }


    public double getPointY(int index) {
        return valuesArray[index].pointY;
    }


    public void setY(int index, double value) {
        valuesArray[index].pointY = value;
    }


    public int indexOfX(double x) {
        int i;
        for (i = 0; i < count; i++) {
            if (valuesArray[i].pointY == x) {
                return i;
            }
        }
        return -1;
    }


    public int indexOfY(double y) {
        int i;
        for (i = 0; i < count; i++) {
            if (valuesArray[i].pointY == y) {
                return i;
            }
        }
        return -1;
    }


    public void addPoint(FunctionPoint point) {
        if (indexOfX(point.pointX) != -1) {
            setY(indexOfX(point.pointX), point.pointY);
        } else {
            int index = floorIndexOfX(point.pointX);
            FunctionPoint[] tmp = new FunctionPoint[count + 1];
            if (index == 0) {
                tmp[0] = point;
                System.arraycopy(valuesArray, 0, tmp, 1, count);
                count++;
            } else if (index == count) {
                System.arraycopy(valuesArray, 0, tmp, 0, count);
                tmp[count] = point;
                count++;
            } else {
                System.arraycopy(valuesArray, 0, tmp, 0, index);
                tmp[index] = point;
                System.arraycopy(valuesArray, index, tmp, index + 1, (count - index));
                count++;
            }
            this.valuesArray = tmp;
        }
    }

    public void deletePoint(int index) {
        FunctionPoint[] tmp = new FunctionPoint[count - 1];
        if (index == 0) {
            System.arraycopy(valuesArray, 1, tmp, 0, count - 1);
        } else if (index == (count - 1)) {
            System.arraycopy(valuesArray, 0, tmp, 0, count - 1);
        } else {
            System.arraycopy(valuesArray, 0, tmp, 0, index);
            System.arraycopy(valuesArray, index + 1, tmp, index, (count - index - 1));
        }
        count--;
        this.valuesArray = tmp;
    }
}

