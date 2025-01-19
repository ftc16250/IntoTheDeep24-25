package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Hardware.arm2Hardware;
import org.firstinspires.ftc.teamcode.Hardware.servoHardware2;
import org.firstinspires.ftc.teamcode.Hardware.holonomicHardware;

@TeleOp
public class holonomic2TeleOp extends OpMode {

    // region New Classes
    holonomicHardware drive = new holonomicHardware();
    arm2Hardware arms = new arm2Hardware();
    servoHardware2 servo = new servoHardware2();
    TeleOpInput2 Input = new TeleOpInput2();
    public enum Side{
        Left,
        Right,
        Both,
        None
    }
    // endregion

    // region Values
    double linearSlideStaticPower = 0;
    double baseSpeed = 1; // This is the multiplier for the movement speed of the base
    static final double armSpeed = 0; // This will not be changed in-game
    // endregion
    @Override
    public void init() {
        // Initialize drive hardware
        drive.init(hardwareMap);
        drive.setMotorDirection(
                DcMotorSimple.Direction.FORWARD,
                DcMotorSimple.Direction.FORWARD,
                DcMotorSimple.Direction.FORWARD,
                DcMotorSimple.Direction.FORWARD
        );

        // Initialize arm hardware
        arms.init(hardwareMap);
        arms.setArmMotorsDirection(Side.Both, DcMotorSimple.Direction.FORWARD);
        arms.setLinearSlideMotorDirection(DcMotorSimple.Direction.FORWARD);

        // Initialize servo hardware
        servo.init(hardwareMap);
        servo.setPosition(0);

        // Debugging telemetry
        telemetry.addData("Drive Initialized", "Success");
        telemetry.addData("Arm Initialized", "Success");
        telemetry.addData("Servo Initialized", "Success");
        telemetry.update();
    }

    protected void MoveBase(double fl, double fr, double bl, double br) {
        drive.setMotorPower(fl * baseSpeed, fr * baseSpeed, bl * baseSpeed, br * baseSpeed);
    }

    @Override
    public void loop() {
        // Base Speed Adjustment
        if (Input.BaseBrakes > 0) {
            baseSpeed = 1 - gamepad1.left_trigger;
        }else {
            baseSpeed = 1;
        }

        // Base Movement
        double forwardSpeed = gamepad1.left_stick_y;
        double strafeSpeed = gamepad1.right_stick_x;
        double spinSpeed = gamepad1.left_stick_x;

        if (forwardSpeed != 0) {
            MoveBase(forwardSpeed, -forwardSpeed, forwardSpeed, -forwardSpeed);
        } else if (strafeSpeed != 0) {
            MoveBase(strafeSpeed, -strafeSpeed, -strafeSpeed, strafeSpeed);
        } else if (spinSpeed != 0) {
            MoveBase(-spinSpeed, -spinSpeed, -spinSpeed, -spinSpeed);
        } else {
            MoveBase(0, 0, 0, 0);
        }

        // Linear Slide Arm Movement
        if(gamepad2.left_stick_y > 0){
            arms.setLinearSlideMotorPower(gamepad2.left_stick_y);
        } else if (gamepad2.left_stick_y<0) {
            arms.setLinearSlideMotorPower(gamepad2.left_stick_y);
        }else {
            arms.setLinearSlideMotorPower(0);
        }

        //Motor Arms Movement
        if(gamepad2.right_stick_x > 0){
            arms.setArmMotorsPower(Side.Both,gamepad2.right_stick_x);
        } else if (gamepad2.right_stick_x<0) {
            arms.setArmMotorsPower(Side.Both,gamepad2.right_stick_x);
        }else {
            arms.setArmMotorsPower(Side.Both,0);
        }


        // Claw Control
        if (gamepad2.x)
        servo.setPosition(0);
        else {
            servo.setPosition(0.7);
        }// 0.7 Open

        // Debugging Telemetry
        telemetry.addData("Base Speed", baseSpeed);
        telemetry.addData("Arm Power", arms.getLinearSlideMotorPower());
        telemetry.addData("Arm Rotations", arms.getArmMotorsRotations(Side.Left));
        telemetry.addData("Claw Position", servo.getPosition());
        telemetry.update();
    }
}