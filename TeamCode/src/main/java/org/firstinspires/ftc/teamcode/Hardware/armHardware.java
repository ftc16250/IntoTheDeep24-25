package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class armHardware {
    private DcMotor motorArm;
    private double ticksPerRotationArm;
    public void init(HardwareMap hardwareMap){
        motorArm = hardwareMap.dcMotor.get("motorArm");
        ticksPerRotationArm = motorArm.getMotorType().getTicksPerRev();
        motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void setMotorDirection(DcMotorSimple.Direction direction){
        motorArm.setDirection(direction);

    }
    public void setMotorPower(double power){
        motorArm.setPower(power);
    }
    public double getMotorRotationsArm() {

        return motorArm.getCurrentPosition() / ticksPerRotationArm;
    }
}