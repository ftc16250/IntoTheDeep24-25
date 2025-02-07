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
public class OdometrySensorRead extends OpMode{
    holonomicHardware drive = new holonomicHardware();
    arm2Hardware arms = new arm2Hardware();
    servoHardware2 servo = new servoHardware2();
    SparkFunOTOS sparkfunOTOS;

    double x;
    double y;
    double h;

    double baseSpeed = 0.3;

    boolean step1Done;
    boolean step2Done;
    boolean step3Done;
    boolean step4Done;
    boolean step5Done;
    boolean step6Done;
    boolean step7Done;
    boolean step8Done;
    boolean step9Done;
    boolean step10Done;

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

    public void loop() {
        SparkFunOTOS.Pose2D pos = sparkfunOTOS.getPosition();
        x = -pos.y;
        y = pos.x;
        h = -pos.h;

        telemetry.addData("X (inch)", x);
        telemetry.addData("Y (inch)", y);
        telemetry.addData("Heading (degrees)", h);

        if(h == 90){

        }
    }

}
