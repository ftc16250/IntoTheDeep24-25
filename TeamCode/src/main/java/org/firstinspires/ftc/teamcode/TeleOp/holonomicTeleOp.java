package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.armHardware;
import org.firstinspires.ftc.teamcode.Hardware.servoHardware;
import org.firstinspires.ftc.teamcode.Hardware.holonomicHardware;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
@TeleOp
public class holonomicTeleOp extends OpMode {
    holonomicHardware drive = new holonomicHardware();
    armHardware motorArm = new armHardware();
    servoHardware servos = new servoHardware();
    double linearSlideStaticPower = 0;
    double baseSpeed = 1; // This is the multiplier for the movement speed of the base
    static final double armSpeed = 1 ; // This will not be changed in game
    SparkFunOTOS sparkfunOTOS;
    @Override
    public void init() {
        drive.init(hardwareMap);
        drive.setMotorDirection(
                DcMotorSimple.Direction.FORWARD,
                DcMotorSimple.Direction.FORWARD,
                DcMotorSimple.Direction.FORWARD,
                DcMotorSimple.Direction.FORWARD
        );
        motorArm.init(hardwareMap);
        motorArm.setMotorDirection(
                DcMotorSimple.Direction.FORWARD);
        servos.init(hardwareMap);

        servos.armServo.setPosition(0);
        sparkfunOTOS = hardwareMap.get(SparkFunOTOS.class, "otos");
        configureOTOS();
    }

    protected void MoveBase(double fl, double fr, double bl, double br) {

        drive.setMotorPower(fl * baseSpeed, fr * baseSpeed, bl * baseSpeed, br * baseSpeed);
    }
    private void configureOTOS() {
        sparkfunOTOS.setLinearUnit(DistanceUnit.INCH);
        sparkfunOTOS.setAngularUnit(AngleUnit.DEGREES);
        sparkfunOTOS.setOffset(new SparkFunOTOS.Pose2D(0, 0, 0));
        sparkfunOTOS.setLinearScalar(1.0);
        sparkfunOTOS.setAngularScalar(1.0);
        sparkfunOTOS.resetTracking();
        sparkfunOTOS.setPosition(new SparkFunOTOS.Pose2D(0,0,0));
        sparkfunOTOS.calibrateImu(255, false);
    }

    @Override
    public void loop() {
        SparkFunOTOS.Pose2D pos = sparkfunOTOS.getPosition();
        telemetry.addData("X (inch)", pos.x);
        telemetry.addData("Y (inch)", pos.y);
        telemetry.addData("Heading (degrees)", pos.h);

        //region Set Base Speed
        if (gamepad1.left_trigger > 0) {
            baseSpeed = 1-gamepad1.left_trigger;
        }else if(gamepad1.right_trigger > 0)
        {
            baseSpeed =gamepad1.right_trigger+1;
        }else{
            baseSpeed = 1;
        }
        //endregion
        // test if game-pads work

        // region Base Movement
        if (gamepad1.left_stick_y > 0) {
            double fowardSpeed = gamepad1.left_stick_y;
            MoveBase(-fowardSpeed, fowardSpeed, -fowardSpeed, fowardSpeed);
        }
        if (gamepad1.left_stick_y < 0) {
            // check what happens when you set negative power
            double backSpeed = gamepad1.left_stick_y;
            MoveBase(-backSpeed, backSpeed, -backSpeed, backSpeed);
        }
        if (gamepad1.left_stick_x < 0) {
            double strafeLeftSpeed = gamepad1.left_stick_x;
            MoveBase(-strafeLeftSpeed, -strafeLeftSpeed, strafeLeftSpeed, strafeLeftSpeed);
            telemetry.addData("strafe", "left");
        }
        if (gamepad1.left_stick_x > 0) {

            double strafeRightSpeed = gamepad1.left_stick_x;
            MoveBase(-strafeRightSpeed, -strafeRightSpeed, strafeRightSpeed, strafeRightSpeed);
            telemetry.addData("strafe", "right");
        }
       /* if (gamepad1.right_bumper) {

            MoveBase(-1, -1, -1, -1);
        }
        if (gamepad1.left_bumper) {

            MoveBase(1, 1, 1, 1);
        }*/
        if(gamepad1.right_stick_x != 0) {
            double spinSpeed = gamepad1.right_stick_x;
            MoveBase(-spinSpeed, -spinSpeed, -spinSpeed, -spinSpeed);
        }
        // don't spin motor if nothing is pressed
        else {  MoveBase(0, 0, 0, 0);}
       // telemetry.addData("Ticks Per Rotation FrontLeft", drive.getMotorRotationsFl());
        //telemetry.addData("Ticks Per Rotation Front Right", drive.getMotorRotationsFr());
        //telemetry.addData("Ticks Per Rotation Back Left", drive.getMotorRotationsBl());
        ///telemetry.addData("Ticks Per Rotation Back Right", drive.getMotorRotationsBr());
//endregion

//region Arm Movement
        if(gamepad2.left_stick_y != 0){
            motorArm.setMotorPower(gamepad2.left_stick_y/3);

        }else {

            motorArm.setMotorPower(0);
        }


        // -----Arm-----
        /*if(gamepad2.right_trigger > 0 ){
            servos.addArmAngle(armSpeed);
            telemetry.addData("balls", "2");
        }else if(gamepad2.left_trigger > 0){
            servos.addArmAngle(-armSpeed);
        }*/

        servos.setArmAngle(gamepad2.right_trigger);
        // -----Claw-----
        if (gamepad2.x) {
            servos.setClawAngle(0.3);
        } else {
            servos.setClawAngle(1 );
        }
//endregion
    }
}