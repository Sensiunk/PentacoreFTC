package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/*
 *
 *This program is for Blue Alliance Square 1
 *This is the left sided balancing stone from the drivers square perspective on the blue side
 *
 */
@Autonomous(name = "PlateMovementTest", group = "Test Programs")
//@Disabled
public class PlateMovementTest extends LinearOpMode {

    DcMotor rightMotorOutside;
    DcMotor leftMotorOutside;
    DcMotor rightMotorInside;
    DcMotor leftMotorInside;
    DcMotor blockMotorArm;
    Servo rightClawServo;
    Servo leftClawServo;

    @Override
    public void runOpMode() {

        // get a reference to our ColorSensor object.
        rightMotorOutside = hardwareMap.dcMotor.get("rightMotorOutside");
        leftMotorOutside = hardwareMap.dcMotor.get("leftMotorOutside");
        rightMotorInside = hardwareMap.dcMotor.get("rightMotorInside");
        leftMotorInside = hardwareMap.dcMotor.get("leftMotorInside");
        blockMotorArm = hardwareMap.dcMotor.get("blockMotorArm");
        rightClawServo = hardwareMap.servo.get("rightClawServo");
        leftClawServo = hardwareMap.servo.get("leftClawServo");
        rightMotorInside.setDirection(DcMotor.Direction.REVERSE);
        rightMotorOutside.setDirection(DcMotor.Direction.REVERSE);
        blockMotorArm.setDirection(DcMotor.Direction.REVERSE);


        // wait for the start button to be pressed.
        waitForStart();

        //move sensor arm down
        leftClawServo.setPosition(0.7);
        rightClawServo.setPosition(0.05);
        sleep(1000);
        blockMotorArm.setPower(0.5);
        sleep(1000);
        blockMotorArm.setPower(0.0);
        sleep(1);
        StopDriving();
        sleep(1);

        //turn right and move towards the crypto box
        //Go forward
        DriveForward(0.25);
        sleep(175);
        StopDriving();
        sleep(100);
        LeftTurn(0.25);
        //Turn Left
        sleep(1125);
        StopDriving();
        sleep(100);
        //Drive Forward
        DriveForward(0.25);
        sleep(950);
        StopDriving();
        sleep(100);
        //Move to the right
        Strafe(0.25);
        //For Center
        //sleep(2000);
        //For Right
        sleep(2400);
        //For Left
        //sleep(1600);
//        //Drop linear slide down to place cube on ground
//        DriveForward(0.5);
//        sleep(150);
//        StopDriving();
//        sleep(100);
//        leftClawServo.setPosition(0.0);
//        rightClawServo.setPosition(0.6);
//        DriveBackward(0.25);
//        sleep(50);
//        StopDriving();


    }

    public void DriveForward(double power) {
        leftMotorInside.setPower(power);
        leftMotorOutside.setPower(power);
        rightMotorInside.setPower(power);
        rightMotorOutside.setPower(power);
    }

    public void DriveBackward(double power) {
        leftMotorInside.setPower(-power);
        leftMotorOutside.setPower(-power);
        rightMotorInside.setPower(-power);
        rightMotorOutside.setPower(-power);
    }

    public void Strafe(double power) {
        leftMotorInside.setPower(-power);
        leftMotorOutside.setPower(power);
        rightMotorInside.setPower(power);
        rightMotorOutside.setPower(-power);
    }

    public void RightTurn(double power) {
        leftMotorInside.setPower(power);
        leftMotorOutside.setPower(power);
        rightMotorInside.setPower(-power);
        rightMotorOutside.setPower(-power);
    }

    public void LeftTurn(double power) {
        leftMotorInside.setPower(-power);
        leftMotorOutside.setPower(-power);
        rightMotorInside.setPower(power);
        rightMotorOutside.setPower(power);
    }

    public void StopDriving() {
        leftMotorInside.setPower(0);
        leftMotorOutside.setPower(0);
        rightMotorInside.setPower(0);
        rightMotorOutside.setPower(0);
    }

    public void Arm(double power) {
        blockMotorArm.setPower(power);
    }

    public void ArmStop() {
        blockMotorArm.setPower(0);
    }
}
