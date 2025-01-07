package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class arm2Hardware {
    private DcMotor motorArm;
    private double ticksPerRotationArm;
    private DcMotor motorDDP;
    private double ticksPerRotationDDP;
    private DcMotor motorDDP2;
    private double ticksPerRotationDDP2;

    public void init(HardwareMap hardwareMap) {
        motorArm = hardwareMap.dcMotor.get("motorArm");
        ticksPerRotationArm = motorArm.getMotorType().getTicksPerRev();
        motorArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorDDP = hardwareMap.dcMotor.get("DDP1");
        ticksPerRotationDDP = motorDDP.getMotorType().getTicksPerRev();
        motorDDP.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorDDP2 = hardwareMap.dcMotor.get("DDP1");
        ticksPerRotationDDP2 = motorDDP.getMotorType().getTicksPerRev();
        motorDDP2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setMotorArmDirection(DcMotorSimple.Direction direction) {
        motorArm.setDirection(direction);
    }

    public void setMotorArmPower(double power) {
        motorArm.setPower(power);
    }

    public double getMotorArmPower() {
        return motorArm.getPower(); // Retrieves the current power of the motorArm
    }

    public double getMotorArmRotations() {
        return motorArm.getCurrentPosition() / ticksPerRotationArm;
    }

    public void setMotorDDPDirection(DcMotorSimple.Direction direction) {
        motorDDP.setDirection(direction);
    }

    public void setMotorDDPPower(double power) {
        motorDDP.setPower(power);
    }

    public double getMotorDDPPower() {
        return motorDDP.getPower(); // Retrieves the current power of the motorElbow
    }

    public double getMotorDDPRotations() {
        return motorDDP.getCurrentPosition() / ticksPerRotationDDP2;
    }
    public void setMotorDDP2Direction(DcMotorSimple.Direction direction) {
        motorDDP2.setDirection(direction);
    }

    public void setMotorDDP2Power(double power) {
        motorDDP2.setPower(power);
    }

    public double getMotorDDP2Power() {
        return motorDDP2.getPower(); // Retrieves the current power of the motorElbow
    }

    public double getMotorDDP2Rotations() {
        return motorDDP2.getCurrentPosition() / ticksPerRotationDDP2;
    }
}