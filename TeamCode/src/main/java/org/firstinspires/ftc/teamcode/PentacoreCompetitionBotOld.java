package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


/**
 * Created by manjeshpuram on 10/29/17.
 */
@TeleOp(name="Pentacore TeleOp")
@Disabled
public class PentacoreCompetitionBotOld extends LinearOpMode
{
    private DcMotor motorRight; //Declare Right Motor
    private DcMotor motorLeft; //Declare Left Motor
    private DcMotor motorArm; //Declare Arm's Motor

    private Servo rightServo; //Declare Right Servo
    private Servo leftServo; //Declare Left Servo

    @Override
    public void runOpMode() throws InterruptedException {
        motorRight = hardwareMap.dcMotor.get("motorRight"); //test
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorArm = hardwareMap.dcMotor.get("motorArm");

        motorRight.setDirection(DcMotor.Direction.REVERSE);

        leftServo = hardwareMap.servo.get("leftServo");
        rightServo = hardwareMap.servo.get("rightServo");

        rightServo.scaleRange(0.6, 1.0);
        leftServo.scaleRange(0.0, 0.4);

        waitForStart();

        while (opModeIsActive()) {
            float arm;
            float right;
            float left;

            arm = -gamepad2.left_stick_y;
            right = -gamepad1.right_stick_y;
            left = -gamepad1.left_stick_y;

            arm = Range.clip(arm, -1, 1);
            right = Range.clip(right, -1, 1);
            left = Range.clip(left, -1, 1);

            arm = (float) scaleInput(arm);
            right = (float) scaleInput(right);
            left = (float) scaleInput(left);

            if (gamepad1.left_trigger > 0.25) {
                left /= 3;
            }

            motorLeft.setPower(left);

            if (gamepad1.right_trigger > 0.25) {
                right /= 3;
            }

            motorRight.setPower(right);

            if (gamepad2.left_trigger > 0.25) {
                arm /= 3;
            }

            motorArm.setPower(arm);


            if (gamepad2.a) {
                telemetry.log().add("Test1", "Test1");
                rightServo.setPosition(Servo.MAX_POSITION);
                leftServo.setPosition(Servo.MIN_POSITION);
            }
            if (gamepad2.b) {
                telemetry.log().add("Test2", "Test2");
                rightServo.setPosition(Servo.MIN_POSITION);
                leftServo.setPosition(Servo.MAX_POSITION);
            }

            idle();
        }

    }
    double scaleInput(double dVal) {
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