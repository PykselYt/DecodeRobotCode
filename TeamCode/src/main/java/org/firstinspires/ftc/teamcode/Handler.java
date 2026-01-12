package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class Handler {

    public static Gamepad prev1 = new Gamepad();
    public static Gamepad prev2 = new Gamepad();
    public static boolean reverse=false;
    public static boolean shooterOn=false;

    public static boolean isPutaJos=false;
    public static double pow=-0.7;

    public static double getPowerSigned(double h, double p){
        double sgn = Math.signum(h);
        h = Math.abs(h);
        for(int i = 1; i < p; i++){
            h *= h;
        }
        return sgn * h;
    }

    public static void update(Gamepad gm1, Gamepad gm2){

        if(gm1.dpad_left){
            Shooter.powerRotator(-0.4);
        }else if(gm1.dpad_right){
            Shooter.powerRotator(0.4);
        }else Shooter.powerRotator(0);

        if(gm1.triangle && gm1.triangle!=prev1.triangle){
            if(shooterOn){
                Shooter.PowerDown();
                Spinner.switchPos();
                shooterOn=false;
            }else{
                Shooter.PowerUp();
                Spinner.switchPos();
                shooterOn=true;
            }
        }

        if(gm1.x && gm1.x!=prev1.x){
            if(isPutaJos){
                Spinner.susPuta();
            }else{
                Spinner.josPuta();
            }
        }

        if(gm1.circle && gm1.circle!=prev1.circle){
            Spinner.nextBall();
        }
        if(gm1.square && gm1.square!=prev1.square){
            Spinner.prevBall();
        }

        if(gm1.right_bumper){
            Spinner.Intake();
        }else{
            Spinner.stopIntake();
        }

        if(gm1.dpad_up && gm1.dpad_up!=prev1.dpad_up)
            Shooter.FarPitch();
        if(gm1.dpad_down && gm1.dpad_down!=prev1.dpad_down)
            Shooter.ClosePitch();

        Chassis.RRD(
                (reverse ? -1 : 1) * getPowerSigned(gm1.left_stick_x, 3),
                (reverse ? 1 : -1) * getPowerSigned(gm1.left_stick_y, 3),
                gm2.cross ? gm1.right_trigger - gm1.left_trigger : getPowerSigned(gm1.right_trigger - gm1.left_trigger, 3) * pow
        );



        Spinner.update();
        Shooter.update();
        prev1.copy(gm1);
        prev2.copy(gm2);
    }
}
