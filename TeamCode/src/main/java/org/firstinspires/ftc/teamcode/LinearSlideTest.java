package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by manjeshpuram on 11/19/17.
 */

@TeleOp(name = "PentacoreTestbot")
public class LinearSlideTest extends LinearOpMode
{
    //private DcMotor LinearSlideMotor; //Declare Linear Slide Motor
    private DcMotor rightMotorOutside;
    private DcMotor leftMotorOutside;
    private DcMotor rightMotorInside;
    private DcMotor leftMotorInside;
    private DcMotor LinearSlideRight;
    private DcMotor LinearSlideLeft;
    private Servo rightServo;
    private Servo leftServo;


    @Override
    public void runOpMode() throws InterruptedException
    {
        rightMotorOutside = hardwareMap.dcMotor.get("rightMotorOutside");
        leftMotorOutside = hardwareMap.dcMotor.get("leftMotorOutside");
        rightMotorInside = hardwareMap.dcMotor.get("rightMotorInside");
        leftMotorInside = hardwareMap.dcMotor.get("leftMotorInside");
        LinearSlideRight = hardwareMap.dcMotor.get("LinearSlideRight");
        LinearSlideLeft = hardwareMap.dcMotor.get("LinearSlideLeft");
        rightServo = hardwareMap.servo.get("rightServo");
        leftServo = hardwareMap.servo.get("leftServo");
        LinearSlideLeft.setDirection(DcMotor.Direction.REVERSE);
        rightMotorInside.setDirection(DcMotor.Direction.REVERSE);
        rightMotorOutside.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive())
        {
            float linearSlide;
            float RightSide;
            float LeftSide;
            float Slide;

            linearSlide = -gamepad2.left_stick_y;
            RightSide = -gamepad1.right_stick_y;
            LeftSide = -gamepad1.left_stick_y;
            Slide = -gamepad2.right_stick_x;

            linearSlide = Range.clip(linearSlide, -1, 1);
            LeftSide = Range.clip(LeftSide, -1, 1);
            RightSide = Range.clip(RightSide, -1, 1);
            Slide = Range.clip(Slide, -1, 1);

            linearSlide = (float) scaleInput(linearSlide);
            RightSide = (float) scaleInput(RightSide);
            LeftSide = (float) scaleInput(LeftSide);
            Slide = (float) scaleInput(Slide);

            if(gamepad2.a) //If the Gamepad 2 A is pressed the the right servo opens
            {
                telemetry.log().add("Test1", " ");
                rightServo.setPosition(0.6);
                leftServo.setPosition(0.0);
            }
            if (gamepad2.b) //If the Gamepad 2 B is pressed the the right servo closes
            {
                telemetry.log().add("Test2", " ");
                rightServo.setPosition(0.0);
                leftServo.setPosition(0.6);
            }
            if (gamepad1.left_trigger > 0.25)
            {
                LeftSide /= 3;
            }

            if (gamepad1.right_trigger > 0.25)
            {
                RightSide /= 3;
            }

            if (gamepad2.left_trigger > 0.25) {
                linearSlide /= 3;
            }

            //Basic Drive
            LinearSlideLeft.setPower(linearSlide);
            LinearSlideRight.setPower(linearSlide);
            rightMotorInside.setPower(RightSide);
            rightMotorOutside.setPower(RightSide);
            leftMotorInside.setPower(LeftSide);
            leftMotorOutside.setPower(LeftSide);

            //Side to Side Left
            rightMotorInside.setPower(Slide);
            rightMotorOutside.setPower(Slide);
            leftMotorInside.setPower(Slide);
            leftMotorOutside.setPower(Slide);

            //Side to Side Right
            /* rightMotorInside.setPower(-RightSlide);
            rightMotorOutside.setPower(RightSlide);
            leftMotorInside.setPower(RightSlide);
            leftMotorOutside.setPower(-RightSlide); */

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