package hardware.nullware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorImpl;


/**
 * A null object form of {@link DcMotor}
 * This prevents having to null-check in case your motor is unplugged
 */
public class NullDcMotor extends DcMotorImpl implements NullHardware {

    public NullDcMotor() {
        super(null, 0);
    }


    @Override
    public void close() {

    }

    @Override
    public String getConnectionInfo() {
        return "Null Hardware";
    }

    @Override
    public Manufacturer getManufacturer() {
        return null;
    }

    @Override
    public String getDeviceName() {
        return "Null Hardware";
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {

    }

    @Override
    public void setMaxSpeed(int encoderTicksPerSecond) {

    }

    @Override
    public int getMaxSpeed() {
        return 0;
    }

    @Override
    public DcMotorController getController() {
        return null;
    }


    @Override
    public void setDirection(Direction direction) {

    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public int getPortNumber() {
        return 0;
    }

    @Override
    public void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior) {

    }

    @Override
    public ZeroPowerBehavior getZeroPowerBehavior() {
        return null;
    }

    @Override
    public void setPower(double power) {

    }

    @Override
    public double getPower() {
        return 0;
    }

    @Override
    public void setPowerFloat() {

    }

    @Override
    public boolean getPowerFloat() {
        return false;
    }

    @Override
    public void setTargetPosition(int position) {

    }

    @Override
    public int getTargetPosition() {
        return 0;
    }

    @Override
    public boolean isBusy() {
        return false;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void setMode(RunMode mode) {

    }

    @Override
    public RunMode getMode() {
        return null;
    }




}
