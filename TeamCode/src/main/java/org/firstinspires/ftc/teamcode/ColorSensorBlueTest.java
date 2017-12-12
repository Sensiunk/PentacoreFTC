package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by manjeshpuram on 12/11/17.
 */
/*
 *
 *This program is for Blue Alliance Square 1
 *This is the left sided balancing stone from the drivers square perspective on the blue side
 *
 */
@Autonomous(name = "ColorSensorBlueTest", group = "Test Programs")
//@Disabled
public class ColorSensorBlueTest extends LinearOpMode
{

    DcMotor rightMotorOutside;
    DcMotor leftMotorOutside;
    DcMotor rightMotorInside;
    DcMotor leftMotorInside;
    DcMotor blockMotorArm;
    Servo colorServo;
    ColorSensor jewelColorSensor;
    Servo secondColorServo;
    Servo rightClawServo;
    Servo leftClawServo;
    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode()
    {
        float hsvValues[] = {0F,0F,0F};
        final float values[] = hsvValues;

        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        // get a reference to our ColorSensor object.
        rightMotorOutside = hardwareMap.dcMotor.get("rightMotorOutside");
        leftMotorOutside = hardwareMap.dcMotor.get("leftMotorOutside");
        rightMotorInside = hardwareMap.dcMotor.get("rightMotorInside");
        leftMotorInside = hardwareMap.dcMotor.get("leftMotorInside");
        blockMotorArm = hardwareMap.dcMotor.get("blockMotorArm");
        colorServo = hardwareMap.servo.get("colorServo");
        secondColorServo = hardwareMap.servo.get("secondColorServo");
        jewelColorSensor = hardwareMap.colorSensor.get("jewelColorSensor");
        rightClawServo = hardwareMap.servo.get("rightClawServo");
        leftClawServo = hardwareMap.servo.get("leftClawServo");
        rightMotorInside.setDirection(DcMotor.Direction.REVERSE);
        rightMotorOutside.setDirection(DcMotor.Direction.REVERSE);
        blockMotorArm.setDirection(DcMotor.Direction.REVERSE);


        // wait for the start button to be pressed.
        waitForStart();

        //set variable for speed of robot moving when doing jewels mission
        double _move = 0.15;
        long _sleeptime = 750;

        //move sensor arm down
        secondColorServo.setPosition(0.5);
        colorServo.setPosition(0.8);
        sleep(1000);
        colorServo.setPosition(0.95);
        sleep(1000);
//        leftClawServo.setPosition(0.7);
//        rightClawServo.setPosition(0.05);
//        blockMotorArm.setPower(0.25);
//        sleep(250);
//        blockMotorArm.setPower(0.0);
//        sleep(1);
        StopDriving();
        sleep(1000);


        //begin to identify the color of jewel
        // convert the RGB values to HSV values.
        Color.RGBToHSV(jewelColorSensor.red() * 8, jewelColorSensor.green() * 8, jewelColorSensor.blue() * 8, hsvValues);

        // send the info back to driver station using telemetry function.
        telemetry.addData("Clear", jewelColorSensor.alpha());
        telemetry.addData("Red  ", jewelColorSensor.red());
        telemetry.addData("Green", jewelColorSensor.green());
        telemetry.addData("Blue ", jewelColorSensor.blue());
        telemetry.addData("Hue", hsvValues[0]);

        //move robot to knock off opposite alliance color ball
        //if senses red drive backward
//        telemetry.log().add("$$$$$$$$$ $$$$$ color sensed red"+jewelColorSensor.red());
//        telemetry.log().add("$$$$$$$$$ $$$$$ color sensed blue"+jewelColorSensor.blue());
//        Log.d("$$$$$$ RED",""+jewelColorSensor.red());
//        Log.d("$$$$$$BLUE",""+jewelColorSensor.blue());

        if (jewelColorSensor.blue() > jewelColorSensor.red())
        {
            StopDriving();
            sleep(1000);
            colorServo.setPosition(0.95);
            secondColorServo.setPosition(1.0);
            sleep(500);
            colorServo.setPosition(0.5);
            secondColorServo.setPosition(0.5);
            colorServo.setPosition(0.0);
            sleep(3000);
        }
        else if (jewelColorSensor.red() > jewelColorSensor.blue())
        {
            StopDriving();
            sleep(1000);
            colorServo.setPosition(0.95);
            secondColorServo.setPosition(0.0);
            sleep(500);
            colorServo.setPosition(0.5);
            secondColorServo.setPosition(0.5);
            colorServo.setPosition(0.0);
            sleep(3000);
        }
        else
        {
            //raise sensor arm up if no color is sensed
            colorServo.setPosition(0.0);
            secondColorServo.setPosition(0.5);
            sleep(1000);
        }
        //turn right and move towards the crypto box
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
    public void Strafe(double power)
    {
        leftMotorInside.setPower(-power);
        leftMotorOutside.setPower(power);
        rightMotorInside.setPower(power);
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
    public void Arm(double power)
    {
        blockMotorArm.setPower(power);
    }
    public void ArmStop()
    {
        blockMotorArm.setPower(0);
    }

}