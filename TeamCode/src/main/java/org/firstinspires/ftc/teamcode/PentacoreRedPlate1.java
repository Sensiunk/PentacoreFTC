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
@Autonomous(name = "PentacoreRedPlate1", group = "Blue Side")
//@Disabled
public class PentacoreRedPlate1 extends LinearOpMode
{

    ColorSensor colorSensorLeft;    // Hardware Device Object
    Servo colorServoLeft;
    DcMotor rightMotorOutside;
    DcMotor leftMotorOutside;
    DcMotor rightMotorInside;
    DcMotor leftMotorInside;
    DcMotor linearSlideRight;
    DcMotor linearSlideLeft;
    Servo rightServoClaw;
    Servo leftServoClaw;
    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode()
    {
        float hsvValues[] = {0F,0F,0F};
        final float values[] = hsvValues;

        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        // get a reference to our ColorSensor object.
        colorSensorLeft = hardwareMap.colorSensor.get("colorSensorLeft");
        colorServoLeft = hardwareMap.servo.get("colorServoLeft");
        rightMotorOutside = hardwareMap.dcMotor.get("rightMotorOutside");
        leftMotorOutside = hardwareMap.dcMotor.get("leftMotorOutside");
        rightMotorInside = hardwareMap.dcMotor.get("rightMotorInside");
        leftMotorInside = hardwareMap.dcMotor.get("leftMotorInside");
        linearSlideRight = hardwareMap.dcMotor.get("linearSlideRight");
        linearSlideLeft = hardwareMap.dcMotor.get("linearSlideLeft");
        rightServoClaw = hardwareMap.servo.get("rightServoClaw");
        leftServoClaw = hardwareMap.servo.get("leftServoClaw");
        linearSlideLeft.setDirection(DcMotor.Direction.REVERSE);
        rightMotorInside.setDirection(DcMotor.Direction.REVERSE);
        rightMotorOutside.setDirection(DcMotor.Direction.REVERSE);


        // wait for the start button to be pressed.
        waitForStart();

        //set variable for speed of robot moving when doing jewels mission
        double _move = 0.15;
        long _sleeptime = 350;
//        String _placement = dovuforia();
//        double _rightAngleValue = 0.23; // Defaulting a value incase the vuforia doesn't work
//        if (_placement.equals("RIGHT"))
//        {
//            _rightAngleValue = 0.25;
//        }
//        if (_placement.equals("CENTER"))
//        {
//            _rightAngleValue = 0.24;
//        }
//        if (_placement.equals("LEFT"))
//        {
//            _rightAngleValue = 0.23;
//        }

        //move sensor arm down
        colorServoLeft.setPosition(0.7);
        sleep(1000);
        colorServoLeft.setPosition(0.9);
        sleep(1000);
        leftServoClaw.setPosition(0.65);
        rightServoClaw.setPosition(0.05);
        LinearSlide(0.25);
        sleep(250);
        LinearSlideStop();
        sleep(1);
        StopDriving();
        sleep(1000);


        //begin to identify the color of jewel
        // convert the RGB values to HSV values.
        Color.RGBToHSV(colorSensorLeft.red() * 8, colorSensorLeft.green() * 8, colorSensorLeft.blue() * 8, hsvValues);

        // send the info back to driver station using telemetry function.
        telemetry.addData("Clear", colorSensorLeft.alpha());
        telemetry.addData("Red  ", colorSensorLeft.red());
        telemetry.addData("Green", colorSensorLeft.green());
        telemetry.addData("Blue ", colorSensorLeft.blue());
        telemetry.addData("Hue", hsvValues[0]);

        //move robot to knock off opposite alliance color ball
        //if senses red drive backward
        telemetry.log().add("$$$$$$$$$ $$$$$ color sensed red"+colorSensorLeft.red());
        telemetry.log().add("$$$$$$$$$ $$$$$ color sensed blue"+colorSensorLeft.blue());
        Log.d("$$$$$$ RED",""+colorSensorLeft.red());
        Log.d("$$$$$$BLUE",""+colorSensorLeft.blue());

        if (colorSensorLeft.red() > colorSensorLeft.blue())
        {
            StopDriving();
            sleep(1000);
            colorServoLeft.setPosition(0.85);
            DriveBackward(-_move);
            sleep(_sleeptime);
            colorServoLeft.setPosition(0.1);
            DriveBackward(_move);
            sleep(_sleeptime+300);
        }
        //if senses blue drive forward
        else if (colorSensorLeft.blue() > colorSensorLeft.red() )
        {
            StopDriving();
            sleep(1000);
            colorServoLeft.setPosition(0.85);
            DriveBackward(_move);
            sleep(_sleeptime);
            colorServoLeft.setPosition(0.1);
            DriveBackward(-_move);
            sleep(_sleeptime+300);
        }
        else
        {
            //raise sensor arm up if no color is sensed
            colorServoLeft.setPosition(0.1);
            sleep(100);
        }
        //turn right and move towards the crypto box
        StopDriving();
        sleep(1000);
        RightTurn(0.24);
        sleep(125);
        StopDriving();
        sleep(1000);
        DriveForward(0.5);
        sleep(100);
        //Drop linear slide down to place cube on ground
        leftServoClaw.setPosition(0.0);
        rightServoClaw.setPosition(0.6);
        DriveForward(0.5);
        sleep(1500);
        DriveBackward(0.25);
        sleep(50);

        StopDriving();


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
    public void LinearSlide(double power)
    {
        linearSlideRight.setPower(power);
        linearSlideLeft.setPower(power);
    }
    public void LinearSlideStop()
    {
        linearSlideLeft.setPower(0);
        linearSlideRight.setPower(0);
    }
    //    private String dovuforia() {
//        String _placement = "CENTER";
//        /*
//         * To start up Vuforia, tell it the view that we wish to use for camera monitor (on the RC phone);
//         * If no camera monitor is desired, use the parameterless constructor instead (commented out below).
//         */
//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
//
//        /*
//         * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
//         * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
//         * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
//         * web site at https://developer.vuforia.com/license-manager.
//         *
//         * Vuforia license keys are always 380 characters long, and look as if they contain mostly
//         * random data. As an example, here is a example of a fragment of a valid key:
//         *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
//         * Once you've obtained a license key, copy the string from the Vuforia web site
//         * and paste it in to your code onthe next line, between the double quotes.
//         */
//        parameters.vuforiaLicenseKey = "ASYvpnj/////AAAAGY97F7/7P0uQkqwPBQGkK0oAbCyi7hLaeuUkqf8kKZs0MLvTMJxQWX6SO0/q0slyPbRkA6+I+NzW4XcffH6M3mmmW5eAOfjXjQxQIJI5NkBijt6NEgnf6DvLZ/hEY+8OwLQX8mmY4Ar3LayYolVEjY7jlg5Ansz7Q1rJjxg5ZW/68QAToTlWb35LgBJ5riXaCYuVk6tZqnDtJKsDqCLhqAey93hwz2ZYnivQBFHBMFr0PzR9oV+GKDZlbVGGJ7rJkDFRm061BwcT2u8xBtyod1moYv/bc/vczkNcHaQpfawEqNmcC8YFkHZHyZxjlj0wijARiQ1yR5ZZOh3pzzjKdATUK9C/7oaNFYYP66zu8XEz";
//
//        /*
//         * We also indicate which camera on the RC that we wish to use.
//         * Here we chose the back (HiRes) camera (for greater range), but
//         * for a competition robot, the front camera might be more convenient.
//         */
//        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
//        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
//
//        /**
//         * Load the data set containing the VuMarks for Relic Recovery. There's only one trackable
//         * in this data set: all three of the VuMarks in the game were created from this one template,
//         * but differ in their instance id information.
//         * @see VuMarkInstanceId
//         */
//        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
//        VuforiaTrackable relicTemplate = relicTrackables.get(0);
//        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
//
//
//        relicTrackables.activate();
//
//        while (opModeIsActive()) {
//
//            /**
//             * See if any of the instances of {@link relicTemplate} are currently visible.
//             * {@link RelicRecoveryVuMark} is an enum which can have the following values:
//             * UNKNOWN, LEFT, CENTER, and RIGHT. When a VuMark is visible, something other than
//             * UNKNOWN will be returned by {@link RelicRecoveryVuMark#from(VuforiaTrackable)}.
//             */
//            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
//            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
//
//                /* Found an instance of the template. In the actual game, you will probably
//                 * loop until this condition occurs, then move on to act accordingly depending
//                 * on which VuMark was visible. */
//                telemetry.addData("VuMark", "%s visible", vuMark);
//                // custom code start
//                if (vuMark != null) {
//                    _placement = vuMark.toString();
//                    System.out.print("Placement value from the VuForia is " + _placement);
//                    break;
//                }
////                // custom code end
////                /* For fun, we also exhibit the navigational pose. In the Relic Recovery game,
////                 * it is perhaps unlikely that you will actually need to act on this pose information, but
////                 * we illustrate it nevertheless, for completeness. */
////                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose();
////                telemetry.addData("Pose", format(pose));
////
////                /* We further illustrate how to decompose the pose into useful rotational and
////                 * translational components */
////                if (pose != null) {
////                    VectorF trans = pose.getTranslation();
////                    Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
////
////                    // Extract the X, Y, and Z components of the offset of the target relative to the robot
////                    double tX = trans.get(0);
////                    double tY = trans.get(1);
////                    double tZ = trans.get(2);
////
////                    // Extract the rotational components of the target relative to the robot
////                    double rX = rot.firstAngle;
////                    double rY = rot.secondAngle;
////                    double rZ = rot.thirdAngle;
//            } else {
//                telemetry.addData("VuMark", "not visible");
//            }
//
//            telemetry.update();
//        }
//        return _placement;
//    }
    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
}