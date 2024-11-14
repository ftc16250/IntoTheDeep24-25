package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.armHardware;
import org.firstinspires.ftc.teamcode.Hardware.servoHardware;
import org.firstinspires.ftc.teamcode.Hardware.holonomicHardware;
@TeleOp
public class holonomicTeleOp extends OpMode {
    holonomicHardware drive = new holonomicHardware();
    armHardware arm = new armHardware();
    servoHardware servos = new servoHardware();
    double baseSpeed = 1; // This is the multiplier for the movement speed of the base

    @Override
    public void init() {
        drive.init(hardwareMap);
        drive.setMotorDirection(
                DcMotorSimple.Direction.FORWARD,
                DcMotorSimple.Direction.FORWARD,
                DcMotorSimple.Direction.FORWARD,
                DcMotorSimple.Direction.FORWARD
        );
        arm.init(hardwareMap);
        arm.setMotorDirection(
                DcMotorSimple.Direction.FORWARD);
        servos.init(hardwareMap);


    }

    protected void MoveBase(int fl, int fr, int bl, int br) {

        drive.setMotorPower(fl * baseSpeed, fr * baseSpeed, bl * baseSpeed, br * baseSpeed);
    }

    @Override
    public void loop() {

        //region Set Base Speed
        if (gamepad1.b) {
            baseSpeed = 0.5;
        } else {
            baseSpeed = 1;
        }
        //endregion
        // test if game-pads work

        // region Base Movement
        if (gamepad1.left_stick_y > 0) {

            MoveBase(-1, 1, -1, 1);
        }
        if (gamepad1.left_stick_y < 0) {
            // check what happens when you set negative power
            MoveBase(1, -1, 1, -1);
        }
        if (gamepad1.right_stick_x > 0) {

            MoveBase(1, 1, -1, -1);
        }
        if (gamepad1.right_stick_x < 0) {

            MoveBase(-1, -1, 1, 1);
        }
        if (gamepad1.right_bumper) {

            MoveBase(-1, -1, -1, -1);
        }
        if (gamepad1.left_bumper) {

            MoveBase(1, 1, 1, 1);
        }

        // don't spin motor if nothing is pressed
        else MoveBase(0, 0, 0, 0);
        telemetry.addData("Ticks Per Rotation FrontLeft", drive.getMotorRotationsFl());
        telemetry.addData("Ticks Per Rotation Front Right", drive.getMotorRotationsFr());
        telemetry.addData("Ticks Per Rotation Back Left", drive.getMotorRotationsBl());
        telemetry.addData("Ticks Per Rotation Back Right", drive.getMotorRotationsBr());
//endregion

//region Arm Movement
        if (gamepad2.left_stick_y > 0) {
            arm.setMotorPower(1);
        } else if (gamepad2.left_stick_y < 0) {
            arm.setMotorPower(1);
        } else {
            arm.setMotorPower(0);
        }

        if (gamepad2.x) {
            servos.setClawPosition(1);
        } else if (gamepad2.a) {
            servos.setClawPosition(1);
        } else {
            servos.setClawPosition(0);
        }

        // -----Servo-----

        if (gamepad2.right_stick_y > 0) {
            servos.setArmPosition(1);
        } else if (gamepad2.right_stick_y < 0) {
            servos.setArmPosition(1);
        }
//endregion
    }
}