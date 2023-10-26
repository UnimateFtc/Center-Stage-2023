//Copyright (c) 2017 FIRST. All rights reserved.

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="Autonomous Left and Right Motors", group="Autonomous Mode")
//@Disabled
public class LeftRightMotors extends OpMode
{
    // Declare OpMode variables
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftMotor = null;
    private double leftMotorPower = 1.0;
    private double leftMotorInitPosition = 0;

    private DcMotor rightMotor = null;
    private double rightMotorPower = 1.0;
    private double rightMotorInitPosition = 0;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        initHardware();
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        runTeleOpControls();
        showTelemetry();
    }

    // Run all mechanisms
    public void runTeleOpControls() {

        // Motors go to specif position
        runLeftMotorToPosition(500);
        runRightMotorToPosition(500);

    }

    public void DualMotor(int rotationsLeft, int rotationsRight){

        runLeftMotorToPosition(rotationsLeft);
        runRightMotorToPosition(rotationsRight);
    }

    public void DualMotor(int rotations){

        runLeftMotorToPosition(rotations);
        runRightMotorToPosition(rotations);
    }

    // Set the left motor to a specific position
    public void runLeftMotorToPosition(int position) {
        leftMotor.setTargetPosition(position);
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotor.setPower(0.2);
    }

    // Set the right motor to a specific position
    public void runRightMotorToPosition(int position) {
        rightMotor.setTargetPosition(position);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setPower(0.2);
    }

    // Inits all hardware members
    public void initHardware() {
        initLeftMotor();
        initRightMotor();
    }

    // Inits the left motor
    public void initLeftMotor() {
        leftMotor = hardwareMap.get(DcMotor.class, "MotorRod1");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        leftMotor.setPower(0);
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // The encoder's current position is set as zero
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // Inits the right motor
    public void initRightMotor() {
        rightMotor = hardwareMap.get(DcMotor.class, "MotorRod2");
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setPower(0);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // The encoder's current position is set as zero
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void showTelemetry() {

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "Power (%.2f), Encoder (%.2f)", leftMotor.getPower(), leftMotor.getCurrentPosition());
        telemetry.addData("Motors", "Power (%.2f), Encoder (%.2f)", rightMotor.getPower(), rightMotor.getCurrentPosition());
        telemetry.update();

    }

}
