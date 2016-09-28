package units;


public class EncoderUnit extends Unit {

    public static final int ROTATION_TETRIX = 1440;
    public static final int ROTATION_ANDYMARK = 1220;
    public static final int ROTATION_LEGO = 360;


    public EncoderUnit(int value) {
        setValue(value);
    }


    public EncoderUnit(float cm, float circumfrence, int rotation) {
        float rotationPerCm = rotation / circumfrence;
        setValue((int) (rotationPerCm * cm));
    }

}
