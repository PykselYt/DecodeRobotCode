package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Other.VisionPipeline;

import java.util.concurrent.TimeUnit;

@TeleOp
public class TeleOpStd extends LinearOpMode {

    Gamepad gm1 = new Gamepad(), gm2 = new Gamepad();

    public static double exposureDuration = 100;

    @Override
    public void runOpMode() throws InterruptedException {

        InitializeHW.InitAll(hardwareMap);


        VisionPipeline.exposureControl.setMode(ExposureControl.Mode.Manual);
        VisionPipeline.exposureControl.setExposure((long) exposureDuration, TimeUnit.MILLISECONDS);

        waitForStart();

        while(opModeIsActive()){
            gm1.copy(gamepad1);
            gm2.copy(gamepad2);
            Handler.update(gm1, gm2);
        }


    }
}
