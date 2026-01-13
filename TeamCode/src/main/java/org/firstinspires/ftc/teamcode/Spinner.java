package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Spinner {
    public static Servo pusher;
    public static CRServo SP1, SP2;
    public static DcMotor I1;

    public static double pushedPos=77, resetPos=103, idlePos=180, pusherPos=180;

    public static double kp=0.37, ki=0,kd=0.001;
    public static double P, I, D, tolerance=1, lastError=0, error=0, maxPower=1, minPower=0.05, deltaError=0;

    public static double offsetFromReset=0, offsetToShooter=0, ballRotate=120;

    public static double position, curPos;
    public static DcMotor encoder;

    public static double rotatePower=0;
    public static double inPower=0;

    public static boolean rotateDone=true, shooterPos=false;

    public static void josPuta(){pusherPos=pushedPos;}
    public static void susPuta(){pusherPos=idlePos;}
    public static void resetPuta(){pusherPos=resetPos;}

    public static void Intake(){inPower=1;}
    public static void stopIntake(){inPower=0;}
    public static boolean isRotatorDone(){return rotateDone;}

    public static void setupRotator(){
        encoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        position=offsetFromReset;
        shooterPos=false;
    }

    public static void nextBall(){position+=ballRotate;}
    public static void prevBall(){position-=ballRotate;}

    public static void switchPos(){
        if(shooterPos){
            shooterPos=false;
            position-=offsetToShooter;
        }else{
            shooterPos=true;
            position+=offsetToShooter;
        }
    }

    public static void setPower(double power){
        SP1.setPower(power);
        SP2.setPower(power);
    }

    public static double getCurrentRotatorPosition(){
        double relativeTicks = encoder.getCurrentPosition();
        double ticksPerTurretRev = 8192;
        return relativeTicks * 360 / ticksPerTurretRev;
    }


    public static void calcPower(){
        error=position-curPos;
        deltaError=error-lastError;
        P=kp*error;
        D=kd*deltaError;
        lastError=error;

        rotatePower=P+D;
        if(Math.abs(error)<tolerance) {
            rotatePower=0;
            rotateDone=true;
        }else rotateDone=false;

        rotatePower = Math.max(-1.0, Math.min(1.0, rotatePower));
        if(rotatePower>0 && rotatePower<minPower) rotatePower=minPower;
        if(rotatePower<0 && rotatePower>-minPower) rotatePower=-minPower;
    }



    public static void update(){
        curPos=getCurrentRotatorPosition();
        calcPower();
        setPower(rotatePower);
        pusher.setPosition(pusherPos);
        I1.setPower(inPower);
    }

}
