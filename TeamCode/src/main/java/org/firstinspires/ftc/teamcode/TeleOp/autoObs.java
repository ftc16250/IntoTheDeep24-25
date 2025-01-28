package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Hardware.arm2Hardware;
import org.firstinspires.ftc.teamcode.Hardware.servoHardware2;
import org.firstinspires.ftc.teamcode.Hardware.holonomicHardware;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous
public class autoObs extends OpMode{
    holonomicHardware drive = new holonomicHardware();
    arm2Hardware arms = new arm2Hardware();
    servoHardware2 servo = new servoHardware2();
    SparkFunOTOS sparkfunOTOS;

    @Override
    public void init() {
        drive.init(hardwareMap);
        drive.setMotorDirection(
                DcMotorSimple.Direction.FORWARD,
                DcMotorSimple.Direction.FORWARD,
                DcMotorSimple.Direction.FORWARD,
                DcMotorSimple.Direction.FORWARD
        );
        arms.init(hardwareMap);
        arms.setLinearSlideMotorDirection(DcMotorSimple.Direction.REVERSE);
        arms.setArmMotorsDirection(arm2Hardware.Side.Both, DcMotorSimple.Direction.FORWARD);
        servo.init(hardwareMap);

        servo.setPosition(0);
        sparkfunOTOS = hardwareMap.get(SparkFunOTOS.class, "otos");
        configureOTOS();

    }
    private void configureOTOS() {
        sparkfunOTOS.setLinearUnit(DistanceUnit.INCH);
        sparkfunOTOS.setAngularUnit(AngleUnit.DEGREES);
        sparkfunOTOS.setOffset(new SparkFunOTOS.Pose2D(0, 0, 0));
        sparkfunOTOS.setLinearScalar(1.0);
        sparkfunOTOS.setAngularScalar(1.0);
        sparkfunOTOS.resetTracking();
        sparkfunOTOS.setPosition(new SparkFunOTOS.Pose2D(0,0,0));
        sparkfunOTOS.calibrateImu(255, false);
    }
    public void init_loop(){
        telemetry.addData("Samples left to calibrate", sparkfunOTOS.getImuCalibrationProgress());
    }
    public void loop(){
        SparkFunOTOS.Pose2D pos = sparkfunOTOS.getPosition();

        telemetry.addData("X (inch)", pos.x);
        telemetry.addData("Y (inch)", pos.y);
        telemetry.addData("Heading (degrees)", pos.h);

        Step1();
        if (pos.y >= 12){
            Step2();

        }
        if (arms.getArmMotorsRotations(arm2Hardware.Side.Both)>= 1234 /* get real rotations*/){
            servo.setPosition(1);
        }

    }

    void Step2(){
        drive.setMotorPower(0,0,0,0);
        arms.setArmMotorsPower(arm2Hardware.Side.Both, 1);
    }void Step1(){
        drive.setMotorPower(1,1,1,1);

    }

}
