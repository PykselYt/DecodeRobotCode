package org.firstinspires.ftc.teamcode;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Chassis {

    public static DcMotor FL, FR, BL, BR;
    public static  IMU imu;

    public static void ROD(double x, double y, double r){

        double d = Math.max(Math.abs(x) + Math.abs(y) + Math.abs(r), 1);
        double fl, bl, fr, br;

        fl = (y + x + r) / d;
        bl = (y - x + r) / d;
        fr = (y - x - r) / d;
        br = (y + x - r) / d;

        FL.setPower(fl);
        FR.setPower(fr);
        BL.setPower(bl);
        BR.setPower(br);
    }

    public static void FOD(double x, double y, double r){
        double orientation = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double YFieldOriented = y * cos(orientation) - x * sin(orientation);
        double XFieldOriented = y * sin(orientation) + x * cos(orientation);

        double maxPow = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(r), 1);

        double fl = (YFieldOriented + XFieldOriented + r) / maxPow;
        double fr = (YFieldOriented - XFieldOriented - r) / maxPow;
        double bl = (YFieldOriented - XFieldOriented + r) / maxPow;
        double br = (YFieldOriented + XFieldOriented - r) / maxPow;

        FL.setPower(fl);
        FR.setPower(fr);
        BL.setPower(bl);
        BR.setPower(br);
    }

}
