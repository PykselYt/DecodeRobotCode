package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Shooter {

    public static DcMotor M1, M2, rotator;
    public static Servo pitcher;


    public static double maxAngle=355;
    public static double ShotPower=1, StopPower=0, curPower=StopPower, curRotPow;
    public static double zeroPitch=40.5, pitchUp=55.6, pitchDown=40.5, curPitch=40.5;


    public static void PowerUp(){curPower=ShotPower;}
    public static void PowerDown(){curPower=StopPower;}
    public static void SetPower(double pow){curPower=pow;}
    public static void ResetPitch(){curPitch=zeroPitch;}
    public static void FarPitch(){curPitch=pitchUp;}
    public static void ClosePitch(){curPitch=pitchDown;}
    public static void SetAngle(double angle){curPitch=angle;}
    public static double GetPitch(){return curPitch;}
    public static void powerRotator(double power){curRotPow=power;}

    public static void update(){
        pitcher.setPosition(curPitch/maxAngle);
        M1.setPower(curPower);
        M2.setPower(curPower);
        rotator.setPower(curRotPow);
    }
}
