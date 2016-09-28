package com.caps.bvs.TeamCode.CoboltCaps.OpModes;

import android.util.Log;

import hardware.HardwareManager;
import hardware.MotorRunner;
import hardware.Power;
import units.EncoderUnit;
import units.TimeUnit;
import units.Values;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class RunEncoders extends LinearOpMode {

    DcMotor motorRight;
    DcMotor motorLeft;

    @Override
    public void runOpMode() throws InterruptedException {
        initMotors();
        waitForStart();
        Log.w("Auton", "Starting Auton");
        //Autonomous starts here

        //Test encoders
        MotorRunner.run(this, new DcMotor[]{motorLeft, motorRight}, Power.NORMAL_SPEED,
                new EncoderUnit((int) (EncoderUnit.ROTATION_ANDYMARK)));
    }

    public void initMotors() {
        HardwareManager manager = new HardwareManager(hardwareMap);

        motorRight = manager.getMotor(Values.RIGHT_MOTOR);
        motorRight.setDirection(DcMotor.Direction.REVERSE);
        motorLeft = manager.getMotor(Values.LEFT_MOTOR);
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    public void stopMotors() {
        motorLeft.setPower(Power.FULL_STOP);
        motorRight.setPower(Power.FULL_STOP);
    }
}

