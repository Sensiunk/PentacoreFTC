package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by manjeshpuram on 10/15/17.
 */

@TeleOp(name = "Pentacore Testbot") //Set the name of the program as "Pentacore Testbot"

@Disabled //Disables the program from being shown in the FTC Drivers Station App

public class PentacoreTestbot extends LinearOpMode
{
    private DcMotor motorRight; //Set motorRight as a usable variable to use with DcMotor
    private DcMotor motorLeft; //Set motorLeft as a usable variable to use with DcMotor
    private DcMotor motorArm; //Set motorArm as a usable variable to use with DcMotor

    private Servo rightServo; //Set rightServo as a usable variable to use with Servo
    private Servo leftServo; //Set leftServo as a usable variable to use with Servo

    @Override
    public void runOpMode() throws InterruptedException
    {
        motorRight = hardwareMap.dcMotor.get("motorRight"); //Look for on the Hardware Config for a DcMotor called "motorRight"
        motorLeft = hardwareMap.dcMotor.get("motorLeft"); //Look for on the Hardware Config for a DcMotor called "motorLeft"
        motorArm = hardwareMap.dcMotor.get("motorArm"); //Look for on the Hardware Config for a DcMotor called "motorArm"

        motorLeft.setDirection(DcMotor.Direction.REVERSE); //Set the motorLeft to the Reverse Direction
        motorArm.setDirection(DcMotor.Direction.REVERSE); //Set the motorArm to the Reverse Direction

        leftServo = hardwareMap.servo.get("leftServo"); //Look for on the Hardware Config for a Servo called "leftServo"
        rightServo = hardwareMap.servo.get("rightServo"); //Look for on the Hardware Config for a Servo called "rightServo"

        waitForStart();

        while (opModeIsActive())
        {
            motorLeft.setPower(-gamepad1.left_stick_y); //motorLeft gets its input from the Gamepad 1 Left Joystick on the Y - axis
            motorRight.setPower(-gamepad1.right_stick_y); //motorRight gets its input from the Gamepad 1 Right Joystick on the Y - axis
            motorArm.setPower(-gamepad2.left_stick_y); //motorLeft gets its input from the Gamepad 2 Left Joystick on the Y - axis

            if(gamepad2.a) //If the Gamepad 2 A is pressed the the right servo opens
            {
                telemetry.log().add("Test1", "Test1");
                rightServo.setPosition(-0.6);
            }
            if (gamepad2.b) //If the Gamepad 2 B is pressed the the right servo closes
            {
                telemetry.log().add("Test2", "Test2");
                rightServo.setPosition(2);
            }
            if(gamepad2.x) //If the Gamepad 2 X is pressed the the left servo opens
            {
                telemetry.log().add("Test1", "Test1");
                leftServo.setPosition(1.5);
            }
            if(gamepad2.y) //If the Gamepad 2 Y is pressed the the left servo closes
            {
                telemetry.log().add("Test2", "Test2");
                leftServo.setPosition(0.2);
            }
            idle();
        }
    }
}
