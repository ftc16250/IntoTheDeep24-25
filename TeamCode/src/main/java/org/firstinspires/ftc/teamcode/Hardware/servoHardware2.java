package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class servoHardware2 {
    public Servo servo;

    public void init(HardwareMap hardwareMap) {

        servo = hardwareMap.get(Servo.class, "Claw");
    }
    public void setPosition(double position) {
        servo.setDirection(Servo.Direction.FORWARD);

        servo.setPosition(position);
    }
    public double getPosition() {
        return servo.getPosition(); // Retrieves the current power of the motorElbow
    }
}