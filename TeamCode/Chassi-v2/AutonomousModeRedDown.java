// Copyright (c) 2017 FIRST. All rights reserved.


package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/** Configuration
 * Control Hub:
 * Motor Port 00: motorEsquerda
 * Motor Port 01: motorDireita
 * Motor Port 02: motorBraco1
 * Motor Port 03: motorBraco2
 * Servo Port 00: servoGarra
 * Servo Port 01: servoBraco
 */

@Autonomous(name="Main Autonomous Mode", group="Iterative OpMode")

public class AutonomousModeRedDown extends OpMode
{

    private ElapsedTime runtime = new ElapsedTime();

    private int ticksRotation = 520;
    private int diameter = 9;
    private int ticksRobotRotation = 2200;

    private Servo pixelsServo = null;

    private DcMotor leftMotor = null;
    private double leftMotorPower = 0.0;

    private DcMotor rightMotor = null;
    private double rightMotorPower = 0.0;

    private DcMotor intakeMotor = null;
    private double intakeMotorPower = 0.0;

    private DcMotor climbMotor = null;
    private double climbMotorPower = 0.0;

    @Override
    public void init() {
        initHardware();
    }

    @Override
    public void start() {
        
        // leva os dois pixel pra fita
        TwoMotorsRunToPosition(leftMotor, rightMotor, ConvertCentimeterToTick(50));
        pixelsServo.setPosition(1.0);
        pixelsServo.setPosition(0.5);

        // volta e estaciona no backstage
        TwoMotorsRunToPosition(leftMotor, rightMotor, ConvertCentimeterToTick(-50));
        TwoMotorsRotation(rightMotor, leftMotor, ConvertAngleToTick(90));
        TwoMotorsRunToPosition(leftMotor, rightMotor, ConvertCentimeterToTick(192));
    }

    @Override
    public void loop() {
    }

    public int ConvertCentimeterToTick(double destinyCentimeters) {
        return (int) (ticksRotation * destinyCentimeters) / (int) (diameter * 3.14);
    }

    public int ConvertAngleToTick(int destinyAngle) {
        return (int) (2200 * (destinyAngle/360.0));
    }

    // Complete Rotation = 2200 ticks
    public void TwoMotorsRotation(DcMotor motorOne, DcMotor motorTwo, int position) {
        motorOne.setTargetPosition(-position);
        motorTwo.setTargetPosition(position);

        motorOne.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorTwo.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorOne.setPower(0.15);
        motorTwo.setPower(0.15);

        while(motorOne.isBusy()) {

        }

        resetMotor(motorOne);
        resetMotor(motorTwo);
    }

    public void OneMotorRunToPosition(DcMotor motor, int position) {
        motor.setTargetPosition(position);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(1);
        while(motor.isBusy()) {

        }
        resetMotor(motor);
    }

    public void TwoMotorsRunToPosition(DcMotor motorOne, DcMotor motorTwo, int position) {
        motorOne.setTargetPosition(position);
        motorTwo.setTargetPosition(position);

        motorOne.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorTwo.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorOne.setPower(0.3);
        motorTwo.setPower(0.3);

        while(motorOne.isBusy()) {

        }

        resetMotor(motorOne);
        resetMotor(motorTwo);
    }

    public void resetMotor(DcMotor motor) {
        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void initHardware() {
        initLeftMotor();
        initRightMotor();
        initIntakeMotor();
        initClimbMotor();
        initPixelsServo();
    }

    public void initPixelsServo() {
        pixelsServo = hardwareMap.get(Servo.class, "servoPixel");
        pixelsServo.setDirection(Servo.Direction.FORWARD);
        pixelsServo.setPosition(0.5);
    }

    public void initIntakeMotor(){
        intakeMotor = hardwareMap.get(DcMotor.class, "motorIntake");
        intakeMotor.setDirection(DcMotor.Direction.REVERSE);
        intakeMotor.setPower(0.0);
    }

    public void initLeftMotor() {
        leftMotor = hardwareMap.get(DcMotor.class, "motorEsquerda");
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        leftMotor.setPower(0.0);
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // The encoder's current position is set as zero
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void initRightMotor() {
        rightMotor = hardwareMap.get(DcMotor.class, "motorDireita");
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setPower(0.0);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // The encoder's current position is set as zero
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void initClimbMotor() {
        climbMotor = hardwareMap.get(DcMotor.class, "motorClimb");
        climbMotor.setDirection(DcMotor.Direction.FORWARD);
        climbMotor.setPower(0.0);
        climbMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

}
