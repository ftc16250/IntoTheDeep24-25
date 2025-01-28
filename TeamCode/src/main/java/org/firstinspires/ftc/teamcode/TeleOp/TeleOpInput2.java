package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp
public class TeleOpInput2 extends OpMode {
    public float LinearSlideAxis;
    public boolean ClawButton;
    public float ArmAxis;

    // Base
    public float FowardSpeed;
    public float StrafeSpeed;
    public float RotationSpeed;
    public float BaseBoost;
    public float BaseBrakes;

    @Override
    public void init(){
        SetInputs();
    }

    @Override
    public void loop(){
        SetInputs();
    }

    private void SetInputs(){
        LinearSlideAxis = gamepad2.right_trigger;
        ClawButton = gamepad2.x;
        ArmAxis = gamepad2.left_trigger;
        // Base
        FowardSpeed = gamepad1.left_stick_y;
        StrafeSpeed = gamepad1.left_stick_x;
        RotationSpeed = gamepad1.right_stick_x;
        BaseBoost = gamepad1.right_trigger;
        BaseBrakes = gamepad1.left_trigger;
    }
}
