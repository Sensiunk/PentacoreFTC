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

@TeleOp(name = "GE2 Test Robot")
@Disabled
public class GE2Robot extends LinearOpMode
{
    private DcMotor motorRight;
    private DcMotor motorLeft;

    //private Servo rightServo;
    //private Servo leftServo;

    @Override
    public void runOpMode() throws InterruptedException
    {
        motorRight = hardwareMap.dcMotor.get("motorRight");
        motorLeft = hardwareMap.dcMotor.get("motorLeft");

        motorRight.setDirection(DcMotor.Direction.REVERSE);

        //leftServo = hardwareMap.servo.get("leftServo");
        //rightServo = hardwareMap.servo.get("rightServo");

        waitForStart();

        while (opModeIsActive())
        {
            motorLeft.setPower(-gamepad1.left_stick_y);
            motorRight.setPower(-gamepad1.right_stick_y);

            idle();
        }
    }
}
