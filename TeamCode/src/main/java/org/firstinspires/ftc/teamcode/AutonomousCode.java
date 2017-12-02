package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
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
@Autonomous(name = "Autonomous Code", group = "Blue Side")
//@Disabled
public class AutonomousCode extends LinearOpMode
{

    ColorSensor colorSensorRight;    // Hardware Device Object
    ColorSensor colorSensorLeft;
    Servo colorServoRight;
    Servo colorServoLeft;
    DcMotor rightMotorOutside;
    DcMotor leftMotorOutside;
    DcMotor rightMotorInside;
    DcMotor leftMotorInside;
    DcMotor linearSlideRight;
    DcMotor linearSlideLeft;
    Servo rightServoClaw;
    Servo leftServoClaw;
    VuforiaLocalizer vuforia;
    String _placement=null;

    @Override
    public void runOpMode()
    {
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        // get a reference to our ColorSensor object.
        colorSensorRight = hardwareMap.colorSensor.get("colorSensorRight");
        colorServoRight = hardwareMap.servo.get("colorServoRight");
        colorSensorLeft = hardwareMap.colorSensor.get("colorSensorLeft");
        colorServoLeft = hardwareMap.servo.get("colorServoLeft");
        rightMotorOutside = hardwareMap.dcMotor.get("rightMotorOutside");
        leftMotorOutside = hardwareMap.dcMotor.get("leftMotorOutside");
        rightMotorInside = hardwareMap.dcMotor.get("rightMotorInside");
        leftMotorInside = hardwareMap.dcMotor.get("leftMotorInside");
        linearSlideRight = hardwareMap.dcMotor.get("linearSlideRight");
        linearSlideLeft = hardwareMap.dcMotor.get("linearSlideLeft");
        rightServoClaw = hardwareMap.servo.get("rightServoClaw");
        leftServoClaw = hardwareMap.servo.get("leftServoClaw");
        linearSlideLeft.setDirection(DcMotor.Direction.REVERSE);
        rightMotorInside.setDirection(DcMotor.Direction.REVERSE);
        rightMotorOutside.setDirection(DcMotor.Direction.REVERSE);


        // wait for the start button to be pressed.
        waitForStart();

        doAutonomousProgram("left", "right");
        //Drop linear slide down to place cube on ground
        //LinearSlide(0.15);
        //sleep(225);

        StopDriving();


    }
    public void DriveForward(double power)
    {
        leftMotorInside.setPower(power);
        leftMotorOutside.setPower(power);
        rightMotorInside.setPower(power);
        rightMotorOutside.setPower(power);
    }
    public void DriveBackward(double power)
    {
        leftMotorInside.setPower(-power);
        leftMotorOutside.setPower(-power);
        rightMotorInside.setPower(-power);
        rightMotorOutside.setPower(-power);
    }
    public void RightTurn(double power)
    {
        leftMotorInside.setPower(power);
        leftMotorOutside.setPower(power);
        rightMotorInside.setPower(-power);
        rightMotorOutside.setPower(-power);
    }
    public void LeftTurn(double power)
    {
        leftMotorInside.setPower(-power);
        leftMotorOutside.setPower(-power);
        rightMotorInside.setPower(power);
        rightMotorOutside.setPower(power);
    }
    public void StopDriving()
    {
        leftMotorInside.setPower(0);
        leftMotorOutside.setPower(0);
        rightMotorInside.setPower(0);
        rightMotorOutside.setPower(0);
    }
    public void LinearSlide(double power)
    {
        linearSlideRight.setPower(power);
        linearSlideLeft.setPower(power);
    }
    void doAutonomousProgram (String _side, String _direction)
    {
        ColorSensor thisSensor=null;
        Servo thisServo=null;
        float hsvValues[] = {0F,0F,0F};
        final float values[] = hsvValues;

        //set variable for speed of robot moving when doing jewels mission
        double _move = 0.15;
        long _sleeptime = 350;

        leftServoClaw.setPosition(0.65);
        rightServoClaw.setPosition(0.05);
        LinearSlide(0.15);
        sleep(250);
        //code for arm side
        if (_side == "right")
        {
            thisSensor=colorSensorRight;
            thisServo=colorServoRight;
            colorServoRight.setPosition(0.7);
            sleep(1000);
            colorServoRight.setPosition(0.9);
            sleep(3000);
        }
        else if (_side == "left")
        {
            thisSensor=colorSensorLeft;
            thisServo=colorServoLeft;
            colorServoLeft.setPosition(0.7);
            sleep(1000);
            colorServoLeft.setPosition(0.9);
            sleep(3000);
        }
        else telemetry.addData("NO SIDE INFO FOUND", "");

        //begin to identify the color of jewel
        // convert the RGB values to HSV values.
        Color.RGBToHSV(thisSensor.red() * 8, thisSensor.green() * 8, thisSensor.blue() * 8, hsvValues);

        // send the info back to driver station using telemetry function.
        telemetry.addData("Clear", thisSensor.alpha());
        telemetry.addData("Red  ", thisSensor.red());
        telemetry.addData("Green", thisSensor.green());
        telemetry.addData("Blue ", thisSensor.blue());
        telemetry.addData("Hue", hsvValues[0]);

        //move robot to knock off opposite alliance color ball
        //if senses red drive backward
        if (thisSensor.red() >= 1)
        {
            thisServo.setPosition(0.85);
            DriveBackward(_move);
            sleep(_sleeptime);
            thisServo.setPosition(0.1);
            DriveBackward(-_move);
            sleep(_sleeptime+100);
        }
        //if senses blue drive forward
        else if (thisSensor.blue() >= 1)
        {
            thisServo.setPosition(0.85);
            DriveBackward(-_move);
            sleep(_sleeptime);
            thisServo.setPosition(0.1);
            DriveBackward(_move);
            sleep(_sleeptime+100);
        }
        else
        {
            //raise sensor arm up if no color is sensed
            thisServo.setPosition(0.1);
            sleep(100);
        }
        //Code for direction
        if (_direction.equals("right"))
        {
            RightTurn(0.5);
            sleep(500);
        }
        else if (_direction.equals("left"))
        {
            LeftTurn(0.5);
            sleep(500);
        }





    }
}