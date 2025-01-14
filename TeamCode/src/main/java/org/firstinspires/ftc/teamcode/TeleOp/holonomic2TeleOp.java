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
    static final double armSpeed = 1; // This will not be changed in-game
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
        double forwardSpeed = Input.FowardSpeed;
        double strafeSpeed = Input.StrafeSpeed;
        double spinSpeed = Input.RotationSpeed;

        if (forwardSpeed != 0) {
            MoveBase(forwardSpeed, -forwardSpeed, forwardSpeed, -forwardSpeed);
        } else if (strafeSpeed != 0) {
            MoveBase(strafeSpeed, -strafeSpeed, -strafeSpeed, strafeSpeed);
        } else if (spinSpeed != 0) {
            MoveBase(-spinSpeed, -spinSpeed, -spinSpeed, -spinSpeed);
        } else {
            MoveBase(0, 0, 0, 0);
        }

        // Arm Movement
        if(Input.LinearSlideAxis > 0){
            arms.setLinearSlideMotorPower(Input.LinearSlideAxis);
        }


        // Claw Control
        servo.setPosition(Input.ClawButton ? 0.7 : 0); // 0.7 Open

        // Debugging Telemetry
        telemetry.addData("Base Speed", baseSpeed);
        telemetry.addData("Arm Power", arms.getLinearSlideMotorPower());
        telemetry.addData("Arm Rotations", arms.getLinearSlideMotorPower());
        telemetry.addData("Claw Position", servo.getPosition());
        telemetry.update();
    }
}