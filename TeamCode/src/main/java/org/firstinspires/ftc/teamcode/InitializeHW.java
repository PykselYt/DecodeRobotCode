package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

public class InitializeHW {


    //public static HardwareMap hm;
    public static IMU imu;
    public static DcMotorController CHM, EHM;
    public static ServoController CHS, EHS, SH;
    public static DigitalChannelController CHD, EHD;

    public static void InitHubs(HardwareMap hm){
        CHM=hm.get(DcMotorController.class, "Control Hub");
        EHM=hm.get(DcMotorController.class, "Expansion Hub 2");
        CHS=hm.get(ServoController.class, "Control Hub");
        EHS=hm.get(ServoController.class, "Expansion Hub 2");
        SH=hm.get(ServoController.class, "Servo Hub 1");
        CHD=hm.get(DigitalChannelController.class, "Control Hub");
        EHD=hm.get(DigitalChannelController.class, "Expansion Hub 2");

        MotorConfigurationType mct;

        for(int i = 0; i < 4; i++){
            mct = CHM.getMotorType(i);
            mct.setAchieveableMaxRPMFraction(1);

            CHM.setMotorType(i, mct);

            mct = EHM.getMotorType(i);
            mct.setAchieveableMaxRPMFraction(1);

            EHM.setMotorType(i, mct);
        }

        imu = hm.get(IMU.class, "imuSigma");
        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        )));
        imu.resetYaw();
    }

    public static void InitAll(HardwareMap hm){
        InitHubs(hm);

        InitChassis(hm);
        InitShooter(hm);
        InitOther(hm);
    }

    public static void InitChassis(HardwareMap  hm){
        Chassis.FL=hm.get(DcMotor.class, "FL");
        Chassis.FR=hm.get(DcMotor.class, "FR");
        Chassis.BL=hm.get(DcMotor.class, "BL");
        Chassis.BR=hm.get(DcMotor.class, "BR");

        Chassis.FL.setDirection(DcMotorSimple.Direction.REVERSE);
        Chassis.FR.setDirection(DcMotorSimple.Direction.FORWARD);
        Chassis.BL.setDirection(DcMotorSimple.Direction.REVERSE);
        Chassis.BR.setDirection(DcMotorSimple.Direction.REVERSE);

        Chassis.FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Chassis.FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Chassis.BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Chassis.BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public static void InitShooter(HardwareMap  hm) {
        Shooter.M1=hm.get(DcMotor.class, "Shooter1");
        Shooter.M2=hm.get(DcMotor.class, "Shooter2");

        Shooter.M1.setDirection(DcMotor.Direction.FORWARD);
        Shooter.M2.setDirection(DcMotor.Direction.FORWARD);

        Shooter.M1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Shooter.M2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Shooter.pitcher=hm.get(Servo.class, "pitcher");

        Shooter.rotator=hm.get(DcMotor.class, "rotator");
        Shooter.rotator.setDirection(DcMotorSimple.Direction.FORWARD);
        Shooter.rotator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //Shooter.rotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //Shooter.rotator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public static void InitOther(HardwareMap  hm) {
        Spinner.I1=hm.get(DcMotor.class, "IntakeMotor");
        Spinner.I1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Spinner.encoder=hm.get(DcMotor.class, "FL");
        Spinner.encoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Spinner.encoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Spinner.SP1=hm.get(CRServo.class, "Spinner1");
        Spinner.SP2=hm.get(CRServo.class, "Spinner2");

    }



}
