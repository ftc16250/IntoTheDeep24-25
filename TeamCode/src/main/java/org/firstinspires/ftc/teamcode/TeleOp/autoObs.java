package org.firstinspires.ftc.teamcode.TeleOp;

import android.renderscript.Script;

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
    enum MovementType{
        X,
        Y,
        H,
        XY,
        XH,
        YH
    }

    enum Direction{
        Left(-1),
        Right(1),
        Foward(1),
        Backward(-1);

        int value;
        Direction(int v){
            v = value;
        }

        public int getValue(){
            return value;
        }
    }
    class Step {
        int id;
        boolean done;
        double targetX;
        double targetY;
        double targetH;
        //MovementType moveTpye;

        public Step(int id, boolean done, double targetX, double targetY, double targetH) {
            this.id = id;
            this.done = done;
            this.targetX = targetX;
            this.targetY = targetY;
            this.targetH = targetH;
            //this.moveTpye = movementType;
        }


    }

    holonomicHardware drive = new holonomicHardware();
    arm2Hardware arms = new arm2Hardware();
    servoHardware2 servo = new servoHardware2();
    SparkFunOTOS sparkfunOTOS;

    double baseSpeed = 0.3;

    double x;
    double y;
    double h;

    int currentStepID;
    Step Step1 = new Step(1, false, 0, 0, -90); // h
    Step Step2 = new Step(1, false, 90, 0, -90); // x
    Step Step3 = new Step(1, false, 0, 0, 0); // h
    Step Step4 = new Step(1, false, 0, 24, 0); // y
    Step Step5 = new Step(1, false, 0, 0, 180); // h
    Step Step6 = new Step(1, false, 0, 0, 0); // y
    Step[] AllSteps = {Step1, Step2, Step3, Step4, Step5, Step6};


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

        x = -pos.y;
        y = pos.x;
        h = -pos.h;

        telemetry.addData("X (inch)", x);
        telemetry.addData("Y (inch)", y);
        telemetry.addData("Heading (degrees)", h);

// region Run Steps

        if(!Step1.done){
            Spin(Direction.Left, 1);
            if(pos.h <= Step1.targetH){
                StopBase();
                Step1.done = true;
                return;
            }
            return;
        }

        if(!Step2.done){
            MoveForwardBackward(Direction.Foward,1);
            if(pos.y >= Step2.targetY){
                StopBase();
                Step2.done = true;
                return;
            }
            return;
        }

        if(!Step3.done){
            Spin(Direction.Right, 1);
            if(pos.h >= Step3.targetH){
                StopBase();
                Step3.done = true;
                return;
            }
        }

        if(!Step3.done){
            Spin(Direction.Right, 1);
            if(pos.h >= Step3.targetH){
                StopBase();
                Step3.done = true;
                return;
            }
        }

        if(!Step4.done){
            MoveForwardBackward(Direction.Foward, 1);
            if(pos.y >= Step4.targetY){
                StopBase();
                Step4.done = true;
                return;
            }
        }

        if(!Step5.done){
            Spin(Direction.Left, 1);
            if(pos.h >= Step5.targetH){
                StopBase();
                Step5.done = true;
                return;
            }
        }

        if(!Step6.done){
            MoveForwardBackward(Direction.Backward, 1);
            if(pos.y <= Step6.targetY){
                StopBase();
                Step6.done = true;
                return;
            }
        }

// endregion
    }

// region Steps
    void Step1(){

    }


// endregion



// region Base Movement
    private void MoveForwardBackward(Direction direction, double forwardSpeed){ // Positive value Moves Foward, Negative value Moves Backwards
        forwardSpeed *= baseSpeed;
        forwardSpeed = Math.abs(forwardSpeed);
        forwardSpeed *= direction.getValue();
        MoveBase(-forwardSpeed, forwardSpeed, -forwardSpeed, forwardSpeed);
    }

    private void Strafe(Direction direction, double strafeSpeed){ // Negative value Strafes Left, Positive value Strafes Right
        strafeSpeed *= baseSpeed;
        strafeSpeed = Math.abs(strafeSpeed);
        strafeSpeed *= direction.getValue();
        MoveBase(strafeSpeed, -strafeSpeed, -strafeSpeed, strafeSpeed);
    }

    private void Spin(Direction direction, double spinSpeed){// Positive value Spins Right, Negative value Spins Left
        spinSpeed *= baseSpeed;
        spinSpeed = Math.abs(spinSpeed);
        spinSpeed *= direction.getValue();
        MoveBase(-spinSpeed, -spinSpeed, -spinSpeed, -spinSpeed);
    }

    private void StopBase(){
        MoveBase(0, 0, 0, 0);
    }

    // Backend
    protected void MoveBase(double fl, double fr, double bl, double br) {
        drive.setMotorPower(fl, fr, bl, br);
    }
    // endregion
}