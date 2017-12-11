package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Strafing Test Program")
public class StrafingTestProgram extends LinearOpMode
{
    DcMotor rightMotorOutside;
    DcMotor leftMotorOutside;
    DcMotor rightMotorInside;
    DcMotor leftMotorInside;
    DcMotor blockMotorArm;
    Servo colorServo;
    ColorSensor jewelColorSensor;
    Servo rightClawServo;
    Servo leftClawServo;

    @Override
    public void runOpMode() throws InterruptedException
    {
        rightMotorOutside = hardwareMap.dcMotor.get("rightMotorOutside");
        leftMotorOutside = hardwareMap.dcMotor.get("leftMotorOutside");
        rightMotorInside = hardwareMap.dcMotor.get("rightMotorInside");
        leftMotorInside = hardwareMap.dcMotor.get("leftMotorInside");
        blockMotorArm = hardwareMap.dcMotor.get("blockMotorArm");
        colorServo = hardwareMap.servo.get("colorServo");
        jewelColorSensor = hardwareMap.colorSensor.get("jewelColorSensor");
        rightClawServo = hardwareMap.servo.get("rightClawServo");
        leftClawServo = hardwareMap.servo.get("leftClawServo");
        rightMotorInside.setDirection(DcMotor.Direction.REVERSE);
        rightMotorOutside.setDirection(DcMotor.Direction.REVERSE);
        blockMotorArm.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        //Main Inits
        rightClawServo.setPosition(0.6);
        leftClawServo.setPosition(0.0);
        colorServo.setPosition(0.0);

        while (opModeIsActive())
        {
            //Introduce variables
            double drive;   // Power for forward and back motion
            double strafe;  // Power for left and right motion
            double rotate;  // Power for rotating the robot
            float arm;

            //Gamepad 1 Portion
            //-------------------------------------------------------------------------
            drive = -gamepad1.left_stick_y;  // Negative because the gamepad is weird
            strafe = gamepad1.left_stick_x;
            rotate = gamepad1.right_stick_x;

            //Set the values for the drive to be only -1 <-> 1
            drive = Range.clip(drive, -1, 1);
            strafe = Range.clip(strafe, -1, 1);
            rotate = Range.clip(rotate, -1, 1);

            //Set the variables drive component to work with our custom method
            drive = (float) scaleInput(drive);
            strafe = (float) scaleInput(strafe);
            rotate = (float) scaleInput(rotate);

            if (gamepad1.left_trigger > 0.25)
            {
                 drive /= 3;
                 strafe /= 3;
                 rotate /= 3;
            }

            //Set the power for the wheels
            leftMotorInside.setPower(drive - strafe + rotate);
            leftMotorOutside.setPower(drive + strafe + rotate);
            rightMotorInside.setPower(drive + strafe - rotate);
            rightMotorOutside.setPower(drive - strafe - rotate);

            //Gamepad 2 Portion
            //-------------------------------------------------------------------------
            arm = -gamepad2.left_stick_y;

            arm = Range.clip(arm, -1, 1);

            arm = (float) scaleInput(arm);

            blockMotorArm.setPower(arm);

            if (gamepad2.a)
            {
                //Block gripper opens
                rightClawServo.setPosition(0.6);
                leftClawServo.setPosition(0.0);
            }
            if (gamepad2.b)
            {
                //Block gripper closes
                rightClawServo.setPosition(0.0);
                leftClawServo.setPosition(0.7);
            }
            if (gamepad2.x)
            {
                rightClawServo.setPosition(0.05);
                leftClawServo.setPosition(0.55);
            }
            if (gamepad2.left_trigger > 0.25)
            {
                arm /= 3;
            }

            //Fail safe actions
            //-------------------------------------------------------------------------

            //Color sensor back up if needed
            if (gamepad1.y)
            {
                colorServo.setPosition(0.0);
            }

            idle();
        }

    }


    double scaleInput(double dVal)
    {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};
        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        }
        if (index > 16) {
            index = 16;
        }
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }
        return dScale;
    }
}