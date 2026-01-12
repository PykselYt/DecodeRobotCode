package org.firstinspires.ftc.teamcode.Other;

import org.openftc.easyopencv.*;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class VisionPipeline extends OpenCvPipeline {

    public boolean objectDetected = false;
    public int objectX = -1;
    public double objectArea = 0;

    private Mat hsv = new Mat();
    private Mat mask = new Mat();

    @Override
    public Mat processFrame(Mat input) {

        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

        // EXEMPLU: culoare roșie (ajustezi)
        Scalar lowGreen = new Scalar(35, 100, 100);
        Scalar highGreen = new Scalar(85, 255, 255);

        Scalar lowPurple = new Scalar(130, 50, 50);
        Scalar highPurple = new Scalar(160, 255, 255);

        Mat maskGreen = new Mat();
        Mat maskPurple = new Mat();
        Mat mask = new Mat();

        Core.inRange(hsv, lowGreen, highGreen, maskGreen);
        Core.inRange(hsv, lowPurple, highPurple, maskPurple);

// combinăm măștile
        Core.bitwise_or(maskGreen, maskPurple, mask);

        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(
                mask,
                contours,
                new Mat(),
                Imgproc.RETR_EXTERNAL,
                Imgproc.CHAIN_APPROX_SIMPLE
        );

        objectDetected = false;
        objectArea = 0;
        objectX = -1;

        if (!contours.isEmpty()) {
            MatOfPoint best = contours.get(0);
            double maxArea = Imgproc.contourArea(best);

            for (MatOfPoint c : contours) {
                double area = Imgproc.contourArea(c);
                if (area > maxArea) {
                    best = c;
                    maxArea = area;
                }
            }

            Rect r = Imgproc.boundingRect(best);
            objectX = r.x + r.width / 2;
            objectArea = maxArea;
            objectDetected = true;

            // debug
            Imgproc.rectangle(input, r, new Scalar(0, 255, 0), 2);
        }

        return input;
    }
}
