package org.firstinspires.ftc.teamcode.Hardware;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.opencv.core.Mat;

public class servoHardware {
    public Servo clawServo;
    public Servo armServo;
    public void init(HardwareMap hardwareMap) {

        clawServo = hardwareMap.get(Servo.class, "ClawServo");
        armServo = hardwareMap.get(Servo.class, "ArmServo");
    }

    public static double mapValue(double value) {
        double oldMin = 0.0;
        double oldMax = 1.0;
        double newMin = -180.0;
        double newMax = 180.0;

        return (value - oldMin) * (newMax - newMin) / (oldMax - oldMin) + newMin;
    }

    // Inverse: Map from [-180, 180] back to [0, 1]
    public static int inverseMapValue(double value) {
        double oldMin = -180.0;
        double oldMax = 180.0;
        double newMin = 0.0;
        double newMax = 1.0;
        double rawReturnValue = (value - oldMin) * (newMax - newMin) / (oldMax - oldMin) + newMin;
        return (int)Math.round(rawReturnValue);
    }
    public void setClawAngle(int angle) {
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(mapValue(angle));
    }

    public void setArmAngle(int angle) {
        armServo.setDirection(Servo.Direction.FORWARD);
        armServo.setPosition(mapValue(angle));
    }

    public int getClawAngle(){
        double receivedPosition = clawServo.getPosition();
        return inverseMapValue(receivedPosition);
    }

    public  void addClawAngle(int angle){
        setClawAngle(getClawAngle()+angle);
    }
}