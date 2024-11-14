package org.firstinspires.ftc.teamcode.Hardware;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class holonomicHardware {
    public DcMotor frontLeft = null;
    public DcMotor frontRight = null;
    public DcMotor backLeft = null;
    public DcMotor backRight = null;
    private double ticksPerRotationFL;
    private double ticksPerRotationFR;
    private double ticksPerRotationBL;
    private double ticksPerRotationBR;



    // get wheel motors from robot hardware config
    public void init(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.dcMotor.get("frontLeftMotor");
        frontRight = hardwareMap.dcMotor.get("frontRightMotor");
        backLeft = hardwareMap.dcMotor.get("backLeftMotor");
        backRight = hardwareMap.dcMotor.get("backRightMotor");
        ticksPerRotationFL = frontLeft.getMotorType().getTicksPerRev();
        ticksPerRotationFR = frontRight.getMotorType().getTicksPerRev();
        ticksPerRotationBL = backLeft.getMotorType().getTicksPerRev();
        ticksPerRotationBR = backRight.getMotorType().getTicksPerRev();
       /* frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }


    // set the direction of all motors
    public void setMotorDirection(DcMotorSimple.Direction flDirection, DcMotorSimple.Direction frDirection, DcMotorSimple.Direction blDirection, DcMotorSimple.Direction brDirection) {
        frontLeft.setDirection(flDirection);
        frontRight.setDirection(frDirection);
        backLeft.setDirection(blDirection);
        backRight.setDirection(brDirection);
    }
    public void setMotorDirection(DcMotorSimple.Direction direction) {
        frontLeft.setDirection(direction);
        frontRight.setDirection(direction);
        backLeft.setDirection(direction);
        backRight.setDirection(direction);
    }

    // set power of all motors
    public void setMotorPower(double flPower, double frPower, double blPower, double brPower) {
        frontLeft.setPower(flPower);
        frontRight.setPower(frPower);
        backLeft.setPower(blPower);
        backRight.setPower(brPower);
    }
    public void setMotorPower(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }

    public double getMotorRotationsFl() {

        return frontLeft.getCurrentPosition() / ticksPerRotationFL;
    }
    public double getMotorRotationsFr() {
        return frontRight.getCurrentPosition() / ticksPerRotationFR;
    }
    public double getMotorRotationsBl() {
        return backLeft.getCurrentPosition() / ticksPerRotationBL;
    }
    public double getMotorRotationsBr() {
        return backRight.getCurrentPosition() / ticksPerRotationBR;
    }
}
