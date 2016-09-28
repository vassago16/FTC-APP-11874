package com.caps.bvs.TeamCode.CoboltCaps.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cControllerPortDevice;
import com.qualcomm.robotcore.hardware.usb.RobotArmingStateNotifier;
import com.qualcomm.robotcore.util.RobotLog;


@Autonomous(name = "Test MC Flavors", group = "Tests")
@Disabled
public  class TestMotorControllers extends LinearOpMode {
    // We don't require all of these motors to be attached; we'll deal with what we find
    DcMotor legacyMotor;
    DcMotor v15Motor;
    DcMotor v2Motor;

    @Override
    public void runOpMode() throws InterruptedException {
        legacyMotor = findMotor("legacy motor");
        v15Motor = findMotor("v1.5 motor");
        v2Motor = findMotor("v2 motor");

        setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setModes(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        // Make the encoders be something other than zero
        reportMotors();
        setPowers(0.5);
        Thread.sleep(3000);
        setPowers(0);
        reportMotors();
        Thread.sleep(3000);
        reportMotors();

        setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Sit there and watch the motors
        while (opModeIsActive()) {
            reportMotors();
            idle();
        }
    }

   public  DcMotor findMotor(String motorName) {
        try {
            DcMotor motor = hardwareMap.dcMotor.get(motorName);

            // Find which device is the USB device
            RobotArmingStateNotifier usbDevice = null;
            if (motor.getController() instanceof RobotArmingStateNotifier) {
                usbDevice = (RobotArmingStateNotifier) motor.getController();
            } else if (motor.getController() instanceof I2cControllerPortDevice) {
                I2cControllerPortDevice i2cControllerPortDevice = (I2cControllerPortDevice) motor.getController();
                if (i2cControllerPortDevice.getI2cController() instanceof RobotArmingStateNotifier) {
                    usbDevice = (RobotArmingStateNotifier) i2cControllerPortDevice.getI2cController();
                }
            }

            // Weed out controllers that aren't actually physically there (and so in pretend state)
            if (usbDevice != null) {
                if (usbDevice.getArmingState() != RobotArmingStateNotifier.ARMINGSTATE.ARMED) {
                    return null;
                }
            }

            return motor;
        } catch (Throwable ignored) {
        }
        return null;
    }

   public void setModes(DcMotor.RunMode mode) {
        if (legacyMotor != null) legacyMotor.setMode(mode);
        if (v15Motor != null) v15Motor.setMode(mode);
        if (v2Motor != null) v2Motor.setMode(mode);
    }

   public  void setPowers(double power) {
        if (legacyMotor != null) legacyMotor.setPower(power);
        if (v15Motor != null) v15Motor.setPower(power);
        if (v2Motor != null) v2Motor.setPower(power);
    }

  public   void reportMotors() {
        telemetry.addData("Motor Report", "");
        reportMotor("legacy motor: ", legacyMotor);
        reportMotor("v1.5 motor: ", v15Motor);
        reportMotor("v2 motor: ", v2Motor);
        telemetry.update();
    }

    public void reportMotor(String caption, DcMotor motor) {
        if (motor != null) {
            int position = motor.getCurrentPosition();
            DcMotor.RunMode mode = motor.getMode();

            telemetry.addLine(caption)
                    .addData("pos", "%d", position)
                    .addData("mode", "%s", mode.toString());

            RobotLog.i("%s pos=%d mode=%s", caption, position, mode.toString());
        }
    }

}

