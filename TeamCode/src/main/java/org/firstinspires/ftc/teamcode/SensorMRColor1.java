package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Sensor: MR Color", group = "Sensor") //Set the name of the program as "Sensor: MR Color"

@Disabled //Disables the program from being shown in the FTC Drivers Station App

public class SensorMRColor1 extends LinearOpMode
{

    ColorSensor colorSensor; //Set colorSensor as a usable variable to use with ColorSensor
    DcMotor motorRight; //Set motorRight as a usable variable to use with DcMotor
    DcMotor motorLeft; //Set motorLeft as a usable variable to use with DcMotor


    @Override
    public void runOpMode()
    {

        float hsvValues[] = {0F,0F,0F};
        final float values[] = hsvValues;

        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        //Get a reference to our ColorSensor object.
        colorSensor = hardwareMap.colorSensor.get("sensor_color");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        motorRight.setDirection(DcMotor.Direction.REVERSE);


        //Wait for the start button to be pressed.
        waitForStart();


        //Convert the RGB values to HSV values.
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

        //Send the info back to driver station using telemetry function.
        telemetry.addData("Clear", colorSensor.alpha());
        telemetry.addData("Red  ", colorSensor.red());
        telemetry.addData("Green", colorSensor.green());
        telemetry.addData("Blue ", colorSensor.blue());
        telemetry.addData("Hue", hsvValues[0]);

        if (colorSensor.red() >= 1) //If the color sensor returns the color red this part of code will run
        {
            motorRight.setPower(.25); //Move forward with a speed of 0.25
            sleep(1000); //Moves forward for 1 second
            motorRight.setPower(-.25); //Move reverse with a speed of -0.25
            sleep(1000); //Moves reverse for 1 second
        }
        else if (colorSensor.blue() >= 1) //If the color sensor returns the color blue this part of the code will run
        {
            motorRight.setPower(-.25); //Move reverse with a speed of -0.25
            sleep(1000); //Moves reverse for 1 second
            motorRight.setPower(.25); //Move forward with a speed of 0.25
            sleep(1000); //Moves forward for 1 second
        }
        else
        {
            motorRight.setPower(0); //If the color sensor retruns no color then this part of the code will run until it sees a color
        }
        telemetry.update(); //Updates what is currently happening back to the driver station
    }
}