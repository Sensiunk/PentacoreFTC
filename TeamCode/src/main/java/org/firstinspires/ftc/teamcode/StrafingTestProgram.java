package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Strafing Test Program")
public class StrafingTestProgram extends LinearOpMode
{
    DcMotor rightMotorOutside;
    DcMotor leftMotorOutside;
    DcMotor rightMotorInside;
    DcMotor leftMotorInside;



    @Override
    public void runOpMode() throws InterruptedException
    {
        rightMotorOutside = hardwareMap.dcMotor.get("rightMotorOutside");
        leftMotorOutside = hardwareMap.dcMotor.get("leftMotorOutside");
        rightMotorInside = hardwareMap.dcMotor.get("rightMotorInside");
        leftMotorInside = hardwareMap.dcMotor.get("leftMotorInside");

        rightMotorInside.setDirection(DcMotor.Direction.REVERSE);
        rightMotorOutside.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive())
        {
            double drive;   // Power for forward and back motion
            double strafe;  // Power for left and right motion
            double rotate;  // Power for rotating the robot

            drive = -gamepad1.left_stick_y;  // Negative because the gamepad is weird
            strafe = gamepad1.left_stick_x;
            rotate = gamepad1.right_stick_x;

            drive = Range.clip(drive, -1, 1);
            strafe = Range.clip(strafe, -1, 1);
            rotate = Range.clip(rotate, -1, 1);

            drive = (float) scaleInput(drive);
            strafe = (float) scaleInput(strafe);
            rotate = (float) scaleInput(rotate);

            leftMotorInside.setPower(drive + strafe + rotate);
            leftMotorOutside.setPower(drive - strafe + rotate);
            rightMotorInside.setPower(drive - strafe - rotate);
            rightMotorOutside.setPower(drive + strafe - rotate);

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