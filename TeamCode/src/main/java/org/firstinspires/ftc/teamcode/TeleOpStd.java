package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp
public class TeleOpStd extends LinearOpMode {

    Gamepad gm1 = new Gamepad(), gm2 = new Gamepad();



    @Override
    public void runOpMode() throws InterruptedException {

        InitializeHW.InitAll(hardwareMap);

        waitForStart();

        while(opModeIsActive()){
            gm1.copy(gamepad1);
            gm2.copy(gamepad2);
            Handler.update(gm1, gm2);
        }


    }
}
