package org.firstinspires.ftc.teamcode.common.CV;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class redDetector extends OpenCvPipeline {
    public double leftValue, midValue;
    static public Location pos;
    Telemetry telemetry;
    Mat lowMat = new Mat();
    Mat highMat = new Mat();

    Mat mat = new Mat();
    //define detection bounding boxes
    Rect LEFT_ROI = new Rect(
            new Point(0,0),
            new Point(600, 720)
    );
    Rect MID_ROI = new Rect(
            new Point(600, 0),
            new Point(1200, 720)
    );

    //define Minimum and Maximum blue values (what is blue and what is not)
    Scalar minHSV1 = new Scalar(0/2, 50,50);
    Scalar maxHSV1 = new Scalar(30/2, 240, 240);
    Scalar minHSV2 = new Scalar(330/2, 50,50);
    Scalar maxHSV2 = new Scalar(360/2, 240, 240);

    //initalise telemetry
    public redDetector(Telemetry t){telemetry = t;}
    @Override
    public Mat processFrame(Mat input) {
        //Convert to HSV
        Imgproc.cvtColor(input, lowMat, Imgproc.COLOR_RGB2HSV);
        Imgproc.cvtColor(input, highMat, Imgproc.COLOR_RGB2HSV);

        //Make all pixels that are blue turn white, everything else black
        Core.inRange(lowMat, minHSV1, maxHSV1, lowMat);
        Core.inRange(highMat, minHSV2, maxHSV2, highMat);

        Core.add(lowMat, highMat, mat);

        //make matrixes of all the pixels inside of each of the rectangles
        Mat left = lowMat.submat(LEFT_ROI);
        Mat mid = lowMat.submat(MID_ROI);

        //draw rects on the screen
        Imgproc.rectangle(lowMat, LEFT_ROI, new Scalar(255,0,0));
        Imgproc.rectangle(lowMat, MID_ROI, new Scalar(255,0,0));

        //find the average greyness
        leftValue = Core.sumElems(left).val[0] / LEFT_ROI.area()/255;
        midValue = Core.sumElems(mid).val[0] / MID_ROI.area()/255;

        if(leftValue>0.1){
            pos = Location.LEFT;
        } else if (midValue>0.05) {
            pos = Location.CENTER;
        }  else{
            pos = Location.RIGHT;
        }
        telemetry.addData("left:", leftValue);
        telemetry.addData("middle:", midValue);
        return lowMat;
    }
}
