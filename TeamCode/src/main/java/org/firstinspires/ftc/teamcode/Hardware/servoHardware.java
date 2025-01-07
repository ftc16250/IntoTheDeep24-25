package org.firstinspires.ftc.teamcode.Hardware;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.opencv.core.Mat;

public class servoHardware {
    public Servo clawServo;
    public Servo armServo;
    public void init(HardwareMap hardwareMap) {

        clawServo = hardwareMap.get(Servo.class, "ClawServo");
        armServo = hardwareMap.get(Servo.class, "ArmServo"); // Possible Issue
    }

    public double mapValue(double value) {
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
    public void setClawAngle(double angle) {
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(angle);

    }

    public void setArmAngle(double angle) {
        armServo.setDirection(Servo.Direction.FORWARD);
        armServo.setPosition(angle);
    }

    public double getClawAngle(){
        //double receivedPosition = clawServo.getPosition();
        //return inverseMapValue(receivedPosition);
        return clawServo.getPosition();
    }

    public double getArmAngle(){
        return armServo.getPosition();
    }

    public  void addClawAngle(double angle){
        //if(angle < 0 && getArmAngle() == )
        setClawAngle(getClawAngle()+angle);
    }
    public  void addArmAngle(double angle){
        setArmAngle(getArmAngle()+angle);
    }
}