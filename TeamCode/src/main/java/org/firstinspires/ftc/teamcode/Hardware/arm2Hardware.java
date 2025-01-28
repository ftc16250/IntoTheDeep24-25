package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class arm2Hardware {

    // Declare arm motors and linear slide motor
    public DcMotor leftArmMotor = null;
    public DcMotor rightArmMotor = null;
    public DcMotor linearSlideMotor = null;

    public enum Side {
        Left,
        Right,
        Both,
        None
    }

    // Initialize hardware
    public void init(HardwareMap hardwareMap) {
        leftArmMotor = hardwareMap.get(DcMotor.class, "leftArmMotor");
        rightArmMotor = hardwareMap.get(DcMotor.class, "rightArmMotor");
        linearSlideMotor = hardwareMap.get(DcMotor.class, "linearSlideMotor");

        // Set zero power behavior
        leftArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
linearSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reset encoders
        leftArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set motors to run without encoders
        leftArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        linearSlideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // Set motor directions
    public void setArmMotorsDirection(arm2Hardware.Side side, DcMotor.Direction direction) {
        if (side == arm2Hardware.Side.Left || side == arm2Hardware.Side.Both) {
            leftArmMotor.setDirection(direction);
        }
        if (side == arm2Hardware.Side.Right || side == arm2Hardware.Side.Both) {
            rightArmMotor.setDirection(direction);
        }
    }

    public void setLinearSlideMotorDirection(DcMotor.Direction direction) {
        linearSlideMotor.setDirection(direction);
    }

    // Set power for arm motors
    public void setArmMotorsPower(arm2Hardware.Side side, double power) {
        if (side == arm2Hardware.Side.Left || side == arm2Hardware.Side.Both) {
            leftArmMotor.setPower(power);
        }
        if (side == arm2Hardware.Side.Right || side == arm2Hardware.Side.Both) {
            rightArmMotor.setPower(power);
        }
    }

    // Set power for linear slide motor
    public void setLinearSlideMotorPower(double power) {
        linearSlideMotor.setPower(power);
    }

    // Get rotations of the arm motors
    public double getArmMotorsRotations(Side side) {
        if (side == Side.Left) {
            return leftArmMotor.getCurrentPosition();
        } else if (side == Side.Right) {
            return rightArmMotor.getCurrentPosition();
        } else {
            return (leftArmMotor.getPower() + rightArmMotor.getPower()) / 2.0;
        }
    }
    public double getArmMotorsPower(Side side) {
        if (side == Side.Left) {
            return leftArmMotor.getPower();
        } else if (side == Side.Right) {
            return rightArmMotor.getPower();
        }
            else {
                return (leftArmMotor.getCurrentPosition() + rightArmMotor.getCurrentPosition()) / 2.0;
            }
    }


    // Get power of the linear slide motor
    public double getLinearSlideMotorPower() {
        return linearSlideMotor.getPower();
    }
    public double getLinearSlideMotorRotations(){
        return linearSlideMotor.getCurrentPosition();
    }
}