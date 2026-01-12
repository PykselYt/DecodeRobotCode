package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Chassis {

    //???chached motors
    public static DcMotor FL, FR, BL, BR;


    public static void RRD(double x, double y, double r){

        double d = Math.max(Math.abs(x) + Math.abs(y) + Math.abs(r), 1);
        double fl, bl, fr, br;
        r*=-1;

        fl = (y + x + r) / d;
        bl = (y - x + r) / d;
        fr = (y - x - r) / d;
        br = (y + x - r) / d;

        FL.setPower(fl);
        FR.setPower(fr);
        BL.setPower(bl);
        BR.setPower(br);
    }

    public static void FRD(double x, double y, double r){
        double d = Math.max(Math.abs(x) + Math.abs(y) + Math.abs(r), 1);
        double fl, bl, fr, br;
        r*=-1;
    }

}
