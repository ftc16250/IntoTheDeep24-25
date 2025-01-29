package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class arm2Hardware {

    // Declare arm motors and linear slide motor
    public DcMotor leftArmMotor = null;
    public DcMotor rightArmMotor = null;
    public DcMotor linearSlideMotor = null;
    public static double fullRotationTicks = 751.8;
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

        // Set motors to run using encoders
        leftArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //leftArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //rightArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        linearSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Reset encoders
        //leftArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       // rightArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //linearSlideMotor.setTargetPosition((int)fullRotationTicks);
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

    public void SetArmsMotorPosition(arm2Hardware.Side side, double rotations, double power){
        double newTicks = fullRotationTicks*rotations;

        if (side == arm2Hardware.Side.Left || side == arm2Hardware.Side.Both) {
            leftArmMotor.setPower(power);
            leftArmMotor.setTargetPosition((int)newTicks);
            leftArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        }
        if (side == arm2Hardware.Side.Right || side == arm2Hardware.Side.Both) {

            rightArmMotor.setPower(power);
            rightArmMotor.setTargetPosition((int)newTicks);
            rightArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    public void SetLinearMotorPosition(double rotations, double power){
        double newTicks = fullRotationTicks*rotations;

        linearSlideMotor.setPower(power);
        linearSlideMotor.setTargetPosition((int)newTicks);
        linearSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void StopLinearSlide(){

    }

    public void ResetArmMotorPosition(arm2Hardware.Side side){
        if (side == arm2Hardware.Side.Left || side == arm2Hardware.Side.Both) {
            leftArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        if (side == arm2Hardware.Side.Right || side == arm2Hardware.Side.Both) {
            rightArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }
    public void ResetLinearSlideMotorPosition(){
        linearSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public double getArmMotorPosition(arm2Hardware.Side side){
        if (side == arm2Hardware.Side.Left) {
            return  (leftArmMotor.getCurrentPosition()/fullRotationTicks);
        }else if (side == arm2Hardware.Side.Right) {
            return (double) (rightArmMotor.getCurrentPosition()/fullRotationTicks);
        }else if (side == Side.Both){
            double averagePos = (double)(Math.abs(leftArmMotor.getCurrentPosition()) + Math.abs(rightArmMotor.getCurrentPosition()))/2;
            return (double)(averagePos/fullRotationTicks);
        }else{
            return 0;
        }
    }

    public double getLinearSlidePosition(){
        return (double)(linearSlideMotor.getCurrentPosition()/fullRotationTicks);
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