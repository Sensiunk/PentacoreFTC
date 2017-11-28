package org.firstinspires.ftc.teamcode;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by manjeshpuram on 10/31/17.
 */
@Autonomous(name="ColorSensorProgram")
@Disabled
public class ColorSensorAutoTest extends LinearOpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    com.qualcomm.robotcore.hardware.ColorSensor sensorColor;
    DcMotor motorRight;
    DcMotor motorLeft;

    @Override
    public void runOpMode()
    {
        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(R.id.RelativeLayout);

        sensorColor = hardwareMap.colorSensor.get("sensorColor");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        motorLeft = hardwareMap.dcMotor.get("motorLeft");

        motorRight.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("Initialization", "Complete");


        int b = 1;

        waitForStart();
        while (opModeIsActive())
        {
            sensorColor.enableLed(true);
            Color.RGBToHSV(sensorColor.red() * 8, sensorColor.green() * 8, sensorColor.blue() * 8, hsvValues);
            telemetry.addData("Red  ", sensorColor.red());
            telemetry.addData("Blue  ", sensorColor.blue());
            telemetry.update();

            if (sensorColor.red() >= 2 && b == 1)
            {
                motorRight.setPower(.25);
                sleep(1000);
                motorRight.setPower(-.25);
                sleep(1000);
                b = 2;
            }
            else if (sensorColor.red() < 2 && b == 1)
            {
                motorRight.setPower(-.25);
                sleep(1000);
                motorRight.setPower(.25);
                sleep(1000);
                b = 2;
            }
            else
            {
                motorRight.setPower(0);
            }
            sensorColor.enableLed(false);
        }
        telemetry.update();
        idle();
    }
}