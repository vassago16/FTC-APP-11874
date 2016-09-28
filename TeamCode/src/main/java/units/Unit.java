package units;



public abstract class Unit {

    long value;

    /**
     * Gets the value of this unit
     *
     * @return The value of the unit
     */
    public long getValue() {
        return value;
    }

    /**
     * Sets the value of this unit
     *
     * @param value The new value
     */
    public void setValue(long value) {
        this.value = value;
    }

}
