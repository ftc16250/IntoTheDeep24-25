package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Hardware.arm2Hardware;
import org.firstinspires.ftc.teamcode.Hardware.servoHardware2;
import org.firstinspires.ftc.teamcode.Hardware.holonomicHardware;

@TeleOp
public class holonomic2TeleOp extends OpMode {

    // region New Classes
    holonomicHardware drive = new holonomicHardware();
    arm2Hardware arms = new arm2Hardware();
    servoHardware2 servo = new servoHardware2();
    TeleOpInput2 Input = new TeleOpInput2();

    public enum Side {
        Left,
        Right,
        Both,
        None
    }
    // endregion

    // region Values

    double baseSpeed = 1; // This is the multiplier for the movement speed of the base
    double armSpeed = 0.01;
    double linearSlideSpeed = 0.01;

    double armMaxRotatation = 0.37;
    double armMinRotatation = 0.1;
    double linearSlideMaxRotation = 5;
    double linearSlideMinRotation = 0.1;

    //static final double maxArmRotation = 342; // Maximum allowed arm rotation
    // endregion

    @Override
    public void init() {
        // Initialize drive hardware
        drive.init(hardwareMap);
        drive.setMotorDirection(
                DcMotorSimple.Direction.FORWARD,
                DcMotorSimple.Direction.FORWARD,
                DcMotorSimple.Direction.FORWARD,
                DcMotorSimple.Direction.FORWARD
        );

        // Initialize arm hardware
        arms.init(hardwareMap);
        arms.setArmMotorsDirection(arm2Hardware.Side.Right, DcMotorSimple.Direction.FORWARD);
        arms.setArmMotorsDirection(arm2Hardware.Side.Left, DcMotorSimple.Direction.REVERSE);
        arms.setLinearSlideMotorDirection(DcMotorSimple.Direction.REVERSE);

        // Initialize servo hardware
        servo.init(hardwareMap);
        servo.setPosition(0);

        // Debugging telemetry
        telemetry.addData("Drive Initialized", "Success");
        telemetry.addData("Arm Initialized", "Success");
        telemetry.addData("Servo Initialized", "Success");
        telemetry.update();
    }

    protected void MoveBase(double fl, double fr, double bl, double br) {
        drive.setMotorPower(fl * baseSpeed, fr * baseSpeed, bl * baseSpeed, br * baseSpeed);
    }

    @Override
    public void loop() {
        // Base Speed Adjustment
        if (Input.BaseBrakes > 0) {
            baseSpeed = 1 - gamepad1.left_trigger;
        } else {
            baseSpeed = 1;
        }

        // Base Movement
        double forwardSpeed = gamepad1.left_stick_y;
        double strafeSpeed = gamepad1.right_stick_x;
        double spinSpeed = gamepad1.left_stick_x;

        if (forwardSpeed != 0) {
            MoveBase(forwardSpeed, -forwardSpeed, forwardSpeed, -forwardSpeed);
        } else if (strafeSpeed != 0) {
            MoveBase(strafeSpeed, -strafeSpeed, -strafeSpeed, strafeSpeed);
        } else if (spinSpeed != 0) {
            MoveBase(-spinSpeed, -spinSpeed, -spinSpeed, -spinSpeed);
        } else {
            MoveBase(0, 0, 0, 0);
        }

        // Linear Slide Arm Movement
        double linearSlidePosition = arms.getLinearSlidePosition();
        if (gamepad2.left_stick_y > 0 && linearSlidePosition < linearSlideMaxRotation) {
            //arms.setLinearSlideMotorPower(gamepad2.left_stick_y);
            arms.SetLinearMotorPosition(linearSlideMaxRotation, -gamepad2.left_stick_y);

        } else if (gamepad2.left_stick_y < 0 && linearSlidePosition > linearSlideMinRotation) {

            arms.SetLinearMotorPosition(linearSlideMinRotation, -gamepad2.left_stick_y);

        } else {
            arms.SetLinearMotorPosition(linearSlidePosition, 1);
        }

        if (gamepad2.right_trigger>0){
            arms.setArmMotorsPower(arm2Hardware.Side.Right, gamepad2.right_trigger);
            arms.setArmMotorsPower(arm2Hardware.Side.Left, -gamepad2.right_trigger);
        } else if (gamepad2.left_trigger>0) {
            arms.setArmMotorsPower(arm2Hardware.Side.Right, -1);
            arms.setArmMotorsPower(arm2Hardware.Side.Left, 1);
        }
        arms.setArmMotorsPower(arm2Hardware.Side.Right, 0.2);
        arms.setArmMotorsPower(arm2Hardware.Side.Left, -0.2);

// region Encoder Arms
        // Arm Motors Movement with Rotation Limit
/*
        double armPosition = arms.getArmMotorPosition(arm2Hardware.Side.Both);
        boolean over = (armPosition > 0.4);
if (gamepad2.right_trigger>0 && !over){
arms.SetArmsMotorPosition(arm2Hardware.Side.Both, armMaxRotatation, gamepad2.right_trigger);
}

if (gamepad2.left_trigger>0 && armPosition > armMinRotatation) {
arms.SetArmsMotorPosition(arm2Hardware.Side.Both, armMinRotatation, gamepad2.left_trigger);
}

if(over){
    arms.SetArmsMotorPosition(arm2Hardware.Side.Both, armMinRotatation, gamepad2.left_trigger);
}else if (gamepad2.right_trigger == 0 && gamepad2.left_trigger == 0)
{
    arms.SetArmsMotorPosition(arm2Hardware.Side.Both, armMaxRotatation, gamepad2.right_trigger);
}
/*
if (gamepad2.left_trigger>0 && armPosition > armMinRotatation) {
    arms.SetArmsMotorPosition(arm2Hardware.Side.Both, armMinRotatation, gamepad2.right_trigger);
}

if(gamepad2.left_trigger == 0 && gamepad2.right_trigger == 0)
{
    arms.SetArmsMotorPosition(arm2Hardware.Side.Both, armMaxRotatation, gamepad2.right_trigger);
}

telemetry.addData("pos", armPosition);
telemetry.addData("right trigger", gamepad2.right_trigger);
telemetry.addData("left trigger", gamepad2.left_trigger);
if(armPosition > 0){
    //telemetry.addData("Over!", armPosition);
   // arms.SetArmsMotorPosition(arm2Hardware.Side.Both, armPosition, 0);
}
*/

/*if (leftRotations<maxArmRotation){
    arms.setArmMotorsPower(arm2Hardware.Side.Right, 0.2);
    arms.setArmMotorsPower(arm2Hardware.Side.Left, -0.2);
}
 */
// endregion
        // Claw Control
        if (gamepad2.x) {
            servo.setPosition(0.3); // Closed position
        } else {
            servo.setPosition(1); // Open position
        }

        if(gamepad2.b && gamepad2.a){
            arms.ResetArmMotorPosition(arm2Hardware.Side.Both);
            arms.ResetLinearSlideMotorPosition();
        }

        // Debugging Telemetry
        telemetry.addData("Base Speed", baseSpeed);
        telemetry.addData("Arm Position Both", arms.getArmMotorPosition(arm2Hardware.Side.Both));
        telemetry.addData("Arm Position Left", arms.getArmMotorPosition(arm2Hardware.Side.Left));
        telemetry.addData("Arm Position Right", arms.getArmMotorPosition(arm2Hardware.Side.Right));
        telemetry.addData("Linear Slide Position", arms.getLinearSlidePosition());
        telemetry.addData("Claw Position", servo.getPosition());
        telemetry.update();
    }
}
