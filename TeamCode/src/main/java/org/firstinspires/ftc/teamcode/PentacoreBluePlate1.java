package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/*
 *
 *This program is for Blue Alliance Square 1
 *This is the left sided balancing stone from the drivers square perspective on the blue side
 *
 */
@Autonomous(name = "PentacoreBluePlate1", group = "Blue Side")
//@Disabled
public class PentacoreBluePlate1 extends LinearOpMode
{
    DcMotor rightMotorOutside;
    DcMotor leftMotorOutside;
    DcMotor rightMotorInside;
    DcMotor leftMotorInside;
    DcMotor blockMotorArm;
    Servo colorServo;
    Servo secondColorServo;
    ColorSensor jewelColorSensor;
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
        jewelColorSensor = hardwareMap.colorSensor.get("jewelColorSensor");
        rightClawServo = hardwareMap.servo.get("rightClawServo");
        leftClawServo = hardwareMap.servo.get("leftClawServo");
        rightMotorInside.setDirection(DcMotor.Direction.REVERSE);
        rightMotorOutside.setDirection(DcMotor.Direction.REVERSE);
        blockMotorArm.setDirection(DcMotor.Direction.REVERSE);


        // wait for the start button to be pressed.
        waitForStart();

        String _placement = dovuforia();

        // just incase the placement value is incorrectly set, we brute force it to set it to CENTER.
        if(_placement.equals("UNKNOWN"))
            _placement="CENTER";

        double _rightAngleValue = 0.23; // Defaulting a value incase the vuforia doesn't work
        if (_placement.equals("RIGHT"))
        {
            _rightAngleValue = 0.25;
        }
        if (_placement.equals("CENTER"))
        {
            _rightAngleValue = 0.24;
        }
        if (_placement.equals("LEFT"))
        {
            _rightAngleValue = 0.23;
        }

        //move sensor arm down
        secondColorServo.setPosition(0.5);
        colorServo.setPosition(0.8);
        leftClawServo.setPosition(0.0);
        rightClawServo.setPosition(0.6);
        sleep(500);
        colorServo.setPosition(0.95);
        sleep(1000);
        leftClawServo.setPosition(0.7);
        rightClawServo.setPosition(0.05);
        blockMotorArm.setPower(0.25);
        sleep(250);
        blockMotorArm.setPower(0.0);
        sleep(1);
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
//        sleep(1000);
//        RightTurn(0.24);
//        sleep(125);
//        StopDriving();
//        sleep(1000);
//        DriveForward(0.5);
//        sleep(100);
//        //Drop linear slide down to place cube on ground
//        leftServoClaw.setPosition(0.0);
//        rightServoClaw.setPosition(0.6);
//        DriveForward(0.5);
//        sleep(1500);
//        DriveBackward(0.25);
//        sleep(50);
//        StopDriving();


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
    private String dovuforia() {
        String _placement = "CENTER";
        /*
         * To start up Vuforia, tell it the view that we wish to use for camera monitor (on the RC phone);
         * If no camera monitor is desired, use the parameterless constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        /*
         * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
         * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
         * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
         * web site at https://developer.vuforia.com/license-manager.
         *
         * Vuforia license keys are always 380 characters long, and look as if they contain mostly
         * random data. As an example, here is a example of a fragment of a valid key:
         *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
         * Once you've obtained a license key, copy the string from the Vuforia web site
         * and paste it in to your code onthe next line, between the double quotes.
         */
        parameters.vuforiaLicenseKey = "ASYvpnj/////AAAAGY97F7/7P0uQkqwPBQGkK0oAbCyi7hLaeuUkqf8kKZs0MLvTMJxQWX6SO0/q0slyPbRkA6+I+NzW4XcffH6M3mmmW5eAOfjXjQxQIJI5NkBijt6NEgnf6DvLZ/hEY+8OwLQX8mmY4Ar3LayYolVEjY7jlg5Ansz7Q1rJjxg5ZW/68QAToTlWb35LgBJ5riXaCYuVk6tZqnDtJKsDqCLhqAey93hwz2ZYnivQBFHBMFr0PzR9oV+GKDZlbVGGJ7rJkDFRm061BwcT2u8xBtyod1moYv/bc/vczkNcHaQpfawEqNmcC8YFkHZHyZxjlj0wijARiQ1yR5ZZOh3pzzjKdATUK9C/7oaNFYYP66zu8XEz";

        /*
         * We also indicate which camera on the RC that we wish to use.
         * Here we chose the back (HiRes) camera (for greater range), but
         * for a competition robot, the front camera might be more convenient.
         */
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        /**
         * Load the data set containing the VuMarks for Relic Recovery. There's only one trackable
         * in this data set: all three of the VuMarks in the game were created from this one template,
         * but differ in their instance id information.
         * @see VuMarkInstanceId
         */
        VuforiaTrackables relicTrackables;
        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary


        relicTrackables.activate();
        int _counter=0;

        while (opModeIsActive()) {
            _counter++;

            /**
             * See if any of the instances of {@link relicTemplate} are currently visible.
             * {@link RelicRecoveryVuMark} is an enum which can have the following values:
             * UNKNOWN, LEFT, CENTER, and RIGHT. When a VuMark is visible, something other than
             * UNKNOWN will be returned by {@link RelicRecoveryVuMark#from(VuforiaTrackable)}.
             */
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                /* Found an instance of the template. In the actual game, you will probably
                 * loop until this condition occurs, then move on to act accordingly depending
                 * on which VuMark was visible. */
                telemetry.addData("VuMark", "%s visible", vuMark);
                // custom code start
                if (vuMark != null) {
                    _placement = vuMark.toString();
                    System.out.print("Placement value from the VuForia is " + _placement);
                }

            } else {
//                telemetry.addData("VuMark", "not visible");
            }

            telemetry.addData("VuMark",_placement);
            telemetry.update();
            if(_counter ==  10000)
                break;
        }
        return _placement;
    }
    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
}