package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.Hardware.arm2Hardware;
import org.firstinspires.ftc.teamcode.Hardware.servoHardware2;
import org.firstinspires.ftc.teamcode.Hardware.holonomicHardware;

@TeleOp
public class holonomic2TeleOp extends OpMode {
    holonomicHardware drive = new holonomicHardware();
    arm2Hardware arms = new arm2Hardware();
    servoHardware2 servo = new servoHardware2();
    double linearSlideStaticPower = 0;
    double baseSpeed = 1; // This is the multiplier for the movement speed of the base
    static final double armSpeed = 1; // This will not be changed in-game

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
        arms.setMotorArmDirection(DcMotorSimple.Direction.FORWARD);
        arms.setMotorDDPDirection(DcMotorSimple.Direction.FORWARD);
        arms.setMotorDDP2Direction(DcMotorSimple.Direction.FORWARD);

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
        if (gamepad1.left_trigger > 0) {
            baseSpeed = 1 - gamepad1.left_trigger;
        } else if (gamepad1.right_trigger > 0) {
            baseSpeed = gamepad1.right_trigger + 1;
        } else {
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

        // Arm Movement
        if (gamepad2.dpad_up) {
            arms.setMotorArmPower(1);
        } else if (gamepad2.dpad_down) {
            arms.setMotorArmPower(-1);
        } else {
            arms.setMotorArmPower(0);
        }

        if (gamepad2.dpad_left) {
            arms.setMotorDDPPower(1);
            arms.setMotorDDP2Power(1);
        } else if (gamepad2.dpad_right) {
            arms.setMotorDDPPower(-1);
            arms.setMotorDDPPower(-1);
        } else {
            arms.setMotorDDPPower(-0.3);
            arms.setMotorDDP2Power(-0.3);

        }

        // Claw Control
        if (gamepad2.a) {
            servo.setPosition(1); // Open claw
        } else if (gamepad2.b) {
            servo.setPosition(0);
        }

        // Debugging Telemetry
        telemetry.addData("Base Speed", baseSpeed);
        telemetry.addData("Arm Power", arms.getMotorArmPower());
        telemetry.addData("Arm Rotations", arms.getMotorArmRotations());
        telemetry.addData("Claw Position", servo.getPosition());
        telemetry.update();
    }
}