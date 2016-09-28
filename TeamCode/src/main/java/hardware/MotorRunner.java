package hardware;

import java.util.ArrayList;
import units.CycleTimer;
import units.EncoderUnit;
import units.TimeUnit;
import units.Unit;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

import java.util.ArrayList;
import java.util.Iterator;
import units.TimeUnit;

import units.EncoderUnit;
import units.Unit;

/**
 * Runs motors with {@link Unit}
 */
public class MotorRunner {

    final static String TAG = "Motor Runner";

    private static ArrayList<RunEvent> events = new ArrayList<RunEvent>();


    public static void setMotorPowers(DcMotor[] motors, double power) {
        for (DcMotor motor : motors) {
            motor.setPower(power);
            Log.w(TAG, "Set motor power:" + power);
        }
    }

    /**
     * Runs a {@link DcMotor} with an {@link Unit}
     * This function will block, only use in a {@link LinearOpMode}
     *
     * @param mode Your OpMode
     * @param motor The motor to run
     * @param power The power to run at
     * @param unit  The unit to run
     */
    public static void run(LinearOpMode mode, DcMotor motor, double power, Unit unit) throws InterruptedException {
        run(mode, new DcMotor[]{motor}, power, unit);
    }

    /**
     * Runs an array of {@link DcMotor} with an {@link Unit}
     * The the first element in the array must be a motor with an encoder
     * This function will block, only use in a {@link LinearOpMode}
     *
     * @param mode Your OpMode
     * @param motors The motors to run
     * @param power  The power to run at
     * @param unit   The unit to run
     */
    public static void run(LinearOpMode mode, DcMotor[] motors, double power, Unit unit) throws InterruptedException {
        if (motors[0] != null) {
            if (unit instanceof TimeUnit) {
                setMotorPowers(motors, power);
                Thread.sleep(unit.getValue());
                setMotorPowers(motors, 0);
            } else if (unit instanceof EncoderUnit) {
                mode.telemetry.addData("Log", "Encoder Unit:" + unit.getValue());
                motors[0].setMode(DcMotor.RunMode.RESET_ENCODERS);
                mode.waitOneFullHardwareCycle();
                motors[0].setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
                mode.waitOneFullHardwareCycle();
                setMotorPowers(motors, power);
                if (unit.getValue() > 0) {
                    while (motors[0].getCurrentPosition() < unit.getValue()) {
                        mode.waitOneFullHardwareCycle();
                        mode.telemetry.addData("Encoder Value", "Encoder Position:" + motors[0].getCurrentPosition());
                    }
                } else if (unit.getValue() < 0) {
                    while (motors[0].getCurrentPosition() > -unit.getValue()) {
                        mode.waitOneFullHardwareCycle();
                        mode.telemetry.addData("Encoder Value", "Encoder Position:" + motors[0].getCurrentPosition());
                    }
                }
                mode.telemetry.addData("Log", "Done Waiting");
                setMotorPowers(motors, 0);
                motors[0].setMode(DcMotor.RunMode.RESET_ENCODERS);
                mode.waitOneFullHardwareCycle();
                motors[0].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
                mode.waitOneFullHardwareCycle();
            }
        }
    }


    public static void runEvent(DcMotor motor, double power, Unit unit) {
        if (unit instanceof EncoderUnit)
        {
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setTargetPosition((int) unit.getValue());
        }
        motor.setPower(power);
        events.add(new RunEvent(motor, unit));
    }


    public static void update() {
        Iterator<RunEvent> iterator = events.iterator();
        while (iterator.hasNext()) {
            RunEvent event = iterator.next();
            event.update();
            if (event.isDone())
                iterator.remove();
        }
    }

    /**
     * Checks if a {@link DcMotor} is still in use from {@link #runEvent(DcMotor, double, Unit)}
     * If this returns false, it is unsafe to use the motor
     *
     * @param motor The motor to test
     * @return Whether the motor is done or not.
     */
    public static boolean doneWith(DcMotor motor) {
        for (RunEvent event : events) {
            if (event.getMotor() == motor)
                return false;
        }
        return true;
    }

}
