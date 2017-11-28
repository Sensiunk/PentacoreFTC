package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Servo Test") //Set the name of the program as "Servo Test"

//@Disabled //Disables program from being shown in FTC Drivers Station App

public class ServoTest extends LinearOpMode
{

    Servo sensorServo; //Set sensorServo as a usable variable to use with Servo

    @Override
    public void runOpMode()
    {
        sensorServo = hardwareMap.servo.get("sensorServo"); //Get servo called "sensorServo"

        //Wait for the start button to be pressed.
        waitForStart();

        sensorServo.setPosition(0.0); //Set 1st position
        sleep(1000); //Wait 1 second to complete turn
        sensorServo.setPosition(0.1); //Set 2nd position
        sleep(1000); //Wait 1 second to complete turn
        sensorServo.setPosition(0.2); //Set 3rd position
        sleep(1000); //Wait 1 second to complete turn
        sensorServo.setPosition(0.3); //Set 4th position
        sleep(1000); //Wait 1 second to complete turn
        sensorServo.setPosition(0.4); //Set 5th position
        sleep(1000); //Wait 1 second to complete turn
        sensorServo.setPosition(0.5); //Set 6th position
        sleep(1000); //Wait 1 second to complete turn
        sensorServo.setPosition(0.6); //Set 7th position
        sleep(1000); //Wait 1 second to complete turn
        sensorServo.setPosition(0.7); //Set 8th position
        sleep(1000); //Wait 1 second to complete turn
        sensorServo.setPosition(0.8); //Set 9th position
        sleep(1000); //Wait 1 second to complete turn
        sensorServo.setPosition(0.9); //Set 10th position
        sleep(1000); //Wait 1 second to complete turn
        sensorServo.setPosition(1.0); //Set 11th position
        sleep(1000); //Wait 1 second to complete turn
    }
}
