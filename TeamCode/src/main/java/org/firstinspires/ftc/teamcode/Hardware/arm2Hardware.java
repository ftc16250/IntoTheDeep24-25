package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TeleOp.holonomic2TeleOp;

public class arm2Hardware {

    // region Variables
    private DcMotor linearSlideMotor; // Linear slide motor
    private DcMotor armMotorLeft;
    private DcMotor armMotorRight;

    private double ticksPerRotationLinearSlide;
    private double ticksPerRotationLeft; // Ticks Per Rotation for the Right Motor Arm
    private double ticksPerRotationRight; // Ticks Per Rotation for the Right Motor Arm
    private holonomic2TeleOp _holonomic2TeleOp;

    public enum Side {
        Left,
        Right,
        Both,
        None
    }
// endregion
    public void init(HardwareMap hardwareMap) {

        // Linear Slide Motor
        linearSlideMotor = hardwareMap.dcMotor.get("LinearSlideMotor");
        ticksPerRotationLinearSlide = linearSlideMotor.getMotorType().getTicksPerRev();
        linearSlideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Left Arm Motor
        armMotorLeft = hardwareMap.dcMotor.get("ArmMotorLeft");
        ticksPerRotationLeft = armMotorLeft.getMotorType().getTicksPerRev();
        armMotorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Right Arm Motor
        armMotorRight = hardwareMap.dcMotor.get("ArmMotorRight");
        ticksPerRotationRight = armMotorRight.getMotorType().getTicksPerRev();
        armMotorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // region Linear Slide Functions
    public void setLinearSlideMotorDirection(DcMotorSimple.Direction direction) {
        linearSlideMotor.setDirection(direction);
    }

    public void setLinearSlideMotorPower(double power) {
        linearSlideMotor.setPower(power);
    }

    public double getLinearSlideMotorPower() {
        return linearSlideMotor.getPower(); // Retrieves the current power of the motorArm
    }

    public double getTicksPerRotationLinearSlide() {

        return linearSlideMotor.getCurrentPosition() / ticksPerRotationLinearSlide;
    }
// endregion

    // region Arm Motors Functions

    // Set the direction any Arm Motor (can do both at once)
    public void setArmMotorsDirection(holonomic2TeleOp.Side side, DcMotorSimple.Direction direction) {
        switch (side) {
            case Both:
                armMotorLeft.setDirection(direction);
                armMotorRight.setDirection(direction);
            case Left:
                armMotorLeft.setDirection(direction);
            case Right:
                armMotorRight.setDirection(direction);
            default:

        }

    }

    // Set the power of any Arm Motor (can do both at once)
    public void setArmMotorsPower(holonomic2TeleOp.Side side, double power) {
        switch (side) {
            case Left:
                armMotorLeft.setPower(power);
            case Right:
                armMotorRight.setPower(power);
            case Both:
                armMotorLeft.setPower(power);
                armMotorRight.setPower(power);
        }
    }

    // Get the power of an Arm Motor
    public double getArmMotorsPower(holonomic2TeleOp.Side side) {
        switch (side) {
            case Left:
                return armMotorLeft.getPower();
            case Right:
                return armMotorRight.getPower();
            default:
                return 0; // If no parameter is passed through
        }
    }

    // Get the rotation position an Arm Motor
    public double getArmMotorsRotations(holonomic2TeleOp.Side side) {
        switch (side) {
            case Left:
                return armMotorLeft.getCurrentPosition() / ticksPerRotationLeft;
            case Right:
                return armMotorRight.getCurrentPosition() / ticksPerRotationRight;
            default:
                return 0; // If nothing is passed through the parameters
        }

    }

    // endregion
}