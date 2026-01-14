package org.firstinspires.ftc.teamcode.Other;


import org.openftc.easyopencv.OpenCvCamera;


public class Camera {

    public static OpenCvCamera robotCamera;

    public static org.firstinspires.ftc.teamcode.Other.VisionPipeline pipeline;


    public void stop() {
    
        robotCamera.stopStreaming();
        robotCamera.closeCameraDevice();
    }

}
