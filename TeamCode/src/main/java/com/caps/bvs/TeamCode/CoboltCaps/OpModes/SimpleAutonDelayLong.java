package com.caps.bvs.TeamCode.CoboltCaps.OpModes;

import android.util.Log;

import hardware.HardwareManager;
import hardware.MotorRunner;
import hardware.Power;
import units.TimeUnit;
import units.Values;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by tucker on 1/30/16.
 */
public class SimpleAutonDelayLong extends LinearOpMode {

    DcMotor motorRight;
    DcMotor motorLeft;

    DcMotor tape1;

    Servo dump;

    @Override
    public void runOpMode() throws InterruptedException {
        initMotors();
        waitForStart();
        Log.w("Auton", "Starting Auton");
        //Autonomous starts here

        //Wait for motors to calibrate
        Thread.sleep(10000);

        //Drive to mountain, backwards
        MotorRunner.run(this, new DcMotor[]{motorLeft, motorRight}, -Power.FULL_SPEED,
                new TimeUnit(Values.DRIVE_SIMPLE_LONG));
    }

    public void initMotors() {
        HardwareManager manager = new HardwareManager(hardwareMap);

        motorRight = manager.getMotor(Values.RIGHT_MOTOR);
        motorLeft = manager.getMotor(Values.LEFT_MOTOR);

        tape1 = manager.getMotor(Values.TAPE_1);

        dump = manager.getServo(Values.DUMP);
    }

    public void stopMotors() {
        motorLeft.setPower(Power.FULL_STOP);
        motorRight.setPower(Power.FULL_STOP);
    }

}
