package units;




public class TimeUnit extends Unit {


    public TimeUnit(long msecs) {
        setValue(msecs);
    }


    public TimeUnit(float seconds) {
        setValue((long) (seconds * 1000));
    }

}
