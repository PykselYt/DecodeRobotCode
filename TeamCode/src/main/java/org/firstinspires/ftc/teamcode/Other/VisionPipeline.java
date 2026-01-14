package org.firstinspires.ftc.teamcode.Other;

import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.openftc.easyopencv.*;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;




public class VisionPipeline extends OpenCvPipeline {

    public static ExposureControl exposureControl;

    public static double minSize=20;
    public static boolean badOne=false;
    public static class detectedObject {
        public double x;
        public double y;
        public double area;
        public boolean green=false;
    }

    private Mat hsv = new Mat();

    @Override
    public Mat processFrame(Mat input) {

        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

        Scalar lowGreen = new Scalar(50, 100, 100);
        Scalar highGreen = new Scalar(100, 255, 255);

        Scalar lowPurple = new Scalar(130, 50, 50);
        Scalar highPurple = new Scalar(160, 255, 255);

        Mat maskGreen = new Mat();
        Mat maskPurple = new Mat();
        //Mat mask = new Mat();

        Core.inRange(hsv, lowGreen, highGreen, maskGreen);
        Core.inRange(hsv, lowPurple, highPurple, maskPurple);

        //Core.bitwise_or(maskGreen, maskPurple, mask);
        ArrayList<detectedObject> objects = new ArrayList<>();

        List<MatOfPoint> contoursGreen = new ArrayList<>();
        Imgproc.findContours(
                maskGreen,
                contoursGreen,
                new Mat(),
                Imgproc.RETR_EXTERNAL,
                Imgproc.CHAIN_APPROX_SIMPLE
        );

        List<MatOfPoint> contoursPurple = new ArrayList<>();
        Imgproc.findContours(
                maskPurple,
                contoursPurple,
                new Mat(),
                Imgproc.RETR_EXTERNAL,
                Imgproc.CHAIN_APPROX_SIMPLE
        );
        int pointNumber = 0;

        while(pointNumber < contoursGreen.size()) {
            MatOfPoint currentPoint = contoursGreen.get(pointNumber);
            double maxArea=Imgproc.contourArea(currentPoint);
            if(maxArea<minSize){
                pointNumber++;
                continue;
            }
            double area =Imgproc.contourArea(currentPoint);
            double perimeter = Imgproc.arcLength(new MatOfPoint2f(currentPoint.toArray()), true);
            double circularity = 4* Math.PI * area / (perimeter * perimeter);
            Rect r = Imgproc.boundingRect(currentPoint);
            if(circularity < 0.67 && badOne){
                Imgproc.rectangle(input, r, new Scalar(255, 0, 0), 2);
                pointNumber++;
                continue;
            }
            detectedObject x=new detectedObject();
            x.x = r.x + (double) r.width / 2;
            x.y = r.y + (double) r.height / 2;
            x.area = maxArea;
            x.green=true;

            objects.add(x);

            Imgproc.rectangle(input, r, new Scalar(0, 255, 0), 2);
            pointNumber++;
        }


        pointNumber=0;
        while(pointNumber < contoursPurple.size()) {
            MatOfPoint currentPoint = contoursPurple.get(pointNumber);
            double maxArea=Imgproc.contourArea(currentPoint);
            if(maxArea<minSize){
                pointNumber++;
                continue;
            }
            double area =Imgproc.contourArea(currentPoint);
            double perimeter = Imgproc.arcLength(new MatOfPoint2f(currentPoint.toArray()), true);
            double circularity = 4* Math.PI * area / (perimeter * perimeter);
            Rect r = Imgproc.boundingRect(currentPoint);
            if(circularity < 0.67 && badOne){
                Imgproc.rectangle(input, r, new Scalar(255, 0, 0), 2);
                pointNumber++;
                continue;
            }
            detectedObject x=new detectedObject();
            x.x = r.x + (double) r.width / 2;
            x.y = r.y + (double) r.height / 2;
            x.area = maxArea;
            x.green=false;

            objects.add(x);

            Imgproc.rectangle(input, r, new Scalar(255, 0, 255), 2);
            pointNumber++;
        }


        maskGreen.release();
        maskPurple.release();
        //mask.release();

        contoursGreen.clear();
        contoursPurple.clear();
        objects.clear();

        return input;
    }
}
