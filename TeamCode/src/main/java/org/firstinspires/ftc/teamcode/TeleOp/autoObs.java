package org.firstinspires.ftc.teamcode.TeleOp;

import android.renderscript.Script;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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
        Left,
        Right,
        Foward,
        Backward
    }
    class Step {
        int id;
        boolean done = false;
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
    Step Step1 = new Step(1, false, -10, 0, 0); // x
    Step Step2 = new Step(2, false, -10, 12, 0); // y
    Step Step3 = new Step(3, false, 0, 12, 0); // x
    Step Step4 = new Step(4, false, 0, 2, 0); // y
    Step Step5 = new Step(5, false, -10, 2, 0); // x
    Step Step6 = new Step(6, false, -10, 12, 0); // y
    Step Step7 = new Step(6, false, 5, 12, 0); // x
    Step Step8 = new Step(6, false, 0, 2, 0); // y
    Step Step9 = new Step(6, false, -10, 2, 0); // x
    Step Step10 = new Step(6, false, -10, 12, 0); // y
    Step Step11 = new Step(6, false, 10, 12, 0); // x
    Step Step12 = new Step(6, false, 10, 2, 0); // y
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
            telemetry.addLine("Step1 Started");
            Strafe(Direction.Left, 1);
            telemetry.addData("should be ", "moving");
            if(x <= Step1.targetX){
                telemetry.addData("should", "stop");
                StopBase();
                Step1.done = true;
                telemetry.addLine("Step1 ended");
                return;
            }
            return;
        }

        if(!Step2.done){
            telemetry.addLine("Step2 Started");

            MoveForwardBackward(Direction.Foward,1);
            if(y >= Step2.targetY){
                StopBase();
                Step2.done = true;
                telemetry.addLine("Step2 Done");
                return;
            }
            return;
        }

        if(!Step3.done){
            telemetry.addLine("Step3 Started");

            Strafe(Direction.Right,1);
            if(x >= Step3.targetX){
                StopBase();
                Step3.done = true;
                telemetry.addLine("Step3 Done");
                return;
            }
            return;
        }

        if(!Step4.done){
            telemetry.addLine("Step4 Started");

            MoveForwardBackward(Direction.Backward,1);
            if(y <= Step4.targetY){
                StopBase();
                Step4.done = true;
                telemetry.addLine("Step4 Done");
                return;
            }
            return;
        }

        if(!Step5.done){
            telemetry.addLine("Step5 Started");

            Strafe(Direction.Left,1);
            if(x <= Step5.targetX){
                StopBase();
                Step5.done = true;
                telemetry.addLine("Step5 Done");
                return;
            }
            return;
        }

        if(!Step6.done){
            telemetry.addLine("Step6 Started");

            MoveForwardBackward(Direction.Foward,1);
            if(y >= Step6.targetY){
                StopBase();
                Step6.done = true;
                telemetry.addLine("Step6 Done");
                return;
            }
            return;
        }

        if(!Step7.done){
            telemetry.addLine("Step7 Started");

            Strafe(Direction.Right,1);
            if(x >= Step7.targetX){
                StopBase();
                Step7.done = true;
                telemetry.addLine("Step7 Done");
                return;
            }
            return;
        }

        if(!Step8.done){
            telemetry.addLine("Step8 Started");

            MoveForwardBackward(Direction.Backward,1);
            if(y <= Step8.targetY){
                StopBase();
                Step8.done = true;
                telemetry.addLine("Step8 Done");
                return;
            }
            return;
        }

        if(!Step9.done){
            telemetry.addLine("Step9 Started");

            Strafe(Direction.Left,1);
            if(x <= Step9.targetX){
                StopBase();
                Step9.done = true;
                telemetry.addLine("Step9 Done");
                return;
            }
            return;
        }

        if(!Step10.done){
            telemetry.addLine("Step10 Started");

            MoveForwardBackward(Direction.Foward,1);
            if(y >= Step10.targetY){
                StopBase();
                Step10.done = true;
                telemetry.addLine("Step10 Done");
                return;
            }
            return;
        }

        if(!Step11.done){
            telemetry.addLine("Step11 Started");

            Strafe(Direction.Right,1);
            if(x >= Step11.targetX){
                StopBase();
                Step11.done = true;
                telemetry.addLine("Step11 Done");
                return;
            }
            return;
        }

        if(!Step12.done){
            telemetry.addLine("Step12 Started");

            MoveForwardBackward(Direction.Backward,1);
            if(y <= Step12.targetY){
                StopBase();
                Step12.done = true;
                telemetry.addLine("Step12 Done");
                return;
            }
            return;
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
        if(direction == Direction.Backward) {
        forwardSpeed *= -1;
        }
        MoveBase(-forwardSpeed, forwardSpeed, -forwardSpeed, forwardSpeed);
    }

    private void Strafe(Direction direction, double strafeSpeed){ // Negative value Strafes Left, Positive value Strafes Right
        strafeSpeed *= baseSpeed;
        strafeSpeed = Math.abs(strafeSpeed);
        if(direction == Direction.Left){
            strafeSpeed *= -1;
        }
        MoveBase(strafeSpeed, -strafeSpeed, -strafeSpeed, strafeSpeed);
    }

    private void Spin(Direction direction, double spinSpeed){// Positive value Spins Right, Negative value Spins Left
        spinSpeed *= baseSpeed;
        spinSpeed = Math.abs(spinSpeed);
        telemetry.addData("spinSpeed", spinSpeed);
        if(direction == Direction.Left){
            spinSpeed *=-1;
        }
        telemetry.addData("newSpinSpeed", spinSpeed);
        telemetry.addData("direction", direction);
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