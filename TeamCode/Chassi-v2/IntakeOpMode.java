//Copyright (c) 2017 FIRST. All rights reserved.

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/** Configuration
 * Control Hub:
 * Motor Port 00: motorDireita
 * Motor Port 01: motorEsquerda
 * Motor Port 02: motorIntake
 * Motor Port 03: motorClimb
 * Servo Port 00: servoPixel
 */

@TeleOp(name="TeleOp Principal", group="Iterative OpMode")

public class IntakeOpMode extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private int ticksRotation = 4096;
    private int diameter = 10;

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
    public void loop() {

        runTeleOpControls();
        showTelemetry();

    }
    
    public void runTeleOpControls() {
        chassiControl();
        intakeControl();
        climbControl();
    }

    public void chassiControl() {
        double drive = gamepad1.left_stick_y*0.7;
        double turn  = -gamepad1.right_stick_x*0.5;
        leftMotorPower = Range.clip(drive + turn, -1.00, 1.00) ;
        rightMotorPower = Range.clip(drive - turn, -1.00, 1.00) ;
        leftMotor.setPower(leftMotorPower*0.5);
        rightMotor.setPower(rightMotorPower*0.5);
    }

    public void intakeControl(){
        intakeMotorPower = gamepad1.left_trigger-gamepad1.right_trigger;
        intakeMotor.setPower(intakeMotorPower);
    }

    public void climbControl() {

        if(gamepad1.isRumbling())
        if(gamepad1.left_bumper) {
            climbMotor.setPower(0.1);
        } else {
            climbMotor.setPower(0);
        }

        if(gamepad1.right_bumper) {
            climbMotor.setPower(0.1);
        } else {
            climbMotor.setPower(0);
        }
    }

    public void initHardware() {
        initLeftMotor();
        initRightMotor();
        initIntakeMotor();
        initClimbMotor();
    }

    public void initIntakeMotor(){
        intakeMotor = hardwareMap.get(DcMotor.class, "motorIntake");
        intakeMotor.setDirection(DcMotor.Direction.REVERSE);
        intakeMotor.setPower(0.0);
    }

    public void initLeftMotor() {
        leftMotor = hardwareMap.get(DcMotor.class, "motorEsquerda");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        leftMotor.setPower(0.0);
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // The encoder's current position is set as zero
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void initRightMotor() {
        rightMotor = hardwareMap.get(DcMotor.class, "motorDireita");
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setPower(0.0);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // The encoder's current position is set as zero
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void initClimbMotor() {
        climbMotor = hardwareMap.get(DcMotor.class, "motorCordinha");
        climbMotor.setDirection(DcMotor.Direction.FORWARD);
        climbMotor.setPower(0.0);
        climbMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void showTelemetry() {

        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftMotorPower, rightMotorPower);

        telemetry.update();

    }

}