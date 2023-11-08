//Copyright (c) 2017 FIRST. All rights reserved.

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp Principal", group="Iterative OpMode")

public class MainTeleOpMode extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftMotor = null;
    private double leftMotorPower = 0.0;

    private DcMotor rightMotor = null;
    private double rightMotorPower = 0.0;

    private DcMotor intakeMotor = null;
    private double intakeMotorPower = 0.0;

    private double climbMotorPower = 0.0;
    private DcMotor climbMotor = null;
    private int climbTicks = 0;
    private int maxClimbTicks = 0;

    private Servo servoDrone = null;

    private Servo servoOuttake = null;

    @Override
    public void init() {

        initHardware();

    }

    @Override
    public void start() {
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
        droneControl();
    }

    public void chassiControl() {
        double drive = gamepad1.left_stick_y*0.9;
        double turn  = -gamepad1.right_stick_x*0.8;

        leftMotorPower = Range.clip(drive + turn, -1.00, 1.00) ;
        rightMotorPower = Range.clip(drive - turn, -1.00, 1.00) ;

        leftMotor.setPower(leftMotorPower);
        rightMotor.setPower(rightMotorPower);
    }

    public void intakeControl(){

        if(gamepad1.left_bumper) {
            intakeMotor.setPower(1.0);
        }

        if (gamepad1.right_bumper) {
            intakeMotor.setPower(-1.0);
        }
    }

    public void climbControl(){
        climbMotorPower = gamepad1.right_trigger-gamepad1.left_trigger;
        climbMotor.setPower(climbMotorPower);

    }

    public void droneControl() {
        if(gamepad1.x && gamepad1.a) {
            servoDrone.setPosition(0);
        }
    }

    public void outtakeControl() {
        if(gamepad1.y) {
            servoOuttake.setPosition(1.0);
        }

        if(gamepad1.b) {
            servoOuttake.setPosition(0.0);
        }
    }

    public void initHardware() {
        initLeftMotor();
        initRightMotor();
        initIntakeMotor();
        initClimbMotor();
        initServoDrone();
        initServoOuttake();
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
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // The encoder's current position is set as zero
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void initRightMotor() {
        rightMotor = hardwareMap.get(DcMotor.class, "motorDireita");
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setPower(0.0);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // The encoder's current position is set as zero
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void initClimbMotor() {
        climbMotor = hardwareMap.get(DcMotor.class, "motorClimb");

    }

    public void initServoDrone() {
        servoDrone = hardwareMap.get(Servo.class, "servoDrone");
        servoDrone.setDirection(Servo.Direction.FORWARD);
        servoDrone.setPosition(0.7);
    }

    public void initServoOuttake() {
        servoOuttake = hardwareMap.get(Servo.class, "servoOuttake");
        servoOuttake.setDirection(Servo.Direction.FORWARD);
        servoOuttake.setPosition(0.0);
    }

    public void showTelemetry() {

        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftMotorPower, rightMotorPower);

        telemetry.update();

    }


    @Override
    public void stop(){
    }


}
