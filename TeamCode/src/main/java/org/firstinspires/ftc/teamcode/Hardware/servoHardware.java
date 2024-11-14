package org.firstinspires.ftc.teamcode.Hardware;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class servoHardware {
    public Servo clawServo;
    public Servo armServo;
    public void init(HardwareMap hardwareMap) {

        clawServo = hardwareMap.get(Servo.class, "ClawServo");
        armServo = hardwareMap.get(Servo.class, "ArmServo");
    }
    public void setClawPosition(double position) {
        clawServo.setDirection(Servo.Direction.REVERSE);

        clawServo.setPosition(position);
    }

    public void setArmPosition(double position) {
        armServo.setDirection(Servo.Direction.REVERSE);

        armServo.setPosition(position);
    }
}