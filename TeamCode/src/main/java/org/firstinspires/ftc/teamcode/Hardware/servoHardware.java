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
    public void setClawAngle(int angle) {
        clawServo.setDirection(Servo.Direction.FORWARD);
        angle += 180;
        double position = (double) (1/360) * angle;
        clawServo.setPosition(position);
    }

    public void setArmAngle(int angle) {
        armServo.setDirection(Servo.Direction.FORWARD);
        angle += 180;
        double position = (double) (1/360) * angle;
        armServo.setPosition(position);
    }

    public int getClawAngle(){
        double receivedPosition = clawServo.getPosition();
        double newAngle = (1/360) * receivedPosition;
        return (int)newAngle;
    }

    public  void addClawAngle(int angle){
        setClawAngle(getClawAngle()+angle);
    }
}